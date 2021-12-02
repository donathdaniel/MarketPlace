package com.example.marketplaceapp.fragments.login

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
import com.example.marketplaceapp.fragments.forgotPassword.ForgotPasswordFragment
import com.example.marketplaceapp.fragments.register.RegisterFragment
import com.example.marketplaceapp.model.LoginCredential
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Response.error

class LoginFragment : BaseFragment() {

    lateinit var logInButton : Button
    lateinit var signUpButton : Button
    lateinit var clickHereTextView : TextView
    lateinit var emailTextLayout : TextInputLayout
    lateinit var passwordTextLayout : TextInputLayout
    lateinit var emailTextView : TextView
    lateinit var passwordTextView : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        logInButton = view.findViewById(R.id.log_in_button)
        signUpButton = view.findViewById(R.id.sign_up_button)
        clickHereTextView = view.findViewById(R.id.click_here_text_view)

        emailTextLayout = view.findViewById(R.id.email_text_view_layout)
        passwordTextLayout = view.findViewById(R.id.password_text_view_layout)
        emailTextView = view.findViewById(R.id.email_text_view)
        passwordTextView = view.findViewById(R.id.password_text_view)

        (mActivity as MainActivity).marketPlaceApiViewModel.loginResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful){
                Log.d("Login",response.body().toString())
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logInButton.setOnClickListener{
            //TODO api
            (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
            //(mActivity as MainActivity).replaceFragment(TimelineFragment(), R.id.fragment_container)
            if(validateInput()){
                (mActivity as MainActivity).marketPlaceApiViewModel.login(LoginCredential(emailTextView.text.toString(), passwordTextView.text.toString()))
            }

        }

        signUpButton.setOnClickListener {
            (mActivity as MainActivity).replaceFragment(RegisterFragment(),R.id.fragment_container,true)
        }
        clickHereTextView.setOnClickListener {
            (mActivity as MainActivity).replaceFragment(ForgotPasswordFragment(),R.id.fragment_container,true)
        }
    }

    private fun validateInput(): Boolean {
        emailTextLayout.error = null
        passwordTextLayout.error = null

        when{
            emailTextView.text.toString().isEmpty() -> {
                emailTextLayout.error = getString(R.string.error)
                return false
            }
            passwordTextView.text.toString().isEmpty() -> {
                passwordTextLayout.error = getString(R.string.error)
                return false
            }
        }
        return true
    }
}