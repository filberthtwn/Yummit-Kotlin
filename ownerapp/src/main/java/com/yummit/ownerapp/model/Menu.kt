package com.yummit.ownerapp.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu (
    @PropertyName("id")
    val id: String,
    @PropertyName("name")
    val name: String,
    @PropertyName("price")
    val price: String,
    @PropertyName("category")
    val category: String,
    @PropertyName("description")
    val description: String,
    @PropertyName("is_available")
    val isAvailable: String
) : Parcelable {
    constructor() : this("","", "","", "", "")
}