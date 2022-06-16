package com.airlinesdemoapp.core.navigation

import com.airlinesdemoapp.domain.entity.UserInfo


interface AppNavigator {
    fun navigateTo(screen:Screen, airline: UserInfo? = null)
}

enum class Screen {
    HOME,
    DETAILS
}
