package com.app.elenchos.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.app.elenchos.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.coroutines.launch
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost

val firebaseAuth = FirebaseAuth.getInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    onProfileClick: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onActivitiesClick: () -> Unit
) {
    val profilePicture: Painter = painterResource(id = R.drawable.iconefoca)

    val context = LocalContext.current

    var userName by remember { mutableStateOf("Carregando...") }
    var userEmail by remember { mutableStateOf("Carregando...") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(firebaseAuth.currentUser) {
        val user = firebaseAuth.currentUser
        userName = user?.displayName ?: "Nome não disponível"
        userEmail = user?.email ?: "Email não disponível"
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
                    title = { Text(text = "Perfil", fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
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
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .border(2.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = profilePicture,
                        contentDescription = "Foto de Perfil",
                        Modifier.size(100.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = userName, fontSize = 24.sp, color = Color.Black)
                Text(text = userEmail, fontSize = 16.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Ação para Editar Perfil */ },
                    modifier = Modifier
                        .width(280.dp)
                        .padding(top = 24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFb7adf6))
                ) {
                    Text("Editar perfil", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        firebaseAuth.signOut()
                        Toast.makeText(context, "Você saiu da conta", Toast.LENGTH_SHORT).show()
                        onNavigateToLogin()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFb7adf6)),
                    modifier = Modifier.width(280.dp)
                ) {
                    Text("Sair da Conta", color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val user = firebaseAuth.currentUser
                        user?.delete()?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Conta excluída com sucesso",
                                    Toast.LENGTH_LONG
                                ).show()
                                onNavigateToLogin()
                            } else {
                                if (task.exception is FirebaseAuthRecentLoginRequiredException) {
                                    Toast.makeText(
                                        context,
                                        "Por favor, faça login novamente e tente excluir a conta",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    onNavigateToLogin()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Erro ao excluir a conta: ${task.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe57373)),
                    modifier = Modifier.width(280.dp)
                ) {
                    Text("Excluir Conta", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}