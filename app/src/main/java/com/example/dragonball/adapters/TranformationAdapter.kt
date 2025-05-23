package com.example.dragonball.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dragonball.R
import com.example.dragonball.data.Trasnformation
import com.squareup.picasso.Picasso

class TranformationAdapter(private val transformations: List<Trasnformation>) :
    RecyclerView.Adapter<TranformationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tranformationNameImageView: ImageView = itemView.findViewById(R.id.transformationImageView)
        val tranformationNameTextView: TextView = itemView.findViewById(R.id.transformationNameTextView)
        val transformationKiTextView: TextView = itemView.findViewById(R.id.transformationKiTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tranformation, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = transformations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transformation = transformations[position]
        Picasso.get().load(transformation.image).into(holder.tranformationNameImageView)
        holder.tranformationNameTextView.text = transformations[position].name
        holder.transformationKiTextView.text = transformations[position].ki
    }

    }