package com.yummit.customer.adapter.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemRestoV4Binding
import com.yummit.customer.fragment.main.DeliveryFragmentDirections
import com.yummit.customer.helper.RestoClickListener
import com.yummit.customer.helper.visible
import com.yummit.customer.model.Restaurant
import kotlinx.android.synthetic.main.item_resto_v4.view.*

class FastHungriousAdapter(val restoList: ArrayList<Restaurant>):
    RecyclerView.Adapter<FastHungriousAdapter.ViewHolder>(), RestoClickListener {
    fun updateList(list: List<Restaurant>){
        restoList.clear()
        restoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRestoV4Binding>(inflater, R.layout.item_resto_v4, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resto = restoList[position]
        holder.view.resto = resto
        resto.close?.let {
            holder.view.tvItemRestoCountdownV4.visible()
        }
        holder.view.listener = this
    }

    override fun onRestoClick(v: View) {
        val restoId = v.restoId_restov4.text.toString().toInt()
        val action = DeliveryFragmentDirections.actionDeliveryResto()
        action.orderType = "delivery"
        action.restoId = restoId
        Navigation.findNavController(v).navigate(action)
    }

    override fun getItemCount(): Int = restoList.size

    inner class ViewHolder(var view: ItemRestoV4Binding): RecyclerView.ViewHolder(view.root)
}