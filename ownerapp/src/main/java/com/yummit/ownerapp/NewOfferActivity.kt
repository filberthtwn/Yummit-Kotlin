package com.yummit.ownerapp

import android.app.Activity
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.error.ANError
import com.yummit.ownerapp.`interface`.OfferCallbacks
import com.yummit.ownerapp.adapter.OffersMenuAdapter
import com.yummit.ownerapp.controller.OfferController
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.model.Offer
import com.yummit.ownerapp.viewInterface.NewOfferInterface
import kotlinx.android.synthetic.main.activity_new_offer.*
import java.util.*
import kotlin.collections.ArrayList

class NewOfferActivity : AppCompatActivity(), NewOfferInterface{
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var offersMenuAdapter: OffersMenuAdapter
    private var addMenuButtonY = 0
    private lateinit var id: String

    private var selectedMenu:ArrayList<Menu> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_offer)

        setupViews()
    }

    /* Get data from NewAddMenuOffer */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            if (data.getSerializableExtra("selectedMenu") != null){
                @Suppress("UNCHECKED_CAST")
                selectedMenu = data.getSerializableExtra("selectedMenu") as ArrayList<Menu>
            }
            offersMenuAdapter.menus = selectedMenu
            offersMenuAdapter.notifyDataSetChanged()
        }

    }

    private fun setupViews() {
        id = getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("id", "").toString()

        /* Hide Action Bar */
        this.supportActionBar?.hide()

        /* Setup recyclerView */
        offersMenuAdapter = OffersMenuAdapter()
        offersMenuAdapter.delegate = this
        rv_new_offers_menu.adapter = offersMenuAdapter
        offersMenuAdapter.menus = selectedMenu
        rv_new_offers_menu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_new_offers_menu.setHasFixedSize(true)

        /* Setup hide keyboard touch anywhere */
        cl_new_offer.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        hideKeyboard()
                }
                return view?.onTouchEvent(event) ?: true
            }
        })

        nsv_new_offer_menu.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN ->
                        hideKeyboard()
                }
                return view?.onTouchEvent(event) ?: true
            }
        })

        btn_new_offer_back.setOnClickListener {
            finish()
        }

        btn_new_offer_add_menu.setOnClickListener {
            val intent = Intent(this, NewAddMenuOffers::class.java)
            intent.putExtra("selectedMenu", selectedMenu)
            startActivityForResult(intent, 0)
        }

        btn_done.setOnClickListener {
            val offerController = OfferController()
            val foodsId = arrayListOf<Int>()
            for (menu in selectedMenu){
                foodsId.add(menu.id.toInt())
            }
            val foodsIdString = foodsId.toString().replace("[", "").replace("]", "").replace(" ","")

            val title = et_title.text.toString()
            val discount = et_discount.text.toString().toInt()
            val startTime = et_offer_start_time.text.toString()
            val endTime = et_offer_end_time.text.toString()

            val token = getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("token", "").toString()
            offerController.createOffer(token, foodsIdString, title, discount, startTime, endTime, object : OfferCallbacks{
                override fun onSuccess(offers: ArrayList<Offer>) {
                    finish()
                }

                override fun onFailed(error: ANError) {
                    Log.d(ContentValues.TAG, error.errorBody)
                }

            })
        }

        et_offer_start_time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)

            val minutes = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { timePicker, sHour, sMinute ->
                    val hour = if (sHour < 10) "0" + sHour else sHour
                    val min = if (sMinute < 10) "0" + sMinute else sMinute

                    et_offer_start_time.setText("" + hour.toString() + ":" + min.toString())
                },
                hour,
                minutes,
                true
            )
            timePickerDialog.show()
        }

        et_offer_end_time.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minutes = calendar.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, sHour: Int, sMinute ->
                    val hour = if (sHour < 10) "0" + sHour else sHour
                    val min = if (sMinute < 10) "0" + sMinute else sMinute
                    et_offer_end_time.setText("" + hour + ":" + min)
                },
                hour,
                minutes,
                true
            )
            timePickerDialog.show()
        }
    }

    override fun didDeleteClicked(position: Int) {
        selectedMenu.removeAt(position)
        offersMenuAdapter.notifyDataSetChanged()
    }

    /* Setup hide keyboard touch anywhere */
    fun Activity.hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(cl_new_offer.windowToken, 0)
    }
}
