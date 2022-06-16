package com.airlinesdemoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // setup timber for debug mode
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
