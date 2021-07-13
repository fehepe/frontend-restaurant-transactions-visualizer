package com.example.frontend_restaurant_transactions_visualizer.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class BuyerDetailsNetworkDS(private val apiService : RTVInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedBuyerDetailsResponse = MutableLiveData<BuyerDetails>()
    val downloadedBuyerDetails: LiveData<BuyerDetails>
        get() = _downloadedBuyerDetailsResponse

    fun fetchBuyerDetails(buyerId: String){
        _networkState.postValue(NetworkState.LOADING)

        try {

            compositeDisposable.add(
                apiService.getBuyerDetails(buyerId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedBuyerDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("BuyerDetailsDS",it.message.toString())
                        }
                    )
            )

        }catch (e: Exception){
            Log.e("BuyerDetailsDS",e.message.toString())
        }
    }
}