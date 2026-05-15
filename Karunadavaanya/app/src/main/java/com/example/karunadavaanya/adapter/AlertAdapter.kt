package com.example.karunadavaanya.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.karunadavaanya.R
import com.example.karunadavaanya.model.Alert

class AlertAdapter(private val alerts: List<Alert>) :
    RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    class AlertViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val animalName: TextView = view.findViewById(R.id.alertAnimal)
        val alertMessage: TextView = view.findViewById(R.id.alertMessage)
        val location: TextView = view.findViewById(R.id.alertLocation)
        val district: TextView = view.findViewById(R.id.alertDistrict)
        val timestamp: TextView = view.findViewById(R.id.alertTime)
        val severityStrip: View = view.findViewById(R.id.severityStrip)
        val alertIcon: ImageView = view.findViewById(R.id.alertIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alert, parent, false)
        return AlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        val alert = alerts[position]
        holder.animalName.text = alert.animalName
        holder.alertMessage.text = alert.description
        holder.location.text = alert.location
        holder.district.text = alert.district
        holder.timestamp.text = alert.timestamp

        // Premium Severity Logic
        val dangerAnimals = listOf("Tiger", "Leopard", "Elephant", "Black Panther")
        val color = if (dangerAnimals.any { it.equals(alert.animalName, ignoreCase = true) }) {
            R.color.danger_red
        } else if (alert.animalName.equals("Sandalwood", ignoreCase = true)) {
            R.color.info_blue
        } else {
            R.color.warning_amber
        }
        
        val context = holder.itemView.context
        val resolvedColor = ContextCompat.getColor(context, color)
        
        holder.severityStrip.setBackgroundColor(resolvedColor)
        holder.alertIcon.setColorFilter(resolvedColor)
    }

    override fun getItemCount() = alerts.size
}
