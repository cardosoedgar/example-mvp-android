package com.cardosoedgar.anotherweatherapp.dagger.component

import com.cardosoedgar.anotherweatherapp.dagger.AppModule
import com.cardosoedgar.anotherweatherapp.dagger.ActivityModule
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by edgarcardoso on 3/7/16.
 */
@Singleton
@Component(modules= arrayOf(AppModule::class))
interface AppComponent {
    fun inject(weatherPresenter: WeatherPresenter)

    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}