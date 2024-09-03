package com.app.elenchos.presentation.broadcast


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
//import com.app.elenchos.presentation.ScreenBlocker
import com.app.elenchos.presentation.util.Utils

class ReceiverApplock : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val utils = Utils(context)
        val currentApp = utils.launcherTopApp

        if (utils.isAppLocked(currentApp)) {
            /*val screenBlockerIntent = Intent(
                context,
                //ScreenBlocker::class.java
            )
            screenBlockerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(screenBlockerIntent)*/
        }
    }
}
