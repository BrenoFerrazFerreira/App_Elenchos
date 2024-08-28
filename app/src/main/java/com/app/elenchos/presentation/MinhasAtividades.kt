package com.app.elenchos.presentation.atividades

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.elenchos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitiesScreen(onNavigateBack: () -> Unit) {
    val activities = listOf(
        ActivityItem("Leitura", R.drawable.ic_leitura, (0..100).random()),
        ActivityItem("Exercício Físico", R.drawable.ic_exercicio, (0..100).random()),
        ActivityItem("Meditação", R.drawable.ic_meditacao, (0..100).random()),
        ActivityItem("Lazer", R.drawable.ic_lazer, (0..100).random()),
        ActivityItem("Outros", R.drawable.ic_outros, (0..100).random())
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SmallTopAppBar(
            title = { Text("Minhas Atividades") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Voltar"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(activities) { activity ->
                ActivityItemView(activity)
            }
        }
    }
}

@Composable
fun ActivityItemView(activity: ActivityItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth().height(8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "${activity.percentage}%", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

data class ActivityItem(val name: String, val iconRes: Int, val percentage: Int)