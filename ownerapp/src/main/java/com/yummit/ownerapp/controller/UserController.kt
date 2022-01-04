package com.yummit.ownerapp.controller

import android.content.ContentValues.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidnetworking.error.ANError
import org.json.JSONObject
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.google.gson.JsonObject
import com.yummit.ownerapp.LoginActivity
import com.yummit.ownerapp.RegisterActivity
import com.yummit.ownerapp.`interface`.RestaurantCallbacks
import com.yummit.ownerapp.`interface`.UserCallbacks
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Restaurant
import java.io.File


class UserController {

    companion object {


        var delegate: AppCompatActivity? = null
        var delegateFragment: Fragment? = null

        fun userLogin(username: String, password: String, callbacks: UserCallbacks) {
            Log.d(TAG, "User Login")
            AndroidNetworking.post("${Global.address}/loginRestaurant")
                .addBodyParameter("email", username)
                .addBodyParameter("password", password)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d(TAG, response.toString(2))

                        val id = response.getJSONObject("success").get("id").toString()
                        val token = response.getJSONObject("success").get("token").toString()
                        val delegate = delegate as LoginActivity

                        getRestaurantDetails(token, object : RestaurantCallbacks {
                            override fun onSuccess(restaurant: Restaurant) {
                                callbacks.onLoggedIn(token, restaurant)
                            }

                        })
                    }

                    override fun onError(error: ANError) {
                        val errorMessage = JSONObject(error.errorBody).get("error").toString()
                        callbacks.onFailed(errorMessage)
                    }
                })
        }

        fun createRestaurant(
            name: String,
            email: String,
            password: String,
            address: String,
            imageFile: File,
            callbacks: UserCallbacks
        ) {
            var imageFileObj:File? = null
            if(imageFile.exists()){
                imageFileObj = imageFile
            }

            AndroidNetworking.upload("${Global.address}/registerRestaurant")
                .addMultipartFile("restaurant_image", imageFileObj)
                .addMultipartParameter("name", name)
                .addMultipartParameter("email", email)
                .addMultipartParameter("password", password)
                .addMultipartParameter("address", address)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d(TAG, response.toString(2))
                        val token = response.getJSONObject("success").get("token")

                        callbacks.onRegistered(token.toString())
                    }

                    override fun onError(error: ANError) {
                        Log.w(TAG, error.errorBody)
                    }
                })
        }

        fun getRestaurantDetails(token: String, restaurantCallbacks: RestaurantCallbacks) {
            AndroidNetworking.post("https://yummit.aurigaaristo.com/api/restaurant/detailsRestaurant")
                .addHeaders("Authorization", "Bearer $token")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d(TAG, response.toString(2))
                        val id = response.getJSONObject("success").get("id").toString()
                        val name = response.getJSONObject("success").get("name").toString()
                        val email = response.getJSONObject("success").get("email").toString()
                        val address = response.getJSONObject("success").get("address").toString()
                        val description =
                            response.getJSONObject("success").get("description").toString()
                        val rating =
                            response.getJSONObject("success").get("rating").toString().toInt()
                        val balance =
                            response.getJSONObject("success").get("balance").toString().toInt()
                        val latitude = response.getJSONObject("success").get("latitude").toString()
                        val longitude =
                            response.getJSONObject("success").get("longitude").toString()
                        val imageUrl =
                            response.getJSONObject("success").get("restaurant_image").toString()

                        val restaurant = Restaurant(
                            id,
                            name,
                            email,
                            address,
                            description,
                            rating,
                            balance,
                            latitude,
                            longitude,
                            imageUrl
                        )
                        restaurantCallbacks.onSuccess(restaurant)
                    }

                    override fun onError(error: ANError) {
                        println("FAILED " + error.errorDetail)
                    }
                })
        }
    }
}