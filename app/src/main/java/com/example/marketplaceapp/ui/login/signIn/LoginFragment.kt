package com.example.marketplaceapp.ui.login.signIn

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.ui.timeline.TimelineFragment
import com.example.marketplaceapp.ui.login.forgotPassword.ForgotPasswordFragment
import com.example.marketplaceapp.ui.login.register.RegisterFragment
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.model.LoginCredential
import com.example.marketplaceapp.utils.putToken
import com.example.marketplaceapp.utils.putUsername
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : BaseFragment() {

    //private val loginViewModel : LoginViewModel = LoginViewModel(MarketPlaceApiRepository())
    private val loginViewModel: LoginViewModel by viewModels()

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

        loginViewModel.loginResponse.observe(
            viewLifecycleOwner,
            { response ->
                if (response.isSuccessful) {
                    Log.d("Login", response.body().toString())

                    val accessToken: String? = response.body()?.token
                    BazaarSharedPreference.sharedPref.putToken(accessToken.toString())

                    val username: String? = response.body()?.username
                    BazaarSharedPreference.sharedPref.putUsername(username.toString())

//                    (mActivity as MainActivity).sharedPref.edit()
//                        ?.putString("accessToken", accessToken)?.apply()
//                    (mActivity as MainActivity).sharedPref.edit()
//                        ?.putString("username", response.body()?.username)?.apply()

//                    val expiredTime: Long? =
//                        response.body()?.creationTime?.plus(response.body()?.refreshTime!!)
//                    (mActivity as MainActivity).sharedPref.edit()
//                        ?.putString("expiredTime", expiredTime.toString())?.apply()

                    Log.d("accessToken", "put " + accessToken.toString())

                    Toast.makeText(context, Constant.loginSuccessfulMessage, Toast.LENGTH_SHORT).show()
                    (mActivity as MainActivity).replaceFragment(
                        TimelineFragment(),
                        R.id.fragment_container
                    )
                } else {
                    Log.d("Login", response.errorBody().toString())

                    Toast.makeText(context, Constant.loginUnsuccessfulMessage, Toast.LENGTH_SHORT).show()
                }
            })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logInButton.setOnClickListener {

            if (validateInput()) {
                loginViewModel.login(
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