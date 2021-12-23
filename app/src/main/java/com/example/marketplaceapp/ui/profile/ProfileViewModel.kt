package com.example.marketplaceapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.ProfileCredential
import com.example.marketplaceapp.model.ProfileResponse
import com.example.marketplaceapp.model.UserInfoResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel: ViewModel() {

    private val repository : MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _userInfoResponse: MutableLiveData<Response<UserInfoResponse>> = MutableLiveData()
    val userInfoResponse: LiveData<Response<UserInfoResponse>> = _userInfoResponse

    private val _profileResponse: MutableLiveData<Response<ProfileResponse>> = MutableLiveData()
    val profileResponse: LiveData<Response<ProfileResponse>> = _profileResponse

    fun getUserInfo(username: String) {
        viewModelScope.launch {
            val response = repository.getUserInfo(username)
            _userInfoResponse.value = response
        }
    }

    fun updateUserData(token: String, profileCredential: ProfileCredential) {
        viewModelScope.launch {
            val response = repository.updateUserData(token, profileCredential)
            _profileResponse.value = response
        }
    }
}