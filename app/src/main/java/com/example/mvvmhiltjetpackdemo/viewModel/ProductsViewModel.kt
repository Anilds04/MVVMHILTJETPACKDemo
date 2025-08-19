package com.example.mvvmhiltjetpackdemo.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmhiltjetpackdemo.model.ProductsItem
import com.example.mvvmhiltjetpackdemo.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel(){

    val products : StateFlow<List<ProductsItem>>
        get() = productsRepository.products

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG----", ":${Thread.currentThread()} ")
            productsRepository.getProducts()
        }

    }
}
