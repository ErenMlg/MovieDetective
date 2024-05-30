package com.softcross.moviedetective.core.common

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun CalculateRemainingDays(date: String): Long = ChronoUnit.DAYS.between(
    LocalDate.now(),
    LocalDate.parse(date))