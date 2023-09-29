package com.org.planningapp.ui.screens.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.Category
import com.org.planningapp.ui.components.AddFloatingButton
import com.org.planningapp.ui.graphs.CategoryRoutes
import com.org.planningapp.ui.graphs.TimesheetByCategoryDestination
import com.org.planningapp.ui.screens.readableDateAndTime
import com.org.planningapp.ui.screens.toLocalDateTimeUTC
import kotlinx.datetime.Clock

@Composable
fun CategoryItem(
    category: Category,
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.padding(18.dp))
            Row {
                Text(text = "Added: " + category.createdAt.readableDateAndTime(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = { onDelete() }
        ) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview
@Composable
fun CategoryItemPreview(
    category: Category = Category(
        id = "1",
        name = "Category 1",
        createdAt = Clock.System.now().toLocalDateTimeUTC()
    )
) {
    CategoryItem(category = category)
}

@Composable
fun CategoryList(
    categories: List<Category>,
    categoriesListViewModel: CategoriesListViewModel = hiltViewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        categories.forEach { category ->
            CategoryItem(
                category = category,
                onDelete = {
                    categoriesListViewModel.removeCategory(category)
                },
                onClick = {
                    val route = TimesheetByCategoryDestination
                        .createRouteWithParam(category.id)

                    navController.navigate(route)
                }
            )
        }
    }
}

@Preview
@Composable
fun CategoryListPreview(
    categories: List<Category> = listOf(
        Category(
            id = "1",
            name = "Test 1",
            createdAt = Clock.System.now().toLocalDateTimeUTC()
        ),
        Category(
            id = "2",
            name = "Test 2",
            createdAt = Clock.System.now().toLocalDateTimeUTC()
        ),
        Category(
            id = "3",
            name = "Test 3",
            createdAt = Clock.System.now().toLocalDateTimeUTC()
        )
    )

) {
    //CategoryList(categories = categories)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CategoriesListViewModel = hiltViewModel()
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
                    navController.navigate(CategoryRoutes.AddCategory.route)
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
                CategoryList(
                    categories = viewModel.categoriesList.collectAsState().value,
                    navController = navController
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

