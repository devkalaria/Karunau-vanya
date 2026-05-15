package com.example.karunadavaanya

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.karunadavaanya.model.Animal

class AnimalDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_detail)

        val animal = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("animal_data", Animal::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("animal_data") as? Animal
        }

        if (animal != null) {
            findViewById<ImageView>(R.id.detailImage).setImageResource(animal.imageResourceId)
            findViewById<TextView>(R.id.detailName).text = animal.name
            findViewById<TextView>(R.id.detailFullDescription).text = animal.fullDescription
            
            findViewById<TextView>(R.id.detailHabitat).text = animal.habitat
            findViewById<TextView>(R.id.detailDiet).text = animal.diet
            findViewById<TextView>(R.id.detailStatus).text = animal.conservationStatus
            findViewById<TextView>(R.id.detailFact).text = animal.interestingFact
            findViewById<TextView>(R.id.detailSafety).text = animal.safetyMeasures
        }
    }
}
