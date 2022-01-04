package com.yummit.ownerapp.global

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.InputStream
import java.net.URL

class Global {

    companion object{

        val address = "https://yummit.aurigaaristo.com/api/restaurant"

        fun getToken(context: Context) : String{
            /* Initialized sharedPreferences variable */
            val prefHelper = context.getSharedPreferences("user", Context.MODE_PRIVATE)

            /* Get Token from sharedPreferences */
            return prefHelper.getString("token", "").toString()
        }

        fun loadImageFromWebOperations(url: String?): Drawable? {
            return try {
                val `is`: InputStream = URL(url).content as InputStream
                Drawable.createFromStream(`is`, "src name")
            } catch (e: java.lang.Exception) {
                null
            }
        }

        fun setupUserPreferences(context: Context, id:String, token:String, name:String, email:String, balance:Int, imageUrl:String){
            /* Save userId into sharedPreferences */
            val editor = context.getSharedPreferences("user", Context.MODE_PRIVATE).edit()
            editor.putString("id", id)
            editor.putString("token", token)
            editor.putString("name", name)
            editor.putString("email", email)
            editor.putInt("balance", balance)
            editor.putString("imageUrl", imageUrl)
            editor.commit()
        }

        fun decimalFormatter(price:Int):String{
            return String.format("%,d", price).replace(',', '.')
        }
    }

//    fun Fragment.hideKeyboard() {
//        view?.let{activity?.hideKeyboard(it)}
//    }
//
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun printResponse(sb:String){
        if (sb.length > 4000) {
            Log.v(TAG, "sb.length = " + sb.length)
            val chunkCount = sb.length / 4000     // integer division
            for (i in 0..chunkCount) {
                val max = 4000 * (i + 1)
                if (max >= sb.length) {
                    Log.d(TAG, sb.substring(4000 * i))
                } else {
                    Log.d(
                        TAG, sb.substring(4000 * i, max)
                    )
                }
            }
        }
        else{
            Log.d(TAG, sb)
        }
    }
}