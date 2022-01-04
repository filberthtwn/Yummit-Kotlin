package com.yummit.customer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yummit.customer.model.Restaurant

@Dao
interface RestaurantDao {
    @Insert
    suspend fun insertAll(vararg restos: Restaurant): List<Long>

    @Query("SELECT * FROM restaurant")
    suspend fun getAllRestaurant(): List<Restaurant>

    @Query("SELECT * FROM restaurant WHERE uuid = :restoId")
    suspend fun getRestaurant(restoId: Int): Restaurant

    @Query("DELETE FROM restaurant")
    suspend fun deleteAllRestaurant()
}