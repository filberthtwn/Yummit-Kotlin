package com.yummit.customer.adapter.myorder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yummit.customer.R
import com.yummit.customer.helper.randomizeID
import com.yummit.customer.helper.readableNumber
import com.yummit.customer.model.Order
import kotlinx.android.synthetic.main.item_orders.view.*

class OnProcessOrdersAdapter(private val context: Context, private val listener: (Order) -> Unit): RecyclerView.Adapter<OnProcessOrdersAdapter.ViewHolder>() {
    var orders = ArrayList<Order>()
    set(orders){
        if (orders.size > 0){
            this.orders.clear()
        }

        this.orders.addAll(orders)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_orders, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(orders[position], listener)
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindContent(order: Order, listener: (Order) -> Unit){
            with(view){
                tv_myorder_title.text = order.restoName
                tv_myorder_id.text = order.orderId.randomizeID()
                tv_myorder_price.text = order.total.readableNumber()

                if (order.type == "Delivery"){
                    tv_myorder_price.setTextColor(ContextCompat.getColor(context, R.color.colorTertiary))
                    Glide.with(context).load(R.drawable.ic_delivery).into(img_myorder_logo)
                } else {
                    tv_myorder_price.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    Glide.with(context).load(R.drawable.ic_bag).into(img_myorder_logo)
                    img_myorder_logo.backgroundTintList = ContextCompat.getColorStateList(context, R.color.colorPrimary)
                }

                tv_myorder_view.setOnClickListener {
                    listener(order)
                }
            }
        }
    }
}