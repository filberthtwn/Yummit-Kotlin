package com.yummit.customer.fragment.main.account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yummit.customer.R
import com.yummit.customer.helper.SharedPreferencesHelper
import kotlinx.android.synthetic.main.fragment_change_profile.*

/**
 * A simple [Fragment] subclass.
 */
class ChangeProfileFragment : Fragment(), View.OnClickListener {
    private var prefHelper = SharedPreferencesHelper(requireContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tb_fragment_changeprofile.setNavigationIcon(R.drawable.ic_arrow_back)
        tb_fragment_changeprofile.setNavigationOnClickListener {

        }

        tv_change_profile_save.setOnClickListener(this)
        tv_change_profile_upload_photo.setOnClickListener(this)

        edt_change_profile_name.setText(prefHelper.getSavedName())
        edt_change_profile_email.setText(prefHelper.getSavedEmail())
        edt_change_profile_phone.setText(prefHelper.getSavedPhone())
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.tv_change_profile_save -> {
                if (checkData()){
                    prefHelper.saveProfile(
                        edt_change_profile_name.text.toString(),
                        edt_change_profile_email.text.toString(),
                        prefHelper.getSavedPassword(),
                        edt_change_profile_phone.text.toString()
                    )
                    Toast.makeText(requireContext(), "Profile Saved", Toast.LENGTH_SHORT).show()
//                    onBackPressed()
                } else {
                    Toast.makeText(requireContext(), "Please complete all blanks!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.tv_change_profile_upload_photo -> {}
        }
    }

    private fun checkData(): Boolean {
        if (edt_change_profile_name.text.isEmpty()) return false
        if (edt_change_profile_email.text.isEmpty()) return false
        if (edt_change_profile_phone.text.isEmpty()) return false
        return true
    }
}
