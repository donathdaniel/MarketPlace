package com.example.marketplaceapp.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplaceapp.model.LoginResponse
import com.example.marketplaceapp.model.RegistrationResponse
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.model.LoginCredential
import com.example.marketplaceapp.model.RegistrationCredential
import kotlinx.coroutines.launch

class MarketPlaceApiViewModel(private val repository: MarketPlaceApiRepository) : ViewModel() {

    val registerResponse: MutableLiveData<Response<RegistrationResponse>> = MutableLiveData()
    val loginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    val activationResponse: MutableLiveData<Response<String>> = MutableLiveData()

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
}