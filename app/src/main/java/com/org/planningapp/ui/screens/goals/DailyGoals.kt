package com.org.planningapp.ui.screens.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.DailyGoal
import com.org.planningapp.ui.components.AddFloatingButton
import com.org.planningapp.ui.graphs.DailyGoalRoutes
import com.org.planningapp.ui.screens.readableDateAndTime
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import kotlinx.datetime.Clock

@Composable
fun DailyGoalItem(
    goal: DailyGoal,
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
        .clip(MaterialTheme.shapes.large)
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(24.dp)
        .clickable {
            onClick()
        }
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Min ⏳",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${goal.minDailyHours}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Max ⌛",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = "${goal.maxDailyHours}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Created At: ${goal.createdAt.readableDateAndTime()}",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light
            )
        }
        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = MaterialTheme.colorScheme.error,
                contentDescription = "Delete Category"
            )
        }
    }
}

@Composable
@Preview
fun DailyGoalItemPreview() {
    DailyGoalItem(
        goal = DailyGoal(
            id = 1,
            createdAt = Clock.System.now().toLocalDateTimeUTC(),
            minDailyHours = 1,
            maxDailyHours = 2
        )
    )
}

@Composable
fun DailyGoalList(
    goals: List<DailyGoal>,
    onDelete: (DailyGoal) -> Unit = {},
    onClick: (DailyGoal) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        goals.forEach { goal ->
            DailyGoalItem(
                goal = goal,
                onDelete = { onDelete(goal) },
                onClick = { onClick(goal) }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DailyGoalsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DailyGoalsViewModel = hiltViewModel(),
) {
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refresh() }
    )
    Scaffold(
        floatingActionButton = {
            AddFloatingButton(
                modifier = Modifier.padding(36.dp, 0.dp, 18.dp, 84.dp),
                onClick = {
                    navController.navigate(DailyGoalRoutes.AddGoal.route)
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
            ) {
                DailyGoalList(
                    goals = viewModel.dailyGoals.collectAsState().value,
                    onDelete = { goal ->
                        viewModel.DeleteGoal(goal)
                    },
                    onClick = { goal ->

                    },
                )
                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = isLoading,
                    state = pullRefreshState
                )
            }
        }
    )
}