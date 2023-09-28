package com.org.planningapp.ui.graphs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.org.planningapp.ui.BottomBarScreen
import com.org.planningapp.ui.screens.categories.CategoriesScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route)
    {
        composable(route = BottomBarScreen.Home.route) {
            Text(text = "Home")
        }

        composable(route = BottomBarScreen.Timesheets.route) {
            Text(text = "Timesheets")
        }

        composable(route = BottomBarScreen.Categories.route) {
            //Text(text = "Categories")
            CategoriesScreen()
        }
    }
}

