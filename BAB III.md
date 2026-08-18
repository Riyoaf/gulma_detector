# BAB III

METODOLOGI PENELITIAN

Bab III membahas mengenai metode pelaksanaan penelitian yang terdiri dari tempat dan waktu penelitian, alur perancangan, sumber data dan metode pengumpulan data, instrumen perancangan dan pembuatan sistem aplikasi, serta desain arsitektur perancangan sistem.

## Tempat dan Waktu Penelitian

Penelitian Rancang Bangun Aplikasi Deteksi Dini Gulma pada Lahan Cabai Berbasis Android dilaksanakan mulai bulan Juli 2025 sampai dengan bulan Juli 2026. Penelitian berlokasi di Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana, Jalan Raya Kampus Udayana, Jimbaran, Kecamatan Kuta Selatan, Kabupaten Badung, Bali.

## Data Penelitian

Data penelitian merupakan landasan utama dalam melatih dan menguji sistem Deteksi Dini Gulma pada Lahan Cabai. Penjelasan rinci mengenai sumber data dan metode pengumpulan data dipaparkan sebagai berikut.

### Sumber Data

Sumber data dalam penelitian ini merupakan komponen primer yang menjadi masukan utama pelatihan model *Deep Learning*. Data yang digunakan diperoleh melalui akuisisi citra langsung di lapangan serta studi literatur yang mencakup jurnal ilmiah, buku, dan dataset relevan.

- **Data Primer:** Dataset citra digital (*image dataset*) yang dikumpulkan secara mandiri (*self-collected*) melalui pengambilan foto di lahan pertanian cabai. Objek utama yang diambil adalah tanaman cabai pada fase pertumbuhan vegetatif kritis (2 minggu setelah tanam). Objek fokus lainnya adalah berbagai jenis gulma dominan yang tumbuh di sekitar tanaman cabai, meliputi golongan rumputan, teki-tekian, dan gulma daun lebar.

### Metode Pengumpulan Data

Metode pengumpulan data dilakukan melalui dua pendekatan utama:

- **Studi Literatur:** Mengumpulkan, membaca, dan menganalisis berbagai referensi ilmiah seperti jurnal nasional dan internasional, buku teks, serta dokumentasi teknis yang berkaitan dengan segmentasi citra pertanian, *Deep Learning*, dan pengembangan aplikasi Android.
- **Observasi Lapangan:** Mengambil citra digital secara langsung di lokasi lahan pertanian cabai di Desa Anggabaya, Penatih, Denpasar, Bali. Pengambilan citra bertujuan mengamati kondisi riil tanaman, pencahayaan alami, serta keberagaman latar belakang (*background*) berupa tanah dan mulsa plastik.

## Instrumen Perancangan dan Pembuatan Sistem

Instrumen perancangan dan pembuatan sistem dikelompokkan menjadi dua kategori: kebutuhan perangkat keras (*hardware*) dan kebutuhan perangkat lunak (*software*). Spesifikasi rinci instrumen yang digunakan disajikan pada Tabel 3.1:

**Tabel 3.1** Rincian Hardware dan Software Penelitian

| **Perangkat** | **Jenis** | **Spesifikasi / Keterangan** |
| --- | --- | --- |
| Hardware | Laptop ACER Predator Helios Neo 16 | Processor Intel® Core™ i5-13500HX, RAM 8GB DDR5, GPU NVIDIA® GeForce RTX™ 4050 |
| Hardware | Smartphone Android | Perangkat pengujian inferensi kamera *on-device* |
| Software | Sistem Operasi | Windows 11 Home |
| Software | Environment Pelatihan | Google Colab (Python 3, TensorFlow/Keras, GPU Tesla T4) |
| Software | Code Editor IDE | Android Studio (Kotlin, Jetpack Compose, TFLite Interpreter) |
| Software | Desain UI/UX | Figma |
| Software | Backend Cloud | Supabase (Cloud Auth & Postgrest Database) |

## Perancangan Penelitian

Penelitian ini mengikuti alur kerja sistematis untuk menjamin kelancaran pelaksanaan penelitian. Alur perancangan penelitian digambarkan pada Gambar 3.1:

