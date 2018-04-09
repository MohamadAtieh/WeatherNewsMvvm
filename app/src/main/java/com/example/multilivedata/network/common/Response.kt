package com.example.multilivedata.network.common

enum class ResponseStatus {
    HAS_CONTENT(),
    NO_CONTENT(),
    ERROR(),
    LOADING()
}

interface ResponseStatusInterface {
    val responseStatus: ResponseStatus
}

data class Response<out T>(
        val data: T?,
        val message: String?,
        val error: Exception?,
        override val responseStatus: ResponseStatus
) : ResponseStatusInterface {
    companion object Helper {
        fun isLoading() = Response(null, null, null, ResponseStatus.LOADING)

        fun hasFailed(throwable: Throwable?)
            = Response(null, null, Exception(throwable), ResponseStatus.ERROR)
    }
}