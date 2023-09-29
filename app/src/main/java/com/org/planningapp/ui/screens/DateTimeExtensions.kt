package com.org.planningapp.ui.screens

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.readableDate (): String {
    val date = this
    return "${date.dayOfMonth}/${date.monthNumber}/${date.year}" +
            " at ${date.hour}:${date.minute} ${if (date.isAM()) "AM" else "PM"}"
}

fun Instant.toLocalDateTimeUTC() : LocalDateTime {
    return this.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.isAM(): Boolean {
    return this.hour < 12
}
