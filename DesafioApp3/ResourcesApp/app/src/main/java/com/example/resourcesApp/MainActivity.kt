package com.example.resourcesApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.resourcesApp.Screens.CategoryListScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val intent = Intent(this, CategoryListScreen::class.java)
        startActivity(intent)
        finish()
    }
}