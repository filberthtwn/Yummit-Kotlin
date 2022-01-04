package com.yummit.ownerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderFood (
    val orderfoodId: Int,
    val quantity:Int,
    val note:String,
    val menu:Menu
//    val status: Int,
//    val price: Int,
//    val orderType: String,
//    val note: String,
//    val foods: ArrayList<FoodOrders>?
    ) : Parcelable {
        constructor() : this(0, 0, "", Menu())
}