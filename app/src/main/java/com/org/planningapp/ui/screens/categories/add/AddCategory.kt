package com.org.planningapp.ui.screens.categories.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.org.planningapp.domain.model.Category
import com.org.planningapp.ui.components.LoadingButton
import com.org.planningapp.ui.graphs.CategoryRoutes
import com.org.planningapp.ui.screens.categories.toLocalDateTime
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    navController: NavController,
    addCategoryViewModel: AddCategoryViewModel = hiltViewModel(),
) {
    val title = addCategoryViewModel.title.collectAsState()
    val loading = addCategoryViewModel.isLoading.collectAsState()
    val routineScope = rememberCoroutineScope()

    fun submitAddCategory(){
        var res: Boolean = false
        routineScope.launch {
            val category = Category(
                name = title.value,
                id = "",
                createdAt = Clock.System.now().toLocalDateTime()
            )

            res = addCategoryViewModel.addCategory(category)
            if (res) {
                navController.navigate(CategoryRoutes.Home.route)
            }
        }
    }

    Scaffold(
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
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = "Add Category",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
            )
        }
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            OutlinedTextField(
                value = title.value,
                label = { Text("Category Name") },
                onValueChange = { addCategoryViewModel.onTitleChange(it) }
            )
            Spacer(modifier = Modifier.padding(20.dp))
            LoadingButton(
                isLoading = loading.value,
                buttonText = "Save Category" ,
                onClick = { submitAddCategory() },
                enabled = title.value.isNotEmpty()
            )
        }
    }
}
