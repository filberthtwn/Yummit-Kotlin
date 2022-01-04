package com.yummit.customer.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yummit.customer.model.helper.FoodOrders
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Order(
    val orderId: Int,
    val restoName: String,
    val total: Int,
    val type: String,
    val foods: ArrayList<FoodOrders>?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}