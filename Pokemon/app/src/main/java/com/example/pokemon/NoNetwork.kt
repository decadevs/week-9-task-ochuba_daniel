package com.example.pokemon

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.pokemon.adapter.NetworkInterface
import com.example.pokemon.utils.ConnectivityCallback

class NoNetwork : AppCompatActivity(), NetworkInterface {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_network)

        val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerDefaultNetworkCallback(ConnectivityCallback(this))
    }

    override fun goBackToRecycler() {
        var intent = Intent(this, MainActivity::class.java )

        startActivity(intent )
    }
}