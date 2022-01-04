package com.yummit.customer.fragment.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yummit.customer.R
import com.yummit.customer.adapter.pickup.CategoriesAdapter
import com.yummit.customer.adapter.pickup.ClosingAdapter
import com.yummit.customer.adapter.pickup.NearbyAdapter
import com.yummit.customer.adapter.pickup.VoucherAdapter
import com.yummit.customer.databinding.FragmentPickUpBinding
import com.yummit.customer.helper.visible
import com.yummit.customer.viewmodel.ClosingHoursRestoViewModel
import com.yummit.customer.viewmodel.NearMeViewModel
import com.yummit.customer.viewmodel.PickUpVoucherViewModel
import kotlinx.android.synthetic.main.fragment_pick_up.*

/**
 * A simple [Fragment] subclass.
 */
class PickUpFragment : Fragment(), View.OnClickListener {
    private var voucherAdapter = VoucherAdapter(arrayListOf())
    private var closingAdapter = ClosingAdapter(arrayListOf())
    private var categoriesAdapter = CategoriesAdapter(requireContext())
    private var nearbyAdapter = NearbyAdapter(arrayListOf())

    private lateinit var voucherViewModel: PickUpVoucherViewModel
    private lateinit var closingHoursViewModel: ClosingHoursRestoViewModel
    private lateinit var nearMeViewModel: NearMeViewModel
    private lateinit var dataBinding: FragmentPickUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pick_up, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tb_fragment_pickup.setNavigationIcon(R.drawable.ic_arrow_back)
        tb_fragment_pickup.setNavigationOnClickListener {

        }

        voucherViewModel = ViewModelProviders.of(this).get(PickUpVoucherViewModel::class.java)
        closingHoursViewModel = ViewModelProviders.of(this).get(ClosingHoursRestoViewModel::class.java)
        nearMeViewModel = ViewModelProviders.of(this).get(NearMeViewModel::class.java)

        voucherViewModel.getPickUpVoucher()
        closingHoursViewModel.getClosingHoursResto()
        nearMeViewModel.getData()

        rv_pickup_voucher.apply {
            adapter = voucherAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        rv_pickup_closing.apply {
            adapter = closingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            hasFixedSize()
        }

        rv_pickup_categories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        rv_pickup_nearby.apply {
            adapter = nearbyAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        tv_pickup_closing_viewall.setOnClickListener(this)
        tv_pickup_nearby_viewall.setOnClickListener(this)

        observeViewModel()
    }

    private fun observeViewModel(){
        voucherViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_pickup_voucher.visible()
                voucherAdapter.updateList(it)
            }
        })

        voucherViewModel.loading.observe(viewLifecycleOwner, Observer {

        })

        closingHoursViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_pickup_closing.visible()
                closingAdapter.updateList(it)
            }
        })

        closingHoursViewModel.loading.observe(viewLifecycleOwner, Observer {

        })

        nearMeViewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                rv_pickup_nearby.visible()
                nearbyAdapter.updateList(it)
            }
        })

        nearMeViewModel.loading.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.tv_pickup_closing_viewall -> {}
            R.id.tv_pickup_nearby_viewall -> {}
        }
    }
}
