package com.org.planningapp.ui.screens.timesheets.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.Timesheet
import com.org.planningapp.ui.components.ComponentTopAppBar
import com.org.planningapp.ui.components.LoadingButton
import com.org.planningapp.ui.screens.categories.CategoriesListViewModel
import com.org.planningapp.ui.screens.readableTime
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTimePickerDialog(
    state: TimePickerState,
    routineScope: CoroutineScope,
    snackState: SnackbarHostState,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    msg: String
){
    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    TimePickerDialog(
        onCancel = { onCancel() },
        onConfirm = {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, state.hour)
            cal.set(Calendar.MINUTE, state.minute)
            cal.isLenient = false
            routineScope.launch {
                snackState.showSnackbar("$msg ${formatter.format(cal.time)}")
            }
            onConfirm()
        }
    ) {
        TimePicker(state = state)
    }
}


@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}


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

    var showPickerOne by remember { mutableStateOf(false) }
    var showPickerTwo by remember { mutableStateOf(false) }

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

    val formatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

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
            ComponentTopAppBar(
                navController = navController,
                route = "timesheets/${categoryID}",
                title = "Add Timesheet for $categoryName!"
            )
        }
    ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 82.dp)
                    .padding(horizontal = 48.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (categoryName == "") {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else {
                    if (showPickerOne){
                        ShowTimePickerDialog(
                            state = startTimePickerState,
                            routineScope = routineScope ,
                            snackState = snackBarHostState,
                            onCancel = { showPickerOne = false },
                            onConfirm = { showPickerOne = false },
                            msg = "Entered Start Time of "
                        )
                    }
                    if (showPickerTwo){
                        ShowTimePickerDialog(
                            state = endTimePickerState,
                            routineScope = routineScope ,
                            snackState = snackBarHostState,
                            onCancel = { showPickerTwo = false },
                            onConfirm = { showPickerTwo = false },
                            msg = "Entered End Time of "
                        )
                    }
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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { showPickerOne = true }
                            ) {
                                Text("Set Start Time")
                            }
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(text = "${startTimePickerState.hour} : ${readableTime(startTimePickerState.minute)}")
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .border(2.dp, MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.shapes.small)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Button(
                                onClick = { showPickerTwo = true }
                            ) {
                                Text("Set End Time")
                            }
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(
                                text = "${endTimePickerState.hour} : ${readableTime(endTimePickerState.minute)}"
                            )
                        }
                        LoadingButton(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxWidth(),
                            isLoading = isLoading.value ,
                            buttonText = "Save Timesheet" ,
                            onClick = { submitAddCategory() },
                            enabled = description.value.isNotEmpty()
                                    && startDate.value < endDate.value
                        )
                    }
                }
            }
    }
}