package com.airlinesdemoapp.data.cache

import com.airlinesdemoapp.data.api_response.AirLine

object InMemoryCache {
    private val cache = ArrayList<AirLine>()

    fun get() = cache

    fun add(restaurants: List<AirLine>) {
        cache.addAll(restaurants)
    }
}