package com.cardosoedgar.anotherweatherapp.views.location

/**
 * Created by edgarcardoso on 3/11/16.
 */
interface LocationInterface {
    fun onPermissionAccepted()
    fun getPermissionRequestCode(): Int
    fun onStart()
    fun onStop()
    fun onDestroy()
    fun requestLocation()
}