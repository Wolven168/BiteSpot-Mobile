package com.rexdev.bitespot.activities

import android.content.Context
import android.content.Intent
import android.net.LinkAddress
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.activities.MainActivity
import com.rexdev.bitespot.R
import com.rexdev.bitespot.functions.Comment
import com.rexdev.bitespot.functions.CommentReq
import com.rexdev.bitespot.functions.Global
import com.rexdev.bitespot.recyclerViewAdapter.CommentRecyclerViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.Address

class LocationActivity : AppCompatActivity() {

    lateinit var tvName : TextView
    lateinit var tvAddress: TextView
    lateinit var ratingBar: RatingBar
    lateinit var locationImage : ImageView
    lateinit var btnBack : Button
    var linkAddress : String = ""

    lateinit var commentList : List<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testlayout_view_location)
        enableEdgeToEdge()

        Global.init(this)

        tvName = findViewById(R.id.tv_name_view)
        tvAddress = findViewById(R.id.tv_address_view)
        ratingBar = findViewById(R.id.rb_rating_view)
        locationImage = findViewById(R.id.iv_locationimage_view)
        btnBack = findViewById(R.id.btn_back_view)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_comments_location)
        val reviewButton = findViewById<Button>(R.id.btn_addreview_view)

        // Get the location ID from the intent
        val locationId = intent.getIntExtra("LOCATION_ID", -1)

        if (locationId != -1) {
            // Use the locationId to fetch more details or display them
            loadData(locationId)
            loadDComments(locationId, recyclerView, this)
        } else {
            // Handle the case where the location ID is not available
            Toast.makeText(this, "Invalid location ID", Toast.LENGTH_SHORT).show()
        }

        reviewButton.setOnClickListener {
            if (Global.LOGGED) {
                showAddReviewDialog()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        btnBack.setOnClickListener { back() }
    }

    private fun loadDComments(id : Int, recyclerView: RecyclerView, context: Context) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getComments(id.toString())
                Log.d("LocationDebug", "Response: $response") // Debugging line
                if (!response.isEmpty()) {
                    commentList = response
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = CommentRecyclerViewAdapter(commentList)
                } else {
                    Toast.makeText(this@LocationActivity, "No Comments Found", Toast.LENGTH_SHORT).show()
                }
            } catch (e : Exception) {
                Log.e("LocationDebug", "Error: ${e.message}")
                Toast.makeText(this@LocationActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData(id: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getLocation(id.toString())
                Log.d("LocationDebug", "Response: $response") // Debugging line
                if (response.success && response.data != null) {
                    tvName.text = response.data.name
                    tvAddress.text = response.data.address
                    linkAddress = response.data.linkAddress.toString()
                    ratingBar.rating = getRating(response.data.rating, response.data.totalRating)
                    if (!response.data.image.isNullOrEmpty()) {
                        Picasso.get()
                            .load(response.data.image)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.baseline_home_24)
                            .fit() // Fit the image to the ImageView dimensions
                            .centerCrop() // Center and crop the image
                            .into(locationImage)
                    } else {
                        locationImage.setImageResource(R.drawable.ic_launcher_background)
                    }
                }
            } catch (e : Exception) {
                Log.e("LocationDebug", "Error: ${e.message}")
                Toast.makeText(this@LocationActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAddReviewDialog() {
        val dialogView = layoutInflater.inflate(R.layout.testlayout_addreview, null)
        val etComment = dialogView.findViewById<EditText>(R.id.et_comment)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.rating_bar)

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Add Review")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            val comment = etComment.text.toString()
            val rating = ratingBar.rating.toInt()

            if (comment.isNotBlank() && rating > 0) {
                alertDialog.dismiss()
                submitReview(comment, rating)
            } else {
                Toast.makeText(this, "Please enter a comment and select a rating", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.show()
    }

    private fun submitReview(comment: String, rating: Int) {
        val locationId = intent.getIntExtra("LOCATION_ID", -1)
        if (locationId == -1) return

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.comment(
                    CommentReq(
                        loc_id = locationId,
                        user_id = Global.ID,  // Replace with your actual user ID getter
                        text = comment,
                        rating = rating
                    )
                )
                if (response.success) {
                    Toast.makeText(this@LocationActivity, "Review submitted!", Toast.LENGTH_SHORT).show()
                    loadDComments(locationId, findViewById(R.id.rv_comments_location), this@LocationActivity)
                } else {
                    Toast.makeText(this@LocationActivity, "Failed to submit review", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LocationActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getRating(rating : Double, totalRating : Int) : Float {
        val rate = (rating / totalRating.toDouble()) * 5
        val finalRating : Float = rate.toFloat()
        return finalRating
    }

    private fun back() {
        val intent = Intent(this, MainActivity::class.java)  // Switch context using requireContext()
        startActivity(intent)
        finish()  // Finish the parent activity to prevent returning
    }
}