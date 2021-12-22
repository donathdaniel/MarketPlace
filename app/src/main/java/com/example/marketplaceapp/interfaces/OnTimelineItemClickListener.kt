package com.example.marketplaceapp.interfaces

interface OnTimelineItemClickListener {

    fun orderNow(position: Int)
}

interface OnMyMarketItemClickListener {

    fun onDelete(position: Int)

    fun onDetails(position: Int)
}