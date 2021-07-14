package com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVClient
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.data.vo.BuyerDetails
import com.example.frontend_restaurant_transactions_visualizer.databinding.ActivitySingleBuyerDetailsBinding

import java.util.*

class SingleBuyerDetails : AppCompatActivity() {

    private lateinit var viewModel: SingleBuyerViewModel
    private lateinit var buyerDetailsRepository: BuyerDetailsRepository
    private lateinit var binding: ActivitySingleBuyerDetailsBinding
    val header: MutableList<String> = ArrayList()
    val body: MutableList<MutableList<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBuyerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var buyerId:String? = intent.getStringExtra("id")
        if (buyerId == null){
            buyerId = "8363f8a9"
        }
        var imgUrl:String? = intent.getStringExtra("img")
        if (imgUrl == null){
            imgUrl = "https://randomuser.me/api/portraits/women/49.jpg"
        }

        val apiService : RTVInterface = RTVClient.getClient()
        buyerDetailsRepository = BuyerDetailsRepository(apiService)

        viewModel = getViewModel(buyerId)

        viewModel.buyerDetails.observe(this, Observer {
            bindUI(it,imgUrl)
        })

        viewModel.networkState.observe(this,{
            binding.pbLoading.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.tvError.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: BuyerDetails, imgUrl : String){

        binding.tvBuyerName.text = it.buyer[0].name

        val moviePosterURL: String = imgUrl
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivBuyerPhoto)

        header.add("Transactions")
        header.add("Buyers With Same IP")
        header.add("Recommendations")

        val transactions: MutableList<String> = ArrayList()
        val recommendations: MutableList<String> = ArrayList()
        val buyersSameIp: MutableList<String> = ArrayList()
        for (trnx in it.transactions){
            var totalPrice : Double = 0.0
            for (prod in trnx.products){
                totalPrice += prod.price
            }
            transactions.add("Transaction #:${trnx.id} Order Total: ${totalPrice/100} $ USD")
            var quantityProd = 0
            for (prodRecomend in trnx.recommendations){
                for (prodName in prodRecomend.productsRecomendation ){
                    recommendations.add( "For the Product: ${prodRecomend.product[0].name} we recommend to buy: ${prodName.name} ")
                }

            }

        }

        for (buyer in it.buyersSameIp){
            buyersSameIp.add("Name: ${buyer.buyer.name} Ip: ${buyer.ip} Device: ${buyer.device} ")
        }





        body.add(transactions)
        body.add(buyersSameIp)
        body.add(recommendations)

        binding.expandableListView.setAdapter(ExpandableListAdapter(this,binding.expandableListView, header, body))
    }

    private  fun getViewModel(buyerId:String): SingleBuyerViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleBuyerViewModel(buyerDetailsRepository,buyerId) as T
            }
        })[SingleBuyerViewModel::class.java]
    }
}