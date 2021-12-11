package com.example.marketplaceapp.fragments.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.fragments.TimelineFragment
import com.example.marketplaceapp.model.LoginCredential
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : BaseFragment() {

    lateinit var logInButton: Button
    lateinit var signUpButton: Button
    lateinit var clickHereTextView: TextView

    lateinit var usernameTextViewLayout: TextInputLayout
    lateinit var passwordTextViewLayout: TextInputLayout
    lateinit var usernameTextView: TextView
    lateinit var passwordTextView: TextView

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        logInButton = view.findViewById(R.id.log_in_button)
        signUpButton = view.findViewById(R.id.sign_up_button)
        clickHereTextView = view.findViewById(R.id.click_here_text_view)

        usernameTextViewLayout = view.findViewById(R.id.username_text_view_layout)
        passwordTextViewLayout = view.findViewById(R.id.password_text_view_layout)
        usernameTextView = view.findViewById(R.id.username_text_view)
        passwordTextView = view.findViewById(R.id.password_text_view)

        (mActivity as MainActivity).marketPlaceApiViewModel.loginResponse.observe(
            viewLifecycleOwner,
            { response ->
                Log.d("Login", response.errorBody().toString())

                if (response.isSuccessful) {
                    Log.d("Login", response.body().toString())

                    val accessToken: String? = response.body()?.token
                    (mActivity as MainActivity).sharedPref.edit()
                        ?.putString("accessToken", accessToken)?.apply()
                    (mActivity as MainActivity).sharedPref.edit()
                        ?.putString("username", response.body()?.username)?.apply()

                    val expiredTime: Long? =
                        response.body()?.creationTime?.plus(response.body()?.refreshTime!!)
                    (mActivity as MainActivity).sharedPref.edit()
                        ?.putString("expiredTime", expiredTime.toString())?.apply()

                    Log.d("accessToken", "put " + accessToken.toString())

                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    (mActivity as MainActivity).replaceFragment(
                        TimelineFragment(),
                        R.id.fragment_container
                    )
                } else {
                    Toast.makeText(context, "Login unsuccessful", Toast.LENGTH_SHORT).show()
                }
            })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logInButton.setOnClickListener {

            if (validateInput()) {
                (mActivity as MainActivity).marketPlaceApiViewModel.login(
                    LoginCredential(
                        usernameTextView.text.toString(),
                        passwordTextView.text.toString()
                    )
                )
            }
        }

        signUpButton.setOnClickListener {
            (mActivity as MainActivity).replaceFragment(
                RegisterFragment(),
                R.id.fragment_container,
                true
            )
        }
        clickHereTextView.setOnClickListener {
            (mActivity as MainActivity).replaceFragment(
                ForgotPasswordFragment(),
                R.id.fragment_container,
                true
            )
        }
    }

    private fun validateInput(): Boolean {
        usernameTextViewLayout.error = null
        passwordTextViewLayout.error = null

        when {
            usernameTextView.text.toString().isEmpty() -> {
                usernameTextViewLayout.error = getString(R.string.error)
                return false
            }
            passwordTextView.text.toString().isEmpty() -> {
                passwordTextViewLayout.error = getString(R.string.error)
                return false
            }
        }
        return true
    }

}