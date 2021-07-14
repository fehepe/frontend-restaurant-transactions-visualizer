package com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details

import androidx.lifecycle.LiveData
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.repository.BuyerDetailsNetworkDS
import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import io.reactivex.disposables.CompositeDisposable

class BuyerDetailsRepository (private val apiService: RTVInterface) {

    lateinit var buyerDetailsNetworkDS: BuyerDetailsNetworkDS

    fun fetchSingleBuyerDetails(compositeDisposable: CompositeDisposable, buyerId:String): LiveData<BuyerDetails> {
        buyerDetailsNetworkDS = BuyerDetailsNetworkDS(apiService,compositeDisposable)
        buyerDetailsNetworkDS.fetchBuyerDetails(buyerId)

        return buyerDetailsNetworkDS.downloadedBuyerDetails
    }

    fun getBuyerDetailsNetworkState(): LiveData<NetworkState> {
        return buyerDetailsNetworkDS.networkState
    }
}