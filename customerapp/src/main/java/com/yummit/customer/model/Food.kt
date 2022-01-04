package com.yummit.customer.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Food (
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("pricefrom")
    val pricefrom: Int = 0,

    @SerializedName("description")
    val desc: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("endtime")
    val endTime: String? = null,

    @SerializedName("new")
    val new: Boolean = false,

    @SerializedName("is_available")
    val available: Boolean = true,

    @SerializedName("image")
    val image: String = "https://aurigaaristo.com/no_image.jpg"
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}