package com.org.planningapp.ui.screens.categories

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.readableDate (): String {
    val date = this
    return "${date.dayOfMonth}/${date.monthNumber}/${date.year}" +
            " at ${date.hour}:${date.minute}"
}

fun Instant.toLocalDateTime() : LocalDateTime {
    return this.toLocalDateTime(TimeZone.currentSystemDefault())
}
