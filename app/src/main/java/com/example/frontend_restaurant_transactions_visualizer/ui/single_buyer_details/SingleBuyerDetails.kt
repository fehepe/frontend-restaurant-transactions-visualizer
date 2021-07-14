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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBuyerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buyerId: String = ""

        val apiService : RTVInterface = RTVClient.getClient()
        buyerDetailsRepository = BuyerDetailsRepository(apiService)

        viewModel = getViewModel(buyerId)

        viewModel.buyerDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this,{
            binding.pbLoading.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.tvError.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: BuyerDetails){

        binding.tvBuyerName.text = it.buyer.name




        val moviePosterURL: String = "https://randomuser.me/api/portraits/med/men/75.jpg"
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivBuyerPhoto)
    }

    private  fun getViewModel(buyerId:String): SingleBuyerViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleBuyerViewModel(buyerDetailsRepository,buyerId) as T
            }
        })[SingleBuyerViewModel::class.java]
    }
}