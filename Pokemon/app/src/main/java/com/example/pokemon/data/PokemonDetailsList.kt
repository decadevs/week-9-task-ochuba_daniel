package com.example.pokemon.data

import android.util.Log
import com.example.pokemon.PokemonDetails

class PokemonDetailsList {
    /**
     * Get the pokemon and add it to this class
     */
    companion object{
        var pokemonDetailsList = arrayListOf<PokemonIndividualDetails>()


        fun addList(pokemonData: PokemonIndividualDetails){

            pokemonDetailsList.add(pokemonData)

            Log.d("RESULTS", "${PokemonDetailsList}")

        }

        fun getList(): ArrayList<PokemonIndividualDetails> {
            Log.d("RESULTS", "${PokemonDetailsList}")
            return pokemonDetailsList
        }
    }
}