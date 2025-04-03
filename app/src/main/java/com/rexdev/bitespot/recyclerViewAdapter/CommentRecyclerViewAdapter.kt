package com.rexdev.bitespot.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.main.recyclerViewAdapter.Location_RecyclerViewAdapter
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Comment
import com.rexdev.bitespot.functions.Location
import com.squareup.picasso.Picasso

class CommentRecyclerViewAdapter (
    private val commentItems: List<Comment>?
) : RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.tv_username_comment)
        val locname: TextView = itemView.findViewById(R.id.tv_location_comment)
        val image: ImageView = itemView.findViewById(R.id.iv_image_comment)
        val details: TextView = itemView.findViewById(R.id.tv_text_comment)
        val rating: RatingBar = itemView.findViewById(R.id.rb_rating_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_rvitem, parent, false) // Use location_item.xml
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = commentItems?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentItem = commentItems?.get(position) ?: return

        holder.username.text = commentItem.username
        holder.details.text = commentItem.text


        val imageUrl = commentItem.locImg

        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.baseline_home_24)
                .fit() // Fit the image to the ImageView dimensions
                .centerCrop() // Center and crop the image
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_background)
        }

        holder.rating.rating = commentItem.rating.toFloat()
    }
}