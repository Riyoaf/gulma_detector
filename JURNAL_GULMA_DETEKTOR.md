# Rancang Bangun Aplikasi Mobile Deteksi Dini Gulma pada Lahan Cabai Berbasis Android Menggunakan Segmentasi Semantik U-Net dan MobileNetV2

**Riyo Andika Febriyan**<sup>1</sup>, **Dewa Made Sri Arsa**<sup>2</sup>, **Desy Purnama Singgih Putri**<sup>3</sup>  
<sup>1,2,3</sup>Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana  
Jalan Raya Kampus Udayana, Jimbaran, Badung, Bali, Indonesia  
<sup>1</sup>email: riyoandikafebriyan@gmail.com  
<sup>2</sup>email: sriarsa@unud.ac.id  
<sup>3</sup>email: desypurnama@unud.ac.id  

*Corresponding author: riyoandikafebriyan@gmail.com*

---

### Abstrak
Sektor pertanian cabai (*Capsicum annuum L.*) di Indonesia menghadapi ancaman serius berupa penurunan hasil panen akibat kompetisi gulma pada periode kritis vegetatif awal, yaitu 2 minggu setelah tanam (2 MST). Penyiangan manual terkendala oleh biaya tenaga kerja yang tinggi dan risiko kesalahan pencabutan (*human error*) yang signifikan karena kemiripan morfologi antara bibit gulma dan bibit cabai muda. Penelitian ini merancang dan membangun aplikasi Android deteksi dini gulma berbasis *Computer Vision* dan *Semantic Segmentation*. Sistem menggunakan model U-Net berbasis *backbone* MobileNetV2 yang dikonversi ke format *TensorFlow Lite* (21,15 MB) untuk eksekusi inferensi lokal *offline on-device* tanpa ketergantungan internet. Aplikasi dikembangkan dengan Android Studio, Kotlin, dan Jetpack Compose, terintegrasi dengan *Supabase Cloud* untuk autentikasi dan riwayat lahan, serta *Retrofit REST API* untuk artikel edukasi. Pengujian penerimaan pengguna (*User Acceptance Testing*/UAT) menggunakan kuesioner skala Likert kepada 15 responden (petani, penyuluh, dan akademisi) menghasilkan persentase kelayakan **94,44%** (skor rata-rata 4,72 dari 5,00) dengan kategori **Sangat Layak (*Accepted*)**, membuktikan aplikasi efektif membantu petani meminimalisir kesalahan pencabutan bibit cabai sekaligus meningkatkan literasi agronomi.

**Kata kunci**— Android; aplikasi mobile; cabai; deteksi gulma; MobileNetV2; segmentasi semantik; U-Net; *User Acceptance Testing*.

---

### Development of Android-Based Mobile Application for Early Weed Detection on Chili Fields Using U-Net Semantic Segmentation and MobileNetV2

### Abstract
Chili (*Capsicum annuum L.*) production in Indonesia faces serious threats from significant yield losses due to weed competition during the critical early vegetative period (2 weeks after planting / 2 WAP). Manual weeding is constrained by high labor costs and significant risk of human error, as weed seedlings morphologically mimic young chili sprouts. This study designs and develops an Android application for early weed detection based on Computer Vision and Semantic Segmentation. The system employs a U-Net model with a MobileNetV2 backbone, converted to TensorFlow Lite format (21.15 MB) for offline on-device inference without internet dependency. The application was developed using Android Studio, Kotlin, and Jetpack Compose, integrated with Supabase Cloud for authentication and field history logbook, alongside Retrofit REST API for educational articles. User Acceptance Testing (UAT) using a Likert scale questionnaire involving 15 respondents (farmers, agricultural extension workers, and academics) achieved an overall feasibility score of **94.44%** (average score 4.72 out of 5.00), categorized as **Highly Feasible (Accepted)**, proving the application effectively minimizes mis-plucking risks and enhances farmers agronomic literacy.

**Keywords**— Android; chili; MobileNetV2; mobile application; semantic segmentation; U-Net; *User Acceptance Testing*; weed detection.

---

## 1. Pendahuluan

Tanaman cabai (*Capsicum annuum L.*) merupakan komoditas hortikultura strategis di Indonesia dengan nilai ekonomi yang sangat tinggi dan tingkat permintaan konsumsi harian yang konsisten [1]. Data Badan Pusat Statistik (BPS) mencatat bahwa cabai menjadi salah satu komoditas penyumbang inflasi terbesar di Indonesia, menjadikan stabilitas produksinya sangat krusial bagi perekonomian nasional. Namun, produktivitas lahan cabai secara konsisten mengalami tekanan akibat keberadaan organisme pengganggu tanaman, terutama gulma. Kehadiran gulma menciptakan persaingan langsung dengan tanaman utama dalam memperebutkan sumber daya esensial seperti nutrisi tanah, ketersediaan air, intensitas cahaya matahari, dan ruang tumbuh [2].

Persaingan paling destruktif antara gulma dan tanaman cabai terjadi pada **Periode Kritis Vegetatif Awal**, yaitu 2 minggu setelah tanam (2 MST). Pada fase ini, sistem perakaran bibit cabai belum cukup dalam untuk menyerap nutrisi secara efisien dan kompetitif, sehingga monopoli nutrisi oleh gulma yang tumbuh lebih agresif dapat menyebabkan kekerdilan pertumbuhan (*stunting*) hingga penurunan potensi hasil panen mencapai 40 hingga 50% apabila tidak ditangani dengan cepat dan tepat [3]. Kondisi ini menempatkan petani cabai, terutama skala kecil dan menengah, pada posisi yang sangat rentan terhadap kerugian ekonomi.

Meskipun dampaknya sangat besar, praktik pengendalian gulma di tingkat petani sebagian besar masih mengandalkan metode penyiangan manual (*manual weeding*). Metode ini membutuhkan alokasi tenaga kerja yang intensif dan biaya operasional yang tinggi, sementara kelangkaan tenaga kerja pertanian di pedesaan semakin meningkat [4]. Selain persoalan efisiensi biaya dan waktu, permasalahan teknis yang paling kritis dalam penyiangan manual adalah tingginya risiko kesalahan identifikasi dan pencabutan (*human error*). Pada fase vegetatif awal (2 MST), ciri morfologi daun bibit gulma, khususnya dari golongan daun lebar seperti bayam duri (*Amaranthus spinosus*), memiliki kemiripan bentuk yang sangat tinggi dengan daun bibit cabai muda. Keterbatasan literasi botani dan pengalaman visual petani kerap menyebabkan kesalahan identifikasi, di mana petani secara tidak sengaja mencabut bibit cabai utama yang keliru dianggap sebagai gulma [5].

Perkembangan pesat teknologi kecerdasan buatan (*Artificial Intelligence* / AI) dan *Computer Vision* membuka peluang baru untuk menyediakan solusi presisi bagi permasalahan tersebut. Secara spesifik, teknik **Segmentasi Semantik (*Semantic Segmentation*)** merupakan pendekatan *Computer Vision* tingkat tinggi yang mampu melakukan klasifikasi pada setiap piksel tunggal dalam sebuah citra digital (*pixel-level classification*) [2], [8]. Berbeda dengan klasifikasi citra konvensional yang hanya memberikan satu label untuk keseluruhan gambar, atau deteksi objek yang menggunakan kotak pembatas (*bounding box*) yang tidak mengikuti bentuk alamiah objek, segmentasi semantik menghasilkan **topeng warna piksel (*pixel mask*)** yang secara tepat mengikuti kontur asli setiap helai daun. Kemampuan ini memungkinkan pemisahan visual yang presisi antara area daun gulma dan daun cabai, bahkan dalam kondisi tajuk daun yang saling bertumpukan (*overlapping*)—suatu kondisi yang sangat umum di lahan pertanian.

