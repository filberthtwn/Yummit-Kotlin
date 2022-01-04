package com.yummit.customer.helper

import android.view.View

interface FoodClickListener {
    fun onFoodClick(v: View)
}

interface AddRemoveFoodClickListener {
    fun onAddRemove(v: View)
}

interface FavoriteFoodClickListener {
    fun onFavorite(v: View)
}