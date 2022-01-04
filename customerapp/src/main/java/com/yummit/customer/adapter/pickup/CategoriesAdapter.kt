package com.yummit.customer.adapter.pickup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemCategoriesBinding

class CategoriesAdapter(private val context: Context): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private val titleList = arrayOf("Pizza", "Burgers", "Traditional")
    private val countList = arrayOf(2350, 350, 150)
    private val imageList = arrayOf(R.drawable.ic_pizza, R.drawable.ic_hamburger, R.drawable.ic_spagetti)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoriesBinding>(inflater, R.layout.item_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.name = titleList[position]
        holder.view.picture = imageList[position]
        holder.view.total = "${countList[position]} places"
    }

    override fun getItemCount(): Int = titleList.size

    inner class ViewHolder(var view: ItemCategoriesBinding): RecyclerView.ViewHolder(view.root)
}