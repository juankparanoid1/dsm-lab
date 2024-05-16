package com.example.resourcesApp.Screens

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.resourcesApp.R
import com.example.resourcesApp.dto.Resource
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResourceDetailScreen : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var resourceId: String
    private lateinit var resourceImageDetail: ImageView
    private lateinit var resourceTitleDetail: TextView
    private lateinit var resourceDateDetail: TextView
    private lateinit var resourceDescriptionDetail: TextView
    private lateinit var resourceURLDetail: TextView
    private val TAG = "ResourceDetailScreen"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_detail_screen)

        resourceId = intent.getStringExtra("resourceId") ?: ""

        resourceImageDetail = findViewById(R.id.resourceImageDetail)
        resourceTitleDetail = findViewById(R.id.resourceTitleDetail)
        resourceDateDetail = findViewById(R.id.resourceDateDetail)
        resourceDescriptionDetail = findViewById(R.id.resourceDescriptionDetail)
        resourceURLDetail = findViewById(R.id.resourceURLDetail)

        firestore = Firebase.firestore
        val resources = firestore
            .collection("resources")
            .document(resourceId)
            .get()
            .addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        if (document.exists()) {
                            val resource = document.toObject(Resource::class.java)
                            if(resource != null) {
                                Picasso.get().load(resource.image).into(resourceImageDetail)
                                resourceTitleDetail.text = resource.title
                                resourceDateDetail.text = "Publicado el ${getHourAndMinuteFromDate(resource.date)}"
                                resourceDescriptionDetail.text = resource.description
                                resourceURLDetail.text = resource.url
                            }
                        } else {
                            Log.d(TAG, "No se encontro el recurso")
                        }
                    }
                } else {
                    Log.d(TAG, "Error obteniendo el recurso ", task.exception)
                }
            })

    }

    private fun getHourAndMinuteFromDate(date: Date): String {
        val dateFormat = SimpleDateFormat("EEEE dd MMMM yyyy", Locale("es", "ES"))
        return dateFormat.format(date)
    }
}