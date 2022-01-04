package com.yummit.ownerapp.`interface`

import com.androidnetworking.error.ANError
import com.yummit.ownerapp.model.Offer

interface OfferCallbacks {
    fun onSuccess(offers: ArrayList<Offer>)
    fun onFailed(error: ANError)
}