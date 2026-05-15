package com.example.karunadavaanya

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        checkDistrictSelection()

        val btnOpenWiki = findViewById<Button>(R.id.btnOpenWiki)
        btnOpenWiki.setOnClickListener {
            startActivity(Intent(this, WildlifeWikiActivity::class.java))
        }

        val btnForestSounds = findViewById<Button>(R.id.btnForestSounds)
        btnForestSounds.setOnClickListener {
            startActivity(Intent(this, ForestSoundsActivity::class.java))
        }

        val btnMovementAlert = findViewById<Button>(R.id.btnMovementAlert)
        btnMovementAlert.setOnClickListener {
            startActivity(Intent(this, MovementAlertActivity::class.java))
        }

        val btnAboutApp = findViewById<Button>(R.id.btnAboutApp)
        btnAboutApp.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_location) {
            showDistrictManagementDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkDistrictSelection() {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedDistrict = sharedPref.getString("selected_district", null)

        if (savedDistrict == null) {
            showDistrictSelectionDialog(isFirstLaunch = true)
        }
    }

    private fun showDistrictManagementDialog() {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val currentDistrict = sharedPref.getString("selected_district", "Not Set")

        AlertDialog.Builder(this)
            .setTitle("Manage Location")
            .setMessage("Current District: $currentDistrict")
            .setPositiveButton("Change District") { _, _ ->
                showDistrictSelectionDialog(isFirstLaunch = false)
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showDistrictSelectionDialog(isFirstLaunch: Boolean) {
        val districts = arrayOf("Hassan", "Mysore", "Dakshina Kannada", "Chikkamagaluru", "Other")
        
        AlertDialog.Builder(this)
            .setTitle("Select Your District")
            .setCancelable(!isFirstLaunch)
            .setItems(districts) { _, which ->
                val selected = districts[which]
                if (selected == "Other") {
                    showCustomDistrictInput(isFirstLaunch)
                } else {
                    saveDistrict(selected)
                    if (!isFirstLaunch) {
                        Toast.makeText(this, "District updated to $selected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }

    private fun showCustomDistrictInput(isFirstLaunch: Boolean) {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Enter District Name")
            .setView(input)
            .setCancelable(!isFirstLaunch)
            .setPositiveButton("Save") { _, _ ->
                val customDistrict = input.text.toString().trim()
                if (customDistrict.isNotEmpty()) {
                    saveDistrict(customDistrict)
                    if (!isFirstLaunch) {
                        Toast.makeText(this, "District updated to $customDistrict", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    showDistrictSelectionDialog(isFirstLaunch)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                if (isFirstLaunch) {
                    showDistrictSelectionDialog(isFirstLaunch)
                } else {
                    dialog.dismiss()
                }
            }
            .show()
    }

    private fun saveDistrict(district: String) {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("selected_district", district).apply()
    }
}
