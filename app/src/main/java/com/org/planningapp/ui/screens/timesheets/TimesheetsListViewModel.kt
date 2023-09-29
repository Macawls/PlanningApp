package com.org.planningapp.ui.screens.timesheets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.org.planningapp.data.repository.TimesheetRepository
import com.org.planningapp.domain.model.Timesheet
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class TimesheetsListViewModel @Inject constructor(
    private val timesheetRepository: TimesheetRepository
) : ViewModel() {

    private val _timesheetsList = MutableStateFlow<List<Timesheet>>(listOf())
    val timesheetsList = _timesheetsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading

    init {
        getTimesheets()
    }

    fun refresh(){
        getTimesheets()
    }

    private fun getTimesheets(){
        viewModelScope.launch {
            val timesheetDtos = timesheetRepository.getAllTimesheets()
            _timesheetsList.emit(timesheetDtos.map { it ->
                Timesheet(
                    id = it.id ?: 0,
                    createdAt = it.createdAt ?: Clock.System.now().toLocalDateTimeUTC(),
                    categoryId = it.categoryId,
                    date = it.date,
                    startTime = it.startTime,
                    endTime = it.endTime,
                    description = it.description,
                    imageUrl = it.imageUrl
                )
            }

            )

            _isLoading.emit(false)
        }
    }

    fun removeTimesheet(timesheet: Timesheet){
        viewModelScope.launch {
            val newList = mutableListOf<Timesheet>().apply { addAll(_timesheetsList.value) }
            newList.remove(timesheet)
            _timesheetsList.emit(newList.toList())

            // use repository to delete


            timesheet.id?.let { timesheetRepository.deleteTimesheet(id = it) }

            // fetch again
            getTimesheets()
        }
    }
}
