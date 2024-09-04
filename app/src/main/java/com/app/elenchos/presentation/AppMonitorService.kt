package com.app.elenchos.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.elenchos.R
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository

class AppMonitorService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AppMonitorService", "Service started")
        startForegroundService()

        // Inicia o monitoramento em segundo plano
        Thread {
            while (true) {
                val currentApp = getForegroundApp(this)
                Log.d("AppMonitorService", "Current foreground app: $currentApp")

                // Verifica se o app atual deve ser bloqueado
                if (shouldBlockApp(currentApp)) {
                    Log.d("AppMonitorService", "Blocking app: $currentApp")
                    blockApp(currentApp)
                }

                Thread.sleep(1000) // Verifica a cada 1 segundo (ajuste conforme necessário)
            }
        }.start()

        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "monitoring_service_channel"
        val channelName = "App Monitoring Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("App Monitor")
            .setContentText("Monitoring apps...")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Substitua pelo ícone do seu app
            .build()

        startForeground(1, notification)
        Log.d("AppMonitorService", "Foreground service started")
    }

    private fun shouldBlockApp(currentApp: String): Boolean {
        // Lista de pacotes de aplicativos que você deseja bloquear
        val blockedApps = listOf(
            "com.whatsapp",
            "com.instagram.android",
            "com.facebook.katana",
            "com.zhiliaoapp.musically", // TikTok
            "com.google.android.apps.maps"
        )

        val totalPercentage = ActivityRepository.getTotalPercentage()
        Log.d("AppMonitorService", "Total activities percentage: $totalPercentage")

        // Verifica se o app atual está na lista de bloqueados e se a porcentagem de atividades é menor que 60
        return blockedApps.contains(currentApp) && totalPercentage < 60
    }

    private fun blockApp(currentApp: String) {
        val lockIntent = Intent(this, LockScreen::class.java)
        lockIntent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        )
        startActivity(lockIntent)
    }

    private fun getForegroundApp(context: Context): String {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 1000 * 60 // Olha para o uso no último minuto

        val stats =
            usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime)
        if (stats.isNotEmpty()) {
            var recentApp: UsageStats? = null
            for (usageStats in stats) {
                if (recentApp == null || usageStats.lastTimeUsed > recentApp.lastTimeUsed) {
                    recentApp = usageStats
                }
            }
            return recentApp?.packageName ?: ""
        }
        return ""
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
