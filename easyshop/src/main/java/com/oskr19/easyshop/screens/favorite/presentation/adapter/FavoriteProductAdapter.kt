package com.oskr19.easyshop.screens.favorite.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oskr19.easyshop.databinding.VerticalProductItemBinding
import com.oskr19.easyshop.screens.search.presentation.ProductItemListener
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI

/**
 * Created by oscar.vergara on 27/07/2021
 */
class FavoriteProductAdapter : RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>() {

    private val products = arrayListOf<ProductUI>()
    lateinit var listener: ProductItemListener

    fun setData(list: List<ProductUI>) {
        products.clear()
        products.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            VerticalProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    fun getItem(position: Int) = products[position]
    fun data() = products

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.position = position
        holder.view.product = getItem(position)
        holder.view.listener = listener
    }

    inner class ViewHolder(var view: VerticalProductItemBinding) : RecyclerView.ViewHolder(view.root)
}
