package com.cardosoedgar.anotherweatherapp.views.weather

import android.location.Location

/**
 * Created by edgarcardoso on 3/11/16.
 */
interface WeatherInterface {
    fun getWeatherOnLocation(location: Location)
}