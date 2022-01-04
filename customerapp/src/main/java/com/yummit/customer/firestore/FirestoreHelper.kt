package com.yummit.customer.firestore

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.yummit.customer.model.Order
import com.yummit.customer.model.helper.FoodOrders

fun addFoodOrder(order: Order, foodOrders: List<FoodOrders>) {
    val db = FirebaseFirestore.getInstance()

    val foList = mutableListOf<HashMap<String, Any?>>()
    for (foodOrder in foodOrders) {
        val fo = hashMapOf(
//            "foodName" to foodOrder.food.name,
//            "foodPrice" to foodOrder.food.price,
            "quantity" to foodOrder.quantity,
            "notes" to foodOrder.notes
        )

        foList.add(fo)
    }

    val data = hashMapOf(
        "name" to order.restoName,
        "total" to order.total,
        "type" to order.type,
        "foods" to foList
    )

    db.collection("foodorders")
        .add(data)
        .addOnSuccessListener {
            Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}")
        }
        .addOnFailureListener {
            Log.w(TAG, "Error adding document", it)
        }
}