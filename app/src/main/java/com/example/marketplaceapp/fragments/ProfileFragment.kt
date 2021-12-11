package com.example.marketplaceapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.adapters.TimelineAdapter

class ProfileFragment : BaseFragment() {

    lateinit var emailTextView : TextView
    lateinit var usernameTextView : TextView
    lateinit var phoneNumberTextView : TextView
//    lateinit var passwordTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        emailTextView = view.findViewById(R.id.email_text_view)
        usernameTextView = view.findViewById(R.id.username_text_view)
        phoneNumberTextView = view.findViewById(R.id.phone_number_text_view)
//        passwordTextView = view.findViewById(R.id.password_text_view)

        (mActivity as MainActivity).marketPlaceApiViewModel.userInfoResponse.observe(
            viewLifecycleOwner,
            { response ->
                Log.d("getUserInfo", response.code().toString())
                if (response.isSuccessful) {
                    Log.d("getUserInfo", response.body().toString())

                    emailTextView.text = response.body()?.data!![0].email
                    usernameTextView.text = response.body()?.data!![0].username
                    phoneNumberTextView.text = response.body()?.data!![0].phoneNumber.toString()

                }
            })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username: String? =
            (mActivity as MainActivity).sharedPref.getString("username", "asdf1234")
        Log.d("username", username.toString())

        if (username != null && username!= "asdf1234") {
            (mActivity as MainActivity).marketPlaceApiViewModel.getUserInfo(username)
        }
    }
}