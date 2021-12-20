package com.example.marketplaceapp.UI.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.LoginResponse
import com.example.marketplaceapp.model.ProductResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class TimelineViewModel : ViewModel() {

    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _getProductResponse: MutableLiveData<Response<ProductResponse>> = MutableLiveData()
    var getProductResponse: LiveData<Response<ProductResponse>> = _getProductResponse

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
}