package com.example.gulmadetektor.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gulmadetektor.data.WeedClassifier
import com.example.gulmadetektor.ui.components.CameraPreview
import com.example.gulmadetektor.ui.navigation.Screen
import com.example.gulmadetektor.ui.theme.AccentOrange
import com.example.gulmadetektor.ui.theme.BackgroundDark
import com.example.gulmadetektor.ui.theme.PrimaryGreen
import java.util.concurrent.Executors
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.yalantis.ucrop.UCrop

@Composable
fun ManualCameraScreen(navController: NavController) {
    val context = LocalContext.current
    val imageCapture = remember { ImageCapture.Builder().build() }
    val repository = remember { com.example.gulmadetektor.data.repository.WeedRepository() }
    val scope = rememberCoroutineScope()
    
    // UI State
    var lensFacing by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    var flashMode by remember { mutableStateOf(ImageCapture.FLASH_MODE_OFF) }
    var isProcessing by remember { mutableStateOf(false) }
    var processingStatus by remember { mutableStateOf("") }

    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    // Permission Logic
    var hasCameraPermission by remember {
        mutableStateOf(
            androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
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
                    text = "Aplikasi membutuhkan izin kamera untuk mendeteksi gulma.", 
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

    // Crop Launcher
    val cropLauncher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK && result.data != null) {
            val resultUri = UCrop.getOutput(result.data!!)
            resultUri?.let { uri ->
                isProcessing = true
                processingStatus = "Mengunggah & Menganalisis..."
                
                scope.launch(kotlinx.coroutines.Dispatchers.IO) {
                    try {
                        val file = java.io.File(uri.path!!)
                        
                        // -- INFERENSI LOKAL ON-DEVICE TFLITE --
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        val classifier = WeedClassifier(context)
                        val (resultBitmap, label) = classifier.segment(bitmap)
                        classifier.close()

                        // Simpan resultBitmap ke file lokal agar bisa dibaca di DetectionResultScreen
                        val resultFile = java.io.File(context.cacheDir, "result_${System.currentTimeMillis()}.jpg")
                        val out = java.io.FileOutputStream(resultFile)
                        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                        out.flush()
                        out.close()
                        
                        val resultUri = Uri.fromFile(resultFile).toString()
                        
                        withContext(kotlinx.coroutines.Dispatchers.Main) {
                            isProcessing = false
                            val encodedMaskUrl = URLEncoder.encode(resultUri, StandardCharsets.UTF_8.toString())
                            val encodedStatus = URLEncoder.encode(label, StandardCharsets.UTF_8.toString())
                            val encodedDims = URLEncoder.encode("${bitmap.width}x${bitmap.height}", StandardCharsets.UTF_8.toString())
                            
                            navController.navigate(
                                Screen.DetectionResult.createRoute(encodedStatus, encodedDims, encodedMaskUrl)
                            ) {
                                // Pop up to home to prevent weird backstack issues when navigating from manual camera
                                popUpTo(Screen.Camera.route) { inclusive = false }
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(kotlinx.coroutines.Dispatchers.Main) {
                            isProcessing = false
                            Toast.makeText(context, "Gagal proses lokal: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(result.data!!)
            Toast.makeText(context, "Crop error: ${error?.message}", Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == android.app.Activity.RESULT_CANCELED) {
            // User cancelled crop
            Toast.makeText(context, "Crop dibatalkan", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to start Crop
    fun startCrop(uri: Uri) {
        val destUri = Uri.fromFile(java.io.File(context.cacheDir, "cropped_${System.currentTimeMillis()}.jpg"))
        val uCrop = UCrop.of(uri, destUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(512, 512)
            .withOptions(UCrop.Options().apply {
                setCompressionQuality(90)
                setToolbarTitle("Potong Gambar")
                setToolbarColor(android.graphics.Color.parseColor("#4CAF50")) // Primary Green
                setStatusBarColor(android.graphics.Color.parseColor("#388E3C")) // Darker Green
                setToolbarWidgetColor(android.graphics.Color.WHITE)
                setActiveControlsWidgetColor(android.graphics.Color.parseColor("#4CAF50"))
                setFreeStyleCropEnabled(true)
            })
        
        cropLauncher.launch(uCrop.getIntent(context))
    }

    LaunchedEffect(flashMode) {
        imageCapture.flashMode = flashMode
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // 1. Camera Preview Layer
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            imageCapture = imageCapture,
            cameraSelector = lensFacing
        )

        // 2. Overlay Layer
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 8.dp.toPx()
                val cornerLength = 60.dp.toPx()
                val width = size.width
                val height = size.height
                val frameSize = width * 0.7f
                val left = (width - frameSize) / 2
                val top = (height - frameSize) / 2
                val right = left + frameSize
                val bottom = top + frameSize

                val path = Path().apply {
                    moveTo(left, top + cornerLength); lineTo(left, top); lineTo(left + cornerLength, top)
                    moveTo(right - cornerLength, top); lineTo(right, top); lineTo(right, top + cornerLength)
                    moveTo(right, bottom - cornerLength); lineTo(right, bottom); lineTo(right - cornerLength, bottom)
                    moveTo(left + cornerLength, bottom); lineTo(left, bottom); lineTo(left, bottom - cornerLength)
                }

                drawPath(
                    path = path,
                    color = PrimaryGreen,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            
            Text(
                text = "Posisikan gulma di tengah bingkai",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 150.dp)
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }

        // 3. Top Control Bar
        Row(
            modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(40.dp).background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) { Icon(Icons.Default.Close, "Close", tint = Color.White) }

            Text(
                text = "PINDAI GULMA",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(20.dp)).padding(horizontal = 16.dp, vertical = 8.dp)
            )

            IconButton(
                onClick = { flashMode = if (flashMode == ImageCapture.FLASH_MODE_OFF) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF },
                modifier = Modifier.size(40.dp).background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    if (flashMode == ImageCapture.FLASH_MODE_ON) Icons.Default.FlashOn else Icons.Default.FlashOff, 
                    "Flash", 
                    tint = if (flashMode == ImageCapture.FLASH_MODE_ON) Color.Yellow else Color.White
                )
            }
        }

        // 4. Bottom Control Bar
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(bottom = 40.dp, start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let { startCrop(it) }
            }

            Box(
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)).background(Color.DarkGray.copy(alpha = 0.7f)).clickable { galleryLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Image, "Gallery", tint = Color.LightGray) }

            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
                Box(modifier = Modifier.size(72.dp).border(4.dp, Color.White, CircleShape))
                Button(
                    onClick = {
                        if (!isProcessing) {
                            takePhoto(context, imageCapture, cameraExecutor) { _, uri ->
                                startCrop(uri)
                            }
                        }
                    },
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = AccentOrange),
                    contentPadding = PaddingValues(0.dp)
                ) { Icon(Icons.Default.PhotoCamera, "Capture", tint = Color.White) }
            }

            IconButton(
                onClick = { lensFacing = if (lensFacing == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA },
                modifier = Modifier.size(50.dp).background(Color.DarkGray.copy(alpha = 0.7f), CircleShape)
            ) { Icon(Icons.Default.Cameraswitch, "Switch Camera", tint = Color.White) }
        }
        
        if (isProcessing) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f)), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = PrimaryGreen)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = processingStatus, color = Color.White, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    executor: java.util.concurrent.Executor,
    onImageCaptured: (Bitmap, Uri) -> Unit
) {
    val photoFile = java.io.File(
        context.cacheDir,
        "weed_${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    Toast.makeText(context, "Error capture: ${exc.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                val exif = android.media.ExifInterface(photoFile.absolutePath)
                val orientation = exif.getAttributeInt(android.media.ExifInterface.TAG_ORIENTATION, android.media.ExifInterface.ORIENTATION_NORMAL)
                
                val matrix = Matrix()
                when (orientation) {
                    android.media.ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                    android.media.ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                    android.media.ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                }
                
                val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    onImageCaptured(rotatedBitmap, savedUri)
                }
            }
        }
    )
}
