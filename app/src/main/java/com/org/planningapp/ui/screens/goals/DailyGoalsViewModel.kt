package com.org.planningapp.ui.screens.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.DailyGoalRepository
import com.org.planningapp.domain.model.DailyGoal
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class DailyGoalsViewModel @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) : ViewModel() {

    private val _dailyGoals = MutableStateFlow<List<DailyGoal>>(listOf())
    val dailyGoals = _dailyGoals

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess

    private val _minHours = MutableStateFlow(0)
    val minHours = _minHours

    private val _maxHours = MutableStateFlow(0)
    val maxHours = _maxHours

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    suspend fun addDailyGoal(goal: DailyGoal) : Boolean {
        return viewModelScope.async {
            _isLoading.value = true
            val res = dailyGoalRepository.createDailyGoal(goal)
            _isSuccess.value = res
            _isLoading.value = false
            res
        }.await()
    }

    fun DeleteGoal(goal: DailyGoal) {
        viewModelScope.launch {
            if (goal.id == null) return@launch
            dailyGoalRepository.deleteDailyGoal(goal.id)
            getGoals()
        }
    }

    init {
        getGoals()
    }

    fun refresh() {
        getGoals()
    }

    private fun getGoals(){
        viewModelScope.launch {
            val goalDtos = dailyGoalRepository.getDailyGoals()

            _dailyGoals.emit(
                goalDtos.map {
                    DailyGoal(
                        id = it.id ?: 0,
                        createdAt = it.createdAt ?: Clock.System.now().toLocalDateTimeUTC(),
                        minDailyHours = it.minDailyHours,
                        maxDailyHours = it.maxDailyHours,
                    )
                }
            )

            _isLoading.emit(false)
        }
    }

}