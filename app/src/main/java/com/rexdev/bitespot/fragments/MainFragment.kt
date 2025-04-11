package com.rexdev.bitespot.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.recyclerViewAdapter.Location_RecyclerViewAdapter
import com.rexdev.bitespot.R
import com.rexdev.bitespot.activities.LocationActivity
import com.rexdev.bitespot.functions.Global
import com.rexdev.bitespot.functions.Location
import com.rexdev.bitespot.functions.LocationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var recyclerViewLocationAdapter: Location_RecyclerViewAdapter? = null
    private var locationList = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.testlayout_location_itemlist, container, false)

        // Initialize RecyclerView and setup adapter
        setupRecyclerview(view)

        // Fetch user location
        getUserLocation()

        return view
    }

    private fun setupRecyclerview(view: View) {
        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rv_location_list)
        recyclerViewLocationAdapter = Location_RecyclerViewAdapter(locationList) { location ->
            val intent = Intent(requireContext(), LocationActivity::class.java)
            // Put the location ID as an extra in the Intent
            intent.putExtra("LOCATION_ID", location.id)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewLocationAdapter
    }

    private fun getUserLocation() {
        Global.getCurrentLocation(requireContext()) { latitude, longitude ->
            if (latitude != 0.0 && longitude != 0.0) {
                // Store the user's current location in Global
                Global.LATITUDE = latitude
                Global.LONGITUDE = longitude
                Log.d("DEBUG", "Location stored: Latitude = $latitude, Longitude = $longitude")
                // Once location is obtained, fetch the locations within the range
                fetchLocationsWithinRadius(latitude, longitude)
            } else {
                Log.e("ERROR", "Unable to get the current location")
                Toast.makeText(requireContext(), "Unable to fetch current location", Toast.LENGTH_SHORT).show()
                // Load sample data if location fetch fails
                loadSampleSet()
            }
        }
    }

    private fun fetchLocationsWithinRadius(latitude: Double, longitude: Double) {
        Log.d("DEBUG", "Fetching locations for lat: $latitude, lon: $longitude, radius: ${Global.AREA_RANGE}")

        RetrofitClient.api.getLocations(latitude, longitude, Global.AREA_RANGE)
            .enqueue(object : Callback<LocationResponse> {
                override fun onResponse(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                    Log.d("DEBUG", "Response Code: ${response.code()}")
                    Log.d("DEBUG", "Response Body: ${response.body()}")

                    if (response.isSuccessful) {
                        val locationResponse = response.body()

                        // Log the size of filtered_locations to make sure it contains data
                        Log.d("DEBUG", "Filtered locations size: ${locationResponse?.data?.filtered_locations?.size}")

                        if (locationResponse != null && locationResponse.data.filtered_locations != null) {
                            val filteredLocations = locationResponse.data.filtered_locations

                            // Check if filtered locations list is not empty
                            if (filteredLocations.isNotEmpty()) {
                                locationList.clear() // Clear previous data
                                locationList.addAll(filteredLocations) // Add the new data from API
                                Log.d("DEBUG", "Data from API added to locationList, new size: ${locationList.size}")

                                // Notify adapter to refresh RecyclerView
                                recyclerViewLocationAdapter?.notifyDataSetChanged()
                            } else {
                                Log.e("DEBUG", "Filtered locations is empty or null, loading sample data.")
                                loadSampleSet() // Load sample data if API response is empty
                            }
                        } else {
                            Log.e("DEBUG", "Filtered locations is null, loading sample data.")
                            loadSampleSet() // Fallback to sample data in case filtered_locations is null
                        }
                    } else {
                        Log.e("DEBUG", "API Response Error: ${response.errorBody()?.string()}")
                        loadSampleSet() // Load sample data if API response is unsuccessful
                    }
                }

                override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                    Log.e("ERROR", "API Request Failed: ${t.message}")
                    loadSampleSet() // Load fallback locations
                    Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun loadSampleSet() {
        val sampleItems = listOf(
            Location(1, "Sample Name1", "Malabago, Calasiao", 5.00, 5.001, 5.00, "Lorem ipsum", 5, null, "", 0),
            Location(2, "Sample Name2", "Dagupan, Upang", 1.00, 1.001, 4.00, "Something Something", 4, null, "", 0),
            Location(3, "Sample Name3", "Malabago, Calasiao", 5.00, 5.001, 5.00, "Blah blah", 5, null, "", 0),
            Location(4, "Sample Name4", "Dagupan, Upang", 1.00, 1.001, 4.00, "Shimi shimi yeah shimi yeah shimi yon", 4, null, "", 0)
        )

        // Clear previous list and add sample items
        locationList.clear()
        locationList.addAll(sampleItems)
        recyclerViewLocationAdapter?.notifyDataSetChanged()
    }
}
