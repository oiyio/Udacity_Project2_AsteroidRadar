package com.udacity.asteroidradar.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class AsteroidAdapter : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById<View>(R.id.textViewName) as TextView
        var textViewDate: TextView = itemView.findViewById<View>(R.id.textViewDate) as TextView
        var imageViewHazardous : ImageView = itemView.findViewById(R.id.imageViewHazardous)

        fun bind(item: Asteroid) {
            textViewName.text = item.codename
            textViewDate.text = item.closeApproachDate
            if(item.isPotentiallyHazardous)
                imageViewHazardous.setImageResource(R.drawable.ic_status_potentially_hazardous)
            else
                imageViewHazardous.setImageResource(R.drawable.ic_status_normal)

            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val topicView = inflater.inflate(R.layout.item_asteroid, parent, false)
        return ViewHolder(topicView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
}