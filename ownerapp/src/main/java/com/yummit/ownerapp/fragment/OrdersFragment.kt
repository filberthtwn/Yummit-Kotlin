package com.yummit.ownerapp.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.yummit.ownerapp.R
import com.yummit.ownerapp.`interface`.OrderCallbacks
import com.yummit.ownerapp.adapter.OrdersAdapter
import com.yummit.ownerapp.adapter.OrdersViewPagerAdapter
import com.yummit.ownerapp.controller.OrderController
import com.yummit.ownerapp.controller.UserController
import com.yummit.ownerapp.model.Order
import kotlinx.android.synthetic.main.fragment_orders.*
import kotlinx.android.synthetic.main.fragment_orders_list.*
import kotlinx.android.synthetic.main.fragment_orders_list.view.*


/**
 * A simple [Fragment] subclass.
 */
class OrdersFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: OrdersViewPagerAdapter
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews(){

//        UserController.userLogin("","")

        // Setup tab layout and view pager
        tabLayout = tl_orders
        adapter = OrdersViewPagerAdapter(childFragmentManager)

        adapter.addFragments(OrdersListFragment("new"),"New")
        adapter.addFragments(OrdersListFragment("on_progress"),"OnProcess")
        adapter.addFragments(OrdersListFragment("done"),"Past")

        vp_orders.adapter = adapter

        tabLayout.setupWithViewPager(vp_orders)
    }
}
