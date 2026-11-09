# ShopWise Zambia 🇿🇲

**Course:** BSE 3340 Mobile Application Development
**Institution:** Zambia University College of Technology (ZUCT)

## 📖 Project Overview

### The Problem
Many small shop owners and market traders in Zambia record sales and stock manually in paper notebooks or rely on memory. This method is highly prone to errors, making it difficult to track daily sales, identify low-stock items, notice products nearing expiry, and accurately calculate profit. This results in lost revenue and poor business management.

### The Solution
**ShopWise Zambia** is a simple, offline-first Android application designed to digitize the informal retail sector. It helps shop owners record products, track real-time stock levels, record sales, calculate profit automatically, and keep all their business records securely in one place on their phone.

---

## 👥 Team Members & Roles

| Student Name | Student ID | Primary Role |
| :--- | :--- | :--- |
| **Limpo Mwenya Musonda** | 2420990 | Project Lead & Architecture (Navigation, Core Logic) |
| **Richard Phiri** | 2410401 | UI/UX Designer (XML Layouts, Dashboard, Forms) |
| **Mwelwa Kondwani** | 2410395 | Data & Backend (Room Database, DAOs, TypeConverters) |
| **Mumba Kelvin** | 2300294 | Business Logic (ViewModels, Profit Calculation, Auth) |
| **Mumba Chitonge** | 2420927 | QA & Integration (RecyclerViews, WorkManager, Testing) |

---

## 📱 App Screens & Features

The app contains the following fully navigable screens:
1. **Login / Signup:** A secure local authentication screen allowing users to create accounts and log in. The app remembers the user via `SharedPreferences`.
2. **Dashboard:** A personalized home screen displaying the owner's name, shop name, Total Revenue, Total Profit, Total Products, and Low Stock count. It includes quick action buttons.
3. **Products / Stock:** A scrollable list (RecyclerView) of all active products.
4. **Add Product:** A validated form to input new products (Name, Buying Price, Selling Price, Quantity).
5. **Record Sale:** A form allowing the user to select a product, enter the quantity sold, and record a payment reference. This automatically deducts from stock.
6. **Sales History:** A chronological list of all completed transactions.
7. **User Profile:** Displays shop details. Includes a popup dialog to update the Shop Name, Owner Name, Phone, and Location, plus a Logout function.

---

## 🔬 Research Note (New Concepts Learned)

As required by the project brief, we researched and implemented two concepts not covered in the standard class curriculum:

### 1. Room Persistence Library (Local Database)
*   **What it is:** Room is an abstraction layer over SQLite that provides a structured, offline-first local database.
*   **Why our app needs it:** ShopWise requires 100% offline functionality due to unreliable internet and power outages in Zambia. We used Room to store `Product`, `Sale`, `SaleItem`, and `UserAccount` entities.
*   **How it works in our app:** We used `@Entity` to define our tables, `@Dao` to define SQL queries (like `getTotalProfit()`), and `Flow` to automatically update the UI when data changes. We also utilized `@TypeConverters` to save `BigDecimal` values for accurate financial calculations.
*   **Source:** Official Android Developers Documentation (Room Persistence Library).

### 2. WorkManager & Background Notifications
*   **What it is:** WorkManager is an Android library for scheduling deferrable, guaranteed background tasks.
*   **Why our app needs it:** Shop owners often forget to check their stock. We needed a way to alert them about low inventory without requiring the app to be open.
*   **How it works in our app:** We created a `StockAlertWorker` class. In `MainActivity`, we enqueue a `PeriodicWorkRequestBuilder` that runs every 15 minutes. The worker queries the Room database for any products where the quantity is below the `lowStockThreshold`. If it finds any, it triggers a `NotificationCompat` system alert.
*   **Source:** Official Android Developers Documentation (WorkManager).

---

## 🛠️ How to Build and Run the App

To run this project from a fresh copy:
1. Ensure you have **Android Studio** installed.
2. Clone this repository or download it as a ZIP file.
3. Open Android Studio, select **File > Open**, and select the `ShopWise` folder.
4. Allow Gradle to sync all dependencies (this may take a few minutes).
5. Connect an Android device via USB debugging, or start an Android Emulator (API 24 or higher).
6. Click the **Green Play Button (Run 'app')** in the top toolbar.
7. The app will install and launch on your device.

---
*Developed for BSE 3340 Group Project - November 2026.*
