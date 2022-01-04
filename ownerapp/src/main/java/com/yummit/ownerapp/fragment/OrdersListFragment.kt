package com.yummit.ownerapp.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.ownerapp.LoginActivity

import com.yummit.ownerapp.R
import com.yummit.ownerapp.`interface`.OrderCallbacks
import com.yummit.ownerapp.adapter.OrdersAdapter
import com.yummit.ownerapp.controller.OrderController
import com.yummit.ownerapp.model.Order
import com.yummit.ownerapp.model.OrderFood
import kotlinx.android.synthetic.main.fragment_orders_list.*

/**
 * A simple [Fragment] subclass.
 */
class OrdersListFragment(status: String) : Fragment() {

    val status = status
    private lateinit var ordersAdapter: OrdersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupData()
    }

    override fun onResume() {
        super.onResume()
        setupData()
    }

    private fun setupViews() {

        /* Setup orders recycler view */
        ordersAdapter = OrdersAdapter(context!!)
        rv_orders.adapter = ordersAdapter
        rv_orders.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_orders.setHasFixedSize(true)
    }

    private fun setupData() {

        // Setup shimmer effect
        shimmer_view_container.startShimmerAnimation()

        val token =
            context?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("token", "")
        println(token)

        val orderController = OrderController()
        orderController.getOrders(token!!, object : OrderCallbacks {
            override fun onSuccess(orders: ArrayList<Order>) {
                val finalOrders: ArrayList<Order> = arrayListOf<Order>()
                for (i in 0 until (orders.size)) {
                    if (orders[i].status == status) {
                        finalOrders.add(orders[i])
                    } else {
                        if (status == "done") {
                            if (orders[i].status == "reject") {
                                finalOrders.add(orders[i])
                            }
                        }
                    }
                }

                ordersAdapter.orders = finalOrders
                ordersAdapter.notifyDataSetChanged()

                shimmer_view_container?.let {
                    shimmer_view_container.stopShimmerAnimation()
                    shimmer_view_container.visibility = View.GONE
                }
            }

            override fun onFailed() {
                val prefs = context?.getSharedPreferences("user", Context.MODE_PRIVATE)?.edit()
                    ?.putString("token", "")
                prefs?.commit()
                val intent = Intent(context, LoginActivity::class.java)
                context?.startActivity(intent)
            }
        })
    }

}