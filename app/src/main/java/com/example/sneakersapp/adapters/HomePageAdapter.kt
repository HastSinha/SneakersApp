package com.example.sneakersapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakersapp.databinding.ItemProductListHomeBinding
import com.example.sneakersapp.modal.ProductItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HomePageAdapter(
    var list: List<ProductItem>,
    val onClick: (productItem: ProductItem, isForCart: Boolean) -> Unit,
) : RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder>() {

    inner class HomePageViewHolder(private val binding: ItemProductListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                Glide.with(ivProduct).load(list[position].image).into(ivProduct)
                tvProductName.text = list[position].name
                tvProductPrice.text = list[position].price.toString()
                ivAddToCart.setOnClickListener {
                    with(list[position]) {
                        onClick(ProductItem(name = name, image = image, price = price), true)
                    }
                    ivAddToCart.isVisible = false
                }
                root.setOnClickListener {
                    with(list[position]) {
                        onClick(ProductItem(name = name, image = image, price = price), false)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomePageViewHolder(
        ItemProductListHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        holder.bind(position)
    }
}