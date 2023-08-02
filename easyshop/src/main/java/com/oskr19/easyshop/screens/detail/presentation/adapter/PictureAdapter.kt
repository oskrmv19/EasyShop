package com.oskr19.easyshop.screens.detail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.oskr19.easyshop.R
import com.oskr19.easyshop.databinding.PictureItemBinding
import com.oskr19.easyshop.screens.common.dto.Picture

/**
 * Created by oscar.vergara on 28/07/2021
 */
class PictureAdapter : RecyclerView.Adapter<PictureAdapter.ViewHolder>() {
    private val pictures: ArrayList<Picture> = arrayListOf()

    fun setPictureList(list: List<Picture>?) {
        pictures.clear()
        list?.let { pictures.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<PictureItemBinding>(
            inflater,
            R.layout.picture_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount() = pictures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.picture = pictures[position]
    }

    class ViewHolder(var view: PictureItemBinding) : RecyclerView.ViewHolder(view.root)
}
