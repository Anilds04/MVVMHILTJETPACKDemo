package com.example.mvvmhiltjetpackdemo.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigateApp(
    modifier: Modifier,
    navController: NavHostController
) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val startDestination = if (currentUser != null) {
        Screen.Home.route   // ✅ already logged in
    } else {
        Screen.Login.route  // 🔑 not logged in
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    )
    {

        composable(route = Screen.Login.route) {
            LoginScreen(modifier) {
                navController.navigate(Screen.Home.route)
                {
                    popUpTo(Screen.Login.route) {
                        inclusive = true
                    }   //Remove login from the backstack
                    launchSingleTop = true
                }
            }
        }

        composable(route = Screen.Home.route) {
            ProductsListView(modifier) {
                safeClick { // make sure item not clicked twice
                    navController.navigate(Screen.Details.createRoute(it))
                }
            }
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            ProductDetailsScreen(modifier)
        }


        composable(route = Screen.Settings.route) {
            SettingScreen(modifier){
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true } // clears all previous routes
                    launchSingleTop = true
                }
            }
        }

    }
}


var lastClickTime = 0L
val debounceTime = 500L // 0.5 second

fun safeClick(onSafeClick: () -> Unit) {
    val now = System.currentTimeMillis()
    if (now - lastClickTime > debounceTime) {
        lastClickTime = now
        onSafeClick()
    }
}


sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Details : Screen("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
    data object Settings : Screen("settings")
}