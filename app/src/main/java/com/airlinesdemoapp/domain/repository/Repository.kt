package com.airlinesdemoapp.domain.repository

import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.data.repository.ReturnedData
import com.airlinesdemoapp.domain.interactor.UpdateData
import io.reactivex.Single

interface Repository {
    fun getAirlines(pageNo:Int) : Single<DataState<ReturnedData>>
    fun deleteUser(id:Int) : Single<DataState<String>>
    fun updateUser(updateData: UpdateData) : Single<DataState<String>>
}