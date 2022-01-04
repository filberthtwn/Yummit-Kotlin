package com.yummit.ownerapp.`interface`

import com.yummit.ownerapp.model.Order
import com.yummit.ownerapp.model.OrderFood

interface OrderCallbacks {
    fun onSuccess(orders:ArrayList<Order>)
    fun onFailed()
}