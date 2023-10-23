package com.hvasoft.data.mapper

/**
 * Interface to map some class from one type to another type
 * Where I means INPUT, the class base
 * Where O means OUTPUT, the class to be mapped
 */
interface Mapper<I, O> {
    fun map(input: I): O
}

// Non-nullable to Non-nullable
interface ListMapper<I, O>: Mapper<List<I>, List<O>>
