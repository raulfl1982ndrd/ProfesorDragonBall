package com.example.dragonball.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dragonball.data.Character
import com.example.dragonball.databinding.ItemCharacterBinding
import com.squareup.picasso.Picasso

class CharacterAdapter (
    private var dataSet: List<Character> = emptyList(),
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context))
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }
    }

    fun updateData(dataSet: List<Character>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}

class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(character: Character) {
        binding.nameTextView.text = character.name
        binding.raceTextView.text = "${character.race} - ${character.gender}"
        Picasso.get().load(character.image).into(binding.avatarImageView)
    }
}