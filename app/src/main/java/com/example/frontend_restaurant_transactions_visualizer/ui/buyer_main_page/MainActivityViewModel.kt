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

    lateinit var listBuyer: MutableLiveData<List<Buyer>>

    init {
        recyclerListData = MutableLiveData()
        listBuyer = MutableLiveData()

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
                    val randGender = (0..1).random()
                    if (response.body() != null){
                        for (buyer in response.body()!!){
                            if (randGender == 0){
                                buyer.url = "https://randomuser.me/api/portraits/men/${(0..99).random()}.jpg"
                            }else {
                                buyer.url =
                                    "https://randomuser.me/api/portraits/women/${(0..99).random()}.jpg"
                            }
                        }
                    }

                    recyclerListData.postValue(response.body())
                    listBuyer.postValue(response.body())


                } else {
                    recyclerListData.postValue(null)
                }
            }
        })



    }

    fun filterBuyer(buyerId : String?){

        var filteredBuyers: List<Buyer> = listOf<Buyer>()

        if (!recyclerListData.value.isNullOrEmpty() and !buyerId.isNullOrEmpty()){

            for (buyer in listBuyer.value!!){
                if (buyer.id.lowercase().startsWith(buyerId!!.lowercase()) or buyer.name.lowercase().startsWith(buyerId!!.lowercase())){
                    filteredBuyers += buyer
                }
            }

            recyclerListData.postValue(filteredBuyers)
        }else{
            recyclerListData.postValue(listBuyer.value)
        }

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