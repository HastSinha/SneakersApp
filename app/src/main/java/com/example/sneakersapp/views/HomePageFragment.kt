package com.example.sneakersapp.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sneakersapp.R
import com.example.sneakersapp.adapters.HomePageAdapter
import com.example.sneakersapp.databinding.FragmentHomePageBinding
import com.example.sneakersapp.modal.ProductItem
import com.example.sneakersapp.viewmodel.ProductsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HomePageFragment : Fragment() {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductsViewModel
    private lateinit var productDetailLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter : HomePageAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        activity?.window?.statusBarColor =
            ContextCompat.getColor(context, R.color.home_page_background_color)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        productDetailLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val productItem = it.data?.getSerializableExtra("productItem") as ProductItem
                    handleOnCLick(productItem, true)
                }
            }
        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearch()
    }

    private fun initSearch() {
        with(binding.svSearch) {
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //No Implementation Required
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        filterProducts(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            viewModel.getItems(requireContext()).observe(viewLifecycleOwner) {
                adapter = HomePageAdapter(it, ::handleOnCLick)
                rvList.adapter = adapter
                val layoutManager = GridLayoutManager(rvList.context, 2)
                rvList.layoutManager = layoutManager
                ivNoProducts.isVisible = it.isEmpty()
            }
        }
    }


    private fun filterProducts(text: String) {
        val list = mutableListOf<ProductItem>()
        viewModel.getItems(requireContext()).observe(viewLifecycleOwner) {
            for (products in it) {
                if (products.name.lowercase().contains(text.lowercase())) {
                    list.add(products)
                }
            }
            if (list.isEmpty()) {
                Toast.makeText(binding.svSearch.context, "No Product Found", Toast.LENGTH_SHORT)
                    .show()
            } else {
                setFilteredProducts(list)
            }
        }
    }

    private fun setFilteredProducts(list: List<ProductItem>) {
        adapter = HomePageAdapter(list, ::handleOnCLick)
        binding.rvList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun handleOnCLick(productItem: ProductItem, isForCart: Boolean) {
        if (isForCart) {
            viewModel.addSelectedProduct(productItem)
            Toast.makeText(activity, "Product Added to Cart", Toast.LENGTH_SHORT)
                .show()
        } else {
            val bundle = Bundle()
            bundle.putSerializable("productData", productItem)
            val intent = Intent(activity, ProductDetailsActivity::class.java)
            intent.putExtras(bundle)
            productDetailLauncher.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}