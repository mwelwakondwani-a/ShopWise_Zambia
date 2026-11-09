package com.example.shopwise.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {
    @Query("SELECT * FROM products WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveProducts(): Flow<List<Product>>

    @Insert suspend fun insertProduct(product: Product)
    @Update suspend fun updateProduct(product: Product)

    @Insert suspend fun insertSale(sale: Sale): Long
    @Insert suspend fun insertSaleItem(saleItem: SaleItem)

    @Query("SELECT * FROM sales ORDER BY saleDate DESC")
    fun getAllSales(): Flow<List<Sale>>

    @Query("SELECT SUM(totalAmount) FROM sales")
    fun getTotalRevenue(): Flow<Double?>

    @Query("SELECT COUNT(*) FROM products WHERE isActive = 1")
    fun getTotalProducts(): Flow<Int>

    @Query("SELECT COUNT(*) FROM products WHERE quantity <= lowStockThreshold AND isActive = 1")
    fun getLowStockCount(): Flow<Int>

    // --- ADDED PROFIT QUERY HERE ---
    @Query("SELECT SUM((CAST(si.unitPrice AS REAL) - p.buyingPrice) * si.quantity) FROM sale_items si INNER JOIN products p ON si.productId = p.id")
    fun getTotalProfit(): Flow<Double?>

    // --- USER PROFILE QUERIES ---
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfile)

    // --- AUTHENTICATION QUERIES ---
    @Insert
    suspend fun insertUserAccount(user: UserAccount): Long

    @Query("SELECT * FROM user_accounts WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): UserAccount?
}