package com.org.planningapp.data.repository

import com.org.planningapp.data.network.dto.DailyGoalDto
import com.org.planningapp.domain.model.DailyGoal

interface DailyGoalRepository {
    suspend fun createDailyGoal(dailyGoal: DailyGoal): Boolean
    suspend fun getDailyGoals(): List<DailyGoalDto>
    suspend fun getDailyGoal(id: String): DailyGoalDto
    suspend fun deleteDailyGoal(id: String)
}