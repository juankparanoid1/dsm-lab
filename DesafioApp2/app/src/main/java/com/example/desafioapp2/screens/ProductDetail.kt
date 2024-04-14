package com.example.desafioapp2.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.desafioapp2.R
import com.example.desafioapp2.models.History
import com.example.desafioapp2.models.Product
import com.example.desafioapp2.navigation.Destinations
import com.example.desafioapp2.service.ProductViewModel
import kotlinx.coroutines.launch
import java.util.Date


@Composable
fun QuantitySelector(onCantidadChange: (Int) -> Unit) {
    var cantidad by remember { mutableStateOf(1) }
    val removeIcon: Painter = painterResource(id = R.drawable.baseline_remove_24);

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { cantidad = maxOf(1, cantidad - 1) }) {
            Icon(removeIcon, contentDescription = "Quitar")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = cantidad.toString(),
            modifier = Modifier.width(10.dp),
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = { cantidad++ }) {
            Icon(Icons.Default.Add, contentDescription = "Agregar")
        }
    }
    onCantidadChange(cantidad)
}

@Composable
fun ProductDetailScreen(producto: Product, navController: NavController) {

    var cantidad by remember { mutableStateOf(1) }
    val productViewModel: ProductViewModel = viewModel();
    val coroutineScope = rememberCoroutineScope();
    var showToast by remember { mutableStateOf(false) }

    if (showToast) {
        showToast(message = "Producto agregado al historial")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Foto del producto
        Image(
            painter = rememberImagePainter(producto.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Nombre del producto
        Text(
            text = producto.name,
            style = MaterialTheme.typography.headlineLarge
        )

        // Precio del producto
        Text(
            text = "$ ${producto.price.toString()}" ?: "",
            style = MaterialTheme.typography.headlineSmall
        )

        // Descripción del producto
        Text(
            text = producto.description ?: "",
            style = MaterialTheme.typography.bodyLarge
        )

        // Slider para seleccionar la cantidad
        Spacer(modifier = Modifier.height(16.dp))

        QuantitySelector { nuevaCantidad ->
            cantidad = nuevaCantidad
        }

        // Botón para agregar al carrito
        Spacer(modifier = Modifier.height(16.dp))

        // Botón de agregar
        Button(
            onClick = {
                coroutineScope.launch {
                    // Save history before navigating
                    producto?.let { product ->
                        val history = product.price?.let {
                            History(
                                id = "0",
                                date = Date(), // Assuming you want to save the current date
                                productName = product.name,
                                price = it,
                                quantity = cantidad
                            )
                        }
                        if (history != null) {
                            productViewModel.saveHistory(history);
                            navController.navigate(Destinations.HOME_ROUTE);
                            showToast = true
                        };
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Pedir", style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun ProductDetail(userId: String, navController: NavController) {
    Log.i("userid", userId);

    val productViewModel: ProductViewModel = viewModel()
    var product by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(true) {
        val fetchedProduct: Product? = productViewModel.getProductById("products", userId)
        product = fetchedProduct
        Log.i("Single product", product.toString())
    }
    product?.let { ProductDetailScreen(it, navController = navController) }
}

@Composable
fun showToast(message: String) {
    Toast.makeText(
        LocalContext.current,
        message,
        Toast.LENGTH_SHORT
    ).show()
}