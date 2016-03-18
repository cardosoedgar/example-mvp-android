package com.cardosoedgar.anotherweatherapp.views.weather

import com.cardosoedgar.anotherweatherapp.Models

/**
 * Created by edgarcardoso on 3/11/16.
 */
interface WeatherView {
    fun onSuccess(city: Models.City);
    fun onFailure();
}