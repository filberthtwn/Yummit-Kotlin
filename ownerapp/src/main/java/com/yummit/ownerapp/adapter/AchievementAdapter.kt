package com.yummit.ownerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.ownerapp.R
import com.yummit.ownerapp.model.Order

class AchievementAdapter(private val context: Context): RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
        ): AchievementAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false))
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: AchievementAdapter.ViewHolder, position: Int) {

    }

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        fun bindContent(order: Order){
//            view.tv_menuList_title.text = order.customer!!.name
//            view.tv_menuList_description.text = "${order.foods?.size} item"
//            view.tv_menuList_price.text = "${(order.total - 10000).readableNumber()}"
        }
    }

}