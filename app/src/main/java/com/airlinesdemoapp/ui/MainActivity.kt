package com.airlinesdemoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airlinesdemoapp.R
import com.airlinesdemoapp.core.navigation.AppNavigator
import com.airlinesdemoapp.core.navigation.Screen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null)
            appNavigator.navigateTo(Screen.HOME)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // handle back press
        if(supportFragmentManager.backStackEntryCount == 0){
            finish()
        }
    }
}