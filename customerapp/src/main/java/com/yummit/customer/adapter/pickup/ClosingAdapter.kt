package com.yummit.customer.adapter.pickup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemRestoV2Binding
import com.yummit.customer.fragment.main.PickUpFragmentDirections
import com.yummit.customer.helper.RestoClickListener
import com.yummit.customer.model.Restaurant
import kotlinx.android.synthetic.main.item_resto_v2.view.*

class ClosingAdapter(val restoList: ArrayList<Restaurant>):
    RecyclerView.Adapter<ClosingAdapter.ViewHolder>(), RestoClickListener {

    fun updateList(list: List<Restaurant>){
        restoList.clear()
        restoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRestoV2Binding>(inflater, R.layout.item_resto_v2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.resto = restoList[position]
        holder.view.listener = this
    }

    override fun onRestoClick(v: View) {
        val restoId = v.restoId_restov2.text.toString().toInt()
        val action = PickUpFragmentDirections.actionPickupResto()
        action.orderType = "self-pickup"
        action.restoId = restoId
        Navigation.findNavController(v).navigate(action)
    }

    override fun getItemCount(): Int = Math.min(restoList.size, 3)

    inner class ViewHolder(var view: ItemRestoV2Binding): RecyclerView.ViewHolder(view.root)
}