package com.yummit.customer.fragment.main.home.order


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yummit.customer.R
import com.yummit.customer.adapter.myorder.OnProcessOrdersAdapter
import com.yummit.customer.adapter.myorder.ViewOrderAdapter
import com.yummit.customer.helper.randomizeID
import com.yummit.customer.helper.readableNumber
import com.yummit.customer.model.Order
import kotlinx.android.synthetic.main.dialog_view_order.view.*
import kotlinx.android.synthetic.main.fragment_on_process.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class OnProcessFragment : Fragment() {
    private lateinit var onProcessOrdersAdapter: OnProcessOrdersAdapter
//    private lateinit var orderHelper: OrderHelper
//    private lateinit var foodOrderHelper: FoodOrderHelper
    private lateinit var viewOrderAdapter: ViewOrderAdapter

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_on_process, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onProcessOrdersAdapter = OnProcessOrdersAdapter(context!!){
            val order = it

            GlobalScope.launch(Dispatchers.Main) {
                val dialog = BottomSheetDialog(context!!)
                val view = layoutInflater.inflate(R.layout.dialog_view_order, null)
                dialog.setContentView(view)

                viewOrderAdapter = ViewOrderAdapter(context!!)
                view.rv_dialog_view_order_foods.adapter = viewOrderAdapter
                view.rv_dialog_view_order_foods.setHasFixedSize(true)
                view.rv_dialog_view_order_foods.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

                view.tv_dialog_view_order_id.text = order.orderId.randomizeID()
                view.tv_dialog_view_order_price.text = (order.total - 10000).readableNumber()
                view.tv_dialog_view_order_deliveryfee.text = context!!.resources.getString(R.string.rp_10_000)
                view.tv_dialog_view_order_total.text = order.total.readableNumber()

//                val skeletonScreen = Skeleton.bind(view.rv_dialog_view_order_foods)
//                    .adapter(viewOrderAdapter)
//                    .load(R.layout.item_dialog_food_order)
//                    .count(3)
//                    .show()

                val deferredFood = async(Dispatchers.IO){
//                    val cursor = foodOrderHelper.queryById(order.orderId.toString())
//                    MappingHelper.mapCursorToArrayListFood(cursor)
                }

                val foods = deferredFood.await()

//                viewOrderAdapter.foods = foods
//                skeletonScreen.hide()

                view.img_dialog_view_order_close.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.setOnDismissListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
        rv_myorder_onprocess.adapter = onProcessOrdersAdapter
        rv_myorder_onprocess.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_myorder_onprocess.setHasFixedSize(true)

//        orderHelper = OrderHelper.getInstance(context!!)
//        orderHelper.open()
//        foodOrderHelper = FoodOrderHelper(context!!)
//        foodOrderHelper.open()

        if (savedInstanceState == null){
            loadOrderAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Order>(EXTRA_STATE)
            if (list != null){
                onProcessOrdersAdapter.orders = list
            }
        }
    }

    private fun loadOrderAsync(){
//        GlobalScope.launch(Dispatchers.Main) {
//            pb_order_onprocess.visible()
//            rv_myorder_onprocess.invisible()
//
//            val deferredOrder = async(Dispatchers.IO) {
//                val cursor = orderHelper.queryAll()
//                MappingHelper.mapCursorToArrayListOrder(cursor)
//            }
//
//            val orders = deferredOrder.await()
//
//            pb_order_onprocess.gone()
//            if (orders.size > 0){
//                onProcessOrdersAdapter.orders = orders
//
//                rv_myorder_onprocess.visible()
//            } else {
//                onProcessOrdersAdapter.orders = ArrayList()
//                Snackbar.make(rv_myorder_onprocess, "No Orders", Snackbar.LENGTH_SHORT).show()
//                tv_order_onprocess_nodata.visible()
//                rv_myorder_onprocess.invisible()
//            }
//        }
    }
}
