package com.example.pokemon.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.PokemonData
import com.example.pokemon.data.PokemonIndividualDetails
import com.example.pokemon.data.PokemonList
import com.example.pokemon.data.ResultsData
import com.example.pokemon.utils.PokemonService
import com.example.pokemon.utils.pokemonApi
import kotlinx.coroutines.launch
import java.lang.Exception

class OverviewModel(): ViewModel() {


    /**
     * LiveData for gotten pokemon data
     */
    private val pokemonData = MutableLiveData<PokemonData>()

      fun getPokemonData(): LiveData<PokemonData>{
          return  pokemonData
      }



    init {
        getPokemon()


    }

    /**
     * Use kotlin coroutine to make network call and get json results
     */
     fun getPokemon(){

        viewModelScope.launch {
           try {
               val listResult = pokemonApi.retrofitService.getPokemon()

               pokemonData.value = listResult
               PokemonList.addList(pokemonData = listResult)


           } catch (e: Exception){


           }
        }

    }


}