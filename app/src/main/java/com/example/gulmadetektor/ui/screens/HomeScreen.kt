package com.example.gulmadetektor.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gulmadetektor.ui.theme.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import com.example.gulmadetektor.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, repository: com.example.gulmadetektor.data.repository.WeedRepository) {
    var weeds by remember { mutableStateOf<List<com.example.gulmadetektor.data.remote.WeedType>>(emptyList()) }
    var articles by remember { mutableStateOf<List<com.example.gulmadetektor.data.remote.Article>>(emptyList()) }
    
    LaunchedEffect(Unit) {
        try {
            weeds = repository.getWeeds()
            articles = repository.getArticles()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp) // Space for bottom nav
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text("Selamat Datang di", fontSize = 12.sp, color = Color.Gray)
                Text("WeedGuard", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
            }
        }

        // Banner Carousel
        val bannerImages = listOf(
            com.example.gulmadetektor.R.drawable.banner_detection_1,
            com.example.gulmadetektor.R.drawable.banner_detection_2,
            com.example.gulmadetektor.R.drawable.banner_detection_3
        )
        
        val bannerTitles = listOf(
            "Deteksi Gulma dengan AI",
            "Segmentasi Semantik Otomatis",
            "Analisis Presisi Tingkat Piksel"
        )
        
        val bannerDescriptions = listOf(
            "Ambil foto lahan Anda, AI akan mendeteksi gulma: area hijau untuk tanaman, area merah untuk gulma",
            "Foto Anda akan dianalisis dengan segmentasi semantik untuk membedakan tanaman dan gulma",
            "Teknologi AI memberikan analisis detail untuk setiap piksel pada foto tanaman Anda"
        )
        
        var currentBanner by remember { mutableStateOf(0) }
        
        LaunchedEffect(Unit) {
            while (true) {
                kotlinx.coroutines.delay(4000)
                currentBanner = (currentBanner + 1) % bannerImages.size
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = bannerImages[currentBanner]),
                        contentDescription = "Banner ${currentBanner + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    
                    // Indicator dots
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        bannerImages.indices.forEach { index ->
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (index == currentBanner) Color.White 
                                        else Color.White.copy(alpha = 0.5f)
                                    )
                            )
                        }
                    }
                }
                
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = bannerTitles[currentBanner],
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D1B12)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = bannerDescriptions[currentBanner],
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // Popular Weeds
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Tipe Gulma Populer", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = { navController.navigate(Screen.AllWeeds.route) }) {
                Text("Lihat Semua", color = PrimaryGreen)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (weeds.isEmpty()) {
                // Loading Skeletons or Empty State
                repeat(3) {
                     Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    )
                }
            } else {
                weeds.take(5).forEach { weed ->
                    Column(
                        modifier = Modifier
                            .width(160.dp)
                            .background(Color.White, RoundedCornerShape(12.dp))
                            .clickable { navController.navigate(Screen.WeedDetail.createRoute(weed.id)) }
                            .padding(8.dp)
                    ) {
                        coil.compose.AsyncImage(
                            model = coil.request.ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                                .data(weed.image_url)
                                .crossfade(true)
                                .build(),
                            contentDescription = weed.name,
                            contentScale = ContentScale.Crop,
                             modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = weed.name, 
                            fontWeight = FontWeight.Bold, 
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Text(
                            text = weed.type, 
                            fontSize = 10.sp, 
                            color = PrimaryGreen, 
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }
        }

        // Recent Articles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Artikel Terbaru", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = { navController.navigate(Screen.Article.route) }) {
                Text("Lihat Semua", color = PrimaryGreen)
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            articles.take(3).forEach { article ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .clickable { navController.navigate(Screen.ArticleDetail.createRoute(article.id)) }
                        .padding(12.dp)
                ) {
                    coil.compose.AsyncImage(
                        model = coil.request.ImageRequest.Builder(androidx.compose.ui.platform.LocalContext.current)
                            .data(article.image_url)
                            .crossfade(true)
                            .build(),
                        contentDescription = article.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            article.source,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            article.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Oleh: ${article.author}",
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
