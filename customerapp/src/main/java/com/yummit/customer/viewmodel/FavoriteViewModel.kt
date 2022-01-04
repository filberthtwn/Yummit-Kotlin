package com.yummit.customer.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.network.YummitApi
import org.json.JSONObject

class FoodFavoriteViewModel (application: Application): BaseViewModel(application){
    internal var prefHelper = SharedPreferencesHelper(getApplication())

    val addDone = MutableLiveData<Boolean>()
    val readDone = MutableLiveData<Boolean>()
    val deleteDone = MutableLiveData<Boolean>()

    fun addFavorite(foodId: Int){
        addDone.value = false

        AndroidNetworking.post(YummitApi.addFoodFav())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .addBodyParameter("id_food", foodId.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject) {
                    addDone.value = true
                }

                override fun onError(anError: ANError?) {
                    addDone.value = false
                }
            })
    }

    fun readFavorite(){
        readDone.value = false

        AndroidNetworking.get(YummitApi.getAllFoodFav())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {

                }

                override fun onError(anError: ANError?) {

                }
            })
    }

    fun deleteFavorite(foodId: Int){
        deleteDone.value = false

        AndroidNetworking.delete(YummitApi.deleteFoodFav(foodId.toString()))
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    deleteDone.value = true
                }

                override fun onError(anError: ANError?) {
                    deleteDone.value = false
                }
            })
    }
}