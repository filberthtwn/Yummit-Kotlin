package com.yummit.customer.fragment.main.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yummit.customer.R
import com.yummit.customer.fragment.main.home.order.FinishedFragment
import com.yummit.customer.fragment.main.home.order.OnProcessFragment
import kotlinx.android.synthetic.main.fragment_my_order.*

class MyOrderFragment(ctx: Context) : Fragment() {
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pagerAdapter = ViewPagerAdapter(fragmentManager!!)
        vp_myorder.adapter = pagerAdapter

        tab_myorder.setupWithViewPager(vp_myorder)
        tab_myorder.getTabAt(0)?.text = context?.resources?.getString(R.string.on_process)
        tab_myorder.getTabAt(1)?.text = context?.resources?.getString(R.string.finished)
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            return when(position){
                0 -> OnProcessFragment()
                1 -> FinishedFragment()
                else -> OnProcessFragment()
            }
        }
    }
}
