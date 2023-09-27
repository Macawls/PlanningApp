package com.org.planningapp.data.repository.impl

import com.org.planningapp.data.network.dto.TimesheetDto
import com.org.planningapp.data.repository.TimesheetRepository
import com.org.planningapp.domain.model.Timesheet
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Inject

const val TIMESHEET_TABLE_ID = "timesheets"

class TimesheetRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
) : TimesheetRepository {

    override suspend fun createTimesheet(timesheet: Timesheet): Boolean {
        return try {
            val timesheetDto = TimesheetDto(
                createdAt = timesheet.createdAt,
                categoryId = timesheet.categoryId,

                date = timesheet.date,
                startTime = timesheet.startTime,
                endTime = timesheet.endTime,

                description = timesheet.description,
                imageUrl = timesheet.imageUrl
            )

            postgrest[TIMESHEET_TABLE_ID].insert(timesheetDto)
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun getTimesheets(): List<TimesheetDto> {
        return postgrest[TIMESHEET_TABLE_ID].select().decodeList()
    }

    override suspend fun getTimesheet(id: String): TimesheetDto {
        return postgrest[TIMESHEET_TABLE_ID].select {
            eq("id", id)
        }.decodeSingle<TimesheetDto>()
    }

    override suspend fun deleteTimesheet(id: String) {
        postgrest[TIMESHEET_TABLE_ID].delete {
            eq("id", id)
        }
    }
}