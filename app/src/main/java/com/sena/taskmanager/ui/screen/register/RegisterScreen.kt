package com.sena.taskmanager.ui.screen.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onRegisterSuccess()
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Task Manager Register", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.register(email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { onNavigateToLogin() }) {
                Text("Already have an account? Login here")
            }

            state.error?.let { err ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = err, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
