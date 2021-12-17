package com.example.test.detailActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.data.responseModels.Products
import com.example.test.databinding.LayoutProductItemBinding


class ProductAdapter(
    val context: Context,
    val list: List<Products>
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(var binding: LayoutProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!list[position].image.isNullOrEmpty()) {
            Glide.with(context)
                .load(list[position].image)
                .into(holder.binding.catImage)
        }
        holder.binding.pName.text = list[position].name
        holder.binding.pack.text = list[position].pack
        holder.binding.price.text = "₹"+list[position].price
    }

    override fun getItemCount() = list.size
}