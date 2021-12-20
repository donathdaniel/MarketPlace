package com.example.marketplaceapp.UI.login.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.LoginCredential
import com.example.marketplaceapp.model.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(): ViewModel() {

    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _loginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Response<LoginResponse>> = _loginResponse

    fun login(loginCredential: LoginCredential) {
        viewModelScope.launch {
            val response = repository.login(loginCredential)
            _loginResponse.value = response
        }
    }
}