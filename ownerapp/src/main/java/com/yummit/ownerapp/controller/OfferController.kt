package com.yummit.ownerapp.controller

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yummit.ownerapp.AddMenuActivity
import com.yummit.ownerapp.`interface`.OfferCallbacks
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.model.Offer
import org.json.JSONObject

class OfferController {
    fun createOffer(token:String, foodsId:String, title:String, discount:Int, startTime:String, endTime:String, offerCallbacks: OfferCallbacks) {
        println(foodsId)
        AndroidNetworking.post("https://yummit.aurigaaristo.com/api/restaurant/createDiscount")
            .addHeaders("Authorization", "Bearer $token")
            .addBodyParameter("id_foods", "$foodsId")
            .addBodyParameter("name", "$title")
            .addBodyParameter("discount", "$discount")
            .addBodyParameter("start_time", "$startTime")
            .addBodyParameter("end_time", "$endTime")
            .addBodyParameter("is_active", "1")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(ContentValues.TAG, response.toString(2))
                    offerCallbacks.onSuccess(arrayListOf())
                }

                override fun onError(error: ANError) {
                    offerCallbacks.onFailed(error)
                }
            })
    }

    fun readOffer(token:String, resturantId:Int, offerCallbacks: OfferCallbacks){
        AndroidNetworking.get("${Global.address}/readDiscountByRestaurantId")
            .addHeaders("Authorization", "Bearer $token")
            .addQueryParameter("id_restaurant", "$resturantId")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val global = Global()
                    global.printResponse(response.toString(2))
                    val offers = ArrayList<Offer>()
                    for (i in 0 until response.getJSONArray("data").length()){
                        val id = response.getJSONArray("data").getJSONObject(i).get("id").toString().toInt()
                        val title = response.getJSONArray("data").getJSONObject(i).get("name").toString()
                        val discount = response.getJSONArray("data").getJSONObject(i).get("discount").toString().toInt()
                        val startTime = response.getJSONArray("data").getJSONObject(i).get("start_time").toString()
                        val endTime = response.getJSONArray("data").getJSONObject(i).get("end_time").toString()
                        val status = response.getJSONArray("data").getJSONObject(i).get("is_active").toString().toInt()
                        val offer = Offer(id, title, discount, startTime, endTime, status)
                        offers.add(offer)
                    }
                    offerCallbacks.onSuccess(offers)
                }

                override fun onError(error: ANError) {
                    offerCallbacks.onFailed(error)
                }
            })
    }

//    fun updateOffer(token:String,foodId:String, ){
//
//    }
}