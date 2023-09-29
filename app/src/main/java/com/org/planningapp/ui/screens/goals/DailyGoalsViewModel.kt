package com.org.planningapp.ui.screens.goals

import androidx.lifecycle.ViewModel
import com.org.planningapp.data.repository.DailyGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyGoalsViewModel @Inject constructor(
    private val dailyGoalRepository: DailyGoalRepository
) : ViewModel() {


}