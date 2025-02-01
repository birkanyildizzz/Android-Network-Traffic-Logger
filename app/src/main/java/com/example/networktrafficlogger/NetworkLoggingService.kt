package com.example.networktrafficlogger

import android.app.Service
import android.content.Intent
import android.net.TrafficStats
import android.os.Environment
import android.os.IBinder
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NetworkLoggingService : Service() {

    private val TAG = "NetworkLoggerService"
    private var isRunning = true

    private var isTrafficIncreased = false // Normal mi yoksa yük testi mi?

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service started")
        // Intent ile "yük testi aktif mi?" kontrolü yap
        isTrafficIncreased = intent?.getBooleanExtra("TRAFFIC_INCREASED", false) ?: false
        Thread { monitorNetworkTraffic() }.start()
        return START_STICKY
    }

    private fun monitorNetworkTraffic() {
        // Hangi CSV dosyasına kayıt yapacağını belirle
        val csvFile =  if (isTrafficIncreased) {
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "increased_traffic.csv")
        } else {
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "normal_traffic.csv")
        }



        val writer = PrintWriter(FileWriter(csvFile, true))
        writer.println("Timestamp,Received Bytes,Transmitted Bytes")

        while (isRunning) {
            try {
                val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val receivedBytes = TrafficStats.getTotalRxBytes()
                val transmittedBytes = TrafficStats.getTotalTxBytes()

                writer.println("$timestamp,$receivedBytes,$transmittedBytes")
                writer.flush()
                Log.d(TAG, "Logged: $timestamp, Received: $receivedBytes, Sent: $transmittedBytes")

                Thread.sleep(5000) // 5 saniyede bir veri kaydet
            } catch (e: Exception) {
                Log.e(TAG, "Error logging network traffic", e)
            }
        }
        writer.close()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        isRunning = false
        Log.d(TAG, "Service stopped")
        super.onDestroy()
    }
}
