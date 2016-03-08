package com.cardosoedgar.anotherweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.cardosoedgar.anotherweatherapp.butterknife.*
import com.cardosoedgar.anotherweatherapp.dagger.LocationModule
import com.cardosoedgar.anotherweatherapp.retrofit.OpenWeather
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    val PERMISSION_REQUEST_CODE = 1;

    val locationRequest = LocationRequest()

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val textViewName: TextView by bindView(R.id.name)
    val textViewTemp: TextView by bindView(R.id.temp)
    val textViewTime: TextView by bindView(R.id.time)

    @Inject lateinit var openWeatherApi: OpenWeather
    @Inject lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CustomApplication.appComponent.activityComponent(LocationModule(this)).inject(this)
        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        googleApiClient.connect()
        super.onStart()
    }

    override fun onStop() {
        googleApiClient.disconnect()
        super.onStop()
    }

    override fun onConnected(p0: Bundle?) {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
            return;
        }

        setupRequestLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE ->
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setupRequestLocation()
        }
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
                getWeatherOnLocation(location.latitude, location.longitude)
        })
    }

    private fun getWeatherOnLocation(latitude: Double, longitude: Double) {
        openWeatherApi.getCurrentLocationWeather(latitude, longitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ city ->
                            val dateFormat = SimpleDateFormat("dd MMM yyyy   HH:mm")
                            var temp = Math.round(city.main.temp)

                            textViewName.text = city.name
                            textViewTemp.text = "${temp.toString()}c"
                            textViewTime.text = "Last Update: ${dateFormat.format(Date())}"
                        },
                        { error ->
                            textViewName.text = ""
                            textViewTemp.text = ""
                            textViewTime.text = "Could not retrive location"
                        })
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}
