package com.example.marketplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.marketplaceapp.ui.login.signIn.LoginFragment
import com.example.marketplaceapp.ui.myFares.MyFaresFragment
import com.example.marketplaceapp.ui.myMarket.MyMarketFragment
import com.example.marketplaceapp.ui.profile.ProfileFragment
import com.example.marketplaceapp.ui.splash.SplashFragment
import com.example.marketplaceapp.ui.timeline.TimelineFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var searchView : SearchView

    lateinit var profileIcon: MenuItem
    lateinit var filterIcon: MenuItem
    lateinit var searchIcon: MenuItem

    lateinit var spinnerFilter: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigation = findViewById(R.id.bottom_navigation)
        topAppBar = findViewById(R.id.top_app_bar)
        searchView = findViewById(R.id.search_app_bar)

        bottomNavigation.visibility = View.GONE
        topAppBar.visibility = View.GONE
        initBottomNavigation()

        profileIcon = topAppBar.menu.findItem(R.id.profile_menu_item)
        filterIcon = topAppBar.menu.findItem(R.id.filter_menu_item)
        searchIcon = topAppBar.menu.findItem(R.id.search_menu_item)
        spinnerFilter = findViewById(R.id.spinner_filter)
        initTopBar()


        replaceFragment(SplashFragment(), R.id.fragment_container)

        searchIcon.setOnMenuItemClickListener{
            searchView.visibility = View.VISIBLE
            topAppBar.visibility = View.GONE
            searchView.isIconified = false

            return@setOnMenuItemClickListener true
        }

        searchView.setOnCloseListener {
            searchView.visibility = View.GONE
            topAppBar.visibility = View.VISIBLE
            true
        }

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

                    if(fragment !is MyFaresFragment)
                        replaceFragment(MyFaresFragment(), R.id.fragment_container)
                    true
                }
                else -> false
            }
        }
    }

    private fun initTopBar(){
        profileIcon.setOnMenuItemClickListener {
            replaceFragment(ProfileFragment(), R.id.fragment_container, true)
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