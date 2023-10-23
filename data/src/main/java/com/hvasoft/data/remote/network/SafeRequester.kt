package com.hvasoft.data.remote.network

import com.hvasoft.domain.common.Resource
import com.hvasoft.domain.common.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Allows to make a request in a safe way catching possible errors
 * and sending report to app center and prints log with timber
 * @return Result<T>: returns a Result wrapper with the given expected data
 */
suspend fun <T : Any> makeSafeRequest(
    execute: suspend () -> Response<T>
): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.success(body)
            } else {
                Resource.error(message = response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.localizedMessage ?: "Something went wrong")
        }
    }
}

/**
 * Extension function to retrieve only the success value from Resource
 */
fun <T> Resource<T>.getSuccess(): T? {
    return if (this.status == Status.SUCCESS) data else null
}