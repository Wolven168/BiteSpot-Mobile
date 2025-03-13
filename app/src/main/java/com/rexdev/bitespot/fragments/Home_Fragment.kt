package com.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.main.recyclerViewAdapter.Location_RecyclerViewAdapter
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Location

class Home_Fragment : Fragment() {
    private lateinit var recyclerView : RecyclerView
    private var recyclerViewLocationAdapter: Location_RecyclerViewAdapter? = null
    private var locationList = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.testlayout_location_itemlist, container, false)

        // Set the adapter
        recyclerView = view.findViewById(R.id.rv_location_list)
        recyclerViewLocationAdapter = Location_RecyclerViewAdapter(locationList)
        recyclerView.adapter = recyclerViewLocationAdapter

        loadSampleSet()

        return view
    }

    private fun loadLocationSet() {

    }

    private fun loadSampleSet() {
        val sampleItems = listOf(
            Location("Sample Name1", "Malabago, Calasiao", 5.00, 5, "Lorem ipsum", null),
            Location("Sample Name2", "Dagupan, Upang", 4.00, 4, "Something Something", null)
        )

        // Clear the list and add sample items
        locationList.clear()
        locationList.addAll(sampleItems)

        // Notify the adapter about data changes
        recyclerViewLocationAdapter?.notifyDataSetChanged()
    }
}