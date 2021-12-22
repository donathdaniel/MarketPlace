package com.example.marketplaceapp.ui.login.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.model.GeneralResponse
import com.example.marketplaceapp.model.ResetPasswordCredential
import kotlinx.coroutines.launch
import retrofit2.Response

class ForgotPasswordViewModel : ViewModel() {

    private val repository: MarketPlaceApiRepository = MarketPlaceApiRepository()
    private val _resetPasswordResponse: MutableLiveData<Response<GeneralResponse>> = MutableLiveData()
    val resetPasswordResponse: LiveData<Response<GeneralResponse>> = _resetPasswordResponse

    fun resetPassword(resetPasswordCredential: ResetPasswordCredential) {
        viewModelScope.launch {
            val response = repository.resetPassword(resetPasswordCredential)
            _resetPasswordResponse.value = response
        }
    }
}