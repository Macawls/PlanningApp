package com.org.planningapp.data.network.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyGoalDto(
    @SerialName("id")
    val id: String? = "",

    @SerialName("user_id")
    val userId: String,

    @SerialName("min_daily_hours")
    val minDailyHours: Int,

    @SerialName("max_daily_hours")
    val maxDailyHours: Int,

    @SerialName("created_at")
    val createdAt: LocalDateTime,
)