**Gambar 3.1** Alur Perancangan Sistem

Alur perancangan penelitian dimulai dari tahap identifikasi masalah untuk memahami kendala penyiangan manual dan risiko kesalahan pencabutan bibit cabai akibat kemiripan fisik dengan gulma. Selanjutnya, rumusan masalah dan tujuan penelitian dirumuskan secara spesifik untuk menguji performa kombinasi arsitektur segmentasi U-Net dan DeepLabV3+ dengan *backbone* MobileNetV2. Studi literatur dilakukan untuk mengkaji pendekatan segmentasi semantik dan karakteristik morfologi gulma.

Tahap pengumpulan data mencakup akuisisi citra digital primer di lahan cabai. Citra mentah tersebut melalui tahap pra-pemrosesan (*preprocessing*), meliputi *resizing* ke ukuran $512 \times 512$ piksel, augmentasi citra, dan anotasi tingkat piksel (*pixel-level annotation*). Tahap perancangan dan pelatihan model melatih algoritma U-Net dan DeepLabV3+ berbasis MobileNetV2. Model terlatih kemudian dievaluasi menggunakan metrik *Global Accuracy*, Presisi, *Recall*, *F1-Score*, IoU, dan *Mean IoU* (mIoU). Model terbaik dikonversi ke format TensorFlow Lite (`.tflite`) dan diintegrasikan ke dalam aplikasi Android. Penelitian diakhiri dengan pengujian sistem (*Black Box Testing* & UAT) dan penarikan kesimpulan.

## Gambaran Umum Sistem

Gambaran umum sistem menjelaskan alur interaksi antarkomponen di dalam aplikasi Android Gulma Detektor yang diilustrasikan pada Gambar 3.2:

**Gambar 3.2** Gambaran Umum Sistem

Arsitektur sistem berpusat pada aplikasi Android sebagai antarmuka utama pengguna. Alur kerja dimulai ketika pengguna memberikan masukan (*input*), seperti pendaftaran akun, login, pemindaian citra melalui kamera/galeri, penambahan catatan lahan, hingga membaca artikel edukasi.

Dalam menjalankan fungsi utamanya, aplikasi Android berinteraksi langsung dengan dua jalur utama:
1. **Mesin Inferensi AI On-Device (TensorFlow Lite):** Pemrosesan segmentasi semantik dilakukan 100% *offline* langsung di dalam perangkat seluler menggunakan interpreter TensorFlow Lite (`best_model_unet_tversky_float32.tflite`). Aplikasi memasukkan matrik piksel citra kamera ke model dan menerima luaran berupa lapisan topeng warna (*masking*) serta label klasifikasi piksel (**Gulma** atau **Tanaman**).
2. **Layanan Cloud Supabase & REST API:** Untuk fitur autentikasi akun dan penyimpanan rekam jejak riwayat lahan, aplikasi terhubung dua arah dengan Supabase (Cloud Auth & Database). Sementara itu, materi artikel edukasi dan ensiklopedia jenis gulma diambil melalui penyedia Retrofit REST API.

## Use Case Diagram

*Use Case Diagram* menggambarkan hubungan interaksi antara aktor (pengguna) dengan fitur-fitur fungsional sistem yang disajikan pada Gambar 3.3:

**Gambar 3.3** Use Case Diagram Pengguna

Sistem memiliki satu aktor utama yaitu **Pengguna (Petani)**. Interaksi dimulai dari manajemen akun melalui fitur **Register** dan **Login**. Pengguna memiliki akses ke fitur **Melihat Artikel** dan **Membaca Pembelajaran** untuk memperluas literasi botani. Fitur utama sistem adalah **Deteksi Gulma** yang terhubung secara *include* dengan fitur **Menambah Catatan** dan **Menyimpan Catatan** riwayat. Pengguna juga dapat mengelola rekam jejak pemindaian melalui fitur **Melihat Riwayat Deteksi** yang terhubung secara *include* dengan opsi **Memperbarui Catatan** dan **Menghapus Riwayat**.

