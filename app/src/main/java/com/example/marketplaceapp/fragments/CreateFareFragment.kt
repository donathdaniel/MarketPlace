package com.example.marketplaceapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.model.ProductAdd
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.gson.annotations.SerializedName
import java.io.File

class CreateFareFragment : BaseFragment() {

    lateinit var emailTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var phoneNumberTextView: TextView
    lateinit var launchMyFairButton: Button

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

        (mActivity as MainActivity).marketPlaceApiViewModel.userInfoResponse.observe(
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

        (mActivity as MainActivity).marketPlaceApiViewModel.addProductResponse.observe(
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

        val username: String? =
            (mActivity as MainActivity).sharedPref.getString("username", "asdf1234")
        Log.d("username", username.toString())

        if (username != null && username != "asdf1234") {
            (mActivity as MainActivity).marketPlaceApiViewModel.getUserInfo(username)
        }

        launchMyFairButton.setOnClickListener {
            val accessToken: String? =
                (mActivity as MainActivity).sharedPref.getString("accessToken", "asdf1234")
            Log.d("accessToken", "get " + accessToken.toString())


            val titleTextView = view.findViewById<TextView>(R.id.title_text_view)
            val descriptionTextView = view.findViewById<TextView>(R.id.description_text_view)
            val priceAmountTextView = view.findViewById<TextView>(R.id.price_amount_text_view)
            val availableAmountTextView = view.findViewById<TextView>(R.id.available_amount_text_view)

            val toggleButton = view.findViewById<MaterialButtonToggleGroup>(R.id.toggle_button)
            val productActive: Boolean = toggleButton.checkedButtonId == R.id.active_toggle_button

            (mActivity as MainActivity).marketPlaceApiViewModel.addProducts(
                accessToken.toString(),
                ProductAdd(
                    null,
                    titleTextView.text.toString(),
                    descriptionTextView.text.toString(),
                    priceAmountTextView.text.toString().toInt(),
                    availableAmountTextView.text.toString().toInt(),
                    productActive,
                    4.0,
                    "Kg",
                    "RON"
                )
            )
        }
    }

}


//data class ProductAdd(
//    val uploadImages: File? = null,
//    val title: String,
//    val description: String,
//
//    @SerializedName("price_per_unit")
//    val price_per_unit: Int,
//
//    val unit: Int,
//
//    @SerializedName("is_active")
//    val isActive: Boolean,
//
//    val rating: Double,
//
//    @SerializedName("amount_type")
//    val amountType: String,
//
//    @SerializedName("price_type")
//    val priceType: String,
//)