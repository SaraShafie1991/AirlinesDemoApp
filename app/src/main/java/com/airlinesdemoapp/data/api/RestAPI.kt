package com.airlinesdemoapp.data.api

import com.airlinesdemoapp.data.api_response.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPI {
    @GET("users")
    fun getAirLines(@Query("page") int: Int): Single<Response>
}