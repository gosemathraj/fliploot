package com.gosemathraj.fliploot.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.gosemathraj.fliploot.data.remote.api.config.Error
import com.gosemathraj.fliploot.utils.NetworkUtils

abstract class BaseViewModel : ViewModel() {

    fun launchOnViewModelScope(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    class ApiCall<T> {
        lateinit var onSuccess: (data: T?) -> Unit
        lateinit var onEnqueue: suspend () -> T?
        lateinit var onError: (error: Error) -> Unit
    }

    suspend fun <X> apiCall(context: Context, call: ApiCall<X>.() -> Unit) {
        val apiCallFunction = ApiCall<X>()
        if (NetworkUtils(context).isNetworkConnected()) {
            try {
                call(apiCallFunction)
                val response = apiCallFunction.onEnqueue()
                if(response != null) {
                    apiCallFunction.onSuccess(response)
                } else {
                    apiCallFunction.onError(Error(Error.ErrorType.GENERAL_ERROR, "Something Went Wrong"))
                }
            } catch (ex : Exception) {
                when(ex) {
                    is IOException -> { apiCallFunction.onError(Error(Error.ErrorType.NETWORK_ERROR, "Network Error")) }
                    is HttpException -> { apiCallFunction.onError(Error(Error.ErrorType.GENERAL_ERROR,"Something Went Wrong")) }
                    else -> { apiCallFunction.onError(Error(Error.ErrorType.GENERAL_ERROR,"Something Went Wrong")) }
                }
            }
        } else {
            apiCallFunction.onError(Error(Error.ErrorType.NO_INTERNET_ERROR, "No Internet connection"))
        }
    }
}

