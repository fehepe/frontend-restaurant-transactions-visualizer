package com.example.frontend_restaurant_transactions_visualizer.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import io.reactivex.disposables.CompositeDisposable

class BuyerDataSourceFactory (private val apiService : RTVInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<String, Buyer>() {

    val buyersLiveDataSource =  MutableLiveData<BuyerDataSource>()

    override fun create(): DataSource<String, Buyer> {
        val buyerDataSource = BuyerDataSource(apiService,compositeDisposable)

        buyersLiveDataSource.postValue(buyerDataSource)
        return buyerDataSource
    }
}