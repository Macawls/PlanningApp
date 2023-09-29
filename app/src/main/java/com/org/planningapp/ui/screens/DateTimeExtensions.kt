package com.org.planningapp.ui.screens

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.readableDateAndTime (): String {
    val date = this
    return "${date.dayOfMonth}/${date.monthNumber}/${date.year}" +
            " at ${date.hour}:${date.minute} ${if (date.isAM()) "AM" else "PM"}"
}
fun LocalDateTime.readableDate(): String {
    val dayOfMonth = this.dayOfMonth
    val month = getDisplayName(this.month.ordinal)
    return "$dayOfMonth${getOrdinalIndicator(dayOfMonth)} of $month"
}

fun LocalDateTime.readableTime(): String {
    val date = this;
    val minute =
    return "${date.hour}:${minute} ${if (date.isAM()) "AM" else "PM"}"
}

fun readableTime(minutes:Int): String {
    return if (minutes > 10) minutes.toString() else minutes.toString() + "0"
}

fun getDisplayName(month: Int): String {
    return when (month) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> ""
    }
}

fun getOrdinalIndicator(day: Int): String {
    return when {
        day in 11..13 -> "th"
        day % 10 == 1 -> "st"
        day % 10 == 2 -> "nd"
        day % 10 == 3 -> "rd"
        else -> "th"
    }
}

fun Instant.toLocalDateTimeUTC() : LocalDateTime {
    return this.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.isAM(): Boolean {
    return this.hour < 12
}
