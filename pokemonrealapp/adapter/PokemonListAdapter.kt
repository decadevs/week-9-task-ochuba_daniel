package com.mylearning.pokemonrealapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mylearning.pokemonrealapp.Interface.IItemClickedListener
import com.mylearning.pokemonrealapp.R
import com.mylearning.pokemonrealapp.common.Common
import com.mylearning.pokemonrealapp.model.Pokemon

/**
 * Pokemon List adapter
 */
class PokemonListAdapter (internal var pokemonList: List<Pokemon>?, var clickThis:(view:View, pos:Int)->Unit) : RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {
    var title = this.javaClass.name
    var listener: Listener? = null

    // View holder
    inner class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        internal var img_pokemon : ImageView
        internal var text_pokemon : TextView



        // instance of itemClickedListener interface
        internal var itemClickedListener : IItemClickedListener?= null

        fun setItemClickListener (itemClickedListener: IItemClickedListener) {
            this.itemClickedListener = itemClickedListener
        }


        // Init block to display the pokemon name and image
        init {
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            text_pokemon = itemView.findViewById(R.id.pokemon_name) as TextView

            itemView.setOnClickListener { view -> itemClickedListener!!.onClick(view, adapterPosition) }
        }
    }


    // onCreate View holder function
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return MyViewHolder(itemView)
    }


    // onBind View Holder function
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pokemon = pokemonList?.get(position)
        Glide.with(holder.itemView.context).load(pokemon?.img).into(holder.img_pokemon)
        holder.text_pokemon.text = pokemon?.name

        holder.itemView.setOnClickListener {
            clickThis(holder.itemView, position)
            Log.i(title, "Onclick here")
            listener?.onClickListener(holder.itemView, position)
        }

    }

    // get the count of pokemon list
    override fun getItemCount(): Int {
        return pokemonList?.size!!
    }

}

interface Listener{
    fun onClickListener(itemView: View, position: Int)
}
