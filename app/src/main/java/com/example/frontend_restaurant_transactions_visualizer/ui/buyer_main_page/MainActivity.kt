package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frontend_restaurant_transactions_visualizer.data.api.RTVClient
import com.example.frontend_restaurant_transactions_visualizer.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
    }



    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }
    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val date = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("$day-$month-$year 12:00:00")
        var x = date.time.toString()
        loadData(x)

    }

    fun loadData(date: String) {
        val retroInstance = RTVClient.getClient()
        val call = retroInstance.loadDataLocal(date)
        call.enqueue(object : Callback<String?> {
            override fun onFailure(call: Call<String?>, t: Throwable) {

            }

            override fun onResponse(call: Call<String?>, response: Response<String?>) {

            }
        })
    }
}
