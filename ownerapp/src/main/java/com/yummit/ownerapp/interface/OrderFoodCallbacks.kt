package com.yummit.ownerapp.`interface`

import com.yummit.ownerapp.model.OrderFood

interface OrderFoodCallbacks {
    fun onSuccess(orderFoods:ArrayList<OrderFood>)
}