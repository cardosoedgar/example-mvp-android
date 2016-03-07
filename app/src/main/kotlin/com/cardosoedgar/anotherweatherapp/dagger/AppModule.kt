package com.cardosoedgar.anotherweatherapp.dagger

import android.app.Application
import com.cardosoedgar.anotherweatherapp.retrofit.OpenWeather
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by edgarcardoso on 3/7/16.
 */
@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplication() : Application {
        return application;
    }

    @Provides
    @Singleton
    fun providesRetrofit() : Retrofit {
       return Retrofit.Builder()
               .baseUrl("http://api.openweathermap.org/data/2.5/")
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
               .build()
    }

    @Provides
    @Singleton
    fun providesOpenWeather(retrofit: Retrofit): OpenWeather {
        return retrofit.create(OpenWeather::class.java)
    }
}