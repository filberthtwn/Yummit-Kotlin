package com.yummit.customer.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.yummit.customer.model.Food
import com.yummit.customer.model.Restaurant
import com.yummit.customer.model.response.FoodsResponse
import com.yummit.customer.model.response.RestosResponse
import com.yummit.customer.network.YummitApi
import org.json.JSONObject

class RestoDetailsViewModel(application: Application): BaseViewModel(application) {
    val loading = MutableLiveData<Boolean>()
    val data = MutableLiveData<Restaurant>()
    val gson = Gson()

    fun getRestoDetails(restoId: Int){
        loading.value = true

        AndroidNetworking.get(YummitApi.getRestaurantDetails(restoId))
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.value = false
                    val restoResponse = gson.fromJson(
                        gson.fromJson(response.toString(), JsonElement::class.java),
                        RestosResponse::class.java
                    )

                    data.value = restoResponse.data[0]
                }

                override fun onError(anError: ANError?) {
                    getRestoDetails(restoId)
                }
            })
    }
}

class RestoFoodsViewModel(application: Application): BaseViewModel(application){
    val loading = MutableLiveData<Boolean>()
    val data = MutableLiveData<List<Food>>()
    val gson = Gson()

    fun getRestoFoods(restoId: Int){
        loading.value = true

        AndroidNetworking.get(YummitApi.getRestoFoods(restoId))
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.value = false
                    val foodResponse = gson.fromJson(
                        gson.fromJson(response.toString(), JsonElement::class.java),
                        FoodsResponse::class.java
                    )

                    data.value = foodResponse.data
                    return
                }

                override fun onError(anError: ANError?) {
                    getRestoFoods(restoId)
                }
            })
    }
}