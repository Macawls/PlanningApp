package com.org.planningapp.ui.screens.timesheets.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.TimesheetRepository
import com.org.planningapp.domain.model.Timesheet
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddTimeSheetViewModel @Inject constructor(
    private val timesheetRepository: TimesheetRepository
) : ViewModel() {
    private val _startDate = MutableStateFlow<LocalDateTime>(Clock.System.now().toLocalDateTimeUTC())
    val startDate = _startDate

    private val _endDate = MutableStateFlow<LocalDateTime>(Clock.System.now().toLocalDateTimeUTC())
    val endDate = _endDate

    private val _date = MutableStateFlow<LocalDateTime>(Clock.System.now().toLocalDateTimeUTC())
    val date = _date

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    private val _description = MutableStateFlow("")
    val description = _description

    suspend fun addTimeSheet(timesheet: Timesheet) : Boolean {
        return viewModelScope.async {
            _isLoading.value = true
            val res = timesheetRepository.createTimesheet(timesheet)
            _isSuccess.value = res
            _isLoading.value = false
            res
        }.await()
    }

    fun onDateChange(date: LocalDateTime) {
        this._date.value = date
    }

    fun onStartDateChange(startDate: LocalDateTime) {
        this._startDate.value = startDate
    }

    fun onEndDateChange(endDate: LocalDateTime) {
        this._endDate.value = endDate
    }

    fun onDescriptionChange(description: String) {
        this._description.value = description
    }
}