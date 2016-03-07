package com.cardosoedgar.anotherweatherapp.dagger

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

/**
 * Created by edgarcardoso on 3/7/16.
 */
@Module
class LocationModule(val context: Context) {

    @Provides
    @ActivityScope
    fun providesGooleApiClient(): GoogleApiClient {
        return GoogleApiClient.Builder(context)
                .addConnectionCallbacks(context as ConnectionCallbacks)
                .addOnConnectionFailedListener(context as OnConnectionFailedListener)
                .addApi(LocationServices.API)
                .build()
    }
}