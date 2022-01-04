package com.yummit.ownerapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.ownerapp.`interface`.FoodCallbacks
import com.yummit.ownerapp.adapter.OffersMenuAdapter
import com.yummit.ownerapp.controller.FoodController
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.viewInterface.AddOffersMenuInterface
import kotlinx.android.synthetic.main.activity_new_add_menu_offers.*

class NewAddMenuOffers : AppCompatActivity(), AddOffersMenuInterface {
    private lateinit var foodController: FoodController
    private lateinit var offersMenuAdapter: OffersMenuAdapter

    lateinit var selectedMenus: ArrayList<Menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_add_menu_offers)

        setupViews()
        setupData()
    }

    private fun setupViews() {

        shimmer_view_container.startShimmerAnimation()

        selectedMenus = arrayListOf()
        if (intent.getSerializableExtra("selectedMenu") != null){
            @Suppress("UNCHECKED_CAST")
            selectedMenus = intent.getSerializableExtra("selectedMenu") as ArrayList<Menu>
        }

        /* Hide Action Bar */
        this.supportActionBar?.hide()

        /* Setup hide keyboard touch anywhere */
        cl_new_add_menu_offers.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        hideKeyboard()
                }
                return view?.onTouchEvent(event) ?: true
            }
        })

        /* Setup recyclerView */
        offersMenuAdapter = OffersMenuAdapter()
        offersMenuAdapter.delegate = this
        rv_add_menu_offers_menu.adapter = offersMenuAdapter
        rv_add_menu_offers_menu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_add_menu_offers_menu.setHasFixedSize(true)

        /* Setup back button */
        btn_new_add_menu_offers_back.setOnClickListener {
            finish()
            val intent = Intent(this, NewOfferActivity::class.java)
            intent.putExtra("selectedMenu", selectedMenus)
            startActivity(intent)
        }

        btn_done.setOnClickListener {
            val data = Intent()
            data.putExtra("selectedMenu", selectedMenus)
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    private fun setupData() {

        val id = getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("id", "").toString()

        foodController = FoodController()
        foodController.getFood(id, object : FoodCallbacks {
            override fun onSuccess(menus: ArrayList<Menu>) {
                /* Check selectedMenu. Hide menu if duplicate */
                for (selectedMenu in selectedMenus){
                    for (i in 0 until menus.size){
                        if(menus[i].id == selectedMenu.id) {
                            menus.removeAt(i)
                            break
                        }
                    }
                }
                offersMenuAdapter.menus = menus
                offersMenuAdapter.notifyDataSetChanged()

                shimmer_view_container.stopShimmerAnimation()
                shimmer_view_container.visibility = View.GONE
            }
        })
    }

    override fun didSelectMenu(menu: Menu) {
        selectedMenus.add(menu)
    }

    /* Setup hide keyboard touch anywhere */
    fun Activity.hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(cl_new_add_menu_offers.windowToken, 0)
    }
}
