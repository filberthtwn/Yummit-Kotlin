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

class OrderViewModel (application: Application): BaseViewModel(application){
    internal var prefHelper = SharedPreferencesHelper(getApplication())

    val loading = MutableLiveData<Boolean>()
    val id = MutableLiveData<Int>()
    val error = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun createOrder(price: Int, deliveryFee: Int?, pickUpTime: String, type: String, longitude: Double?, latitude: Double?, idVoucher: Int?){
        loading.value = true

        AndroidNetworking.post(YummitApi.createOrder())
            .addBodyParameter("status", 1.toString())
            .addBodyParameter("price", price.toString())
            .addBodyParameter("note", "")
            .addBodyParameter("delivery_fee", deliveryFee.toString())
            .addBodyParameter("order_type", type)
            .addBodyParameter("longitude", longitude.toString())
            .addBodyParameter("latitude", latitude.toString())
            .addBodyParameter("id_voucher", idVoucher.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    id.value = response.getString("id").toInt()
                }

                override fun onError(anError: ANError?) {
                    loading.value = false
                    error.value = "Can't connect to network! Please try again later!"
                }
            })
    }

    fun createFoodOrder(idOrder: Int, idFood: Int, quantity: Int, note: String?){
        AndroidNetworking.post(YummitApi.createOrderFood())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .addBodyParameter("quantity", quantity.toString())
            .addBodyParameter("note", note)
            .addBodyParameter("id_order", idOrder.toString())
            .addBodyParameter("id_food", idFood.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {

                }

                override fun onError(anError: ANError?) {

                }
            })
    }

    fun done(){
        done.value = true
        loading.value = false
    }
}