package com.example.marketplaceapp.ui.timeline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplaceapp.BaseFragment
import com.example.marketplaceapp.BazaarSharedPreference
import com.example.marketplaceapp.MainActivity
import com.example.marketplaceapp.R
import com.example.marketplaceapp.interfaces.OnTimelineItemClickListener
import com.example.marketplaceapp.utils.Constant
import com.example.marketplaceapp.ui.timeline.adapter.TimelineAdapter
import com.example.marketplaceapp.model.Product
import com.example.marketplaceapp.ui.details.DetailsFragment
import com.example.marketplaceapp.ui.login.signIn.LoginFragment
import com.example.marketplaceapp.ui.profile.ProfileViewByOthersFragment
import com.example.marketplaceapp.utils.getToken
import com.example.marketplaceapp.utils.getUsername
import java.util.*


class TimelineFragment : BaseFragment(), OnTimelineItemClickListener {

    private val timelineViewModel: TimelineViewModel by viewModels()

    private lateinit var timelineList: MutableList<Product>
    private lateinit var timelineAdapter: TimelineAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar: ProgressBar
    var LIMIT = 20
    var INCREASE_LIMIT = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_timeline, container, false)

        if ((mActivity as MainActivity).searchView.visibility == View.GONE) {
            (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE
        }
        (mActivity as MainActivity).bottomNavigation.visibility = View.VISIBLE
        (mActivity as MainActivity).searchIcon.isVisible = true
        (mActivity as MainActivity).filterIcon.isVisible = true
        recyclerview = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LIMIT += INCREASE_LIMIT
                    timelineViewModel.getProducts2(LIMIT)
                }
            }
        })

        timelineViewModel.getProductResponse.observe(
            viewLifecycleOwner,
            { response ->

                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE

                    Log.d("getProducts", response.body().toString())
                    timelineList = response.body()?.products as MutableList<Product>

                    timelineAdapter = TimelineAdapter(timelineList, this)
                    recyclerview.adapter = timelineAdapter
                    val linearLayoutManager = LinearLayoutManager(context)
                    linearLayoutManager.scrollToPositionWithOffset(LIMIT - INCREASE_LIMIT - 3, 0);
                    recyclerview.layoutManager = linearLayoutManager
                    recyclerview.setHasFixedSize(true)

                } else {
                    Log.d("getProducts", response.code().toString())
                }
            })

        (mActivity as MainActivity).searchView.queryHint =
            (mActivity).getString(R.string.product_search_hint)

        (mActivity as MainActivity).searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                filter(query)
                return true
            }
        })

//        val filterByList = resources.getStringArray(R.array.filterBy)
//        val adapter = ArrayAdapter(context!!, R.layout.support_simple_spinner_dropdown_item, filterByList)
//        (mActivity as MainActivity).spinnerFilter.adapter = adapter
//
//        (mActivity as MainActivity).filterIcon.setOnMenuItemClickListener {
//            Log.d("sdf","sfda")
//            (mActivity as MainActivity).spinnerFilter.isVisible = true
//            return@setOnMenuItemClickListener true
//        }
//
//        (mActivity as MainActivity).spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Toast.makeText(context, parent?.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//            }
//        }

        timelineViewModel.getProducts2()

        return view
    }

    private fun filter(text: String?) {
        val filteredList: MutableList<Product> = mutableListOf()

        for (item in timelineList) {
            if (text != null) {
                if (item.title.lowercase(Locale.getDefault())
                        .contains(text.lowercase(Locale.getDefault()))
                ) {

                    filteredList.add(item)
                }
            }
        }
//        if (filteredList.isEmpty()) {
//            Toast.makeText(context, Constant.noTimelineItemMessage, Toast.LENGTH_SHORT).show()
//        }

        timelineAdapter.filterList(filteredList)
    }

    override fun orderNow(product: Product) {

        if (product.username == BazaarSharedPreference.sharedPref.getUsername()) {
            return
        }

        (mActivity as MainActivity).searchView.visibility = View.GONE
        (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE

        val bundle = Bundle()
        bundle.putString(Constant.username, product.username)
        bundle.putString(Constant.title, product.title)
        bundle.putString(Constant.description, product.description)
        bundle.putString(Constant.pricePerUnit, product.pricePerUnit)
        bundle.putString(Constant.priceType, product.priceType)
        bundle.putString(Constant.amountType, product.amountType)
        bundle.putBoolean(Constant.isActive, product.isActive)
        bundle.putString(Constant.units, product.units)
        val fragment = MakeOrderDialogFragment()
        fragment.arguments = bundle

        fragment.show(childFragmentManager, "something")
    }

    override fun onProfile(product: Product) {
        (mActivity as MainActivity).searchView.visibility = View.GONE
        (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE

        val bundle = Bundle()
        bundle.putString(Constant.username, product.username)
        val fragment = ProfileViewByOthersFragment()
        fragment.arguments = bundle

        (mActivity as MainActivity).replaceFragment(
            fragment,
            R.id.fragment_container,
            true
        )
    }

    override fun onDetails(product: Product) {
        (mActivity as MainActivity).searchView.visibility = View.GONE
        (mActivity as MainActivity).topAppBar.visibility = View.VISIBLE

        val bundle = Bundle()
        bundle.putString(Constant.productId, product.productId)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        (mActivity as MainActivity).replaceFragment(
            fragment,
            R.id.fragment_container,
            true
        )

    }

}