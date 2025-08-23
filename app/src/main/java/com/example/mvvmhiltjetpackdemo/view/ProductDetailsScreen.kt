package com.example.mvvmhiltjetpackdemo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mvvmhiltjetpackdemo.viewModel.ProductDetailsViewModel
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun ProductDetailsScreen(modifier: Modifier) {

    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    val product = productDetailsViewModel.productdetails.collectAsState()
    var productCount by remember { mutableStateOf<Int>(0) }
    var isFavorite by remember { mutableStateOf<Boolean>(false) }


    Column(modifier = modifier.padding(20.dp)) {
        Surface(
            color = Color.White,
            shadowElevation = 8.dp, // 👈 elevation
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()

            ) {

                Icon(
                    imageVector = if (isFavorite)
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder,
                    "", tint = if (isFavorite) Color.Red else Color.Black,
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(16.dp)
                        .clickable {
                            isFavorite = !isFavorite
                        }
                )


                GlideImage(
                    imageModel = { product.value?.image },
                    modifier = Modifier
                        .padding(50.dp),
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    },
                    failure = {
                        Text("Image failed to load", modifier = Modifier.padding(16.dp))
                    }
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = product.value?.title.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            Text(text = "£${product.value?.price.toString()}")
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIconButtonTonal(
                    icon = Icons.Default.Remove,
                    contentDescription = "",
                    onClick = {

                        if (productCount > 0) productCount--
                    })

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = productCount.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(12.dp))

                CustomIconButtonTonal(
                    icon = Icons.Default.Add,
                    contentDescription = "",
                    onClick = {
                        productCount++
                    })

                Spacer(modifier = Modifier.weight(1f))
                FilledTonalButton(onClick = {}) {
                    Text("ADD")
                }

            }


        }

        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 25.dp, bottom = 12.dp)
        )

        Text(
            text = product.value?.description.toString()
        )
    }
}


@Composable
fun CustomIconButtonTonal(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: DpSize = DpSize(50.dp, 30.dp),
    cornerRadius: Dp = 16.dp,
    padding: Dp = 6.dp
) {
    FilledTonalButton(
        onClick = onClick,
        shape = RoundedCornerShape(cornerRadius),
        contentPadding = PaddingValues(padding),
        modifier = modifier
            .width(buttonSize.width)
            .height(buttonSize.height)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewProductDetailsScreen() {
    ProductDetailsScreen(Modifier)
}