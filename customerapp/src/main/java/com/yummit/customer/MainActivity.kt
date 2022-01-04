package com.yummit.customer

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.yummit.customer.helper.checkGPS
import kotlinx.android.synthetic.main.dialog_gps_permission.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var fragment: Fragment
    private var KEY = "savefragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gps = checkGPS()
        if (!gps) {
            showGPSDialog()
        }

        supportActionBar?.hide()
    }

    private fun showGPSDialog(){
        val dialog = BottomSheetDialog(this@MainActivity)
        val view = layoutInflater.inflate(R.layout.dialog_gps_permission, null)
        dialog.setContentView(view)

        view.btn_gps_turnon.setOnClickListener {
            dialog.dismiss()
            showGPSPermission()
        }

        view.btn_gps_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showGPSPermission(){
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied){
                        Toast.makeText(applicationContext, "Please allow the GPS Permission", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .check()
    }
}
