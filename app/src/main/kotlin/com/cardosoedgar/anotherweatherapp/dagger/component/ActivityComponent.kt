package com.cardosoedgar.anotherweatherapp.dagger.component

import com.cardosoedgar.anotherweatherapp.views.location.LocationPresenter
import com.cardosoedgar.anotherweatherapp.dagger.ActivityScope
import com.cardosoedgar.anotherweatherapp.dagger.LocationModule
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherPresenter
import dagger.Subcomponent

/**
 * Created by edgarcardoso on 3/7/16.
 */
@ActivityScope
@Subcomponent(modules= arrayOf(LocationModule::class))
interface ActivityComponent {
    fun inject(weatherPresenter: WeatherPresenter)
    fun inject(locationPresenter: LocationPresenter)
}
