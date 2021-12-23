package com.example.marketplaceapp.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.ProductResponse
import com.example.marketplaceapp.utils.getToken
import kotlinx.coroutines.launch
import retrofit2.Response

class TimelineViewModel : ViewModel() {

    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _getProductResponse: MutableLiveData<Response<ProductResponse>> = MutableLiveData()
    var getProductResponse: LiveData<Response<ProductResponse>> = _getProductResponse

    fun getProducts(
        limit: Int? = null,
        filter: String? = null,
        sort: String? = "{\"creation_time\" : -1}",
        skip: Int? = null
    ) {
        val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
        viewModelScope.launch {
            val response = repository.getProducts(accessToken, limit, filter, sort, skip)
            _getProductResponse.value = response
        }
    }

    fun getProducts2(limit: Int = 20) {
        val headers = HashMap<String, String>()
        headers["token"] = BazaarSharedPreference.sharedPref.getToken()
        headers["sort"] = "{\"creation_time\": -1}"
        headers["limit"] = limit.toString()

        viewModelScope.launch {
            val response = repository.getProducts2(headers)
            _getProductResponse.value = response
        }
    }
}