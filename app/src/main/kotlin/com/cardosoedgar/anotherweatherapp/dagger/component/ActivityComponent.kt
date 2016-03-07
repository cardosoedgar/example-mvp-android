package com.cardosoedgar.anotherweatherapp.dagger.component

import com.cardosoedgar.anotherweatherapp.MainActivity
import com.cardosoedgar.anotherweatherapp.dagger.ActivityScope
import com.cardosoedgar.anotherweatherapp.dagger.LocationModule
import dagger.Subcomponent

/**
 * Created by edgarcardoso on 3/7/16.
 */
@ActivityScope
@Subcomponent(modules= arrayOf(LocationModule::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
}
