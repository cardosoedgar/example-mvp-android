package com.cardosoedgar.anotherweatherapp.views

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.cardosoedgar.anotherweatherapp.Codes
import com.cardosoedgar.anotherweatherapp.CustomApplication
import com.cardosoedgar.anotherweatherapp.R
import com.cardosoedgar.anotherweatherapp.butterknife.*
import com.cardosoedgar.anotherweatherapp.dagger.ActivityModule
import com.cardosoedgar.anotherweatherapp.Model
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherInterface
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), WeatherView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @Inject lateinit var googleApiClient: GoogleApiClient
    @Inject lateinit var weatherProvider: WeatherInterface
    @Inject lateinit var sharedPreferences: SharedPreferences

    val locationRequest = LocationRequest()

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val textViewName: TextView by bindView(R.id.name)
    val textViewTemp: TextView by bindView(R.id.temp)
    val textViewTime: TextView by bindView(R.id.time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomApplication.appComponent.activityComponent(ActivityModule(this)).inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onSuccess(city: Model.City) {
        val dateFormat = SimpleDateFormat("HH:mm - dd MMM")
        val temp = Math.round(city.main.temp)

        textViewName.text = city.name
        textViewTemp.text = "${temp.toString()}º"
        textViewTime.text = "Last Update: ${dateFormat.format(Date())}"
    }

    override fun onFailure() {
        textViewName.text = ""
        textViewTemp.text = ""
        textViewTime.text = "Could not retrive location"
    }

    fun onLocationProvided(location: Location) {
        weatherProvider.getWeatherOnLocation(location)
    }

    fun requestLocation() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Codes.PERMISSION_REQUEST_CODE)
            return;
        }
        setupRequestLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            Codes.PERMISSION_REQUEST_CODE ->
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    setupRequestLocation()
                else
                    textViewTime.text = "Location permission not provided"
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
                        onLocationProvided(location)
                    saveLocation(location)
                })
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
        requestLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.refresh) {
            requestLocation()
        }
        return true
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
