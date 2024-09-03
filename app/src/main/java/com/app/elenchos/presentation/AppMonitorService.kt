package com.app.elenchos.presentation

import android.content.Context
import android.content.Intent

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import com.app.elenchos.R
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository

import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat


class AppMonitorService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AppMonitorService", "Service started")
        startForegroundService()

        // Start background monitoring
        Thread {
            while (true) {
                val currentApp = getForegroundApp(this)
                Log.d("AppMonitorService", "Current foreground app: $currentApp")
                //if (shouldBlockApp(currentApp)) {
                if (shouldBlockApp(currentApp) || currentApp == "com.google.android.apps.nexuslauncher") {
                    Log.d("AppMonitorService", "Blocking app: $currentApp")
                    blockApp(currentApp)
                }
                Thread.sleep(1000) // Check every 1 second (adjust as needed)
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
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
            .build()

        startForeground(1, notification)
        Log.d("AppMonitorService", "Foreground service started")
    }

    private fun shouldBlockApp(currentApp: String): Boolean {
        val blockedApps = listOf(
            "com.whatsapp",
            "com.instagram.android",
            "com.facebook.katana",
            "com.zhiliaoapp.musically", // TikTok
            "com.google.android.apps.maps"
        )
        val totalPercentage = ActivityRepository.getTotalPercentage()
        Log.d("AppMonitorService", "Total activities percentage: $totalPercentage")
        return blockedApps.contains(currentApp) && totalPercentage < 700
    }

    private fun blockApp(currentApp: String) {
        val totalPercentage = ActivityRepository.getTotalPercentage()
        Log.d("AppMonitorService", "Blocking app: $currentApp with percentage: $totalPercentage")
        if (totalPercentage < 700) {
            val lockIntent = Intent(this, LockScreen::class.java)
            lockIntent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
            )
            startActivity(lockIntent)
        }
    }

    private fun getForegroundApp(context: Context): String {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 1000 * 60 // Look at usage in the last minute

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
