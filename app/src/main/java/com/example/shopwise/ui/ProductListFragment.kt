package com.example.shopwise.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopwise.R
import com.example.shopwise.databinding.FragmentProductListBinding
import com.example.shopwise.viewmodel.ShopViewModel

class ProductListFragment : Fragment(R.layout.fragment_product_list) {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()
    private val adapter = ProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductListBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.fabAdd.setOnClickListener { findNavController().navigate(R.id.action_productList_to_addProduct) }

        viewModel.allProducts.asLiveData().observe(viewLifecycleOwner) { products ->
            binding.tvEmpty.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (products.isEmpty()) View.GONE else View.VISIBLE
            adapter.setData(products)
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}