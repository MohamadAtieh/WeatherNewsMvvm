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
        const val FETCH_SUCCESSFUL = "Result Successful"
        const val FETCH_FAILED = "Failed to Fetch"
        const val FETCH_EMPTY = "No results found"

        fun isLoading() = Response(null, null, null, ResponseStatus.LOADING)

        fun hasFailed(msg: String?, throwable: Throwable?)
            = Response(null, msg, Exception(throwable), ResponseStatus.ERROR)
    }
}