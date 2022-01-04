package com.yummit.customer

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.viewpager.widget.ViewPager
import com.yummit.customer.adapter.onboarding.OnboardingAdapter
import com.yummit.customer.helper.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, View.OnClickListener {
    private lateinit var adapter: OnboardingAdapter
    private lateinit var dots: MutableList<TextView>

    private lateinit var prefHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        prefHelper = SharedPreferencesHelper(this)

        supportActionBar?.hide()

        adapter = OnboardingAdapter(supportFragmentManager)
        vp_onboarding.adapter = adapter

        addDotsIndicator(0)

        vp_onboarding.addOnPageChangeListener(this)

        btn_onboarding_login.setOnClickListener(this)
        btn_onboarding_signup.setOnClickListener(this)

        prefHelper.onboardingDone()
    }

    @Suppress("DEPRECATION")
    private fun addDotsIndicator(position: Int){ //dots not working
        dots = mutableListOf()
        dots_onboarding.removeAllViews()

        for (i in 0..2){
            dots.add(TextView(this))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            } else {
                dots[i].text = Html.fromHtml("&#8226;")
            }
            dots[i].textSize = R.dimen.dots.toFloat()
            dots[i].setTextColor(Color.argb(35, 255, 255, 255))

            dots_onboarding.addView(dots[i])
        }

        if (dots.size > 0){
            dots[position].setTextColor(Color.argb(100, 255, 255, 255))
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        addDotsIndicator(position)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_onboarding_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("goto", "login")
                startActivity(intent)
                finish()
            }
            R.id.btn_onboarding_signup -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("goto", "signup")
                startActivity(intent)
                finish()
            }
        }
    }
}
