package com.oskr19.easyshop.screens.search.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.oskr19.easyshop.R
import com.oskr19.easyshop.databinding.FragmentSearchResultBinding
import com.oskr19.easyshop.screens.search.presentation.adapter.ProductAdapter
import com.oskr19.easyshop.screens.search.presentation.mapper.ProductUIMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultFragment : Fragment(), ProductItemListener {

    @Inject
    protected lateinit var adapter: ProductAdapter

    @Inject
    protected lateinit var mapper: ProductUIMapper

    private lateinit var binding: FragmentSearchResultBinding
    private val args: SearchResultFragmentArgs by navArgs()
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater,
            R.layout.fragment_search_result, container, false)

        initViews()
        setListeners()
        observeViewModel()

        return binding.root
    }

    private fun initViews() {
        adapter.listener = this
        with(binding.recyclerView){
            adapter = this@SearchResultFragment.adapter
            layoutManager = LinearLayoutManager(this@SearchResultFragment.requireContext())
        }

        binding.searchToolbar.textViewSearch.text = args.query
        viewModel.search(args.query)
    }

    private fun setListeners() {
        binding.searchToolbar.toolbarContainer.setOnClickListener {
            val action = SearchResultFragmentDirections.resultToSearch(args.query)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.searchResponse.observe(viewLifecycleOwner,{ value ->
            value?.let {
                adapter.setData(it)
            }
        })
    }

    override fun onItemClick(position: Int) {

    }

    override fun setFavorite(position: Int, isFavorite: Boolean) {

    }
}