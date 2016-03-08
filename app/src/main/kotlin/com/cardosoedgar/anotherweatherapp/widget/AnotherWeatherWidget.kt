package com.cardosoedgar.anotherweatherapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.cardosoedgar.anotherweatherapp.R
import com.cardosoedgar.anotherweatherapp.retrofit.OpenWeather
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers

/**
 * Created by edgarcardoso on 3/8/16.
 */
class AnotherWeatherWidget: AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        val view = RemoteViews(context!!.packageName, R.layout.widget_layout)
        val sharedPref = context.getSharedPreferences(context.getString(R.string.shared_pref), Context.MODE_PRIVATE)
        val latitude = sharedPref.getString("latitude", "")
        val longitude = sharedPref.getString("longitude", "")

        if (!latitude.isEmpty() && !longitude.isEmpty()) {
            openWeatherAPI().getCurrentLocationWeather(latitude, longitude)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        city ->
                        view.setTextViewText(R.id.temp, "${Math.round(city.main.temp).toString()}º")
                        appWidgetManager!!.updateAppWidget(appWidgetIds, view)
                    }, { })
        }
    }

    fun openWeatherAPI() : OpenWeather {
        return retrofit().create(OpenWeather::class.java)
    }

    fun retrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}