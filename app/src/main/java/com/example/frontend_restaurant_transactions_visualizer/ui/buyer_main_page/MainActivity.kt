package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.frontend_restaurant_transactions_visualizer.R
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVClient
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVInterface
import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.databinding.ActivityMainBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    lateinit var buyerRepository: BuyerPagedListRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        // set toolbar as support action bar
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            title = "Buyers List of Restaurant"
            elevation = 15F

            // toolbar button click listener
            binding.btnLoad.setOnClickListener {
                showDatePickerDialog()

            }
        }
        val apiService : RTVInterface = RTVClient.getClient()

        buyerRepository = BuyerPagedListRepository(apiService)

        viewModel = getViewModel()

        val buyerAdapter = BuyerPagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = buyerAdapter.getItemViewType(position)
                if (viewType == buyerAdapter.BUYER_VIEW_TYPE) return  1    // BUYER_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        };


        binding.rvBuyerList.layoutManager = gridLayoutManager
        binding.rvBuyerList.setHasFixedSize(true)
        binding.rvBuyerList.adapter = buyerAdapter

        viewModel.buyerPagedList.observe(this, Observer {
            buyerAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.pbBuyers.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtErrorBuyers.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                buyerAdapter.setNetworkState(it)
            }
        })

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("$day-$month-$year 12:00:00")
        var x = date.time.toString()
        Toast.makeText(this,date.time.toString(), Toast.LENGTH_SHORT).show()

    }
     private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(buyerRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
