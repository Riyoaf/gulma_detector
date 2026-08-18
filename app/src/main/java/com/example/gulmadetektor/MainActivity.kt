package com.example.gulmadetektor

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gulmadetektor.ui.navigation.Screen
import com.example.gulmadetektor.ui.screens.*
import com.example.gulmadetektor.ui.theme.GulmaDetektorTheme
import com.example.gulmadetektor.ui.theme.GulmaDetektorTheme
import com.example.gulmadetektor.ui.theme.PrimaryGreen
import com.example.gulmadetektor.data.repository.WeedRepository
import androidx.compose.ui.platform.LocalContext

import io.github.jan.supabase.gotrue.handleDeeplinks
import com.example.gulmadetektor.data.remote.SupabaseModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SupabaseModule.client.handleDeeplinks(intent)
        enableEdgeToEdge()
        setContent {
            GulmaDetektorTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val authRepository = remember { com.example.gulmadetektor.data.repository.AuthRepository() } // Init Auth Repo
    val repository = remember { WeedRepository() } // Restore WeedRepository
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Check login state
    // Use a state to hold the start destination, defaulting to null until check is done
    // Or just check synchronously if possible (supabase auth state is usually async or cached). 
    // For simplicity, we assume we check session existence.
    // However, recomposition might be tricky. Let's use a simpler approach for now:
    // If user is not logged in, they will be navigated to Login Screen from Home if we defend it,
    // OR we set start destination based on initial check.
    
    // Handle deep link from intent if the app is opened via reset password link
    val intentData = (LocalContext.current as? android.app.Activity)?.intent?.data
    val isResetPassword = intentData?.scheme == "weedguard" && intentData.host == "reset-password"
    
    // Pindahkan pengecekan login ke dalam logic Splash Screen
    val startDest = if (isResetPassword) Screen.UpdatePassword.route else Screen.Splash.route

    Scaffold(
        bottomBar = {
            // Hide on Camera, Login, Register, Splash, ForgotPassword, UpdatePassword
            val hideBottomBarScreens = listOf(
                Screen.Camera.route, Screen.ManualCamera.route, Screen.LiveCamera.route, Screen.Login.route, Screen.Register.route, 
                Screen.Splash.route, Screen.ForgotPassword.route, Screen.UpdatePassword.route
            )
            
            if (currentRoute !in hideBottomBarScreens) {
                NavigationBar(
                    containerColor = Color.White,
                    modifier = Modifier.height(80.dp)
                ) {
                    val items = listOf(
                        Screen.Home,
                        Screen.Article,
                        Screen.Camera,
                        Screen.History,
                        Screen.Profile
                    )
                    
                    items.forEach { screen ->
                        val isSelected = currentRoute == screen.route
                        val isCamera = screen == Screen.Camera
                        
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) return@NavigationBarItem
                                
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                if (isCamera) {
                                     // Placeholder for floating button, handled by Scaffold FAB or custom layout?
                                     // In HTML navigation, camera is just an item.
                                     // Let's use standard icon for now, customized.
                                     Icon(Icons.Default.PhotoCamera, contentDescription = "Camera", tint = PrimaryGreen) 
                                } else {
                                     Icon(screen.icon!!, contentDescription = screen.title)
                                }
                            },
                            label = { 
                                Text(
                                    screen.title ?: "", 
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal,
                                    color = if (isSelected) PrimaryGreen else Color.Gray
                                ) 
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent, // No pill indicator
                                selectedIconColor = PrimaryGreen,
                                unselectedIconColor = Color.Gray,
                                selectedTextColor = PrimaryGreen,
                                unselectedTextColor = Color.Gray
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val isFullScreen = currentRoute in listOf(
            Screen.Login.route, 
            Screen.Register.route, 
            Screen.Splash.route,
            Screen.ForgotPassword.route,
            Screen.UpdatePassword.route,
            Screen.ManualCamera.route,
            Screen.LiveCamera.route
        )
        
        NavHost(
            navController = navController,
            startDestination = startDest,
            modifier = Modifier.padding(
                top = if (isFullScreen) 0.dp else innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
        ) {
            composable(Screen.Splash.route) { com.example.gulmadetektor.ui.screens.WelcomeScreen(navController, authRepository) }
            composable(Screen.Login.route) { LoginScreen(navController, authRepository) }
            composable(Screen.Register.route) { RegisterScreen(navController, authRepository) }
            composable(Screen.ForgotPassword.route) { com.example.gulmadetektor.ui.screens.ForgotPasswordScreen(navController, authRepository) }
            composable(Screen.UpdatePassword.route) { com.example.gulmadetektor.ui.screens.UpdatePasswordScreen(navController, authRepository) }
            
            composable(Screen.Home.route) { HomeScreen(navController, repository) }
            composable(Screen.Article.route) { EducationScreen(navController, repository) }
            composable(Screen.Camera.route) { 
                WeedDetectionScreen(navController) 
            }
            composable(Screen.ManualCamera.route) { ManualCameraScreen(navController) }
            composable(Screen.LiveCamera.route) { LiveCameraScreen(navController) }
            composable(Screen.History.route) { HistoryScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
            composable(Screen.DetectionResult.route) { backStackEntry ->
                val label = backStackEntry.arguments?.getString("label") ?: "Unknown"
                val confidence = backStackEntry.arguments?.getString("confidence") ?: "0%"
                val rawImageUri = backStackEntry.arguments?.getString("imageUri") ?: ""
                val imageUri = java.net.URLDecoder.decode(rawImageUri, "UTF-8")
                val historyId = backStackEntry.arguments?.getString("historyId")?.toIntOrNull() ?: -1
                DetectionResultScreen(navController, label, confidence, imageUri, historyId, repository)
            }
            composable(Screen.WeedDetail.route) { backStackEntry ->
                val weedId = backStackEntry.arguments?.getString("weedId")?.toIntOrNull() ?: 0
                WeedDetailScreen(navController, weedId, repository)
            }
            composable(Screen.AllWeeds.route) {
                AllWeedsScreen(navController, repository)
            }
            composable(Screen.ArticleDetail.route) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId")?.toIntOrNull() ?: 0
                ArticleDetailScreen(navController, articleId, repository)
            }
        }
    }
}