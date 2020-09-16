package com.example.pokemon.adapter

import com.example.pokemon.data.PokemonData
import com.example.pokemon.data.ResultsData

/**
 * Interface to handle recyclerItem click
 */

interface OnPokemonListClick {

    fun onItemClick(item: ResultsData, position: Int){

    }
}