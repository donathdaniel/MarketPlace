package com.example.marketplaceapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.ui.login.signIn.LoginFragment
import com.example.marketplaceapp.model.ProfileCredential
import com.example.marketplaceapp.utils.*
import com.google.android.material.button.MaterialButton

class ProfileFragment : BaseFragment() {

    private val profileViewModel: ProfileViewModel by viewModels()

    lateinit var emailTextView: TextView
    lateinit var mainUsernameTextView: TextView
    lateinit var usernameTextView: TextView
    lateinit var phoneNumberTextView: TextView
    lateinit var publishButton: MaterialButton
    lateinit var logOutButton: MaterialButton
//    lateinit var passwordTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        mainUsernameTextView = view.findViewById(R.id.username_text_view)

        emailTextView = view.findViewById(R.id.email_text_view)
        usernameTextView = view.findViewById(R.id.user_name_text_view)
        phoneNumberTextView = view.findViewById(R.id.phone_number_text_view)

        publishButton = view.findViewById(R.id.publish_button)
        logOutButton = view.findViewById(R.id.log_out_button)
//        passwordTextView = view.findViewById(R.id.password_text_view)

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

        profileViewModel.profileResponse.observe(
            viewLifecycleOwner,
            { response ->
                if (response.isSuccessful) {
                    Log.d("updateUserData", response.body().toString())


                    BazaarSharedPreference.sharedPref.putToken(response.body()?.updatedData?.token.toString())
                    BazaarSharedPreference.sharedPref.putUsername(response.body()?.updatedData?.username.toString())

                    Toast.makeText(context, Constant.updateUserInfoMessage, Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Log.d("updateUserData", response.code().toString())
                }
            }
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username: String = BazaarSharedPreference.sharedPref.getUsername()
        Log.d("username", username)

        if (username != "") {
            profileViewModel.getUserInfo(username)
        }

        publishButton.setOnClickListener {
            val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
            profileViewModel.updateUserData(
                accessToken,
                ProfileCredential(
                    phoneNumberTextView.text.toString().toLong(),
                    emailTextView.text.toString(),
                    usernameTextView.text.toString()
                )
            )
        }

        logOutButton.setOnClickListener {
            BazaarSharedPreference.sharedPref.putToken("")

            (mActivity as MainActivity).replaceFragment(
                LoginFragment(),
                R.id.fragment_container
            )
        }
    }
}