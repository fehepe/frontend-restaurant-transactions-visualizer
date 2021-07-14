package com.example.frontend_restaurant_transactions_visualizer.data.vo

import com.google.gson.annotations.SerializedName

data class Transaction(
    val id: String,
    val ip: String,
    val device: String,
    val products: List<Product>,
    val recommendations: List<ProductRecommendations>
)
