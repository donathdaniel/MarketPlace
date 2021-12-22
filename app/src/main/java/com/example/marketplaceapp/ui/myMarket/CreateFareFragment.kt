package com.example.marketplaceapp.ui.myMarket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.model.ProductAdd
import com.example.marketplaceapp.utils.getToken
import com.example.marketplaceapp.utils.getUsername
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputLayout

class CreateFareFragment : BaseFragment() {

    private val createFareViewModel : CreateFareViewModel by viewModels()

    lateinit var emailTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var phoneNumberTextView: TextView
    lateinit var launchMyFairButton: Button

    lateinit var titleTextView : TextView
    lateinit var descriptionTextView : TextView
    lateinit var priceAmountTextView : TextView
    lateinit var availableAmountTextView : TextView
    lateinit var priceTypeTextView : TextView
    lateinit var amountTypeTextView : TextView

    lateinit var titleTextViewLayout : TextInputLayout
    lateinit var priceAmountTextViewLayout : TextInputLayout
    lateinit var priceTypeTextViewLayout : TextInputLayout
    lateinit var availableAmountTextViewLayout : TextInputLayout
    lateinit var amountTypeTextViewLayout : TextInputLayout
    lateinit var descriptionTextViewLayout : TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_fare, container, false)

        emailTextView = view.findViewById(R.id.email_text_view)
        usernameTextView = view.findViewById(R.id.user_name_text_view)
        phoneNumberTextView = view.findViewById(R.id.phone_number_text_view)
        launchMyFairButton = view.findViewById(R.id.launch_my_fair_button)

        titleTextViewLayout = view.findViewById(R.id.title_text_view_layout)
        priceAmountTextViewLayout = view.findViewById(R.id.price_amount_text_view_layout)
        priceTypeTextViewLayout = view.findViewById(R.id.price_type_text_view_layout)
        availableAmountTextViewLayout = view.findViewById(R.id.available_amount_text_view_layout)
        amountTypeTextViewLayout = view.findViewById(R.id.amount_type_text_view_layout)
        descriptionTextViewLayout = view.findViewById(R.id.description_text_view_layout)

        createFareViewModel.userInfoResponse.observe(
            viewLifecycleOwner,
            { response ->
                if (response.isSuccessful) {
                    Log.d("getUserInfo", response.body().toString())

                    emailTextView.text = response.body()?.data!![0].email
                    usernameTextView.text = response.body()?.data!![0].username
                    phoneNumberTextView.text = response.body()?.data!![0].phoneNumber.toString()

                } else {
                    Log.d("getUserInfo", response.code().toString())
                }
            })

        createFareViewModel.addProductResponse.observe(
            viewLifecycleOwner,
            { response ->
                if(response.isSuccessful){
                    Log.d("addProduct", response.body().toString())

                    (mActivity as MainActivity).replaceFragment(MyMarketFragment(), R.id.fragment_container)
                }
                else{
                    Log.d("addProduct", response.code().toString())
                }
            }
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username: String = BazaarSharedPreference.sharedPref.getUsername()
        Log.d("username", username.toString())

        if (username != "") {
            createFareViewModel.getUserInfo(username)
        }

        launchMyFairButton.setOnClickListener {
            val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
            Log.d("accessToken", "get $accessToken")


            titleTextView = view.findViewById(R.id.title_text_view)
            descriptionTextView = view.findViewById(R.id.description_text_view)
            priceAmountTextView = view.findViewById(R.id.price_amount_text_view)
            availableAmountTextView = view.findViewById(R.id.available_amount_text_view)

            val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggle_button)
            val productActive: Boolean = toggleButton.checkedButtonId == R.id.active_toggle_button

            priceTypeTextView = view.findViewById(R.id.price_type_text_view)
            amountTypeTextView = view.findViewById(R.id.amount_type_text_view)

            if(validateInput()) {
                createFareViewModel.addProducts(
                    accessToken.toString(),
                    ProductAdd(
                        null,
                        titleTextView.text.toString(),
                        descriptionTextView.text.toString(),
                        priceAmountTextView.text.toString(),
                        availableAmountTextView.text.toString(),
                        productActive,
                        4.0,
                        amountTypeTextView.text.toString(),
                        priceTypeTextView.text.toString()
                    )
                )
            }
        }
    }

    private fun validateInput(): Boolean {
        titleTextViewLayout.error = null
        priceAmountTextViewLayout.error = null
        priceTypeTextViewLayout.error = null
        availableAmountTextViewLayout.error = null
        amountTypeTextViewLayout.error = null
        descriptionTextViewLayout.error = null

        when {
            titleTextView.text.toString().isEmpty() -> {
                titleTextViewLayout.error = getString(R.string.error)
                return false
            }
            priceAmountTextView.text.toString().isEmpty() -> {
                priceAmountTextViewLayout.error = getString(R.string.error)
                return false
            }
            priceTypeTextView.text.toString().isEmpty() -> {
                priceTypeTextViewLayout.error = getString(R.string.error)
                return false
            }
            availableAmountTextView.text.toString().isEmpty() -> {
                availableAmountTextViewLayout.error = getString(R.string.error)
                return false
            }
            amountTypeTextView.text.toString().isEmpty() -> {
                amountTypeTextViewLayout.error = getString(R.string.error)
                return false
            }
            descriptionTextView.text.toString().isEmpty() -> {
                descriptionTextViewLayout.error = getString(R.string.error)
                return false
            }
        }
        return true
    }

}