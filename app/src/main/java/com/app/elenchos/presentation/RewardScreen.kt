package com.app.elenchos.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Menu
import kotlinx.coroutines.launch

data class RewardItem(
    val title: String,
    val description: String,
    val pointsRequired: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    onNavigateToHome: () -> Unit,
    onProfileClick: () -> Unit,
    onActivitiesClick: () -> Unit,
    onNavigateToNews: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToReward: () -> Unit,
    currentPoints: Int // Pontos atuais do usuário
) {
    val rewardItems = generateRewardItems()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Tela inicial", color = Color(0xFFb7adf6)) },
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
                    label = {
                        Text(
                            text = "Minhas Atividades",
                            color = Color(0xFFb7adf6)
                        )
                    },
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
                            contentDescription = "ranking",
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
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = "Recompensas",
                            color = Color(0xFFb7adf6),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = true,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.CardGiftcard,
                            contentDescription = "recompensas",
                            tint = Color(0xFFb7adf6)
                        )
                    },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToReward()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Recompensas",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFb7adf6),
                        titleContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "MenuButton",
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
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Exibe os pontos atuais do usuário
                Text(
                    text = "Seus Pontos: $currentPoints",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFb7adf6),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(rewardItems) { rewardItem ->
                        RewardCard(rewardItem, currentPoints)
                    }
                }
            }
        }
    }
}

@Composable
fun RewardCard(rewardItem: RewardItem, currentPoints: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = rewardItem.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFb7adf6)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = rewardItem.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pontos Necessários: ${rewardItem.pointsRequired}",
                fontSize = 14.sp,
                color = if (currentPoints >= rewardItem.pointsRequired) Color(0xFF00C853) else Color(0xFFD32F2F) // Verde se o usuário tiver pontos suficientes, vermelho caso contrário
            )
        }
    }
}

fun generateRewardItems(): List<RewardItem> {
    return listOf(
        RewardItem("Spotify Premium", "Assinatura de 1 mês de Spotify Premium.", 1000),
        RewardItem("Gift Card de Jogo", "Gift card para compras em jogos.", 800),
        RewardItem("Cupom de Desconto", "Cupom de 20% de desconto em compras online.", 500),
        RewardItem("Netflix Premium", "Assinatura de 1 mês de Netflix Premium.", 1200),
        RewardItem("Amazon Gift Card", "Gift card para compras na Amazon.", 900)
    )
}
