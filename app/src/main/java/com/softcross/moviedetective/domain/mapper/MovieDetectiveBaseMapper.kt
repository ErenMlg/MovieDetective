package com.softcross.moviedetective.domain.mapper

interface MovieDetectiveBaseMapper<I, O> {
    fun map(input: I): O
}