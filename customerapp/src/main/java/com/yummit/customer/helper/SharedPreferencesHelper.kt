package com.yummit.customer.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesHelper {
    companion object {
        private val onboarding = "onboarding"
        private val token = "token"

        private val fullname = "name"
        private val email = "email"
        private val password = "password"
        private val phone = "phone"
        private val money = "money"

        const val refresh_time = "refreshtime"
        const val refresh_order = "refreshorder"

        internal var prefs: SharedPreferences? = null

//        private var menu1 = "menu1"
//        private var menu2 = "menu2"
//        private var menu3 = "menu3"
//        private var topMeals1 = "topmeals1"
//        private var topMeals2 = "topmeals2"
//        private var topMeals3 = "topmeals3"

        @Volatile
        internal var instance: SharedPreferencesHelper? = null
        internal var LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveLogin(em: String, pass: String){
        prefs?.edit(commit = true){
            putString(email, em)
            putString(password, pass)
        }
    }
    fun saveProfile(name: String, em: String, pass: String, ph: String){
        prefs?.edit(commit = true){
            putString(fullname, name)
            putString(email, em)
            putString(password, pass)
            putString(phone, ph)
        }
    }
    fun getSavedName() = prefs?.getString(fullname, "")?:""
    fun getSavedEmail() = prefs?.getString(email, "")?:""
    fun getSavedPassword() = prefs?.getString(password, "")?:""
    fun getSavedPhone() = prefs?.getString(phone, "")?:""

    fun saveMoney(mon: Int){
        prefs?.edit(commit = true){
            putInt(money, mon)
        }
    }
    fun getSavedMoney() = prefs?.getInt(money, 0) ?: 0

    fun onboardingDone(){
        prefs?.edit(commit = true){
            putBoolean(onboarding, true)
        }
    }
    fun getOnboardingStatus() = prefs?.getBoolean(onboarding, false) ?: false

    fun saveToken(api: String){
        prefs?.edit(commit = true){
            putString(token, api)
        }
    }
    fun getToken() = prefs?.getString(token, "")?:""

    fun saveUpdateTime(time: Long){
        prefs?.edit(commit = true){
            putLong(refresh_time, time)
        }
    }
    fun getUpdateTime() = prefs?.getLong(refresh_time, 0L)
    fun getCacheDuration() = prefs?.getString("pref_cache_duration", "")

    fun saveUpdateTimeOrder(time: Long){
        prefs?.edit(commit = true){
            putLong(refresh_time, time)
        }
    }
    fun getUpdateTimeOrder() = prefs?.getLong(refresh_time, 0L)

//    fun likeMenu(code: Int, to: Boolean){
//        prefs?.edit(commit = true){
//            when (code){
//                1 -> putBoolean(menu1, to)
//                2 -> putBoolean(menu2, to)
//                3 -> putBoolean(menu3, to)
//            }
//        }
//    }
//    fun getMenu1() = prefs?.getBoolean(menu1, false)
//    fun getMenu2() = prefs?.getBoolean(menu2, false)
//    fun getMenu3() = prefs?.getBoolean(menu3, false)
//
//    fun likeTopMeals(code: Int, to: Boolean){
//        prefs?.edit(commit = true){
//            when (code){
//                1 -> putBoolean(topMeals1, to)
//                2 -> putBoolean(topMeals2, to)
//                3 -> putBoolean(topMeals3, to)
//            }
//        }
//    }
//    fun getTopMeals1() = prefs?.getBoolean(topMeals1, false)
//    fun getTopMeals2() = prefs?.getBoolean(topMeals2, false)
//    fun getTopMeals3() = prefs?.getBoolean(topMeals3, false)
}