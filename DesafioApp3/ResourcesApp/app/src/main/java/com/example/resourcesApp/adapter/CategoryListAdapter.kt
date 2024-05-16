package com.example.resourcesApp.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resourcesApp.R
import com.example.resourcesApp.Screens.ResourceListScreen
import com.example.resourcesApp.dto.Category
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso

open class CategoryListAdapter(private var query: Query) :
    FirestoreAdapter<CategoryListAdapter.ViewHolder>(query) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_category_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val snapshot = getSnapshot(position)
        val currentItem = snapshot.toObject(Category::class.java)
        Log.i("item ", currentItem.toString())
        if (currentItem != null) {
            val image = if (currentItem.image.isNullOrEmpty()) {
                "https://firebasestorage.googleapis.com/v0/b/resources-app-de050.appspot.com/o/resources-images%2Flohp-category-it-and-software-2x-v2.jpg?alt=media&token=33134a3c-0825-46f7-8455-4ea157a34ba7"
            } else {
                currentItem.image
            }
            Picasso.get().load(image).into(holder.imageView)
            holder.textViewName.text = currentItem.name
            holder.itemView.setOnClickListener {
                if (!snapshot.id.isNullOrEmpty()) {
                    val resourceListScreen =
                        Intent(holder.itemView.context, ResourceListScreen::class.java)
                    resourceListScreen.putExtra("categoryId", snapshot.id)
                    resourceListScreen.putExtra("name", currentItem.name)
                    holder.itemView.context.startActivity(resourceListScreen)
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.catgoryImageCard)
        val textViewName: TextView = itemView.findViewById(R.id.categoryName)
    }
}