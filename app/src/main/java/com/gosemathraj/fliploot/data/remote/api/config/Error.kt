package com.gosemathraj.fliploot.data.remote.api.config

data class Error (
    var errorType : ErrorType? = null,
    var errorMessage : String? = null) {

    enum class ErrorType {
        NO_INTERNET_ERROR, NETWORK_ERROR, GENERAL_ERROR
    }
}