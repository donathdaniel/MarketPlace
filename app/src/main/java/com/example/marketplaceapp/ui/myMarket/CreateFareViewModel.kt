package com.example.marketplaceapp.ui.myMarket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.ProductAdd
import com.example.marketplaceapp.model.ProductAddResponse
import com.example.marketplaceapp.model.UserInfoResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class CreateFareViewModel: ViewModel() {
    private val repository : MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _addProductResponse: MutableLiveData<Response<ProductAddResponse>> = MutableLiveData()
    var addProductResponse: LiveData<Response<ProductAddResponse>> = _addProductResponse

    private val _userInfoResponse: MutableLiveData<Response<UserInfoResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<Response<UserInfoResponse>> = _userInfoResponse

    fun addProducts(token: String, productAdd: ProductAdd) {
        viewModelScope.launch {
            val response = repository.addProducts(token, productAdd)
            _addProductResponse.value = response
        }
    }

    fun getUserInfo(username: String) {
        viewModelScope.launch {
            val response = repository.getUserInfo(username)
            _userInfoResponse.value = response
        }
    }
}