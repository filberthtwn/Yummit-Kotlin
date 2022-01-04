package com.yummit.customer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yummit.customer.model.Restaurant
import com.yummit.customer.model.helper.FoodOrders

@Database(entities = arrayOf(FoodOrders::class, Restaurant::class), version = 1, exportSchema = false)
abstract class YummitDatabase: RoomDatabase(){
    abstract fun foodOrderDao(): FoodOrderDao
    abstract fun restaurantDao() : RestaurantDao

    companion object {
        @Volatile
        internal var instance: YummitDatabase? = null
        internal var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            YummitDatabase::class.java,
            "dbyummitcus"
        ).build()
    }
}