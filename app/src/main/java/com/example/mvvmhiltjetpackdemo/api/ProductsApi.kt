package com.example.mvvmhiltjetpackdemo.api

import com.example.mvvmhiltjetpackdemo.model.ProductsItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductsItem>>

}