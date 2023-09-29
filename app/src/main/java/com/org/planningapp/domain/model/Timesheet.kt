package com.org.planningapp.domain.model
import kotlinx.datetime.LocalDateTime

data class Timesheet(
    val id: Int? = null,
    val categoryId: String,
    val date: LocalDateTime,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val description: String,
    val imageUrl: String?,
    val createdAt: LocalDateTime,
)