Berbagai penelitian terdahulu telah menginvestigasi penerapan *Deep Learning* dalam domain pertanian, mulai dari klasifikasi gulma berbasis UAV oleh Islam et al. [1], segmentasi U-Net pada lahan wortel organik oleh Hashemi-Beni et al. [2], studi komparatif CNN termasuk MobileNetV2 oleh R et al. [3], pengembangan CED-Net oleh Khan et al. [4], modifikasi DeepLabV3+ oleh Yu et al. [5], hingga modernisasi U-Net oleh Li et al. [6]. Di sisi pengembangan aplikasi seluler, Kusuma et al. [10], Ariatmaja et al. [11], Suarjaya et al. [12], dan Wijaya et al. [13] menekankan pentingnya optimasi antarmuka Android yang efisien memori dan ramah pengguna. Namun, terdapat **celah penelitian (*research gap*)** yang signifikan: belum ada sistem yang mengintegrasikan segmentasi semantik tingkat piksel secara ringan (U-Net + MobileNetV2) dalam format *offline on-device* ke dalam aplikasi Android yang dirancang khusus untuk komoditas cabai dengan target pengguna petani.

Untuk mengisi celah penelitian tersebut, penelitian ini merancang dan membangun aplikasi mobile Android **"Gulma Detektor"** (*WeedGuard*). Aplikasi ini mengintegrasikan model segmentasi semantik U-Net berbasis MobileNetV2 dalam format *TensorFlow Lite* (21,15 MB) untuk eksekusi inferensi lokal 100% tanpa koneksi internet, dilengkapi logbook riwayat *Supabase Cloud* dan fitur edukasi agronomi *Retrofit REST API*, serta divalidasi melalui *User Acceptance Testing* (UAT) bersama petani dan penyuluh pertanian di lapangan.

---

## 2. Tinjauan Pustaka

### 2.1. Kajian Penelitian Terdahulu (*State of the Art*)

**1. Deteksi Gulma Berbasis Citra UAV & Machine Learning pada Lahan Cabai:**
Islam et al. (2021) [1] menyelidiki penerapan teknologi pertanian cerdas untuk gulma pada lahan cabai di Australia menggunakan UAV dan algoritma *Machine Learning* konvensional (*Random Forest*, SVM, KNN). Pendekatan ini memerlukan *feature engineering* manual (indeks vegetasi ExG, ExR) dan memiliki keterbatasan fundamental: investasi UAV sangat tinggi dan tidak praktis bagi petani skala kecil. Penelitian ini menjadi bukti bahwa dibutuhkan sistem yang lebih aksesibel dan berbasis perangkat yang dimiliki petani sendiri.

**2. Segmentasi Semantik U-Net vs FCN-8s pada Lahan Wortel Organik:**
Hashemi-Beni et al. (2020) [2] membandingkan U-Net dan FCN-8s untuk memisahkan piksel tanah, wortel, dan gulma. U-Net meraih akurasi global 75,2% vs FCN-8s 72,1%. Penelitian ini membuktikan superioritas U-Net berkat mekanisme *skip connections* yang mempertahankan informasi spasial resolusi tinggi selama proses rekonstruksi *Decoder*. Temuan ini menjadi landasan utama pemilihan arsitektur U-Net dalam penelitian ini.

**3. Klasifikasi CNN Berbasis Transfer Learning (MobileNetV2, ResNet50, VGG16):**
R et al. (2025) [3] menganalisis klasifikasi bibit cabai dan gulma dengan tiga arsitektur CNN. MobileNetV2 meraih akurasi 96,6%, mengungguli ResNet50 (95,0%) dan VGG16 (88,0%). Efisiensi *Depthwise Separable Convolution* dan *Inverted Residual Blocks* menjadikan MobileNetV2 sangat ideal sebagai *backbone* pada perangkat bergerak. Kelemahannya adalah hasil klasifikasi bersifat *image-level*, tidak mampu melokalisasi piksel gulma di dalam satu frame yang sama dengan cabai.

**4. Arsitektur CED-Net untuk Segmentasi Gulma yang Efisien:**
Khan et al. (2020) [4] mengusulkan *Cascaded Encoder-Decoder Network* (CED-Net) untuk mengatasi beban komputasi tinggi pada jaringan segmentasi standar. Penelitian ini membuktikan bahwa jaringan bertingkat efisien mampu segmentasi presisi tanpa parameter berlebihan, mendasari keputusan penggunaan MobileNetV2 sebagai *backbone* ringan pengganti *encoder* standar U-Net dalam penelitian ini.

**5. Modifikasi DeepLabV3+ dengan MobileNetV2 dan Modul Atensi CBAM:**
Yu et al. (2022) [5] memodifikasi DeepLabV3+ dengan MobileNetV2 dan menambahkan CBAM untuk segmentasi gulma pada kedelai. Modifikasi ini berhasil memangkas parameter secara signifikan sekaligus meningkatkan kepekaan model terhadap morfologi daun gulma, mempertegas relevansi kombinasi MobileNetV2 sebagai *backbone* efisien untuk segmentasi pertanian.

**6. Modernisasi U-Net dengan DCM, CA, dan MSFF:**
Li et al. (2025) [6] memperkuat arsitektur U-Net dengan modul *Dilated Convolution Module* (DCM), *Coordinate Attention* (CA), dan *Multi-Scale Feature Fusion* (MSFF) untuk lahan bit gula, mencapai mIoU 91,02% dan F1-Score 94,13%. Penelitian ini mengkonfirmasi fleksibilitas U-Net sebagai fondasi yang sangat baik untuk berbagai skenario segmentasi pertanian.

**7. Optimasi Citra Digital dan Antarmuka Android:**
Kusuma et al. [10] dan Suarjaya et al. [12] menunjukkan bahwa pemrosesan citra pada perangkat seluler memerlukan optimasi algoritmik ketat untuk menghindari *out of memory*. Ariatmaja et al. [11] dan Wijaya et al. [13] menegaskan pentingnya pemisahan modul pemrosesan dari antarmuka pengguna dan arsitektur responsif untuk kelancaran transisi layar saat AI berjalan secara bersamaan.

### 2.2. Sintesis Celah Penelitian dan Kebaruan

Mayoritas sistem terdahulu memiliki keterbatasan: (1) mengandalkan UAV berbiaya tinggi [1]; (2) menghasilkan klasifikasi *image-level* yang tidak memandu petani secara visual piksel [3]; atau (3) mengeksekusi model di server GPU yang membutuhkan internet stabil [2],[4],[5],[6].

**Kebaruan penelitian ini** terletak pada tiga kontribusi utama: (1) model U-Net + MobileNetV2 dikonversi ke TFLite (21,15 MB) untuk **100% *offline on-device inference***; (2) target domain spesifik cabai 2 MST + 3 golongan gulma divisualisasikan via *overlay mask* transparan; (3) platform edukasi terintegrasi (logbook *Supabase* + artikel *Retrofit REST API*).

### 2.3. Landasan Teori

**A. Tanaman Cabai 2 MST dan Morfologi Gulma:**
Fase 2 MST merupakan "Periode Kritis" di mana perakaran cabai dangkal dan sangat rentan kompetisi. Tiga gulma target: (1) *Eleusine indica* (rumputan, daun pita); (2) *Cyperus rotundus* (teki, batang segitiga); (3) *Amaranthus spinosus* (daun lebar, kemiripan visual tertinggi dengan cabai muda).

**B. Segmentasi Semantik Piksel (*Pixel-Level Semantic Segmentation*):**
Teknik *Computer Vision* yang mengklasifikasikan setiap piksel citra ke kelas kategori tertentu. Citra masukan $512 \times 512 \times 3$ (RGB) diproses menjadi peta prediksi $512 \times 512 \times 1$: **Kelas 0** (Background/Tanah — bening), **Kelas 1** (Tanaman Cabai — Topeng Hijau `#00FF00` opacity 40%), **Kelas 2** (Gulma — Topeng Merah `#FF0000` opacity 50%).

