# Laporan Analisis Model: `gulma.keras`

## Ringkasan Eksekutif
Model `gulma.keras` adalah model **Deep Learning untuk Segmentasi Semantik** (Semantic Segmentation). Model ini dirancang untuk memisahkan objek dalam citra ke dalam 3 kategori (kemungkinan: Background, Gulma, Tanaman). Arsitektur yang digunakan adalah **U-Net** dengan encoder (backbone) berbasis **MobileNetV2**.

## 1. Spesifikasi Teknis

### Arsitektur
- **Tipe Model:** Encoder-Decoder (U-Net like).
- **Backbone (Encoder):** **MobileNetV2**.
    - Diidentifikasi dari struktur layer seperti `expanded_conv_depthwise`, `block_1_expand`, dll.
    - MobileNetV2 dipilih karena ringan dan efisien untuk perangkat mobile (edge devices).
- **Decoder:** Custom Decoder dengan 4 stage upsampling.
    - Menggunakan *skip connections* (Concatenate) untuk menggabungkan fitur resolusi tinggi dari encoder ke decoder.
- **Head:** Convolutional layer akhir + Aktivasi Softmax.

### Parameter
- **Total Parameter:** **8,047,731** (~8.05 Juta).
- **Trainable Parameters:** 8,011,635.
- **Non-trainable Parameters:** 36,096 (State dari Batch Normalization).
- **Ukuran File:**
    - Keras (`.keras`): ~97 MB.
    - TFLite (`.tflite`): ~32 MB (setelah konversi).

## 2. Input dan Output

### Input Layer
- **Nama:** `input_layer`
- **Shape:** `(None, None, None, 3)`
    - **Batch Size:** `None` (Fleksibel).
    - **Height & Width:** `None` (Fleksibel/Fully Convolutional). Model dapat menerima resolusi gambar berapapun (misal 256x256, 512x512).
    - **Channels:** `3` (RGB Images).
- **Tipe Data:** `float32`.

### Output Layer
- **Shape:** `(None, None, None, 3)`
    - **Height & Width:** Sama dengan input (karena struktur U-Net mempertahankan resolusi spasial).
    - **Channels:** `3` (Jumlah Kelas).
- **Aktivasi:** **Softmax**.
    - Output berupa probabilitas. Untuk setiap piksel, jumlah nilai di 3 channel adalah 1.0.
    - Prediksi kelas diambil menggunakan `argmax` pada axis channel.

## 3. Kompatibilitas Deployment
- **TensorFlow Lite:** **Kompatibel**.
    - Model berhasil dikonversi ke `.tflite`.
    - Menggunakan operasi standar yang didukung TFLite (`SELECT_TF_OPS` mungkin diperlukan untuk beberapa operasi kompleks, namun struktur MobileNet+U-Net umumnya didukung dengan baik).
- **Rekomendasi Penggunaan Mobile:**
    - Karena input shape fleksibel, disarankan untuk meresize input ke ukuran tetap (misal 256x256 atau 512x512) saat inferensi di Android/iOS untuk performa yang konsisten.

## 4. Struktur Layer (Sampel)
Berikut adalah gambaran aliran data dari awal hingga akhir:

1.  **Input:** Citra Masuk -> `(H, W, 3)`
2.  **Encoder (MobileNetV2):** Ekstraksi fitur, resolusi mengecil.
    - `Conv1`
    - `expanded_conv_...`
    - `block_1_...` sampai `block_16_...`
3.  **Bridge:** Titik bottleneck dengan fitur terkompresi.
4.  **Decoder:** Upsampling, resolusi membesar.
    - `decoder_stage4_upsampling`
    - `decoder_stage4_concat` (Skip connection)
    - `decoder_stage4a_...`
    - ... berulang hingga stage terakhir.
5.  **Output:** Prediksi final.
    - `final_conv` -> `(H, W, 3)`
    - `softmax` -> Probabilitas.
