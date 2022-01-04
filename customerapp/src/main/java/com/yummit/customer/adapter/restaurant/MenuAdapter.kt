package com.yummit.customer.adapter.restaurant

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yummit.customer.R
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.helper.readableNumber
import com.yummit.customer.model.Food
import kotlinx.android.synthetic.main.item_food_v2.view.*

class MenuAdapter(internal val context: Context,
                  internal val addRemove: (Food) -> Unit,
                  internal val detail: (Food) -> Unit,
                  internal val favorite: (Food) -> Unit)
    : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    internal var prefHelper = SharedPreferencesHelper(context)

    var foods = ArrayList<Food>()
    fun updateList(list: List<Food>){
        foods.clear()
        foods.addAll(list)
        notifyDataSetChanged()
    }

    private val code = arrayOf(1, 2, 3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_food_v2, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(
            foods[position],
            addRemove,
            detail,
            favorite
        )
    }

    override fun getItemCount(): Int = Math.min(3, foods.size)

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val name = view.tv_item_food_name_v2
        private val desc = view.tv_item_food_desc_v2
        private val image = view.img_item_foodv2
        private val price = view.tv_item_food_price_v2
        private val pricefrom = view.tv_item_food_pricefrom_v2
        private val add = view.btn_item_food_add_v2
        private val like = view.img_item_food_like_v2

        var checked = false
        var love = false

        fun bindContent(
            food: Food,
            addRemove: (Food) -> Unit,
            detail: (Food) -> Unit,
            favorite: (Food) -> Unit
        ) {
            name.text = food.name
            desc.text = food.desc
            price.text = food.price.readableNumber()
            pricefrom.text = food.pricefrom.readableNumber()
            pricefrom.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            Glide.with(context).load(food.image).into(image)

            if (!love){
                Glide.with(context).load(R.drawable.ic_heart_outline).into(like)
            } else {
                Glide.with(context).load(R.drawable.ic_heart).into(like)
            }

            add.setOnClickListener {
                addRemove(food)

                if (!checked){
                    add.text = context.resources.getString(R.string.remove)
                    add.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    add.background = ContextCompat.getDrawable(context, R.drawable.btn_orange)
                } else {
                    add.text = context.resources.getString(R.string.add)
                    add.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    add.background = ContextCompat.getDrawable(context, R.drawable.btn_orange_outline)
                }
                add.setPadding(48, 12, 48, 12)
                checked = !checked
            }

            like.setOnClickListener {
                favorite(food)

                if (love){
                    Glide.with(context).load(R.drawable.ic_heart_outline).into(like)
                } else {
                    Glide.with(context).load(R.drawable.ic_heart).into(like)
                }
                love = !love
            }

            view.setOnClickListener {
                detail(food)
            }
        }
    }
}