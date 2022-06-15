package com.airlinesdemoapp.domain.error

sealed class Failure {
    object NetworkConnectionError : Failure()
    sealed class ServerError : Failure(){
        object  NotFound : ServerError()
        object  ServiceUnavailable : ServerError()
        object  AccessDenied : ServerError()
    }
    object  UnknownError : Failure()
}