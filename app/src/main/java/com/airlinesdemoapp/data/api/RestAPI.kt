package com.airlinesdemoapp.data.api

import com.airlinesdemoapp.data.api_response.Response
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface RestAPI {
    @GET("users")
    fun getAirLines(@Query("page") int: Int): Single<Response>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") int: Int): Single<Response>

    @PUT("users/{id}")
    fun updateUser(@Path("id") int: Int, @Body requestBody: RequestBody): Single<Response>
}