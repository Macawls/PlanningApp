package com.org.planningapp.data.repository

import com.org.planningapp.data.network.dto.TimesheetDto
import com.org.planningapp.domain.model.Timesheet

interface TimesheetRepository {
    suspend fun createTimesheet(timesheet: Timesheet): Boolean
    suspend fun getAllTimesheets(): List<TimesheetDto>

    suspend fun getTimesheetsFromCategory(categoryID: String): List<TimesheetDto>

    suspend fun getTimesheet(id: Int): TimesheetDto
    suspend fun deleteTimesheet(id: Int)
}