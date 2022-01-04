package com.yummit.ownerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer (
    val id:Int,
    val title:String,
    val discount:Int,
    val startTime:String,
    val endTime:String,
    val status:Int
): Parcelable {
    constructor() : this(-1, "", 0, "", "", 0)
}
