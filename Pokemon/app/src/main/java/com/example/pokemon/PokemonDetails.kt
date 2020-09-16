package com.example.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pokemon.databinding.ActivityPokemonDetailsBinding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.overview.PokemonOverviewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import kotlinx.android.synthetic.main.layout_moves.*
import kotlinx.android.synthetic.main.restrict_seen_abilites.*

class PokemonDetails : AppCompatActivity() {
    private lateinit var bottomSheetBehaviorDetails: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetMoves: BottomSheetBehavior<ConstraintLayout>
    lateinit var pokemonBinding: ActivityPokemonDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonBinding = DataBindingUtil.setContentView(this,R.layout.activity_pokemon_details)


       setBackgroundColor()

        initBottomLayoutRestrict()

        initBottomSheetLayout()

        var string = intent.getStringExtra("NUM")

        var URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"

        Picasso.get()
            .load("${URL}${string}.png")
            .into(image_back)

        /**
         * restrict What to see
         */

        checkBox_abilities.setOnClickListener {
            if (checkBox_abilities.isChecked){
                moves_cardview.visibility = View.INVISIBLE

            } else{
                moves_cardview.visibility = View.VISIBLE
            }
       }


        checkbox_experience.setOnClickListener{
            if (checkbox_experience.isChecked){
                lottie_experience.visibility = View.INVISIBLE
                base_experience_details_pokemon.visibility = View.INVISIBLE
            } else{
                lottie_experience.visibility = View.VISIBLE
                base_experience_details_pokemon.visibility = View.VISIBLE
            }
        }

        checkbox_height.setOnClickListener {
            if (checkbox_height.isChecked){
                lottie_animation_height.visibility = View.INVISIBLE
                pokemon_details_height.visibility = View.INVISIBLE
            } else{
                lottie_animation_height.visibility = View.VISIBLE
                pokemon_details_height.visibility = View.VISIBLE
            }
        }


        checkbox_stats.setOnClickListener {
            if (checkbox_stats.isChecked){
                pokemon_stats_details.visibility = View.INVISIBLE
            } else{
                pokemon_stats_details.visibility = View.VISIBLE
            }
        }






         //select abilities to see
        select_abilities_to_see.setOnClickListener {
           inflateLayoutRestrict()
        }


        //make bottomsheet go down
        go_down_restrict.setOnClickListener {
            inflateLayoutRestrict()
        }

//        inflate abilities bottomsheet
        moves_cardview.setOnClickListener {
            inflateLayoutMoves()
        }



        Log.d("GOTTENURL", "$string")

        val viewGotten = ViewModelProvider(this).get(PokemonOverviewModel::class.java)


        //get pokemon details
        viewGotten.getPokemonIndividualDetails(string!!)


        /**
         * Observe gotten pokemon details
         */
        viewGotten.getPokemonIndividualDataDetails().observe(this, Observer {

            pokemon_name_details.text = it.species.name
            pokemon_stats_details.text = it.stats[0].base_stat
            pokemonBinding.pokemonDetailsHeight.text = it.height
            pokemon_details_weight_text.text = it.weight
            pokemon_details_order_text.text = it.order
            base_experience_details_pokemon.text = it.base_experience
            moves_text_one.text = it.moves[0].move.name
            moves_text_two.text = it.moves[1].move.name
            moves_text_three.text = it.moves[2].move.name
            moves_test_four.text = it.moves[3].move.name

        })
    }

    /**
     * Check Whether the bottomsheet is inflated or not and act accordingly
     */

    private fun inflateLayoutRestrict() {
        if (bottomSheetBehaviorDetails.state != BottomSheetBehavior.STATE_HALF_EXPANDED) {
            bottomSheetBehaviorDetails.state = BottomSheetBehavior.STATE_HALF_EXPANDED


        } else {
            bottomSheetBehaviorDetails.state = BottomSheetBehavior.STATE_COLLAPSED


        }
    }

    private fun inflateLayoutMoves() {
        if (bottomSheetMoves.state != BottomSheetBehavior.STATE_HALF_EXPANDED) {
            bottomSheetMoves.state = BottomSheetBehavior.STATE_HALF_EXPANDED


        } else {
            bottomSheetMoves.state = BottomSheetBehavior.STATE_COLLAPSED


        }
    }

    /**
     * Initialise the Bottom Sheet
     */

    fun initBottomLayoutRestrict(){
         bottomSheetBehaviorDetails = BottomSheetBehavior.from<ConstraintLayout>(bottomSheetRestrictAbilities)
         bottomSheetBehaviorDetails.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
             override fun onSlide(bottomSheet: View, slideOffset: Float) {

             }

             override fun onStateChanged(bottomSheet: View, newState: Int) {
                 when (newState) {
                     BottomSheetBehavior.STATE_COLLAPSED -> {


                     }
                     BottomSheetBehavior.STATE_HIDDEN -> {

                     }
                     BottomSheetBehavior.STATE_EXPANDED -> {


                     }
                     BottomSheetBehavior.STATE_DRAGGING -> {

                     }
                     BottomSheetBehavior.STATE_SETTLING -> {


                     }
                 }


             }
         })

     }

    /**
     * Initiate Moves Layout
     */

    fun initBottomSheetLayout(){
        bottomSheetMoves = BottomSheetBehavior.from<ConstraintLayout>(bottomSheetMovesLayout)
        bottomSheetMoves.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {


                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {


                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {


                    }
                }


            }
        })

    }
    /**
     * Set the Background Color
     */
    fun setBackgroundColor(){
        var colorInt = (0..5).random()

        when(colorInt % 5){

            0 -> pokemon_details_layout_gotten.setBackgroundResource(R.color.blueRecycler)
            1 -> pokemon_details_layout_gotten.setBackgroundResource(R.color.orangeRecycler)
            2 -> pokemon_details_layout_gotten.setBackgroundResource(R.color.greenRecycler)
            3 -> pokemon_details_layout_gotten.setBackgroundResource(R.color.redRecycler)
            else -> pokemon_details_layout_gotten.setBackgroundResource(R.color.yellowRecycler)

        }

    }
}