package com.example.marketplaceapp.ui.timeline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.BazaarSharedPreference.getToken
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.interfaces.OnTimelineItemClickListener
import com.example.marketplaceapp.ui.timeline.adapter.TimelineAdapter
import com.example.marketplaceapp.model.Product
import java.util.*


class TimelineFragment : BaseFragment(), OnTimelineItemClickListener {

    private val timelineViewModel : TimelineViewModel by viewModels()

    private lateinit var timelineList: MutableList<Product>
    private lateinit var timelineAdapter: TimelineAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)

        timelineViewModel.getProductResponse.observe(
            viewLifecycleOwner,
            { response ->

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())
//                    timelineList = response.body()?.products!!
                    timelineList = response.body()?.products as MutableList<Product>

                    timelineAdapter = TimelineAdapter(timelineList, this)
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

        (mActivity as MainActivity).searchView.queryHint = (mActivity).getString(R.string.product_search_hint)

        (mActivity as MainActivity).searchIcon.setOnMenuItemClickListener{
            (mActivity as MainActivity).searchView.visibility = View.VISIBLE
            (mActivity as MainActivity).topAppBar.visibility = View.GONE
            (mActivity as MainActivity).searchView.isIconified = false

            return@setOnMenuItemClickListener true
        }

        (mActivity as MainActivity).searchView.setOnCloseListener {
            (mActivity as MainActivity).searchView.visibility = View.GONE
            (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
            true
        }

        (mActivity as MainActivity).searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                filter(query)
                return true
            }
        })


        val accessToken: String = BazaarSharedPreference.sharedPref.getToken()
        Log.d("accessToken", "get $accessToken")

        timelineViewModel.getProducts(accessToken)

        return view
    }

    private fun filter(text: String?) {
        val filteredList: MutableList<Product> = mutableListOf()

        for (item in timelineList) {
            if (text != null) {
                if (item.title.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {

                    filteredList.add(item)
                }
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(context, Constant.noTimelineItemMessage, Toast.LENGTH_SHORT).show()
        }

        timelineAdapter.filterList(filteredList)
    }

    override fun orderNow(position: Int) {
        val bundle = Bundle()
        bundle.putString(Constant.username, timelineList[position].username)
        bundle.putString(Constant.title, timelineList[position].title)
        bundle.putString(Constant.pricePerUnit, timelineList[position].pricePerUnit)
        bundle.putString(Constant.priceType, timelineList[position].priceType)
        bundle.putString(Constant.amountType, timelineList[position].amountType)
        bundle.putBoolean(Constant.isActive, timelineList[position].isActive)
        bundle.putString(Constant.units, timelineList[position].units)
        val fragment = MakeOrderDialogFragment()
        fragment.arguments = bundle

        fragment.show(childFragmentManager,"something")
    }
}