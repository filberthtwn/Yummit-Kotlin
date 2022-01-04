package com.yummit.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yummit.customer.fragment.login.LoginFragment
import com.yummit.customer.fragment.login.SignUpFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        loadFragment(LoginFragment())

        if (intent != null){
            when (intent.getStringExtra("goto")){
                "login" -> loadFragment(LoginFragment())
                "signup" -> loadFragment(SignUpFragment())
            }
        } else {
            loadFragment(LoginFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_login_container, fragment)
            .commit()
    }
}
