package com.example.mvvmhiltjetpackdemo.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onSplashFinished : () -> Unit){
    LaunchedEffect(key1 = Unit) {

        delay(3000)
        onSplashFinished()

    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "SplashScreen", fontSize = 50.sp)
    }

}