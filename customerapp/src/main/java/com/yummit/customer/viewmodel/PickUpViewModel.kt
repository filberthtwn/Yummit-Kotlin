package com.yummit.customer.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.yummit.customer.model.Restaurant
import com.yummit.customer.model.Voucher
import com.yummit.customer.model.response.RestosResponse
import com.yummit.customer.network.YummitApi
import org.json.JSONObject

class PickUpVoucherViewModel(application: Application): BaseViewModel(application){
    val loading = MutableLiveData<Boolean>()
    val data = MutableLiveData<List<Voucher>>()

    fun getPickUpVoucher(){
        loading.value = true
//        disposable.add(
//            apiService.getPickupVoucher()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<List<Voucher>>(){
//                    override fun onSuccess(t: List<Voucher>) {
//                        data.value = t
//                        loading.value = false
//                    }
//
//                    override fun onError(e: Throwable) {
//
//                    }
//                })
//        )
    }
}

class ClosingHoursRestoViewModel(application: Application): BaseViewModel(application){
    val loading = MutableLiveData<Boolean>()
    val data = MutableLiveData<List<Restaurant>>()
    val gson = Gson()

    fun getClosingHoursResto(){
        loading.value = true

        AndroidNetworking.get(YummitApi.getAllRestaurant())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val restoResponse = gson.fromJson(
                        gson.fromJson(response.toString(), JsonElement::class.java),
                        RestosResponse::class.java
                    )

                    data.value = restoResponse.data
                }

                override fun onError(anError: ANError?) {
                    loading.value = false
                }
            })
    }
}

