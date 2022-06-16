package com.airlinesdemoapp.data.cache

import com.airlinesdemoapp.domain.entity.UserInfo

object InMemoryCache {
    private val cache = ArrayList<UserInfo>()

    fun get() = cache

    fun add(airlines: List<UserInfo>) {
        cache.addAll(airlines)
    }
}