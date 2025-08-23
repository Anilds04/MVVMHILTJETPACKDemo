package com.example.mvvmhiltjetpackdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.example.mvvmhiltjetpackdemo.ui.theme.MVVMHILTJETPACKDemoTheme
import com.example.mvvmhiltjetpackdemo.view.MyApp

import dagger.hilt.android.AndroidEntryPoint

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
                MyApp()
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
