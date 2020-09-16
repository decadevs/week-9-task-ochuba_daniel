package com.example.pokemon.utils

import com.example.pokemon.data.PokemonData
import com.example.pokemon.data.PokemonIndividualDetails
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://pokeapi.co/api/v2/"
  var INDIVIDUAL_POKEMON = ""

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


// initialise retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

 interface PokemonService{


     // get each individual pokemon
     @GET("pokemon")
    suspend fun getPokemon(): PokemonData


     //get pokemon details
     @GET("pokemon/{pokemon}")
     suspend fun getPokemonDetails(@Path("pokemon") pokemon:String ): PokemonIndividualDetails
 }

 object pokemonApi{
     //initialse pokemon service by lazy
     val retrofitService: PokemonService by lazy {
         retrofit.create(PokemonService::class.java)
     }
 }