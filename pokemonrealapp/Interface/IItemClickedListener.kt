package com.mylearning.pokemonrealapp.Interface

import android.view.View

// Item click listener interface
interface IItemClickedListener {
    fun onClick (view: View, position : Int)
}