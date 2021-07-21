package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import com.example.frontend_restaurant_transactions_visualizer.databinding.ActivityMainBinding
import com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details.SingleBuyerDetails
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(),BuyerListAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerViewAdapter: BuyerListAdapter
    lateinit var viewModel: MainActivityViewModel

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

        initRecyclerView()
        initViewModel()
        viewModel.getBuyerList()



    }
    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getBuyerListObserverable().observe(this, Observer<List<Buyer>> {
            if(it == null) {
                Toast.makeText(this@MainActivity, "no result found...", Toast.LENGTH_LONG).show()
            } else {
                recyclerViewAdapter.buyers = it.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getBuyerList()
    }
    private fun initRecyclerView() {
        binding.rvBuyerList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = BuyerListAdapter(this@MainActivity)
            adapter = recyclerViewAdapter

        }
    }


    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val currentTime = Calendar.getInstance().time

        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val currentHours = simpleDateFormat.format(currentTime)


        var dateString = "$day-$month-$year $currentHours"
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateString)
        var x = date.time.toLong()/1000L

        viewModel.loadData(x.toString())


    }

    override fun onItemEditCLick(buyer: Buyer, img:String) {
        val intent = Intent(this@MainActivity, SingleBuyerDetails::class.java)
        intent.putExtra("id", buyer.id)
        intent.putExtra("img",img)
        startActivityForResult(intent, 1000)
    }


}