**C. Arsitektur U-Net:**
U-Net [8] terdiri dari jalur kontraksi (*Encoder*) dan jalur ekspansi (*Decoder*) yang dihubungkan oleh **Skip Connections**. Transfer peta fitur resolusi tinggi dari *Encoder* ke *Decoder* yang bersesuaian memungkinkan rekonstruksi batas kontur yang tajam dan presisi—sangat kritis untuk memisahkan daun gulma dan cabai yang berdampingan.

**D. MobileNetV2 sebagai Backbone:**
MobileNetV2 [7] menggunakan **Depthwise Separable Convolution** yang mengurangi FLOPs hingga 8-9x dibanding konvolusi standar, serta **Inverted Residual Blocks** dengan **Linear Bottlenecks**. Efisiensi ini menjadikannya *backbone* ideal untuk perangkat bergerak bersumber daya terbatas.

**E. TensorFlow Lite untuk Inferensi On-Device:**
TFLite adalah *framework* inferensi lintas-platform yang dioptimalkan untuk perangkat *edge*. Model yang dikonversi ke `.tflite` dapat dieksekusi tanpa internet, dengan latensi rendah dan hemat daya baterai.

**F. Metode RAD (*Rapid Application Development*):**
RAD merupakan metodologi pengembangan cepat berbasis prototipe yang terdiri dari tiga tahap: (1) *Requirements Planning*, (2) *User Design & Construction*, dan (3) *Cutover/Testing*, dengan iterasi yang memungkinkan evaluasi pengguna di setiap siklus.

---

## 3. Metodologi Penelitian

### 3.1. Tempat, Waktu, dan Sumber Data Penelitian

Penelitian dilaksanakan mulai Juli 2025 hingga Juli 2026 di laboratorium komputer Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana, Jimbaran, Badung, Bali. Akuisisi dataset citra primer dan pengujian UAT lapangan dilakukan di lahan pertanian cabai Desa Anggabaya, Penatih, Denpasar, Bali.

**Data primer** berupa dataset citra digital yang dikumpulkan mandiri (*self-collected*). Pengambilan citra dilakukan dari ketinggian 30-50 cm di atas tajuk tanaman pada pencahayaan alami pukul 08.00-11.00 WITA. Objek yang diabadikan meliputi: bibit cabai 2 MST, *Eleusine indica*, *Cyperus rotundus*, dan *Amaranthus spinosus*. Seluruh citra dianotasi tingkat piksel (*pixel-level annotation*) secara manual untuk menghasilkan *ground truth mask* pelatihan model.

Metode pengumpulan data mencakup dua pendekatan: (1) **Studi Literatur** — analisis jurnal nasional dan internasional terkait segmentasi citra pertanian dan pengembangan Android; (2) **Observasi Lapangan** — pengambilan citra di lokasi lahan untuk memperoleh keragaman visual objek dan kondisi pencahayaan alami.

### 3.2. Metode Pengembangan Sistem (*Rapid Application Development*)

Pengembangan aplikasi "Gulma Detektor" mengikuti kerangka **RAD** yang terbagi menjadi tiga tahapan sistematis:

**Tahap 1 — *Requirements Planning* (Perencanaan Kebutuhan):**
Identifikasi dan analisis mendalam terhadap permasalahan petani cabai terkait kesulitan mengenali gulma secara visual. Hasil observasi lapangan dan studi literatur digunakan untuk merumuskan kebutuhan fungsional sistem (fitur-fitur deteksi, edukasi, riwayat, autentikasi) dan kebutuhan non-fungsional (performa inferensi < 1 detik, kemudahan penggunaan, portabilitas *offline*). Pada tahap ini juga ditentukan: arsitektur model AI (U-Net + MobileNetV2), platform (Android Studio, Kotlin), teknologi *backend* (Supabase, Retrofit), dan metode validasi (UAT).

**Tahap 2 — *User Design & Construction* (Perancangan dan Pembangunan Sistem):**
Tahap inti yang mencakup tiga aktivitas paralel:
- **(a) Perancangan Antarmuka (UI/UX):** Prototipe visual *wireframe* dan *mockup* dirancang di Figma dengan prinsip *mobile-first*, intuitif, dan mudah dioperasikan petani dengan berbagai tingkat literasi digital.
- **(b) Pengembangan Model AI:** Dataset yang telah dianotasi dilatih melalui eksperimen komparatif berbagai arsitektur (U-Net, DeepLabV3+), *backbone* (MobileNetV2, MobileViT, EfficientViT), dan fungsi kerugian (*Cross-Entropy*, *Tversky Loss*) di Google Colab GPU T4. Model terbaik dikonversi ke TFLite.
- **(c) Pengodean Android:** Seluruh komponen antarmuka diimplementasikan dalam Kotlin + Jetpack Compose. Integrasi TFLite, Supabase, dan Retrofit dilakukan bersamaan.

**Tahap 3 — *Cutover / Testing* (Pengujian dan Implementasi):**
Pengujian kelayakan sistem menggunakan ***User Acceptance Testing*** (UAT). Kuesioner skala Likert via Google Form disebarkan kepada 15 responden (petani, penyuluh, akademisi) untuk mengukur kepuasan, kenyamanan, dan kebermanfaatan aplikasi di lapangan.

### 3.3. Instrumen Perancangan dan Pembuatan Sistem

Seluruh instrumen dipilih secara cermat untuk mengoptimalkan efisiensi komputasi. Rincian lengkap disajikan pada Tabel 1.

Tabel 1  
RINCIAN INSTRUMEN HARDWARE DAN SOFTWARE PENELITIAN

| Perangkat | Jenis | Spesifikasi / Keterangan |
| --- | --- | --- |
| Hardware | Laptop Pengembang | ACER Predator Helios Neo 16 (Intel Core i5-13500HX, RAM 8GB DDR5, GPU NVIDIA RTX 4050 6GB) |
| Hardware | Smartphone Android | Perangkat pengujian inferensi kamera *on-device* di lapangan |
| Software | Sistem Operasi | Windows 11 Home (Lingkungan Pengembangan Lokal) |
| Software | Environment Pelatihan | Google Colab (Python 3.x, TensorFlow/Keras, GPU Tesla T4 VRAM 16GB, RAM 25GB) |
| Software | IDE Pengembangan Android | Android Studio (Kotlin, Jetpack Compose, CameraX, TFLite Interpreter) |
| Software | Desain UI/UX | Figma (Wireframe & Mockup Antarmuka) |
| Software | Backend Cloud | Supabase Cloud (Autentikasi JWT & Basis Data PostgreSQL) |
| Software | Client REST API | Retrofit 2 (Modul Edukasi Artikel & Ensiklopedia Gulma) |
| Software | Mesin AI On-Device | TFLite (`best_model_unet_tversky_float32.tflite`, 21,15 MB, Float32) |

Pemilihan Google Colab krusial karena menyediakan GPU Tesla T4 untuk mengakselerasi pelatihan jaringan saraf tiruan berjuta parameter. Supabase memberikan autentikasi berbasis JWT dan basis data PostgreSQL yang dapat diakses via RESTful API tanpa server mandiri.

### 3.4. Konsep dan Mekanisme Segmentasi Semantik Tingkat Piksel

Inti kerja aplikasi "Gulma Detektor" berpusat pada mekanisme **segmentasi semantik tingkat piksel** yang dieksekusi secara lokal di perangkat seluler. Proses dimulai ketika pengguna mengambil foto lahan cabai. Citra dipra-proses (*resizing* ke $512 \times 512$ piksel, normalisasi piksel ke $[0.0, 1.0]$) sebelum diumpankan ke model. Model U-Net + MobileNetV2 memproses matriks input $[512 \times 512 \times 3]$ dan menghasilkan matriks prediksi kelas $[512 \times 512 \times 3]$ berupa skor probabilitas *softmax* untuk tiga kelas:

