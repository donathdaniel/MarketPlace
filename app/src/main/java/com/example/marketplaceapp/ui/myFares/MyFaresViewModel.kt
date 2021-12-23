package com.example.marketplaceapp.ui.myFares

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.OrdersResponse
import com.example.marketplaceapp.utils.deleteQuotes
import com.example.marketplaceapp.utils.getToken
import com.example.marketplaceapp.utils.getUsername
import kotlinx.coroutines.launch
import retrofit2.Response

class MyFaresViewModel : ViewModel() {
    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()

    private var _getSalesResponse: MutableLiveData<Response<OrdersResponse>> = MutableLiveData()
    val getSalesResponse: LiveData<Response<OrdersResponse>> = _getSalesResponse

    private var _getOrderResponse: MutableLiveData<Response<OrdersResponse>> = MutableLiveData()
    val getOrderResponse: LiveData<Response<OrdersResponse>> = _getOrderResponse

    fun getSales() {
        val headers = HashMap<String, String>()
        headers["token"] = BazaarSharedPreference.sharedPref.getToken()
        val username = BazaarSharedPreference.sharedPref.getUsername()
        headers["filter"] = "{\"owner_username\": \"$username\"}"
        viewModelScope.launch {
            val result = repository.getOrders(headers)
            _getSalesResponse.value = result
        }
    }

    fun getOrders() {
        val headers = HashMap<String, String>()
        headers["token"] = BazaarSharedPreference.sharedPref.getToken()
        val username = BazaarSharedPreference.sharedPref.getUsername()
        headers["filter"] = "{\"username\": \"$username\"}"
        viewModelScope.launch {
            val result = repository.getOrders(headers)
            _getOrderResponse.value = result
        }
    }
}