## Activity Diagram

*Activity Diagram* memvisualisasikan alur kerja (*workflow*) logis sistem dari titik awal (*start point*) hingga titik akhir (*end point*).

### Activity Diagram Autentikasi

#### Activity Diagram Register
Mengilustrasikan alur pendaftaran akun baru pada Gambar 3.4. Pengguna mengisi formulir pendaftaran di antarmuka aplikasi. Data dikirimkan ke Supabase Cloud Database untuk disimpan. Setelah terverifikasi sukses, sistem menampilkan notifikasi pendaftaran berhasil dan mengarahkan pengguna ke layar Login.

**Gambar 3.4** Activity Diagram Register

#### Activity Diagram Login
Mengilustrasikan alur verifikasi kredensial akun pada Gambar 3.5. Pengguna memasukkan *email* dan *password*. Sistem mengirimkan permintaan validasi ke Supabase Auth. Jika kredensial valid, sistem membuat token sesi dan membuka Halaman Utama (*Home*). Jika tidak valid, sistem menampilkan peringatan kesalahan.

**Gambar 3.5** Activity Diagram Login

### Activity Diagram Akses Artikel & Pembelajaran
Mengilustrasikan alur penarikan materi literasi pada Gambar 3.6 dan Gambar 3.7. Pengguna memilih artikel atau ensiklopedia gulma pada antarmuka aplikasi. Sistem mengirimkan permintaan HTTP GET via Retrofit ke REST API eksternal. REST API merespons dengan mengirimkan paket data teks dan gambar secara *real-time* untuk ditampilkan di layar pengguna.

**Gambar 3.6** Activity Diagram Akses Artikel
**Gambar 3.7** Activity Diagram Akses Pembelajaran Gulma

### Activity Diagram Deteksi Gulma
Mengilustrasikan alur kerja modul deteksi pada Gambar 3.8. Pengguna membuka antarmuka kamera dan memilih sumber citra (kamera atau galeri). Gambar dipotong (*crop*) dan disesuaikan ke ukuran $512 \times 512$ piksel. Sistem mengumpankan matriks piksel ke mesin *on-device interpreter* TFLite. Model memproses segmentasi semantik dan menampilkan hasil visualisasi topeng warna (*overlay mask* Merah = Gulma, Hijau = Tanaman) serta label klasifikasi **Gulma** atau **Tanaman**. Pengguna dapat menambahkan catatan lahan dan menyimpan hasil ke dalam riwayat Supabase.

**Gambar 3.8** Activity Diagram Deteksi Gulma

### Activity Diagram Riwayat (*History*)
Mengilustrasikan alur pengelolaan data rekam jejak lahan pada Gambar 3.9. Pengguna membuka halaman Riwayat. Aplikasi memuat daftar riwayat dari basis data Supabase secara kronologis. Pengguna dapat memilih kartu riwayat untuk melihat detail *overlay mask*, memperbarui catatan (*Update*), atau menghapus item riwayat (*Delete*).

**Gambar 3.9** Activity Diagram Riwayat Deteksi

## Pembuatan Model Deep Learning

Tahap pembuatan model merupakan proses inti dalam membangun kecerdasan buatan sistem deteksi.

**Gambar 3.15** Alur Pembuatan Model

Alur pembuatan model diawali dengan pemuatan dataset yang telah di-*preprocess* (dibagi menjadi *train set*, *validation set*, dan *test set*). Data diumpankan ke dua arsitektur segmentasi semantik: U-Net + MobileNetV2 dan DeepLabV3+ + MobileNetV2.

- **Pelatihan Model:** Kedua model dilatih di lingkungan Google Colab menggunakan GPU Tesla T4 dengan fungsi *loss* kombinasi *Cross-Entropy* dan *Tversky Loss* untuk mengatasi ketidakseimbangan piksel (*class imbalance*).
- **Evaluasi & Output Model:** Performa diuji menggunakan *test set* terisolasi berbasis metrik *Global Accuracy*, Presisi, *Recall*, *F1-Score*, IoU, dan mIoU. Model terbaik berformat `.h5` disimpan dan selanjutnya dikonversi ke format `.tflite` untuk ditanamkan ke dalam aplikasi Android.

