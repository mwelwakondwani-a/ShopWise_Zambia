package com.example.shopwise.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.shopwise.R
import com.example.shopwise.viewmodel.ShopViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {

    private var _binding: View? = null
    private val viewModel: ShopViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvProfileShopName = view.findViewById<TextView>(R.id.tvProfileShopName)
        val tvProfileOwnerName = view.findViewById<TextView>(R.id.tvProfileOwnerName)
        val tvProfilePhone = view.findViewById<TextView>(R.id.tvProfilePhone)
        val tvProfileLocation = view.findViewById<TextView>(R.id.tvProfileLocation)

        val btnEditProfile = view.findViewById<MaterialButton>(R.id.btnEditProfile)
        val btnLogout = view.findViewById<MaterialButton>(R.id.btnLogout)

        // FIND NEW BACK BUTTON
        val btnBack = view.findViewById<MaterialButton>(R.id.btnBack)

        // Load profile data
        viewModel.userProfile.asLiveData().observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                tvProfileShopName.text = profile.shopName
                tvProfileOwnerName.text = profile.ownerName
                tvProfilePhone.text = if (profile.phoneNumber.isEmpty()) "Not set" else profile.phoneNumber
                tvProfileLocation.text = if (profile.location.isEmpty()) "Not set" else profile.location
            }
        }

        btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        // --- BACK BUTTON LOGIC ---
        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // --- LOGOUT LOGIC ---
        btnLogout.setOnClickListener {
            val prefs = requireContext().getSharedPreferences("ShopWisePrefs", Context.MODE_PRIVATE)
            prefs.edit { clear() }

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, true)
                .build()
            findNavController().navigate(R.id.loginFragment, null, navOptions)
        }
    }

    private fun showEditProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_profile, null)

        val etShop = dialogView.findViewById<TextInputEditText>(R.id.etDialogShopName)
        val etOwner = dialogView.findViewById<TextInputEditText>(R.id.etDialogOwnerName)
        val etPhone = dialogView.findViewById<TextInputEditText>(R.id.etDialogPhone)
        val etLocation = dialogView.findViewById<TextInputEditText>(R.id.etDialogLocation)

        viewModel.userProfile.value?.let {
            etShop.setText(it.shopName)
            etOwner.setText(it.ownerName)
            etPhone.setText(it.phoneNumber)
            etLocation.setText(it.location)
        }

        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Edit Profile")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                viewModel.saveProfile(
                    etShop.text.toString(),
                    etOwner.text.toString(),
                    etPhone.text.toString(),
                    etLocation.text.toString()
                )
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}