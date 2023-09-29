package com.org.planningapp.ui.graphs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.org.planningapp.ui.BottomBarScreen
import com.org.planningapp.ui.screens.categories.CategoriesScreen
import com.org.planningapp.ui.screens.categories.add.AddCategoryScreen
import com.org.planningapp.ui.screens.timesheets.TimeSheetsScreen
import com.org.planningapp.ui.screens.timesheets.add.AddTimesheetScreen

sealed class CategoryRoutes(val route: String) {
    object Home: CategoryRoutes(BottomBarScreen.Categories.route)
    object AddCategory: CategoryRoutes("add_category")
}

sealed class TimesheetRoutes(val route: String) {
    object Home: TimesheetRoutes("timesheets")
    object AddTimesheet: TimesheetRoutes("add_timesheet")
}

object TimesheetByCategoryDestination {
    const val timesheetId = "id"
    val arguments = listOf(navArgument(name = timesheetId) {
        type = NavType.StringType
    })
    fun createRouteWithParam(timesheetId: String) =
        "${TimesheetRoutes.Home.route}/${timesheetId}"
}

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route)
    {
        composable(route = BottomBarScreen.Home.route) {
            Text(text = "Home")
        }

        composable(route = BottomBarScreen.Categories.route) {
            CategoriesScreen(navController = navController)
        }

        composable(route = BottomBarScreen.Goals.route) {
            Text(text = "Goals")
        }

        composable(route = CategoryRoutes.AddCategory.route) {
            AddCategoryScreen(navController = navController)
        }

        composable(
            route = "${"timesheets"}/{${TimesheetByCategoryDestination.timesheetId}}",
            arguments = TimesheetByCategoryDestination.arguments
        ) { navBackStackEntry ->
            val categoryId = navBackStackEntry.arguments?.getString(TimesheetByCategoryDestination.timesheetId)
            TimeSheetsScreen(categoryID = categoryId, navController = navController)
        }

        composable(route = TimesheetRoutes.AddTimesheet.route) {
            AddTimesheetScreen(navController = navController)
        }
    }
}

