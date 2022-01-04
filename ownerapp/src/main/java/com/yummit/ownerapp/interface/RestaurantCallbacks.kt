package com.yummit.ownerapp.`interface`

import com.yummit.ownerapp.model.Restaurant

interface RestaurantCallbacks {
    fun onSuccess(restaurant: Restaurant)
}