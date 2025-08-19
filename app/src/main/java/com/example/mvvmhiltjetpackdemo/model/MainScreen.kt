package com.example.mvvmhiltjetpackdemo.model

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmhiltjetpackdemo.App
import com.example.mvvmhiltjetpackdemo.view.LoginScreen
import com.example.mvvmhiltjetpackdemo.view.ProductDetailsScreen
import com.example.mvvmhiltjetpackdemo.view.ProductsListView



@Composable
fun BottomBar() {
    val navList = mutableListOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Notification", Icons.Default.Notifications),
        NavItem("Settings", Icons.Default.Settings)
    )


    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar {
        navList.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                },
                icon = { Icon(imageVector = navItem.icon, "") },
                label = { Text(text = navItem.label) }
            )
        }
    }
}

@Composable
fun Apps() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("home")) {
                BottomBar()
            }

        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        //loadWebView(Modifier.padding(innerPadding)

        NavHost(navController = navController, startDestination = "loginScreen") {

            composable(route = "loginScreen") {
                LoginScreen(Modifier.padding(innerPadding)){

                    navController.navigate("home")
                }


            }

            composable(route = "home") {
                ProductsListView(Modifier.padding(innerPadding)) {
                    navController.navigate("detail/$it")
                }
            }

            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                })
            ) {
                ProductDetailsScreen(Modifier.padding(innerPadding))
            }
        }

    }
}
