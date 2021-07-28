package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.frontend_restaurant_transactions_visualizer.R
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details.SingleBuyerDetails


class BuyerListAdapter(val clickListener: OnItemClickListener) : RecyclerView.Adapter<BuyerListAdapter.ViewHolder>(){

    var buyers  =  mutableListOf<Buyer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyerListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.buyer_list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var context = viewHolder.itemView.context




        viewHolder.buyerName.text = buyers[position].name
        viewHolder.buyerAge.text =buyers[position].age.toString() + " years old"

        Glide.with(context)
            .load(buyers[position].url)
            .into(viewHolder.buyerImg);

        viewHolder.itemView.setOnClickListener{
            clickListener.onItemEditCLick(buyers[position],buyers[position].url)

        }

}

    override fun getItemCount(): Int {
        return buyers.size
    }



    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var buyerImg : ImageView
        var buyerName : TextView
        var buyerAge: TextView

        init {
            buyerImg = itemView.findViewById(R.id.buyerImg)
            buyerName = itemView.findViewById(R.id.buyerName)
            buyerAge = itemView.findViewById(R.id.buyerAge)
        }

    }

    interface OnItemClickListener {
        fun onItemEditCLick(buyer: Buyer, img : String)
    }
}