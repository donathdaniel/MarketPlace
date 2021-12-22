package com.example.marketplaceapp.ui.myMarket

import android.content.DialogInterface
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
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.interfaces.OnMyMarketItemClickListener
import com.example.marketplaceapp.model.Product
import com.example.marketplaceapp.ui.myMarket.adapter.MyMarketAdapter
import com.example.marketplaceapp.utils.getToken
import com.example.marketplaceapp.utils.getUsername
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyMarketFragment : BaseFragment(), OnMyMarketItemClickListener {
    private val myMarketViewModel: MyMarketViewModel by viewModels()

    private lateinit var myMarketList: MutableList<Product>
    private lateinit var timelineAdapter: MyMarketAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var floatingActionButton: FloatingActionButton

    private var productPosition: Int = 0

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
                    myMarketList = response.body()?.products as MutableList<Product>

                    timelineAdapter = MyMarketAdapter(myMarketList, this)
                    recyclerview.adapter = timelineAdapter
                    recyclerview.layoutManager = LinearLayoutManager(context)
                    recyclerview.setHasFixedSize(true)

                } else {
                    Log.d("getProducts", response.code().toString())
                }
            })

        myMarketViewModel.deleteProductResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                Log.d("deleteProducts", response.body().toString())

                myMarketList.remove(myMarketList[productPosition])
                timelineAdapter.notifyItemRemoved(productPosition)
            } else {
                Log.d("deleteProducts", response.code().toString())
            }
        })

        (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
        (mActivity as MainActivity).bottomNavigation.visibility = View.VISIBLE
        recyclerview = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        floatingActionButton = view.findViewById(R.id.floating_action_button)

        floatingActionButton.setOnClickListener {
            (mActivity as MainActivity).replaceFragment(
                CreateFareFragment(),
                R.id.fragment_container,
                true
            )
        }

        val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
        Log.d("accessToken", "get $accessToken")

        val username: String? = BazaarSharedPreference.sharedPref.getUsername()

        myMarketViewModel.getProducts(accessToken, null, "{\"username\": \"$username\"}")
        return view
    }

    override fun onDelete(position: Int) {
        val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
        productPosition = position

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete))
            .setPositiveButton("Yes") { _, _ ->
                myMarketViewModel.deleteProducts(accessToken, myMarketList[position].productId)
            }
            .setNegativeButton("No", null)
            .show()

        //timelineAdapter.notifyItemRemoved(position)
    }

    override fun onDetails(position: Int) {
        //(mActivity as MainActivity).replaceFragment(DetailsProduct(), R.id.fragment_container)
    }
}