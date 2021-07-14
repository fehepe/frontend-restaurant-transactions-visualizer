package com.example.frontend_restaurant_transactions_visualizer.data.vo

import com.google.gson.annotations.SerializedName

data class BuyerDetails(

    val buyer: List<Buyer>,
    val transactions: List<Transaction>,
    @SerializedName("buyerEqIp")
    val buyersSameIp: List<BuyersSameIp>,
    @SerializedName("recommendations")
    val products: List<Product>
)
