package com.cardosoedgar.anotherweatherapp

/**
 * Created by edgarcardoso on 3/7/16.
 */
object Model {
    data class City(val name: String,
                    val id: String,
                    val base: String,
                    val dt: Int,
                    val code: Int,
                    val coord: Coordinate,
                    val weather: Array<Weather>,
                    val main: Main,
                    val wind: Wind,
                    val clouds: Clouds,
                    val sys: Sys)

    data class Weather(val id: Int,
                       val main: String,
                       val description: String,
                       val icon: String)

    data class Coordinate(val lon: Float,
                          val lat: Float)

    data class Main(val temp: Float,
                    val pressure: Float,
                    val humidity: Int,
                    val temp_max: Float,
                    val temp_min: Float,
                    val sea_level: Float,
                    val grnd_level: Float)

    data class Wind(val speed: Float,
                    val deg: Float)

    data class Clouds(val all: Int)

    data class Sys(val message: Float,
                   val country: String,
                   val sunrise: Int,
                   val sunset: Int)
}