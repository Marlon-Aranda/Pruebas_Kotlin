package com.example.pruebas_01

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button

class FloatingButtonService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var floatingButton: View

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Inflar el botón flotante desde un layout personalizado
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingButton = inflater.inflate(R.layout.floating_button, null)

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.TOP or Gravity.CENTER
        layoutParams.y = 100 // Desplazamiento opcional

        // Agregar el botón flotante a la pantalla
        windowManager.addView(floatingButton, layoutParams)

        // Configurar el botón para volver a la app principal
        val floatingActionButton: Button = floatingButton.findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            // Regresa a la app principal
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)

            // Detiene el servicio y elimina el botón flotante
            stopSelf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Elimina el botón flotante cuando el servicio es destruido
        if (::floatingButton.isInitialized) {
            windowManager.removeView(floatingButton)
        }
    }
}