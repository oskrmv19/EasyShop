package com.oskr19.easyshop.screens.search.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.oskr19.easyshop.MainActivity
import com.oskr19.easyshop.R
import com.oskr19.easyshop.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSearchBinding
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        setupToolbar()

        return binding.root
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarLayout.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu);
        val item = menu.findItem(R.id.action_search)
        val sv = item.actionView as SearchView

        sv.queryHint = getString(R.string.search_title)

        item.icon = null
        item.title = null
        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                sv.setOnQueryTextListener(this@SearchFragment)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                (requireActivity() as MainActivity).setSupportActionBar(null)
                Navigation.findNavController(binding.toolbarLayout.toolbar).popBackStack()
                return true
            }

        })

        binding.toolbarLayout.root.post {
            binding.toolbarLayout.toolbar.menu.performIdentifierAction(
                R.id.action_search,
                Menu.FLAG_PERFORM_NO_CLOSE
            )
            binding.toolbarLayout.root.visibility = View.VISIBLE
        }

        args.query?.let {
            sv.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text).setTextColor(ResourcesCompat.getColor(resources,R.color.orange,null))
            sv.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text).setText(it,false)
        }

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            val action = SearchFragmentDirections.searchToResult(it)
            Navigation.findNavController(binding.root)
                .navigate(action,NavOptions.Builder().setPopUpTo(R.id.homeFragment,false).build())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}