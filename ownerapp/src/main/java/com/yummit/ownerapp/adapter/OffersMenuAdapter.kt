package com.yummit.ownerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yummit.ownerapp.NewAddMenuOffers
import com.yummit.ownerapp.NewOfferActivity
import com.yummit.ownerapp.R
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.model.Order
import com.yummit.ownerapp.readableNumber
import kotlinx.android.synthetic.main.item_offers_menu.view.*

class OffersMenuAdapter : RecyclerView.Adapter<OffersMenuAdapter.ViewHolder>() {

    lateinit var delegate: AppCompatActivity
    var menus = ArrayList<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_offers_menu,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = menus.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(menus[position], position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindContent(menu: Menu, position:Int) {
            view.tv_title.text = menu.name
            view.tv_original_price.text = "${(menu.price.toInt()).readableNumber()}"
            view.tv_discount_price.visibility = View.GONE
            if (delegate is NewAddMenuOffers) {
                view.btn_delete.visibility = View.GONE
                view.btn_delete.isEnabled = false
                view.btn_selected.setOnClickListener {
                    when (view.btn_selected.text) {
                        view.context.getString(R.string.plus) -> {
                            (delegate as NewAddMenuOffers).didSelectMenu(menu)
                            view.btn_selected.text = "-"
                        }
                        else -> {
                            view.btn_selected.text = "+"
                        }
                    }
                }
            } else {
                /* Disable button selected */
                view.btn_selected.visibility = View.GONE
                view.btn_selected.isEnabled = false

                view.btn_delete.setOnClickListener {
                    (delegate as NewOfferActivity).didDeleteClicked(position)
                }
            }
        }
    }
}
