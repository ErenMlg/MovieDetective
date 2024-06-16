package com.softcross.moviedetective.core.common.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun calculateRemainingDays(date: String): Long = ChronoUnit.DAYS.between(
    LocalDate.now(),
    LocalDate.parse(date)
)

fun String.convertToFormattedDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)
    val date = LocalDate.parse(this, inputFormatter)
    return date.format(outputFormatter)
}

fun String.convertToFormattedYear(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH)
    val date = LocalDate.parse(this, inputFormatter)
    return date.format(outputFormatter)
}
