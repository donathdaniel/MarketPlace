package com.example.marketplaceapp.ui.myFares.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplaceapp.R
import com.example.marketplaceapp.model.Order
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.utils.deleteQuotes
import com.example.marketplaceapp.utils.deleteSlash
import com.example.marketplaceapp.utils.deleteSlash2

class MyFaresAdapter(private var orderList: MutableList<Order>) :
    RecyclerView.Adapter<MyFaresAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.product_image_view)
        val ownerNameTextView: TextView = itemView.findViewById(R.id.owner_name_text_view)
        val ownerImageView: ImageView = itemView.findViewById(R.id.owner_image_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)
        val amountTextView: TextView = itemView.findViewById(R.id.amount_text_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)

        val acceptButton: ImageView = itemView.findViewById(R.id.accept_button)
        val declineButton: ImageView = itemView.findViewById(R.id.decline_button)

        val dateTextView : TextView = itemView.findViewById(R.id.date_text_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_fares_recyclerview_element, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = orderList[position]

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

        holder.ownerNameTextView.text = itemsViewModel.ownerUsername.deleteQuotes()

        Glide.with(holder.itemView.context)
            .load(R.drawable.avatar)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .circleCrop()
            .into(holder.ownerImageView)

        Glide.with(holder.itemView.context)
            .load(R.drawable.ic_accept_button)
            .placeholder(R.drawable.ic_accept_button)
            .error(R.drawable.ic_accept_button)
            .circleCrop()
            .into(holder.acceptButton)

        Glide.with(holder.itemView.context)
            .load(R.drawable.ic_decline_button)
            .placeholder(R.drawable.ic_decline_button)
            .error(R.drawable.ic_decline_button)
            .circleCrop()
            .into(holder.declineButton)

        holder.productNameTextView.text = itemsViewModel.title.deleteQuotes().deleteSlash().deleteSlash2()

        holder.amountTextView.text = "Amount: ${itemsViewModel.units}Kg".deleteQuotes()
        holder.priceTextView.text = "Price: ${itemsViewModel.pricePerUnit}Ron".deleteQuotes()

        holder.dateTextView.text = Constant.getDate(itemsViewModel.creationTime)
    }

    override fun getItemCount(): Int = orderList.size
}