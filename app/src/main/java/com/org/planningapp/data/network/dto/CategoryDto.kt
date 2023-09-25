package com.org.planningapp.data.network.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
        @SerialName("id")
        val id: String,

        @SerialName("user_uid")
        val userUid: String,

        @SerialName("name")
        val name: String,

        @SerialName("created_at")
        val createdAt: LocalDateTime,
)
