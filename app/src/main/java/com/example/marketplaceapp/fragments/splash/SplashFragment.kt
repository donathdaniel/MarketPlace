package com.example.marketplaceapp.fragments.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.fragments.login.LoginFragment

class SplashFragment : BaseFragment() {

    lateinit var splashLogoImageView: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var loadingTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        splashLogoImageView = view.findViewById(R.id.splash_logo_image_view)
        progressBar = view.findViewById(R.id.progress_bar)
        loadingTextView = view.findViewById(R.id.loading_text_view)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        var ok = false
        object : CountDownTimer(3000, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                if (ok) {
                    splashLogoImageView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    loadingTextView.visibility = View.VISIBLE
                }
                ok = true
                Log.d("onTick", millisUntilFinished.toString())
            }

            override fun onFinish() {
                (mActivity as MainActivity).replaceFragment(
                    LoginFragment(),
                    R.id.fragment_container
                )
                activity?.window?.clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }.start()

        return view
    }


}