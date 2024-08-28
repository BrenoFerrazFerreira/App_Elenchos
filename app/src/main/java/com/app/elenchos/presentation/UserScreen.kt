package com.app.elenchos.presentation.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.elenchos.R
import com.app.elenchos.ui.theme.ElenchosTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException


val firebaseAuth = FirebaseAuth.getInstance()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val profilePicture: Painter = painterResource(id = R.drawable.user)

    val context = LocalContext.current

    var userName by remember { mutableStateOf("Carregando...") }
    var userEmail by remember { mutableStateOf("Carregando...") }

    LaunchedEffect(firebaseAuth.currentUser) {
        val user = firebaseAuth.currentUser
        userName = user?.displayName ?: "Nome não disponível"
        userEmail = user?.email ?: "Email não disponível"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        SmallTopAppBar(
            title = { Text("Perfil") },
            navigationIcon = {
                IconButton(onClick = onNavigateToHome) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Voltar"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .border(2.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
        ) {
            Image(painter = profilePicture, contentDescription = "Foto de Perfil")
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
            Text("Editar seu perfil", color = Color.White, fontSize = 18.sp)
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
                        Toast.makeText(context, "Conta excluída com sucesso", Toast.LENGTH_LONG).show()
                        onNavigateToLogin() // Navega de volta para a tela de login
                    } else {
                        // Verifica se a exclusão falhou por causa da recente autenticação
                        if (task.exception is FirebaseAuthRecentLoginRequiredException) {
                            Toast.makeText(context, "Por favor, faça login novamente e tente excluir a conta", Toast.LENGTH_LONG).show()
                            // Navega para a tela de login para reautenticar
                            onNavigateToLogin()
                        } else {
                            Toast.makeText(context, "Erro ao excluir a conta: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe57373 )),
            modifier = Modifier.width(280.dp)
        ) {
            Text("Excluir Conta", color = Color.White, fontSize = 18.sp)
        }
    }
}