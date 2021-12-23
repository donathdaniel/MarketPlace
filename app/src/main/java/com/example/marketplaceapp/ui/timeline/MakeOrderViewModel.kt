package com.example.marketplaceapp.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.OrderAdd
import com.example.marketplaceapp.model.OrderAddResponse
import com.example.marketplaceapp.utils.getToken
import kotlinx.coroutines.launch
import retrofit2.Response

class MakeOrderViewModel: ViewModel() {

    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _addOrderResponse: MutableLiveData<Response<OrderAddResponse>> = MutableLiveData()
    val addOrderResponse: LiveData<Response<OrderAddResponse>> = _addOrderResponse

    fun addOrder(orderAdd: OrderAdd){
        val accessToken = BazaarSharedPreference.sharedPref.getToken()
        viewModelScope.launch {
            val response = repository.addOrder(accessToken, orderAdd)
            _addOrderResponse.value = response
        }
    }
}