package com.yummit.ownerapp.controller

import android.content.ContentValues.TAG
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yummit.ownerapp.`interface`.OrderCallbacks
import com.yummit.ownerapp.`interface`.OrderFoodCallbacks
import com.yummit.ownerapp.model.Customer
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.model.Order
import com.yummit.ownerapp.model.OrderFood
import com.yummit.ownerapp.viewInterface.SuccessInterface
import org.json.JSONObject
import java.text.SimpleDateFormat

class OrderController {
    val delegate: AppCompatActivity? = null

    fun getOrders(token: String, orderCallbacks: OrderCallbacks) {

        AndroidNetworking.get("https://yummit.aurigaaristo.com/api/restaurant/restaurantGetAllOrderByRestaurantId")
            .addHeaders("Authorization", "Bearer " + token)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString(2))
                    val orders = ArrayList<Order>()
                    for (i in 0 until response.getJSONArray("data").length()) {
                        val data = response.getJSONArray("data").getJSONObject(i)
                        val id = data.get("id").toString().toInt()
                        val tempPickupTime = data.get("pickup_time").toString()
                        var dateFormatter = SimpleDateFormat("HH:mm:ss")
                        val date = dateFormatter.parse(tempPickupTime)

                        dateFormatter = SimpleDateFormat("HH:mm")
                        val pickupTime = dateFormatter.format(date!!)

                        val status = data.get("status").toString()
                        val price = data.get("price").toString().toInt()
                        val orderType = data.get("order_type").toString()
                        val note = data.get("note").toString()

                        val customerId = data.get("id_user").toString()
                        val name = data.get("name").toString()
                        val phoneNumber = data.get("phone_number").toString()
                        val customerObj = Customer(customerId, name, "", phoneNumber)

                        val order = Order(
                            id,
                            customerObj,
                            pickupTime,
                            status,
                            price,
                            orderType,
                            note,
                            arrayListOf<OrderFood>()
                        )
                        orders.add(order)
                    }
                    orderCallbacks.onSuccess(orders)
                }

                override fun onError(error: ANError) {
                    orderCallbacks.onFailed()
                    println("Error: " + error.errorCode)
                }
            })
    }

    fun getOrderFood(token: String, orderId: Int, orderFoodCallbacks: OrderFoodCallbacks) {
        AndroidNetworking.get("https://yummit.aurigaaristo.com/api/restaurant/readAllOrderFoodByOrderId")
            .addQueryParameter("order_id", orderId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString(2))
                    val orderFoods = ArrayList<OrderFood>()
                    for (i in 0 until response.getJSONArray("data").length()) {
                        print(response)
                        val data = response.getJSONArray("data").getJSONObject(i)
                        val id = data.get("id").toString().toInt()
                        val quantity = data.get("quantity").toString().toInt()
                        val note = data.get("note").toString()

                        // Setup Food Object
                        val foodName = data.get("name").toString()
                        val foodPrice = data.get("price").toString()
                        val foodDescription = data.get("description").toString()
                        val foodAvailability = data.get("is_available").toString()
                        val food = Menu("-1", foodName, foodPrice, "0", foodDescription, foodAvailability)

                        val orderFood = OrderFood(id, quantity, note, food)
                        orderFoods.add(orderFood)
                    }
                    orderFoodCallbacks.onSuccess(orderFoods)
                }

                override fun onError(error: ANError) {
                    println("Error: " + error.errorCode)
                }
            })
    }

    fun acceptOrder(token: String, orderId: Int, successInterface: SuccessInterface) {
        AndroidNetworking.patch("https://yummit.aurigaaristo.com/api/restaurant/acceptOrderByOrderId")
            .addHeaders("Authorization", "Bearer ${token}")
            .addQueryParameter("id_order", orderId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString(2))
                    successInterface.onSuccess()
                }

                override fun onError(error: ANError) {
                    println("Error: " + error.errorCode)
                }

            })
    }

    fun rejectOrder(token: String, orderId: Int, successInterface: SuccessInterface) {
        AndroidNetworking.patch("https://yummit.aurigaaristo.com/api/restaurant/rejectOrderByOrderId")
            .addHeaders("Authorization", "Bearer ${token}")
            .addQueryParameter("id_order", orderId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString(2))
                    successInterface.onSuccess()
                }

                override fun onError(error: ANError) {
                    println("Error: " + error.errorCode)
                }

            })
    }


    fun finishOrder(token: String, orderId: Int, successInterface: SuccessInterface) {
        AndroidNetworking.patch("https://yummit.aurigaaristo.com/api/restaurant/doneOrderByOrderId")
            .addHeaders("Authorization", "Bearer ${token}")
            .addQueryParameter("id_order", orderId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG, response.toString(2))
                    successInterface.onSuccess()
                }

                override fun onError(error: ANError) {
                    println("Error: " + error.errorCode)
                }

            })
    }
}