package com.main.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Location
import com.squareup.picasso.Picasso

class Location_RecyclerViewAdapter(
    private val locationItems: List<Location>?
) : RecyclerView.Adapter<Location_RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView =
            itemView.findViewById(R.id.tv_locationitem_name) // Change to your image view ID
        val image: ImageView =
            itemView.findViewById(R.id.iv_locationitem_display) // Change to your name TextView ID
        val details: TextView =
            itemView.findViewById(R.id.tv_locationitem_details) // Change to your price TextView ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.testlayout_location_item, parent, false) // Change to your item layout
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = locationItems?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationItem = locationItems?.get(position) ?: return

        holder.name.text = locationItem.name
        holder.details.text = locationItem.details
        locationItem.image?.let {
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image)
        }
    }

}