package com.app.elenchos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.elenchos.ui.theme.ElenchosTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.font.Font
import com.google.firebase.auth.ktx.auth
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.auth.UserProfileChangeRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ElenchosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthenticationApp()
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onNavigateToHome: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Crop
        )

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Text("Email", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        )

        Text("Senha", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                Firebase.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onNavigateToHome()
                            Log.d("Login", "Login bem-sucedido")
                        } else {
                            // Se houver erro, exiba uma mensagem
                            Toast.makeText(context, "Erro ao fazer login: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            },
            modifier = Modifier
                .width(280.dp)
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFb7adf6))
        ) {
            Text("Login", color = Color.White, fontSize = 18.sp)
        }

        TextButton(onClick = onNavigateToRegister, modifier = Modifier.padding(top = 16.dp)) {
            Text("Ainda não tem conta?", color = Color(0xFFb7adf6))
        }
    }
}

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Cadastro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.logo), // Substitua pelo recurso da sua logo
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Crop
        )

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Text("Nome", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        )

        Text("Email", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        )

        Text("Senha", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Text("Confirme a Senha", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        BasicTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (password == confirmPassword) {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Obtenha o usuário recém-criado
                                val user = Firebase.auth.currentUser

                                // Crie uma solicitação de atualização de perfil para adicionar o nome do usuário
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()

                                user?.updateProfile(profileUpdates)
                                    ?.addOnCompleteListener { profileTask ->
                                        if (profileTask.isSuccessful) {
                                            Toast.makeText(context, "Cadastro bem-sucedido!", Toast.LENGTH_LONG).show()
                                            onNavigateToLogin()
                                        } else {
                                            Toast.makeText(context, "Erro ao atualizar perfil: ${profileTask.exception?.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            } else {
                                Toast.makeText(context, "Erro ao cadastrar: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "As senhas não correspondem", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .width(280.dp)
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFb7adf6))
        ) {
            Text("Registrar", color = Color.White, fontSize = 18.sp)
        }

        TextButton(onClick = onNavigateToLogin, modifier = Modifier.padding(top = 16.dp)) {
            Text("Entre em sua conta", color = Color(0xFFb7adf6))
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun PreviewAuthenticationApp() {
    AuthenticationApp()
}

@Composable
fun HomeScreen(onProfileClick: () -> Unit, onActivitiesClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFb7adf6))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Bem-vindo!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        HomeButton(text = "Seu Perfil", onClick = onProfileClick)
        Spacer(modifier = Modifier.height(16.dp))
        HomeButton(text = "Minhas Atividades", onClick = onActivitiesClick)
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFb7adf6),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onProfileClick = {}, onActivitiesClick = {})
}