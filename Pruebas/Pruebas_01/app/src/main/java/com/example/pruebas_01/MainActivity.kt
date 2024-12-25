package com.example.pruebas_01

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pruebas_01.databinding.ActivityMainBinding
import android.provider.Settings
import android.widget.Toast
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar y solicitar permisos para superposición
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, 1234)
        }
        binding.activateServiceButton.setOnClickListener {
            // Inicia el servicio para crear el botón flotante
            val serviceIntent = Intent(this, FloatingButtonService::class.java)
            startService(serviceIntent)

            // Intenta lanzar la app de YouTube con una URL específica
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))
            youtubeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                startActivity(youtubeIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "No se pudo abrir YouTube", Toast.LENGTH_SHORT).show()
            }
        }
        binding.checkRootButton.setOnClickListener {
            val isRooted = checkRootAccess()
            val message = if (isRooted) {
                "El dispositivo está ruteado."
            } else {
                "El dispositivo no está ruteado."
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Permiso de superposición denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkRootAccess(): Boolean {
        val paths = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/system/app/Superuser.apk",
            "/system/app/SuperSU.apk",
            "/system/app/Magisk.apk"
        )
        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }
        return false
    }
}