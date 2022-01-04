package com.yummit.customer.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.network.YummitApi
import org.json.JSONException
import org.json.JSONObject

class LoginViewModel(application: Application): BaseViewModel(application){

    internal var prefHelper = SharedPreferencesHelper(getApplication())

    val loginStatus = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(email: String, password: String){
        loading.value = true

        AndroidNetworking.post(YummitApi.login())
            .addBodyParameter("email", email)
            .addBodyParameter("password", password)
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.value = false

                    try {
                        val success = response.getJSONObject("success")
                        loginStatus.value = true

                        prefHelper.run {
                            saveMoney(success.getInt("balance"))
                            saveProfile(success.getString("name"), email, password, success.getInt("phone_number").toString())
                            saveToken(success.getString("token"))
                        }

                    } catch (e: JSONException){
                        val err = response.getString("error")
                        loginStatus.value = false
                        error.value = err
                    } catch (e: JSONException){
                        loginStatus.value = false
                        error.value = "Can't get data from network. Please try again later!"
                    }
                }

                override fun onError(anError: ANError) {
                    loading.value = false
                    loginStatus.value = false
                    error.value = "Network Failed! Please try again later!"
                    prefHelper.saveLogin("", "")
                }
            })
    }
}

class SignUpViewModel (application: Application): BaseViewModel(application){

    internal var prefHelper = SharedPreferencesHelper(getApplication())

    val signUpStatus = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signUp(name: String, email: String, phone: String, password: String){
        loading.value = true

        AndroidNetworking.post(YummitApi.register())
            .addBodyParameter("name", name)
            .addBodyParameter("email", email)
            .addBodyParameter("password", password)
            .addBodyParameter("c_password", password)
            .addBodyParameter("phone_number", phone)
            .addBodyParameter("balance", "0")
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.value = false

                    try {
                        val success = response.getJSONObject("success")
                        signUpStatus.value = true

                        prefHelper.run {
                            saveProfile(name, email, password, phone)
                            saveToken(success.getString("token"))
                        }

                    } catch (e: JSONException){
                        val err = response.getString("error")
                        signUpStatus.value = false
                        error.value = err
                    } catch (e: JSONException){
                        signUpStatus.value = false
                        error.value = "Can't get data from network. Please try again later!"
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.value = false
                    signUpStatus.value = false
                    error.value = "Network Failed! Please try again later!"
                    prefHelper.saveLogin("", "")
                }
            })
    }
}

class LogoutViewModel (application: Application): BaseViewModel(application){
    internal var prefHelper = SharedPreferencesHelper(getApplication())

    val logoutStatus = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun logout(){
        loading.value = true

        AndroidNetworking.post(YummitApi.logout())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.value = false

                    val message = response.getString("message")

                    if (message == "Successfully logged out"){
                        logoutStatus.value = true
                    } else {
                        loading.value = false
                        error.value = message
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.value = false
                    logoutStatus.value = false
                    error.value = "Network Failed! Please try again later!"
                }
            })
    }
}

class ChangePasswordViewModel (application: Application): BaseViewModel(application){
    internal var prefHelper = SharedPreferencesHelper(getApplication())

    val status = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val mess = MutableLiveData<String>()

    fun changePassword(newPassword: String){
        loading.value = true

        AndroidNetworking.post(YummitApi.logout())
            .addHeaders("Authorization", "Bearer ${prefHelper.getToken()}")
            .addBodyParameter("password", prefHelper.getSavedPassword())
            .addBodyParameter("new_password", newPassword)
            .addBodyParameter("c_new_password", newPassword)
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    loading.value = false

                    val message = response.getString("message")

                    status.value = message == "Password has been changed"
                    mess.value = message
                }

                override fun onError(anError: ANError?) {
                    loading.value = false
                    status.value = false
                    mess.value = "Network Failed! Please try again later!"
                }
            })
    }
}

