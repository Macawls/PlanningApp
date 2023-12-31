package com.org.planningapp.ui.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.org.planningapp.ui.screens.HomeScreen

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val HOME = "home_graph"
}

@Composable
fun RootNavigationGraph(navController: NavHostController, startDesitination: String) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDesitination
    ) {
        authNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}



