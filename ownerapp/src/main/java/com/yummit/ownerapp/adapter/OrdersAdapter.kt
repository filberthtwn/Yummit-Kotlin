package com.yummit.ownerapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.ownerapp.*
import com.yummit.ownerapp.model.Order
import kotlinx.android.synthetic.main.item_orders.view.*

class OrdersAdapter(private val context: Context): RecyclerView.Adapter<OrdersAdapter.ViewHolder>(){
    var orders = ArrayList<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_orders, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(orders[position])
        holder.itemView.setOnClickListener{_ ->
            val intent = Intent(context, OrderDetailActivity::class.java)
//            /* Passing Order to orderDetails activity */
            intent.putExtra("order", orders[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindContent(order: Order){
            view.tv_title.text = "Order #" + String.format("%04d", order.orderId) //For Order id 0000
            view.tv_discount.text = "0 item"
            view.tv_menuList_price.text = "${(order.price - 10000).readableNumber()}"
            if (order.orderType == "self-pickup"){
                view.iv_food.setImageResource(R.drawable.ic_self_pickup)
                view.tv_type.text = "Self Pickup"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.tv_type.setTextColor(context.resources.getColor(R.color.colorPrimary, null))
                }
            }
        }
    }

}