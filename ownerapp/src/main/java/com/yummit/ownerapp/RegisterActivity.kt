package com.yummit.ownerapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.yummit.ownerapp.`interface`.RestaurantCallbacks
import com.yummit.ownerapp.`interface`.UserCallbacks
import com.yummit.ownerapp.controller.UserController
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Restaurant
import com.yummit.ownerapp.viewInterface.SuccessInterface
import kotlinx.android.synthetic.main.activity_register.*
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File
import java.lang.Exception

class RegisterActivity : AppCompatActivity(), UserCallbacks {

    lateinit var imageFile:File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupViews()
    }

    private fun setupViews(){

        imageFile = File("")

        /* Hide Action Bar */
        this.supportActionBar?.hide()

        /* Hide keyboard onTouch anywhere */
        constraintLayout_register.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN ->
                        hideKeyboard()
                }

                return view?.onTouchEvent(event) ?: true
            }
        })

        /* email ediText become first responder*/
        et_register_name.requestFocus()

        setupButton()
    }

    private fun setupButton(){

        /* Sign up process */
        btn_signup.setOnClickListener {

            pb_register.visibility = View.VISIBLE
            btn_signup.visibility = View.INVISIBLE

            val name = et_register_name.text.toString()
            val email = et_register_email.text.toString()
            val password = et_register_password.text.toString()
            val confirmPassword = et_register_confirmPassword.text.toString()
            val address = et_address.text.toString()

            if (password.equals(confirmPassword, true)){
                UserController.delegate = this
                UserController.createRestaurant(name, email, password, address, imageFile, object : UserCallbacks{
                    override fun onRegistered(token: String) {
                        super.onRegistered(token)
                        UserController.getRestaurantDetails(token, object: RestaurantCallbacks{
                            override fun onSuccess(restaurant: Restaurant) {
                                pb_register.visibility = View.INVISIBLE
                                Global.setupUserPreferences(applicationContext, restaurant.id, token,restaurant.name,restaurant.email, restaurant.balance, restaurant.imageUrl)
                                val intent = Intent(applicationContext, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
                    }
                })
            }else{
                Toast.makeText(applicationContext, "Password Not Match", Toast.LENGTH_SHORT)
            }
        }

        /* Setup Already Have an Account Button */
        btn_signInNow.setOnClickListener {
            finish()
        }

        /* Setup Add Image Button */
        iv_restaurant_logo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, 1001)
                }else{
                    EasyImage.openGallery(this, 0)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object :
            EasyImage.Callbacks {
            override fun onImagesPicked(
                p0: MutableList<File>,
                p1: EasyImage.ImageSource?,
                p2: Int
            ) {
                imageFile = p0[0]
                iv_restaurant_logo.setImageURI(data?.data)
            }

            override fun onImagePickerError(p0: Exception?, p1: EasyImage.ImageSource?, p2: Int) {
                println("ERROR")
            }

            override fun onCanceled(p0: EasyImage.ImageSource?, p1: Int) {
                println("CANCELED")
            }
        })

    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
