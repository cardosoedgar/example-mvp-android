package com.cardosoedgar.anotherweatherapp.views.location

import android.location.Location

/**
 * Created by edgarcardoso on 3/11/16.
 */
interface LocationView {
    fun onLocationProvided(location : Location)
    fun requestPermission(permission: String, requestCode: Int)
}