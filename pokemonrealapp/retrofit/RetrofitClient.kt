package com.mylearning.pokemonrealapp.retrofit

import com.mylearning.pokemonrealapp.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


// Retrofit client object
object RetrofitClient {

    private var ourInstance: Retrofit? = null
    val instance: Retrofit
        get() {
            if (ourInstance == null)
                ourInstance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return ourInstance!!
        }

    val service = instance.create(IPokemonList::class.java)
}