package com.oskr19.easyshop.screens.categories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.oskr19.easyshop.MainActivity
import com.oskr19.easyshop.core.presentation.extensions.showHide
import com.oskr19.easyshop.databinding.FragmentCategoriesBinding
import com.oskr19.easyshop.screens.categories.presentation.adapter.CategoryAdapter
import com.oskr19.easyshop.screens.common.dto.Category
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    private val args by navArgs<CategoriesFragmentArgs>()
    val viewModel by activityViewModels<CategoriesViewModel>()

    @Inject
    internal lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
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
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        (requireActivity() as MainActivity).supportActionBar?.customView?.showHide(false)
    }

    private fun initViews() {
        binding.listView.adapter = adapter
        binding.viewModel = viewModel
        binding.states.loading.viewModel = viewModel
        binding.states.disconnected.viewModel = viewModel
        binding.states.root.showHide(true)
    }

    private fun setListeners() {
        binding.listView.setOnItemClickListener { _, view, position, _ ->
            val action = CategoriesFragmentDirections.categoriesToCategories()
            action.categoryId = adapter.getItem(position).id
            view.findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        if (args.categoryId.isNullOrEmpty()) {
            viewModel.getCategories().observe(viewLifecycleOwner, { value ->
                value?.let {
                    adapter.setData(it)
                }
            })
        } else {
            viewModel.getCategoryInfo(args.categoryId?:"").observe(viewLifecycleOwner,::observeCategory)
        }
    }

    private fun observeCategory(category: Category?) {
        category?.let {
            when {
                viewModel.isLastCategory -> {
                    viewModel.isLastCategory = false
                    val size = category.pathFromRoot?.size?:2
                    viewModel.getCategoryInfo(category.pathFromRoot?.get(size-2)?.id?:args.categoryId?:category.id).observe(viewLifecycleOwner,::observeCategory)
                }
                it.childrenCategories.isEmpty() -> {
                    viewModel.isLastCategory = true
                    val action = CategoriesFragmentDirections.categoriesToResult("", "", category.id)
                    binding.root.findNavController().navigate(action)
                }
                else -> {
                    viewModel.isLastCategory = false
                    adapter.setData(it.childrenCategories)
                }
            }

        }
    }
}