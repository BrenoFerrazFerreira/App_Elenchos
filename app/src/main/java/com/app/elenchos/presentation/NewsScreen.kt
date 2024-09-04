package com.app.elenchos.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.ui.draw.clip
import com.app.elenchos.R
import kotlinx.coroutines.launch

data class NewsItem(
    val title: String,
    val category: String,
    val description: String,
    val imageResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    onNavigateToHome: () -> Unit,
    onProfileClick: () -> Unit,
    onActivitiesClick: () -> Unit,
    onNavigateToNews: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToReward: () -> Unit
) {
    val newsItems = generateNewsItems()
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
                            color = Color(0xFFb7adf6),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = true,
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
                            color = Color(0xFFb7adf6)
                        )
                    },
                    selected = false,
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
                            text = "Notícias",
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(newsItems) { newsItem ->
                        NewsCard(newsItem)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = newsItem.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = newsItem.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFb7adf6)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = newsItem.category,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = newsItem.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

fun generateNewsItems(): List<NewsItem> {
    return listOf(
        NewsItem("Leitura Recomendada", "Leitura", "Descubra os melhores livros para ler nesta semana.", R.drawable.book_icon),
        NewsItem("Últimas em Esportes", "Esportes", "As notícias mais quentes do mundo dos esportes.", R.drawable.sport_icon),
        NewsItem("Meditação para Iniciantes", "Meditação", "Dicas e truques para começar a meditar.", R.drawable.meditation_icon),
        NewsItem("Eventos de Lazer", "Lazer", "Aproveite as melhores atividades de lazer deste mês.", R.drawable.leisure_icon),
        NewsItem("Notícias Recentes", "Geral", "Atualizações e eventos recentes que você deve saber.", R.drawable.news_icon),
        NewsItem("Curiosidades Sobre a Ciência", "Ciência", "Fatos e descobertas científicas interessantes.", R.drawable.science_icon)
    )
}
