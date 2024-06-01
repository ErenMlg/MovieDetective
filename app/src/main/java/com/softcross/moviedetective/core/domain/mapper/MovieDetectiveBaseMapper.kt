package com.softcross.moviedetective.core.domain.mapper

interface MovieDetectiveBaseMapper<I, O> {
    fun map(input: I): O
}