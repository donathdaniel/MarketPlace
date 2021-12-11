package com.example.marketplaceapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplaceapp.R
import com.example.marketplaceapp.model.Product

class TimelineAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<TimelineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_recyclerview_element, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TimelineAdapter.ViewHolder, position: Int) {
        val itemsViewModel = productList[position]

        Glide.with(holder.itemView.context)
            .load(R.drawable.palinka)
            .placeholder(R.drawable.palinka)
            .error(R.drawable.palinka)
            .circleCrop()
            .into(holder.productImageView)

        holder.priceTextView.text =
            itemsViewModel.pricePerUnit + " " + itemsViewModel.priceType + "/" + itemsViewModel.amountType
        holder.ownerNameTextView.text = itemsViewModel.username
        holder.productNameTextView.text = itemsViewModel.title
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.product_image_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
        val ownerNameTextView: TextView = itemView.findViewById(R.id.owner_name_text_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)

    }

}