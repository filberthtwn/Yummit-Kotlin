package com.yummit.customer.model.response

import com.google.gson.annotations.SerializedName

data class CodeResponse(
    @SerializedName("code")
    val code: Int
)