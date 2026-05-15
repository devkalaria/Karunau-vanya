package com.example.karunadavaanya

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.karunadavaanya.adapter.AnimalAdapter
import com.example.karunadavaanya.model.Animal

class WildlifeWikiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wildlife_wiki)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val animalList = listOf(
            Animal(1, "Black Panther", 
                "The ghost of the forest.", 
                "The black panther is a melanistic color variant of the leopard (Panthera pardus) and the jaguar (Panthera onca). They are not a distinct species but are defined by the excess of black pigment (melanin) in their fur, which often conceals their typical spots or rosettes.",
                "Tropical rainforests, marshlands, and dense woodlands across Asia, Africa, and Central/South America.",
                "Carnivorous: Preys on deer, wild boar, monkeys, and smaller mammals or birds.",
                "Vulnerable / Endangered (depending on the base species and region).",
                "Black panthers have excellent hearing and vision, making them one of the most effective nocturnal hunters in the wild.",
                "Stay calm. Avoid eye contact. Slowly back away and do not run. Make yourself appear larger if the animal approaches.",
                R.drawable.blackpanther),
            Animal(2, "Hornbill", "The majestic bird with a large beak.", "Hornbills are a family of birds found in tropical and subtropical Africa, Asia and Melanesia.", "Forests", "Fruit and insects", "Vulnerable", "They have a unique nesting habit.", "Keep a safe distance to avoid disturbing their natural behavior. Do not feed wild birds.", R.drawable.great_hornbill_female),
            Animal(3, "Sandalwood", "The fragrant heartwood.", "Santalum album, or Indian sandalwood, is a small tropical tree, and the most common source of sandalwood and its oil.", "Dry deciduous forests", "Parasitic tree", "Vulnerable", "It is one of the most expensive woods in the world.", "Protect forest resources. Illegal cutting of sandalwood is a serious offense. Report any suspicious activities to forest authorities.", R.drawable.sandalwood),
            Animal(4, "Elephant", "The gentle giant of the wild.", "Elephants are the largest existing land animals. Three species are currently recognised: the African bush elephant, the African forest elephant, and the Asian elephant.", "Savannahs and forests", "Herbivorous", "Endangered", "They are highly intelligent and have complex social structures.", "Maintain a safe distance. Avoid loud sounds or sudden movements. Move away from known herd paths and never block their way.", R.drawable.elephant),
            Animal(5, "Tiger", "The king of the jungle.", "The Bengal tiger is the national animal of India. It is one of the most iconic and powerful predators in the world, known for its orange coat with black stripes.", "Tropical forests, mangroves, and grasslands.", "Carnivorous: Preys on large ungulates like deer, wild boar, and gaurs.", "Endangered", "Each tiger has a unique pattern of stripes, much like human fingerprints.", "Maintain a safe distance. Never approach a tiger. Avoid running; instead, slowly back away while facing the animal. Avoid direct confrontation.", R.drawable.tiger),
            Animal(6, "Cheetah", "The fastest land animal.", "The cheetah is the world's fastest land animal, capable of reaching speeds up to 110 km/h in short bursts. They have a slender body and distinctive 'tear marks'.", "Savannas and open woodlands.", "Carnivorous: Primarily hunts small to medium-sized ungulates like gazelles.", "Vulnerable", "Unlike other big cats, cheetahs cannot roar; they purr, hiss, and chirp.", "Cheetahs usually avoid humans. Stay calm, maintain a safe distance, and avoid approaching cubs. Do not make sudden movements.", R.drawable.cheetah)
        )

        val adapter = AnimalAdapter(animalList) { animal ->
            val intent = Intent(this, AnimalDetailActivity::class.java)
            intent.putExtra("animal_data", animal)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }
}
