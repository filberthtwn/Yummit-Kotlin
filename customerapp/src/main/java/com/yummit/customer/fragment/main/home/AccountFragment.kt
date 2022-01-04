package com.yummit.customer.fragment.main.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yummit.customer.R
import com.yummit.customer.fragment.main.HomeFragmentDirections
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.helper.comingSoon
import com.yummit.customer.helper.readableNumber
import kotlinx.android.synthetic.main.content_fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment(ctx: Context) : Fragment(), View.OnClickListener {
    private lateinit var prefHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prefHelper = SharedPreferencesHelper(requireContext())
        loadDataPreference()

        btn_logout.setOnClickListener(this)
        tv_account_loyalty_next.setOnClickListener(this)
        img_account_edit.setOnClickListener(this)
        btn_account_money_topup.setOnClickListener(this)
        ll_account_rewards.setOnClickListener(this)
        ll_account_language.setOnClickListener(this)
        ll_account_help.setOnClickListener(this)
        ll_account_about.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.img_account_edit -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeChangeprofile())
            }
            R.id.tv_account_loyalty_next -> {}
            R.id.btn_account_money_topup -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeTopuppayment())
            }
            R.id.ll_account_rewards -> {
                activity?.comingSoon()
            }
            R.id.ll_account_language -> {
                activity?.comingSoon()
            }
            R.id.ll_account_help -> {
                activity?.comingSoon()
            }
            R.id.ll_account_about -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeAboutus())
            }
            R.id.btn_logout -> {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadDataPreference()
    }

    fun loadDataPreference(){
        tv_account_name.text = prefHelper.getSavedName()
        tv_account_email.text = prefHelper.getSavedEmail()
        tv_account_money.text = prefHelper.getSavedMoney().readableNumber()
    }
}
