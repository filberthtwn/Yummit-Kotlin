package com.yummit.ownerapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.ownerapp.adapter.OrderItemAdapter
import com.yummit.ownerapp.model.Order
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.yummit.ownerapp.`interface`.OrderFoodCallbacks
import com.yummit.ownerapp.controller.OrderController
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.global.Status
import com.yummit.ownerapp.model.OrderFood
import com.yummit.ownerapp.viewInterface.SuccessInterface
import kotlinx.android.synthetic.main.activity_order_detail.*


class OrderDetailActivity() : AppCompatActivity() {
//    private lateinit var customer: Customer
    private lateinit var order:Order
    private lateinit var ordersItemAdapter: OrderItemAdapter
    private val orderController = OrderController()
    private lateinit var token:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        setupViews()
    }

    private fun setupViews(){

        /* Get token */
        token = getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("token","").toString()

        /* Hide Action Bar */
        this.supportActionBar?.hide()

        /* Get Order object from OrdersAdapter */
        order = intent.getParcelableExtra("order")

        /* Setup textView based on data */
        tv_order_id.text = "Order #" + String.format("%04d", order.orderId) //For Order id 0000
        tv_total_price.text =  "${(order.price - 10000).readableNumber()}"
        tv_pickup_time.text = "Today at ${order.pickupTime}"
        tv_notes.text = "${order.note}"
        tv_customer_name.text = "${order.customer.name}"
        tv_customer_phone.text = "${order.customer.phone}"


        if (order.orderType == "self-pickup"){
            tv_shipping_title.visibility = View.INVISIBLE
            tv_shipping_amount.visibility = View.INVISIBLE
        }

        cl_bottom.visibility = View.INVISIBLE

        when(order.status){

            getString(R.string.onProgress) -> {
                /* Hide "REJECT" button */
                btn_reject.isEnabled = false
                btn_reject.text = ""

                /* Setup "ACCEPT" button into "FINISH ORDER" */
                btn_accept.setText(getString(R.string.finishOrder))
                btn_accept.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPurple))
            }

            getString(R.string.done),getString(R.string.reject) -> {
                /* Hide "REJECT" button */
                btn_reject.isEnabled = false
                btn_reject.text = ""

                btn_accept.visibility = View.GONE
            }

            else ->{

            }
        }

        /* Close page when back button clicked */
        btn_order_detail_back.setOnClickListener {
            finish()
        }

        btn_call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${order.customer.phone}")
            startActivity(intent)
        }

        btn_reject.setOnClickListener {
            orderController.rejectOrder(token, order.orderId, object :SuccessInterface{
                override fun onSuccess() {
                    finish()
                }

            })
        }

        btn_accept.setOnClickListener {
            when(order.status){
                getString(R.string.onProgress) -> {
                    orderController.finishOrder(token, order.orderId, object :SuccessInterface{
                        override fun onSuccess() {
                            finish()
                        }
                    })
                }

                else -> {
                    orderController.acceptOrder(token, order.orderId, object :SuccessInterface{
                        override fun onSuccess() {
                            finish()
                        }
                    })
                }
            }
        }

        /* Setup Order Item RecycleView */
        setupOrderItemRecycleView()

        setupData()
    }



    private fun setupOrderItemRecycleView(){
        ordersItemAdapter = OrderItemAdapter(this)
        rv_order_item.adapter = ordersItemAdapter
        rv_order_item.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_order_item.setHasFixedSize(true)
        ordersItemAdapter.foodOrders = order.foods!!
    }

    private fun setupData(){

        orderController.getOrderFood(token!!, order.orderId, object :OrderFoodCallbacks{
            override fun onSuccess(orderFoods: ArrayList<OrderFood>) {
                ordersItemAdapter.foodOrders = orderFoods
                ordersItemAdapter.notifyDataSetChanged()

                tv_total_price.text = "Rp ${Global.decimalFormatter(order.price)}"
                cl_bottom.visibility = View.VISIBLE

                /* Hide Progress Bar */
                pb_foodOrder.isActivated = false
                pb_foodOrder.visibility = View.GONE
            }

        })
    }
}
