package com.example.networkmonitor

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkButton: Button = findViewById(R.id.checkButton)
        val connectionStatus: TextView = findViewById(R.id.connectionStatus)
        val detailsButton: Button = findViewById(R.id.detailsButton)
        val pingButton: Button = findViewById(R.id.pingButton)

        checkButton.setOnClickListener {
            val cm = getSystemService(ConnectivityManager::class.java)
            val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)

            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    connectionStatus.text = "Mobile Data"
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    connectionStatus.text = "Wi-Fi"
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    connectionStatus.text = "Ethernet"
                }
            } else {
                connectionStatus.text = "No Connection"
            }
        }

        detailsButton.setOnClickListener{
            startActivity(Intent(this, NetworkDetailsActivity::class.java))
        }
        pingButton.setOnClickListener{
            startActivity(Intent(this, PingActivity::class.java))
        }

        val aboutButton: Button = findViewById(R.id.aboutButton)
        aboutButton.setOnClickListener{
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}
