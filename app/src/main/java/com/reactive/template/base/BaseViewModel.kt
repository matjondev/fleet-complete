package com.reactive.template.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.reactive.template.R
import com.reactive.template.network.ApiInterface
import com.reactive.template.network.BaseResponse
import com.reactive.template.utils.extensions.loge
import com.reactive.template.utils.extensions.logi
import com.reactive.template.utils.network.Errors
import com.reactive.template.utils.preferences.SharedManager
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import retrofit2.HttpException

sealed class SharedActions<T> {
    data class Success<T>(val data: T) : SharedActions<T>()
    object Update : SharedActions<Nothing>()
    object None : SharedActions<Nothing>()
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

open class BaseViewModel(
    private val gson: Gson,
    private val context: Context,
    val sharedManager: SharedManager
) : ViewModel(), KoinComponent {

    @LayoutRes
    var parentLayoutId: Int = 0

    val shared: MutableLiveData<Any> by inject()
    val data = MutableLiveData<Result<BaseResponse>>()

    private val api: ApiInterface by inject(named("api"))

    private fun <T : BaseResponse> postValue(
        liveData: MutableLiveData<Result<T>>,
        data: T,
        onSuccess: (T) -> Unit = {}
    ) {
        liveData.postValue(Result.Loading)
        if (data.isSuccess()) {
            liveData.postValue(Result.Success(data))
            onSuccess(data)
        } else liveData.postValue(Result.Error(parseError(Throwable(data.exception?.message))))
    }

    private suspend fun <T : BaseResponse> catchError(
        liveData: MutableLiveData<Result<T>>,
        block: suspend () -> Unit
    ) {
        liveData.postValue(Result.Loading)
        try {
            block()
        } catch (ex: Throwable) {
            liveData.postValue(Result.Error(parseError(ex)))
        }
    }

    private fun parseError(e: Throwable?): String {
        var message = context.resources.getString(R.string.smth_wrong)
        if (e != null && e.localizedMessage != null) {
            loge(e.localizedMessage)
            if (e is HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                errorBody?.let {
                    try {
                        val errors = gson.fromJson(it, BaseResponse::class.java).exception

                        errors?.message?.let {
                            if (it.isNotBlank()) message = it
                        }
                    } catch (e: Exception) {
                        loge("Error in Parsings ${e.localizedMessage}")
                        e.printStackTrace()
                    }
                }
            } else message = Errors.traceErrors(e, context)
        }
        return message
    }


    fun fetchData() {
        if (sharedManager.token.isNotEmpty()) {
            logi("Current token : " + sharedManager.token)
        }
    }

    fun register() {
        viewModelScope.launch {
            catchError(data) {
                postValue(data, api.register())
            }
        }
    }
}