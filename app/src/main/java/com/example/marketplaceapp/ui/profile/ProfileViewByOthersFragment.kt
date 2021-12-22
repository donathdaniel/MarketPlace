package com.example.marketplaceapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.ui.login.signIn.LoginFragment
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.utils.getUsername
import com.google.android.material.button.MaterialButton


class ProfileViewByOthersFragment : BaseFragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    lateinit var emailTextView: TextView
    lateinit var mainUsernameTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var phoneNumberTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_view_by_others, container, false)

        var username: String = BazaarSharedPreference.sharedPref.getUsername()
        if(username == arguments?.getString(Constant.username).toString()){
            (mActivity as MainActivity).replaceFragment(
                ProfileFragment(),
                R.id.fragment_container
            )
        }

        mainUsernameTextView = view.findViewById(R.id.username_text_view)

        emailTextView = view.findViewById(R.id.email_text_view)
        usernameTextView = view.findViewById(R.id.user_name_text_view)
        phoneNumberTextView = view.findViewById(R.id.phone_number_text_view)

        (mActivity as MainActivity).searchIcon.isVisible = false
        (mActivity as MainActivity).filterIcon.isVisible = false

        profileViewModel.userInfoResponse.observe(
            viewLifecycleOwner,
            { response ->
                if (response.isSuccessful) {
                    Log.d("getUserInfo", response.body().toString())

                    mainUsernameTextView.text = response.body()?.data!![0].username
                    emailTextView.text = response.body()?.data!![0].email
                    usernameTextView.text = response.body()?.data!![0].username
                    phoneNumberTextView.text = response.body()?.data!![0].phoneNumber.toString()

                } else {
                    Log.d("getUserInfo", response.code().toString())
                }
            })

        username = arguments?.getString(Constant.username).toString()
        Log.d("username", username)

        if (username != "") {
            profileViewModel.getUserInfo(username)
        }

        return view
    }

}