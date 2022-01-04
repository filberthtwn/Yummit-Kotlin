package com.yummit.customer.adapter.pickup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemRestoV3Binding
import com.yummit.customer.fragment.main.PickUpFragmentDirections
import com.yummit.customer.helper.RestoClickListener
import com.yummit.customer.helper.gone
import com.yummit.customer.model.Restaurant
import kotlinx.android.synthetic.main.item_resto_v3.view.*

class NearbyAdapter(val restoList: ArrayList<Restaurant>):
    RecyclerView.Adapter<NearbyAdapter.NearMeViewHolder>(), RestoClickListener {

    fun updateList(list: List<Restaurant>){
        restoList.clear()
        restoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearMeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemRestoV3Binding>(inflater, R.layout.item_resto_v3, parent, false)
        return NearMeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NearMeViewHolder, position: Int) {
        holder.view.resto = restoList[position]
        holder.view.listener = this

        restoList[position].close ?: holder.view.tvItemCountdownV3.gone()
    }

    override fun onRestoClick(v: View) {
        val restoId = v.restoId_restov3.text.toString().toInt()
        val action = PickUpFragmentDirections.actionPickupResto()
        action.restoId = restoId
        Navigation.findNavController(v).navigate(action)
    }

    override fun getItemCount(): Int = Math.min(restoList.size, 5)

    inner class NearMeViewHolder(var view: ItemRestoV3Binding) : RecyclerView.ViewHolder(view.root)
}