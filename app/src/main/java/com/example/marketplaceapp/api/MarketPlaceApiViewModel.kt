package com.example.marketplaceapp.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.model.*
import kotlinx.coroutines.launch

class MarketPlaceApiViewModel(private val repository: MarketPlaceApiRepository) : ViewModel() {

    var registerResponse: MutableLiveData<Response<RegistrationResponse>> = MutableLiveData()

    //    var activationResponse: MutableLiveData<Response<String>> = MutableLiveData()
//    var loginResponse: MutableLiveData<Response<LoginResponse>> = MutableLiveData()
    val resetPasswordResponse: MutableLiveData<Response<GeneralResponse>> = MutableLiveData()
    val userInfoResponse: MutableLiveData<Response<UserInfoResponse>> = MutableLiveData()


    var getProductResponse: MutableLiveData<Response<ProductResponse>> = MutableLiveData()
    var addProductResponse: MutableLiveData<Response<ProductAddResponse>> = MutableLiveData()

    // User
    fun registration(registrationCredential: RegistrationCredential) {
        viewModelScope.launch {
            val response = repository.register(registrationCredential)
            registerResponse.value = response
        }
    }

//    fun activation(username : String){
//        viewModelScope.launch {
//            val response = repository.activation(username)
//            activationResponse.value = response
//        }
//    }

//    fun login(loginCredential: LoginCredential) {
//        viewModelScope.launch {
//            val response = repository.login(loginCredential)
//            loginResponse.value = response
//        }
//    }

    fun resetPassword(resetPasswordCredential: ResetPasswordCredential) {
        viewModelScope.launch {
            val response = repository.resetPassword(resetPasswordCredential)
            resetPasswordResponse.value = response
        }
    }

    fun getUserInfo(username: String) {
        viewModelScope.launch {
            val response = repository.getUserInfo(username)
            userInfoResponse.value = response
        }
    }


    // Products
    fun getProducts(
        token: String,
        limit: Int? = null,
        filter: String? = null,
        sort: String? = null,
        skip: Int? = null
    ) {
        viewModelScope.launch {
            val response = repository.getProducts(token, limit, filter, sort, skip)
            getProductResponse.value = response
        }
    }

    fun addProducts(token: String, productAdd: ProductAdd) {
        viewModelScope.launch {
            val response = repository.addProducts(token, productAdd)
            addProductResponse.value = response
        }
    }
}