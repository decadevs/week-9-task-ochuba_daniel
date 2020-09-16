package com.example.pokemon.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.util.Log
import com.example.pokemon.adapter.NetworkInterface

class ConnectivityCallback(var click:NetworkInterface) : ConnectivityManager.NetworkCallback() {

    override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
        val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
        notifyConnectedState(connected)
    }

    /**
     * Notifies when connection changes
     */
    private fun notifyConnectedState(connected: Boolean) {
        Log.d("OYEDELEMYBOY", "THIS IS $connected")
        if (connected){
           click.goBackToRecycler()
        }
    }
    override fun onLost(network: Network) {
        notifyConnectedState(false)
    }
}