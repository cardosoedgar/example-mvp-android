package com.cardosoedgar.anotherweatherapp.dagger.component

import com.cardosoedgar.anotherweatherapp.dagger.ActivityScope
import com.cardosoedgar.anotherweatherapp.dagger.PresenterModule
import com.cardosoedgar.anotherweatherapp.views.MainActivity
import dagger.Subcomponent

/**
 * Created by edgarcardoso on 3/11/16.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(PresenterModule::class))
interface  PresenterComponent {
    fun inject(mainActivity: MainActivity)
}