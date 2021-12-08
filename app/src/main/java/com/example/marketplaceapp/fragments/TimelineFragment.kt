package com.example.marketplaceapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.adapters.TimelineAdapter


class TimelineFragment : BaseFragment() {
    private lateinit var timelineAdapter: TimelineAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)

        (mActivity as MainActivity).marketPlaceApiViewModel.getProductResponse.observe(
            viewLifecycleOwner,
            { response ->
                Log.d("getProducts", response.code().toString())
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())
                    val timelineList = response.body()?.products!!

                    timelineAdapter = TimelineAdapter(timelineList)
                    recyclerview.adapter = timelineAdapter
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    recyclerview.setHasFixedSize(true)

                }
            })

        (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
        (mActivity as MainActivity).bottomNavigation.visibility = View.VISIBLE
        recyclerview = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        val accessToken: String? = (mActivity as MainActivity).sharedPref.getString("accessToken", "asdf1234")
        Log.d("accessToken", "get " + accessToken.toString())

        if (accessToken != null) {
            (mActivity as MainActivity).marketPlaceApiViewModel.getProducts(accessToken)
        }

        return view
    }


}