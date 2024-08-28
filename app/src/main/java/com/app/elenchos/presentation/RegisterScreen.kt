package com.app.elenchos.presentation.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

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
            painter = painterResource(id = R.drawable.logo),
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