package com.yummit.customer.adapter.restaurant

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

class TopMealsAdapter(val foodList: ArrayList<Food>):
    RecyclerView.Adapter<TopMealsAdapter.ViewHolder>(), FoodClickListener{
    fun updateList(list: List<Food>){
        foodList.clear()
        foodList.addAll(list)
        notifyDataSetChanged()
    }

    companion object {
        lateinit var listener: (Food) -> Unit
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
        if (!food.pricefrom.equals(0)) holder.view.tvItemFoodPricefromV1.visible()
        holder.view.listener = this
    }

    override fun onFoodClick(v: View) {

    }

    override fun getItemCount(): Int = foodList.size

    inner class ViewHolder(var view: ItemFoodV1Binding): RecyclerView.ViewHolder(view.root)
}