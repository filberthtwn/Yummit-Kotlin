package com.yummit.customer.adapter.checkout

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yummit.customer.R
import com.yummit.customer.helper.readableNumber
import com.yummit.customer.model.helper.FoodOrders
import kotlinx.android.synthetic.main.dialog_notes.view.*
import kotlinx.android.synthetic.main.item_checkout.view.*

class CheckoutAdapter(private val context: Context): RecyclerView.Adapter<CheckoutAdapter.ViewHolder>(){
    var allPrice = 0

    private var totalPrice: TextView? = null
    private var totalPayment: TextView? = null

    var foods: List<FoodOrders> = emptyList()
    fun addList(list: List<FoodOrders>){
        this.foods = list
        notifyDataSetChanged()
    }

    fun totals(price: TextView, payment: TextView){
        totalPrice = price
        totalPayment = payment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_checkout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindContent(foods[position], position)
    }

    override fun getItemCount(): Int = foods.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val image = view.img_item_checkout
        private val name = view.tv_item_checkout_name
        private val pri = view.tv_item_checkout_price
        private val add = view.img_checkout_add
        private val amount = view.tv_checkout_amount
        private val remove = view.img_checkout_remove
        private val notes = view.tv_checkout_notes

        private var count = 1
        private var note = ""

        fun bindContent(foodOrders: FoodOrders, pos: Int){
            val price = 0

            Glide.with(context).load(R.drawable.bg_food_example_3).into(image)
            name.text = ""
            pri.text = "0"
            amount.text = count.toString()

            allPrice += price
            reloadPrice()

            add.setOnClickListener {
                count++
                allPrice += price
                reloadAmount(count, pos)
                reloadPrice()
            }

            remove.setOnClickListener {
                if (count > 1) {
                    count--
                    allPrice -= price
                }
                reloadAmount(count, pos)
                reloadPrice()
            }

            notes.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_notes, null, false)

                if (note != "")
                    dialogView.edt_dialog_notes.setText(note)

                builder.setView(dialogView)
                builder.setTitle("Notes")
                builder.setPositiveButton("Save"
                ) { _, _ ->
                    note = dialogView.edt_dialog_notes.text.toString()
                    reloadNotes(note, pos)
                }
                builder.setNegativeButton("Cancel"){
                        dialog, _ ->
                    dialog.dismiss()
                }
                builder.create().show()
            }
        }

        private fun reloadAmount(am: Int, pos: Int){
            amount.text = am.toString()

//            foods[pos].quantity = am
        }

        private fun reloadNotes(note: String, pos: Int){
//            foods[pos].notes = note

            if (note == ""){
                notes.text = "Add Notes"
            } else {
                notes.text = "Edit Notes"
            }
        }

        private fun reloadPrice(){
            totalPrice?.text = allPrice.readableNumber()
            totalPayment?.text = (allPrice + 10000).readableNumber()
        }
    }
}