package com.example.frontend_restaurant_transactions_visualizer.data.api

import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RTVInterface {

    @GET("buyer")
    fun getBuyerList(): Call<List<Buyer>>

    @GET("buyer/{buyerId}")
    fun getBuyerDetails(@Path("buyerId")id: String):  Call<BuyerDetails>

    @POST("load/{date}")
    fun loadDataLocal(@Path("date") date: String) : Call<String>
}