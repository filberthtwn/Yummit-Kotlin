package com.yummit.ownerapp.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  Order(
    val orderId: Int = 0,
    val customer:Customer,
    val pickupTime:String,
    val status: String,
    val price: Int,
    val orderType: String,
    val note: String,
    var foods: ArrayList<OrderFood>?
) : Parcelable {
    constructor() : this(0, Customer(), "","", 0, "", "", arrayListOf<OrderFood>())
}