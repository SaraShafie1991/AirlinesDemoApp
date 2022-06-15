package com.airlinesdemoapp.core.navigation

import com.airlinesdemoapp.data.api_response.AirLine

interface AppNavigator {
    fun navigateTo(screen:Screen, airLine: AirLine? = null)
}
enum class Screen {
    HOME,
    DetailsScreen,
    EditScreen
}