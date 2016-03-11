package com.cardosoedgar.anotherweatherapp.dagger

import android.content.Context
import com.cardosoedgar.anotherweatherapp.views.location.LocationInterface
import com.cardosoedgar.anotherweatherapp.views.location.LocationPresenter
import com.cardosoedgar.anotherweatherapp.views.location.LocationView
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherInterface
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherPresenter
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherView
import dagger.Module
import dagger.Provides

/**
 * Created by edgarcardoso on 3/11/16.
 */
@ActivityScope
@Module
class PresenterModule(val context: Context){

    @Provides
    @ActivityScope
    fun providesLocationPresenter(): LocationInterface {
        return LocationPresenter(context as LocationView)
    }

    @Provides
    @ActivityScope
    fun providesWeatherPresenter(): WeatherInterface {
        return WeatherPresenter(context as WeatherView)
    }
}