package com.oskr19.easyshop.screens.detail.presentation

import android.os.Bundle
import android.text.TextUtils
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oskr19.easyshop.R
import com.oskr19.easyshop.databinding.AttributesBottomSheetBinding
import com.oskr19.easyshop.databinding.TableRowEvenBinding
import com.oskr19.easyshop.databinding.TableRowGrouptHeaderBinding
import com.oskr19.easyshop.databinding.TableRowOddBinding
import com.oskr19.easyshop.screens.common.dto.Attribute
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AttributesListDialogFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "AttributesListDialogFragment"
        var attributes: List<Attribute>? = null
    }

    private lateinit var binding: AttributesBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AttributesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attributes?.let {
            createTableRows(it)
        }
    }

    private fun createTableRows(attributes: List<Attribute>) {

        val attrs = attributes.sortedWith(compareBy { it.groupName })
        var lastGroup: String? = null


        attrs.forEachIndexed { index, attribute ->

            var isOdd = index % 2 == 0

            val inflater = LayoutInflater.from(
                ContextThemeWrapper( binding.root.context, R.style.TableLayoutThemeGray )
            )

            if (lastGroup != attribute.groupName && !TextUtils.isEmpty(attribute.groupName)) {

                isOdd = true

                val binding = TableRowGrouptHeaderBinding.inflate( inflater, null, false )
                binding.groupName = attribute.groupName
                lastGroup = attribute.groupName
                this.binding.tableAttributes.addView(binding.root)
            }

            if (isOdd) {

                val binding = TableRowOddBinding.inflate( inflater,null,false)
                binding.attr = attribute.name to attribute.valueName
                this.binding.tableAttributes.addView(binding.root)
            } else {

                val binding = DataBindingUtil.inflate<TableRowEvenBinding>(
                    inflater,
                    R.layout.table_row_even, null, false
                )
                binding.attr = attribute.name to attribute.valueName
                this.binding.tableAttributes.addView(binding.root)
            }
        }
    }
}