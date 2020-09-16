package com.mylearning.pokemonrealapp.utils

import android.view.View

fun View.hide():Boolean{
    this.visibility = View.INVISIBLE
    if(this.visibility == View.VISIBLE){
        this.visibility = View.INVISIBLE
        return true
    }
    return true
}

fun View.show():Boolean{
    this.visibility = View.VISIBLE
    if(this.visibility == View.INVISIBLE){
        this.visibility = View.VISIBLE
        return true
    }
    return false
}