package com.app.elenchos.presentation.login

import androidx.compose.runtime.Composable
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.elenchos.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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