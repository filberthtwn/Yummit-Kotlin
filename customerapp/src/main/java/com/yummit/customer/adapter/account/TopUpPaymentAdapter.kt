package com.yummit.customer.adapter.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yummit.customer.R
import kotlinx.android.synthetic.main.item_payment_topup.view.*

class TopUpPaymentAdapter(private val context: Context, private val listener: () -> Unit): RecyclerView.Adapter<TopUpPaymentAdapter.ViewHolder>() {
    private var desc = arrayOf("Transfer Bank", "BCA KlikPay", "BCA Virtual Account")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_payment_topup, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(listener, desc[position])
    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        private val descr = view.tv_payment_topup_detail

        fun bindContent(listener: () -> Unit, de: String){
            descr.text = de

            view.setOnClickListener {
//                var money = context.sharePref().getInt(SharedPreferenceKey.money, 0)
//                money += 10000
//                context.sharePref().edit().putInt(SharedPreferenceKey.money, money).apply()

                listener()
            }
        }
    }
}