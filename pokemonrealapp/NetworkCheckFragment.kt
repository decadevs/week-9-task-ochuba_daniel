package com.mylearning.pokemonrealapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Visibility
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_network_check.*

/**
 * Network Fragment implement ServiceInterface
 */
class NetworkCheckFragment : Fragment(), ServiceInterface {
private lateinit var pokemonListFragment: PokemonListFragment
    lateinit var itemView:View

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         itemView = inflater.inflate(R.layout.fragment_network_check, container, false)
        // Connectivity Manager with registerDefaultNetworkCallback method
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         cm.registerDefaultNetworkCallback(ConnectivityCallback(this))

     return itemView

    }

    /**
     * Function Check network
     */
    fun checkConnected () {
            Log.d("CHECKNEXT", "Should trigger")
    }

    /**
     * Function to refresh the fragment and navigate back to pokemonList Fragment id there is network.
     */
    override fun refreshList() {
        Log.d("SEE", "This is a log")
        //progressBar_cyclic.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            //Do something after 100ms
            view?.findNavController()?.navigate(R.id.action_networkCheckFragment_to_pokemonListFragment)
           // progressBar_cyclic.visibility = View.INVISIBLE
        }, 15000)

    }

    /**
     * Display if no network
     */
    override fun noNetwork() {
        TODO("Not yet implemented")
    }

}