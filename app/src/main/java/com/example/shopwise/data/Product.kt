package com.example.shopwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val buyingPrice: Double,
    val sellingPrice: Double,
    val quantity: Int,
    val lowStockThreshold: Int = 5,
    val expiryDate: Long? = null,
    val isActive: Boolean = true
)