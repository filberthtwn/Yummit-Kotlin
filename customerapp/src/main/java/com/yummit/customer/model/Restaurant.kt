package com.yummit.customer.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Restaurant(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "distance")
    @SerializedName("distance")
    val distance: String? = null,

    @ColumnInfo(name = "time")
    @SerializedName("time")
    val time: String? = "20 mins",

//    @SerializedName("type")
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val type: String? = null,

    @SerializedName("rating")
    @ColumnInfo(name = "rating")
    val avg: Double = 2.5,

    @SerializedName("tot_rating")
    @ColumnInfo(name = "tot_rating")
    val total: Int = 378,

    @SerializedName("address")
    @ColumnInfo(name = "address")
//    @SerializedName("place")
    val place: String? = null,

    @SerializedName("imageUrl")
    @ColumnInfo(name = "imageUrl")
    val image: String? = "https://aurigaaristo.com/no_image.jpg",

    @SerializedName("closedTime")
    val close: String? = null,

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    val longitude: Double = 0.0,

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    val latitude: Double = 0.0
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}