package com.example.karunadavaanya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.karunadavaanya.adapter.AlertAdapter
import com.example.karunadavaanya.model.Alert
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MovementAlertActivity : AppCompatActivity() {

    private lateinit var rvAlerts: RecyclerView
    private lateinit var adapter: AlertAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvFilterStatus: TextView
    private val alertList = mutableListOf<Alert>()
    private lateinit var database: com.google.firebase.database.DatabaseReference
    private var userDistrict: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movement_alert)

        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        userDistrict = sharedPref.getString("selected_district", "") ?: ""

        // Initialize Database with specific URL
        val databaseUrl = "https://karunadavanya-972bb-default-rtdb.asia-southeast1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("Alerts")
        database.keepSynced(true)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        rvAlerts = findViewById(R.id.rvAlerts)
        progressBar = findViewById(R.id.progressBar)
        tvFilterStatus = findViewById(R.id.tvFilterStatus)
        val fabReport: FloatingActionButton = findViewById(R.id.fabReport)

        tvFilterStatus.text = "Showing alerts for: $userDistrict"

        rvAlerts.layoutManager = LinearLayoutManager(this)
        adapter = AlertAdapter(alertList)
        rvAlerts.adapter = adapter

        fabReport.setOnClickListener {
            startActivity(Intent(this, ReportAlertActivity::class.java))
        }

        fetchAlerts()
    }

    private fun fetchAlerts() {
        progressBar.visibility = View.VISIBLE
        val currentTime = System.currentTimeMillis()
        val sixHoursInMillis = 6 * 60 * 60 * 1000 // 6 hours expiration

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                alertList.clear()
                for (alertSnapshot in snapshot.children) {
                    val alert = alertSnapshot.getValue(Alert::class.java)
                    if (alert != null) {
                        // District Filter
                        if (alert.district.equals(userDistrict, ignoreCase = true)) {
                            var alertTime = alert.createdAt
                            
                            if (alertTime == 0L) {
                                try {
                                    val sdf = java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a", java.util.Locale.getDefault())
                                    alertTime = sdf.parse(alert.timestamp)?.time ?: 0L
                                } catch (ignored: Exception) {
                                    alertTime = 0L
                                }
                            }

                            if (alertTime != 0L && (currentTime - alertTime) > sixHoursInMillis) {
                                alertSnapshot.ref.removeValue()
                            } else {
                                alertList.add(alert)
                            }
                        }
                    }
                }
                alertList.reverse()
                if (alertList.isEmpty()) {
                    Toast.makeText(this@MovementAlertActivity, "No active alerts for $userDistrict", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MovementAlertActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
