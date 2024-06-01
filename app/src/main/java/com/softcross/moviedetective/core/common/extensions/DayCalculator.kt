package com.softcross.moviedetective.core.common.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun calculateRemainingDays(date: String): Long = ChronoUnit.DAYS.between(
    LocalDate.now(),
    LocalDate.parse(date))