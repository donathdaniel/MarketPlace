package com.example.marketplaceapp.interfaces

import com.example.marketplaceapp.model.Product

interface OnTimelineItemClickListener {

    fun orderNow(product: Product)

    fun onProfile(product: Product)

    fun onDetails(product: Product)
}

//interface onProfileClickListener{
//    fun onProfile(position: Int)
//}

interface OnMyMarketItemClickListener {

    fun onDelete(position: Int)

    fun onDetails(position: Int)
}