## Rancangan Pengujian Black Box Testing

Pengujian *Black Box Testing* dilakukan untuk memverifikasi fungsionalitas eksternal antarmuka aplikasi tanpa membedah struktur internal kode.

### Pengujian Registrasi Pengguna
Memverifikasi keandalan formulir pendaftaran pengguna baru pada Tabel 3.4.

**Tabel 3.4** Skenario Pengujian Registrasi

| **No** | **Skenario** | **Langkah Pengujian** | **Data Input** | **Hasil yang Diharapkan** |
| --- | --- | --- | --- | --- |
| 1 | Registrasi dengan data lengkap dan valid | 1. Buka halaman registrasi<br>2. Masukkan Nama, Email, Password, dan Confirm Password valid<br>3. Klik tombol Daftar | Nama: Joe Farm<br>Email: joefarm@example.com<br>Password: Joe123!<br>Confirm: Joe123! | Akun berhasil dibuat dan tersimpan di database Supabase, notifikasi sukses muncul, dan halaman berpindah ke Login. |
| 2 | Registrasi dengan email terdaftar | 1. Masukkan email terdaftar<br>2. Klik tombol Daftar | Email: joefarm@example.com | Pendaftaran ditolak, muncul pesan peringatan "Email sudah terdaftar". |
| 3 | Registrasi dengan konfirmasi password tidak cocok | 1. Masukkan password dan konfirmasi berbeda<br>2. Klik tombol Daftar | Password: password123<br>Confirm: password098 | Registrasi ditolak, muncul pesan validasi "Password dan konfirmasi tidak cocok". |
| 4 | Registrasi dengan password kurang dari 6 karakter | 1. Masukkan password < 6 karakter<br>2. Klik tombol Daftar | Password: 12345 | Registrasi ditolak, muncul pesan validasi "Password minimal 6 karakter". |
| 5 | Registrasi dengan kolom mandatory kosong | 1. Kosongkan kolom Nama<br>2. Klik tombol Daftar | Nama: [Kosong] | Tombol tidak dapat ditekan atau muncul pesan "Kolom Nama wajib diisi". |

### Pengujian Login Pengguna
Memverifikasi mekanisme otentikasi gerbang masuk aplikasi pada Tabel 3.5.

**Tabel 3.5** Skenario Pengujian Login

| **No** | **Skenario** | **Langkah Pengujian** | **Data Input** | **Hasil yang Diharapkan** |
| --- | --- | --- | --- | --- |
| 1 | Login kredensial valid | 1. Masukkan email dan password terdaftar<br>2. Klik tombol Masuk | Email: joefarm@example.com<br>Password: Joe123! | Otentikasi Supabase sukses, token sesi dibuat, pengguna masuk ke Halaman Utama. |
| 2 | Login dengan email tidak terdaftar | 1. Masukkan email belum terdaftar<br>2. Klik tombol Masuk | Email: petani@test.com | Login gagal, muncul pesan kesalahan "Pengguna tidak ditemukan". |
| 3 | Login dengan password salah | 1. Masukkan email benar dan password salah<br>2. Klik tombol Masuk | Password: salah123 | Login gagal, muncul pesan kesalahan "Password tidak valid". |

### Pengujian Fitur Edukasi & Informasi
Memverifikasi penarikan artikel dan ensiklopedia gulma pada Tabel 3.6.

**Tabel 3.6** Skenario Pengujian Fitur Edukasi dan Informasi

| **No** | **Skenario** | **Langkah Pengujian** | **Data Input** | **Hasil yang Diharapkan** |
| --- | --- | --- | --- | --- |
| 1 | Menampilkan daftar artikel | 1. Buka menu Edukasi<br>2. Gulir daftar artikel | - | Daftar artikel termuat dari REST API, menampilkan judul, deskripsi singkat, dan *thumbnail*. |
| 2 | Membaca detail artikel | 1. Klik salah satu item artikel | Artikel ID: 1 | Halaman detail artikel terbuka menampilkan konten artikel lengkap secara utuh. |
| 3 | Membaca ensiklopedia gulma | 1. Klik tab "Jenis Gulma"<br>2. Pilih salah satu jenis gulma | Gulma ID: 1 | Halaman detail ensiklopedia menampilkan deskripsi morfologi dan panduan penanganan. |

