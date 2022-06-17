package com.airlinesdemoapp.domain.interactor

import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.data.repository.ReturnedData
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.domain.error.ErrorHandler
import com.airlinesdemoapp.domain.error.Failure
import com.airlinesdemoapp.domain.repository.Repository
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class GetAirlines @Inject constructor(private val repository: Repository)
    : Usecase<Int, Single<DataState<ReturnedData>>>, ErrorHandler {
    override fun execute(para: Int): Single<DataState<ReturnedData>> {
        return repository.getAirlines(para).onErrorReturn {
            DataState.Error(getError(it))
        }
    }

    override fun getError(throwable: Throwable): Failure {
        return when (throwable) {
            is UnknownHostException -> Failure.NetworkConnectionError
            is HttpException -> {
                when (throwable.code()) {
                    // not found 404
                    HttpURLConnection.HTTP_NOT_FOUND -> Failure.ServerError.NotFound
                    // access denied 403
                    HttpURLConnection.HTTP_FORBIDDEN -> Failure.ServerError.AccessDenied
                    // unavailable service 503
                    HttpURLConnection.HTTP_UNAVAILABLE -> Failure.ServerError.ServiceUnavailable
                    // Server error 500
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> Failure.ServerError.InternalServerError
                    // all the others will be treated as unknown error
                    else -> Failure.UnknownError
                }
            }
            else -> Failure.UnknownError
        }
    }
}
