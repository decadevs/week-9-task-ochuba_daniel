package com.mylearning.pokemonrealapp.retrofit

import com.mylearning.pokemonrealapp.model.Pokedex
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Pokemon list interface to det the pokemon details in json format
 */
interface IPokemonList {

    @get:GET("pokedex.json")
    val listPokemon : Observable<Pokedex>
    @GET("pokedex.json")
    fun getPokeMonList():Observable<Pokedex>
}