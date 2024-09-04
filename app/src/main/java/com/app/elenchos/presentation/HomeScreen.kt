package com.app.elenchos.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.elenchos.R
import com.app.elenchos.presentation.common.CustomButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

val firebaseAuth = FirebaseAuth.getInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onActivitiesClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToNews: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var userName by remember { mutableStateOf("Carregando...") }

    LaunchedEffect(com.app.elenchos.presentation.firebaseAuth.currentUser) {
        val user = com.app.elenchos.presentation.firebaseAuth.currentUser
        userName = user?.displayName ?: "Nome não disponível"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(color = Color(0xFFb7adf6))
                        .fillMaxWidth()
                        .height(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Menu",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Tela inicial", color = Color(0xFFb7adf6), fontWeight = FontWeight.Bold) },
                    selected = true,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "home",
                            tint = Color(0xFFb7adf6)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToHome()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Perfil", color = Color(0xFFb7adf6)) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "profile",
                            tint = Color(0xFFb7adf6)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onProfileClick()
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Minhas Atividades", color = Color(0xFFb7adf6)) },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "lista",
                            tint = Color(0xFFb7adf6)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onActivitiesClick()
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Veja o ranking",
                            color = Color(0xFFb7adf6)
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.EmojiEvents,
                            contentDescription = "lista",
                            tint = Color(0xFFb7adf6)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToRanking()
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Notícias",
                            color = Color(0xFFb7adf6)
                        )
                    },
                    selected = false,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "notícias",
                            tint = Color(0xFFb7adf6)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToNews()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Focus", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFb7adf6),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "MenuButton"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.Settings, // Use o ícone de configurações
                                contentDescription = "Settings",
                                tint = Color.White // Ajuste a cor do ícone conforme necessário
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RoundImageWithShadow(
                    image = painterResource(id = R.drawable.logo), // Substitua pelo ID da sua imagem
                    imageSize = 200.dp, // Define o tamanho da imagem
                    shadowColor = Color(0xFFb7adf6) // Define a cor da sombra
                )
                Text(
                    text = "Olá, ${userName}!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFb7adf6),
                    modifier = Modifier.padding(bottom = 24.dp, top = 24.dp)
                )
                CustomButton(text = "Meu Perfil", onClick = onProfileClick)
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(text = "Minhas Atividades", onClick = onActivitiesClick)
            }
        }
    }
}

@Composable
fun RoundImageWithShadow(image: Painter, imageSize: Dp, shadowColor: Color) {
    Box(
        modifier = Modifier
            .size(imageSize)
            .clip(CircleShape) // Garante que a sombra será circular
            .background(shadowColor.copy(alpha = 0.7f)) // Sombra como fundo com opacidade
            .shadow(10.dp, shape = CircleShape) // Sombra real aplicada
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape), // Garante que a imagem também é circular
            contentScale = ContentScale.Crop // Ajusta a escala da imagem para preencher o espaço
        )
    }
}