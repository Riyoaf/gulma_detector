package com.example.gulmadetektor.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.gulmadetektor.data.remote.WeedType
import com.example.gulmadetektor.data.repository.WeedRepository
import com.example.gulmadetektor.ui.navigation.Screen
import com.example.gulmadetektor.ui.theme.AccentOrange
import com.example.gulmadetektor.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionResultScreen(
    navController: NavController,
    label: String, // Treat as status
    confidence: String, // Treat as dimensions or empty
    imageUri: String, // Treat as Mask URL
    historyId: Int,
    repository: WeedRepository
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val historyRepository = remember { com.example.gulmadetektor.data.repository.HistoryRepository(context) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // Parse the imageUri directly as URL string (it was updated in prev screen)
    val maskUrl = imageUri 

    var matchedWeed by remember { mutableStateOf<WeedType?>(null) }
    var notes by remember { mutableStateOf("") }
    var existingHistory by remember { mutableStateOf<com.example.gulmadetektor.data.remote.DetectionHistoryDto?>(null) }
    var isSaving by remember { mutableStateOf(false) }
    
    LaunchedEffect(label) {
        // TFLITE OFFLINE MODE:
        // Tidak perlu memanggil API repository.getWeeds() karena label
        // hanya berupa "Gulma" atau "Tanaman", dan semua teks panduan
        // sudah di-hardcode (tercatat) di UI komponen di bawah.
        // Hal ini membuat layar hasil deteksi memuat secara instan dan 100% offline.
        
        /* 
        try {
            val weeds = repository.getWeeds()
            // Simple matching logic
            matchedWeed = weeds.find { it.name.contains(label, ignoreCase = true) || label.contains(it.name, ignoreCase = true) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        */
    } 

    LaunchedEffect(historyId) {
        if (historyId != -1) {
            val history = historyRepository.getHistoryById(historyId.toLong())
            if (history != null) {
                existingHistory = history
                notes = history.notes ?: ""
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Hasil Deteksi Gulma", 
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(Icons.Outlined.Share, contentDescription = "Share")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White.copy(alpha = 0.8f) 
                ),
                windowInsets = WindowInsets(0.dp)
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // 1. Image Section
            var showZoomDialog by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp) 
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray)
                    .clickable { showZoomDialog = true } // Open Zoom Dialog
            ) {
                 coil.compose.AsyncImage(
                     model = coil.request.ImageRequest.Builder(context)
                         .data(imageUri) // Note: Using imageUri here instead of maskUrl since maskUrl might not be defined
                         .crossfade(true)
                         .build(),
                     contentDescription = "Detected Weed Overlay",
                     contentScale = ContentScale.Crop,
                     modifier = Modifier.fillMaxSize()
                 )
                 
                 // Hint Icon
                 Icon(
                     Icons.Default.ZoomIn,
                     contentDescription = "Zoom",
                     tint = Color.White,
                     modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .background(Color.Black.copy(alpha = 0.5f), androidx.compose.foundation.shape.CircleShape)
                        .padding(8.dp)
                 )
            }

            if (showZoomDialog) {
                Dialog(
                    onDismissRequest = { showZoomDialog = false },
                    properties = DialogProperties(usePlatformDefaultWidth = false)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        // Zoom Logic inside Dialog
                        var scale by remember { mutableStateOf(1f) }
                        var offset by remember { mutableStateOf(Offset.Zero) }
                        var size by remember { mutableStateOf(IntSize.Zero) }

                        coil.compose.AsyncImage(
                            model = coil.request.ImageRequest.Builder(context)
                                .data(imageUri) // Note: Using imageUri here instead of maskUrl since maskUrl might not be defined
                                .build(),
                            contentDescription = "Zoomed Image",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .onSizeChanged { size = it }
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    translationX = offset.x,
                                    translationY = offset.y
                                )
                                .pointerInput(Unit) {
                                    detectTransformGestures { _, pan, zoom, _ ->
                                        scale = (scale * zoom).coerceIn(1f, 5f)
                                        if (scale > 1f) {
                                            val maxX = (size.width.toFloat() * (scale - 1)) / 2
                                            val maxY = (size.height.toFloat() * (scale - 1)) / 2
                                            val newOffset = offset + pan
                                            offset = Offset(
                                                newOffset.x.coerceIn(-maxX, maxX),
                                                newOffset.y.coerceIn(-maxY, maxY)
                                            )
                                        } else {
                                            offset = Offset.Zero
                                        }
                                    }
                                }
                        )

                        // Close Button
                        IconButton(
                            onClick = { showZoomDialog = false },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                }
            }

            // 2. Guide Card (Merged Title removed)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                
                // Dynamic Weed Card
                if (matchedWeed != null) {
                    val weed = matchedWeed!!
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)), // Light Green
                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .clickable { 
                                navController.navigate(Screen.WeedDetail.createRoute(weed.id)) 
                            }
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            // Thumbnail
                            coil.compose.AsyncImage(
                                model = coil.request.ImageRequest.Builder(context)
                                    .data(weed.image_url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = weed.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                            
                            // DEBUG: Show URL so we can see if it's correct
                            Text(
                                text = "URL: $imageUri",
                                fontSize = 10.sp,
                                color = Color.Red,
                                modifier = Modifier.padding(8.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Jenis Gulma Terdeteksi",
                                    fontSize = 12.sp,
                                    color = PrimaryGreen,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = weed.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D1B12)
                                )
                                Text(
                                    text = "Klik untuk lihat detail >",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }

                // 3. Guide Card
                Card(
                     colors = CardDefaults.cardColors(containerColor = Color.White),
                     elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                     shape = RoundedCornerShape(12.dp),
                     modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "PANDUAN HASIL DETEKSI",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PrimaryGreen,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // Green Item
                        Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(top = 4.dp)
                                    .clip(CircleShape)
                                    .background(Color.Green)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Area Hijau: Tanaman Utama",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Ini adalah tanaman budidaya Anda yang terdeteksi sehat. Pastikan area ini tetap dominan dan bebas dari gangguan tanaman lain.",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        // Red Item
                        Row(verticalAlignment = Alignment.Top) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .padding(top = 4.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Area Merah: Gulma Pengganggu",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Ini adalah tanaman liar yang tumbuh di sela-sela tanaman utama Anda. Area ini perlu mendapatkan perhatian khusus.",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                // 4. Why Important
                Card(
                    colors = CardDefaults.cardColors(containerColor = AccentOrange.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                         Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Info, contentDescription = null, tint = AccentOrange)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "MENGAPA INI PENTING?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = AccentOrange
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Area berwarna merah (gulma) yang Anda lihat pada gambar sedang melakukan kompetisi dengan tanaman hijau Anda. Gulma berebut mengambil unsur hara (pupuk), air, dan sinar matahari. Jika area merah dibiarkan meluas, pertumbuhan tanaman utama akan terhambat, menjadi kerdil, atau bahkan mati kalah saing.",
                            fontSize = 13.sp,
                            color = Color(0xFF5D4037),
                            lineHeight = 20.sp
                        )
                    }
                }

                // 5. Handling Advice
                Text(
                    text = "SARAN PENANGANAN:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0D1B12),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                HandlingItem(
                    title = "Cabut Manual",
                    desc = "Karena posisi gulma terlihat tumbuh sangat dekat dengan batang tanaman utama, pencabutan dengan tangan (manual) adalah cara paling aman. Pastikan mencabut hingga ke akarnya agar tidak tumbuh kembali."
                )
                HandlingItem(
                    title = "Hindari Herbisida Kontak",
                    desc = "Jangan menyemprot herbisida sembarangan pada area ini karena berisiko mengenai daun tanaman utama (hijau) yang dapat menyebabkan layu atau kematian tanaman budidaya."
                )
                HandlingItem(
                    title = "Sanitasi Lahan",
                    desc = "Segera bersihkan sisa gulma yang telah dicabut dari area bedengan untuk mencegah penyebaran biji atau penyakit kembali ke tanah."
                )

                // 6. Catatan Pengguna
                Text(
                    text = "CATATAN ANDA:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0D1B12),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text("Tambahkan catatan deteksi ini...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }

            // 7. Action Button
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        if (isSaving) return@Button
                        
                        scope.launch {
                            isSaving = true
                            try {
                                if (historyId == -1) {
                                    val history = com.example.gulmadetektor.data.remote.DetectionHistoryDto(
                                        weedName = label,
                                        confidence = confidence,
                                        imageUrl = imageUri,
                                        notes = notes.takeIf { it.isNotBlank() }
                                    )
                                    historyRepository.insertHistory(history, localImageUri = imageUri)
                                    snackbarHostState.showSnackbar(
                                        message = "Hasil deteksi berhasil disimpan!",
                                        duration = SnackbarDuration.Short
                                    )
                                    navController.popBackStack()
                                } else {
                                    existingHistory?.let {
                                        val updatedHistory = it.copy(notes = notes.takeIf { n -> n.isNotBlank() })
                                        historyRepository.updateHistory(updatedHistory)
                                        // Scroll ke atas agar user melihat hasil update, bukan diam di kolom catatan
                                        scrollState.animateScrollTo(0)
                                        snackbarHostState.showSnackbar(
                                            message = "Catatan berhasil diperbarui!",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar(
                                    message = "Gagal menyimpan: ${e.message}",
                                    duration = SnackbarDuration.Short
                                )
                            } finally {
                                isSaving = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = if (isSaving) 2.dp else 10.dp,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    // Selalu enabled=true agar tidak ada disabled-overlay Material3
                    // (disabled overlay = rectangle transparan yg mengganggu)
                    // Klik dijaga manual via "if (isSaving) return@Button"
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        // Berubah warna saat loading: lebih gelap untuk sinyal "sedang proses"
                        containerColor = if (isSaving) Color(0xFF2E7D32) else PrimaryGreen,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                color = Color.White,
                                trackColor = Color.Transparent,
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Menyimpan...",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        } else {
                            Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (historyId == -1) "Simpan Hasil Deteksi" else "Perbarui Catatan",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(80.dp)) // Bottom padding
        }
    }
}

@Composable
fun HandlingItem(title: String, desc: String) {
    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(vertical = 8.dp)) {
        Icon(
            Icons.Outlined.Verified, // or CheckCircle
            contentDescription = null,
            tint = PrimaryGreen,
            modifier = Modifier.size(20.dp).padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0D1B12))
            Text(desc, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
        }
    }
}
