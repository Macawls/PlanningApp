package com.org.planningapp.ui.screens.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.Category
import com.org.planningapp.ui.graphs.CategoryRoutes
import kotlinx.datetime.Clock

@Composable
fun CategoryItem(
    category: Category,
    onDelete: () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(16.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.padding(14.dp))
            Row {
                Text(text = "Added: " + category.createdAt.readableDate(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
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
        createdAt = Clock.System.now().toLocalDateTime()
    )
) {
    CategoryItem(category = category)
}

@Composable
fun CategoryList(
    categories: List<Category>,
    categoriesListViewModel: CategoriesListViewModel = hiltViewModel()
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
            createdAt = Clock.System.now().toLocalDateTime()
        ),
        Category(
            id = "2",
            name = "Test 2",
            createdAt = Clock.System.now().toLocalDateTime()
        ),
        Category(
            id = "3",
            name = "Test 3",
            createdAt = Clock.System.now().toLocalDateTime()
        )
    )

) {
    CategoryList(categories = categories)
}

@Composable
fun AddCategoryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
    }
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
            AddCategoryButton(
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
                CategoryList(categories = viewModel.categoriesList.collectAsState().value)
                PullRefreshIndicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    refreshing = isLoading,
                    state = pullRefreshState
                )
            }
        }
    )
}

