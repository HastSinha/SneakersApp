package com.example.sneakersapp.views

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.sneakersapp.R
import com.example.sneakersapp.databinding.ActivityProductDetailsBinding
import com.example.sneakersapp.modal.ProductItem
import com.example.sneakersapp.viewmodel.ProductsViewModel

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var viewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBar()
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        val bundle = intent.extras
        val data = bundle?.getSerializable("productData") as ProductItem
        with(binding) {
            Glide.with(ivProduct).load(data.image).into(ivProduct)
            tvName.text = data.name
            tvPrice.text = data.price.toString()
            btnAddToCart.setOnClickListener {
                setUpdatedResult(data)
                Toast.makeText(btnAddToCart.context, "Product Added to Cart", Toast.LENGTH_SHORT)
                    .show()
                btnAddToCart.isEnabled = false
            }
            handleToasts()
            fabBack.setOnClickListener {
                onBackPressed()
            }
            ivInfo.setListenerForToast(getString(R.string.text_verified_product))
        }
    }

    private fun setStatusBar() {
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window?.statusBarColor = ContextCompat.getColor(this, R.color.home_page_background_color)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun handleToasts() {
        with(binding) {
            tvEight.setListenerForToast(getString(R.string.text_size_selected, 8))
            tvNine.setListenerForToast(getString(R.string.text_size_selected, 9))
            tvSeven.setListenerForToast(getString(R.string.text_size_selected, 7))
            ivBlue.setListenerForToast(getString(R.string.text_color_selected,"Blue"))
            ivPink.setListenerForToast(getString(R.string.text_color_selected,"Pink"))
            ivRed.setListenerForToast(getString(R.string.text_color_selected,"Red"))
        }
    }

    private fun View.setListenerForToast(text: String) {
        this.setOnClickListener {
            Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpdatedResult(productItem: ProductItem) {
        val intent = Intent().apply {
            putExtra("productItem", productItem)
        }
        setResult(Activity.RESULT_OK, intent)
    }
}