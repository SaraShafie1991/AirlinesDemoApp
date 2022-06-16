package com.airlinesdemoapp.domain.interactor

interface Usecase<T,R> {
    fun execute(para:T): R
}