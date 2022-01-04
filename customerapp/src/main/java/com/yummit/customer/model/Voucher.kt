package com.yummit.customer.model

import com.google.gson.annotations.SerializedName

data class Voucher(
    @SerializedName("imageUrl")
    val image: String
)