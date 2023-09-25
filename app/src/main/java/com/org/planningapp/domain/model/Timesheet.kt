package com.org.planningapp.domain.model
import kotlinx.datetime.LocalDateTime

data class Timesheet(
    val id: String,
    val userUid: String,
    val categoryId: String,
    val entryTime: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val description: String,
    val imageUrl: String?,
    val createdAt: LocalDateTime,
)