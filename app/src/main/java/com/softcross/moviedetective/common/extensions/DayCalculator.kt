package com.softcross.moviedetective.common.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun calculateRemainingDays(date: String): Long {
    val remainDay = ChronoUnit.DAYS.between(
        LocalDate.now(),
        LocalDate.parse(date)
    )
    return if (remainDay < 0) {
        0
    } else {
        remainDay
    }
}

fun String.convertToFormattedDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
    val date = LocalDate.parse(this, inputFormatter)
    return date.format(outputFormatter)
}

fun String.convertToFormattedYear(): String {
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(this, inputFormatter)
        return date.format(outputFormatter)
    }catch (e: Exception){
        return "N/A"
    }
}
