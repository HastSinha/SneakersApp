package com.example.sneakersapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sneakersapp.databinding.ItemCartProductListBinding
import com.example.sneakersapp.modal.ProductItem

class CartPageAdapter(
    val products: List<ProductItem>,
    val totalPrice: (price: Float) -> Unit,
    val deleteProduct: (productItem: ProductItem, position: Int) -> Unit
) : RecyclerView.Adapter<CartPageAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartProductListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            with(binding) {
                val price = products[position].price
                tvProductName.text = products[position].name
                tvProductPrice.text = price.toString()
                Glide.with(ivProduct).load(products[position].image).into(ivProduct)
                totalPrice(price)
                ivCross.setOnClickListener {
                    deleteProduct(products[position],position)
                }

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CartViewHolder(
        ItemCartProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }
}
