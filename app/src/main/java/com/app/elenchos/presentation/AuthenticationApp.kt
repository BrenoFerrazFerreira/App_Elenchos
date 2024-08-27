package com.app.elenchos.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.app.elenchos.presentation.login.LoginScreen
import com.app.elenchos.presentation.register.RegisterScreen
import com.app.elenchos.presentation.home.HomeScreen

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
            onProfileClick = { /* Navegar para o perfil */ },
            onActivitiesClick = { /* Navegar para as atividades */ }
        )
    }
}
