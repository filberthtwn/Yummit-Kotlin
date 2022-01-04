package com.yummit.ownerapp

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
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
import com.androidnetworking.AndroidNetworking
import com.yummit.ownerapp.`interface`.RestaurantCallbacks
import com.yummit.ownerapp.`interface`.UserCallbacks
import com.yummit.ownerapp.controller.UserController
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Restaurant
import com.yummit.ownerapp.plainObject.ErrorStatus
import com.yummit.ownerapp.viewInterface.AuthenticationInterface
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import pl.aprilapps.easyphotopicker.EasyImage

class LoginActivity : AppCompatActivity() {

    var dialog:ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }

    private fun setupViews(){

        /* Go to HomeActivity when user already logged in */
        val prefs = getSharedPreferences("user", Context.MODE_PRIVATE)
        println(prefs.getString("token", ""))
        if (prefs.getString("token", "") != ""){
            println("Already Logged In!")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        /* Hide Action Bar */
        this.supportActionBar?.hide()

        /* email ediText become first responder*/
        et_login_email.requestFocus()

        /* Hide keyboard onTouch anywhere */
        constraintLayout_login.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN ->
                        hideKeyboard()
                }

                return view?.onTouchEvent(event) ?: true
            }
        })


        setupButton()
    }

    private fun setupButton(){

        /* Setup Login Button */
        btn_login.setOnClickListener{

            hideKeyboard()

            if(et_login_email.text.toString() != ""){

                et_login_email.background = getDrawable(R.drawable.shape_rounded_edit_text)

                if(et_login_password.text.toString() != ""){

                    et_login_password.background = getDrawable(R.drawable.shape_rounded_edit_text)

                    /* Show progressDialog */
                    dialog = ProgressDialog(this)
                    dialog?.setMessage("Please Wait...")
                    dialog?.show()

                    val email = et_login_email.text.toString()
                    val password = et_login_password.text.toString()

                    UserController.delegate = this
                    UserController.userLogin(email, password, object : UserCallbacks{
                        override fun onLoggedIn(token: String, restaurant: Restaurant) {
                            Global.setupUserPreferences(applicationContext, restaurant.id, token,restaurant.name,restaurant.email, restaurant.balance, restaurant.imageUrl)
                            dialog?.dismiss()

                            val intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        override fun onFailed(message: String) {
                            super.onFailed(message)
                            if (message == ErrorStatus.WRONG_EMAIL_PASSWORD){
                                dialog?.dismiss()
                                Toast.makeText(applicationContext,"Wrong Email/ Password", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }else{
                    et_login_password.background = getDrawable(R.drawable.shape_warning_rounded_edit_text)
                }
            }else{
                et_login_email.background = getDrawable(R.drawable.shape_warning_rounded_edit_text)
                if(et_login_password.text.toString() == ""){
                    et_login_password.background = getDrawable(R.drawable.shape_warning_rounded_edit_text)
                }
            }


        }

        /* Setup Sign Up Button */
        btn_signUpNow.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

//    override fun onSuccess(id:String, name:String, email:String, token:String) {
//
//        /* Save userId into sharedPreferences */
//        val editor = getSharedPreferences("user", Context.MODE_PRIVATE).edit()
//        editor.putString("id", id)
//        editor.putString("token", token)
//        editor.putString("name", name)
//        editor.putString("email", email)
//        editor.commit()
//
//        println("Login Success")
//        dialog?.dismiss()
//
//        val intent = Intent(this, HomeActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
