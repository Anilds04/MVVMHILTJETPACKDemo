package com.example.mvvmhiltjetpackdemo.api

import com.example.mvvmhiltjetpackdemo.model.ProductsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductsItem>>

    @GET("/products/{id}")
    suspend fun getProductsById(@Path("id") userId : String) : Response<ProductsItem>
}