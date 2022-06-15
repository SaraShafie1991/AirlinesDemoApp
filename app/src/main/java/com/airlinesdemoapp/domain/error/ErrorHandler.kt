package com.airlinesdemoapp.domain.error

interface ErrorHandler {
    fun getError(throwable: Throwable) : Failure
}