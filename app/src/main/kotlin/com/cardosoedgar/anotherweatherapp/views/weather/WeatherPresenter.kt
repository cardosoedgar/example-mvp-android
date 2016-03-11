package com.cardosoedgar.anotherweatherapp.views.weather

import android.content.Context
import android.location.Location
import com.cardosoedgar.anotherweatherapp.CustomApplication
import com.cardosoedgar.anotherweatherapp.dagger.LocationModule
import com.cardosoedgar.anotherweatherapp.retrofit.OpenWeather
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by edgarcardoso on 3/11/16.
 */
class WeatherPresenter : WeatherInterface {

    var weatherView: WeatherView? = null

    @Inject lateinit var openWeatherApi: OpenWeather

    constructor(weatherView: WeatherView) {
        CustomApplication.appComponent.activityComponent(LocationModule(weatherView as Context)).inject(this)
        this.weatherView = weatherView
    }

    override fun getWeatherOnLocation(location: Location) {
        openWeatherApi.getCurrentLocationWeather(location.latitude, location.longitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    city -> weatherView?.onSuccess(city)
                }, {
                    error -> weatherView?.onFailure()
                })
    }
}