package com.org.planningapp.data.repository.impl

import com.org.planningapp.data.network.dto.DailyGoalDto
import com.org.planningapp.data.repository.DailyGoalRepository
import com.org.planningapp.domain.model.DailyGoal
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Inject

const val DAILY_GOAL_TABLE_ID = "daily_goals"

class DailyGoalRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
) : DailyGoalRepository {
    override suspend fun createDailyGoal(timesheet: DailyGoal): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyGoals(): List<DailyGoalDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyGoal(id: String): DailyGoalDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDailyGoal(id: String) {
        TODO("Not yet implemented")
    }
}