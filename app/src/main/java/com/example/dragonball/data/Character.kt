package com.example.dragonball.data

import com.google.gson.annotations.SerializedName

class CharacterList (
    @SerializedName("items") val items: List<Character>
) {
}

data class Character (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ki") val ki: String,
    @SerializedName("race") val race: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String
) {
}