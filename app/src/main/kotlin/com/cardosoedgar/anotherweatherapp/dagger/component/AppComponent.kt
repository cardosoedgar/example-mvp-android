package com.cardosoedgar.anotherweatherapp.dagger.component

import com.cardosoedgar.anotherweatherapp.dagger.AppModule
import com.cardosoedgar.anotherweatherapp.dagger.LocationModule
import com.cardosoedgar.anotherweatherapp.dagger.PresenterModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by edgarcardoso on 3/7/16.
 */
@Singleton
@Component(modules= arrayOf(AppModule::class))
interface AppComponent {
    fun activityComponent(locationModule: LocationModule): ActivityComponent
    fun presenterComponent(presenterModule: PresenterModule): PresenterComponent
}