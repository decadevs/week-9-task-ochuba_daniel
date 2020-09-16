package com.mylearning.pokemonrealapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mylearning.pokemonrealapp.Interface.IItemClickedListener
import com.mylearning.pokemonrealapp.R
import com.mylearning.pokemonrealapp.common.Common
import com.robertlevonyan.views.chip.Chip
import kotlinx.android.synthetic.main.chip_items.view.*

/**
 * Pokemon type Adapter for details decoration
 */
class PokemonTypeAdapter (internal var typeList : List<String>, listener: Listener) : RecyclerView.Adapter<PokemonTypeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chip_items, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.chip.chipText = typeList[position]
        holder.chip.changeBackgroundColor(Common.getColorByType(typeList[position]))
    }

    override fun getItemCount(): Int {
        return typeList.size
    }


    inner class MyViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

        internal var chip : Chip = itemView.findViewById(R.id.chip) as Chip

        fun bind(itemView: View, listener: Listener){

        }
    }

}

