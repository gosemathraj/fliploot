package com.gosemathraj.fliploot.data.remote.api.config

data class Resource<out T>(val status: Status, val data: T? = null, val message: String? = null, val error: Error? = null) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data = data)
        }

        fun <T> error(error: Error): Resource<T> {
            return Resource(Status.ERROR, error = error)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}