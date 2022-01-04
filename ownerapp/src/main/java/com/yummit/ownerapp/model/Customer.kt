package com.yummit.ownerapp.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customer (
    val customerId:String,
    @PropertyName("name")
    val name: String,
    @PropertyName("address")
    val address: String,
    @PropertyName("phone")
    val phone: String
) : Parcelable {
    constructor() : this("", "", "", "")
}