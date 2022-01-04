package com.yummit.ownerapp.controller

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yummit.ownerapp.RegisterActivity
import org.json.JSONObject
import org.json.JSONArray
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.yummit.ownerapp.AddMenuActivity
import com.yummit.ownerapp.`interface`.FoodCallbacks
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.viewInterface.SuccessInterface
import java.io.File


class FoodController {
    var delegate: AppCompatActivity? = null

    fun createFood(token:String, food:Menu, imageFile: File, callback: SuccessInterface){
        AndroidNetworking.upload("https://yummit.aurigaaristo.com/api/restaurant/createFood")
            .addHeaders("Authorization", "Bearer $token")
            .addMultipartParameter("id_restaurant", "9")
            .addMultipartParameter("name", food.name)
            .addMultipartParameter("price", food.price.replace(Regex("[^0-9]"), ""))
            .addMultipartFile("food_image" , imageFile)
            .addMultipartParameter("description", food.description)
            .addMultipartParameter("is_available", "1")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG,response.toString(2))

                    val delegate = delegate as AddMenuActivity
                    delegate.onSuccess()
                }

                override fun onError(error: ANError) {
                    println(error.message)
                    println("FAILED " + error.errorBody)
                }
            })
    }

    fun getFood(token:String, foodCallbacks: FoodCallbacks){
        AndroidNetworking.get("https://yummit.aurigaaristo.com/api/restaurant/readFoodByRestaurantId")
            .addHeaders("Authorization", "Bearer $token")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val global = Global()
                    global.printResponse(response.toString(2))

                    val menus = ArrayList<Menu>()
                    for (i in 0 until response.getJSONArray("data").length()){
                        val id = response.getJSONArray("data").getJSONObject(i).get("id").toString()
                        val name = response.getJSONArray("data").getJSONObject(i).get("name").toString()
                        val price = response.getJSONArray("data").getJSONObject(i).get("price").toString()
                        val isAvailable = response.getJSONArray("data").getJSONObject(i).get("is_available").toString()
                        val category = response.getJSONArray("data").getJSONObject(i).get("id_food_category").toString()
                        val description = response.getJSONArray("data").getJSONObject(i).get("description").toString()
                        val menu = Menu(id, name, price, category, description, isAvailable)
                        menus.add(menu)
                    }
                    foodCallbacks.onSuccess(menus)
                }

                override fun onError(error: ANError) {
                    println("Error: " + error.errorDetail)
                }
            })
    }

    fun updateFood(token:String, food:Menu, foodCallbacks: FoodCallbacks){
        println("BCA ${food.id}")
        AndroidNetworking.patch("https://yummit.aurigaaristo.com/api/restaurant/updateFoodByFoodId")
            .addHeaders("Authorization", "Bearer $token")
            .addBodyParameter("id_food", food.id)
            .addBodyParameter("name", food.name)
            .addBodyParameter("price", food.price)
            .addBodyParameter("description", food.description)
            .addBodyParameter("is_available", food.isAvailable)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.d(TAG,response.toString(2))
                    foodCallbacks.onSuccess(arrayListOf())
                }

                override fun onError(error: ANError) {
                    println(error.message)
                    println("FAILED " + error.errorBody)
                }
            })
    }

}