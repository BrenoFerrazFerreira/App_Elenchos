package com.app.elenchos.presentation.blocker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.app.elenchos.presentation.atividades.ActivityStatus

fun checkAndBlockApps(context: Context, activities: List<ActivityStatus>) {
    val totalProgress = activities.sumOf { it.percentage }

    if (totalProgress < 100) {
        blockSocialApps(context)
    } else {
        blockSocialApps(context)
        Toast.makeText(context, "Parabéns, você atingiu sua meta diária!", Toast.LENGTH_SHORT).show()
    }
}

fun blockSocialApps(context: Context) {
    val packageNames = listOf(
        "com.whatsapp",
        "com.instagram.android",
        "com.facebook.katana",
        "com.zhiliaoapp.musically", // TikTok
        "com.google.android.apps.maps"
    )

    packageNames.forEach { packageName ->
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            // Abre a tela de detalhes do aplicativo para que o usuário o desinstale ou force sua parada
            val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", packageName, null)
            }
            context.startActivity(appSettingsIntent)
        }
    }

    Toast.makeText(context, "Você não pode acessar essas redes sociais até atingir sua meta diária.", Toast.LENGTH_LONG).show()
}