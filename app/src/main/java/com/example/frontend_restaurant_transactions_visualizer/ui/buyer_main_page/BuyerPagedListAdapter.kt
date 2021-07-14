package com.example.frontend_restaurant_transactions_visualizer.ui.buyer_main_page

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.Toast
import com.example.frontend_restaurant_transactions_visualizer.R

import com.example.frontend_restaurant_transactions_visualizer.data.repository.NetworkState
import com.example.frontend_restaurant_transactions_visualizer.data.vo.Buyer
import kotlinx.android.synthetic.main.buyer_list_item.view.*
import com.example.frontend_restaurant_transactions_visualizer.ui.single_buyer_details.SingleBuyerDetails
import kotlinx.android.synthetic.main.network_state_item.view.*


class BuyerPagedListAdapter(public val context: Context) : PagedListAdapter<Buyer, RecyclerView.ViewHolder>(BuyerDiffCallback()) {

    val BUYER_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == BUYER_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.buyer_list_item, parent, false)
            return BuyerItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == BUYER_VIEW_TYPE) {
            (holder as BuyerItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            BUYER_VIEW_TYPE
        }
    }




    class BuyerDiffCallback : DiffUtil.ItemCallback<Buyer>() {
        override fun areItemsTheSame(oldItem: Buyer, newItem: Buyer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Buyer, newItem: Buyer): Boolean {
            return oldItem == newItem
        }

    }


    class BuyerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(buyer: Buyer?,context: Context) {

            itemView.cv_tvBuyerName.text = buyer?.name
            itemView.cv_tvBuyerAge.text =  buyer?.age.toString() + " years old"

            val buyerPosterURL = "https://randomuser.me/api/portraits/med/men/75.jpg"
            Glide.with(itemView.context)
                .load(buyerPosterURL)
                .into(itemView.cv_ivBuyerPhoto);

            itemView.setOnClickListener{
                val intent = Intent(context, SingleBuyerDetails::class.java)
                intent.putExtra("id", buyer?.id)
                context.startActivity(intent)
            }

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.pbItem.visibility = View.VISIBLE;
            }
            else  {
                itemView.pbItem.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.tvErrorMsgItem.visibility = View.VISIBLE;
                itemView.tvErrorMsgItem.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.tvErrorMsgItem.visibility = View.VISIBLE;
                itemView.tvErrorMsgItem.text = networkState.msg;
            }
            else {
                itemView.tvErrorMsgItem.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }




}