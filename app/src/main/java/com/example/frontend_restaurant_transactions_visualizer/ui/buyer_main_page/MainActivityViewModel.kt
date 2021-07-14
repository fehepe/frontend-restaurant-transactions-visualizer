package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val buyerRepository : BuyerPagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  buyerPagedList : LiveData<PagedList<Buyer>> by lazy {
        buyerRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        buyerRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return buyerPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}