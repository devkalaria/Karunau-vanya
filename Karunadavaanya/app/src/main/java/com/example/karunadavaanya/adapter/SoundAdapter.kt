package com.example.karunadavaanya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karunadavaanya.R
import com.example.karunadavaanya.model.Sound
import com.google.android.material.button.MaterialButton

class SoundAdapter(
    private val sounds: List<Sound>,
    private val onPlayClick: (Sound) -> Unit
) : RecyclerView.Adapter<SoundAdapter.SoundViewHolder>() {

    class SoundViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val soundName: TextView = view.findViewById(R.id.soundName)
        val btnPlay: MaterialButton = view.findViewById(R.id.btnPlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sound, parent, false)
        return SoundViewHolder(view)
    }

    override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
        val sound = sounds[position]
        holder.soundName.text = sound.name
        holder.btnPlay.setOnClickListener { onPlayClick(sound) }
    }

    override fun getItemCount() = sounds.size
}