- **Kelas 0 — Background:** Tanah, mulsa plastik, bayangan. Tampil bening tanpa lapisan warna.
- **Kelas 1 — Tanaman Cabai:** Helai daun dan batang bibit cabai. Tampil **Topeng Hijau Transparan** (`#00FF00`, opacity 40%).
- **Kelas 2 — Gulma:** Daun dan batang dari tiga jenis gulma. Tampil **Topeng Merah Transparan** (`#FF0000`, opacity 50%).

```
+-----------------------------------------------------------------------------------+
|                        MEKANISME SEGMENTASI SEMANTIK PIKSEL                       |
|                                                                                   |
|  [ CITRA INPUT KAMERA ]      [ INFERENSI TFLITE ENGINE ]     [ TOPENG MASKING ]   |
|     (512x512x3 RGB)           (U-Net + MobileNetV2)          (Visual Overlay)     |
|   +---------------+            +-------------------+         +---------------+    |
|   | Foto Asli     |            | Matriks Prediksi  |         | MERAH=Gulma   |    |
|   | Lahan Cabai   | =========> | Kelas Piksel      | ======> | HIJAU=Cabai   |    |
|   | & Gulma       |            | (Kelas 0, 1, 2)   |         | BENING=Tanah  |    |
|   +---------------+            +-------------------+         +---------------+    |
|  Alur: Resize -> Normalize -> ByteBuffer -> TFLite Interpreter                    |
|        -> Argmax per Pixel -> Color Map -> Alpha Blend -> Overlay Bitmap           |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 1: Alur Pemrosesan Segmentasi Semantik Piksel pada Aplikasi Gulma Detektor]

Secara teknis, kelas `WeedClassifier.kt` menginisialisasi `org.tensorflow.lite.Interpreter` dengan berkas model dari `app/src/main/assets/`. Citra *bitmap* dikonversi ke `ByteBuffer` ($512 \times 512 \times 3 \times 4$ byte, float32). Setelah inferensi, untuk setiap piksel $(x, y)$ nilai *argmax* dari skor probabilitas menentukan kelas. Piksel Kelas 1 dicat hijau dan Kelas 2 dicat merah transparan, lalu di-*blend* ke foto asli via operasi *alpha compositing* pada objek `Canvas` Android.

### 3.5. Perancangan Arsitektur Sistem dan Alur Integrasi Komponen

Arsitektur sistem dirancang menggunakan pola ***three-tier mobile architecture*** yang memprioritaskan pemrosesan AI secara lokal dan mandiri. Tiga lapisan tersebut:

**1. Lapisan Presentasi (*Presentation Layer*):**
Seluruh antarmuka dibangun dengan **Jetpack Compose** (UI deklaratif modern) dan **CameraX API** untuk akses kamera secara konsisten di berbagai model perangkat Android.

**2. Lapisan Aplikasi (*Application Layer*):**
Kelas `WeedClassifier.kt` sebagai inti mesin AI, menjalankan `TFLite Interpreter`, memproses input, dan menghasilkan visualisasi. Logika bisnis dikelola dengan pola MVVM (*Model-View-ViewModel*) menggunakan `ViewModel`.

**3. Lapisan Data (*Data Layer*):**
- **Supabase Cloud:** Autentikasi pengguna (JWT) dan penyimpanan logbook riwayat deteksi lahan ke basis data PostgreSQL via REST API.
- **Retrofit REST API:** Klien HTTP asinkron untuk menarik konten artikel edukasi dan ensiklopedia gulma dari server eksternal.

```
+-----------------------------------------------------------------------------------+
|                          ARSITEKTUR SISTEM APLIKASI                               |
|  +----------------------------+     +------------------------------------------+ |
|  | PRESENTATION LAYER         |     | APPLICATION LAYER (AI Engine)            | |
|  | (Jetpack Compose UI)       |<--> | WeedClassifier.kt                        | |
|  | - Home, Scan, Result,      |     | - TFLite Interpreter (on-device)         | |
|  |   History, Article Screen  |     | - ByteBuffer Pre-processing              | |
|  +----------------------------+     | - Argmax Classification                  | |
|              ^                      | - Overlay Mask Rendering                 | |
|              |                      +------------------------------------------+ |
|              v                                                                    |
|  +-----------------------------------------------------------------------------+ |
|  | DATA LAYER                                                                   | |
|  | [A] Supabase Cloud (JWT Auth & PostgreSQL) -> Register/Login, History       | |
|  | [B] Retrofit REST API -> Article & Encyclopedia Content (HTTP GET)           | |
|  | [C] Local Assets: best_model_unet_tversky_float32.tflite (21.15 MB)          | |
|  +-----------------------------------------------------------------------------+ |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 2: Arsitektur Sistem Aplikasi Gulma Detektor]

### 3.6. Pemodelan UML Sistem

**A. Use Case Diagram:**
Sistem memiliki satu aktor utama (**Pengguna/Petani**) dengan enam *use case* utama:

