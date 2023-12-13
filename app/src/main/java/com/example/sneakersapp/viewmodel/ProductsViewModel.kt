package com.example.sneakersapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sneakersapp.modal.ProductItem
import com.example.sneakersapp.repository.ProductRepository

class ProductsViewModel : ViewModel() {

    private val _selectedProducts = MutableLiveData<List<ProductItem>>()
    val selectedProducts: LiveData<List<ProductItem>> get() = _selectedProducts
    private val _subTotal = MutableLiveData<Float>()
    val subTotal: LiveData<Float> get() = _subTotal
    private val _tax = MutableLiveData<Float>()
    val tax: LiveData<Float> get() = _tax
    private val _totalAmount = MutableLiveData<Float>()
    val totalAmount: LiveData<Float> get() = _totalAmount

    fun addSelectedProduct(productsItem: ProductItem) {
        val currentList = _selectedProducts.value ?: emptyList()
        val updatedList = currentList.toMutableList().apply {
            add(productsItem)
        }
        _selectedProducts.value = updatedList
    }

    fun updateAmount(amount: Float) {
        val currentAmount = _subTotal.value ?: 0f
        val updatedAmount = currentAmount + amount
        _subTotal.value = updatedAmount
        _tax.value = (_subTotal.value ?: 0f) / 3f
        _totalAmount.value = _subTotal.value?.plus(_tax.value ?: 0f)
    }

    fun deleteProduct(productsItem: ProductItem) {
        val currentList = _selectedProducts.value ?: emptyList()
        val updatedList = currentList.toMutableList().apply {
            remove(productsItem)
        }
        _selectedProducts.value = updatedList
        val currentAmount = _subTotal.value ?: 0f
        val updatedAmount = currentAmount - productsItem.price
        _subTotal.value = updatedAmount
        _tax.value = (_subTotal.value ?: 0f) / 3f
        _totalAmount.value = _subTotal.value?.plus(_tax.value ?: 0f)
    }

    fun getItems(context: Context) = ProductRepository(context).getProductList()
}