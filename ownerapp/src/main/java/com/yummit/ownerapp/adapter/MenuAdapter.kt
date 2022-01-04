package com.yummit.ownerapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.yummit.ownerapp.model.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.ownerapp.AddMenuActivity
import com.yummit.ownerapp.OrderDetailActivity
import com.yummit.ownerapp.R
import com.yummit.ownerapp.readableNumber
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter(private val context: Context): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    var menus = ArrayList<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(menus[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddMenuActivity::class.java)
//            /* Passing menu to addMenuActivity activity */
            intent.putExtra("selectedMenu", menus[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = menus.size

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindContent(menu: Menu) {
            view.tv_title.text = menu.name
            view.tv_discount.text = menu.description
            view.tv_menuList_price.text = "${(menu.price.toInt()).readableNumber()}"
//            view.iv_food.setimageurl
        }
    }
}