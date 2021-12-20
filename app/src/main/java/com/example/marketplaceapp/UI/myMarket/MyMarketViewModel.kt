package com.example.marketplaceapp.UI.myMarket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.ProductAdd
import com.example.marketplaceapp.model.ProductAddResponse
import com.example.marketplaceapp.model.ProductResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MyMarketViewModel: ViewModel() {
    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _getProductResponse: MutableLiveData<Response<ProductResponse>> = MutableLiveData()
    var getProductResponse: LiveData<Response<ProductResponse>> = _getProductResponse

    private var _addProductResponse: MutableLiveData<Response<ProductAddResponse>> = MutableLiveData()
    var addProductResponse: LiveData<Response<ProductAddResponse>> = _addProductResponse

    fun getProducts(
        token: String,
        limit: Int? = null,
        filter: String? = null,
        sort: String? = null,
        skip: Int? = null
    ) {
        viewModelScope.launch {
            val response = repository.getProducts(token, limit, filter, sort, skip)
            _getProductResponse.value = response
        }
    }

    fun addProducts(token: String, productAdd: ProductAdd) {
        viewModelScope.launch {
            val response = repository.addProducts(token, productAdd)
            _addProductResponse.value = response
        }
    }
}