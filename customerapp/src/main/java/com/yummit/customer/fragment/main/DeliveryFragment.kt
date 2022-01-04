package com.yummit.customer.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.customer.R
import com.yummit.customer.adapter.delivery.ClosingHoursAdapter
import com.yummit.customer.adapter.delivery.FastHungriousAdapter
import com.yummit.customer.adapter.pickup.CategoriesAdapter
import com.yummit.customer.adapter.pickup.VoucherAdapter
import com.yummit.customer.databinding.FragmentDeliveryBinding
import com.yummit.customer.helper.visible
import com.yummit.customer.viewmodel.ClosingHoursFoodViewModel
import com.yummit.customer.viewmodel.DeliveryVoucherViewModel
import com.yummit.customer.viewmodel.FastAndHungriousViewModel
import kotlinx.android.synthetic.main.fragment_delivery.*

class DeliveryFragment : Fragment(), View.OnClickListener {
    private var voucherAdapter = VoucherAdapter(arrayListOf())
    private var categoriesAdapter = CategoriesAdapter(requireContext())
    private var closingHoursAdapter = ClosingHoursAdapter(arrayListOf())
    private var fastHungriousAdapter = FastHungriousAdapter(arrayListOf())

    private lateinit var dataBinding: FragmentDeliveryBinding
    private lateinit var voucherViewModel: DeliveryVoucherViewModel
    private lateinit var closingHoursViewModel: ClosingHoursFoodViewModel
    private lateinit var fastAndHungriousViewModel: FastAndHungriousViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tb_fragment_delivery.setNavigationIcon(R.drawable.ic_arrow_back)
        tb_fragment_delivery.setNavigationOnClickListener { 
            Navigation.findNavController(view).navigateUp()
        }

        voucherViewModel = ViewModelProviders.of(this).get(DeliveryVoucherViewModel::class.java)
        closingHoursViewModel = ViewModelProviders.of(this).get(ClosingHoursFoodViewModel::class.java)
        fastAndHungriousViewModel = ViewModelProviders.of(this).get(FastAndHungriousViewModel::class.java)

        voucherViewModel.getDeliveryVoucher()
        closingHoursViewModel.getClosingHoursFood()
        fastAndHungriousViewModel.getFastAndHungrious()

        rv_delivery_voucher.apply {
            adapter = voucherAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        rv_delivery_categories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        rv_delivery_closing.apply {
            adapter = closingHoursAdapter
            layoutManager = GridLayoutManager(context, 2)
            hasFixedSize()
        }

        rv_delivery_fast_hungrious.apply {
            adapter = fastHungriousAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }

        tv_delivery_closing_viewall.setOnClickListener(this)
        tv_delivery_fast_seeall.setOnClickListener(this)

        observeViewModel()
    }

    private fun observeViewModel(){
        voucherViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_delivery_voucher.visible()
                voucherAdapter.updateList(it)
            }
        })

        voucherViewModel.loading.observe(viewLifecycleOwner, Observer {

        })

        closingHoursViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_delivery_closing.visible()
                closingHoursAdapter.updateList(it)
            }
        })

        closingHoursViewModel.loading.observe(viewLifecycleOwner, Observer {

        })

        fastAndHungriousViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_delivery_fast_hungrious.visible()
                fastHungriousAdapter.updateList(it)
            }
        })

        fastAndHungriousViewModel.loading.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.tv_delivery_closing_viewall -> {}
            R.id.tv_delivery_fast_seeall -> {}
        }
    }
}
