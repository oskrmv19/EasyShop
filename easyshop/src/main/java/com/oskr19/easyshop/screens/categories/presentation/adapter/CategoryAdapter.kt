package com.oskr19.easyshop.screens.categories.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.oskr19.easyshop.R
import com.oskr19.easyshop.screens.common.dto.Category

/**
 * Created by oscar.vergara on 4/08/2021
 */
class CategoryAdapter: BaseAdapter() {
    private var categories = arrayListOf<Category>()

    fun setData(list: List<Category>){
        categories.clear()
        categories.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount() = categories.size

    override fun getItem(position: Int) = categories[position]

    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(convertView != null)
            return convertView

        var inflater = LayoutInflater.from(parent?.context)
        if(inflater == null){
           inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val content = inflater.inflate(R.layout.category_item,parent,false)
        content.findViewById<TextView>(R.id.textViewTitle).text = categories[position].name

        return content
    }
}
