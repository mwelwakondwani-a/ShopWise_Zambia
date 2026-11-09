package com.example.shopwise.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopwise.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ShopViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).shopDao()

    val allProducts = dao.getActiveProducts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val allSales = dao.getAllSales().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- DASHBOARD STATS ---
    val totalRevenue = dao.getTotalRevenue().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    val totalProfit = dao.getTotalProfit().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)
    val totalProducts = dao.getTotalProducts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val lowStockCount = dao.getLowStockCount().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // --- USER PROFILE ---
    val userProfile = dao.getUserProfile().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveProfile(shopName: String, ownerName: String, phone: String, location: String) {
        viewModelScope.launch {
            val profile = UserProfile(shopName = shopName, ownerName = ownerName, phoneNumber = phone, location = location)
            dao.saveUserProfile(profile)
        }
    }

    // --- ACTIONS ---
    fun addProduct(name: String, buy: Double, sell: Double, qty: Int) {
        viewModelScope.launch {
            dao.insertProduct(Product(name = name, buyingPrice = buy, sellingPrice = sell, quantity = qty))
        }
    }

    fun recordSale(product: Product, quantitySold: Int, paymentRef: String) {
        viewModelScope.launch {
            val unitPrice = BigDecimal(product.sellingPrice.toString())
            val total = unitPrice.multiply(BigDecimal(quantitySold.toString()))

            val sale = Sale(totalAmount = total, paymentMethod = "Cash", paymentReference = paymentRef)
            val newSaleId = dao.insertSale(sale)

            val saleItem = SaleItem(
                saleId = newSaleId, productId = product.id, productName = product.name,
                quantity = quantitySold, unitPrice = unitPrice, subtotal = total
            )
            dao.insertSaleItem(saleItem)
            dao.updateProduct(product.copy(quantity = product.quantity - quantitySold))
        }
    }

    // --- LOGIN & SIGNUP LOGIC ---
    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = dao.login(username, password)
            onResult(user != null)
        }
    }

    fun signup(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val newUser = UserAccount(username = username, password = password)
                dao.insertUserAccount(newUser)

                // AUTOMATICALLY CREATE A PROFILE FOR THE NEW USER
                val newProfile = UserProfile(shopName = "$username's Shop", ownerName = username)
                dao.saveUserProfile(newProfile)

                onResult(true)
            } catch (e: Exception) {
                onResult(false) // Fails if username already exists
            }
        }
    }
}