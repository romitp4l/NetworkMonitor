package com.example.networkmonitor



import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NetworkDetailsActivity : AppCompatActivity() {

    private lateinit var networkTypeTextView: TextView
    private lateinit var ipAddressTextView: TextView
    private lateinit var ssidTextView: TextView
    private lateinit var bssidTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_details)

        networkTypeTextView = findViewById(R.id.networkType)
        ipAddressTextView = findViewById(R.id.ipAddress)
        ssidTextView = findViewById(R.id.ssid)
        bssidTextView = findViewById(R.id.bssid)

        displayNetworkDetails()
    }

    private fun displayNetworkDetails() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        val networkCapabilities = cm.getNetworkCapabilities(activeNetwork)

        if (networkCapabilities != null) {
            var networkType = "Unknown"
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                networkType = "Wi-Fi"
                val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                ssidTextView.text = "SSID: ${wifiInfo.ssid.removeSurrounding("\"")}"
                bssidTextView.text = "BSSID: ${wifiInfo.bssid}"
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                networkType = "Mobile Data"
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                networkType = "Ethernet"
            }
            networkTypeTextView.text = "Network Type: $networkType"

            val linkProperties = cm.getLinkProperties(activeNetwork)
            val addresses = linkProperties?.linkAddresses
            if (addresses != null && addresses.isNotEmpty()) {
                val ipV4Address = addresses.firstOrNull { it.address.address.size == 4 }
                ipAddressTextView.text = "IP Address: ${ipV4Address?.address?.hostAddress}"
            } else {
                ipAddressTextView.text = "IP Address: Unavailable"
            }
        } else {
            networkTypeTextView.text = "Network Type: No Connection"
            ipAddressTextView.text = "IP Address: No Connection"
            ssidTextView.text = "SSID: No Connection"
            bssidTextView.text = "BSSID: No Connection"
        }
    }
}