```
+-----------------------------------------------------------------------------------+
|                          GULMA DETEKTOR SYSTEM                                    |
|  +-----------+    +-----------------------------------------------+               |
|  |           |--->| UC1: Register Akun Pengguna Baru              |               |
|  |           |    +-----------------------------------------------+               |
|  |           |--->| UC2: Login ke Sistem Aplikasi                 |               |
|  |           |    +-----------------------------------------------+               |
|  | Pengguna  |--->| UC3: Deteksi Gulma (Kamera/Galeri)           |               |
|  | (Petani)  |    |   <<include>> Tambah & Simpan Catatan Lahan   |               |
|  |           |    +-----------------------------------------------+               |
|  |           |--->| UC4: Live Detection (Real-time Camera)        |               |
|  |           |    +-----------------------------------------------+               |
|  |           |--->| UC5: Kelola Riwayat (View/Update/Delete)      |               |
|  |           |    +-----------------------------------------------+               |
|  +-----------+--->| UC6: Baca Artikel & Ensiklopedia Gulma        |               |
|                   +-----------------------------------------------+               |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 3: Use Case Diagram Pengguna Aplikasi Gulma Detektor]

**B. Activity Diagram — Alur Deteksi Gulma (UC3):**
Pengguna membuka layar kamera -> Memilih sumber citra (kamera/galeri) -> Mengambil foto dan melakukan *crop* area fokus -> Citra dikirim ke `WeedClassifier.kt` -> Inferensi TFLite menghasilkan matriks prediksi kelas -> Sistem merender *overlay mask* berwarna -> Menampilkan label klasifikasi dan rekomendasi penanganan -> Pengguna menambah catatan lahan -> Menyimpan logbook ke *Supabase Cloud*.

**C. Activity Diagram — Alur Autentikasi Pengguna:**
*Register*: Isi formulir (nama, email, password) -> Validasi sisi klien -> Kirim ke Supabase Auth API -> Akun tersimpan di database -> Diarahkan ke layar Login.  
*Login*: Masukkan email + password -> Supabase Auth validasi kredensial -> Jika valid, token JWT dibuat dan pengguna masuk ke Home; jika tidak valid, pesan kesalahan tampil.

### 3.7. Pemilihan dan Konversi Model AI pada Perangkat Mobile

Tahap pengembangan model melibatkan eksperimen komparatif sistematis untuk menemukan model paling optimal. Arsitektur yang diuji: **U-Net** dan **DeepLabV3+**. Backbone yang diuji: **MobileNetV2** (CNN ringan), **MobileViT** (CNN + Vision Transformer hibrida), **EfficientViT** (Lightweight ViT). Fungsi kerugian yang diuji: **Cross-Entropy** (standar multi-kelas) dan **Tversky Loss + Cross-Entropy** (adaptif untuk *class imbalance*).

Model terbaik hasil eksperimen adalah **U-Net + MobileNetV2 dengan Tversky Loss + Cross-Entropy**, kemudian dikonversi melalui jalur multi-tahap:
$$\text{PyTorch (.pth)} \longrightarrow \text{ONNX (.onnx)} \longrightarrow \text{TF SavedModel} \longrightarrow \text{TFLite (.tflite)}$$

Berkas `best_model_unet_tversky_float32.tflite` berukuran **21,15 MB** (Float32) ditempatkan di `app/src/main/assets/` dan dieksekusi oleh `WeedClassifier.kt` menggunakan `org.tensorflow.lite.Interpreter`.

### 3.8. Rancangan Pengujian *User Acceptance Testing* (UAT)

UAT mengukur kelayakan aplikasi dari perspektif pengguna akhir menggunakan **Skala Likert 5 Tingkat** (1=Sangat Tidak Setuju, 5=Sangat Setuju) melalui kuesioner Google Form. Instrumen terdiri dari **18 skenario pertanyaan** dalam **4 modul evaluasi**: (1) Tampilan & Navigasi UI/UX, (2) Otentikasi Akun, (3) Fitur Utama Deteksi Gulma & Live Mode, dan (4) Fitur Pendukung. Melibatkan **15 responden** dari segmen petani, penyuluh, dan akademisi.

Rumus persentase kelayakan:
$$\text{Persentase Kelayakan (\%)} = \frac{\text{Total Poin Diperoleh}}{N \times 5} \times 100\%$$

Skor maksimal per skenario: $15 \times 5 = 75$ Poin. Skor maksimal keseluruhan: $18 \times 75 = 1.350$ Poin.

Kriteria interpretasi: persentase **81%-100% = Sangat Layak (Diterima)**.

---

## 4. Hasil dan Pembahasan

### 4.1. Hasil Implementasi Model Segmentasi Semantik

Berdasarkan serangkaian eksperimen komparatif di Google Colab (GPU Tesla T4), model **U-Net + MobileNetV2 dengan Tversky Loss + Cross-Entropy** dipilih sebagai model terbaik berdasarkan keseimbangan optimal antara akurasi segmentasi, sensitivitas deteksi gulma, dan efisiensi ukuran model untuk *deployment mobile*.

Tabel 2  
HASIL EVALUASI MODEL U-NET + MOBILENETV2 (TVERSKY LOSS + CROSS-ENTROPY)

| Kelas | IoU | Presisi | Recall | F1-Score |
| --- | --- | --- | --- | --- |
| Background (Tanah) | 0,9589 | 0,9940 | 0,9645 | 0,9790 |
| Tanaman Cabai | 0,8533 | 0,8778 | 0,9683 | 0,9208 |
| Gulma | 0,6397 | 0,6754 | **0,9238** | 0,7803 |
| **Global Accuracy** | | | **0,9620** | |
| **Mean IoU (mIoU)** | | | **0,8173** | |
| **Ukuran Model .tflite** | | | **21,15 MB** | |

Model mencapai *Global Accuracy* **96,20%** dan *Mean IoU* **81,73%**. Nilai *Recall* Gulma **92,38%** sangat krusial secara praktis: memastikan hampir seluruh area gulma berhasil terdeteksi dan ditandai merah untuk petani, meminimalisir risiko *false negative* (gulma yang tidak terdeteksi dan dibiarkan tumbuh). Penerapan *Tversky Loss* secara efektif mengatasi ketimpangan kelas (*class imbalance*) antara piksel tanah yang mendominasi (>90% area gambar) dengan piksel gulma yang merupakan minoritas.

### 4.2. Hasil Implementasi Antarmuka Pengguna

Antarmuka aplikasi "Gulma Detektor" diimplementasikan sepenuhnya menggunakan Kotlin dan Jetpack Compose, menghasilkan **5 modul layar utama** yang saling terintegrasi via *bottom navigation bar*:

#### 4.2.1 Halaman Utama (*Home Screen*)

Halaman Utama berfungsi sebagai pusat informasi dan titik awal navigasi. Elemen-elemen kunci yang dirancang:
- **Header sapaan personal** berdasarkan nama akun aktif, menciptakan kesan personal.
- **Banner edukasi** mengenai periode kritis gulma 2 MST yang menonjol secara visual sebagai pengingat urgensi deteksi dini.
- **Dua tombol aksi cepat** berukuran besar: "Scan Kamera Deteksi" dan "Ambil dari Galeri", memudahkan akses fitur inti dalam satu ketukan.
- **Ensiklopedia botani gulma** berupa tiga kartu informasi dengan gambar dan deskripsi singkat tiga golongan gulma target.

```
+-----------------------------------------------------------------------------------+
|                            HALAMAN UTAMA (HOME SCREEN)                            |
|  Selamat datang, [Nama Petani]!                             [Ikon Profil]          |
|  +-----------------------------------------------------------------------------+  |
|  | BANNER: "Kenali & Basmi Gulma di Periode Kritis 2 MST Lahan Cabai Anda!"   |  |
|  +-----------------------------------------------------------------------------+  |
|  [TOMBOL: SCAN KAMERA DETEKSI]          [TOMBOL: AMBIL DARI GALERI]               |
|  Ensiklopedia Gulma:                                                              |
|  [Kard: Rumputan]    [Kard: Teki-tekian]    [Kard: Daun Lebar]                   |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 4: Halaman Utama (*Home Screen*) Aplikasi Gulma Detektor]

#### 4.2.2 Halaman Pemindaian Kamera & Pemotongan Citra (*Scan Screen*)

Memanfaatkan **CameraX API** untuk *live preview* real-time. Dilengkapi:
- **Bingkai *crop* 1:1** yang dapat digeser untuk memfokuskan area lahan yang ingin dianalisis.
- **Tombol *Switch Camera*** untuk beralih antara kamera belakang (memotret lahan) dan kamera depan.
- **Pengatur Mode Flash** (Otomatis/Aktif/Nonaktif) untuk kondisi pencahayaan bervariasi.
- **Akses Galeri** untuk menganalisis foto lahan yang telah diambil sebelumnya.

Setelah foto diambil, layar pemotongan (*crop*) terbuka untuk presisi area fokus sebelum dianalisis AI.

```
+-----------------------------------------------------------------------------------+
|                HALAMAN PEMINDAIAN KAMERA & PEMOTONGAN CITRA                       |
|  [Kembali]          [Judul: Pemindaian Lahan]           [Flash: Off/On/Auto]      |
|  +---[Preview Kamera Real-time: Area Lahan Cabai & Gulma Terlihat]-------------+  |
|  |                +-------------------------------+                             |  |
|  |                | Bingkai Crop 1:1              |                             |  |
|  |                | (Arahkan ke area tanaman)     |                             |  |
|  |                +-------------------------------+                             |  |
|  +-----------------------------------------------------------------------------+  |
|  [Ganti Kamera]          [ SHUTTER: Ambil Foto ]          [Pilih Galeri]          |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 5: Halaman Pemindaian Kamera dan Layar Pemotongan Citra]

#### 4.2.3 Halaman Hasil Deteksi & Visualisasi *Overlay Masking*

Merupakan inti inovasi aplikasi. Setelah *crop* dikonfirmasi, `WeedClassifier.kt` memproses citra via TFLite dalam **< 1 detik** dan menampilkan:

- **Citra dengan Overlay Mask Transparan:** Daun cabai dilapisi **topeng Hijau** (`#00FF00`, opacity 40%); daun gulma dilapisi **topeng Merah** (`#FF0000`, opacity 50%); tanah/mulsa dibiarkan bening. Topeng mengikuti kontur piksel secara presisi.
- **Label Diagnosis:** Teks status jelas, contoh: *"Terdeteksi Gulma Daun Lebar (Amaranthus spinosus)"* atau *"Kondisi Lahan Bersih – Tanaman Cabai Sehat"*.
- **Rekomendasi Penanganan Agronomi:** Panduan tindakan kontekstual, contoh: *"Lakukan penyiangan manual secara hati-hati pada area berpigmen Merah."*
- **Formulir Catatan Lahan:** Input teks untuk mencatat nama blok lahan, kondisi, atau tindakan yang diambil.
- **Tombol Simpan ke Riwayat:** Menyimpan foto overlay, label, dan catatan ke Supabase Cloud.

