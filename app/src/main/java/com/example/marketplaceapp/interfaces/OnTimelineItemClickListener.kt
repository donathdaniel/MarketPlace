package com.example.marketplaceapp.interfaces

import com.example.marketplaceapp.model.Product

interface OnTimelineItemClickListener {

    fun orderNow(product: Product)

    fun onProfile(product: Product)

    fun onDetails(product: Product)
}

interface OnMyMarketItemClickListener {

    fun onDelete(product: Product)

    fun onProfile(product: Product)

    fun onDetails(product: Product)

    fun onAcivateOrInactivate(product: Product)
}