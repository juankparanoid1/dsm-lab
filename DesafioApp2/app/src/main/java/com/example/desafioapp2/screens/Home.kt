package com.example.desafioapp2.screens

import com.example.desafioapp2.R
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.desafioapp2.models.Product
import com.example.desafioapp2.navigation.Destinations
import com.example.desafioapp2.service.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun Carousel(productos: List<Product>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(productos) { producto ->
                ProductItem(producto = producto)
            }
        }
    }

}

@Composable
fun ProductItem(producto: Product) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        if (!producto.imageUrl.isNullOrBlank()) {
            val painter = rememberImagePainter(producto.imageUrl)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )
        } else {
            Spacer(modifier = Modifier.size(100.dp))
        }
        Text(text = producto.name, color = Color.Black)
    }
}

@Composable
fun ProductCard(product: Product, navController: NavController) {
    Card(
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clickable {
                val route =
                    Destinations.PRODUCT_DETAIL_ROUTE.replace("{userId}", product.id.toString());
                navController.navigate(route);
            }
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            // Imagen del producto
            Image(
                painter = rememberImagePainter(product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                // Nombre del producto
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Precio del producto
                Text(
                    text = "$ ${product.price.toString()}" ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Descripción del producto
                Text(
                    text = product.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(products) { product ->
            Log.i("image",product.imageUrl.toString());
            ProductCard(product = product, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope();

    val productViewModel: ProductViewModel = viewModel()
    var productList by remember { mutableStateOf(emptyList<Product>()) }
    LaunchedEffect(true) {
        val list = productViewModel.getProductList("products")
        productList = list
        Log.i("lista", productList.toString())
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.clave_a),
                        fontWeight = FontWeight.W500,
                        fontSize = 25.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Destinations.HISTORY_ROUTE)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.History,
                            contentDescription = "Shopping history"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Text(
                text = stringResource(id = R.string.feature_products),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp, top = 15.dp, start = 5.dp)
            )
            Carousel(productos = productList)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.products),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp, start = 5.dp)
            )
            ProductList(products = productList, navController = navController);
        }
    }
}

