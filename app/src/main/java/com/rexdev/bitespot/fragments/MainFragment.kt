package com.rexdev.bitespot.fragments

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

        getUserLocation()

        setupRecyclerview(view)

        return view
    }

    private fun setupRecyclerview(view: View) {
        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rv_location_list)
        recyclerViewLocationAdapter = Location_RecyclerViewAdapter(locationList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recyclerViewLocationAdapter

        // Use saved coordinates from Global
        val latitude = Global.LATITUDE
        val longitude = Global.LONGITUDE

        if (latitude != 0.0 && longitude != 0.0) {
            fetchLocationsWithinRadius(latitude, longitude)
        } else {
            Toast.makeText(requireContext(), "Invalid location data", Toast.LENGTH_SHORT).show()
            loadSampleSet()
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

                        if (locationResponse?.filtered_locations != null) { // Check if filtered_locations is null
                            locationList.clear()
                            locationList.addAll(locationResponse.filtered_locations)
                            recyclerViewLocationAdapter?.notifyDataSetChanged()
                        } else {
                            Log.e("DEBUG", "filtered_locations is null. Loading sample set.")
                            loadSampleSet() // Load fallback locations
                        }
                    } else {
                        Log.e("DEBUG", "Failed API Response: ${response.errorBody()?.string()}")
                        loadSampleSet()
                        Toast.makeText(requireContext(), "Failed to load locations", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                    Log.e("ERROR", "API Request Failed: ${t.message}")
                    loadSampleSet()
                    Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getUserLocation() {
        Global.getCurrentLocation(requireContext()) { latitude, longitude ->
            if (latitude != 0.0 && longitude != 0.0) {
                // Store the user's current location in Global
                Global.LATITUDE = latitude
                Global.LONGITUDE = longitude
                Log.d("DEBUG", "Location stored: Latitude = $latitude, Longitude = $longitude")
            } else {
                Log.e("ERROR", "Unable to get the current location")
                Toast.makeText(requireContext(), "Unable to fetch current location", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun loadSampleSet() {
        val sampleItems = listOf(
            Location(1,"Sample Name1", "Malabago, Calasiao", 5.00, 5.001, 5.00, "Lorem ipsum", 5, null, "", 0),
            Location(2,"Sample Name2", "Dagupan, Upang", 1.00, 1.001,4.00, "Something Something", 4, null, "", 0)
        )

        locationList.clear()
        locationList.addAll(sampleItems)
        recyclerViewLocationAdapter?.notifyDataSetChanged()
    }
}
