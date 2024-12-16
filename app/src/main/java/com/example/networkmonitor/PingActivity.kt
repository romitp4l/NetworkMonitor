package com.example.networkmonitor

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class PingActivity : AppCompatActivity() {
    private lateinit var ipAddressEditText: EditText
    private lateinit var pingResultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ping)

        ipAddressEditText = findViewById(R.id.ipAddressEditText)
        pingResultTextView = findViewById(R.id.pingResultTextView)
        val pingButton: Button = findViewById(R.id.pingButton)

        pingButton.setOnClickListener {
            val ipAddress = ipAddressEditText.text.toString()
            PingTask().execute(ipAddress)
        }
    }

    private inner class PingTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String {
            val ipAddress = params[0]
            return try {
                val process = Runtime.getRuntime().exec("ping -c 3 $ipAddress")
                val reader = java.io.BufferedReader(java.io.InputStreamReader(process.inputStream))
                val output = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    output.append(line).append("\n")
                }
                output.toString()
            } catch (e: IOException) {
                "Error: ${e.message}"
            }
        }

        override fun onPostExecute(result: String) {
            pingResultTextView.text = result
        }
    }
}
