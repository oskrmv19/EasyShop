package com.oskr19.easyshop.screens.detail.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.oskr19.easyshop.MainActivity
import com.oskr19.easyshop.R
import com.oskr19.easyshop.core.presentation.dialog.DialogWindow
import com.oskr19.easyshop.core.presentation.dialog.FullActions
import com.oskr19.easyshop.core.presentation.extensions.setHeightPercent
import com.oskr19.easyshop.core.presentation.extensions.showHide
import com.oskr19.easyshop.core.presentation.transformer.CarouselEffectTransformer
import com.oskr19.easyshop.databinding.FragmentDetailBinding
import com.oskr19.easyshop.databinding.TableRowEvenBinding
import com.oskr19.easyshop.databinding.TableRowOddBinding
import com.oskr19.easyshop.screens.detail.presentation.adapter.PictureAdapter
import com.oskr19.easyshop.screens.search.presentation.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    internal lateinit var pictureAdapter: PictureAdapter
    private lateinit var binding: FragmentDetailBinding

    private var _dialog: Dialog? = null
    private val viewModel by viewModels<DetailViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
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
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarLayout.toolbar)
        NavigationUI.setupActionBarWithNavController(requireActivity() as MainActivity,binding.root.findNavController())
        binding.toolbarLayout.toolbar.title = getString(R.string.details)
        binding.toolbarLayout.toolbar.setNavigationOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun initViews() {
        binding.viewModel = viewModel
        binding.states.loading.viewModel = viewModel
        binding.states.disconnected.viewModel = viewModel
        binding.states.root.showHide(true)
        binding.viewPager.setPageTransformer(
            CarouselEffectTransformer(
                requireContext(),
                binding.viewPager
            )
        )
        binding.viewPager.adapter = pictureAdapter
        binding.viewPager.setHeightPercent(0.35)

        viewModel.setProduct(searchViewModel.getSelectedProduct())

        //Call getDetail
        viewModel.getDetail()
    }

    private fun setListeners() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicator.text = String.format(
                    getString(R.string.indicator_template),
                    position + 1,
                    pictureAdapter.itemCount
                )
            }
        })

        binding.textViewOthersAttributes.setOnClickListener {
            viewModel.detail.value?.let {
                AttributesListDialogFragment.attributes = it.attributes
                childFragmentManager
                    .beginTransaction()
                    .add(AttributesListDialogFragment(), AttributesListDialogFragment.tag)
                    .commit()
            }
        }
    }

    private fun observeViewModel() {

        viewModel.detail.observe(viewLifecycleOwner, { value ->
            value?.let {
                createTableRows()
                pictureAdapter.setPictureList(it.pictures)
                binding.product = it
                it.pictures?.let { pictures ->
                    binding.indicator.text =
                        String.format(getString(R.string.indicator_template), 1, pictures.size)
                } ?: binding.indicator.showHide(false)
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
                            viewModel.getDetail()
                        }
                    })

                    _dialog?.show()
                }
            }
        })
    }

    private fun createTableRows() {
        searchViewModel.getSelectedProduct().attributes?.let {
            it.forEachIndexed { index, attribute ->
                val inflater = LayoutInflater.from(ContextThemeWrapper(binding.root.context, R.style.TableLayoutThemeOrange))

                if (index % 2 == 0) {
                    val binding = DataBindingUtil.inflate<TableRowOddBinding>(
                        inflater,
                        R.layout.table_row_odd,
                        null,
                        false
                    )
                    binding.attr = attribute.name to attribute.valueName
                    this.binding.tableAttributes.addView(binding.root)
                } else {
                    val binding = DataBindingUtil.inflate<TableRowEvenBinding>(
                        inflater,
                        R.layout.table_row_even,
                        null,
                        false
                    )
                    binding.attr = attribute.name to attribute.valueName
                    this.binding.tableAttributes.addView(binding.root)
                }

            }
        }
    }
}