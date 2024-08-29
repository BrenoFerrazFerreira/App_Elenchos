package com.app.elenchos.presentation

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.app.elenchos.presentation.blockertest.checkAndBlockApps
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository

class AppMonitorService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval: Long = 2000 // Verifica a cada 2 segundos

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler.post(checkForegroundApp)
        return START_STICKY
    }

    private val checkForegroundApp = object : Runnable {
        override fun run() {
            val activities = ActivityRepository.getActivities() // Recupera as atividades atualizadas
            checkAndBlockApps(applicationContext, activities)
            handler.postDelayed(this, checkInterval)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(checkForegroundApp)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
