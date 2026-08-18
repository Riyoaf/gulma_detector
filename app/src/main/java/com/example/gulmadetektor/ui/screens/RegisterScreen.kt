package com.example.gulmadetektor.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.gulmadetektor.data.repository.AuthRepository

@Composable
fun RegisterScreen(navController: NavController, authRepository: AuthRepository) {
    AuthScreen(navController = navController, authRepository = authRepository, initialTab = 1)
}
