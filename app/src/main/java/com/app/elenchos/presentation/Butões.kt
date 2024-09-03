package com.app.elenchos.presentation

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
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
import com.app.elenchos.presentation.repository.SharedPrefUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onActivitiesClick: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val appPackageName = "com.google.android.apps.maps" // Exemplo com Google Maps
    val sharedPrefUtil = SharedPrefUtil.getInstance(context)

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
                    label = { Text(text = "Home", color = Color(0xFFb7adf6)) },
                    selected = false,
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
                    label = { Text(text = "Profile", color = Color(0xFFb7adf6)) },
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
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                tint = Color.White
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
                    image = painterResource(id = R.drawable.logo),
                    imageSize = 200.dp,
                    shadowColor = Color(0xFFb7adf6)
                )
                Text(
                    text = "Teste de bloqueio hihihi piroquinha",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFb7adf6),
                    modifier = Modifier.padding(bottom = 24.dp, top = 24.dp)
                )
                CustomButton(text = "Meu Perfil", onClick = onProfileClick)
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(text = "Minhas Atividades", onClick = onActivitiesClick)

                // Botão para bloquear Google Maps
                Button(
                    onClick = {
                        sharedPrefUtil.setAppBlocked(appPackageName, true)
                        Toast.makeText(context, "Google Maps bloqueado", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Bloquear Google Maps")
                }

                // Botão para desbloquear Google Maps
                Button(
                    onClick = {
                        sharedPrefUtil.setAppBlocked(appPackageName, false)
                        Toast.makeText(context, "Google Maps desbloqueado", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Desbloquear Google Maps")
                }
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
