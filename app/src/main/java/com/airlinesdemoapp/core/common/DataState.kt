package com.airlinesdemoapp.core.common

import com.airlinesdemoapp.domain.error.Failure

sealed class DataState<out T> {
    data class Success<out T>(val data:T) : DataState<T>()
    data class Error(val error: Failure) : DataState<Nothing>()
    object loading : DataState<Nothing>()
}