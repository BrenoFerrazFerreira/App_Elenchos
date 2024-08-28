package com.app.elenchos.presentation.blockertest

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.util.SortedMap
import java.util.TreeMap
import com.app.elenchos.presentation.atividades.ActivityStatus
import android.provider.Settings


fun getForegroundApp(context: Context): String {
    var currentApp = "NULL"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        val appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
        if (appList != null && appList.isNotEmpty()) {
            val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
            for (usageStats in appList) {
                mySortedMap[usageStats.lastTimeUsed] = usageStats
            }
            if (mySortedMap.isNotEmpty()) {
                currentApp = mySortedMap[mySortedMap.lastKey()]!!.packageName
            }
        }
    } else {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.runningAppProcesses
        currentApp = tasks[0].processName
    }
    return currentApp
}

fun usageAccessSettingsPage(context: Context) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

fun showHomeScreen(context: Context) {
    val startMain = Intent(Intent.ACTION_MAIN)
    startMain.addCategory(Intent.CATEGORY_HOME)
    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(startMain)
}

fun checkAndBlockApps(context: Context, activities: List<ActivityStatus>) {
    // Verifica se a permissão de uso foi concedida
    if (!hasUsageStatsPermission(context)) {
        requestUsageAccessPermission(context)
        return
    }

    val allActivitiesCompleted = activities.all { it.percentage == 100 }

    if (!allActivitiesCompleted) {
        val blockedApps = listOf(
            "com.whatsapp",
            "com.instagram.android",
            "com.facebook.katana",
            "com.zhiliaoapp.musically" // TikTok
        )

        val currentApp = getForegroundApp(context)
        if (blockedApps.contains(currentApp)) {
            showHomeScreen(context)
            Toast.makeText(context, "Você não pode acessar essas redes sociais até atingir sua meta diária.", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, "Parabéns, você atingiu sua meta diária!", Toast.LENGTH_SHORT).show()
    }
}

fun hasUsageStatsPermission(context: Context): Boolean {
    val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        appOpsManager.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            context.packageName
        )
    } else {
        appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            context.packageName
        )
    }
    return mode == AppOpsManager.MODE_ALLOWED
}

fun requestUsageAccessPermission(context: Context) {
    Toast.makeText(context, "Por favor, conceda permissão para monitoramento de uso.", Toast.LENGTH_LONG).show()
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}