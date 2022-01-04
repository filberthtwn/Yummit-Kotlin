
package com.yummit.ownerapp.viewInterface

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.yummit.ownerapp.R
import com.yummit.ownerapp.fragment.MenuFragment
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

        FirebaseApp.initializeApp(this)
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
        }
        return false
    }

    fun switchFragment(fragment: Fragment) {
        println("ABC")
        supportFragmentManager.beginTransaction().replace(R.id.fl_home_container, fragment).commit()
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_home_container, fragment)
            .commit()
    }
}