package com.org.planningapp.ui.screens.goals.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.DailyGoalRepository
import com.org.planningapp.domain.model.DailyGoal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddDailyGoalViewModel @Inject constructor(
    private val repository: DailyGoalRepository
) : ViewModel() {

    private val _minHoursString = MutableStateFlow("")
    val minHoursString = _minHoursString

    private val _maxHoursString = MutableStateFlow("")
    val maxHoursString = _maxHoursString

    private val _minHours = MutableStateFlow(0)
    val minHours = _minHours

    private val _maxHours = MutableStateFlow(0)
    val maxHours = _maxHours

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess

    suspend fun addDailyGoal(goal: DailyGoal): Boolean {
        return viewModelScope.async {
            _isLoading.value = true
            val res = repository.createDailyGoal(goal)
            _isSuccess.value = res
            _isLoading.value = false
            res
        }.await()
    }

    fun onMinHoursChange(hours: String) {
        _minHoursString.value = hours
        this._minHours.value = _minHoursString.value.toInt()
    }

    fun onMaxHoursChange(hours: String) {
        _maxHoursString.value = hours
        this._maxHours.value = _maxHoursString.value.toInt()
    }
}