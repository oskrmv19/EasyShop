package com.oskr19.easyshop.screens.search.presentation

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oskr19.easyshop.MainActivity
import com.oskr19.easyshop.R
import com.oskr19.easyshop.core.presentation.base.BaseFragment
import com.oskr19.easyshop.core.presentation.dialog.DialogAction
import com.oskr19.easyshop.core.presentation.dialog.DialogWindow
import com.oskr19.easyshop.core.presentation.extensions.showHide
import com.oskr19.easyshop.databinding.FragmentSearchResultBinding
import com.oskr19.easyshop.screens.common.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.detail.presentation.DetailViewModel
import com.oskr19.easyshop.screens.favorite.presentation.FavoriteActionViewModel
import com.oskr19.easyshop.screens.search.presentation.adapter.SearchProductAdapter
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import com.oskr19.easyshop.screens.search.presentation.model.SearchParams
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class SearchResultFragment : BaseFragment(), ProductItemListener {

    @Inject
    internal lateinit var adapterSearch: SearchProductAdapter

    @Inject
    internal lateinit var mapper: ProductUIMapper

    private lateinit var binding: FragmentSearchResultBinding
    private val args: SearchResultFragmentArgs by navArgs()
    private val viewModel: SearchViewModel by activityViewModels()
    private val favoriteActionViewModel: FavoriteActionViewModel by activityViewModels()
    private var searchParams = SearchParams()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initViews()
        setListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.searchToolbar.toolbar)
        NavigationUI.setupActionBarWithNavController(requireActivity() as MainActivity,binding.root.findNavController())
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.searchToolbar.arrowBack.showHide(true)
        binding.searchToolbar.arrowBack.setOnClickListener {
            val action = SearchResultFragmentDirections.resultToSearch(args.query)
            it.findNavController().navigate(action, NavOptions.Builder().setPopUpTo(R.id.searchFragment, false).build())
        }
    }

    private fun initViews() {

        binding.viewModel = viewModel
        binding.states.loading.viewModel = viewModel
        binding.states.disconnected.viewModel = viewModel
        binding.states.root.showHide(true)

        adapterSearch.listener = this

        with(binding.recyclerView) {
            adapter = this@SearchResultFragment.adapterSearch
            layoutManager = LinearLayoutManager(this@SearchResultFragment.requireContext())
        }

        binding.searchToolbar.textViewSearch.text = args.query
        binding.recyclerView.addOnScrollListener(setupScrollListener())

    }

    private fun setListeners() {
        binding.searchToolbar.toolbarContainer.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack(R.id.searchFragment, false)
        }
    }

    private fun observeViewModel() {
        viewModel.results.observe(viewLifecycleOwner, { value ->
            value?.let {
                binding.loadingMore.showHide(false)
                adapterSearch.setData(it)
            }
            binding.textViewNoProducts.showHide(value.isNullOrEmpty())
        })

        viewModel.status.observe(viewLifecycleOwner, { value ->
            value?.let {
                _dialog?.dismiss()
                when {
                    it.isError() -> {
                        _dialog =
                            DialogWindow.dialogOnGenericError(requireContext(), object : DialogAction    {
                                override fun onPositive() {
                                    viewModel.search()
                                }
                            })
                        _dialog?.show()
                    }
                }
            }
        })

        viewModel.searchParams.observe(viewLifecycleOwner,{ value ->
            value?.let {
                searchParams = it
            }
        })

        viewModel.setParams(args.query,args.categoryId,args.sellerId)
        viewModel.search()
    }

    override fun onItemClick(position: Int) {
        viewModel.setSelectedPosition(position)
        val action = SearchResultFragmentDirections.resultsToDetail(adapterSearch.getItem(position).id)
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun setFavorite(position: Int, isFavorite: Boolean) {
        favoriteActionViewModel.setFavorite(adapterSearch.getItem(position).id, isFavorite)
            .observe(viewLifecycleOwner, { value ->
                value?.let {
                    adapterSearch.getItem(position).favorite = !isFavorite
                    adapterSearch.notifyItemChanged(position)
                }
            })
    }

    private fun setupScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                layoutManager?.let {

                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (binding.loadingMore.visibility != View.VISIBLE && !searchParams.lastPage) {

                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {

                            viewModel.search()
                            binding.loadingMore.showHide(true)
                            SystemClock.sleep(50)
                        }
                    }
                }
            }
        }
    }
}