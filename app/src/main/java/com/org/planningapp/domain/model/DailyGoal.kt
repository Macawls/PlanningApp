package com.org.planningapp.domain.model
import kotlinx.datetime.LocalDateTime

data class DailyGoal(
    val id: String,
    val userUid: String,
    val minDailyHours: Int,
    val maxDailyHours: Int,
    val createdAt: LocalDateTime
)
