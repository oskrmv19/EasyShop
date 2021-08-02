package com.oskr19.easyshop.screens.search.presentation

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.oskr19.easyshop.MainActivity
import com.oskr19.easyshop.R
import com.oskr19.easyshop.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var _searchManager: SearchManager
    private lateinit var binding: FragmentSearchBinding
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarLayout.toolbar)
        NavigationUI.setupActionBarWithNavController(requireActivity() as MainActivity,binding.root.findNavController())
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarLayout.toolbar.title = null
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val item = menu.findItem(R.id.action_search)
        val sv = item.actionView as SearchView
        val txtSearch = sv.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        txtSearch.setHintTextColor(Color.LTGRAY)
        txtSearch.hint = getString(R.string.search_title)

        sv.setSearchableInfo(_searchManager.getSearchableInfo(
            ComponentName(requireContext(), MainActivity::class.java)
        ))

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                if(this@SearchFragment.isAdded) {
                    (requireActivity() as MainActivity).onBackPressed()
                } else {
                    item?.setOnActionExpandListener(null)
                }
                return true
            }

        })

        //Auto-Open search
        item.expandActionView()
        sv.onActionViewExpanded()

        //Update search text
        txtSearch.post {
            if(!TextUtils.isEmpty(args.query)) {
                txtSearch.text.clear()
                txtSearch.text = txtSearch.text.append(args.query)
                txtSearch.setSelection(txtSearch.text.length)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)

    }

}