package com.yummit.customer.adapter.myorder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.model.helper.FoodOrders
import kotlinx.android.synthetic.main.item_dialog_food_order.view.*

class ViewOrderAdapter(private val context: Context): RecyclerView.Adapter<ViewOrderAdapter.ViewHolder>() {
    var foods = ArrayList<FoodOrders>()
    set(foods){
        if (foods.size > 0){
            this.foods.clear()
        }

        this.foods.addAll(foods)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dialog_food_order, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(foods[position])
    }

    override fun getItemCount(): Int = foods.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val name = view.tv_item_dialog_foodorder_name
        private val amount = view.tv_item_dialog_foodorder_amount
        private val price = view.tv_item_dialog_foodorder_price
        private val total = view.tv_item_dialog_foodorder_total

        fun bindContent(foodOrders: FoodOrders){
//            name.text = foodOrders.food.name
            amount.text = foodOrders.quantity.toString()
//            price.text = foodOrders.food.price.readableNumber()
//            total.text = (foodOrders.food.price * foodOrders.quantity).readableNumber()
        }
    }
}