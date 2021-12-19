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

class MyMarketAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<MyMarketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMarketAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_market_recyclerview_element, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: MyMarketAdapter.ViewHolder, position: Int) {
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

        var activeInactiveColor : Int
        var activeInactiveText : String
        if (itemsViewModel.isActive) {
            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_active_product)
                .placeholder(R.drawable.ic_active_product)
                .error(R.drawable.ic_active_product)
                .circleCrop()
                .into(holder.activeInactiveImageView)

            activeInactiveColor = R.color.colorPrimary
            activeInactiveText = "Active"

        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.ic_inactive_product)
                .placeholder(R.drawable.ic_inactive_product)
                .error(R.drawable.ic_inactive_product)
                .circleCrop()
                .into(holder.activeInactiveImageView)

            activeInactiveColor = R.color.colorHintText
            activeInactiveText = "Inactive"

            holder.priceTextView.setTextColor(R.color.colorHintText)
        }

        holder.activeInactiveTextView.setTextColor(activeInactiveColor)
        holder.activeInactiveTextView.text = activeInactiveText
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.product_image_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
        val ownerNameTextView: TextView = itemView.findViewById(R.id.owner_name_text_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)

        val activeInactiveImageView: ImageView =
            itemView.findViewById(R.id.active_inactive_image_view)
        val activeInactiveTextView: TextView = itemView.findViewById(R.id.active_inactive_text_view)

    }


}