package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVClient
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {


    lateinit var recyclerListData: MutableLiveData<List<Buyer>>

    init {
        recyclerListData = MutableLiveData()

    }


    fun getBuyerListObserverable() : MutableLiveData<List<Buyer>> {
        return recyclerListData
    }

    fun getBuyerList(){


        val retroInstance = RTVClient.getClient()
        val call = retroInstance.getBuyerList()
        call.enqueue(object : Callback<List<Buyer>>{
            override fun onFailure(call: Call<List<Buyer>>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<List<Buyer>>, response: Response<List<Buyer>>) {
                if(response.isSuccessful) {



                    recyclerListData.postValue(response.body())


                } else {
                    recyclerListData.postValue(null)
                }
            }
        })



    }


    fun loadData(date: String)  {

        val retroInstance = RTVClient.getClient()
        val call = retroInstance.loadDataLocal(date)
        call.enqueue(object : Callback<String?> {
            override fun onFailure(call: Call<String?>, t: Throwable) {

            }

            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                getBuyerList()

            }
        })

    }
}