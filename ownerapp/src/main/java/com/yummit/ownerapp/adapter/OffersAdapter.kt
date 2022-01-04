package com.yummit.ownerapp.adapt

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.ownerapp.NewOfferActivity
import com.yummit.ownerapp.R
import com.yummit.ownerapp.model.Offer
import com.yummit.ownerapp.model.Order
import kotlinx.android.synthetic.main.item_offers.view.*

class OffersAdapter(private val context: Context): RecyclerView.Adapter<OffersAdapter.ViewHolder>(){


    var offers:ArrayList<Offer> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_offers, parent, false))
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    override fun onBindViewHolder(holder: OffersAdapter.ViewHolder, position: Int) {

        holder.bindContent(offers[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewOfferActivity::class.java)
            context.startActivity(intent)
        }

    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindContent(offer: Offer){
            view.tv_title.text = offer.title
            view.tv_discount.text = "${offer.discount}% Off"
            when (offer.status){
                0 -> view.sw_availability.isChecked = false
                1 -> view.sw_availability.isChecked = true
            }
        }
    }
}