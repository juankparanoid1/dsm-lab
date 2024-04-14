package com.example.desafioapp2.service

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.desafioapp2.models.History
import com.example.desafioapp2.models.Product
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance();

    suspend fun getProductList(collectionName: String): List<Product> {
        return withContext(Dispatchers.IO) {
            val querySnapshot = Tasks.await(db.collection(collectionName).get())
            val productList = mutableListOf<Product>()
            for (document in querySnapshot) {
                val productId = document.id;
                val product = document.toObject(Product::class.java)
                val productWithId = product.copy(id = productId)
                productList.add(productWithId)
            }
            productList
        }
    }

    suspend fun getProductById(collectionName: String, productId: String): Product? {
        return withContext(Dispatchers.IO) {
            val documentRef = db.collection(collectionName).document(productId)
            val documentSnapshot = Tasks.await(documentRef.get())
            if (documentSnapshot.exists()) {
                val product = documentSnapshot.toObject(Product::class.java)
                product?.copy(id = productId)
            } else {
                null
            }
        }
    }

    suspend fun saveHistory(history: History) {
        withContext(Dispatchers.IO) {
            val historyData = hashMapOf(
                "date" to history.date,
                "productName" to history.productName,
                "price" to history.price,
                "quantity" to history.quantity
            )

            db.collection("shopping-history")
                .add(historyData)
                .addOnSuccessListener { documentReference ->
                    Log.d("ADDED", "Se agrego el documento con el ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("ERROR", "Error al guardar el historial", e)
                }
        }
    }

    suspend fun getShoppingHistory(collectionName: String): List<History> {
        return withContext(Dispatchers.IO) {
            val querySnapshot = Tasks.await(db.collection(collectionName).get())
            val historyList = mutableListOf<History>()
            for (document in querySnapshot) {
                val historyId = document.id // Document ID as String
                val history = document.toObject(History::class.java)
                val historyWithId = history.copy(id = historyId)
                historyList.add(historyWithId)
            }
            historyList
        }
    }
}