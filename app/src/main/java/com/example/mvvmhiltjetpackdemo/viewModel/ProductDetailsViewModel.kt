package com.example.mvvmhiltjetpackdemo.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmhiltjetpackdemo.api.ProductsApi
import com.example.mvvmhiltjetpackdemo.model.ProductsItem
import com.example.mvvmhiltjetpackdemo.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    val productdetails: StateFlow<ProductsItem?>
        get() = productsRepository.productDetails

    init {
        val id = savedStateHandle.get<String>("id")?:"1"
        Log.d("TAG id", "$id: ")
        viewModelScope.launch {
            productsRepository.getProductsById(id)
        }

    }
}