package com.rexdev.bitespot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.recyclerViewAdapter.Location_RecyclerViewAdapter
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Location

class SearchFragment : Fragment() {
    private lateinit var recyclerView : RecyclerView
    private var recyclerViewLocationAdapter: Location_RecyclerViewAdapter? = null
    private var locationList = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.testlayout_search, container, false)

        // Set the adapter
        recyclerViewLocationAdapter = Location_RecyclerViewAdapter(locationList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewLocationAdapter

        loadSampleSet()

        return view
    }

    private fun loadLocationSet() {

    }

    private fun loadSampleSet() {
        val sampleItems = listOf(
            Location(1,"Sample Name1", "Malabago, Calasiao", 5.00, 5.001, 5.00, "Lorem ipsum", 5, null, "", 5),
            Location(2,"Sample Name2", "Dagupan, Upang", 1.00, 1.001,4.00, "Something Something", 4, null, "", 4)
        )

        // Clear the list and add sample items
        locationList.clear()
        locationList.addAll(sampleItems)

        // Notify the adapter about data changes
        recyclerViewLocationAdapter?.notifyDataSetChanged()
    }
}