package com.org.planningapp.ui.screens.goals.add

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.DailyGoal
import com.org.planningapp.ui.components.LoadingButton
import com.org.planningapp.ui.graphs.DailyGoalRoutes
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDailyGoalScreen(
    navController: NavController,
    viewModel: AddDailyGoalViewModel = hiltViewModel(),
) {

    val minHours = viewModel.minHours.collectAsState()
    val maxHours = viewModel.maxHours.collectAsState()

    val minHoursString = viewModel.minHoursString.collectAsState()
    val maxHoursString = viewModel.maxHoursString.collectAsState()

    val routineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }

    fun submitAddGoal(){
        routineScope.launch {
            val goal = DailyGoal(
                minDailyHours = minHours.value,
                maxDailyHours = maxHours.value,
                createdAt = Clock.System.now().toLocalDateTimeUTC()
            )

            if (viewModel.addDailyGoal(goal)) {
                navController.navigate(DailyGoalRoutes.Home.route)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(DailyGoalRoutes.Home.route)
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
                        text = "Add New Goal!  🤯🔥",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        }
    ){
        val loading = viewModel.isLoading.collectAsState()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            OutlinedTextField(
                value = minHoursString.value,
                label = { Text("Min Hours") },
                onValueChange = { viewModel.onMinHoursChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            )
            LaunchedEffect(UInt){
                focusRequester.requestFocus()
            }
            OutlinedTextField(
                value = maxHoursString.value,
                label = { Text("Max Hours") },
                onValueChange = { viewModel.onMaxHoursChange(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.padding(20.dp))
            LoadingButton(
                isLoading = loading.value,
                buttonText = "Save Goal" ,
                onClick = { submitAddGoal() },
                enabled = minHours.value > 0 && maxHours.value > 0
            )
        }
    }
}