package com.example.marketplaceapp.ui.myMarket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.*
import com.example.marketplaceapp.utils.getToken
import kotlinx.coroutines.launch
import retrofit2.Response

class CreateFareViewModel : ViewModel() {
    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _addProductResponse: MutableLiveData<Response<ProductAddResponse>> =
        MutableLiveData()
    val addProductResponse: LiveData<Response<ProductAddResponse>> = _addProductResponse

    private val _userInfoResponse: MutableLiveData<Response<UserInfoResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<Response<UserInfoResponse>> = _userInfoResponse

    private var _updateProductResponse: MutableLiveData<Response<ProductUpdateResponse>> =
        MutableLiveData()
    val updateProductResponse: LiveData<Response<ProductUpdateResponse>> = _updateProductResponse

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

    fun updateProducts(productId: String, productUpdate: ProductUpdate) {
        val accessToken = BazaarSharedPreference.sharedPref.getToken()
        viewModelScope.launch {
            val response = repository.updateProducts(productId, accessToken, productUpdate)
            _updateProductResponse.value = response
        }
    }
}