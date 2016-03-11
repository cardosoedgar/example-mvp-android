package com.cardosoedgar.anotherweatherapp.views.location

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.cardosoedgar.anotherweatherapp.Codes
import com.cardosoedgar.anotherweatherapp.CustomApplication
import com.cardosoedgar.anotherweatherapp.dagger.LocationModule
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import javax.inject.Inject

/**
 * Created by edgarcardoso on 3/11/16.
 */
class LocationPresenter : LocationInterface {

    val locationRequest = LocationRequest()
    var locationView: LocationView? = null

    @Inject lateinit var googleApiClient: GoogleApiClient
    @Inject lateinit var sharedPreferences: SharedPreferences

    constructor(locationView: LocationView) {
        CustomApplication.appComponent.activityComponent(LocationModule(locationView as Context)).inject(this)
        this.locationView = locationView
    }

    override fun getPermissionRequestCode(): Int {
        return Codes.PERMISSION_REQUEST_CODE
    }

    override fun onStart() {
        googleApiClient.connect()
    }

    override fun onStop() {
        googleApiClient.disconnect()
    }

    override fun onDestroy() {
        locationView = null
    }

    override fun onPermissionAccepted() {
        setupRequestLocation()
    }

    override fun onConnected() {
        val permissionCheck = ContextCompat.checkSelfPermission(locationView as Context, Manifest.permission.ACCESS_FINE_LOCATION)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            locationView?.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, this.getPermissionRequestCode())
            return;
        }

        setupRequestLocation()
    }

    private fun setupRequestLocation() {
        locationRequest.numUpdates = 1
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            if(result.status.statusCode == LocationSettingsStatusCodes.SUCCESS) {
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                { location ->
                    if(location is Location)
                        locationView?.onLocationProvided(location)
                    saveLocation(location)
                })
    }

    /*

    saving location so the widget can request weather conditions.

     */
    private fun saveLocation(location: Location) {
        sharedPreferences.makeEdit {
            putString("latitude", location.latitude.toString())
            putString("longitude", location.longitude.toString())
        }
    }

    inline fun SharedPreferences.makeEdit(func: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.func()
        editor.apply()
    }
}