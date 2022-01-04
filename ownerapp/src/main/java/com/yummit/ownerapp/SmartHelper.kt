package com.yummit.ownerapp

import android.content.Context
import android.location.LocationManager
import android.view.View
import android.widget.Toast
import java.math.BigInteger
import java.security.MessageDigest
import java.text.NumberFormat




fun Int.readableNumber(): String {
    return "Rp " + NumberFormat.getInstance().format(this).replace(',', '.')
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.checkGPS(): Boolean {
    val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var gpsEnabled = false
    var networkEnabled = false

    try {
        gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (e: Exception) {
    }

    try {
        networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (e: Exception) {
    }

    return gpsEnabled && networkEnabled
}

fun Int.randomizeID(): String {
    return "Order #789A09SJ0$this"
}

fun Context.comingSoon() {
    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
}

fun String.getFirst(): String {
    val word = this.split(" ")
    return word.get(0)
}