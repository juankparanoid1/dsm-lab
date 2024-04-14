package com.example.desafioapp2.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.desafioapp2.models.History
import com.example.desafioapp2.service.ProductViewModel
import com.example.desafioapp2.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ShoppingHistoryCard(history: History) {

    val formattedDate = remember(history.date) {
        val dateFormatter = SimpleDateFormat("EEEE, dd 'de' MMMM 'del' yyyy", Locale("es", "ES"))
        dateFormatter.format(history.date)
    }

    val formattedPrice = remember(history.price) {
        String.format("%.2f", history.price)
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "${stringResource(id = R.string.history_date)} $formattedDate", style = MaterialTheme.typography.bodyLarge)
            Text(text = "${stringResource(id = R.string.history_product)} ${history.productName}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "${stringResource(id = R.string.history_price)} $formattedPrice", style = MaterialTheme.typography.bodyLarge)
            Text(text = "${stringResource(id = R.string.history_quantity)} ${history.quantity}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun History(navController: NavController) {
    val productViewModel: ProductViewModel = viewModel()
    var historyList by remember { mutableStateOf(emptyList<History>()) }
    LaunchedEffect(true) {
        val list = productViewModel.getShoppingHistory("shopping-history")
        historyList = list
        Log.i("shoppingHistory", historyList.toString())
    }
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(historyList) { history ->
            ShoppingHistoryCard(history = history)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
