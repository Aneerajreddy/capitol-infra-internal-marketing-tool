package com.company.internalapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    error: String?,
    onLogin: (String, String) -> Unit
) {
    var mobile by rememberSaveable { mutableStateOf("9000000002") }
    var passwordOrOtp by rememberSaveable { mutableStateOf("password") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Internal Sales & Ops", style = MaterialTheme.typography.headlineSmall)
        Text("Sign in with mobile + password/OTP", modifier = Modifier.padding(top = 8.dp))

        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            label = { Text("Mobile Number") }
        )

        OutlinedTextField(
            value = passwordOrOtp,
            onValueChange = { passwordOrOtp = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            label = { Text("Password / OTP") },
            visualTransformation = PasswordVisualTransformation()
        )

        if (!error.isNullOrBlank()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        TextButton(
            onClick = { onLogin(mobile, passwordOrOtp) },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 12.dp)
        ) {
            Text("Login")
        }
    }
}
