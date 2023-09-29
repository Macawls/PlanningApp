package com.org.planningapp.ui.screens.timesheets.add

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.Timesheet
import com.org.planningapp.ui.components.LoadingButton
import com.org.planningapp.ui.screens.categories.CategoriesListViewModel
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimesheetScreen(
    navController: NavController,
    viewModel: AddTimeSheetViewModel = hiltViewModel(),
    categoryListViewModel: CategoriesListViewModel = hiltViewModel(),
) {
    val startDate = viewModel.startDate.collectAsState()
    val endDate = viewModel.endDate.collectAsState()
    val description = viewModel.description.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    val focusRequester = remember { FocusRequester() }
    var categoryName by remember { mutableStateOf("") }

    // get the categoryID from the route
    val categoryID = navController.previousBackStackEntry?.arguments?.getString("id") ?: ""

    val snackBarHostState = remember { SnackbarHostState() }



    val dateState = rememberDatePickerState(
        initialSelectedDateMillis = startDate.value.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
        initialDisplayedMonthMillis = endDate.value.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
        initialDisplayMode = DisplayMode.Input,
    )

    var showTimePicker by remember { mutableStateOf(false) }

    val startTimePickerState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0,
    )

    val endTimePickerState = rememberTimePickerState(
        initialHour = 13,
        initialMinute = 0,
    )

    val routineScope = rememberCoroutineScope()

    fun submitAddCategory(){
        routineScope.launch {
            val dateMillis = dateState.selectedDateMillis ?: 0L

            val dateLocalDateTime = Instant
                .fromEpochMilliseconds(dateMillis)
                .toLocalDateTimeUTC()

            val startLocalDateTime = LocalDateTime(
                year = dateLocalDateTime.year,
                monthNumber = dateLocalDateTime.monthNumber,
                dayOfMonth = dateLocalDateTime.dayOfMonth,
                hour = startTimePickerState.hour,
                minute = startTimePickerState.minute,
                second = 0,
                nanosecond = 0,
            )

            val endLocalDateTime = LocalDateTime(
                year = dateLocalDateTime.year,
                monthNumber = dateLocalDateTime.monthNumber,
                dayOfMonth = dateLocalDateTime.dayOfMonth,
                hour = endTimePickerState.hour,
                minute = endTimePickerState.minute,
                second = 0,
                nanosecond = 0,
            )

            val sheet = Timesheet(
                categoryId = categoryID,
                createdAt = Clock.System.now().toLocalDateTimeUTC(),
                description = description.value,
                startTime = startLocalDateTime,
                endTime = endLocalDateTime,
                date = dateLocalDateTime,
                imageUrl = null,
            )

            if (viewModel.addTimeSheet(sheet)) {
                navController.navigate("timesheets/${categoryID}")
            }
        }
    }
    LaunchedEffect(UInt){
        routineScope.launch{
            val category = categoryListViewModel.GetCategoryById(categoryID)
            categoryName = category.name
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("timesheets/${categoryID}")
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = "Add Timesheet for $categoryName",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        }
    ) {
        if (categoryName == ""){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 82.dp)
                    .padding(horizontal = 48.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 82.dp)
                    .padding(horizontal = 48.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = description.value,
                        onValueChange = { viewModel.onDescriptionChange(it) } ,
                        singleLine =  false,
                        maxLines = 3,
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                    DatePicker(
                        modifier = Modifier,
                        state = dateState,
                    )
                    Text(
                        text = "Start Time",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider( modifier = Modifier.padding(6.dp))
                    TimeInput(state = startTimePickerState)
                    Text(
                        text = "End Time",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider( modifier = Modifier.padding(6.dp))
                    TimeInput(state = endTimePickerState)
                    LoadingButton(
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = isLoading.value ,
                        buttonText = "Save Timesheet" ,
                        onClick = { submitAddCategory() },
                        enabled = description.value.isNotEmpty()
                                && startDate.value < endDate.value
                                && startTimePickerState.hour < endTimePickerState.hour
                    )
                }
            }
        }
    }
}