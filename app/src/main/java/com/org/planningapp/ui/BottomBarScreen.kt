package com.org.planningapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Categories : BottomBarScreen(
        route = "categories",
        title = "Categories",
        icon = Icons.Default.List
    )

    object Goals : BottomBarScreen(
        route = "goals",
        title = "Goals",
        icon = Icons.Default.Person
    )
}