package com.yummit.ownerapp.viewInterface

interface AuthenticationInterface {
    fun onSuccess(id:String, name:String, email:String, token:String)
}