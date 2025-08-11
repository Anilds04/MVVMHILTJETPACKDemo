package com.example.mvvmhiltjetpackdemo.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mvvmhiltjetpackdemo.api.ProductsApi
import com.example.mvvmhiltjetpackdemo.model.Products
import com.example.mvvmhiltjetpackdemo.model.ProductsItem
import dagger.Module
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class ProductsRepository @Inject constructor(private val productsApi: ProductsApi) {

    private val _products = MutableStateFlow<List<ProductsItem>>(emptyList())
    val products: StateFlow<List<ProductsItem>>
        get() = _products

    private val _productDetails = MutableStateFlow<ProductsItem?>(null)
    val productDetails: StateFlow<ProductsItem?>
        get() = _productDetails


    suspend fun getProducts() {
        val response = productsApi.getProducts()

        if (response.isSuccessful && response.body() != null) {
            _products.emit(response.body()!!)
        }
    }

    suspend fun getProductsById(id: String) {

        val response = productsApi.getProductsById(id)

        if (response.isSuccessful) {
            _productDetails.emit(response.body())
        }
    }
}