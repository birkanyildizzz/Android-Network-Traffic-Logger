package com.example.networktrafficlogger

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NetworkTrafficLogger)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startNormalButton = findViewById<Button>(R.id.startNormalTrafficButton)
        val startIncreasedButton = findViewById<Button>(R.id.startIncreasedTrafficButton)
        val stopButton = findViewById<Button>(R.id.stopServiceButton)
        val shareNormalButton = findViewById<Button>(R.id.shareNormalCsvButton)
        val shareIncreasedButton = findViewById<Button>(R.id.shareIncreasedCsvButton)

        // İzinleri kontrol et ve iste
        requestPermissions()

        // Normal trafik kaydını başlat
        startNormalButton.setOnClickListener {
            startLoggingService(false)
        }

        // Yük testi trafiği kaydını başlat
        startIncreasedButton.setOnClickListener {
            startLoggingService(true)
        }

        // Servisi durdur
        stopButton.setOnClickListener {
            stopLoggingService()
        }

        // Normal trafik CSV dosyasını paylaş
        shareNormalButton.setOnClickListener {
            shareCSVFile("normal_traffic.csv")
        }

        // Yük testi sonrası trafik CSV dosyasını paylaş
        shareIncreasedButton.setOnClickListener {
            shareCSVFile("increased_traffic.csv")
        }
    }

    // İzinleri kontrol eden ve istekte bulunan fonksiyon
    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        }
    }

    // Servisi başlatan fonksiyon
    private fun startLoggingService(isTrafficIncreased: Boolean) {
        val serviceIntent = Intent(this, NetworkLoggingService::class.java)
        serviceIntent.putExtra("TRAFFIC_INCREASED", isTrafficIncreased) // Yük testi mi, normal mi?
        startService(serviceIntent)
    }

    // Servisi durduran fonksiyon
    private fun stopLoggingService() {
        val serviceIntent = Intent(this, NetworkLoggingService::class.java)
        stopService(serviceIntent)
    }

    // Belirtilen CSV dosyasını paylaşan fonksiyon
    private fun shareCSVFile(fileName: String) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

        if (file.exists()) {
            Log.d("CSV Paylaşım", "Dosya bulundu: ${file.absolutePath}")
            val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            try {
                startActivity(Intent.createChooser(intent, "CSV Dosyasını Paylaş"))
            } catch (e: Exception) {
                Log.e("CSV Paylaşım", "Paylaşım başlatılamadı", e)
            }
        } else {
            Log.e("CSV Paylaşım", "CSV dosyası bulunamadı! Konum: ${file.absolutePath}")
        }
    }
}
