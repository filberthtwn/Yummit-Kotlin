package com.yummit.ownerapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yummit.ownerapp.R
import com.yummit.ownerapp.adapter.OrdersAdapter
import com.yummit.ownerapp.fragment.AccountFragment
import com.yummit.ownerapp.fragment.MenuFragment
import com.yummit.ownerapp.fragment.OffersFragment
import com.yummit.ownerapp.fragment.OrdersFragment

import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation_home.setOnNavigationItemSelectedListener(this)

        if (intent == null){
            when (intent.getStringExtra("goto")){

            }
        } else {
            loadFragment(OrdersFragment())
        }
        this.supportActionBar?.hide()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bnv_orders->{
                loadFragment(OrdersFragment())
                return true
            }
            R.id.bnv_menu->{
                loadFragment(MenuFragment())
            }
            R.id.bnv_offers->{
                loadFragment(OffersFragment())
            }
            R.id.bnv_account->{
                loadFragment(AccountFragment())
            }
        }
        bottom_navigation_home.menu.findItem(item.itemId).isChecked = true
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_home_container, fragment)
            .commit()
    }
}
