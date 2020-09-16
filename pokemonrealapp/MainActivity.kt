package com.mylearning.pokemonrealapp

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import com.mylearning.pokemonrealapp.common.Common
import com.mylearning.pokemonrealapp.retrofit.RetrofitClient
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


//    private fun isNetworkConnected(): Boolean {
//        //1
//        val connectivityManager =
//            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        //2
//        val activeNetwork = connectivityManager.activeNetwork
//        //3
//        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
//        //4
//        return networkCapabilities != null &&
//                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//    }


    // val title by lazy { this.javaClass.name }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setTitle("POKEMON LIST")
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.list_item_fragment)

  }

    // On Item Options Selected function
        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                android.R.id.home -> {
                    toolbar.title = "POKEMON LIST"

                    // Clear all fragment in the stack with name detail
                    supportFragmentManager.popBackStack(
                        "detail",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )

                    supportActionBar!!.setDisplayShowHomeEnabled(false)
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)

                }
            }
            return true
        }


    }

