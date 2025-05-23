package com.example.dragonball.data

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

class CharacterList (
    @SerializedName("items") val items: List<Character>
) {
}

data class Character (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("ki") val ki: String,
    @SerializedName("maxki") val maxki: String,
    @SerializedName("race") val race: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("affiliation") val affiliation	: String,
    //@SerializedName("deletedAt") val deletedAt : String,
    @SerializedName("originPlanet") val originPlanet : OriginPlanet,
    @SerializedName("transformations") val transformations : List<Trasnformation>
) {
}
class OriginPlanet (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("isDestroyed") val isDestroyed: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image	: String,
    //@SerializedName("deletedAt") val deletedAt: Int,
) { }

class Trasnformation (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image	: String,
    @SerializedName("ki") val ki	: String
    //@SerializedName("deletedAt") val deletedAt: Int,
) { }