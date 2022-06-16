package com.airlinesdemoapp.data.repository

import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.data.api.RestAPI
import com.airlinesdemoapp.data.cache.InMemoryCache
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.domain.repository.AirlinesRepository
import io.reactivex.Single

class AirlinesRepositoryImpl(private val api: RestAPI) : AirlinesRepository {
    override fun getAirlines(pageNo: Int): Single<DataState<List<UserInfo>>> {
        val cache = InMemoryCache.get()
        val filteredData = ArrayList<UserInfo>()

        cache.forEach {
            filteredData.add(it)
        }

        if (filteredData != null && filteredData.isNotEmpty()) {
            return Single.just(DataState.Success(filteredData))
        }
        return api.getAirLines(pageNo)
            .map {
                val restList = ArrayList<UserInfo>()
                it.data.forEach { rest ->
                    val newRestaurant = UserInfo(
                        id = rest.id,
                        first_name = rest.first_name,
                        last_name = rest.last_name,
                        email = rest.email,
                        avatar = rest.avatar,
                    )
                    restList.add(newRestaurant)
                }
                InMemoryCache.add(restList)
                DataState.Success(restList)
            }
    }
}