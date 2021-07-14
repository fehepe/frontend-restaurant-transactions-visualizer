package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.repository.BuyerDataSource
import com.example.frontend_restaurant_transactions_visualizer.data.repository.BuyerDataSourceFactory
import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import io.reactivex.disposables.CompositeDisposable

class BuyerPagedListRepository (private val apiService : RTVInterface) {

    lateinit var buyerPagedList: LiveData<PagedList<Buyer>>
    lateinit var buyersDataSourceFactory: BuyerDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Buyer>> {
        buyersDataSourceFactory = BuyerDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build()

        buyerPagedList = LivePagedListBuilder(buyersDataSourceFactory, config).build()

        return buyerPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<BuyerDataSource, NetworkState>(
            buyersDataSourceFactory.buyersLiveDataSource, BuyerDataSource::networkState)
    }
}