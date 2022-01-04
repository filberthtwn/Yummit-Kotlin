package com.yummit.customer.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.yummit.customer.database.YummitDatabase
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.model.Restaurant
import com.yummit.customer.model.response.RestosResponse
import com.yummit.customer.network.YummitApi
import kotlinx.coroutines.launch
import org.json.JSONObject

class NearMeViewModel(application: Application): BaseViewModel(application) {

    internal var prefHelper = SharedPreferencesHelper(application)
    internal var refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    internal var gson = Gson()

    private var token = ""

    val loading = MutableLiveData<Boolean>()
    val data = MutableLiveData<List<Restaurant>>()
    val loadError = MutableLiveData<Boolean>()

    fun getData(){
        token = prefHelper.getToken()
        checkCacheDuration()

        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && (System.nanoTime() - updateTime) < refreshTime){
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    fun getDataBypassCache(){
        fetchFromRemote()
    }

    private fun checkCacheDuration(){
        val cachePreference = prefHelper.getCacheDuration()

        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: 5 * 60
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException){
            e.printStackTrace()
        }
    }

    private fun fetchFromDatabase(){
        loading.value = true
        launch {
            val restos = YummitDatabase(getApplication()).restaurantDao().getAllRestaurant()
            restoRetrieved(restos)
            Log.d("resto", "Resto Data from database")
        }
    }

    private fun fetchFromRemote(){
        loading.value = true

        AndroidNetworking.get(YummitApi.getAllRestaurant())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val restoResponse = gson.fromJson(
                        gson.fromJson(response.toString(), JsonElement::class.java),
                        RestosResponse::class.java
                    )

                    val data = restoResponse.data
                    storeDataLocally(data)
                }

                override fun onError(anError: ANError?) {
                    loadError.value = true
                    loading.value = false
                }
            })
    }

    private fun restoRetrieved(list: List<Restaurant>){
        data.value = list
        loadError.value = false
        loading.value = false
    }

    internal fun storeDataLocally(list: List<Restaurant>){
        launch {
            val dao = YummitDatabase(getApplication()).restaurantDao()
            dao.deleteAllRestaurant()
            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            restoRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }
}