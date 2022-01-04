package com.yummit.customer.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.yummit.customer.R

class OnboardingFragment(val image: Int, val title: String, val desc: String) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(this).load(image).into(view.findViewById(R.id.img_onboarding))
        view.findViewById<TextView>(R.id.tv_onboarding_title).text = title
        view.findViewById<TextView>(R.id.tv_onboarding_description).text = desc
    }
}