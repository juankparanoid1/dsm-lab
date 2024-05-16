package com.example.resourcesApp.Screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resourcesApp.R
import com.example.resourcesApp.adapter.ResourceListAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class ResourceListScreen : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var resourceRecycleView: RecyclerView;
    private lateinit var resourceListAdapter: ResourceListAdapter
    private lateinit var categoryId: String
    private lateinit var categoryName: String
    private lateinit var screenNameLabel: TextView
    private lateinit var searchInputText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_list_screen)

        categoryId = intent.getStringExtra("categoryId") ?: ""
        categoryName = intent.getStringExtra("name") ?: ""

        screenNameLabel = findViewById<TextView>(R.id.appNameLabel)

        firestore = Firebase.firestore
        val resources = firestore
            .collection("resources")
            .whereIn("category", listOf(categoryId))
            .orderBy("title", Query.Direction.ASCENDING)
            .limit(50)

        val currentScreenName = screenNameLabel.text
        Log.d("ResourceListScreen", currentScreenName.toString())
        screenNameLabel.text = "${categoryName} ${currentScreenName}"
        Log.d("ResourceListScreen", screenNameLabel.text.toString())

        displayResourceList(resources)

        searchInputText = findViewById<TextInputEditText>(R.id.searchInputText)
        searchInputText.addTextChangedListener(object : TextWatcher {
            private val handler = Handler(Looper.getMainLooper())
            private var searchRunnable: Runnable = Runnable {}
            private val debounceDuration = 500L // Adjust the debounce duration as needed

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                handler.removeCallbacks(searchRunnable)
                searchRunnable = Runnable {
                    val searchText = s.toString().lowercase().trim()
                    Log.d("ResourceListScreen", searchText.toString())
                    val searchContact = firestore
                        .collection("resources")
                        .whereEqualTo("titleLoweCase", searchText)
                    val query: Query = if (searchText.isNullOrBlank()) resources else searchContact
                    displayResourceList(query)
                }
                handler.postDelayed(searchRunnable, debounceDuration)
            }
        })
    }

    private fun displayResourceList(query: Query) {
        resourceRecycleView = findViewById(R.id.resourcesList)
        resourceRecycleView.layoutManager = LinearLayoutManager(this)
        resourceListAdapter = ResourceListAdapter(query)
        resourceRecycleView.adapter = resourceListAdapter
        resourceListAdapter.startListening()
    }
}