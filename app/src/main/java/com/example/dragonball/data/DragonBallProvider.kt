package com.example.dragonball.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DragonBallProvider {

    suspend fun getAllCharacters() : List<Character> {
        val apiService = getRetrofit().create(DragonBallApiService::class.java)
        val result = apiService.findAllCharacters()
        return result.items
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getCharacterApiService():DragonBallApiService{
        return getRetrofit().create(DragonBallApiService::class.java)
    }

}