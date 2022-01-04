package com.yummit.customer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yummit.customer.model.helper.FoodOrders

@Dao
interface FoodOrderDao {
    @Insert
    suspend fun insertData(foodOrders: FoodOrders): Long

    @Query("SELECT * FROM foodorders")
    suspend fun getAllOrders(): List<FoodOrders>

    @Query("SELECT * FROM foodorders WHERE uuid = :orderId")
    suspend fun getOrder(orderId: Int): FoodOrders

    @Query("DELETE FROM foodorders")
    suspend fun deleteAllOrders()

    @Query("DELETE FROM foodorders WHERE id = :foodId")
    suspend fun deleteOrder(foodId: Int)

    @Query("UPDATE foodorders SET quantity = :quantity WHERE id = :foodId")
    suspend fun setQuantity(foodId: Int, quantity: Int)

    @Query("SELECT COUNT(*) FROM foodorders")
    suspend fun totalOrder(): Int

    @Query("SELECT SUM(quantity * price) AS total FROM foodorders")
    suspend fun totalPrice(): Int

    @Query("SELECT COUNT(*) FROM foodorders WHERE id = :foodId")
    suspend fun checkFood(foodId: Int): Int
}