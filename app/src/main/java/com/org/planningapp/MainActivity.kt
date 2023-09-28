package com.org.planningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.org.planningapp.ui.graphs.Graph
import com.org.planningapp.ui.graphs.RootNavigationGraph
import com.org.planningapp.ui.theme.PlanningAppTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var supabaseClient: SupabaseClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val hasExistingSession = supabaseClient.gotrue.currentSessionOrNull() != null

            PlanningAppTheme {
                RootNavigationGraph(
                    startDesitination = if (hasExistingSession) Graph.HOME else Graph.AUTH,
                    navController = rememberNavController()
                )
            }
        }
    }
}