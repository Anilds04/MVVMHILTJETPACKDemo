package com.example.mvvmhiltjetpackdemo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmhiltjetpackdemo.viewModel.ProductDetailsViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ProductDetailsScreen(modifier: Modifier) {

    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    val product = productDetailsViewModel.productdetails.collectAsState()

    Box(modifier = modifier
        .fillMaxSize()) {

        GlideImage(
            imageModel = { product.value?.image },
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp).padding(20.dp),
            loading = {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            },
            failure = {
                Text("Image failed to load", modifier = Modifier.padding(16.dp))
            }
        )
    }
}