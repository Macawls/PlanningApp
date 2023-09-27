package com.org.planningapp.data.repository.impl

import com.org.planningapp.data.network.dto.DailyGoalDto
import com.org.planningapp.data.repository.DailyGoalRepository
import com.org.planningapp.domain.model.DailyGoal
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

const val DAILY_GOAL_TABLE_ID = "daily_goals"

class DailyGoalRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val goTrue: GoTrue
) : DailyGoalRepository {
    override suspend fun createDailyGoal(dailyGoal: DailyGoal): Boolean {
        return try {
            val user = goTrue.currentUserOrNull() ?: throw Exception("User not logged in")

            val dailyGoalDto = DailyGoalDto(
                createdAt = dailyGoal.createdAt,
                minDailyHours = dailyGoal.minDailyHours,
                maxDailyHours = dailyGoal.maxDailyHours,
            )

            postgrest[DAILY_GOAL_TABLE_ID].insert(dailyGoalDto)
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun getDailyGoals(): List<DailyGoalDto> {
        return postgrest[DAILY_GOAL_TABLE_ID].select().decodeList<DailyGoalDto>()
    }

    override suspend fun getDailyGoal(id: String): DailyGoalDto {
        return postgrest[DAILY_GOAL_TABLE_ID].select {
            eq("id", id)
        }.decodeSingle<DailyGoalDto>()
    }

    override suspend fun deleteDailyGoal(id: String) {
        postgrest[DAILY_GOAL_TABLE_ID].delete {
            eq("id", id)
        }
    }
}