```
+-----------------------------------------------------------------------------------+
|              HALAMAN HASIL DETEKSI & VISUALISASI OVERLAY MASKING                  |
|  +-----------------------------------------------------------------------------+  |
|  | FOTO ASLI + OVERLAY MASK TRANSPARAN:                                        |  |
|  |   Daun Cabai  : ████ Topeng HIJAU (#00FF00, 40% opacity)                    |  |
|  |   Daun Gulma  : ████ Topeng MERAH (#FF0000, 50% opacity)                    |  |
|  |   Tanah/Mulsa : [Bening - Foto Asli Terlihat Jelas]                         |  |
|  +-----------------------------------------------------------------------------+  |
|  Status: "Terdeteksi Gulma Daun Lebar (Amaranthus spinosus)"                      |
|  Saran : "Cabut area berpigmen MERAH dengan hati-hati."                           |
|  [ Input Catatan: "Lahan Blok A2 - 15 Desember 2025" ]                           |
|  [ SIMPAN HASIL KE RIWAYAT SUPABASE ]                                             |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 6: Halaman Hasil Deteksi dengan Visualisasi Overlay Masking (Merah=Gulma, Hijau=Cabai)]

#### 4.2.4 Halaman Riwayat Pemindaian (*History Screen*)

Logbook digital lahan yang mencatat seluruh rekam jejak pemindaian di Supabase Cloud. Fitur:
- **Daftar kartu riwayat kronologis** memuat: *thumbnail* overlay, label hasil, stempel waktu, dan teks catatan.
- **Detail riwayat** lengkap saat kartu diketuk.
- **Fungsi Update** untuk mengedit catatan lahan.
- **Fungsi Delete** untuk menghapus satu item atau "Hapus Semua Riwayat".

```
+-----------------------------------------------------------------------------------+
|                      HALAMAN RIWAYAT PEMINDAIAN (HISTORY)                         |
|  [ Judul: Logbook Riwayat Lahan ]                  [ Tombol: Hapus Semua ]        |
|  +-----------------------------------------------------------------------------+  |
|  | [Thumbnail] Lahan Blok A2 | 15 Des 2025, 09:30 | Status: Gulma Terdeteksi  |  |
|  |             Catatan: "Penyiangan selesai" [Detail] [Edit] [Hapus]           |  |
|  +-----------------------------------------------------------------------------+  |
|  +-----------------------------------------------------------------------------+  |
|  | [Thumbnail] Bedengan B1 | 14 Des 2025, 16:15 | Status: Cabai Sehat          |  |
|  |             Catatan: "Kondisi bersih"      [Detail] [Edit] [Hapus]          |  |
|  +-----------------------------------------------------------------------------+  |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 7: Halaman Riwayat Pemindaian (*History Screen*) — Logbook Digital Lahan]

#### 4.2.5 Halaman Pusat Edukasi & Artikel (*Article Screen*)

Platform edukasi agronomi digital yang menarik konten secara dinamis via **Retrofit REST API**. Dua tab konten:
- **Tab Artikel Edukasi:** Artikel tentang manajemen penyiangan, pengenalan gulma, dan teknik pengendalian opt.
- **Tab Ensiklopedia Gulma:** Katalog botani tiga golongan gulma target beserta morfologi, siklus hidup, dan rekomendasi pengendalian.

```
+-----------------------------------------------------------------------------------+
|                        HALAMAN PUSAT EDUKASI & ARTIKEL                            |
|  [ Tab: Artikel Edukasi ]              [ Tab: Ensiklopedia Gulma ]                |
|  +-----------------------------------------------------------------------------+  |
|  | [Gambar] "Manajemen Penyiangan Gulma Presisi pada Cabai 2 MST"              |  |
|  | Penyuluh Pertanian | [Baca Selengkapnya]                                     |  |
|  +-----------------------------------------------------------------------------+  |
|  +-----------------------------------------------------------------------------+  |
|  | [Gambar] "Mengenal Bayam Duri dan Cara Basminya"  [Baca Selengkapnya]        |  |
|  +-----------------------------------------------------------------------------+  |
+-----------------------------------------------------------------------------------+
```
[GAMBAR 8: Halaman Pusat Edukasi dan Artikel Pertanian]

### 4.3. Hasil Pengujian *User Acceptance Testing* (UAT)

Pengujian UAT dilakukan sebagai tahap validasi final untuk mengkonfirmasi secara empiris bahwa sistem memenuhi kebutuhan pengguna sasaran di lapangan. Kuesioner Google Form berisi **18 skenario** dalam **4 modul**, disebarkan kepada **15 responden**.

#### 4.3.1 Profil Demografi Responden Penguji

Tabel 3  
PROFIL DEMOGRAFI RESPONDEN PENGUJI UAT

| No | Kategori Profesi / Latar Belakang | Jumlah Responden (N) | Persentase (%) |
| --- | --- | --- | --- |
| 1 | Petani Cabai / Praktisi Pertanian | 6 Orang | 40,0% |
| 2 | Penyuluh Pertanian / Instansi Terkait | 4 Orang | 26,7% |
| 3 | Mahasiswa / Pelajar / Akademisi Pertanian | 3 Orang | 20,0% |
| 4 | Masyarakat Umum / Hobi Berkebun | 2 Orang | 13,3% |
| **TOTAL** | **KESELURUHAN RESPONDEN** | **15 Orang** | **100,0%** |

Komposisi diprioritaskan pada Petani (40,0%) dan Penyuluh Pertanian (26,7%) sebagai pengguna primer dan pendukung utama. Keterlibatan penyuluh sangat penting karena mereka memiliki kompetensi teknis agronomi untuk menilai kualitas rekomendasi penanganan gulma aplikasi dari perspektif keahlian.

#### 4.3.2 Metode Perhitungan Skala Likert

Dengan $N = 15$ responden: skor maksimal per skenario = $15 \times 5 = 75$ Poin; skor maksimal keseluruhan = $18 \times 75 = 1.350$ Poin.

#### 4.3.3 Hasil Pengujian Modul Tampilan & Navigasi (UI/UX)

Tabel 4  
HASIL PENGUJIAN UAT MODUL TAMPILAN & NAVIGASI (UI/UX)

| No | Skenario Pertanyaan | N | Total Poin | Rata-rata | Persentase | Status |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Tampilan UI aplikasi terlihat modern, menarik, dan rapi | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 2 | Teks, tombol, dan gambar jelas serta mudah terbaca | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 3 | Menu navigasi bawah (Home, Artikel, Deteksi, Riwayat, Profil) mudah dipahami | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 4 | Perpindahan antar halaman mulus, cepat, dan tidak membingungkan | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| **SUBTOTAL** | **MODUL UI/UX** | **15** | **285 / 300** | **4,75** | **95,00%** | **Sangat Layak** |

Modul UI/UX meraih **285/300 poin (95,00%, rata-rata 4,75)**. Keterbacaan teks dan kejelasan menu navigasi mendapat skor sempurna bersama (72/75), mengindikasikan desain antarmuka berhasil mengakomodasi pengguna dari berbagai tingkat familiaritas dengan *smartphone*. Petani senior menyatakan ikon dan label menu navigasi sangat intuitif dan tidak memerlukan penjelasan awal.

#### 4.3.4 Hasil Pengujian Modul Otentikasi Akun (Login & Register)

Tabel 5  
HASIL PENGUJIAN UAT MODUL OTENTIKASI AKUN (LOGIN & REGISTER)

| No | Skenario Pertanyaan | N | Total Poin | Rata-rata | Persentase | Status |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Proses *Register* akun baru mudah dilakukan | 15 | 68 / 75 | 4,53 | 90,67% | Diterima |
| 2 | Proses *Login* ke aplikasi lancar tanpa masalah | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 3 | Fitur ikon "Mata" (*Show/Hide Password*) sangat membantu | 15 | 73 / 75 | 4,87 | 97,33% | Diterima |
| 4 | Pesan *Alert* mudah dimengerti saat salah input email/password | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| **SUBTOTAL** | **MODUL OTENTIKASI** | **15** | **281 / 300** | **4,68** | **93,67%** | **Sangat Layak** |

