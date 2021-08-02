package com.oskr19.easyshop.screens.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oskr19.easyshop.databinding.SearchProductItemBinding
import com.oskr19.easyshop.screens.search.presentation.ProductItemListener
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI

/**
 * Created by oscar.vergara on 27/07/2021
 */
class ProductAdapter (
    private val counters: ArrayList<ProductUI>,
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    lateinit var listener: ProductItemListener

    fun setData(list: List<ProductUI>) {
        counters.clear()
        counters.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchProductItemBinding.inflate( LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = counters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.position = position
        holder.view.product = counters[position]
        holder.view.listener = listener
    }

    inner class ViewHolder(var view: SearchProductItemBinding) : RecyclerView.ViewHolder(view.root)
}