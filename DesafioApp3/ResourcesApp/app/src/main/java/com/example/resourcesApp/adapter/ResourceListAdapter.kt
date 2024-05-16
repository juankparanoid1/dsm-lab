package com.example.resourcesApp.adapter

import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.resourcesApp.R
import com.example.resourcesApp.Screens.ResourceDetailScreen
import com.example.resourcesApp.dto.Resource
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso

class ResourceListAdapter(private var query: Query) :
    FirestoreAdapter<ResourceListAdapter.ViewHolder>(query) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_resource_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val snapshot = getSnapshot(position)
        val currentItem = snapshot.toObject(Resource::class.java)
        Log.i("item ", currentItem.toString())
        if (currentItem != null) {
            val image = if (currentItem.image.isNullOrEmpty()) {
                "https://firebasestorage.googleapis.com/v0/b/resources-app-de050.appspot.com/o/resources-images%2Fempty-resource.jpeg?alt=media&token=30f02564-08b5-4c7a-a878-d79378e1413e"
            } else {
                currentItem.image
            }
            Picasso.get().load(image).into(holder.imageView)
            holder.textViewName.text = currentItem.title

            holder.tagsLayout.removeAllViews()
            for (tag in currentItem.tags) {
                val tagTextView = TextView(holder.itemView.context)
                tagTextView.text = tag
                tagTextView.setBackgroundResource(R.drawable.tag_background)
                tagTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.tag_text_color))
                tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
                tagTextView.setPadding(16, 8, 16, 8)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, 0, 16, 0)
                tagTextView.layoutParams = layoutParams
                holder.tagsLayout.addView(tagTextView)
            }

            holder.itemView.setOnClickListener {
                if (!snapshot.id.isNullOrEmpty()) {
                    val resourceListScreen =
                        Intent(holder.itemView.context, ResourceDetailScreen::class.java)
                    resourceListScreen.putExtra("resourceId", snapshot.id)
                    holder.itemView.context.startActivity(resourceListScreen)
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.resourceImageCard)
        val textViewName: TextView = itemView.findViewById(R.id.resourceName)
        val tagsLayout: LinearLayout = itemView.findViewById(R.id.tagsLayout)
    }
}