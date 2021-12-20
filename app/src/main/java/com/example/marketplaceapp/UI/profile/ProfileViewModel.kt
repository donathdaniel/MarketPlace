package com.example.marketplaceapp.UI.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.UserInfoResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel: ViewModel() {

    private val repository : MarketPlaceApiRepository = MarketPlaceApiRepository()
    private val _userInfoResponse: MutableLiveData<Response<UserInfoResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<Response<UserInfoResponse>> = _userInfoResponse

    fun getUserInfo(username: String) {
        viewModelScope.launch {
            val response = repository.getUserInfo(username)
            _userInfoResponse.value = response
        }
    }
}