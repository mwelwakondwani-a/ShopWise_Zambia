package com.example.shopwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey(autoGenerate = true) val saleId: Long = 0L,
    val saleDate: Long = System.currentTimeMillis(),
    val totalAmount: BigDecimal,
    val paymentMethod: String,
    val paymentReference: String
)

@Entity(tableName = "sale_items")
data class SaleItem(
    @PrimaryKey(autoGenerate = true) val saleItemId: Long = 0L,
    val saleId: Long,
    val productId: Long,
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val subtotal: BigDecimal
)