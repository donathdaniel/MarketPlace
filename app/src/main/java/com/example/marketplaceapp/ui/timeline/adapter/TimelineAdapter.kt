package com.example.marketplaceapp.ui.timeline.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplaceapp.R
import com.example.marketplaceapp.interfaces.OnTimelineItemClickListener
import com.example.marketplaceapp.model.Product
import com.example.marketplaceapp.utils.deleteQuotes
import com.google.android.material.button.MaterialButton

class TimelineAdapter(private var productList: MutableList<Product>, private val listener : OnTimelineItemClickListener) :
    RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View, clickAtOrderNow: OnTimelineItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.product_image_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
        val ownerNameTextView: TextView = itemView.findViewById(R.id.owner_name_text_view)
        val ownerImageView: ImageView = itemView.findViewById(R.id.owner_image_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)

        private val orderNowButton: MaterialButton = itemView.findViewById(R.id.order_now_button)

        init{
            orderNowButton.setOnClickListener{
                clickAtOrderNow.orderNow(productList[adapterPosition])
            }

            ownerImageView.setOnClickListener {
                clickAtOrderNow.onProfile(productList[adapterPosition])
            }

            ItemView.setOnClickListener {
                clickAtOrderNow.onDetails(productList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_recyclerview_element, parent, false), listener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = productList[position]

        if(position%2 == 0) {
            Glide.with(holder.itemView.context)
                .load(R.drawable.palinka)
                .placeholder(R.drawable.palinka)
                .error(R.drawable.palinka)
                .circleCrop()
                .into(holder.productImageView)
        }
        else{
            Glide.with(holder.itemView.context)
                .load(R.drawable.palinka2)
                .placeholder(R.drawable.palinka2)
                .error(R.drawable.palinka2)
                .circleCrop()
                .into(holder.productImageView)
        }

        holder.priceTextView.text =
            (itemsViewModel.pricePerUnit + " " + itemsViewModel.priceType + "/" + itemsViewModel.amountType).deleteQuotes()
        holder.ownerNameTextView.text = itemsViewModel.username.deleteQuotes()

        Glide.with(holder.itemView.context)
            .load(R.drawable.avatar)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .circleCrop()
            .into(holder.ownerImageView)

        holder.productNameTextView.text = itemsViewModel.title.deleteQuotes()

    }

    override fun getItemCount(): Int = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: MutableList<Product>) {
        this.productList = filterList
        notifyDataSetChanged()
    }

}