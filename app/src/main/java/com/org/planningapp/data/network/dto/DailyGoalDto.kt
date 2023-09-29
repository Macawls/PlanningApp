package com.org.planningapp.data.network.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyGoalDto(
    @SerialName("id")
    val id: Int? = 0,

    @SerialName("min_daily_hours")
    val minDailyHours: Int,

    @SerialName("max_daily_hours")
    val maxDailyHours: Int,

    @SerialName("created_at")
    val createdAt: LocalDateTime? = Clock.System.now().toLocalDateTime(TimeZone.UTC),
)
