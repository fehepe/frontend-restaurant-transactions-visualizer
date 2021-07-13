package com.example.frontend_restaurant_transactions_visualizer.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BuyerDataSource (private val apiService : RTVInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<String, Buyer>(){


    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Buyer>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getBuyerList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it, null, null)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("BuyerDataSource", it.message.toString())
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Buyer>) {

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Buyer>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getBuyerList()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {

                            callback.onResult(it, null)
                            networkState.postValue(NetworkState.LOADED)



                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("BuyerDataSource", it.message.toString())
                    }
                )
        )
    }
}