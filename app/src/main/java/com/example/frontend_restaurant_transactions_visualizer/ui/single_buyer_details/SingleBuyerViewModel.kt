package com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import io.reactivex.disposables.CompositeDisposable

class SingleBuyerViewModel (private val buyerRepository: BuyerDetailsRepository, buyerId: String):
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val buyerDetails : LiveData<BuyerDetails> by lazy{
        buyerRepository.fetchSingleBuyerDetails(compositeDisposable, buyerId)
    }

    val networkState : LiveData<NetworkState> by lazy{
        buyerRepository.getBuyerDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}