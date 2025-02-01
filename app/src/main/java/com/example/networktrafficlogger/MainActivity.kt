package com.example.networktrafficlogger
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_NetworkTrafficLogger)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.startServiceButton)
        val stopButton = findViewById<Button>(R.id.stopServiceButton)
        val shareButton = findViewById<Button>(R.id.shareCsvButton)



        startButton.setOnClickListener {
            requestPermissions()
        }

        stopButton.setOnClickListener {
            stopService()
        }
        shareButton.setOnClickListener {
            shareCSVFile()
        }
    }

    private fun shareCSVFile() {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "network_traffic.csv")

        if (file.exists()) {
            Log.d("CSV Paylaşım", "Dosya bulundu: ${file.absolutePath}")
            val uri: Uri = FileProvider.getUriForFile(this, "$packageName.provider", file)

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

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        } else {
            startService()
        }
    }

    private fun startService() {
        val serviceIntent = Intent(this, NetworkLoggingService::class.java)
        startService(serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, NetworkLoggingService::class.java)
        stopService(serviceIntent)
    }

}
