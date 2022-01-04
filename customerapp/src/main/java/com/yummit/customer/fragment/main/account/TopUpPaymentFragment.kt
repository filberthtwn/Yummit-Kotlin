package com.yummit.customer.fragment.main.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yummit.customer.R
import com.yummit.customer.adapter.account.TopUpPaymentAdapter
import com.yummit.customer.helper.NotificationHelper
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.helper.readableNumber
import kotlinx.android.synthetic.main.dialog_top_up.view.*
import kotlinx.android.synthetic.main.fragment_top_up_payment.*

/**
 * A simple [Fragment] subclass.
 */
class TopUpPaymentFragment : Fragment() {
    private lateinit var adapter: TopUpPaymentAdapter
    private var prefHelper = SharedPreferencesHelper(context!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_up_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TopUpPaymentAdapter(requireContext()){
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.dialog_top_up, null)
            dialog.setContentView(view)

            view.btn_topup_5k.setOnClickListener {
                notification(5000)
                dialog.dismiss()
//                onBackPressed()
            }
            view.btn_topup_10k.setOnClickListener {
                notification(10000)
                dialog.dismiss()
//                onBackPressed()
            }
            view.btn_topup_15k.setOnClickListener {
                notification(15000)
                dialog.dismiss()
//                onBackPressed()
            }
            view.btn_topup_20k.setOnClickListener {
                notification(20000)
                dialog.dismiss()
//                onBackPressed()
            }
            view.btn_topup_25k.setOnClickListener {
                notification(25000)
                dialog.dismiss()
//                onBackPressed()
            }
            view.btn_topup_50k.setOnClickListener {
                notification(50000)
                dialog.dismiss()
//                onBackPressed()
            }

            dialog.setOnDismissListener {
                dialog.dismiss()
            }

            dialog.show()
        }
        rv_payment_topup.adapter = adapter
        rv_payment_topup.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_payment_topup.setHasFixedSize(true)

        tb_fragment_topup_payment.setNavigationIcon(R.drawable.ic_arrow_back)
        tb_fragment_topup_payment.setNavigationOnClickListener {

        }
        tb_fragment_topup_payment.title = getString(R.string.top_up)
    }

    private fun notification(amount: Int){
        val money = prefHelper.getSavedMoney()
        prefHelper.saveMoney(money+amount)

        context?.let {
            NotificationHelper(it).createNotification("Yumm-pay", "Your top up of ${amount.readableNumber()} via apps is successful. Updated balance: ${(money + amount).readableNumber()}")
        }
    }
}
