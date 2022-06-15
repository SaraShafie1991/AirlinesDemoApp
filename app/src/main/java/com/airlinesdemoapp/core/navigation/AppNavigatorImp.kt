package com.airlinesdemoapp.core.navigation

import androidx.fragment.app.FragmentActivity
import com.airlinesdemoapp.R
import com.airlinesdemoapp.data.api_response.AirLine
import javax.inject.Inject

class AppNavigatorImp @Inject constructor(val activity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screen, airLine: AirLine?) {
//        val fragment = when(screen){
//            Screen.DetailsScreen ->
//            Screen.EditScreen ->
//        }
//        activity.supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.home_container, fragment)
//            .addToBackStack(fragment.javaClass.canonicalName)
//            .commit()
    }
}