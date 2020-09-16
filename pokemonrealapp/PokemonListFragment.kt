package com.mylearning.pokemonrealapp

import android.app.AlertDialog
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mylearning.pokemonrealapp.adapter.Listener
import com.mylearning.pokemonrealapp.adapter.PokemonListAdapter
import com.mylearning.pokemonrealapp.common.ItemOffsetDecoration
import com.mylearning.pokemonrealapp.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Pokemon List Fragment : Home page
 */
class PokemonListFragment : Fragment(), ServiceInterface,  Listener  {
//    Variable initialized
    lateinit var myView:View
    private val TAG = "MyActivity"
    val title by lazy { this.javaClass.name }
    lateinit var adapter:PokemonListAdapter
    internal var compositeDisposable = CompositeDisposable()
//    internal var iPokemonList : IPokemonList

    internal lateinit var recyler_view : RecyclerView

//    init {
//        val retrofit = RetrofitClient.instance
//        iPokemonList = retrofit.create(IPokemonList::class.java)
//    }
    /**
     * On create function override
     */
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // ConnectivityManager with registerDefaultNetworkCallback call back
        val cm = context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(ConnectivityCallback(this))
        // Inflate the layout for this fragment
        val itemView =  inflater.inflate(R.layout.fragment_pokemon_list, container, false)
       // recyler_view = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView

        myView = itemView


        /**
         * Check if there is network and and fetch the pokemon list
         * If there is no network display an informative dialog box and
         * navigate to networkCheckFragment
         */
        if(isNetworkConnected()) {
            fetchData(itemView)
        } else {
            AlertDialog.Builder(requireContext()).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    view?.findNavController()?.navigate(R.id.action_pokemonListFragment_to_networkCheckFragment)
                }
                .setIcon(android.R.drawable.ic_dialog_alert).show()

        }

        return itemView
    }


    /**
     * Fetch data connecting to the recycler view and connecting to the retrofit client service
     */
     fun fetchData(itemView: View) {
        Log.i(title, "Fragment")
//        val res = RetrofitClient.service.getPokeMonList().subscribeOn(Schedulers.io())
//        val subscribe = res.observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                val list = it.pokemon?.map { it.name }
//                Log.i(title, "res ${list?.toList()}")
//            }

        //Connecting the pokemon list recycler view
        recyler_view = itemView.findViewById(R.id.pokemon_recyclerview) as RecyclerView

        recyler_view.setHasFixedSize(true)
        recyler_view.layoutManager = GridLayoutManager(activity, 2)

        val itemDecoration = ItemOffsetDecoration(requireActivity(), R.dimen.spacing)

        recyler_view.addItemDecoration(itemDecoration)

        /**
         * CompositeDisposable for threading
         */
        compositeDisposable.add(RetrofitClient.service.listPokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                /**
                 * Connecting the adapter with the pokemon list to the recycler view
                 */
                adapter = PokemonListAdapter(it.pokemon) {view, position ->

                    // Moving from pokemon list fragment to pokemon list detail fragment
                    val action = PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetail()
                    val p = it.pokemon?.get(position)
                    action.pokemonDetail = p
                    Log.i(title, "${p?.weaknesses}")
                    findNavController().navigate(action)
                    Toast.makeText(requireContext(), "${p?.name}", Toast.LENGTH_SHORT).show()
                }
                adapter.listener = this
                recyler_view.adapter = adapter

                val list = it.pokemon?.map { it.name }
                Log.i(title, "res ${list?.toList()}")


            }

        );

    }

    override fun onClickListener(itemView: View, position: Int) {

    }

    // Network Check function return a boolean

         fun isNetworkConnected(): Boolean {
        //1
        val connectivityManager = activity?.
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun refreshList() {

    }

    override fun noNetwork() {

    }


}