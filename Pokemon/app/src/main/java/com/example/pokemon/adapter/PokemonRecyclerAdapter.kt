package com.example.pokemon.adapter

import android.accounts.AccountManager.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.data.PokemonData
import com.example.pokemon.data.ResultsData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_all_pokemon.view.*
import java.lang.reflect.Array.get

class PokemonRecyclerAdapter(private val pokemons: List<ResultsData>, var listener: OnPokemonListClick) : RecyclerView.Adapter<PokemonRecyclerAdapter.PokemonViewHolder>()  {


    class PokemonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val pokemonName: TextView  = itemView.pokemon_name
        val pokemonNumber: TextView = itemView.pokemon_recycler_item_number
        val pokemonImage: ImageView = itemView.pokemon_individual_image
        val constraintInner: ConstraintLayout = itemView.constraint_inner_item
        val imageBackground:ImageView = itemView.item_image_background


        fun initialise(item: ResultsData, action: OnPokemonListClick){
            pokemonName.text = item.name
            pokemonNumber.text = "#"+ getNumber(item.url)
            Picasso.get()
                .load(getImageUrl(item.url))
                .into(pokemonImage)
            Picasso.get()
                .load(getImageUrl(item.url))
                .into(imageBackground)

            //randomly access background color to recycle item
            when(getNumber(item.url).toInt() % 5){

                0 -> constraintInner.setBackgroundResource(R.color.blueRecycler)
                1 -> constraintInner.setBackgroundResource(R.color.orangeRecycler)
                2 -> constraintInner.setBackgroundResource(R.color.greenRecycler)
                3 -> constraintInner.setBackgroundResource(R.color.redRecycler)
                else -> constraintInner.setBackgroundResource(R.color.yellowRecycler)

            }
            itemView.setOnClickListener{
                action.onItemClick(item, adapterPosition )

            }


        }
        /**
         * Get the Number of the pokemon
         */
        fun getNumber(url:String): String{
            var init = url.split('/')
            var number = init[init.size - 2]

            return number
        }

        /**
         * Gets the url of the image to be loaded
         */

        fun getImageUrl(url:String): String{

            var init = url.split('/')
            var number = init[init.size - 2]
            var baseURL = "https://pokeres.bastionbot.org/images/pokemon/"
            var completedUrl = "${baseURL}${number}.png"

            return completedUrl
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonRecyclerAdapter.PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_all_pokemon, parent, false)

        return PokemonViewHolder(itemView )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {



        holder.initialise(pokemons[position], listener)
    }

    override fun getItemCount() = pokemons.size


}