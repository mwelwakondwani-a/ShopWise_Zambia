package com.example.shopwise.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.shopwise.R
import com.example.shopwise.databinding.FragmentDashboardBinding
import com.example.shopwise.viewmodel.ShopViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)

        // 1. Observe Profile to show Owner Name and Shop Name
        viewModel.userProfile.asLiveData().observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                binding.tvWelcome.text = "Welcome back, ${profile.ownerName}!"
                binding.tvShopHeader.text = "${profile.shopName} Overview"
            }
        }

        // 2. Observe stats
        viewModel.totalRevenue.asLiveData().observe(viewLifecycleOwner) { rev ->
            binding.tvRevenue.text = "K %.2f".format(rev ?: 0.0)
        }

        viewModel.totalProfit.asLiveData().observe(viewLifecycleOwner) { profit ->
            binding.tvProfit.text = "K %.2f".format(profit ?: 0.0)
        }

        viewModel.totalProducts.asLiveData().observe(viewLifecycleOwner) { count ->
            binding.tvTotalProducts.text = count.toString()
        }

        viewModel.lowStockCount.asLiveData().observe(viewLifecycleOwner) { count ->
            binding.tvLowStock.text = count.toString()
        }

        // 3. Button Clicks
        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_addProduct)
        }
        binding.btnRecordSale.setOnClickListener {
            findNavController().navigate(R.id.recordSaleFragment)
        }
        binding.btnViewInventory.setOnClickListener {
            findNavController().navigate(R.id.productListFragment)
        }
        binding.btnSalesHistory.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }

        // 4. NEW HOME LOGOUT CLICK
        binding.btnHomeLogout.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("ShopWisePrefs", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build()
            findNavController().navigate(R.id.loginFragment, null, navOptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView();
        _binding = null
    }
}