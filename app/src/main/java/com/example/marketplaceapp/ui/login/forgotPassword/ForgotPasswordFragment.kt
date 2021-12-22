package com.example.marketplaceapp.ui.login.forgotPassword

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
import com.example.marketplaceapp.BazaarSharedPreference.getUsername
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.ui.login.signIn.LoginFragment
import com.example.marketplaceapp.model.ResetPasswordCredential
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordFragment : BaseFragment() {

    private val forgotPasswordViewModel : ForgotPasswordViewModel by viewModels()

    lateinit var emailTextViewLayout: TextInputLayout
    lateinit var emailTextView: TextView
    lateinit var emailMeButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        emailTextViewLayout = view.findViewById(R.id.email_text_view_layout)
        emailTextView = view.findViewById(R.id.email_text_view)
        emailMeButton = view.findViewById(R.id.email_me_button)

        forgotPasswordViewModel.resetPasswordResponse.observe(
            viewLifecycleOwner,
            { response ->
                Log.d("ResetPassword", response.errorBody().toString())
                if (response.isSuccessful) {
                    Log.d("ResetPassword", response.body()?.message.toString())

                    Toast.makeText(context, "Password reset successfully", Toast.LENGTH_SHORT)
                        .show()
                    (mActivity as MainActivity).replaceFragment(
                        LoginFragment(),
                        R.id.fragment_container
                    )
                }
            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailMeButton.setOnClickListener {
            if (validateInput()) {
                val username: String? = BazaarSharedPreference.sharedPref.getUsername()
                Log.d("username", username.toString())

                forgotPasswordViewModel.resetPassword(
                    ResetPasswordCredential(username.toString(), emailTextView.text.toString())
                )
            }
        }
    }

    private fun validateInput(): Boolean {
        emailTextViewLayout.error = null

        when {
            emailTextView.text.toString().isEmpty() -> {
                emailTextViewLayout.error = getString(R.string.error)
                return false
            }
        }
        return true
    }
}