package com.yummit.ownerapp.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.yummit.ownerapp.model.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.yummit.ownerapp.AddMenuActivity

import com.yummit.ownerapp.R
import com.yummit.ownerapp.`interface`.FoodCallbacks
import com.yummit.ownerapp.adapter.MenuAdapter
import com.yummit.ownerapp.controller.FoodController
import com.yummit.ownerapp.global.Global
import kotlinx.android.synthetic.main.fragment_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {
    private lateinit var shimmerContainer: ShimmerFrameLayout
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupData()
    }

    override fun onResume() {
        super.onResume()
        setupData()
    }

    private fun setupViews(){

        shimmerContainer = shimmer_view_container
        shimmerContainer.startShimmerAnimation()

        /* Setup recycleView */
        menuAdapter = MenuAdapter(context!!)
        rv_menu.adapter = menuAdapter
        rv_menu.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_menu.setHasFixedSize(true)

        btn_menu_new_menu.setOnClickListener {
            val intent = Intent(context, AddMenuActivity::class.java)
            startActivity(intent)
        }

        btn_menu_add_menu.setOnClickListener {
            val intent = Intent(context, AddMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupData(){
        val foodController = FoodController()

        foodController.getFood(Global.getToken(requireContext()), object : FoodCallbacks {
            override fun onSuccess(menus: ArrayList<Menu>) {
                println(menus.size)
                menuAdapter.menus = menus
                menuAdapter.notifyDataSetChanged()

                // Stop and hide shimmer effect
                shimmerContainer.stopShimmerAnimation()
                shimmerContainer.visibility = View.GONE

                // Hide empty state
                if (menus.size == 0){
                    cl_menu_empty_state.isVisible = true
                    btn_menu_new_menu.isVisible = true
                }
            }
        })
    }
}
