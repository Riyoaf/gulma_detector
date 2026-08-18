package com.example.gulmadetektor.ui.screens

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gulmadetektor.data.WeedClassifier
import com.example.gulmadetektor.ui.components.CameraPreview
import com.example.gulmadetektor.ui.theme.PrimaryGreen
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.util.concurrent.Executors

@Composable
fun LiveCameraScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // Logic Request Permission Kamera
    var hasCameraPermission by remember {
        mutableStateOf(
            androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    if (!hasCameraPermission) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Mode Real-Time membutuhkan izin kamera untuk mendeteksi gulma secara langsung.", 
                    color = Color.White, 
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center, 
                    modifier = Modifier.padding(32.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { permissionLauncher.launch(android.Manifest.permission.CAMERA) }, 
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Berikan Izin Kamera", color = Color.White)
                }
            }
        }
        return
    }
    
    var overlayBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var fps by remember { mutableStateOf(0) }
    var isProcessing by remember { mutableStateOf(false) }
    
    val analyzerExecutor = remember { Executors.newSingleThreadExecutor() }
    val classifier = remember { WeedClassifier(context) }
    
    var frameCounter = 0
    var lastFpsTimestamp = System.currentTimeMillis()

    // Setup ImageAnalysis
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            // Menggunakan RGBA_8888 agar sangat mudah dikonversi ke Bitmap
            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
            .setTargetResolution(Size(720, 1280)) // Standard resolution
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply {
                setAnalyzer(analyzerExecutor) { imageProxy ->
                    if (isProcessing) {
                        imageProxy.close()
                        return@setAnalyzer
                    }
                    isProcessing = true
                    
                    try {
                        // 1. Convert ImageProxy to Bitmap
                        val bitmap = imageProxyToBitmap(imageProxy)
                        
                        // 2. Potong ke kotak 1:1 di tengah (Center Crop)
                        val dimension = minOf(bitmap.width, bitmap.height)
                        val xOffset = (bitmap.width - dimension) / 2
                        val yOffset = (bitmap.height - dimension) / 2
                        
                        val matrix = Matrix()
                        // Rotasi dari sensor kamera jika perlu
                        matrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                        
                        val croppedBitmap = Bitmap.createBitmap(bitmap, xOffset, yOffset, dimension, dimension, matrix, true)
                        
                        // 3. Resize ke 512x512
                        val resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, 512, 512, true)
                        
                        // 4. Inference
                        val overlay = classifier.segmentLive(resizedBitmap)
                        
                        // 5. Update State
                        overlayBitmap = overlay
                        
                        // Kalkulasi FPS
                        frameCounter++
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastFpsTimestamp >= 1000) {
                            fps = frameCounter
                            frameCounter = 0
                            lastFpsTimestamp = currentTime
                        }
                        
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        imageProxy.close()
                        isProcessing = false
                    }
                }
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            imageAnalysis.clearAnalyzer()
            analyzerExecutor.shutdown()
            classifier.close()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // 1. Camera Feed (Background)
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            imageAnalysis = imageAnalysis,
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        )
        
        // 2. Overlay Mask dari AI
        overlayBitmap?.let { bmp ->
            // Karena kita melakukan Center Crop di Analyzer, kita harus menampilkan
            // bmp di tengah layar dengan rasio 1:1 mengikuti lebar layar.
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "Mask Overlay",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f), // Tampilkan berbentuk bujur sangkar di tengah layar
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        
        // 3. UI Kontrol & Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) { 
                Icon(Icons.Default.Close, "Close", tint = Color.White) 
            }

            Text(
                text = "LIVE MODE • $fps FPS",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.8f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
        
        // Bantuan di bawah
        Text(
            text = "Arahkan kamera ke gulma.\nWarna Hijau = Tanaman | Merah = Gulma",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(12.dp)
        )
    }
}

/**
 * Konversi cepat dari RGBA_8888 ImageProxy ke Bitmap.
 */
private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
    val planeProxy = image.planes[0]
    val buffer = planeProxy.buffer
    buffer.rewind()
    val bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
    bitmap.copyPixelsFromBuffer(buffer)
    return bitmap
}
