package com.yummit.customer.helper

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.os.Bundle
import java.io.IOException

class LocationSearcher(val context: Context) : LocationListener {
    private lateinit var locationManager: LocationManager
    private lateinit var location: Location

    companion object {
        var addressList = mutableListOf<Address>()
        lateinit var address: Address
    }

    @SuppressLint("MissingPermission")
    fun startLocationSearch(){
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
        onLocationChanged(location)
    }

    override fun onLocationChanged(location: Location) {
        val longitude = location.longitude
        val latitude = location.latitude

        getLocationDetail(longitude, latitude)
    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    private fun getLocationDetail(longitude: Double, latitude: Double){
        val geocoder = Geocoder(context)

        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1)
            address = addressList[0]
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}