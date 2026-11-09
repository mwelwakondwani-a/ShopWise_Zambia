package com.example.shopwise.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.shopwise.R
import com.example.shopwise.databinding.FragmentLoginBinding
import com.example.shopwise.viewmodel.ShopViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShopViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            val user = binding.etLoginUsername.text.toString().trim()
            val pass = binding.etLoginPassword.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(user, pass) { success ->
                if (success) {
                    saveLoginState()
                    findNavController().navigate(R.id.action_login_to_dashboard)
                } else {
                    Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnShowSignup.setOnClickListener {
            showSignupDialog()
        }
    }

    private fun showSignupDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_signup, null)
        val etUser = view.findViewById<TextInputEditText>(R.id.etSignupUsername)
        val etPass = view.findViewById<TextInputEditText>(R.id.etSignupPassword)

        AlertDialog.Builder(requireContext())
            .setTitle("Create Account")
            .setView(view)
            .setPositiveButton("Sign Up") { _, _ ->
                val user = etUser.text.toString().trim()
                val pass = etPass.text.toString().trim()

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.signup(user, pass) { success ->
                        if (success) {
                            Toast.makeText(context, "Account created! Please login.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Username already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveLoginState() {
        val prefs = requireContext().getSharedPreferences("ShopWisePrefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("isLoggedIn", true).apply()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}