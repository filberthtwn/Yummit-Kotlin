package com.yummit.customer.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.model.Order
import com.yummit.customer.network.YummitApi
import kotlinx.coroutines.launch
import org.json.JSONObject

class MyOrderViewModel (application: Application): BaseViewModel(application){
    internal var prefHelper = SharedPreferencesHelper(application)
    internal var refreshTime = 1 * 60 * 1000 * 1000 * 1000L
    internal var gson = Gson()

    private var token = ""

    val loading = MutableLiveData<Boolean>()
    val data = MutableLiveData<List<Order>>()
    val loadError = MutableLiveData<Boolean>()

    fun getData(){
        token = prefHelper.getToken()
        checkCacheDuration()

        val updateTime = prefHelper.getUpdateTimeOrder()
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
            val cachePreferenceInt = cachePreference?.toInt() ?: 1 * 60
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException){
            e.printStackTrace()
        }
    }

    private fun fetchFromDatabase(){
        loading.value = true
//        launch {
//            val orders = YummitDatabase(getApplication())
//        }
    }

    private fun fetchFromRemote(){
        loading.value = true
        AndroidNetworking.get(YummitApi.getAllOrders())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {

                }

                override fun onError(anError: ANError?) {

                }
            })
    }

    private fun orderRetrieved(list: List<Order>){

    }

    internal fun storeDataLocally(list: List<Order>){
        launch {

        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }
}