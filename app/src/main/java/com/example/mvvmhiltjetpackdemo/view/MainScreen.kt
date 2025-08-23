package com.example.mvvmhiltjetpackdemo.view

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mvvmhiltjetpackdemo.R
import com.example.mvvmhiltjetpackdemo.model.NavItem


@Composable
fun MyApp() {

    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry.value?.destination?.route

    val homeRoute = currentRoute in listOf("home")
    val detailsRoute = currentRoute in listOf("detail/{id}")

    val context = LocalContext.current

    Scaffold(
        topBar = {

            if (currentRoute == "home") {
                TopBar(title = "Home", true, onBackClick = {})
            } else if (currentRoute?.startsWith("detail") == true) {
                TopBar(title = "Details", false, onBackClick = {
                    navController.popBackStack()
                })
            }

        },

        bottomBar = {
            if (homeRoute) {
                BottomBar()
            }
        }, modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavigateApp(Modifier.padding(innerPadding), navController)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, canExit: Boolean, onBackClick: () -> Unit) {
    val context = LocalContext.current
    var lastBackPressedTime by remember { mutableStateOf(0L) }
    val toast = remember { Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT) }
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = Color.White,
        ),
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = {
                if (canExit) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastBackPressedTime < 2000) {
                        toast.cancel()
                        (context as? Activity)?.finish()
                    } else {
                        lastBackPressedTime = currentTime
                        toast.show()
                    }
                } else {
                    onBackClick()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    " ",
                    tint = Color.White
                )
            }
        },

        actions = {

            BadgedBox(
                modifier = Modifier.padding(end = 20.dp),
                badge = {
                    Badge(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ){
                        Text(text = "2")
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Shopping cart",
                    modifier = Modifier.size(35.dp)
                )
            }

        }
    )
}

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
                label = { Text(text = navItem.label) })
        }
    }
}


@Composable
fun DoubleBackToExit(navController: NavController) {
    val context = LocalContext.current
    var lastBackPressedTime by remember { mutableStateOf(0L) }
    val toast = remember { Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressedTime < 2000) {
            toast.cancel() // dismiss the toast if still visible
            (context as? Activity)?.finish()
        } else {
            lastBackPressedTime = currentTime
            toast.show()
        }
    }
}

