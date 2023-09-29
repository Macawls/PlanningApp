package com.org.planningapp.ui.screens.timesheets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.Timesheet
import com.org.planningapp.ui.components.AddFloatingButton
import com.org.planningapp.ui.graphs.CategoryRoutes
import com.org.planningapp.ui.graphs.TimesheetRoutes
import com.org.planningapp.ui.screens.categories.CategoriesListViewModel
import com.org.planningapp.ui.screens.readableDate
import com.org.planningapp.ui.screens.readableDateAndTime
import com.org.planningapp.ui.screens.readableTime
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


@Composable
fun TimesheetItemWoah(
    timesheet: Timesheet,
    onDelete: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Column {

            // Display Timesheet information
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "Date: ${timesheet.date.readableDateAndTime()}")
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "Start Time: " + timesheet.startTime.readableDateAndTime())
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "End Time: ${timesheet.endTime.readableDateAndTime()}")
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Description: ${timesheet.description}",
                minLines = 2,
            )
            // todo: image url

            // Display Delete button
            Button(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp)
            ) {
                Text(text = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TimesheetItem(
    timesheet: Timesheet,
    onDelete: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        ListItem(
            icon = {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📅",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Left
                    )
                }
            },
            overlineText = {
                Column {
                    Text(
                        text = "Date",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Left
                    )
                }
            },
            text = {
                Column {
                    Row {
                        Text(
                            text = timesheet.date.readableDate(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            },
            singleLineSecondaryText = true,
            trailing = {
                Column {
                    Text(
                        text = timesheet.startTime.readableTime() + " to " +
                                timesheet.endTime.readableTime(),

                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Light
                    )
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = onDelete,
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .padding(start = 16.dp)
                        ) {
                            Text(text = "Delete")
                        }
                    }

                }
            },
            secondaryText = {
                Column {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row {
                        Text(
                            text = timesheet.description,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Light
                        )
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                }
            }
        )
    }
}


@Preview
@Composable
fun TimesheetPreview(){
    val timesheet = Timesheet(
        id = 1,
        categoryId = "1",
        date = Clock.System.now().toLocalDateTimeUTC(),
        startTime = Clock.System.now().toLocalDateTimeUTC(),
        endTime = Clock.System.now().toLocalDateTimeUTC(),
        description = "This is a really long descrition woah woah woah description",
        imageUrl = null,
        createdAt = Clock.System.now().toLocalDateTimeUTC(),
    )
    TimesheetItem(timesheet = timesheet)
}

@Composable
fun TimesheetsList(
    timesheets: List<Timesheet>,
    timesheetsListViewModel: TimeSheetsListByCategoryViewModel = hiltViewModel(),
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp)
    ) {
        timesheets.forEach { timesheet ->
            Spacer(modifier = Modifier.padding(12.dp))
            TimesheetItem(timesheet = timesheet, onDelete = {
                timesheetsListViewModel.removeTimesheet(timesheet)
            })
        }
    }
}


// needs to know which category to show timesheets for
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimeSheetsScreen(
    categoryID: String?,
    navController: NavController,
    viewModel: TimeSheetsListByCategoryViewModel = hiltViewModel(),
    categoriesViewModel: CategoriesListViewModel = hiltViewModel(),
) {
    var categoryName by remember { mutableStateOf("") }

    val timesheets = viewModel.timesheetsList.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val currentRoutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit){
        if (categoryID != null) {
            currentRoutineScope.launch {
                val res = categoriesViewModel.GetCategoryById(categoryID)
                categoryName = res.name
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (categoryName.isEmpty()){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        else {
            Scaffold(
                floatingActionButton = {
                    AddFloatingButton(
                        modifier = Modifier.padding(36.dp, 0.dp, 18.dp, 28.dp),
                        onClick = {
                            navController.navigate(TimesheetRoutes.AddTimesheet.route)
                        }
                    )
                },
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.navigate(CategoryRoutes.Home.route)
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
                                text = "Timesheets for $categoryName!  ⌚⚡",
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        },
                        )
                }
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    TimesheetsList(timesheets = timesheets.value)
                }
            }
        }
    }
}