package com.yummit.customer.network

import com.yummit.customer.BuildConfig

object YummitApi {
    private const val address = BuildConfig.BASE_URL

    //--------------------------------------------------------------------------
    //authentication
    fun login(): String{
        return "$address/login"
    }

    fun register(): String{
        return "$address/register"
    }

    fun logout(): String {
        return "$address/logout"
    }

    fun changePassword(): String {
        return "$address/changePassword"
    }

    //--------------------------------------------------------------------------
    //restaurant
    fun getAllRestaurant(): String {
        return "$address/readAllRestaurant"
    }

    fun getRestaurantDetails(idResto: Int): String {
        return "$address/readRestaurantDetailByRestaurantId?id_restaurant=$idResto"
    }

    //--------------------------------------------------------------------------
    //food
    fun getRestoFoods(idResto: Int): String{
        return "$address/readFoodByRestaurantId?id_restaurant=$idResto"
    }

    //--------------------------------------------------------------------------
    //order
    fun createOrder(): String {
        return "$address/createOrder"
    }

    fun createOrderFood(): String {
        return "$address/createOrderFood"
    }

    fun getAllOrders(): String {
        return "$address/readAllOrderByUserId"
    }

    //--------------------------------------------------------------------------
    //favorite
    fun addRestoFav(): String {
        return "$address/createFavoriteRestaurant"
    }

    fun getAllRestoFav(): String {
        return "$address/readFavoriteRestaurantByUserId"
    }

    fun deleteRestoFav(idResto: String): String {
        return "$address/deleteFavoriteFoodById/$idResto"
    }

    fun addFoodFav(): String {
        return "$address/createFavoriteFood"
    }

    fun getAllFoodFav(): String {
        return "$address/readFavoriteFoodByUserId"
    }

    fun deleteFoodFav(idFood: String): String {
        return "$address/deleteFavoriteFoodById/$idFood"
    }
}