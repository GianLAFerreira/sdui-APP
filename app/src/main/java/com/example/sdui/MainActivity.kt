package com.example.sdui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sdui.ui.DetailScreen
import com.example.sdui.ui.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val nav = rememberNavController()
                    NavHost(navController = nav, startDestination = "home") {
                        composable("home") {
                            HomeScreen(onNavigate = { route, params ->
                                if (route == "/detail") {
                                    val id = (params?.get("id") as? String) ?: "UNKNOWN"
                                    nav.navigate("detail/$id")
                                } else if (route == "/home") {
                                    nav.navigate("home")
                                }
                            })
                        }
                        composable(
                            route = "detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStack ->
                            val id = backStack.arguments?.getString("id") ?: "UNKNOWN"
                            DetailScreen(id = id, onNavigate = { route, _ ->
                                if (route == "/home") nav.navigate("home")
                            })
                        }
                    }
                }
            }
        }
    }
}
