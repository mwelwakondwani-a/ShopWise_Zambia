package com.example.shopwise.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.shopwise.R
import com.example.shopwise.data.Product
import com.example.shopwise.databinding.FragmentRecordSaleBinding
import com.example.shopwise.viewmodel.ShopViewModel

class RecordSaleFragment : Fragment(R.layout.fragment_record_sale) {
    private var _binding: FragmentRecordSaleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()
    private var currentProducts = listOf<Product>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecordSaleBinding.bind(view)

        viewModel.allProducts.asLiveData().observe(viewLifecycleOwner) { products ->
            currentProducts = products
            val names = products.map { "${it.name} (Stock: ${it.quantity})" }
            binding.spinnerProducts.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, names)
        }

        binding.btnSell.setOnClickListener {
            val pos = binding.spinnerProducts.selectedItemPosition
            // FIX 1: Added ?. before toString() and fallback to empty string
            val qty = binding.etSaleQty.text?.toString()?.toIntOrNull()
            val paymentRef = binding.etPaymentRef.text?.toString() ?: ""

            if (pos < 0 || pos >= currentProducts.size || qty == null || qty <= 0) {
                Toast.makeText(context, "Invalid sale details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = currentProducts[pos]
            if (qty > product.quantity) {
                Toast.makeText(context, "Not enough stock!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.recordSale(product, qty, paymentRef)
            Toast.makeText(context, "Sale Recorded", Toast.LENGTH_SHORT).show()

            // FIX 2: Added ?. before clear()
            binding.etSaleQty.text?.clear()
            binding.etPaymentRef.text?.clear()
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}