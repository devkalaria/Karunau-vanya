package com.example.karunadavaanya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        
        // Enable back button in toolbar if needed
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About App"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
