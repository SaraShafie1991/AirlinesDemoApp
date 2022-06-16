package com.airlinesdemoapp.core.navigation

import androidx.fragment.app.FragmentActivity
import com.airlinesdemoapp.R
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.ui.feature.details.DetailsFragment
import com.airlinesdemoapp.ui.feature.home.HomeFragment
import javax.inject.Inject

class AppNavigatorImp @Inject constructor(val activity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: Screen, user: UserInfo?) {
        val fragment = when(screen){
            Screen.HOME -> HomeFragment()
            Screen.DETAILS -> DetailsFragment.newInstance(user)
        }
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.home_container, fragment)
            .addToBackStack(fragment.javaClass.canonicalName)
            .commit()
    }
}