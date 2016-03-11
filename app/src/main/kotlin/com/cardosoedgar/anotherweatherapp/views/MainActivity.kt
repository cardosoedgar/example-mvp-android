package com.cardosoedgar.anotherweatherapp.views

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.cardosoedgar.anotherweatherapp.CustomApplication
import com.cardosoedgar.anotherweatherapp.R
import com.cardosoedgar.anotherweatherapp.views.location.LocationInterface
import com.cardosoedgar.anotherweatherapp.views.location.LocationView
import com.cardosoedgar.anotherweatherapp.butterknife.*
import com.cardosoedgar.anotherweatherapp.dagger.PresenterModule
import com.cardosoedgar.anotherweatherapp.Model
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherInterface
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LocationView, WeatherView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @Inject lateinit var locationProvider: LocationInterface
    @Inject lateinit var weatherProvider: WeatherInterface

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val textViewName: TextView by bindView(R.id.name)
    val textViewTemp: TextView by bindView(R.id.temp)
    val textViewTime: TextView by bindView(R.id.time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CustomApplication.appComponent.presenterComponent(PresenterModule(this)).inject(this)
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

    override fun onStart() {
        locationProvider.onStart()
        super.onStart()
    }

    override fun onStop() {
        locationProvider.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        locationProvider.onDestroy()
        super.onDestroy()
    }

    override fun onConnected(p0: Bundle?) {
        locationProvider.onConnected()
    }

    override fun onLocationProvided(location: Location) {
        weatherProvider.getWeatherOnLocation(location)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            locationProvider.getPermissionRequestCode() ->
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locationProvider.onPermissionAccepted()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}
