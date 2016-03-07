package com.cardosoedgar.anotherweatherapp.retrofit

import com.cardosoedgar.anotherweatherapp.Model
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by edgarcardoso on 3/7/16.
 */
interface OpenWeather {

    @GET("weather?units=metric&appid=be14c6367245244b5fffaa2283642b25")
    fun getCurrentLocationWeather(@Query("lat") latitude : Double,
                                  @Query("lon") longitude: Double): Observable<Model.City>
}