### Pengujian Fitur Deteksi Gulma
Memverifikasi modul inferensi kamera dan klasifikasi segmentasi pada Tabel 3.7.

**Tabel 3.7** Skenario Pengujian Fitur Deteksi Gulma

| **No** | **Skenario** | **Langkah Pengujian** | **Data Input** | **Hasil yang Diharapkan** |
| --- | --- | --- | --- | --- |
| 1 | Tampilkan jendela kamera | 1. Tekan tombol Scan/Deteksi | Izin Akses Kamera | Jendela *preview* kamera terbuka *real-time* dengan bingkai panduan fokus. |
| 2 | Uji tombol *switch* kamera | 1. Tekan ikon *switch* kamera | - | Kamera berpindah antara lensa depan dan lensa belakang. |
| 3 | Uji tombol *flash* | 1. Tekan ikon *flash* | - | Mode *flash* berganti (On / Off / Auto). |
| 4 | Ambil dan potong citra (*Crop*) | 1. Tekan tombol *shutter*<br>2. Sesuaikan bingkai pemotong | Citra foto lahan | Citra terpotong presisi sesuai area fokus objek pengguna. |
| 5 | Analisis segmentasi citra | 1. Konfirmasi citra potong | Citra ukuran $512 \times 512$ | Mesin TFLite memproses inferensi, memunculkan *overlay mask* (Merah = Gulma, Hijau = Tanaman) dan label klasifikasi **Gulma** atau **Tanaman**. |
| 6 | Ambil citra dari galeri | 1. Tekan ikon Galeri<br>2. Pilih berkas foto | Berkas citra memori HP | Citra terpilih masuk ke layar *crop* dan siap dianalisis. |
| 7 | Tambah catatan pasca-deteksi | 1. Masukkan teks pada kolom catatan<br>2. Tekan tombol Simpan | Teks catatan lahan | Catatan tersimpan bersama citra hasil segmentasi ke Supabase Cloud. |

### Pengujian Fitur Riwayat (*History*)
Memverifikasi manajemen rekam jejak pemindaian lahan pada Tabel 3.8.

**Tabel 3.8** Skenario Pengujian Fitur Riwayat

| **No** | **Skenario** | **Langkah Pengujian** | **Data Input** | **Hasil yang Diharapkan** |
| --- | --- | --- | --- | --- |
| 1 | Simpan hasil deteksi | 1. Tekan tombol Simpan Deteksi | Data hasil pemindaian | Data terdaftar sebagai item riwayat baru di Supabase. |
| 2 | Tampilkan daftar riwayat | 1. Buka menu Riwayat | - | Kartu riwayat termuat urut dari waktu pemindaian terbaru. |
| 3 | Tampilkan detail riwayat | 1. Klik salah satu kartu riwayat | History ID | Layar detail menampilkan citra *overlay*, nilai dimensi, dan catatan lahan. |
| 4 | Perbarui catatan riwayat | 1. Edit teks catatan<br>2. Tekan tombol Simpan Perubahan | Catatan perbaikan | Catatan berhasil diperbarui pada basis data Supabase. |
| 5 | Hapus item riwayat | 1. Tekan ikon hapus<br>2. Konfirmasi penghapusan | History ID | Item terhapus permanen dari daftar riwayat dan basis data Supabase. |

## Rancangan Pengujian User Acceptance Testing (UAT)

Setelah lolos pengujian fungsionalitas *Black Box Testing*, pengujian penerimaan pengguna (*User Acceptance Testing* / UAT) dilakukan dengan menyebarkan kuesioner skala Likert (skala 1-5) kepada kelompok responden sasaran (petani cabai dan penyuluh pertanian) untuk mengukur tingkat kenyamanan, akurasi visual, dan kelayakan aplikasi di lapangan.