package com.yummit.customer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.yummit.customer.helper.SharedPreferencesHelper

class SplashActivity : AppCompatActivity() {
    private lateinit var prefHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefHelper = SharedPreferencesHelper(this)

        supportActionBar?.hide()

        Handler().postDelayed({
            val sp = prefHelper.getOnboardingStatus()
            if (!sp){
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 3000)
    }
}
