package com.softcross.moviedetective.common.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.regex.Pattern

fun String.emailRegex(): Boolean {
    val pattern = Pattern.compile(
        "^[A-Za-z](?=\\S+\$)(.*)([@]{1})(?=\\S+\$)(.{1,})(\\.)(.{1,})"
    )
    return if (this.isNotEmpty()) {
        pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.nameSurnameRegex(): Boolean {
    val pattern = Pattern.compile(
        "^[a-zA-Z]{2,}\$"
    )
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}

fun String.passwordRegex(): Boolean {
    val pattern = Pattern.compile(
        "^(?=.*[0-9])(?=\\S+\$)(?=.*[a-z])(?=.*[A-Z]).{8,}\$"
    )
    return if (this.isNotEmpty()) {
        return pattern.matcher(this).matches()
    } else {
        false
    }
}

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
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(this, inputFormatter)
        return date.format(outputFormatter)
    }catch (e: Exception){
        return "N/A"
    }
}

fun String.dateTimeToFormattedDate(): String {
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(this, inputFormatter)
        return date.format(outputFormatter)
    }catch (e: Exception){
        return "N/A"
    }
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

