package com.yummit.ownerapp.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yummit.ownerapp.LoginActivity
import com.yummit.ownerapp.LoyaltyActivity

import com.yummit.ownerapp.R
import com.yummit.ownerapp.`interface`.RestaurantCallbacks
import com.yummit.ownerapp.controller.UserController
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Restaurant
import com.yummit.ownerapp.readableNumber
import kotlinx.android.synthetic.main.fragment_account.*

/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews(){

        btn_loyalty.setOnClickListener {
            val intent = Intent(context, LoyaltyActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {
            activity?.finish()

            val prefs = context?.getSharedPreferences("user", Context.MODE_PRIVATE)?.edit()
            prefs?.remove("userId")
            prefs?.remove("token")
            prefs?.commit()

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        val restaurantPrefs = context!!.getSharedPreferences("user", Context.MODE_PRIVATE)
        Glide.with(requireView()).load(restaurantPrefs.getString("imageUrl","")).into(iv_restaurant_logo)
        tv_customer_name.text = restaurantPrefs.getString("name", "").toString()
        tv_customer_email.text = restaurantPrefs.getString("email", "").toString()
        tv_balance.text = "${restaurantPrefs.getInt("balance", 0).readableNumber()}"


        setupData()
    }

    private fun setupData(){
        val token = context?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("token","")
        if (token != null) {
            UserController.getRestaurantDetails(token, object: RestaurantCallbacks{
                override fun onSuccess(restaurant: Restaurant) {

                    if (view != null){
                        Glide.with(requireView()).load(restaurant.imageUrl).into(iv_restaurant_logo)
                        if (tv_balance != null){
                            tv_balance.text = "${restaurant.balance.readableNumber()}"
                        }
                    }


                }
            })
        }
    }



}
