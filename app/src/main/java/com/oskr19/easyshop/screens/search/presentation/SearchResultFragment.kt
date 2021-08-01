package com.oskr19.easyshop.screens.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.oskr19.easyshop.R
import com.oskr19.easyshop.databinding.FragmentSearchResultBinding
import com.oskr19.easyshop.screens.common.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.search.presentation.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultFragment : Fragment(), ProductItemListener {

    @Inject
    internal lateinit var adapter: ProductAdapter

    @Inject
    internal lateinit var mapper: ProductUIMapper

    private lateinit var binding: FragmentSearchResultBinding
    private val args: SearchResultFragmentArgs by navArgs()
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)

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
        viewModel.results.observe(viewLifecycleOwner,{ value ->
            value?.let {
                adapter.setData(it)
            }
        })
    }

    override fun onItemClick(position: Int) {
        viewModel.setSelectedProduct(position)
        val action = SearchResultFragmentDirections.resultsToDetail()
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun setFavorite(position: Int, isFavorite: Boolean) {

    }
}