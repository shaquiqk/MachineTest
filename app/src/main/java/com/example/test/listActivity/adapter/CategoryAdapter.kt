package com.example.test.listActivity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.data.responseModels.Contents
import com.example.test.databinding.LayoutCatItemBinding


class CategoryAdapter(
    val context: Context,
    val list: List<Contents>,
    var onItemClick: (item: Contents) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(var binding: LayoutCatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutCatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!list[position].c_image.isNullOrEmpty()) {
            Glide.with(context)
                .load(list[position].c_image)
                .into(holder.binding.catImage)
        }
        holder.binding.catName.text = list[position].c_name

        holder.itemView.setOnClickListener {
            onItemClick.invoke(list[position])
        }
    }

    override fun getItemCount() = list.size
}