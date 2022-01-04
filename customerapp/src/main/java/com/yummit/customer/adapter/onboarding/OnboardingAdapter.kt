package com.yummit.customer.adapter.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yummit.customer.R
import com.yummit.customer.fragment.onboarding.OnboardingFragment

class OnboardingAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val slideImages = intArrayOf(
        R.drawable.onboarding1,
        R.drawable.onboarding2,
        R.drawable.onboarding3
    )

    private val slideTitle = arrayOf(
        "Delicious Food",
        "Half the Price",
        "Deliver or Pickup"
    )

    private val slideDescription = arrayOf(
        "lorem ipsum dolor sim",
        "lorem ipsum dolor sim",
        "lorem ipsum dolor sim"
    )

    override fun getCount(): Int = slideTitle.size

    override fun getItem(position: Int): Fragment {
        return OnboardingFragment(slideImages[position], slideTitle[position], slideDescription[position])
    }
}