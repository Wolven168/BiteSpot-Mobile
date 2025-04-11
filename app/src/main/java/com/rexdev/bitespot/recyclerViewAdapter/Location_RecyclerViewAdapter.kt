package com.main.recyclerViewAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rexdev.bitespot.R
import com.rexdev.bitespot.activities.LocationActivity
import com.rexdev.bitespot.functions.Location
import com.squareup.picasso.Picasso

class Location_RecyclerViewAdapter(
    private val locationItems: List<Location>?,
    private val goToLocation : (Location) -> Unit
) : RecyclerView.Adapter<Location_RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_locationitem_name)
        val image: ImageView = itemView.findViewById(R.id.iv_locationitem_display)
        val details: TextView = itemView.findViewById(R.id.tv_locationitem_details)
        val btn_view: Button = itemView.findViewById(R.id.btn_locationitem_view)
        val review: RatingBar = itemView.findViewById(R.id.rb_location_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.testlayout_location_item, parent, false) // Use location_item.xml
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = locationItems?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationItem = locationItems?.get(position) ?: return

        holder.name.text = locationItem.name
        holder.details.text = locationItem.description
        holder.review.rating = getRating(locationItem.rating, locationItem.totalRating)

        val imageUrl = locationItem.image

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

        holder.btn_view.setOnClickListener {
            // Create an Intent to start the LocationDetailActivity
            goToLocation(locationItem)
        }
    }

    private fun getRating(rating : Double, totalRating : Int) : Float {
        val rate = (rating / totalRating.toDouble()) * 5
        val finalRating : Float = rate.toFloat()
        return finalRating
    }
}