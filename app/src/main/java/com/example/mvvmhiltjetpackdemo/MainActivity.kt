package com.example.mvvmhiltjetpackdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmhiltjetpackdemo.api.ProductsApi
import com.example.mvvmhiltjetpackdemo.ui.theme.MVVMHILTJETPACKDemoTheme
import com.example.mvvmhiltjetpackdemo.viewModel.ProductsViewModel
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   /// val productsViewModel : ProductsViewModel by viewModels()

    //In jetpack compose
    //val productsViewModel1 : ProductsViewModel = viewModel()

   // lateinit var productsViewModel : ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

       // productsViewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        //Used inside Jetpack compose
       // productsViewModel.products.collectAsState()
      /*  CoroutineScope(Dispatchers.IO).launch {
            productsViewModel.products.collect{
                Log.d("TAG", "onCreate: $it")
            }

        }*/

        setContent {
            MVVMHILTJETPACKDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MVVMHILTJETPACKDemoTheme {
        Greeting("Android")
    }
}