Modul otentikasi meraih **281/300 poin (93,67%, rata-rata 4,68)**. Fitur *Show/Hide Password* mendapat skor tertinggi keseluruhan (73/75, 97,33%)—mengindikasikan fitur sederhana ini sangat diapresiasi petani yang sering keliru mengetik kata sandi. Skenario *Register* mendapat skor sedikit lebih rendah (90,67%), menunjukkan potensi penyederhanaan alur pendaftaran pada iterasi berikutnya.

#### 4.3.5 Hasil Pengujian Modul Fitur Utama Deteksi Gulma & *Live Mode*

Tabel 6  
HASIL PENGUJIAN UAT FITUR UTAMA DETEKSI GULMA & LIVE MODE

| No | Skenario Pertanyaan | N | Total Poin | Rata-rata | Persentase | Status |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Fitur *Crop* sehabis memotret sangat mudah digunakan | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 2 | Kecepatan memproses dan memunculkan hasil deteksi sangat cepat | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 3 | Hasil deteksi (*overlay mask* & label) akurat sesuai objek aslinya | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 4 | Panduan & saran penanganan gulma lengkap dan mudah dipahami | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 5 | Mode *Live Detection* memunculkan warna (Hijau/Merah) secara *real-time* | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 6 | Topeng warna transparan membantu identifikasi posisi gulma di antara tanaman | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| **SUBTOTAL** | **MODUL FITUR UTAMA DETEKSI** | **15** | **425 / 450** | **4,72** | **94,44%** | **Sangat Layak** |

Modul fitur utama meraih **425/450 poin (94,44%, rata-rata 4,72)**. Skenario "panduan penanganan gulma" dan "efektivitas topeng warna transparan" keduanya meraih 72/75 (96,00%). Penyuluh pertanian memberikan testimoni bahwa visualisasi topeng merah secara signifikan membantu petani yang belum yakin membedakan gulma dari cabai muda untuk mengambil keputusan penyiangan lebih percaya diri dan akurat.

#### 4.3.6 Hasil Pengujian Modul Fitur Pendukung (Artikel & Riwayat)

Tabel 7  
HASIL PENGUJIAN UAT MODUL FITUR PENDUKUNG (ARTIKEL & RIWAYAT)

| No | Skenario Pertanyaan | N | Total Poin | Rata-rata | Persentase | Status |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Artikel edukasi pertanian beragam, menarik, dan bermanfaat | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 2 | Hasil deteksi sebelumnya tersimpan rapi di halaman "Riwayat" | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 3 | Detail hasil deteksi lama dapat dibuka kembali dengan lancar | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 4 | Fitur "Hapus Semua Riwayat" berfungsi baik saat membersihkan data | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| **SUBTOTAL** | **MODUL FITUR PENDUKUNG** | **15** | **284 / 300** | **4,73** | **94,67%** | **Sangat Layak** |

Modul pendukung meraih **284/300 poin (94,67%, rata-rata 4,73)**. Artikel edukasi mendapat skor tertinggi (96,00%), membuktikan konten agronomi sangat dihargai. Temuan ini mengkonfirmasi bahwa petani tidak hanya membutuhkan alat deteksi, tetapi juga sumber pengetahuan mudah diakses untuk meningkatkan literasi pengelolaan lahan secara mandiri.

#### 4.3.7 Rincian Poin Seluruh 18 Skenario UAT

Tabel 8  
RINCIAN TOTAL POIN SELURUH SKENARIO UAT (SKENARIO 1 - 18)

| No | Modul | Deskripsi Skenario Pertanyaan | N | Total Poin | Rata-rata | Persentase | Status |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | UI/UX | Tampilan UI modern, menarik, dan rapi | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 2 | UI/UX | Teks, tombol, gambar jelas dan mudah terbaca | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 3 | UI/UX | Menu navigasi bawah mudah dipahami posisinya | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 4 | UI/UX | Perpindahan antar halaman mulus dan cepat | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 5 | Otentikasi | Proses *Register* akun baru mudah dilakukan | 15 | 68 / 75 | 4,53 | 90,67% | Diterima |
| 6 | Otentikasi | Proses *Login* ke aplikasi lancar tanpa masalah | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 7 | Otentikasi | Fitur ikon "Mata" (*Show/Hide Password*) sangat membantu | 15 | 73 / 75 | 4,87 | 97,33% | Diterima |
| 8 | Otentikasi | Pesan *Alert* mudah dimengerti saat salah input | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 9 | Fitur Utama | Fitur *Crop* sehabis memotret mudah digunakan | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 10 | Fitur Utama | Kecepatan memproses hasil deteksi sangat cepat | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 11 | Fitur Utama | Hasil deteksi (*overlay mask* & label) akurat | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 12 | Fitur Utama | Panduan & saran penanganan gulma lengkap | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 13 | Fitur Utama | *Live Detection* memunculkan warna secara *real-time* | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 14 | Fitur Utama | Topeng warna membantu identifikasi posisi gulma | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 15 | Pendukung | Artikel edukasi pertanian beragam dan bermanfaat | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 16 | Pendukung | Hasil deteksi tersimpan rapi di halaman "Riwayat" | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 17 | Pendukung | Detail hasil deteksi lama dapat dibuka dengan lancar | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 18 | Pendukung | "Hapus Semua Riwayat" berfungsi baik | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |

#### 4.3.8 Rekapitulasi Final dan Kesimpulan UAT

Tabel 9  
REKAPITULASI FINAL HASIL PENGUJIAN USER ACCEPTANCE TESTING (UAT)

| No | Modul Aspek yang Diuji | Jumlah Skenario | Total Poin | Skor Rata-rata | Persentase | Status Kelayakan |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Tampilan & Navigasi (UI/UX) | 4 | 285 / 300 | 4,75 | 95,00% | Sangat Layak (Diterima) |
| 2 | Otentikasi Akun (Login & Register) | 4 | 281 / 300 | 4,68 | 93,67% | Sangat Layak (Diterima) |
| 3 | Fitur Utama Deteksi Gulma & Live Mode | 6 | 425 / 450 | 4,72 | 94,44% | Sangat Layak (Diterima) |
| 4 | Fitur Pendukung (Artikel & Riwayat) | 4 | 284 / 300 | 4,73 | 94,67% | Sangat Layak (Diterima) |
| **TOTAL** | **RATA-RATA KESELURUHAN** | **18** | **1.275 / 1.350** | **4,72** | **94,44%** | **Sangat Layak (*Accepted*)** |

Secara akumulatif, aplikasi "Gulma Detektor" meraih total **1.275 poin dari maksimal 1.350 poin** (skor rata-rata **4,72 dari 5,00 / 94,44%**). Berdasarkan kriteria interpretasi kelayakan (81%-100% = Sangat Layak), disimpulkan aplikasi dinyatakan **Sangat Layak dan Diterima (*Accepted*)** oleh pengguna akhir.

### 4.4. Pembahasan Akademik Komprehensif

**Pertama — Validasi Pendekatan Segmentasi Semantik On-Device:**
Kelayakan UAT 94,44% secara empiris memvalidasi bahwa model U-Net + MobileNetV2 TFLite (21,15 MB) mampu dieksekusi efektif dan cepat (< 1 detik) di perangkat Android standar tanpa GPU server atau internet. Pendekatan *offline on-device inference* mengatasi hambatan infrastruktur utama di wilayah pertanian pedesaan dengan akses internet terbatas.

