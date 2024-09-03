package com.app.elenchos.presentation.util

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.annotation.RequiresApi


class Utils(private val context: Context) {
    private var usageStatsManager: UsageStatsManager? = null

    @get:RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    val launcherTopApp: String
        get() {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            usageStatsManager =
                context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val endTime = System.currentTimeMillis()
            val beginTime = endTime - 10000
            val usageEvents = usageStatsManager!!.queryEvents(beginTime, endTime)
            val event = UsageEvents.Event()
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event)
                if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    return event.packageName
                }
            }
            return ""
        }

    fun addLockedApp(packageName: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lockedApps = prefs.getStringSet(LOCKED_APPS, HashSet())
        lockedApps!!.add(packageName)
        val editor = prefs.edit()
        editor.putStringSet(LOCKED_APPS, lockedApps)
        editor.apply()
    }

    fun removeLockedApp(packageName: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lockedApps = prefs.getStringSet(LOCKED_APPS, HashSet())
        lockedApps!!.remove(packageName)
        val editor = prefs.edit()
        editor.putStringSet(LOCKED_APPS, lockedApps)
        editor.apply()
    }

    fun isAppLocked(packageName: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lockedApps = prefs.getStringSet(LOCKED_APPS, HashSet())
        return lockedApps!!.contains(packageName)
    }

    companion object {
        private const val PREFS_NAME = "AppBlockrPrefs"
        private const val LOCKED_APPS = "LOCKED_APPS"
        private const val EXTRA_LAST_APP = "EXTRA_LAST_APP"
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        fun checkPermission(ctx: Context): Boolean {
            var appOpsManager: AppOpsManager? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                appOpsManager = ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            }
            val mode = appOpsManager!!.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                ctx.packageName
            )
            return mode == AppOpsManager.MODE_ALLOWED
        }
    }
}
