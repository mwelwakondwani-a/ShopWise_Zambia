package com.example.shopwise.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopwise.R
import com.example.shopwise.databinding.FragmentAddProductBinding
import com.example.shopwise.viewmodel.ShopViewModel

class AddProductFragment : Fragment(R.layout.fragment_add_product) {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddProductBinding.bind(view)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val buy = binding.etBuyPrice.text.toString().toDoubleOrNull()
            val sell = binding.etSellPrice.text.toString().toDoubleOrNull()
            val qty = binding.etQty.text.toString().toIntOrNull()

            if (name.isEmpty() || buy == null || sell == null || qty == null) {
                Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.addProduct(name, buy, sell, qty)
            Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}