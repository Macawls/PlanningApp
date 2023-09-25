package com.org.planningapp.data.repository

import com.org.planningapp.data.network.dto.TimesheetDto
import com.org.planningapp.domain.model.Timesheet

interface TimesheetRepository {
    suspend fun createTimesheet(timesheet: Timesheet): Boolean
    suspend fun getTimesheets(): List<TimesheetDto>
    suspend fun getTimesheet(id: String): TimesheetDto
    suspend fun deleteTimesheet(id: String)
}