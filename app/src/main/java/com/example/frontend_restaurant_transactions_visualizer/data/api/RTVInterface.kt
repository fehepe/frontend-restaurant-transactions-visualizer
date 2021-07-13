package com.example.frontend_restaurant_transactions_visualizer.data.api

import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RTVInterface {

    @GET("buyer")
    fun getBuyerList(): Single<List<Buyer>>

    @GET("buyer/{buyerId}")
    fun getBuyerDetails(@Path("buyerId")id: Int): Single<BuyerDetails>

    @GET("buyer/{date}")
    fun getBuyerListByDate(@Path("buyerId")id: String): Single<List<Buyer>>

    @POST("load")
    fun loadDataLocal(@Query("date") date: String): Single<String>
}