package com.example.gulmadetektor.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Article : Screen("article", "Artikel", Icons.Default.Article)
    object Camera : Screen("camera", "Deteksi", Icons.Default.PhotoCamera)
    object History : Screen("history", "Riwayat", Icons.Default.History)
    object Profile : Screen("profile", "Profil", Icons.Default.AccountCircle)

    object Splash : Screen("splash")

    // Auth
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password", "Lupa Kata Sandi")
    object UpdatePassword : Screen("update_password", "Ubah Kata Sandi")
    
    // Non-bottom bar screens
    object ManualCamera : Screen("manual_camera", "Kamera Manual")
    object LiveCamera : Screen("live_camera", "Deteksi Live")

    object Recommendation : Screen("recommendation")
    object DetectionResult : Screen("detection_result/{label}/{confidence}?imageUri={imageUri}&historyId={historyId}", "Hasil Deteksi") {
        fun createRoute(label: String, confidence: String, imageUri: String, historyId: Int = -1): String {
            val encodedUri = java.net.URLEncoder.encode(imageUri, "UTF-8")
            return "detection_result/$label/$confidence?imageUri=$encodedUri&historyId=$historyId"
        }
    }
    
    object WeedDetail : Screen("weed_detail/{weedId}", "Detail Gulma") {
        fun createRoute(weedId: Int) = "weed_detail/$weedId"
    }
    
    object AllWeeds : Screen("all_weeds", "Semua Gulma")
    
    object ArticleDetail : Screen("article_detail/{articleId}", "Detail Artikel") {
        fun createRoute(articleId: Int) = "article_detail/$articleId"
    }
}
