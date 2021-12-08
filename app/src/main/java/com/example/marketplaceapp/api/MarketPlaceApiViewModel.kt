package com.example.marketplaceapp.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.model.*
import kotlinx.coroutines.launch

class MarketPlaceApiViewModel(private val repository: MarketPlaceApiRepository) : ViewModel() {

    var registerResponse: MutableLiveData<Response<RegistrationResponse>> = MutableLiveData()
    var loginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    var activationResponse: MutableLiveData<Response<String>> = MutableLiveData()
    var getProductResponse: MutableLiveData<Response<ProductCredential>> = MutableLiveData()

    fun registration(registrationCredential: RegistrationCredential){
        viewModelScope.launch {
            val response = repository.register(registrationCredential)
            registerResponse.value = response
        }
    }

    fun activation(username : String){
        viewModelScope.launch {
            val response = repository.activation(username)
            activationResponse.value = response
        }
    }

    fun login(loginCredential: LoginCredential){
        viewModelScope.launch {
            val response = repository.login(loginCredential)
            loginResponse.value = response
        }
    }

    fun getProducts(token: String){
        viewModelScope.launch {
            val response = repository.getProducts(token)
            getProductResponse.value = response
        }
    }
}