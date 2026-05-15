package com.example.karunadavaanya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karunadavaanya.R
import com.example.karunadavaanya.model.Animal

class AnimalAdapter(
    private val animals: List<Animal>,
    private val onItemClick: (Animal) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val animalImage: ImageView = itemView.findViewById(R.id.animalImage)
        val animalName: TextView = itemView.findViewById(R.id.animalName)
        val animalDescription: TextView = itemView.findViewById(R.id.animalDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animals[position]
        holder.animalName.text = animal.name
        holder.animalDescription.text = animal.shortDescription
        holder.animalImage.setImageResource(animal.imageResourceId)

        holder.itemView.setOnClickListener {
            onItemClick(animal)
        }
    }

    override fun getItemCount(): Int = animals.size
}
