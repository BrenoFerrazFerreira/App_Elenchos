package com.app.elenchos.presentation

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
//import com.app.elenchos.presentation.atividades.ActivityStatus
import com.app.elenchos.presentation.repository.activityrepo.ActivityStatus
import android.provider.Settings
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository

//import com.app.elenchos.presentation.repository.activityrepo.ActivityStatus


fun getForegroundApp(context: Context): String {
    var currentApp = "NULL"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val time = System.currentTimeMillis()
        val appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
        if (appList != null && appList.isNotEmpty()) {
            val recentApp = appList.maxByOrNull { it.lastTimeUsed }
            if (recentApp != null) {
                currentApp = recentApp.packageName
            }
        }
    } else {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.runningAppProcesses
        if (tasks.isNotEmpty()) {
            currentApp = tasks[0].processName
        }
    }
    return currentApp
}

fun showHomeScreen(context: Context) {
    val startMain = Intent(Intent.ACTION_MAIN)
    startMain.addCategory(Intent.CATEGORY_HOME)
    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(startMain)
}

//fun checkAndBlockApps(context: Context, activities: List<ActivityStatus>) {
//    if (!hasUsageStatsPermission(context)) {
//        requestUsageAccessPermission(context)
//        return
//    }
//
//    val totalPercentage = ActivityRepository.getTotalPercentage()
//    Toast.makeText(context, "Total Percentage: $totalPercentage", Toast.LENGTH_SHORT).show()
//
//    if (totalPercentage < 700) {
//        Toast.makeText(context, "Total Percentage is less than 700", Toast.LENGTH_SHORT).show()
//        val blockedApps = listOf(
//            "com.whatsapp",
//            "com.instagram.android",
//            "com.facebook.katana",
//            "com.zhiliaoapp.musically", // TikTok
//            "com.google.android.apps.maps"
//        )
//
//        val currentApp = getForegroundApp(context)
//        Toast.makeText(context, "Current App: $currentApp", Toast.LENGTH_SHORT).show()
//
//        if (blockedApps.contains("com.google.android.apps.maps")) {
//            showHomeScreen(context)
//            Toast.makeText(context, "Você não pode acessar estas redes sociais até atingir sua meta diária.", Toast.LENGTH_LONG).show()
//        }
//    } else {
//        Toast.makeText(context, "Parabéns, você atingiu sua meta diária! Redes sociais liberadas.", Toast.LENGTH_SHORT).show()
//    }
//}
fun checkAndBlockApps(context: Context, activities: List<ActivityStatus>) {
    if (!hasUsageStatsPermission(context)) {
        requestUsageAccessPermission(context)
        return
    }

    val totalPercentage = ActivityRepository.getTotalPercentage()

    if (totalPercentage < 700) {
        val blockedApps = listOf(
            "com.whatsapp",
            "com.instagram.android",
            "com.facebook.katana",
            "com.zhiliaoapp.musically", // TikTok
            "com.google.android.apps.maps"
        )

        val currentApp = getForegroundApp(context)
        if (blockedApps.contains(currentApp) && currentApp != context.packageName) {
            val lockIntent = Intent(context, LockScreen::class.java)
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(lockIntent)
            Toast.makeText(context, "Você não pode acessar estas redes sociais até atingir sua meta diária.", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, "Parabéns, você atingiu sua meta diária! Redes sociais liberadas.", Toast.LENGTH_SHORT).show()
    }
}


fun hasUsageStatsPermission(context: Context): Boolean {
    val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val time = System.currentTimeMillis()
    val appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time)
    return appList.isNotEmpty()
}

fun requestUsageAccessPermission(context: Context) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}
