package com.org.planningapp.data.network.dto

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
        @SerialName("id")
        val id: String? = "",

        @SerialName("name")
        val name: String,

        @SerialName("created_at")
        val createdAt: LocalDateTime? = Clock.System.now().toLocalDateTime(TimeZone.UTC),
)
