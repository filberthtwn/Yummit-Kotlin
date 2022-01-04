package com.yummit.customer.model.response

import com.yummit.customer.model.Food
import com.yummit.customer.model.Order
import com.yummit.customer.model.Restaurant

data class RestosResponse(val data: ArrayList<Restaurant>)

data class FoodsResponse(val data: ArrayList<Food>)

data class OrderResponse(val data: ArrayList<Order>)