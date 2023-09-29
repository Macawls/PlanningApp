package com.org.planningapp.data.network.dto
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimesheetDto(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("category_id")
    val categoryId: String,

    @SerialName("date")
    val date: LocalDateTime,

    @SerialName("start_time")
    val startTime: LocalDateTime,

    @SerialName("end_time")
    val endTime: LocalDateTime,

    @SerialName("description")
    val description: String,

    @SerialName("image_url")
    val imageUrl: String?,

    @SerialName("created_at")
    val createdAt: LocalDateTime? = Clock.System.now().toLocalDateTime(TimeZone.UTC),
)
