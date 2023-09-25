package com.org.planningapp.domain.model
import kotlinx.datetime.LocalDateTime

data class Category(
    val id: String,
    val name: String,
    val createdAt: LocalDateTime,
)
