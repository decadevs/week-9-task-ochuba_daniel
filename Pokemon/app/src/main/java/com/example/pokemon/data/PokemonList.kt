package com.example.pokemon.data

import android.util.Log
import com.example.pokemon.MainActivity

/**
 * Handles saving each pokemon details
 */
class PokemonList {

   companion object{

       var pokemonList = arrayListOf<ResultsData>()


       fun addList(pokemonData: PokemonData){

           pokemonList = pokemonData.results as ArrayList<ResultsData>

           Log.d("RESULTS", "$pokemonList")

       }

       fun getList(): ArrayList<ResultsData> {

           return pokemonList
       }
   }
}