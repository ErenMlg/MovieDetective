package com.softcross.moviedetective.common.extensions

fun List<Int>.listToString(): String {
    return this.toString().replace("[", "").replace("]", "").replace(" ", "")
}

fun String.toIntList(): MutableList<Int> {
    return this.split(",").map { it.trim().toInt() }.toMutableList()
}

fun <T> MutableList<T>.swap(from: Int, to: Int) {
    val temp = this[from]
    this[from] = this[to]
    this[to] = temp
}