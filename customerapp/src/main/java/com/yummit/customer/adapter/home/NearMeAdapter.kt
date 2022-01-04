package com.yummit.customer.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemRestoV1Binding
import com.yummit.customer.fragment.main.HomeFragmentDirections
import com.yummit.customer.helper.RestoClickListener
import com.yummit.customer.model.Restaurant
import kotlinx.android.synthetic.main.item_resto_v1.view.*

class NearMeAdapter(val restoList: ArrayList<Restaurant>) :
    RecyclerView.Adapter<NearMeAdapter.NearMeViewHolder>(), RestoClickListener {

    fun updateList(list: List<Restaurant>){
        restoList.clear()
        restoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearMeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRestoV1Binding>(inflater, R.layout.item_resto_v1, parent, false)
        return NearMeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NearMeViewHolder, position: Int) {
        holder.view.resto = restoList[position]
        holder.view.listener = this
    }

    override fun onRestoClick(v: View) {
        val restoId = v.restoId_restov1.text.toString().toInt()
        val action = HomeFragmentDirections.actionHomeResto()
        action.orderType = "delivery"
        action.restoId = restoId
        Navigation.findNavController(v).navigate(action)
    }

    override fun getItemCount(): Int = Math.min(restoList.size, 4)

    inner class NearMeViewHolder(var view: ItemRestoV1Binding) : RecyclerView.ViewHolder(view.root)
}