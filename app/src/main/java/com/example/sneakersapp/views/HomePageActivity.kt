package com.example.sneakersapp.views

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sneakersapp.R
import com.example.sneakersapp.databinding.ActivityHomePageBinding
import com.example.sneakersapp.modal.ProductItem
import com.example.sneakersapp.modal.Response
import com.example.sneakersapp.viewmodel.ProductsViewModel
import com.google.gson.Gson
import java.io.IOException

class HomePageActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var cartPageFragment : CartPageFragment
    private lateinit var homePageFragment: HomePageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBar()
        cartPageFragment = CartPageFragment()
        homePageFragment = HomePageFragment()
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        initHomePageFragment()
        handleBottomNavigation()
    }

    private fun setStatusBar() {
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window?.statusBarColor = ContextCompat.getColor(this, R.color.home_page_background_color)
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun handleBottomNavigation() {
        binding.bnvHome.setOnItemSelectedListener {
            if (it.itemId == R.id.menu_item_cart) {
                if (!cartPageFragment.isAdded) {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.flHome.id, cartPageFragment, "cart_page_fragment").commit()
                }
                true
            } else {
                if (!homePageFragment.isAdded) {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.flHome.id, homePageFragment, "cart_page_fragment").commit()
                }
                true
            }
        }
    }

    private fun initHomePageFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.flHome.id, homePageFragment, "home_page_fragment")
        fragmentTransaction.commit()
    }

}