package com.example.sneakersapp.modal

import java.io.Serializable

data class Response(
	val productLists: List<ProductItem>? = null
)

data class ProductItem(
	val price: Float,
	val name: String,
	val image: String
) : Serializable
