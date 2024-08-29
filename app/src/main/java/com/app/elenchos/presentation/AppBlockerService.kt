package com.app.elenchos.presentation

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import com.app.elenchos.ui.theme.ElenchosTheme

class AppBlockerService : AccessibilityService() {

    override fun onServiceConnected() {
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
            packageNames = arrayOf(
                "com.whatsapp",
                "com.instagram.android",
                "com.facebook.katana",
                "com.zhiliaoapp.musically"
            )
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        }
        this.serviceInfo = info
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val packageName = event.packageName?.toString()
                if (packageName in listOf(
                        "com.whatsapp",
                        "com.instagram.android",
                        "com.facebook.katana",
                        "com.zhiliaoapp.musically",
                        "com.google.android.apps.maps"
                    )
                ) {
                    // Aqui você pode iniciar uma Activity do seu app para exibir a mensagem personalizada
                    val intent = Intent(this, BlockedAppActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    class BlockedAppActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                ElenchosTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Text(text = "Você não cumpriu suas metas diárias e não pode usar este aplicativo.")
                    }
                }
            }
        }
    }

    override fun onInterrupt() {
        // Método necessário, mas pode ficar vazio
    }
}
