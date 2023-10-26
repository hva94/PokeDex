package com.hvasoft.domain.common

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T : Any>(val exception: Exception?, val errorMessage: String?) : Resource<T>()
    class Loading<T : Any> : Resource<T>()
}
