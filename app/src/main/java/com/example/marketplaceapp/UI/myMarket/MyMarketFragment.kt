package com.example.marketplaceapp.UI.myMarket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.UI.myMarket.adapter.MyMarketAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyMarketFragment : BaseFragment() {
    private val myMarketViewModel : MyMarketViewModel by viewModels()

    private lateinit var timelineAdapter: MyMarketAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar : ProgressBar
    private lateinit var floatingActionButton : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_market, container, false)

        myMarketViewModel.getProductResponse.observe(
            viewLifecycleOwner,
            { response ->

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())
                    val timelineList = response.body()?.products!!

                    timelineAdapter = MyMarketAdapter(timelineList)
                    recyclerview.adapter = timelineAdapter
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    recyclerview.setHasFixedSize(true)

                }
                else{
                    Log.d("getProducts", response.code().toString())
                }
            })

        (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
        (mActivity as MainActivity).bottomNavigation.visibility = View.VISIBLE
        recyclerview = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        floatingActionButton = view.findViewById(R.id.floating_action_button)

        floatingActionButton.setOnClickListener{
            (mActivity as MainActivity).replaceFragment(
                CreateFareFragment(),
                R.id.fragment_container,
                true
            )
        }

        val accessToken: String? = (mActivity as MainActivity).sharedPref.getString("accessToken", "asdf1234")
        Log.d("accessToken", "get " + accessToken.toString())

        var username: String? = (mActivity as MainActivity).sharedPref.getString("username", "asdf1234")
//        username = "{ \"username\" : \" $username \"}"
        username = "{\"username\": \"qwer\"}"

        if (accessToken != null) {
            myMarketViewModel.getProducts(accessToken, null, username)

        }
        return view
    }
}