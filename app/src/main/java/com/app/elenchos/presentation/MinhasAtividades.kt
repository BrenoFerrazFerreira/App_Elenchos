package com.app.elenchos.presentation.atividades

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.elenchos.R
import com.app.elenchos.presentation.checkAndBlockApps
//import com.app.elenchos.presentation.blocker.checkAndBlockApps
//import com.app.elenchos.presentation.blockertest.checkAndBlockApps
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository.getActivities
import com.app.elenchos.presentation.repository.activityrepo.ActivityRepository.getTotalPercentage
import com.app.elenchos.presentation.repository.activityrepo.ActivityStatus
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesScreen(
    onNavigateToHome: () -> Unit,
    onProfileClick: () -> Unit,
    onActivitiesClick: () -> Unit,
    onNavigateToRanking: () -> Unit,
    onNavigateToNews: () -> Unit,
    onNavigateToReward: () -> Unit
) {
    val context = LocalContext.current
    val activities = remember { generateRandomActivities() }
    val scope = rememberCoroutineScope()

    // Atualiza as atividades no repositório
    ActivityRepository.updateActivities(activities)

    // Chama a função de bloqueio
    checkAndBlockApps(context, activities)

    // Calcula o total das porcentagens após a atualização das atividades
    val totalPercentage = ActivityRepository.getTotalPercentage()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

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
                            color = Color(0xFFb7adf6),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = true,
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
                            text = "Minhas Atividades",
                            color = Color.White
                        )
                    },
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
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total das porcentagens: $totalPercentage%",
                    color = Color(0xFFb7adf6),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(getActivities()) { activity ->
                        ActivityItemView(activity)
                    }
                }
            }
        }
    }
}


@Composable
fun ActivityItemView(activity: ActivityStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = activity.iconRes),
            contentDescription = activity.name,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = activity.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            LinearProgressIndicator(
                progress = activity.percentage / 100f,
                color = Color(0xFFb7adf6),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "${activity.percentage}%", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

fun generateRandomActivities(): List<ActivityStatus> {
    return listOf(
        ActivityStatus("Leitura", R.drawable.ic_leitura, (0..100).random()),
        ActivityStatus("Exercício Físico", R.drawable.ic_exercicio, (0..100).random()),
        ActivityStatus("Meditação", R.drawable.ic_meditacao, (0..100).random()),
        ActivityStatus("Lazer", R.drawable.ic_lazer, (0..100).random()),
        ActivityStatus("Outros", R.drawable.ic_outros, (0..100).random())
    )
}