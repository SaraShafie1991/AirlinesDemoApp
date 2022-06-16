package com.airlinesdemoapp.domain.repository

import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.domain.entity.UserInfo
import io.reactivex.Single

interface AirlinesRepository {
    fun getAirlines(pageNo:Int) : Single<DataState<List<UserInfo>>>
}