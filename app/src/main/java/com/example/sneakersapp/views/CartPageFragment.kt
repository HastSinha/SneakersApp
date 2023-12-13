package com.example.sneakersapp.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersapp.R
import com.example.sneakersapp.adapters.CartPageAdapter
import com.example.sneakersapp.databinding.FragmentCartPageBinding
import com.example.sneakersapp.modal.ProductItem
import com.example.sneakersapp.viewmodel.ProductsViewModel

class CartPageFragment : Fragment() {

    private var _binding: FragmentCartPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        activity?.window?.statusBarColor = ContextCompat.getColor(context, R.color.white)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[ProductsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ivEmptyCart.isVisible = true
            initCartRecyclerView()
            setOtherDetails()
            setBackPressEvents()
        }
    }

    private fun setBackPressEvents() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                handleBackPress()
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
                object : OnBackPressedCallback(enabled = true) {
                    override fun handleOnBackPressed() {
                        handleBackPress()
                    }
                })
            btnCheckout.setOnClickListener {
                viewModel.selectedProducts.observe(viewLifecycleOwner) {
                    it.forEach { item ->
                        viewModel.deleteProduct(item)

                    }
                    ivCheckout.isVisible = true
                }
            }
        }
    }

    private fun initCartRecyclerView() {
        with(binding) {
            viewModel.selectedProducts.observe(viewLifecycleOwner) {
                ivEmptyCart.isVisible = it.isEmpty()
                rvCart.adapter = CartPageAdapter(it, ::getPrice, ::deleteProduct)
                val layoutManager =
                    LinearLayoutManager(binding.rvCart.context, LinearLayoutManager.VERTICAL, false)
                rvCart.layoutManager = layoutManager
            }
        }
    }

    private fun getPrice(price: Float) {
        viewModel.updateAmount(price)
    }

    private fun handleBackPress() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fl_home, HomePageFragment(), "home_page_fragment")?.commit()
    }

    private fun deleteProduct(productItem: ProductItem, position: Int) {
        viewModel.deleteProduct(productItem)
        binding.rvCart.adapter?.notifyItemRemoved(position)
        Toast.makeText(binding.rvCart.context, "Product Deleted From Cart", Toast.LENGTH_SHORT)
            .show()
    }

    private fun setOtherDetails() {
        with(binding) {
            viewModel.subTotal.observe(viewLifecycleOwner) {
                tvSubtotal.text = it.toString()
            }
            viewModel.tax.observe(viewLifecycleOwner) {
                tvTax.text = it.toString()
            }
            viewModel.totalAmount.observe(viewLifecycleOwner) {
                tvTotal.text = it.toString()
            }
            ivInfo.setOnClickListener {
                Toast.makeText(
                    ivInfo.context,
                    getString(R.string.text_verified_product),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}