package com.org.planningapp.ui.graphs

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.org.planningapp.ui.screens.auth.login.LoginForm

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "login")
    object SignUp : AuthScreen(route = "sign_up")
    object Forgot : AuthScreen(route = "forgot")
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginForm(navController = navController)
        }
        composable(route = AuthScreen.SignUp.route) {
            Text(text = AuthScreen.SignUp.route)
        }

        composable(route = AuthScreen.Forgot.route){
            Text(text = AuthScreen.Forgot.route)
        }
    }
}