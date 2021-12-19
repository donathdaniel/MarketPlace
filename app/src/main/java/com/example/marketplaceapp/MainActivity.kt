package com.example.marketplaceapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.api.MarketPlaceApiViewModel
import com.example.marketplaceapp.fragments.MyMarketFragment
import com.example.marketplaceapp.fragments.ProfileFragment
import com.example.marketplaceapp.fragments.splash.SplashFragment
import com.example.marketplaceapp.fragments.TimelineFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var sharedPref : SharedPreferences

    lateinit var marketPlaceApiViewModel: MarketPlaceApiViewModel

    lateinit var bottomNavigation: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var profileIcon: MenuItem
    lateinit var filterIcon: MenuItem
    lateinit var searchIcon: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = applicationContext.getSharedPreferences(Constant.sharedPreferenceName, Context.MODE_PRIVATE)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        topAppBar = findViewById(R.id.top_app_bar)

        bottomNavigation.visibility = View.GONE
        topAppBar.visibility = View.GONE
        initBottomNavigation()

        profileIcon = topAppBar.menu.findItem(R.id.profile_menu_item)
        filterIcon = topAppBar.menu.findItem(R.id.filter_menu_item)
        searchIcon = topAppBar.menu.findItem(R.id.search_menu_item)
        initTopBar()


        marketPlaceApiViewModel = MarketPlaceApiViewModel(MarketPlaceApiRepository())

        replaceFragment(SplashFragment(), R.id.fragment_container)

//        val accessToken: String? = sharedPref.getString("accessToken", "asdf1234")
//        Log.d("accessToken", "get " + accessToken.toString())
//
//        if(accessToken == null || accessToken == "asdf1234") {
//
//        }
//        else{
//            replaceFragment(TimelineFragment(), R.id.fragment_container)
//        }
    }

    fun replaceFragment(fragment: Fragment, containerId: Int, addToBackStack:Boolean = false, withAnimation:Boolean = false){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        when(addToBackStack){
            true -> {
                transaction.addToBackStack(null)
            }
        }
        transaction.commit()
    }

    private fun initBottomNavigation(){
        bottomNavigation.setOnItemSelectedListener {item ->
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            when(item.itemId) {
                R.id.timeline -> {

                    if(fragment !is TimelineFragment)
                        replaceFragment(TimelineFragment(), R.id.fragment_container)
                    true
                }
                R.id.my_market -> {

                    if(fragment !is MyMarketFragment)
                        replaceFragment(MyMarketFragment(), R.id.fragment_container)
                    true
                }
                R.id.my_fares -> {

//                    if(fragment !is MyAppointmentsFragment)
//                        replaceFragment(MyAppointmentsFragment(), R.id.fragment_container)
                    true
                }
                else -> false
            }
        }
    }

    private fun initTopBar(){
        profileIcon.setOnMenuItemClickListener {
            replaceFragment(ProfileFragment(), R.id.fragment_container)
            true
        }
        filterIcon.setOnMenuItemClickListener {
            //TODO
            true
        }
        searchIcon.setOnMenuItemClickListener {
            //TODO
            true
        }
    }
}