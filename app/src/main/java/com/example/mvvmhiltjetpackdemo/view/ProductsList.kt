package com.example.mvvmhiltjetpackdemo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmhiltjetpackdemo.model.ProductsItem
import com.example.mvvmhiltjetpackdemo.viewModel.ProductsViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ProductsListView(modifier: Modifier){

    val productsViewModel : ProductsViewModel = viewModel()
    val productsList = productsViewModel.products.collectAsState()


    LazyVerticalGrid(GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(productsList.value){
            ProductListItem(it)
        }
    }
}

@Composable
fun ProductListItem(productsItem: ProductsItem){

    Box (modifier = Modifier.padding(10.dp).height(200.dp)){
        Column {

            GlideImage(
                imageModel = { productsItem.image },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                loading = {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                },
                failure = {
                    Text("Image failed to load", modifier = Modifier.padding(16.dp))
                }
            )

            Text(text = productsItem.title, maxLines = 1)

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = productsItem.rating.rate.toString() +"*****"+productsItem.rating.count, color = Color.LightGray )
                Text(text = "£" + productsItem.price.toString(), color = Color.DarkGray)
            }
        }
    }
}


@Composable
@Preview(showBackground = true , showSystemUi = true)
fun PreviewProductListItem(){

    //ProductListItem()
}
