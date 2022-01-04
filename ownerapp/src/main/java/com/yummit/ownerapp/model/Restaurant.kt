package com.yummit.ownerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant (

    val id:String,
    val name:String,
    val email:String,
    val address:String,
    val description:String,
    val rating:Int,
    val balance:Int,
    val latitude:String,
    val longitude:String,
    val imageUrl:String

//    val foods: ArrayList<FoodOrders>?
    ) : Parcelable {
        constructor() : this("","","","","",0,0,"","","")
}
