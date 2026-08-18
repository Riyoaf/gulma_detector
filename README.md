# 🌾 Gulma Detektor - WeedGuard AI

**Aplikasi Android untuk deteksi dan manajemen gulma menggunakan AI dan Computer Vision**

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg)](https://developer.android.com/jetpack/compose)
[![TensorFlow Lite](https://img.shields.io/badge/ML-TensorFlow%20Lite-FF6F00.svg)](https://www.tensorflow.org/lite)

## 📱 Tentang Aplikasi

**Gulma Detektor (WeedGuard AI)** adalah aplikasi mobile berbasis Android yang membantu petani dan praktisi pertanian untuk:
- 🔍 **Mendeteksi gulma** secara otomatis menggunakan kamera
- 🤖 **Segmentasi gambar** dengan TensorFlow Lite untuk membedakan tanaman utama dan gulma
- 📚 **Edukasi** tentang jenis-jenis gulma dan cara penanganannya
- 💬 **Chatbot AI** dengan Gemini untuk konsultasi pertanian
- 📊 **Riwayat deteksi** tersimpan lokal dengan Room Database

## ✨ Fitur Utama

### 1. 📸 Deteksi Gulma Real-time
- Kamera terintegrasi untuk capture gambar lahan
- Segmentasi otomatis menggunakan TensorFlow Lite
- Visualisasi area gulma (merah) vs tanaman utama (hijau)
- Rekomendasi penanganan berdasarkan hasil deteksi

### 2. 📖 Pusat Edukasi & Artikel
- Artikel terbaru tentang manajemen gulma
- Database jenis-jenis gulma dengan gambar dan deskripsi
- Informasi detail setiap jenis gulma
- Data real-time dari API

### 3. 🤖 Chatbot AI (Gemini)
- Konsultasi langsung dengan AI tentang gulma
- Model: Gemini 3 Flash Preview
- Respons cepat dan akurat
- Bahasa Indonesia

### 4. 📜 Riwayat Deteksi
- Simpan hasil deteksi secara lokal
- Review kembali deteksi sebelumnya
- Timestamp otomatis
- Gambar tersimpan

### 5. 🏠 Dashboard Informatif
- Banner edukatif
- Tipe gulma populer
- Artikel terbaru
- Navigasi mudah

## 🛠️ Teknologi yang Digunakan

### Core Technologies
- **Kotlin** - Bahasa pemrograman utama
- **Jetpack Compose** - Modern UI toolkit
- **Material Design 3** - Design system

### Machine Learning & AI
- **TensorFlow Lite** - Model segmentasi gulma
- **Google Gemini AI** - Chatbot assistant
- **TensorFlow Lite Task Vision** - Image processing

### Networking & Data
- **Retrofit** - REST API client
- **OkHttp** - HTTP client
- **Gson** - JSON serialization
- **Coil** - Image loading

### Local Storage
- **Room Database** - Local database untuk riwayat
- **KSP** - Kotlin Symbol Processing

### Camera & Image
- **CameraX** - Camera API
- **uCrop** - Image cropping

## 🏗️ Arsitektur

```
app/
├── data/
│   ├── local/          # Room Database entities & DAOs
│   ├── remote/         # API interfaces & models
│   └── repository/     # Data repositories
├── ui/
│   ├── components/     # Reusable UI components
│   ├── navigation/     # Navigation setup
│   ├── screens/        # App screens
│   └── theme/          # Theme & styling
└── MainActivity.kt     # Entry point
```

**Pattern:** Repository Pattern + MVVM-like structure

## 🌐 API Integration

### Weed Type API
- **Base URL:** `https://riyoaf--weed-type-api-fastapi-app.modal.run`
- **Endpoints:**
  - `GET /weeds` - List semua jenis gulma
  - `GET /weeds/{id}` - Detail gulma
  - `GET /articles` - List artikel
  - `GET /articles/{id}` - Detail artikel

### Segmentation API
- **Base URL:** `https://sultanazizul--gulma-segmentation-api-predict-endpoint.modal.run`
- **Endpoint:**
  - `POST /` - Upload gambar untuk segmentasi

## 📦 Dependencies

```kotlin
// Core
implementation("androidx.core:core-ktx:1.10.1")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
implementation("androidx.activity:activity-compose:1.8.0")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.8.5")

// CameraX
implementation("androidx.camera:camera-core:1.3.4")
implementation("androidx.camera:camera-camera2:1.3.4")
implementation("androidx.camera:camera-lifecycle:1.3.4")

// TensorFlow Lite
implementation("org.tensorflow:tensorflow-lite:2.14.0")
implementation("org.tensorflow:tensorflow-lite-support:0.4.4")

// Gemini AI
implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

// Networking
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Image Loading
implementation("io.coil-kt:coil-compose:2.5.0")
```

## 🚀 Cara Menjalankan

### Prerequisites
- Android Studio Hedgehog atau lebih baru
- JDK 11 atau lebih baru
- Android SDK API 24+ (Android 7.0+)
- Gradle 8.13+

### Setup
1. Clone repository:
```bash
git clone https://github.com/sultanazizul/gulma-detektor-android.git
cd gulma-detektor-android
```

2. Buka project di Android Studio

3. Sync Gradle files

4. Setup API Keys (jika diperlukan):
   - Gemini API key di `ChatbotScreen.kt`

5. Build & Run:
```bash
./gradlew assembleDebug
```

## 📱 Screenshots

### Home Screen
- Dashboard dengan banner edukatif
- Tipe gulma populer
- Artikel terbaru

### Detection Screen
- Kamera real-time
- Hasil segmentasi
- Rekomendasi penanganan

### Education Screen
- List artikel
- Detail artikel
- Database gulma

### Chatbot Screen
- Chat dengan AI
- Konsultasi real-time

## 🎯 Roadmap

- [ ] Offline mode untuk artikel
- [ ] Export riwayat ke PDF
- [ ] Statistik deteksi
- [ ] Multi-language support
- [ ] Dark mode
- [ ] Push notifications

## 👥 Tim Pengembang

- **Sultan Azizul** - [@sultanazizul](https://github.com/sultanazizul)

## 📄 Lisensi

Project ini menggunakan lisensi MIT. Lihat file `LICENSE` untuk detail.

## 🙏 Acknowledgments

- TensorFlow Lite untuk model ML
- Google Gemini AI untuk chatbot
- FastAPI untuk backend API
- Material Design untuk design system

---

**Dibuat dengan ❤️ untuk membantu petani Indonesia**
