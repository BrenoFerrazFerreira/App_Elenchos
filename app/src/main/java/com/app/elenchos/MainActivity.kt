package com.app.elenchos

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.text.font.FontVariation
import com.app.elenchos.presentation.AppMonitorService
import com.app.elenchos.ui.theme.ElenchosTheme
import com.app.elenchos.presentation.AuthenticationApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita a funcionalidade de "edge-to-edge" se disponível
        enableEdgeToEdge()

        // Inicia o serviço de monitoramento em segundo plano
        val intent = Intent(this, AppMonitorService::class.java)
        startService(intent)

        // Verifica se a permissão de sobreposição de tela é necessária
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestOverlayPermission(this)
        }

        setContent {
            ElenchosTheme {
                AuthenticationApp()
            }
        }
    }

    private fun requestOverlayPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.packageName)
                )
                activity.startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 1234 // Defina um código de solicitação apropriado
    }
}
