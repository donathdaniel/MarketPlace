package com.example.marketplaceapp.ui.myMarket.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplaceapp.R
import com.example.marketplaceapp.interfaces.OnMyMarketItemClickListener
import com.example.marketplaceapp.model.Product
import com.example.marketplaceapp.utils.deleteQuotes

class MyMarketAdapter(
    private var myMarketList: MutableList<Product>,
    private val listener: OnMyMarketItemClickListener
) :
    RecyclerView.Adapter<MyMarketAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View, clickAt: OnMyMarketItemClickListener) :
        RecyclerView.ViewHolder(ItemView) {
        val productImageView: ImageView = itemView.findViewById(R.id.product_image_view)
        val priceTextView: TextView = itemView.findViewById(R.id.price_text_view)
        val ownerNameTextView: TextView = itemView.findViewById(R.id.owner_name_text_view)
        val ownerImageView: ImageView = itemView.findViewById(R.id.owner_image_view)
        val productNameTextView: TextView = itemView.findViewById(R.id.product_name_text_view)

        val activeInactiveImageView: ImageView =
            itemView.findViewById(R.id.active_inactive_image_view)
        val activeInactiveTextView: TextView = itemView.findViewById(R.id.active_inactive_text_view)

        private val deleteImageView: ImageView = itemView.findViewById(R.id.delete_image_view)

        init {
            deleteImageView.setOnClickListener {
                clickAt.onDelete(myMarketList[adapterPosition])
            }

            ownerImageView.setOnClickListener {
                clickAt.onProfile(myMarketList[adapterPosition])
            }

            itemView.setOnClickListener {
                clickAt.onDetails(myMarketList[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_market_recyclerview_element, parent, false), listener
        )
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = myMarketList[position]

        Glide.with(holder.itemView.context)
            .load(R.drawable.palinka)
            .placeholder(R.drawable.palinka)
            .error(R.drawable.palinka)
            .circleCrop()
            .into(holder.productImageView)

        holder.priceTextView.text =
            (itemsViewModel.pricePerUnit + " " + itemsViewModel.priceType + "/" + itemsViewModel.amountType).deleteQuotes()
        holder.ownerNameTextView.text = itemsViewModel.username.deleteQuotes()
        holder.productNameTextView.text = itemsViewModel.title.deleteQuotes()

        Glide.with(holder.itemView.context)
            .load(R.drawable.avatar)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .circleCrop()
            .into(holder.ownerImageView)

        val activeInactiveColor: Int
        val activeInactiveText: String
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

        holder.activeInactiveTextView.setTextColor(
            ContextCompat.getColor(
                holder.activeInactiveImageView.context,
                activeInactiveColor
            )
        )
        holder.activeInactiveTextView.text = activeInactiveText.deleteQuotes()
    }

    override fun getItemCount(): Int = myMarketList.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: MutableList<Product>) {
        this.myMarketList = filterList
        notifyDataSetChanged()
    }


}