package com.example.dragonball.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DragonBallApiService {
    @GET("characters?limit=100")
    suspend fun findAllCharacters() : CharacterList

    @GET("characters?limit=100")
    suspend fun findCharactersByName(@Query("name") query: String) : CharacterList

    @GET("characters/{character-id}")
    suspend fun getCharacterById(@Path("character-id") id: Int) : Character
}