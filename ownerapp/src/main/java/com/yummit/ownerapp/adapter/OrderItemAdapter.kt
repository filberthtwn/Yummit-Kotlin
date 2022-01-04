package com.yummit.ownerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.ownerapp.R
import com.yummit.ownerapp.model.OrderFood
import com.yummit.ownerapp.readableNumber
import kotlinx.android.synthetic.main.item_order_item.view.*

class OrderItemAdapter(private val context: Context): RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {
    var foodOrders = arrayListOf<OrderFood>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_item, parent, false))
    }

    override fun getItemCount(): Int {
        return foodOrders.size
    }

    override fun onBindViewHolder(holder: OrderItemAdapter.ViewHolder, position: Int) {
        holder.bindContent(foodOrders[position])
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindContent(foodOrders: OrderFood) {
            view.textView14.text = foodOrders.menu.name
            view.textView15.text = foodOrders.quantity.toString()
            view.textView17.text = (foodOrders.menu.price.toInt() * foodOrders.quantity).readableNumber()
        }
    }
}