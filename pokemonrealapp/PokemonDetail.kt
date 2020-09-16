package com.mylearning.pokemonrealapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mylearning.pokemonrealapp.adapter.PokemonEvolutionAdapter
import com.mylearning.pokemonrealapp.model.Pokemon
import com.mylearning.pokemonrealapp.utils.hide
import com.mylearning.pokemonrealapp.utils.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*


class PokemonDetail : Fragment() {

    val title = this.javaClass.name

    lateinit var pokemon: Pokemon


    companion object {
        internal var instance : PokemonDetail? = null

        fun getInstance() : PokemonDetail {
            if (instance == null)
                instance = PokemonDetail()
            return instance!!
        }
    }

    /**
     *  Argument to poss details to Pokemon Details
     */
    val args:PokemonDetailArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false)

        return itemView
    }


    /**
     * On Resume function
     */
    override fun onResume() {
        super.onResume()

        name.text= args.pokemonDetail?.name
        height.text = args.pokemonDetail?.height
        weight.text = args.pokemonDetail?.weight
        val imageUrl = args.pokemonDetail?.img

        Picasso.get().load(imageUrl).into(pokemon_image)
        Log.i(title, "image url $imageUrl")

        val weaknessInfo = args.pokemonDetail?.weaknesses

        Log.i(title, "${weaknessInfo?.toList()}")

        val weaknessAdapter = PokemonEvolutionAdapter(weaknessInfo)
        recycler_weakness.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler_weakness.adapter = weaknessAdapter
        weakness.setOnClickListener {

            if(recycler_weakness.show()){
                recycler_weakness.hide()
            }
            else{
                recycler_weakness.show()
            }
        }
//        Log.i(title, "Name $name")

        // for types
        val typeInfo = args.pokemonDetail?.type
        Log.i(title, "${typeInfo?.toList()}")

        val typeAdapter = PokemonEvolutionAdapter(typeInfo)

        recycler_type.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recycler_type.adapter = typeAdapter

        type.setOnClickListener {
            recycler_type.show()
        }

        // prev Evolution
        val prevEvolutionInfo = args.pokemonDetail?.type
        Log.i(title, "${prevEvolutionInfo?.toList()}")

        val prevEvolutionAdapter = PokemonEvolutionAdapter(prevEvolutionInfo)

        recycler_prev_evolution.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        recycler_prev_evolution.adapter = prevEvolutionAdapter




    }

}