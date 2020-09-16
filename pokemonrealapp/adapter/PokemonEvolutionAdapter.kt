package com.mylearning.pokemonrealapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.mylearning.pokemonrealapp.R
import com.mylearning.pokemonrealapp.common.Common
import com.mylearning.pokemonrealapp.model.Evolution
import com.mylearning.pokemonrealapp.model.SubjectInfo
import com.robertlevonyan.views.chip.Chip
import kotlinx.android.synthetic.main.text_item_layout.view.*

/**
 * Pokemon Evolution recycler view Adapter
 */
class PokemonEvolutionAdapter (var evolutionList : List<String>?) : RecyclerView.Adapter<PokemonEvolutionAdapter.MyViewHolder> () {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.text_item_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val subject = evolutionList?.get(position)
        holder.title.text = subject

    }

    override fun getItemCount(): Int {
        return evolutionList!!.size
    }


    init {
        if (evolutionList == null)
            evolutionList = ArrayList()
    }
    inner class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.item_title_tv)

    }


}