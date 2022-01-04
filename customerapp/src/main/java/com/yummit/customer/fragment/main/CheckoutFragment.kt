package com.yummit.customer.fragment.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.customer.R
import com.yummit.customer.adapter.checkout.CheckoutAdapter
import com.yummit.customer.firestore.addFoodOrder
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.model.Food
import com.yummit.customer.model.Order
import com.yummit.customer.model.Restaurant
import com.yummit.customer.model.helper.FoodOrders
import kotlinx.android.synthetic.main.content_fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_checkout.*

/**
 * A simple [Fragment] subclass.
 */
class CheckoutFragment : Fragment() {
    private lateinit var checkoutAdapter: CheckoutAdapter
    private var foods: List<Food> = emptyList()
    private var orderFoods: List<FoodOrders> = emptyList()
    private lateinit var resto: Restaurant

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            checkoutAdapter = CheckoutAdapter(it)
            checkoutAdapter.addList(orderFoods)
            checkoutAdapter.totals(tv_checkout_totalprice, tv_checkout_totalpayment)

            rv_checkout_orders.adapter = checkoutAdapter
            rv_checkout_orders.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            rv_checkout_orders.setHasFixedSize(true)
        }

        btn_checkout.setOnClickListener {
            val money = SharedPreferencesHelper().getSavedMoney()
            val total = checkoutAdapter.allPrice + 10000
            if (money > total && money > 0) {
//                checkoutPresenter.checkOut()
            } else {
//                val snackbar = Snackbar.make(
//                    findViewById(android.R.id.content),
//                    "Insufficient Balance",
//                    Snackbar.LENGTH_LONG
//                )
//                snackbar.setAction("Top Up") {
//                    startActivity(Intent(this, TopUpPaymentActivity::class.java))
//                    finish()
//                }
//                snackbar.show()
            }
        }

//        FirebaseApp.initializeApp(this)
    }

//    override fun showLoading() {
//        btn_checkout.gone()
//        pb_checkout.visible()
//    }
//
//    override fun moveData(data: CodeResponse) {
//        if (insertToDatabase()){
//            NotificationHelper(this).createNotification("Your Order will arrive in 30 minutes", intent.getStringExtra("name")!! + " is making your food :)")
//            insertToFirebase()
//            minMoney()
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        } else {
//            Toast.makeText(this, "Order failed to save", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun insertToDatabase(): Boolean{
//        val values = ContentValues()
//        values.put(DatabaseContract.OrderColumns.RESTO_NAME, resto.name)
//        values.put(DatabaseContract.OrderColumns.TOTAL, (checkoutAdapter.allPrice + 10000))
//        values.put(DatabaseContract.OrderColumns.TYPE, "pickup")
//
//        val resultId = orderHelper.insert(values)
//        val foodOrders = checkoutAdapter.foods
//
//        for (foodOrder in foodOrders){
//            val fo = ContentValues()
//            fo.put(DatabaseContract.FoodColumns.FOOD_NAME, foodOrder.food.name)
//            fo.put(DatabaseContract.FoodColumns.FOOD_PRICE, foodOrder.food.price)
//            fo.put(DatabaseContract.FoodColumns.ORDER_ID, resultId)
//            fo.put(DatabaseContract.FoodColumns.QUANTITY, foodOrder.quantity)
//            fo.put(DatabaseContract.FoodColumns.NOTES, foodOrder.notes)
//
//            foodOrderHelper.insert(fo)
//        }

        return true
    }

    private fun convertToOrderFoods(foods: List<Food>): List<FoodOrders>{
        val foodOrders = mutableListOf<FoodOrders>()
        for (food in foods){
//            val foodOrder = FoodOrders(0, 1, null)
//            foodOrders.add(foodOrder)
        }
        return foodOrders
    }

    private fun minMoney(){
        val money = SharedPreferencesHelper().getSavedMoney()
        val total = checkoutAdapter.allPrice + 10000

        val save = money - total
        SharedPreferencesHelper().saveMoney(save)
    }

    private fun insertToFirebase() {
        addFoodOrder(
            Order(0, resto.name!!, (checkoutAdapter.allPrice + 10000), "pickup", null),
            checkoutAdapter.foods
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
//                startActivity(Intent(this, RestaurantActivity::class.java).putExtra("resto", resto))
//                finish()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
