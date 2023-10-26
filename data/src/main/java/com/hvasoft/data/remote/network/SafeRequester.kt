package com.hvasoft.data.remote.network

import com.hvasoft.domain.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Allows to make a request in a safe way catching possible errors
 * and sending report to app center and prints log with timber
 * @return Resource<T>: returns a Resource wrapper with the given expected data
 */
suspend fun <T : Any> makeSafeRequest(
    execute: suspend () -> Response<T>
): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Error(exception = null, errorMessage = response.message())
            }
        } catch (exception: Exception) {
            Resource.Error(exception = exception, errorMessage = exception.message)
        }
    }
}

/**
 * Extension function to retrieve only the success value from Resource
 */
fun <T> Resource<T>.getSuccess(): T? {
    return if (this is Resource.Success)
        this.data
    else null

}