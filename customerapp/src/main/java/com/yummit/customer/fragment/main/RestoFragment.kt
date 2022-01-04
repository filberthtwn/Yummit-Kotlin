package com.yummit.customer.fragment.main


import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yummit.customer.R
import com.yummit.customer.adapter.restaurant.MenuAdapter
import com.yummit.customer.adapter.restaurant.TopMealsAdapter
import com.yummit.customer.database.YummitDatabase
import com.yummit.customer.databinding.FragmentRestoBinding
import com.yummit.customer.helper.gone
import com.yummit.customer.helper.readableNumber
import com.yummit.customer.helper.visible
import com.yummit.customer.model.Food
import com.yummit.customer.model.Restaurant
import com.yummit.customer.model.helper.FoodOrders
import com.yummit.customer.viewmodel.FoodFavoriteViewModel
import com.yummit.customer.viewmodel.RestoDetailsViewModel
import com.yummit.customer.viewmodel.RestoFoodsViewModel
import kotlinx.android.synthetic.main.content_fragment_restaurant.*
import kotlinx.android.synthetic.main.dialog_view_food.view.*
import kotlinx.android.synthetic.main.fragment_resto.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass.
 */
class RestoFragment : Fragment() {
    private var topMealsAdapter = TopMealsAdapter(arrayListOf())

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var restoDetailsViewModel: RestoDetailsViewModel
    private lateinit var restoFoodsViewModel: RestoFoodsViewModel
    private lateinit var foodFavoriteViewModel: FoodFavoriteViewModel
    private lateinit var dataBinding: FragmentRestoBinding
    private lateinit var resto: Restaurant

    private var restoId = 0
    private var orderType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_resto, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tb_fragment_restaurant.setNavigationIcon(R.drawable.ic_arrow_back)
        tb_fragment_restaurant.setNavigationOnClickListener {

        }

        arguments?.let {
            restoId = RestoFragmentArgs.fromBundle(it).restoId
            orderType = RestoFragmentArgs.fromBundle(it).orderType
        }

        restoDetailsViewModel = ViewModelProviders.of(this).get(RestoDetailsViewModel::class.java)
        restoFoodsViewModel = ViewModelProviders.of(this).get(RestoFoodsViewModel::class.java)
        foodFavoriteViewModel = ViewModelProviders.of(this).get(FoodFavoriteViewModel::class.java)

        restoDetailsViewModel.getRestoDetails(restoId)
        restoFoodsViewModel.getRestoFoods(restoId)

        context?.let {
            menuAdapter = MenuAdapter(requireContext(), { //addremove
                foodToCart(it)
                loadCartView()
            }, { //detail
                val dialog = BottomSheetDialog(requireContext())
                val view = layoutInflater.inflate(R.layout.dialog_view_food, null)
                dialog.setContentView(view)

                view.tv_dialog_view_name.text = it.name
                view.tv_dialog_view_price.text = it.price.readableNumber()
                view.tv_dialog_view_pricefrom.text = it.pricefrom.readableNumber()
                view.tv_dialog_view_pricefrom.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                view.tv_dialog_view_desc.text = it.desc

                Glide.with(this).load(it.image).into(view.img_dialog_view)

                val food = it

                view.btn_dialog_view_addtocart.setOnClickListener {
                    foodToCart(food)
                    dialog.dismiss()
                    loadCartView()
                    menuAdapter.ViewHolder(it).checked = checkData(food)
                }

                view.img_dialog_view_close.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.setOnDismissListener {
                    dialog.dismiss()
                }

                dialog.show()
            }, {//favorite

            })
        }

        observeViewModel()

        rv_resto_topmeals.apply {
            adapter = topMealsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        rv_resto_menu.apply {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }

        loadCartView()
        ll_resto_cart.setOnClickListener {
            val action = RestoFragmentDirections.actionRestoCheckout(orderType, resto)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel(){
        restoDetailsViewModel.data.observe(viewLifecycleOwner, Observer {
            dataBinding.includeResto.resto = it
            resto = it
        })

        restoDetailsViewModel.loading.observe(viewLifecycleOwner, Observer {

        })

        restoFoodsViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                menuAdapter.updateList(it)
            }
        })

        restoFoodsViewModel.loading.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun foodToCart(food: Food){ // add and remove
        val foodOrders = FoodOrders(food.id, food.name, food.price, 1, "")

        GlobalScope.launch {
            val data = YummitDatabase(requireContext()).foodOrderDao()
            if (data.checkFood(foodOrders.foodId) != 0){
                data.deleteOrder(foodOrders.foodId)
            } else {
                data.insertData(foodOrders)
            }
        }
    }

    private fun loadCartView(){
        GlobalScope.launch {
            val data = YummitDatabase(requireContext()).foodOrderDao()
            val count = data.totalOrder()
            val price = data.totalPrice()
            if (count == 0){
                ll_resto_cart.gone()
            } else {
                ll_resto_cart.visible()
                tv_cart_total_item.text = if (count == 1){
                    "1 item"
                } else {
                    "$count items"
                }
                tv_cart_total_price.text = price.readableNumber()
            }
        }
    }

    private fun checkData(food: Food): Boolean {
        var ans = false
        runBlocking {
            val data = YummitDatabase(requireContext()).foodOrderDao()
            val check = async { data.checkFood(food.id) }

            ans = check.await() != 0
        }
        return ans
    }
}
