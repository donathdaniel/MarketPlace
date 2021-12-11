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
import com.example.marketplaceapp.model.ResetPasswordCredential
import com.google.android.material.textfield.TextInputLayout

class ForgotPasswordFragment : BaseFragment() {

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

        (mActivity as MainActivity).marketPlaceApiViewModel.resetPasswordResponse.observe(
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
                val username: String? =
                    (mActivity as MainActivity).sharedPref.getString("username", "asdf1234")
                Log.d("username", username.toString())

                (mActivity as MainActivity).marketPlaceApiViewModel.resetPassword(
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