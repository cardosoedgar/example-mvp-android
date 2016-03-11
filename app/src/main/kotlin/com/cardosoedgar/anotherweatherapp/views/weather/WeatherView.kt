package com.cardosoedgar.anotherweatherapp.views.weather

import com.cardosoedgar.anotherweatherapp.Model

/**
 * Created by edgarcardoso on 3/11/16.
 */
interface WeatherView {
    fun onSuccess(city: Model.City);
    fun onFailure();
}