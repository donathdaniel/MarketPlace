package com.example.marketplaceapp.ui.myFares

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.model.Order
import com.example.marketplaceapp.model.Product
import com.example.marketplaceapp.ui.myFares.adapter.MyFaresAdapter
import com.example.marketplaceapp.ui.myMarket.adapter.MyMarketAdapter
import com.example.marketplaceapp.utils.getToken
import com.google.android.material.button.MaterialButton

class MyFaresFragment : BaseFragment() {

    private val myMarketViewModel: MyFaresViewModel by viewModels()

    lateinit var ongoingSalesButton: MaterialButton
    lateinit var ongoingOrdersButton: MaterialButton

    private lateinit var myFaresList: MutableList<Order>
    private lateinit var myFaresAdapter: MyFaresAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_fares, container, false)

        myMarketViewModel.getSalesResponse.observe(viewLifecycleOwner,
             { response ->

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())
                    myFaresList = response.body()?.orders as MutableList<Order>

                    myFaresAdapter = MyFaresAdapter(myFaresList)
                    recyclerview.adapter = myFaresAdapter
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    recyclerview.setHasFixedSize(true)

                } else {
                    Log.d("getProducts", response.code().toString())
                }
        })

        myMarketViewModel.getOrderResponse.observe(viewLifecycleOwner,
            { response ->

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())
                    myFaresList = response.body()?.orders as MutableList<Order>

                    myFaresAdapter = MyFaresAdapter(myFaresList)
                    recyclerview.adapter = myFaresAdapter
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    recyclerview.setHasFixedSize(true)

                } else {
                    Log.d("getProducts", response.code().toString())
                }
            })

        if((mActivity as MainActivity).searchView.visibility == View.GONE) {
            (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
        }
        (mActivity as MainActivity).bottomNavigation.visibility = View.VISIBLE
        (mActivity as MainActivity).filterIcon.isVisible = false

        recyclerview = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        ongoingSalesButton = view.findViewById(R.id.ongoing_sales_button)
        ongoingOrdersButton = view.findViewById(R.id.ongoing_orders_button)

        ongoingOrdersButton.setTextColor(ContextCompat.getColor(ongoingOrdersButton.context, R.color.colorHintText))

        val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
        Log.d("accessToken", "get $accessToken")

        myMarketViewModel.getSales()

        ongoingSalesButton.setOnClickListener {
            ongoingOrdersButton.setTextColor(ContextCompat.getColor(ongoingOrdersButton.context, R.color.colorHintText))
            ongoingSalesButton.setTextColor(ContextCompat.getColor(ongoingOrdersButton.context, R.color.white))
            myMarketViewModel.getSales()
        }

        ongoingOrdersButton.setOnClickListener {
            ongoingSalesButton.setTextColor(ContextCompat.getColor(ongoingOrdersButton.context, R.color.colorHintText))
            ongoingOrdersButton.setTextColor(ContextCompat.getColor(ongoingOrdersButton.context, R.color.white))
            myMarketViewModel.getOrders()
        }

        return view
    }


}