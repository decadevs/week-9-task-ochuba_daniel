package com.mylearning.pokemonrealapp

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.util.Log

/**
 * ConnectivityCallBack Class to check for network and network state
 */
class ConnectivityCallback(var listener:ServiceInterface) : ConnectivityManager.NetworkCallback() {


    override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
        val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
        notifyConnectedState(connected)
    }

    // Get notification base on network state
    private fun notifyConnectedState(connected: Boolean) {

        Log.d("OYEDELEMYBOY", "THIS IS $connected")

        if (connected){
         listener.refreshList()

           // frag.fetchData(itemView)
        }
    }

    override fun onLost(network: Network) {
        notifyConnectedState(false)
    }
}