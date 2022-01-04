package com.yummit.customer.adapter.pickup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import com.yummit.customer.databinding.ItemVoucherBinding
import com.yummit.customer.helper.VoucherClickListener
import com.yummit.customer.model.Voucher

class VoucherAdapter(val voucherList: ArrayList<Voucher>):
    RecyclerView.Adapter<VoucherAdapter.ViewHolder>(), VoucherClickListener {
    fun updateList(list: List<Voucher>){
        voucherList.clear()
        voucherList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemVoucherBinding>(inflater, R.layout.item_voucher, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.image = voucherList[position]
        holder.view.listener = this
    }

    override fun onVoucherClick(v: View) {

    }

    override fun getItemCount(): Int = voucherList.size

    inner class ViewHolder(var view: ItemVoucherBinding): RecyclerView.ViewHolder(view.root)
}