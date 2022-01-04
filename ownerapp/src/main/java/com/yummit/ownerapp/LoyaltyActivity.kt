package com.yummit.ownerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.ownerapp.adapter.AchievementAdapter
import kotlinx.android.synthetic.main.activity_loyalty.*

class LoyaltyActivity : AppCompatActivity() {

    private lateinit var achievementAdapter: AchievementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loyalty)

        setupViews()
    }

    private fun setupViews(){

        // Hide actionBar
        this.supportActionBar?.hide()

        /* Setup recycleView */
        achievementAdapter = AchievementAdapter(this)
        rv_achievement.adapter = achievementAdapter
        rv_achievement.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_achievement.setHasFixedSize(true)

        /* Setup back button */
        btn_back.setOnClickListener {
            finish()
        }
    }
}
