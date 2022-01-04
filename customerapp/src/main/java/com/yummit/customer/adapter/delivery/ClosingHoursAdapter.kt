package com.yummit.customer.adapter.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemFoodV1Binding
import com.yummit.customer.helper.FoodClickListener
import com.yummit.customer.helper.gone
import com.yummit.customer.helper.visible
import com.yummit.customer.model.Food

class ClosingHoursAdapter(val foodList: ArrayList<Food>):
    RecyclerView.Adapter<ClosingHoursAdapter.ViewHolder>(), FoodClickListener {
    fun updateList(list: List<Food>){
        foodList.clear()
        foodList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemFoodV1Binding>(inflater, R.layout.item_food_v1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.view.food = food
        if (!food.new) holder.view.imgItemNewV1.gone()
        if (food.endTime != null) holder.view.tvItemCountdownV1.visible()
        if (!food.pricefrom.equals(0)) holder.view.tvItemFoodPricefromV1.visible()
        holder.view.imgItemFoodLikeV1.gone()
        holder.view.listener = this
    }

    override fun onFoodClick(v: View) {

    }

    override fun getItemCount(): Int = Math.min(foodList.size, 4)

    inner class ViewHolder(var view: ItemFoodV1Binding): RecyclerView.ViewHolder(view.root)
}