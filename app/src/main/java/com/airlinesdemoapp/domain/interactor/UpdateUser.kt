package com.airlinesdemoapp.domain.interactor

import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.domain.error.ErrorHandler
import com.airlinesdemoapp.domain.error.Failure
import com.airlinesdemoapp.domain.repository.Repository
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class UpdateUser @Inject constructor(private val repository: Repository)
    : Usecase<UpdateData, Single<DataState<String>>>, ErrorHandler {
    override fun execute(para: UpdateData): Single<DataState<String>> {
        return repository.updateUser(para).onErrorReturn {
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


class UpdateData(val id:Int, val text:String)
