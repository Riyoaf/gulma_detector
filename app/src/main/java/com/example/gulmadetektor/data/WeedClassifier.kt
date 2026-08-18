package com.example.gulmadetektor.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder

class WeedClassifier(private val context: Context) {
    private var interpreter: Interpreter? = null
    
    // Konfigurasi Pre-processing
    private val INPUT_SIZE = 512
    private val IMAGE_MEAN = floatArrayOf(0.485f, 0.456f, 0.406f)
    private val IMAGE_STD = floatArrayOf(0.229f, 0.224f, 0.225f)
    
    // Pra-alokasi buffer untuk mencegah OutOfMemory & GC Pauses saat Real-Time
    private val inputBuffer = ByteBuffer.allocateDirect(1 * INPUT_SIZE * INPUT_SIZE * 3 * 4).apply {
        order(ByteOrder.nativeOrder())
    }
    private val intValues = IntArray(INPUT_SIZE * INPUT_SIZE)
    private val overlayPixels = IntArray(INPUT_SIZE * INPUT_SIZE)
    private val outputArray = Array(1) { Array(INPUT_SIZE) { Array(INPUT_SIZE) { FloatArray(3) } } }
    
    init {
        setupInterpreter()
    }

    private fun setupInterpreter() {
        try {
            val model = FileUtil.loadMappedFile(context, "best_model_unet_tversky_float32.tflite")
            val options = Interpreter.Options().apply {
                numThreads = 4
            }
            interpreter = Interpreter(model, options)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to initialize interpreter: ${e.message}", e)
        }
    }

    @Synchronized
    fun segment(originalBitmap: Bitmap): Pair<Bitmap, String> {
        if (interpreter == null) {
            setupInterpreter()
        }

        // 1. Pre-Processing
        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, INPUT_SIZE, INPUT_SIZE, true)
        
        inputBuffer.rewind() // Reset buffer position
        resizedBitmap.getPixels(intValues, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)
        
        var pixel = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val value = intValues[pixel++]
                val r = ((value shr 16) and 0xFF) / 255.0f
                val g = ((value shr 8) and 0xFF) / 255.0f
                val b = (value and 0xFF) / 255.0f

                inputBuffer.putFloat((r - IMAGE_MEAN[0]) / IMAGE_STD[0])
                inputBuffer.putFloat((g - IMAGE_MEAN[1]) / IMAGE_STD[1])
                inputBuffer.putFloat((b - IMAGE_MEAN[2]) / IMAGE_STD[2])
            }
        }

        // 2. Inference
        interpreter?.run(inputBuffer, outputArray)

        // 3. Post-Processing
        val overlayBitmap = Bitmap.createBitmap(INPUT_SIZE, INPUT_SIZE, Bitmap.Config.ARGB_8888)
        
        var cropCount = 0
        var weedCount = 0

        var pixelIndex = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val probs = outputArray[0][i][j]
                
                var maxProb = probs[0]
                var argmax = 0
                if (probs[1] > maxProb) { maxProb = probs[1]; argmax = 1 }
                if (probs[2] > maxProb) { maxProb = probs[2]; argmax = 2 }

                when (argmax) {
                    0 -> overlayPixels[pixelIndex] = Color.TRANSPARENT
                    1 -> { overlayPixels[pixelIndex] = Color.argb(128, 0, 255, 0); cropCount++ }
                    2 -> { overlayPixels[pixelIndex] = Color.argb(128, 255, 0, 0); weedCount++ }
                }
                pixelIndex++
            }
        }
        
        overlayBitmap.setPixels(overlayPixels, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)
        
        val scaledOverlay = Bitmap.createScaledBitmap(overlayBitmap, originalBitmap.width, originalBitmap.height, true)
        val finalResult = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(finalResult)
        canvas.drawBitmap(originalBitmap, 0f, 0f, null)
        canvas.drawBitmap(scaledOverlay, 0f, 0f, null)
        
        val labelName = if (weedCount > cropCount) "Gulma" else "Tanaman"

        return Pair(finalResult, labelName)
    }

    /**
     * Fungsi khusus untuk Live Detection yang dioptimalkan.
     * Mengembalikan Bitmap overlay transparan (hanya warna merah/hijau) ukuran 512x512.
     * Tidak menggabungkan dengan gambar asli untuk menghemat pemrosesan.
     */
    @Synchronized
    fun segmentLive(resizedBitmap: Bitmap): Bitmap? {
        if (interpreter == null) return null
        if (resizedBitmap.width != INPUT_SIZE || resizedBitmap.height != INPUT_SIZE) return null

        // 1. Pre-Processing
        inputBuffer.rewind()
        resizedBitmap.getPixels(intValues, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)
        
        var pixel = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val value = intValues[pixel++]
                val r = ((value shr 16) and 0xFF) / 255.0f
                val g = ((value shr 8) and 0xFF) / 255.0f
                val b = (value and 0xFF) / 255.0f

                inputBuffer.putFloat((r - IMAGE_MEAN[0]) / IMAGE_STD[0])
                inputBuffer.putFloat((g - IMAGE_MEAN[1]) / IMAGE_STD[1])
                inputBuffer.putFloat((b - IMAGE_MEAN[2]) / IMAGE_STD[2])
            }
        }

        // 2. Inference
        interpreter?.run(inputBuffer, outputArray)

        // 3. Post-Processing (Hanya overlay)
        val overlayBitmap = Bitmap.createBitmap(INPUT_SIZE, INPUT_SIZE, Bitmap.Config.ARGB_8888)
        
        var pixelIndex = 0
        for (i in 0 until INPUT_SIZE) {
            for (j in 0 until INPUT_SIZE) {
                val probs = outputArray[0][i][j]
                
                var maxProb = probs[0]
                var argmax = 0
                if (probs[1] > maxProb) { maxProb = probs[1]; argmax = 1 }
                if (probs[2] > maxProb) { maxProb = probs[2]; argmax = 2 }

                when (argmax) {
                    0 -> overlayPixels[pixelIndex] = Color.TRANSPARENT
                    1 -> overlayPixels[pixelIndex] = Color.argb(128, 0, 255, 0)
                    2 -> overlayPixels[pixelIndex] = Color.argb(128, 255, 0, 0)
                }
                pixelIndex++
            }
        }
        
        overlayBitmap.setPixels(overlayPixels, 0, INPUT_SIZE, 0, 0, INPUT_SIZE, INPUT_SIZE)
        return overlayBitmap
    }

    @Synchronized
    fun close() {
        interpreter?.close()
        interpreter = null
    }
}
