package com.app.elenchos

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.app.elenchos.presentation.AppMonitorService
import com.app.elenchos.ui.theme.ElenchosTheme
import com.app.elenchos.presentation.AuthenticationApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ElenchosTheme {
                val intent = Intent(this, AppMonitorService::class.java)
                startService(intent)
                AuthenticationApp()
            }
        }
    }
}
