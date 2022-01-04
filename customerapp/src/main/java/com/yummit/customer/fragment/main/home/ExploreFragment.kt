package com.yummit.customer.fragment.main.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.yummit.customer.R
import com.yummit.customer.adapter.home.NearMeAdapter
import com.yummit.customer.databinding.FragmentExploreBinding
import com.yummit.customer.fragment.main.HomeFragmentDirections
import com.yummit.customer.helper.*
import com.yummit.customer.viewmodel.NearMeViewModel
import kotlinx.android.synthetic.main.fragment_explore.*

class ExploreFragment(private val ctx: Context) : Fragment(), View.OnClickListener {
    private lateinit var dialog: Dialog

    private lateinit var dataBinding: FragmentExploreBinding
    private lateinit var nearMeViewModel: NearMeViewModel

    private var nearMeAdapter = NearMeAdapter(arrayListOf())
    private lateinit var prefHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefHelper = SharedPreferencesHelper(requireContext())
        dataBinding.userName = "Hungry ${prefHelper.getSavedName().getFirst()}?"

        nearMeViewModel = ViewModelProviders.of(this).get(NearMeViewModel::class.java)
        nearMeViewModel.getData()

        rv_home_nearme.apply {
            adapter = nearMeAdapter
            layoutManager = GridLayoutManager(context, 2)
            hasFixedSize()
        }

        refresh_explore.setOnRefreshListener {
            rv_home_nearme.invisible()
            list_nearme_error.gone()
            pb_list_nearme.visible()
            nearMeViewModel.getDataBypassCache()
            refresh_explore.isRefreshing = false
        }

        tv_home_nearme_viewall.setOnClickListener(this)
        ll_home_delivery.setOnClickListener(this)
        ll_home_pickup.setOnClickListener(this)
        ll_home_donate.setOnClickListener(this)
        ll_home_foodmap.setOnClickListener(this)

        observeViewModel()
    }

    private fun observeViewModel(){
        nearMeViewModel.data.observe(viewLifecycleOwner, Observer {restos ->
            restos?.let {
                rv_home_nearme.visible()
                nearMeAdapter.updateList(restos)
            }
        })

        nearMeViewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                list_nearme_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        nearMeViewModel.loadError.observe(viewLifecycleOwner, Observer {
            it?.let {
                pb_list_nearme.visibility = if (it) View.VISIBLE else View.GONE
                if (it){
                    list_nearme_error.gone()
                    rv_home_nearme.invisible()
                }
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.tv_home_nearme_viewall -> {
                activity?.comingSoon()
            }
            R.id.ll_home_delivery -> {
                Navigation.findNavController(requireActivity(), R.id.fl_home_container).navigate(HomeFragmentDirections.actionHomeDelivery())
            }
            R.id.ll_home_pickup -> {
                findNavController().navigate(HomeFragmentDirections.actionHomePickup())
            }
            R.id.ll_home_donate -> {
                activity?.comingSoon()
            }
            R.id.ll_home_foodmap -> {
                activity?.comingSoon()
            }
        }
    }


}
