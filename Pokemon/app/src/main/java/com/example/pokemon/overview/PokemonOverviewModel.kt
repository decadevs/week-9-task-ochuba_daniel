package com.example.pokemon.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.PokemonDetailsList
import com.example.pokemon.data.PokemonIndividualDetails
import com.example.pokemon.utils.pokemonApi
import kotlinx.coroutines.launch
import java.lang.Exception

class PokemonOverviewModel():ViewModel() {
    private var pokemonIndividualData = MutableLiveData<PokemonIndividualDetails>()


    fun getPokemonIndividualDataDetails(): LiveData<PokemonIndividualDetails> {
        return pokemonIndividualData
    }



    /**
     * Use kotlin coroutine to make network call and get json results of each pokemon
     */
    fun getPokemonIndividualDetails(id:String){
        viewModelScope.launch {
            try {
                val pokemonIndividualResults = pokemonApi.retrofitService.getPokemonDetails(id)


               Log.d("SUCCESSDETAILS", "${pokemonIndividualResults}")
                pokemonIndividualData.setValue(pokemonIndividualResults)

                PokemonDetailsList.addList(pokemonIndividualResults)

            } catch (e: Exception){
                Log.d("FAILUREDETAILS", "$e")
            }
        }
    }
}