package com.cardosoedgar.anotherweatherapp.dagger

import android.content.Context
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherInterface
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherPresenter
import com.cardosoedgar.anotherweatherapp.views.weather.WeatherView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

/**
 * Created by edgarcardoso on 3/11/16.
 */
@ActivityScope
@Module
class ActivityModule(val context: Context) {

    @Provides
    @ActivityScope
    fun providesWeatherPresenter(): WeatherInterface {
        return WeatherPresenter(context as WeatherView)
    }

    @Provides
    @ActivityScope
    fun providesGooleApiClient(): GoogleApiClient {
        return GoogleApiClient.Builder(context)
                .addConnectionCallbacks(context as GoogleApiClient.ConnectionCallbacks)
                .addOnConnectionFailedListener(context as GoogleApiClient.OnConnectionFailedListener)
                .addApi(LocationServices.API)
                .build()
    }
}