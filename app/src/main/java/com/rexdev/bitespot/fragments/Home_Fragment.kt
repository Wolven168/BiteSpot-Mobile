package com.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Global
import com.rexdev.bitespot.functions.Location
import com.main.recyclerViewAdapter.Location_RecyclerViewAdapter
import com.rexdev.bitespot.functions.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home_Fragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var recyclerViewLocationAdapter: Location_RecyclerViewAdapter? = null
    private var locationList = mutableListOf<Location>()
    private val g = Global()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.testlayout_location_itemlist, container, false)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rv_location_list)
        recyclerViewLocationAdapter = Location_RecyclerViewAdapter(locationList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewLocationAdapter

        // Get device location and fetch data from API
        g.getCurrentLocation(requireContext()) { latitude, longitude ->
            // Check if we have valid coordinates
            if (latitude != 0.0 && longitude != 0.0) {
                fetchLocationsWithinRadius(latitude, longitude)
            } else {
                // Handle case where location is unavailable
                Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show()
            }
        }

        return view // Return the inflated view here
    }

    // Fetch locations within 1km radius from the current location
    private fun fetchLocationsWithinRadius(latitude: Double, longitude: Double) {
        RetrofitClient.api.getLocationsWithinRadius(latitude, longitude, 1.0) // 1km radius
            .enqueue(object : Callback<List<Location>> {
                override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                    if (response.isSuccessful) {
                        // Clear the list and add the new data
                        locationList.clear()
                        locationList.addAll(response.body() ?: emptyList())
                        recyclerViewLocationAdapter?.notifyDataSetChanged()
                    } else {
                        // Handle error response
                        Toast.makeText(requireContext(), "Failed to load locations", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                    // Handle network or other failures
                    Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
