package com.example.mvvmhiltjetpackdemo

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mvvmhiltjetpackdemo.api.ProductsApi
import com.example.mvvmhiltjetpackdemo.model.Apps
import com.example.mvvmhiltjetpackdemo.model.NavItem
import com.example.mvvmhiltjetpackdemo.ui.theme.MVVMHILTJETPACKDemoTheme
import com.example.mvvmhiltjetpackdemo.view.LoginScreen
import com.example.mvvmhiltjetpackdemo.view.ProductDetailsScreen
import com.example.mvvmhiltjetpackdemo.view.ProductListItem
import com.example.mvvmhiltjetpackdemo.view.ProductsListView
import com.example.mvvmhiltjetpackdemo.view.SplashScreen
import com.example.mvvmhiltjetpackdemo.viewModel.ProductDetailsViewModel
import com.example.mvvmhiltjetpackdemo.viewModel.ProductsViewModel

import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // val productsViewModel : ProductsViewModel by viewModels()

    //In jetpack compose
    //val productsViewModel1 : ProductsViewModel = viewModel()

    // lateinit var productsViewModel : ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
       installSplashScreen()
        super.onCreate(savedInstanceState)
          enableEdgeToEdge()


        setContent {
            MVVMHILTJETPACKDemoTheme {
                Apps()
            }
        }
    }




/*    @Composable
    fun loadWebView(modifier: Modifier){
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient() // handle navigation
                    settings.javaScriptEnabled = true // enable JS if needed
                    loadUrl("https://www.comparethemarket.com/")
                }
            }
        )
    }*/

}
