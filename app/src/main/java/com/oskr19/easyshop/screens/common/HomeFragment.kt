package com.oskr19.easyshop.screens.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oskr19.easyshop.R
import com.oskr19.easyshop.core.presentation.base.BaseFragment
import com.oskr19.easyshop.core.presentation.dialog.DialogWindow
import com.oskr19.easyshop.core.presentation.extensions.showHide
import com.oskr19.easyshop.core.presentation.state.State
import com.oskr19.easyshop.core.presentation.viewmodel.BaseViewModel
import com.oskr19.easyshop.databinding.FragmentHomeBinding
import com.oskr19.easyshop.screens.common.dto.ProductSearch
import com.oskr19.easyshop.screens.favorite.presentation.FavoriteActionViewModel
import com.oskr19.easyshop.screens.favorite.presentation.FavoritesViewModel
import com.oskr19.easyshop.screens.favorite.presentation.adapter.FavoriteProductAdapter
import com.oskr19.easyshop.screens.favorite.presentation.state.NoFavoritesState
import com.oskr19.easyshop.screens.favorite.presentation.state.NoLatelySeenState
import com.oskr19.easyshop.screens.lately_seen.presentation.LatelySeenViewModel
import com.oskr19.easyshop.screens.lately_seen.presentation.adapter.LatelySeenProductAdapter
import com.oskr19.easyshop.screens.search.presentation.ProductItemListener
import com.oskr19.easyshop.screens.search.presentation.SearchViewModel
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    internal lateinit var favoriteAdapter: FavoriteProductAdapter

    @Inject
    internal lateinit var latelyAdapter: LatelySeenProductAdapter

    private val favoriteActionViewModel by viewModels<FavoriteActionViewModel>()
    private val favoritesViewModel by activityViewModels<FavoritesViewModel>()
    private val latelySeenViewModel by activityViewModels<LatelySeenViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolbar()
        initViews()
        setListeners()
        observeViewModel()

    }

    private fun setUpToolbar() {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.search_custom_toolbar_layout, null)
        val params = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            resources.getDimension(R.dimen.mtrl_toolbar_default_height).toInt() - 15
        )

        (requireActivity() as AppCompatActivity).supportActionBar?.let {
            it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            it.setCustomView(v, params)
            it.setDisplayHomeAsUpEnabled(true)
            it.customView?.setOnClickListener {
                binding.root.findNavController().navigate(R.id.searchFragment)
            }
            it.setBackgroundDrawable(
                ResourcesCompat.getDrawable( requireContext().resources, R.drawable.shape_toolbar_normal, null )
            )
            it.customView?.findViewById<TextView>(R.id.textViewSearch)?.text = getString(R.string.search_title)
        }
    }

    private fun initViews() {
        binding.viewModel = favoritesViewModel
        binding.states.disconnected.viewModel = favoritesViewModel
        binding.states.loading.viewModel = favoritesViewModel
        binding.states.root.showHide(true)

        favoriteAdapter.listener = object : ProductItemListener {
            override fun onItemClick(position: Int) {
                navigateToDetail(favoritesViewModel.getProduct(position))
            }

            override fun setFavorite(position: Int, isFavorite: Boolean) {
                onFavorite(favoriteAdapter.getItem(position), isFavorite, position)
            }
        }

        latelyAdapter.listener = object : ProductItemListener {
            override fun onItemClick(position: Int) {
                navigateToDetail(latelySeenViewModel.getProduct(position))
            }

            override fun setFavorite(position: Int, isFavorite: Boolean) {
                val product = latelyAdapter.getItem(position)
                favoriteActionViewModel.setFavorite(product.id, isFavorite)
                    .observe(viewLifecycleOwner, { value ->
                        value?.let {
                            onFavoriteFromLately(product,isFavorite, position)
                        }
                    })
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = favoriteAdapter
        }

        binding.recyclerView1.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = latelyAdapter
        }

    }

    private fun onFavoriteFromLately(product: ProductUI, favorite: Boolean, position: Int) {
        if (favorite) {
            val index =
                favoriteAdapter.data().indexOfFirst { it.id == product.id }
            favoriteAdapter.data().removeAt(index)
            favoriteAdapter.notifyItemRemoved(index)
        } else {
            product.favorite = !favorite
            favoriteAdapter.data().add(product)
            favoriteAdapter.notifyItemInserted(favoriteAdapter.data().size - 1)
        }
        latelyAdapter.getItem(position).favorite = !favorite
        latelyAdapter.notifyItemChanged(position)
        updateFavoriteContent()
    }

    private fun setListeners() {

        binding.textViewRemoveAll.setOnClickListener {
            favoriteAdapter.data().map {
                favoriteActionViewModel.setFavorite(it.id,it.favorite)
                updateLatelySeen(it.id)
            }
            favoriteAdapter.setData(listOf())
            updateFavoriteContent()
        }

        binding.textViewNoFavorites.setOnClickListener {
            val action = HomeFragmentDirections.homeToSearch()
            Navigation.findNavController(it).navigate(action)
        }

        binding.textViewFavorite.setOnClickListener {
            val action = HomeFragmentDirections.homeToCategories()
            it.findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        favoritesViewModel.status.observe(viewLifecycleOwner, ::handleError)
        latelySeenViewModel.status.observe(viewLifecycleOwner, ::handleError)

        setViewModel(favoritesViewModel)
        favoritesViewModel.getFavorites().observe(viewLifecycleOwner, { value ->
            value?.let {
                favoriteAdapter.setData(it)
                updateFavoriteContent()
                setViewModel(latelySeenViewModel)
                getLatelySeen()
            }
        })
    }

    private fun getLatelySeen() {
        latelySeenViewModel.fetchAll().observe(viewLifecycleOwner, { value ->
            value?.let {
                latelyAdapter.setData(it)
                binding.latelyContent.showHide(it.isNotEmpty())
            }
        })
    }

    private fun handleError(state: State?) {
        when {
            state?.isError() == true -> {
                _dialog?.dismiss()
                _dialog = DialogWindow.dialogOnError(requireContext())
                _dialog?.show()
            }
            state is NoFavoritesState -> {
                favoriteAdapter.setData(listOf())
                updateFavoriteContent()
                setViewModel(latelySeenViewModel)
                latelySeenViewModel.fetchAll()
            }
            state is NoLatelySeenState -> {
                binding.latelyContent.showHide(false)
            }
        }
    }

    private fun updateFavoriteContent() {
        val list = favoriteAdapter.data()
        binding.textViewRemoveAll.showHide(list.isNotEmpty())
        binding.textViewFavorite.text = String.format(getString(R.string.favorites), list.size)
        binding.textViewNoFavorites.showHide(list.isEmpty())
        binding.recyclerView.visibility = if (list.isNotEmpty()) View.VISIBLE else View.INVISIBLE
    }

    fun onFavorite(product: ProductUI, isFavorite: Boolean, position: Int) {
        favoriteActionViewModel.setFavorite(product.id, isFavorite)
            .observe(viewLifecycleOwner, { value ->
                value?.let {
                    favoriteAdapter.data().removeAt(position)
                    favoriteAdapter.notifyItemRemoved(position)
                    updateLatelySeen(product.id)
                    updateFavoriteContent()
                    favoriteAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun updateLatelySeen(id: String) {
        val index = latelyAdapter.data().indexOfFirst { it.id == id }
        if (index > -1) {
            latelyAdapter.getItem(index).favorite = latelyAdapter.getItem(index).favorite.not()
            latelyAdapter.notifyItemChanged(index)
        }
    }

    private fun setViewModel(viewModel: BaseViewModel) {
        binding.states.loading.viewModel = viewModel
        binding.states.disconnected.viewModel = viewModel
        binding.viewModel = viewModel
    }

    private fun navigateToDetail(product: ProductSearch) {
        searchViewModel.setSelectedProduct(product)
        val action = HomeFragmentDirections.homeToDetail(product.id)
        Navigation.findNavController(binding.root).navigate(action)
    }

}