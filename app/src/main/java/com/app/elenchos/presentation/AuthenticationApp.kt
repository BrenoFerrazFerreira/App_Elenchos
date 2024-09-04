package com.app.elenchos.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.elenchos.presentation.login.LoginScreen
import com.app.elenchos.presentation.register.RegisterScreen
import com.app.elenchos.presentation.home.HomeScreen
import com.app.elenchos.presentation.atividades.ActivitiesScreen

@Composable
fun AuthenticationApp() {
    var currentScreen by remember { mutableStateOf("Login") }

    when (currentScreen) {
        "Login" -> LoginScreen(
            onNavigateToRegister = { currentScreen = "Register" },
            onNavigateToHome = { currentScreen = "Home" }
        )

        "Register" -> RegisterScreen(onNavigateToLogin = { currentScreen = "Login" })
        "Home" -> HomeScreen(
            onProfileClick = { currentScreen = "User" },
            onActivitiesClick = { currentScreen = "Activity" },
            onNavigateToHome = { currentScreen = "Home" },
            onNavigateToRanking = { currentScreen = "Ranking" },
            onNavigateToNews = { currentScreen = "News" },
            onNavigateToReward = { currentScreen = "Reward" }
        )

        "User" -> ProfileScreen(
            onNavigateToHome = { currentScreen = "Home" },
            onNavigateToLogin = { currentScreen = "Login" },
            onProfileClick = { currentScreen = "User" },
            onActivitiesClick = { currentScreen = "Activity" },
            onNavigateToRanking = { currentScreen = "Ranking" },
            onNavigateToNews = { currentScreen = "News" },
            onNavigateToReward = { currentScreen = "Reward" }
        )

        "Activity" -> ActivitiesScreen(
            onNavigateToHome = { currentScreen = "Home" },
            onProfileClick = { currentScreen = "User" },
            onActivitiesClick = { currentScreen = "Activity" } ,
            onNavigateToRanking = { currentScreen = "Ranking" },
            onNavigateToNews = { currentScreen = "News" },
            onNavigateToReward = { currentScreen = "Reward" }
        )

        "Ranking" -> RankingScreen(
            onNavigateToHome = { currentScreen = "Home" },
            onProfileClick = { currentScreen = "User" },
            onActivitiesClick = { currentScreen = "Activity" },
            onNavigateToRanking = { currentScreen = "Ranking" },
            onNavigateToNews = { currentScreen = "News" },
            onNavigateToReward = { currentScreen = "Reward" }
        )

        "News" -> NewsScreen(
            onNavigateToHome = { currentScreen = "Home" },
            onProfileClick = { currentScreen = "User" },
            onActivitiesClick = { currentScreen = "Activity" },
            onNavigateToRanking = { currentScreen = "Ranking" },
            onNavigateToNews = { currentScreen = "News" },
            onNavigateToReward = { currentScreen = "Reward" }
        )

        "Reward" -> RewardsScreen(
            onNavigateToHome = { currentScreen = "Home" },
            onProfileClick = { currentScreen = "User" },
            onActivitiesClick = { currentScreen = "Activity" },
            onNavigateToRanking = { currentScreen = "Ranking" },
            onNavigateToNews = { currentScreen = "News" },
            onNavigateToReward = { currentScreen = "Reward" },
            currentPoints = 50
        )
    }
}
