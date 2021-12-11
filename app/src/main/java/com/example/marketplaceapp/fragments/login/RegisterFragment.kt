package com.example.marketplaceapp.fragments.login

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
import com.example.marketplaceapp.model.RegistrationCredential
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : BaseFragment() {

    lateinit var registerButton: Button
    lateinit var logIn: TextView

    lateinit var usernameTextViewLayout: TextInputLayout
    lateinit var phoneNumberTextViewLayout: TextInputLayout
    lateinit var emailTextViewLayout: TextInputLayout
    lateinit var passwordTextViewLayout: TextInputLayout
    lateinit var usernameTextView: TextView
    lateinit var phoneNumberTextView: TextView
    lateinit var emailTextView: TextView
    lateinit var passwordTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        registerButton = view.findViewById(R.id.register_button)
        logIn = view.findViewById(R.id.log_in_text_view)

        usernameTextViewLayout = view.findViewById(R.id.username_text_view_layout)
        phoneNumberTextViewLayout = view.findViewById(R.id.phone_number_text_view_layout)
        emailTextViewLayout = view.findViewById(R.id.email_text_view_layout)
        passwordTextViewLayout = view.findViewById(R.id.password_text_view_layout)
        usernameTextView = view.findViewById(R.id.username_text_view)
        phoneNumberTextView = view.findViewById(R.id.phone_number_text_view)
        emailTextView = view.findViewById(R.id.email_text_view)
        passwordTextView = view.findViewById(R.id.password_text_view)

        (mActivity as MainActivity).marketPlaceApiViewModel.registerResponse.observe(
            viewLifecycleOwner,
            { response ->
                Log.d("Register", response.errorBody().toString())
                if (response.isSuccessful) {
                    Log.d("Register", response.body().toString())

                    Toast.makeText(context, "You need to activate your account", Toast.LENGTH_SHORT)
                        .show()
                    (mActivity as MainActivity).replaceFragment(
                        LoginFragment(),
                        R.id.fragment_container
                    )
                }
                else{
                    Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {
            (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
            (mActivity as MainActivity).bottomNavigation.visibility = View.VISIBLE

            if (validateInput()) {
                (mActivity as MainActivity).marketPlaceApiViewModel.registration(
                    RegistrationCredential(
                        usernameTextView.text.toString(),
                        passwordTextView.text.toString(),
                        emailTextView.text.toString(),
                        phoneNumberTextView.text.toString().toLong()
                    )
                )
            }
        }

        logIn.setOnClickListener {
            (mActivity as MainActivity).replaceFragment(LoginFragment(), R.id.fragment_container)
        }
    }

    private fun validateInput(): Boolean {
        usernameTextViewLayout.error = null
        phoneNumberTextViewLayout.error = null
        emailTextViewLayout.error = null
        passwordTextViewLayout.error = null

        when {
            usernameTextView.text.toString().isEmpty() -> {
                usernameTextViewLayout.error = getString(R.string.error)
                return false
            }
            phoneNumberTextView.text.toString().isEmpty() -> {
                phoneNumberTextViewLayout.error = getString(R.string.error)
                return false
            }
            emailTextView.text.toString().isEmpty() -> {
                emailTextViewLayout.error = getString(R.string.error)
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