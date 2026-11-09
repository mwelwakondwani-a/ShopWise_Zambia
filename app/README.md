## Backend Architecture

For the ShopWise Mobile App, we implemented a **Local-First Backend Architecture** using Android's Room Database.

Because small shop owners in Zambia often face unreliable internet and power outages, a cloud-based backend was not viable. Instead, the app functions entirely offline, ensuring 100% uptime for the user.

### The Backend Stack:
1. **The Database (Room/SQLite):** Acts as the persistent data store. It holds the `Product`, `Sale`, and `UserProfile` entities.
2. **The API (DAO - Data Access Object):** We use Room DAOs to handle all database operations (Insert, Update, Query). For example, the `getLowStockProducts()` query acts as a backend API call to retrieve specific data for the UI.
3. **The Business Logic (ViewModel):** The `ShopViewModel` handles the backend logic, such as calculating total profit, recording sales transactions, and managing user authentication.
4. **The Worker (WorkManager):** Our background service queries the database asynchronously and triggers local push notifications if stock is low.

### How to Test the Backend (Deployment Guide)
To verify the backend is working:
1. Add a product in the app.
2. In Android Studio, open the **App Inspection** tab -> **Database Inspector**.
3. Open the `shopwise_database` and expand the `products` table. You will see the exact data you entered in the app, proving the backend persistence is working.


1. Richard Phiri 2410401
2. Mwelwa Kondwani 2410395
3. Mumba Kelvin 2300294
4. Mumba Chitonge 2420927
5. Limpo Mwenya Musonda 2420990