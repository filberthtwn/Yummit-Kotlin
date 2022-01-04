package com.yummit.ownerapp.`interface`

import com.yummit.ownerapp.model.Restaurant

interface UserCallbacks {

    fun onLoggedIn(token:String, restaurant: Restaurant){

    }
    fun onRegistered(token:String){

    }

    fun onFailed(message:String){

    }
}