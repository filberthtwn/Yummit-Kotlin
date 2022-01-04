package com.yummit.customer.model.helper

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class FoodOrders(
    @ColumnInfo(name = "id")
    val foodId: Int,

    @ColumnInfo(name = "name")
    val foodName: String,

    @ColumnInfo(name = "price")
    val foodPrice: Int,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "notes")
    val notes: String?
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}