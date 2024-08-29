package com.app.elenchos.presentation.repository.activityrepo

import com.app.elenchos.R

data class ActivityStatus(val name: String, val iconRes: Int, val percentage: Int)

object ActivityRepository {
    private var activities: List<ActivityStatus> = listOf(
        ActivityStatus("Leitura", R.drawable.ic_leitura, 0),
        ActivityStatus("Exercício Físico", R.drawable.ic_exercicio, 0),
        ActivityStatus("Meditação", R.drawable.ic_meditacao, 0),
        ActivityStatus("Lazer", R.drawable.ic_lazer, 0),
        ActivityStatus("Outros", R.drawable.ic_outros, 0)
    )

    fun updateActivities(newActivities: List<ActivityStatus>) {
        activities = newActivities
    }

    fun getActivities(): List<ActivityStatus> {
        return activities
    }
}