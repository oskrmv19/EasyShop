package com.oskr19.easyshop.screens.detail.presentation

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.oskr19.easyshop.R
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

    private val viewModel by viewModels<DetailViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        initViews()
        setListeners()
        observeViewModel()

        return binding.root
    }

    private fun initViews() {
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
                    .add(AttributesListDialogFragment(), AttributesListDialogFragment.TAG)
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