package com.example.shopwise.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1, // We only have one profile, so ID is always 1
    val shopName: String = "My Shop",
    val ownerName: String = "Shop Owner",
    val phoneNumber: String = "",
    val location: String = ""
)