package com.oskr19.easyshop.screens.search.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.oskr19.easyshop.core.presentation.dialog.DialogWindow
import com.oskr19.easyshop.core.presentation.dialog.FullActions
import com.oskr19.easyshop.core.presentation.extensions.showHide
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

    private var _dialog: Dialog? = null
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

        binding.viewModel = viewModel
        binding.states.loading.viewModel = viewModel
        binding.states.disconnected.viewModel = viewModel
        binding.states.root.showHide(true)

        adapter.listener = this

        with(binding.recyclerView) {
            adapter = this@SearchResultFragment.adapter
            layoutManager = LinearLayoutManager(this@SearchResultFragment.requireContext())
        }

        binding.searchToolbar.textViewSearch.text = args.query

    }

    private fun setListeners() {
        binding.searchToolbar.toolbarContainer.setOnClickListener {
            val action = SearchResultFragmentDirections.resultToSearch(args.query)
            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.results.observe(viewLifecycleOwner, { value ->
            value?.let {
                adapter.setData(it)
            }
        })

        viewModel.status.observe(viewLifecycleOwner, { value ->
            value?.let {
                _dialog?.dismiss()
                if(it.isError()){
                    _dialog = DialogWindow.dialogOnGenericError(requireContext(), object: FullActions {
                        override fun onNegative() {
                            //nothing is necessary
                        }

                        override fun onPositive() {
                            viewModel.search(binding.searchToolbar.textViewSearch.text.toString())
                        }
                    })

                    _dialog?.show()
                }
            }
        })

        if (adapter.itemCount == 0) {
            viewModel.search(binding.searchToolbar.textViewSearch.text.toString())
        }
    }

    override fun onItemClick(position: Int) {
        viewModel.setSelectedProduct(position)
        val action = SearchResultFragmentDirections.resultsToDetail()
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun setFavorite(position: Int, isFavorite: Boolean) {

    }
}