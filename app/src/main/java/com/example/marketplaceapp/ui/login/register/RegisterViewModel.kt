package com.example.marketplaceapp.ui.login.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.RegistrationCredential
import com.example.marketplaceapp.model.RegistrationResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private var _registerResponse: MutableLiveData<Response<RegistrationResponse>> =
        MutableLiveData()
    var registerResponse: MutableLiveData<Response<RegistrationResponse>> = _registerResponse

    fun registration(registrationCredential: RegistrationCredential) {
        viewModelScope.launch {
            val response = repository.register(registrationCredential)
            registerResponse.value = response
        }
    }
}