package com.example.marketplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.example.marketplaceapp.api.MarketPlaceApiRepository
import com.example.marketplaceapp.api.MarketPlaceApiViewModel
import com.example.marketplaceapp.fragments.login.LoginFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var profileIconIcon: MenuItem
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var topAppBar: MaterialToolbar
    lateinit var marketPlaceApiViewModel: MarketPlaceApiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        topAppBar = findViewById(R.id.topAppBar)

        bottomNavigation.visibility = View.GONE
        topAppBar.visibility = View.GONE
        initBottomNavigation()
        profileIconIcon = topAppBar.menu.findItem(R.id.profile)

        replaceFragment(LoginFragment(), R.id.fragment_container)

        marketPlaceApiViewModel = MarketPlaceApiViewModel(MarketPlaceApiRepository())

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

//                    if(fragment !is HospitalListFragment)
//                        replaceFragment(HospitalListFragment(), R.id.fragment_container)
                    true
                }
                R.id.my_market -> {

//                    if(fragment !is FeedbackFragment)
//                        replaceFragment(FeedbackFragment(), R.id.fragment_container)
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
        profileIconIcon.setOnMenuItemClickListener {
            //replaceFragment(ProfileFragment(), R.id.fragment_container)
            true
        }
    }
}