package com.org.planningapp.data.network.dto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimesheetDto(
    @SerialName("id")
    val id: String? = "",

    @SerialName("user_uid")
    val userUid: String,

    @SerialName("category_id")
    val categoryId: String,

    @SerialName("entry_time")
    val entryTime: LocalDateTime,

    @SerialName("start_time")
    val startTime: LocalDateTime,

    @SerialName("end_time")
    val endTime: LocalDateTime,

    @SerialName("description")
    val description: String,

    @SerialName("image_url")
    val imageUrl: String?,

    @SerialName("createdAt")
    val createdAt: LocalDateTime,
)
