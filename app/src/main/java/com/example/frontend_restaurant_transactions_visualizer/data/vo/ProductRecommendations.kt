package com.example.frontend_restaurant_transactions_visualizer.data.vo

import com.google.gson.annotations.SerializedName

data class ProductRecommendations(
    val product: Product,
    val productsRecomendation: List<Product>,
)
