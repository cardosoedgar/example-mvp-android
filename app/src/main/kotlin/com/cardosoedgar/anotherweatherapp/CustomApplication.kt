package com.cardosoedgar.anotherweatherapp

import android.app.Application
import com.cardosoedgar.anotherweatherapp.dagger.component.AppComponent
import com.cardosoedgar.anotherweatherapp.dagger.AppModule
import com.cardosoedgar.anotherweatherapp.dagger.component.DaggerAppComponent

/**
 * Created by edgarcardoso on 3/7/16.
 */
class CustomApplication: Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(this)).build()
    }
}