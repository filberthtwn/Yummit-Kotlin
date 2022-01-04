package com.yummit.ownerapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class OrdersViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var listFragment = ArrayList<Fragment>()
    var listTitles = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return listFragment.get(position)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getCount(): Int {
        return listTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitles.get(position)
    }

    fun addFragments(fragment: Fragment, title:String){
        listFragment.add(fragment)
        listTitles.add(title)
    }

//    override fun isViewFromObject(view: View, objects: Objects): Boolean {
//        return view==object
//    }

//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        return super.instantiateItem(container, position)
//    }
}