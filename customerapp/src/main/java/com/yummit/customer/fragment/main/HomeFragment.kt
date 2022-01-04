package com.yummit.customer.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yummit.customer.R
import com.yummit.customer.databinding.FragmentHomeBinding
import com.yummit.customer.fragment.main.home.AccountFragment
import com.yummit.customer.fragment.main.home.ExploreFragment
import com.yummit.customer.fragment.main.home.MyOrderFragment
import com.yummit.customer.helper.comingSoon
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var fragment: Fragment
    private var KEY = "savefragment"

    private lateinit var dataBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_navigation_home.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState != null){
            fragment = fragmentManager!!.getFragment(savedInstanceState, KEY)!!
        } else {
            fragment = ExploreFragment(requireContext())
        }
        loadFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.bnv_explore -> {
                fragment = ExploreFragment(requireContext())
                loadFragment()
                return true
            }
            R.id.bnv_order -> {
                fragment = MyOrderFragment(requireContext())
                loadFragment()
                return true
            }
            R.id.bnv_inbox -> {
                context?.comingSoon()
            }
            R.id.bnv_account -> {
                fragment = AccountFragment(requireContext())
                loadFragment()
                return true
            }
        }
        return false
    }

    private fun loadFragment() {
        fragmentManager!!.beginTransaction().replace(R.id.fl_home_container, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragmentManager!!.putFragment(outState, KEY, fragment)
    }
}
