package com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVClient
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleBuyerDetailsViewModel: ViewModel() {

    lateinit var loadBuyerDetails: MutableLiveData<BuyerDetails>

    init {
        loadBuyerDetails = MutableLiveData()

    }


    fun getBuyerDetailsObserverable() : MutableLiveData<BuyerDetails> {
        return loadBuyerDetails
    }

    fun getBuyerDetails(buyerId : String){

        val retroInstance = RTVClient.getClient()
        val call = retroInstance.getBuyerDetails(buyerId)
        call.enqueue(object : Callback<BuyerDetails> {
            override fun onFailure(call: Call<BuyerDetails>, t: Throwable) {
                loadBuyerDetails.postValue(null)
            }

            override fun onResponse(call: Call<BuyerDetails>, response: Response<BuyerDetails>) {
                if(response.isSuccessful) {

                    loadBuyerDetails.postValue(response.body())
                } else {
                    loadBuyerDetails.postValue(null)
                }
            }
        })


    }
}