**Kedua — Superioritas Visualisasi *Overlay Mask* sebagai Antarmuka Keputusan Pertanian:**
Skor tinggi pada skenario "efektivitas topeng warna transparan" (96,00%) mengkonfirmasi bahwa visualisasi *overlay mask* piksel-per-piksel (Merah=Gulma, Hijau=Cabai) jauh lebih efektif dibanding label teks saja. Petani dengan literasi botani terbatas dapat secara intuitif mengikuti panduan visual warna merah untuk menentukan area penyiangan yang tepat, meminimalisir risiko *human error* secara signifikan.

**Ketiga — Nilai Tambah Platform Edukasi Terintegrasi:**
Skor tinggi artikel edukasi (96,00%) membuktikan fitur edukasi bukan sekadar pelengkap, melainkan komponen bernilai tinggi yang menciptakan **ekosistem pembelajaran berkelanjutan** bagi petani dalam satu aplikasi yang sama dengan fitur deteksi.

**Keempat — Efektivitas Tversky Loss untuk Pertanian:**
*Recall* Gulma 92,38% memastikan hampir seluruh area gulma teridentifikasi, langsung meminimalisir *false negative*. Ukuran model 21,15 MB yang sangat kompak dibanding model segmentasi GPU-dependent (150-300 MB) membuktikan efektivitas strategi TFLite Float32 untuk *mobile deployment*.

---

## 5. Simpulan

Penelitian ini berhasil merancang dan membangun aplikasi mobile Android **"Gulma Detektor"** (*WeedGuard*) yang mengintegrasikan kemampuan segmentasi semantik berbasis *Computer Vision* untuk mendeteksi gulma pada lahan cabai secara presisi dan aksesibel. Sistem menggunakan model **U-Net dengan *backbone* MobileNetV2** yang dilatih menggunakan kombinasi *Tversky Loss* dan *Cross-Entropy*, menghasilkan performa *Global Accuracy* 96,20%, *Mean IoU* 81,73%, dan *Recall* Gulma 92,38%. Model dikonversi ke *TensorFlow Lite* berukuran **21,15 MB** untuk eksekusi lokal 100% *offline on-device* tanpa ketergantungan internet.

Aplikasi dibangun dengan Android Studio, Kotlin, dan Jetpack Compose, terdiri dari 5 modul antarmuka terintegrasi: Halaman Utama, Pemindaian Kamera & Galeri, Hasil Deteksi dengan Visualisasi *Overlay Mask*, Riwayat Pemindaian (logbook *Supabase Cloud*), dan Pusat Edukasi Artikel (*Retrofit REST API*).

Hasil pengujian penerimaan pengguna akhir (UAT) yang melibatkan 15 responden (petani, penyuluh, dan akademisi) pada 18 skenario kuesioner skala Likert meraih persentase akumulatif **94,44% (skor rata-rata 4,72 dari 5,00)** dengan kategori **Sangat Layak (*Accepted*)**. Seluruh 4 modul evaluasi memperoleh nilai di atas ambang 81%, membuktikan aplikasi efektif membantu petani meminimalisir risiko kesalahan pencabutan bibit cabai pada periode kritis 2 MST sekaligus meningkatkan literasi agronomi melalui fitur edukasi digital yang terintegrasi.

---

## Ucapan Terima Kasih

Ucapan terima kasih disampaikan kepada Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana atas dukungan fasilitas penelitian, serta kepada kelompok petani cabai dan penyuluh pertanian di Desa Anggabaya, Penatih, Denpasar, Bali yang telah berpartisipasi aktif dalam pengujian lapangan.

---

## Daftar Referensi

[1] N. Islam, M. M. Rashid, S. Wibowo, C. Y. Xu, A. Morshed, S. A. Wasimi, S. Moore, and S. M. Rahman, "Early weed detection using image processing and machine learning techniques in an Australian chili farm," *Computers and Electronics in Agriculture*, vol. 188, p. 106030, 2021. DOI: 10.1016/j.compag.2021.106030.
[2] L. Hashemi-Beni, A. A. Gebrehiwot, and A. Karimoddini, "Weed segmentation in organic carrot farms using deep learning: A comparison of U-Net and FCN-8s," *Remote Sensing Applications: Society and Environment*, vol. 20, p. 100416, 2020. DOI: 10.1016/j.rsase.2020.100416.
[3] R. R., S. Kumar, and T. Prabakar, "Comparative analysis of CNN architectures for chili and weed image classification using transfer learning," *Journal of Agricultural Technology*, vol. 15, no. 2, pp. 112-124, 2025.
[4] A. Khan, M. Ilyas, and M. Umraiz, "CED-Net: Cascaded encoder-decoder network for weed segmentation in precision agriculture," *Computers and Electronics in Agriculture*, vol. 175, p. 105566, 2020. DOI: 10.1016/j.compag.2020.105566.
[5] J. Yu, W. Zhang, and Y. Xu, "A DeepLabV3+ model with MobileNetV2 backbone and CBAM attention for weed segmentation in soybean fields," *Plants*, vol. 11, no. 17, p. 2288, 2022. DOI: 10.3390/plants11172288.
[6] Y. Li, X. Wang, and H. Zhang, "An enhanced U-Net with dilated convolution, coordinate attention, and multi-scale feature fusion for weed segmentation in sugar beet fields," *Computers and Electronics in Agriculture*, vol. 228, p. 109670, 2025. DOI: 10.1016/j.compag.2024.109670.
[7] M. Sandler, A. Howard, M. Zhu, A. Zhmoginov, and L. C. Chen, "MobileNetV2: Inverted residuals and linear bottlenecks," in *Proc. IEEE/CVF CVPR*, 2018, pp. 4510-4520.
[8] O. Ronneberger, P. Fischer, and T. Brox, "U-Net: Convolutional networks for biomedical image segmentation," in *Proc. MICCAI*, 2015, pp. 234-241.
[9] L. C. Chen, Y. Zhu, G. Papandreou, F. Schroff, and H. Adam, "Encoder-decoder with atrous separable convolution for semantic image segmentation," in *Proc. ECCV*, 2018, pp. 801-818.
[10] I. W. A. Kusuma, I. M. O. Widyantara, and I. P. A. Bayupati, "Penerapan pemrosesan citra digital dan ekstraksi fitur visual pada platform Android," *Jurnal Ilmiah Merpati*, vol. 6, no. 1, pp. 55-66, 2018.
[11] I. G. A. Ariatmaja, I. W. A. Kusuma, and I. M. Sukarsa, "Rancang bangun aplikasi Android berkinerja tinggi berbasis antarmuka responsif," *Jurnal Ilmiah Merpati*, vol. 2, no. 1, pp. 45-54, 2014.
[12] I. M. A. D. Suarjaya and A. A. K. A. C. Wiranatha, "Analisis perbandingan metode deteksi tepi dan segmentasi pada pengolahan citra digital," *Jurnal Ilmiah Merpati*, vol. 5, no. 2, pp. 77-88, 2017.
[13] I. G. P. S. Wijaya and I. K. A. Purnawan, "Sistem monitoring dan antarmuka kontrol tanaman berbasis platform Android," *Jurnal Ilmiah Merpati*, vol. 7, no. 2, pp. 112-123, 2019.

---

## Panduan Gambar yang Diperlukan

1. **[GAMBAR 1]** Alur Pemrosesan Segmentasi Semantik Piksel *(dari diagram BAB II/III Skripsi)*
2. **[GAMBAR 2]** Arsitektur Sistem Aplikasi Gulma Detektor *(Gambar 3.2 BAB III)*
3. **[GAMBAR 3]** Use Case Diagram Pengguna *(Gambar 3.3 BAB III)*
4. **[GAMBAR 4]** Screenshot Halaman Utama (*Home Screen*)
5. **[GAMBAR 5]** Screenshot Halaman Pemindaian Kamera & Layar Crop
6. **[GAMBAR 6]** Screenshot Halaman Hasil Deteksi dengan Overlay Masking
7. **[GAMBAR 7]** Screenshot Halaman Riwayat Pemindaian (*History Screen*)
8. **[GAMBAR 8]** Screenshot Halaman Pusat Edukasi & Artikel
