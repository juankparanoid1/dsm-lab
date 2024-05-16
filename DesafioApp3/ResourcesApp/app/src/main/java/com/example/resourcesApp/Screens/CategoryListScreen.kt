package com.example.resourcesApp.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resourcesApp.R
import com.example.resourcesApp.adapter.CategoryListAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class CategoryListScreen : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var categoriesRecycleView: RecyclerView;
    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list_screen)

        Toast.makeText(this, R.string.welcome_app, Toast.LENGTH_SHORT).show()

        firestore = Firebase.firestore
        val categories = firestore
            .collection("categories")
            .orderBy("name", Query.Direction.ASCENDING)
            .limit(50)

        categoriesRecycleView = findViewById(R.id.categoryListRV)
        categoriesRecycleView.layoutManager = LinearLayoutManager(this)
        categoryListAdapter = CategoryListAdapter(categories)
        categoriesRecycleView.adapter = categoryListAdapter
        categoryListAdapter.startListening()
    }
}