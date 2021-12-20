package com.example.marketplaceapp.UI.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R

class ProfileFragment : BaseFragment() {

    private val profileViewModel : ProfileViewModel by viewModels()

    lateinit var emailTextView : TextView
    lateinit var mainUsernameTextView : TextView
    lateinit var usernameTextView : TextView
    lateinit var phoneNumberTextView : TextView
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
//        passwordTextView = view.findViewById(R.id.password_text_view)

        profileViewModel.userInfoResponse.observe(
            viewLifecycleOwner,
            { response ->
                if (response.isSuccessful) {
                    Log.d("getUserInfo", response.body().toString())

                    mainUsernameTextView.text = response.body()?.data!![0].username
                    emailTextView.text = response.body()?.data!![0].email
                    usernameTextView.text = response.body()?.data!![0].username
                    phoneNumberTextView.text = response.body()?.data!![0].phoneNumber.toString()

                }
                else{
                    Log.d("getUserInfo", response.code().toString())
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
            profileViewModel.getUserInfo(username)
        }
    }
}