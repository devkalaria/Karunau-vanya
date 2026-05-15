package com.example.karunadavaanya

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.karunadavaanya.model.Alert
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportAlertActivity : AppCompatActivity() {

    private lateinit var etAnimalName: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etLocation: TextInputEditText
    private lateinit var etDistrict: TextInputEditText
    private lateinit var btnSubmitAlert: MaterialButton

    private lateinit var database: com.google.firebase.database.DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_alert)

        // Initialize Database with specific URL
        val databaseUrl = "https://karunadavanya-972bb-default-rtdb.asia-southeast1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("Alerts")

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        etAnimalName = findViewById(R.id.etAnimalName)
        etDescription = findViewById(R.id.etDescription)
        etLocation = findViewById(R.id.etLocation)
        etDistrict = findViewById(R.id.etDistrict)
        btnSubmitAlert = findViewById(R.id.btnSubmitAlert)

        // Auto-fill district from SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
        val savedDistrict = sharedPref.getString("selected_district", "")
        etDistrict.setText(savedDistrict)

        btnSubmitAlert.setOnClickListener {
            submitAlert()
        }
    }

    private fun submitAlert() {
        val animalName = etAnimalName.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val district = etDistrict.text.toString().trim()
        val timestamp = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())

        if (animalName.isEmpty() || description.isEmpty() || location.isEmpty() || district.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val alertId = database.push().key ?: run {
            Toast.makeText(this, "Failed to generate ID", Toast.LENGTH_SHORT).show()
            return
        }
        
        Toast.makeText(this, "Submitting alert...", Toast.LENGTH_SHORT).show()
        val createdAt = System.currentTimeMillis()
        val alert = Alert(animalName, description, location, district, timestamp, createdAt)

        database.child(alertId).setValue(alert).addOnSuccessListener {
            Toast.makeText(this, "Alert submitted successfully!", Toast.LENGTH_SHORT).show()
            etAnimalName.text?.clear()
            etDescription.text?.clear()
            etLocation.text?.clear()
            etDistrict.text?.clear()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to submit alert: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
