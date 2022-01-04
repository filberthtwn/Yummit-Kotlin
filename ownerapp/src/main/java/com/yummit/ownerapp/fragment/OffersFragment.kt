package com.yummit.ownerapp.fragment


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.error.ANError
import com.yummit.ownerapp.NewOfferActivity

import com.yummit.ownerapp.R
import com.yummit.ownerapp.`interface`.OfferCallbacks
import com.yummit.ownerapp.adapt.OffersAdapter
import com.yummit.ownerapp.controller.OfferController
import com.yummit.ownerapp.model.Offer
import kotlinx.android.synthetic.main.fragment_offers.*

/**
 * A simple [Fragment] subclass.
 */
class OffersFragment : Fragment() {

    private lateinit var offersAdapter: OffersAdapter
    private val offerController = OfferController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupData()
    }

    fun setupViews(){

        ll_empty_state.visibility = View.GONE
        btn_offers_add_new_offers.visibility = View.GONE

        /* Setup recycleView */
        offersAdapter = OffersAdapter(context!!)
        rv_offers.adapter = offersAdapter
        rv_offers.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_offers.setHasFixedSize(true)

        btn_offers_add_new_offers.setOnClickListener {
            val intent = Intent(context, NewOfferActivity::class.java)
            startActivity(intent)
        }

        btn_offers_add_offer.setOnClickListener {
            val intent = Intent(context, NewOfferActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupData(){
        val token = context?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("token", "").toString()
        val restaurantId = context?.getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("id", "")!!.toInt()

        shimmer_view_container.startShimmerAnimation()

        offerController.readOffer(token, restaurantId, object : OfferCallbacks{
            override fun onSuccess(offers: ArrayList<Offer>) {
                if (shimmer_view_container != null){
                    shimmer_view_container.stopShimmerAnimation()
                    shimmer_view_container.visibility = View.GONE
                }

                offersAdapter.offers = offers
                offersAdapter.notifyDataSetChanged()
            }

            override fun onFailed(error: ANError) {
                Log.d(ContentValues.TAG, error.errorDetail)
            }
        })
    }


}
