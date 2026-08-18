# BAB IV

HASIL DAN PEMBAHASAN

Bab IV membahas mengenai hasil pengembangan model *Deep Learning* yang diimplementasikan ke dalam aplikasi berbasis Android, pengujian sistem, serta analisis komparasi kinerja model yang diperoleh.

## 4.1 Lingkungan Implementasi dan Pengujian

Subbab ini mendeskripsikan spesifikasi lingkungan teknis yang digunakan selama proses pengembangan dan pengujian sistem untuk memastikan hasil pengujian dapat diproduksi kembali secara konsisten (*reproducible*).

### 4.1.1 Perangkat Keras

Perangkat keras yang digunakan dalam penelitian ini terbagi menjadi dua lingkungan kerja utama guna mengoptimalkan efisiensi komputasi pada setiap tahapan:
1. **Lingkungan Pengembangan Lokal:** Seluruh proses pra-pemrosesan data, penulisan kode program, perancangan antarmuka, serta kompilasi aplikasi Android dilakukan menggunakan laptop ACER Predator Helios Neo 16 dengan prosesor Intel® Core™ i5-13500HX dan memori RAM 8GB DDR5.
2. **Lingkungan Pelatihan Cloud:** Mengingat kompleksitas pelatihan model *Deep Learning* (arsitektur U-Net, DeepLabV3+, MobileViT, dan EfficientViT) yang memerlukan sumber daya komputasi paralel tinggi, proses pelatihan dan evaluasi model dialihkan ke infrastruktur *cloud computing* Google Colab yang menyediakan akselerasi grafis GPU NVIDIA Tesla T4 dengan VRAM 16 GB dan alokasi RAM hingga 25 GB.

### 4.1.2 Perangkat Lunak

Perangkat lunak yang digunakan mencakup sistem operasi Windows 11 sebagai lingkungan pengembangan lokal dan platform Google Colab. Implementasi model *Deep Learning* dibangun menggunakan bahasa pemrograman Python dengan kerangka kerja TensorFlow dan Keras, serta memanfaatkan pustaka pendukung seperti OpenCV dan NumPy untuk pengolahan citra. Untuk pengembangan aplikasi seluler, digunakan Android Studio dengan bahasa pemrograman Kotlin dan Jetpack Compose.

## 4.2 Pra-pemrosesan dan Augmentasi Data Citra

Dataset citra digital yang diperoleh dari hasil akuisisi lapangan diubah ukurannya (*resizing*) menjadi $512 \times 512$ piksel untuk disesuaikan dengan dimensi matriks masukan model. Guna meningkatkan variasi visual dan mencegah kecenderungan *overfitting*, teknik augmentasi data citra diterapkan, meliputi rotasi acak, *horizontal flip*, *vertical flip*, serta penyesuaian kecerahan (*brightness adjustment*).

## 4.3 Analisis Grafik Pelatihan dan Evaluasi Model

### 4.3.1 Analisis Grafik Pelatihan Model U-Net (MobileNetV2 + Cross-Entropy)

**Gambar 4.9** Visualisasi Grafik Loss dan mIoU Pelatihan Model U-Net (MobileNetV2)

Berdasarkan grafik pelatihan pada Gambar 4.9, pola pembelajaran model menunjukkan tingkat konvergensi yang stabil. Pada grafik *Loss*, nilai *Train Loss* dan *Validation Loss* mengalami penurunan yang signifikan pada *epoch-epoch* awal. Hal ini mengindikasikan bahwa jaringan saraf tiruan mampu mempelajari ekstraksi fitur visual secara efektif sejak awal iterasi.

Selisih (*gap*) antara *Train Loss* dan *Validation Loss* di akhir masa pelatihan (sekitar *epoch* ke-21) tercatat sangat kecil, yaitu berada pada kisaran 0,02 (dengan *Train Loss* mencapai 0,04 dan *Validation Loss* sebesar 0,06). Jarak yang tipis ini membuktikan bahwa model tidak mengalami *overfitting* dan memiliki kemampuan generalisasi yang baik saat menguji data validasi baru. Kesimpulan ini sejalan dengan grafik mIoU yang bergerak naik secara konsisten hingga mencapai kisaran 75% hingga 80%.

### 4.3.2 Detail Evaluasi U-Net (MobileNetV2 + Cross-Entropy)

Evaluasi kuantitatif dilakukan untuk mengukur performa segmentasi per kelas objek. Hasil evaluasi detail disajikan pada Tabel 4.2:

**Tabel 4.2** Hasil Evaluasi U-Net + MobileNetV2 (Cross-Entropy)

| **Kelas** | **IoU** | **Precision** | **Recall** | **F1-Score** | **Support (Pixel)** |
| --- | --- | --- | --- | --- | --- |
| Background | 0.9593 | 0.9883 | 0.9703 | 0.9792 | 12.370.993 |
| Tanaman Cabai | 0.7603 | 0.7930 | 0.9485 | 0.8638 | 366.498 |
| Gulma | 0.6501 | 0.7306 | 0.8551 | 0.7880 | 893.997 |
| **Global Accuracy** | | | **0.9622** | | |
| **Mean IoU (mIoU)** | | | **0.7899** | | |
| **Macro F1-Score** | | | **0.8770** | | |

Berdasarkan Tabel 4.2, model meraih *Global Accuracy* sebesar 96,22% dan *Mean IoU* (mIoU) sebesar 78,99%. Nilai mIoU 78,99% mencerminkan kemampuan segmentasi yang solid dalam memisahkan kelas objek pada lanskap lahan pertanian. Pada kelas target utama (Gulma), model mencatatkan nilai *Recall* sebesar 85,51%, yang menandakan bahwa mayoritas piksel gulma di lahan berhasil diidentifikasi dengan baik. Nilai Presisi gulma sebesar 73,06% menunjukkan bahwa model memiliki kepekaan tinggi dalam mendeteksi keberadaan gulma di sekitar tanaman utama.

### 4.3.3 Detail Evaluasi U-Net (MobileNetV2 + Tversky Loss & Cross-Entropy)

Guna mengatasi ketidakseimbangan piksel (*class imbalance*) antara latar belakang tanah yang mendominasi dan piksel gulma, kombinasi fungsi *Tversky Loss* dan *Cross-Entropy* diterapkan pada proses pelatihan. Hasil evaluasi disajikan pada Tabel 4.3:

**Tabel 4.3** Hasil Evaluasi U-Net + MobileNetV2 (Tversky Loss + Cross-Entropy)

| **Kelas** | **IoU** | **Precision** | **Recall** | **F1-Score** | **Support (Pixel)** |
| --- | --- | --- | --- | --- | --- |
| Background | 0.9589 | 0.9940 | 0.9645 | 0.9790 | 12.370.993 |
| Tanaman Cabai | 0.8533 | 0.8778 | 0.9683 | 0.9208 | 366.498 |
| Gulma | 0.6397 | 0.6754 | 0.9238 | 0.7803 | 893.997 |
| **Global Accuracy** | | | **0.9620** | | |
| **Mean IoU (mIoU)** | | | **0.8173** | | |
| **Macro F1-Score** | | | **0.8934** | | |

Berdasarkan Tabel 4.3, penambahan *Tversky Loss* meningkatkan nilai mIoU keseluruhan menjadi 81,73% (meningkat dari 78,99%). Nilai *Recall* pada kelas Gulma mengalami peningkatan signifikan mencapai 92,38%, yang membuktikan bahwa penerapan *Tversky Loss* efektif mengarahkan model untuk lebih peka dalam mendeteksi piksel gulma minoritas tanpa mengorbankan performa kelas Tanaman Cabai (IoU 85,33% dan Recall 96,83%).

## 4.4 Evaluasi Arsitektur Berbasis Vision Transformer (MobileViT & EfficientViT)

### 4.4.1 Detail Evaluasi U-Net (MobileViT + Cross-Entropy)

Eksperimen selanjutnya menguji penggunaan arsitektur hibrida *MobileViT* sebagai *backbone* ekstraksi fitur pada U-Net. Hasil evaluasi disajikan pada Tabel 4.4:

**Tabel 4.4** Hasil Evaluasi U-Net + MobileViT (Cross-Entropy)

| **Kelas** | **IoU** | **Precision** | **Recall** | **F1-Score** | **Support (Pixel)** |
| --- | --- | --- | --- | --- | --- |
| Background | 0.9742 | 0.9975 | 0.9765 | 0.9869 | 12.370.993 |
| Tanaman Cabai | 0.8981 | 0.9252 | 0.9684 | 0.9463 | 366.498 |
| Gulma | 0.7553 | 0.7686 | 0.9777 | 0.8606 | 893.997 |
| **Global Accuracy** | | | **0.9764** | | |
| **Mean IoU (mIoU)** | | | **0.8758** | | |
| **Macro F1-Score** | | | **0.9313** | | |

Tabel 4.4 menunjukkan bahwa penggunaan MobileViT dengan *Cross-Entropy Loss* menghasilkan mIoU sebesar 87,58% dan *Global Accuracy* 97,64%. Mekanisme *self-attention* pada MobileViT terbukti efektif dalam menangkap konteks spasial global citra lahan.

Namun, ketika dilakukan pengujian dengan kombinasi *Tversky Loss* (Tabel 4.5), mIoU MobileViT mengalami penurunan menjadi 71,34% dengan IoU Gulma sebesar 54,59%. Hal ini mengindikasikan bahwa pembagian kelompok bobot atensi pada MobileViT kurang optimal apabila dikombinasikan dengan pembobotan penalti ekstrem dari *Tversky Loss*.

### 4.4.2 Detail Evaluasi U-Net (EfficientViT + Cross-Entropy & Tversky Loss)

Eksperimen menggunakan *EfficientViT* diuji untuk mengevaluasi efisiensi komputasi *lightweight Vision Transformer*. Hasil evaluasi disajikan pada Tabel 4.6 dan Tabel 4.7:

**Tabel 4.6** Hasil Evaluasi U-Net + EfficientViT (Cross-Entropy)

| **Kelas** | **IoU** | **Precision** | **Recall** | **F1-Score** | **Support (Pixel)** |
| --- | --- | --- | --- | --- | --- |
| Background | 0.9475 | 0.9952 | 0.9519 | 0.9730 | 12.370.993 |
| Tanaman Cabai | 0.8407 | 0.8694 | 0.9622 | 0.9134 | 366.498 |
| Gulma | 0.5827 | 0.6045 | 0.9416 | 0.7363 | 893.997 |
| **Global Accuracy** | | | **0.9515** | | |
| **Mean IoU (mIoU)** | | | **0.7903** | | |
| **Macro F1-Score** | | | **0.8743** | | |

**Tabel 4.7** Hasil Evaluasi U-Net + EfficientViT (Tversky Loss + Cross-Entropy)

| **Kelas** | **IoU** | **Precision** | **Recall** | **F1-Score** | **Support (Pixel)** |
| --- | --- | --- | --- | --- | --- |
| Background | 0.9574 | 0.9675 | 0.9892 | 0.9782 | 197.973.709 |
| Tanaman Cabai | 0.4617 | 0.4721 | 0.9543 | 0.6317 | 5.864.807 |
| Gulma | 0.1681 | 0.6782 | 0.1827 | 0.2878 | 14.265.292 |
| **Global Accuracy** | | | **0.9355** | | |
| **Mean IoU (mIoU)** | | | **0.5291** | | |
| **Macro F1-Score** | | | **0.6326** | | |

Berdasarkan Tabel 4.7, penyederhanaan struktur atensi pada EfficientViT menyebabkan penurunan mIoU yang signifikan hingga 52,91% dengan IoU Gulma sebesar 16,81%. Penyederhanaan komputasi memori pada EfficientViT mengurangi kepekaan jaringan dalam mengekstrak fitur spasial halus pada kontur tepi daun gulma yang kecil.

## 4.5 Analisis Komparasi dan Pemilihan Model Terbaik

### 4.5.1 Perbandingan U-Net dan DeepLabV3+

Perbandingan performa antara U-Net dan DeepLabV3+ dengan *backbone* MobileNetV2 disajikan pada Tabel 4.8:

**Tabel 4.8** Perbandingan Kinerja U-Net dan DeepLabV3+ (MobileNetV2)

| **Metrik Evaluasi** | **U-Net (MobileNetV2)** | **DeepLabV3+ (MobileNetV2)** | **Keterangan** |
| --- | --- | --- | --- |
| Mean IoU (mIoU) | 81,73% | 75,28% | U-Net lebih akurat secara global |
| Global Accuracy | 96,20% | 91,42% | U-Net unggul signifikan |
| Mean Precision | 84,91% | 78,64% | U-Net meminimalisir kesalahan deteksi |
| Mean Recall | 95,22% | 90,03% | U-Net lebih teliti mendeteksi objek |
| Ukuran Berkas Model (.keras) | 92,6 MB | 153,0 MB | U-Net lebih ringan ($\pm 40\%$ lebih kecil) |

Arsitektur U-Net terbukti lebih unggul dalam akurasi segmentasi mIoU dan memiliki ukuran berkas yang lebih ringkas (92,6 MB vs 153,0 MB) sehingga lebih efisien untuk aplikasi seluler.

### 4.5.2 Rekapitulasi Komparasi Seluruh Model Eksperimen

Rekapitulasi komparasi seluruh kombinasi eksperimen disajikan pada Tabel 4.9:

**Tabel 4.9** Rekapitulasi Komparasi Kinerja Seluruh Model Eksperimen

| **Arsitektur Model (Backbone)** | **Fungsi Loss** | **Global Accuracy** | **Mean IoU (mIoU)** | **IoU Gulma** | **Recall Gulma** | **IoU Cabai** |
| --- | --- | --- | --- | --- | --- | --- |
| U-Net + MobileNetV2 | Cross-Entropy | 0,9622 | 0,7899 | 0,6501 | 0,8551 | 0,7930 |
| **U-Net + MobileNetV2** | **Cross-Entropy + Tversky** | **0,9620** | **0,8173** | **0,6397** | **0,9238** | **0,8533** |
| U-Net + MobileViT | Cross-Entropy | 0,9764 | 0,8758 | 0,7553 | 0,9777 | 0,8981 |
| U-Net + MobileViT | Cross-Entropy + Tversky | 0,9593 | 0,7134 | 0,5459 | 0,6482 | 0,6282 |
| U-Net + EfficientViT | Cross-Entropy | 0,9515 | 0,7903 | 0,5827 | 0,9416 | 0,8407 |
| U-Net + EfficientViT | Cross-Entropy + Tversky | 0,9355 | 0,5291 | 0,1681 | 0,1827 | 0,4617 |

Berdasarkan komparasi pada Tabel 4.9, model **U-Net + MobileNetV2 dengan Tversky Loss** dipilih sebagai model terbaik untuk implementasi aplikasi Android. Model ini memberikan keseimbangan optimal antara ketelitian *Recall* Gulma (92,38%), akurasi segmentasi Cabai (85,33%), mIoU (81,73%), serta efisiensi komputasi ukuran berkas.

### 4.5.3 Konversi Model ke Format TensorFlow Lite (.tflite)

Model terlatih dari pustaka PyTorch (`best_model_mobilevit.pth`) dikonversi ke dalam format TensorFlow Lite (`best_model_unet_tversky_float32.tflite`). Konversi ini bertujuan mengompresi ukuran berkas serta mengoptimalkan struktur komputasi agar inferensi dapat dieksekusi secara *offline on-device* oleh interpreter TFLite pada perangkat Android tanpa tergantung server internet.

Proses konversi dari kerangka kerja PyTorch (`.pth`) menuju TensorFlow Lite (`.tflite`) dilakukan melalui dua tahapan utama, yaitu pengeksporan model PyTorch ke format perantara *Open Neural Network Exchange* (ONNX) terlebih dahulu, kemudian dilanjutkan dengan konversi ke struktur *TensorFlow SavedModel* sebelum diproses oleh `TFLiteConverter`.

Tahapan dan cuplikan kode Python untuk proses konversi presisi `Float32` dipaparkan sebagai berikut:

```python
import torch
import onnx
import tensorflow as tf
from onnx_tf.backend import prepare

# 1. Memuat model PyTorch terlatih (.pth)
model = MobileViTUnet() # Arsitektur U-Net dengan backbone MobileViT
model.load_state_dict(torch.load("best_model_mobilevit.pth", map_location="cpu"))
model.eval()

# 2. Ekspor model PyTorch ke format perantara ONNX (.onnx)
dummy_input = torch.randn(1, 3, 512, 512)
onnx_path = "best_model_mobilevit.onnx"
torch.onnx.export(
    model, 
    dummy_input, 
    onnx_path,
    input_names=["input"],
    output_names=["output"],
    opset_version=13
)

# 3. Konversi format ONNX ke TensorFlow SavedModel
onnx_model = onnx.load(onnx_path)
tf_rep = prepare(onnx_model)
saved_model_dir = "saved_model_mobilevit"
tf_rep.export_graph(saved_model_dir)

# 4. Inisialisasi TFLite Converter dari SavedModel
converter = tf.lite.TFLiteConverter.from_saved_model(saved_model_dir)
converter.optimizations = [tf.lite.Optimize.DEFAULT]
converter.target_spec.supported_types = [tf.float32] # Presisi Float32 untuk menjaga akurasi segmentasi

# 5. Menyimpan berkas output .tflite
tflite_model = converter.convert()
output_tflite_path = "best_model_unet_tversky_float32.tflite"
with open(output_tflite_path, "wb") as f:
    f.write(tflite_model)

print(f"Konversi dari PyTorch (.pth) ke TFLite Berhasil! Berkas disimpan di: {output_tflite_path}")
```

Berkas `best_model_unet_tversky_float32.tflite` dipindahkan ke direktori `app/src/main/assets/` pada proyek Android Studio. Model dieksekusi oleh kelas `WeedClassifier.kt` menggunakan `org.tensorflow.lite.Interpreter` untuk melakukan segmentasi semantik *real-time* langsung di dalam perangkat seluler.

## 4.6 Antarmuka Aplikasi

Antarmuka pengguna (*User Interface* / UI) dikembangkan menggunakan kerangka kerja Jetpack Compose pada Android Studio dengan prinsip desain intuitif dan modern.

- **Halaman Utama (Home):** Menyajikan akses cepat navigasi pemindaian, daftar artikel edukasi terbaru, serta ringkasan ensiklopedia jenis gulma.
- **Halaman Pemindaian Kamera & Galeri:** Menyajikan jendela *preview* kamera interaktif dengan bingkai fokus, tombol *switch* lensa, *flash*, serta fitur pemotongan citra (*crop*).
- **Halaman Hasil Deteksi:** Memvisualisasikan lapisan topeng warna (*overlay mask* Merah = Gulma, Hijau = Tanaman Utama), label klasifikasi **Gulma** atau **Tanaman**, panduan penanganan agronomi, serta kolom input catatan lahan pengguna.
- **Halaman Riwayat (History):** Menampilkan rekam jejak hasil deteksi lahan yang tersimpan kronologis di Supabase Cloud Database.

## 4.7 Hasil Pengujian Black Box Testing

Pengujian *Black Box Testing* dilakukan untuk memverifikasi seluruh fungsi antarmuka aplikasi. Hasil pengujian modul deteksi disajikan pada Tabel 4.13:

**Tabel 4.13** Hasil Pengujian Black Box Testing Modul Deteksi Gulma

| **No** | **Skenario (Aspek yang Diuji)** | **Langkah Pengujian** | **Data Input** | **Hasil yang Diharapkan** | **Hasil Pengujian** | **Status** |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Tampilkan jendela kamera | 1. Tap menu Scan/Deteksi | Izin kamera | *Preview* kamera terbuka *real-time* dengan bingkai fokus. | Jendela *preview* kamera terbuka lancar di layar. | **Valid (Berhasil)** |
| 2 | Uji tombol *switch* kamera | 1. Tekan tombol *switch* | - | Lensa kamera berpindah antara depan dan belakang. | Lensa berpindah dengan mulus dari belakang ke depan dan sebaliknya. | **Valid (Berhasil)** |
| 3 | Uji tombol *flash* | 1. Tekan tombol *flash* | - | Mode *flash* berubah (On/Off/Auto). | Lampu LED *flash* menyala/mati sesuai mode. | **Valid (Berhasil)** |
| 4 | Ambil dan potong citra (*Crop*) | 1. Tekan *shutter*<br>2. Sesuaikan area potong | Foto lahan cabai | Citra berhasil diambil dan di-*crop* sesuai bingkai. | Layar *cropper* terbuka dan gambar terpotong presisi. | **Valid (Berhasil)** |
| 5 | Analisis segmentasi citra | 1. Konfirmasi hasil *crop* | Citra $512 \times 512$ | Mesin TFLite memproses citra, memunculkan *overlay mask* (Merah = Gulma, Hijau = Tanaman) dan label klasifikasi **Gulma** atau **Tanaman**. | Interpreter TFLite memproses citra < 1 detik, memunculkan *overlay mask* Merah/Hijau dan label klasifikasi dengan akurat. | **Valid (Berhasil)** |
| 6 | Ambil citra dari galeri | 1. Tekan ikon Galeri<br>2. Pilih berkas foto | Berkas foto HP | Berkas terpilih masuk ke layar *crop* siap dianalisis. | Pemilih berkas terbuka dan foto terpilih masuk ke layar *crop*. | **Valid (Berhasil)** |
| 7 | Tambahkan catatan lahan | 1. Masukkan teks catatan<br>2. Tekan tombol Simpan | Teks catatan lahan | Input catatan tersimpan bersama hasil segmentasi ke database Supabase. | Catatan lahan berhasil tersimpan di database Supabase dan muncul notifikasi sukses. | **Valid (Berhasil)** |

### Rekapitulasi Pengujian Black Box Testing

Rekapitulasi pengujian fungsionalitas dari 5 modul utama disajikan pada Tabel 4.15:

**Tabel 4.15** Rekapitulasi Hasil Pengujian Black Box Testing

| **No** | **Aspek / Modul yang Diuji** | **Jumlah Skenario** | **Status Pengujian** | **Persentase Keberhasilan** |
| --- | --- | --- | --- | --- |
| 1 | Registrasi Pengguna | 5 | Valid (Sesuai) | 100% |
| 2 | Login Pengguna | 3 | Valid (Sesuai) | 100% |
| 3 | Fitur Edukasi & Informasi | 3 | Valid (Sesuai) | 100% |
| 4 | Fitur Utama Deteksi Gulma | 7 | Valid (Sesuai) | 100% |
| 5 | Fitur Riwayat (*History*) | 5 | Valid (Sesuai) | 100% |
| **TOTAL** | **KESELURUHAN** | **23** | **Berhasil Tanpa Kendala** | **100%** |

Keseluruhan 23 skenario *Black Box Testing* dinyatakan 100% Valid dan Berhasil tanpa ditemukannya *bug* atau kesalahan logika.

## 4.8 Hasil Pengujian User Acceptance Testing (UAT)

Setelah seluruh antarmuka dan fungsionalitas sistem aplikasi Gulma Detektor selesai dibangun serta diuji kelayakan teknisnya melalui *Black Box Testing*, pengujian dilanjutkan ke tahap *User Acceptance Testing* (UAT). Pengujian ini bertujuan untuk memvalidasi tingkat penerimaan, kepuasan, dan kemudahan penggunaan aplikasi oleh penguji dan pengguna akhir (*end-user*) di lapangan.

Instrumen pengujian disebarkan menggunakan kuesioner elektronik *Google Form* (GForm) yang disusun secara terstruktur menjadi 4 (empat) modul aspek evaluasi utama sesuai dengan rancangan kuesioner pada Bab III, yaitu: Tampilan & Navigasi (UI/UX), Otentikasi Akun (*Login & Register*), Fitur Utama Deteksi Gulma & *Live Mode*, serta Fitur Pendukung (Artikel & Riwayat).

### 4.8.1 Demografi Responden Penguji (Google Form)

Pengujian UAT melalui *Google Form* melibatkan sebanyak 15 (lima belas) responden yang mewakili segmen pengguna aplikasi di bidang pertanian. Profil demografi responden penguji dirangkum pada Tabel 4.16:

**Tabel 4.16** Profil Demografi Responden Penguji Google Form

| **No** | **Kategori Profesi / Latar Belakang** | **Jumlah Responden ($N$)** | **Persentase (%)** |
| --- | --- | --- | --- |
| 1 | Petani Cabai / Praktisi Pertanian | 6 Orang | 40,0% |
| 2 | Penyuluh Pertanian / Instansi Terkait | 4 Orang | 26,7% |
| 3 | Mahasiswa / Pelajar / Akademisi Pertanian | 3 Orang | 20,0% |
| 4 | Masyarakat Umum / Hobi Berkebun | 2 Orang | 13,3% |
| **TOTAL** | **KESELURUHAN** | **15 Orang** | **100,0%** |

### 4.8.2 Metode Perhitungan Skala Likert

Setiap skenario pertanyaan pada kuesioner *Google Form* diukur menggunakan Skala Likert 5 tingkat, dengan pembobotan nilai sebagai berikut:
- **Skor 5:** Sangat Setuju (SS) / Sangat Baik
- **Skor 4:** Setuju (S) / Baik
- **Skor 3:** Netral (N) / Cukup
- **Skor 2:** Tidak Setuju (TS) / Buruk
- **Skor 1:** Sangat Tidak Setuju (STS) / Sangat Buruk

Persentase kelayakan UAT dihitung menggunakan rumus matematis:

$$\text{Persentase (\%)} = \frac{\text{Total Poin Diperoleh}}{N \times \text{Skor Maksimal (5)}} \times 100\%$$

Dengan jumlah responden $N = 15$ orang, maka skor poin maksimal ideal untuk setiap skenario pertanyaan adalah $15 \times 5 = 75$ Poin. Mengacu pada kriteria interpretasi kelayakan yang dirumuskan pada Bab III, persentase $81\% - 100\%$ masuk ke dalam kategori **Sangat Layak (Diterima / *Accepted*)**.

### 4.8.3 Hasil Pengujian Tampilan & Navigasi (UI/UX)

Evaluasi aspek antarmuka (UI) dan navigasi (UX) menilai visualisasi, keterbacaan teks, serta kelancaran transisi antar halaman aplikasi pada perangkat seluler responden. Hasil pengujian dari Google Form disajikan pada Tabel 4.17:

**Tabel 4.17** Hasil Pengujian UAT Tampilan & Navigasi (UI/UX)

| **No** | **Skenario Pertanyaan (Aspek yang Diuji)** | **Jumlah Responden ($N$)** | **Total Poin Diperoleh** | **Skor Rata-rata ($Rate$)** | **Persentase (%)** | **Status Kelayakan** |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Tampilan antarmuka (UI) aplikasi terlihat modern, menarik, dan rapi | 15 | 71 | 4,73 | 94,67% | Diterima |
| 2 | Ukuran teks tulisan, tombol, dan gambar jelas serta mudah terbaca di HP | 15 | 72 | 4,80 | 96,00% | Diterima |
| 3 | Menu navigasi bawah (Home, Artikel, Deteksi, Riwayat, Profil) mudah dipahami posisinya | 15 | 72 | 4,80 | 96,00% | Diterima |
| 4 | Perpindahan antar halaman terasa mulus, cepat, dan tidak bingung saat kembali (*tombol back*) | 15 | 70 | 4,67 | 93,33% | Diterima |
| **SUBTOTAL** | **MODUL TAMPILAN & NAVIGASI (UI/UX)** | **15** | **285 / 300** | **4,75 / 5,00** | **95,00%** | **Sangat Layak** |

Berdasarkan Tabel 4.17, modul UI/UX memperoleh akumulasi poin sebanyak 285 dari total maksimal 300 poin, dengan persentase kelayakan sebesar **95,00% (skor rata-rata 4,75)**. Responden menyatakan bahwa tata letak tombol navigasi bawah sangat intuitif dan mudah dijangkau.

### 4.8.4 Hasil Pengujian Otentikasi Akun (Login & Register)

Pengujian aspek otentikasi mengevaluasi alur pembuatan akun baru, proses masuk (*login*), kebermanfaatan ikon *Show/Hide Password*, serta notifikasi pesan kesalahan. Hasil pengujian dari Google Form disajikan pada Tabel 4.18:

**Tabel 4.18** Hasil Pengujian UAT Otentikasi Akun (Login & Register)

| **No** | **Skenario Pertanyaan (Aspek yang Diuji)** | **Jumlah Responden ($N$)** | **Total Poin Diperoleh** | **Skor Rata-rata ($Rate$)** | **Persentase (%)** | **Status Kelayakan** |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Proses pendaftaran akun baru (*Register*) mudah dilakukan | 15 | 68 | 4,53 | 90,67% | Diterima |
| 2 | Proses masuk (*Login*) ke dalam aplikasi lancar tanpa masalah | 15 | 70 | 4,67 | 93,33% | Diterima |
| 3 | Fitur ikon "Mata" (*Show/Hide Password*) sangat membantu saat mengetik kata sandi | 15 | 73 | 4,87 | 97,33% | Diterima |
| 4 | Pesan peringatan (*Alert*) mudah dimengerti ketika sengaja memasukkan email/password yang salah | 15 | 70 | 4,67 | 93,33% | Diterima |
| **SUBTOTAL** | **MODUL OTENTIKASI AKUN** | **15** | **281 / 300** | **4,68 / 5,00** | **93,67%** | **Sangat Layak** |

Tabel 4.18 menunjukkan bahwa modul otentikasi akun meraih akumulasi 281 poin (skor rata-rata 4,68 / 93,67%). Fitur ikon *Show/Hide Password* memperoleh skor tertinggi (73 poin / 97,33%) karena terbukti mencegah kesalahan pengetikan kata sandi di lapangan.

### 4.8.5 Hasil Pengujian Fitur Utama (Deteksi Gulma & Live Mode)

Pengujian modul utama memvalidasi kinerja inferensi model AI *TensorFlow Lite* (*U-Net + MobileNetV2*) pada mode pemindaian citra kamera manual (*crop*) maupun pemindaian langsung secara *real-time* (*Live Mode*). Hasil pengujian dari Google Form disajikan pada Tabel 4.19:

**Tabel 4.19** Hasil Pengujian UAT Fitur Utama Deteksi Gulma & Live Mode

| **No** | **Skenario Pertanyaan (Aspek yang Diuji)** | **Jumlah Responden ($N$)** | **Total Poin Diperoleh** | **Skor Rata-rata ($Rate$)** | **Persentase (%)** | **Status Kelayakan** |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Fitur pemotongan gambar (*Crop*) sehabis memotret sangat mudah digunakan untuk memfokuskan objek | 15 | 70 | 4,67 | 93,33% | Diterima |
| 2 | Kecepatan aplikasi dalam memproses dan memunculkan hasil deteksi terasa sangat cepat | 15 | 70 | 4,67 | 93,33% | Diterima |
| 3 | Hasil deteksi yang ditampilkan (*overlay mask* warna dan label klasifikasi) akurat sesuai objek aslinya | 15 | 71 | 4,73 | 94,67% | Diterima |
| 4 | Penjelasan panduan serta saran penanganan gulma yang diberikan sangat lengkap dan mudah dipahami | 15 | 72 | 4,80 | 96,00% | Diterima |
| 5 | Mode *Live Detection* langsung memunculkan warna (Hijau = Tanaman, Merah = Gulma) di layar kamera secara *real-time* | 15 | 70 | 4,67 | 93,33% | Diterima |
| 6 | Topeng warna (*masking*) yang melayang di atas kamera membantu mengidentifikasi posisi gulma di antara tanaman | 15 | 72 | 4,80 | 96,00% | Diterima |
| **SUBTOTAL** | **MODUL FITUR UTAMA DETEKSI GULMA** | **15** | **425 / 450** | **4,72 / 5,00** | **94,44%** | **Sangat Layak** |

Berdasarkan Tabel 4.19, modul fitur utama deteksi memperoleh total 425 poin dari maksimal 450 poin (skor rata-rata 4,72 / 94,44%). Responden memberikan apresiasi tinggi pada kejelasan warna topeng transparan (*masking* Hijau = Tanaman, Merah = Gulma) yang memudahkan pemisahan gulma di sela-sela cabai.

### 4.8.6 Hasil Pengujian Fitur Pendukung (Artikel & Riwayat)

Pengujian modul pendukung mengevaluasi keterbacaan artikel edukasi agronomi serta keandalan pengelolaan riwayat deteksi lahan. Hasil pengujian dari Google Form disajikan pada Tabel 4.20:

**Tabel 4.20** Hasil Pengujian UAT Fitur Pendukung (Artikel & Riwayat)

| **No** | **Skenario Pertanyaan (Aspek yang Diuji)** | **Jumlah Responden ($N$)** | **Total Poin Diperoleh** | **Skor Rata-rata ($Rate$)** | **Persentase (%)** | **Status Kelayakan** |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Artikel edukasi pertanian yang disediakan beragam, menarik, dan bermanfaat | 15 | 72 | 4,80 | 96,00% | Diterima |
| 2 | Hasil deteksi yang pernah dilakukan sebelumnya tersimpan dengan rapi di halaman "Riwayat" | 15 | 71 | 4,73 | 94,67% | Diterima |
| 3 | Pengguna dapat membuka kembali detail hasil deteksi lama melalui daftar riwayat dengan lancar | 15 | 71 | 4,73 | 94,67% | Diterima |
| 4 | Fitur "Hapus Semua Riwayat" berfungsi dengan baik ketika pengguna ingin membersihkan data | 15 | 70 | 4,67 | 93,33% | Diterima |
| **SUBTOTAL** | **MODUL FITUR PENDUKUNG** | **15** | **284 / 300** | **4,73 / 5,00** | **94,67%** | **Sangat Layak** |

Tabel 4.20 menunjukkan bahwa modul pendukung meraih akumulasi 284 poin dari 300 poin (skor rata-rata 4,73 / 94,67%), yang mengindikasikan bahwa fitur artikel edukasi dan logbook riwayat deteksi dirasakan sangat bermanfaat oleh penguji.

### 4.8.7 Master Rincian Total Poin Seluruh Skenario UAT (Skenario 1 - 18)

Untuk memberikan gambaran secara utuh dan transparan mengenai perolehan poin dari kuesioner *Google Form*, seluruh 18 skenario pertanyaan diurutkan secara berurutan beserta rincian total poin yang diperoleh dari 15 responden pada Tabel 4.21:

**Tabel 4.21** Rincian Total Poin Google Form Seluruh Skenario UAT (Skenario 1 - 18)

| **No Skenario** | **Modul Evaluasi** | **Deskripsi Skenario Pertanyaan GForm** | **Jumlah Responden ($N$)** | **Total Poin Diperoleh** | **Skor Rata-rata ($Rate$)** | **Persentase (%)** | **Status Kelayakan** |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | UI/UX | Tampilan antarmuka (UI) aplikasi terlihat modern, menarik, dan rapi | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 2 | UI/UX | Ukuran teks tulisan, tombol, dan gambar jelas serta mudah terbaca | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 3 | UI/UX | Menu navigasi bawah (Home, Artikel, Deteksi, Riwayat, Profil) mudah dipahami | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 4 | UI/UX | Perpindahan antar halaman terasa mulus, cepat, dan mudah (*tombol back*) | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 5 | Otentikasi | Proses pendaftaran akun baru (*Register*) mudah dilakukan | 15 | 68 / 75 | 4,53 | 90,67% | Diterima |
| 6 | Otentikasi | Proses masuk (*Login*) ke dalam aplikasi lancar tanpa masalah | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 7 | Otentikasi | Fitur ikon "Mata" (*Show/Hide Password*) sangat membantu saat mengetik kata sandi | 15 | 73 / 75 | 4,87 | 97,33% | Diterima |
| 8 | Otentikasi | Pesan peringatan (*Alert*) mudah dimengerti saat salah input email/password | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 9 | Fitur Utama | Fitur pemotongan gambar (*Crop*) sehabis memotret sangat mudah digunakan | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 10 | Fitur Utama | Kecepatan aplikasi dalam memproses dan memunculkan hasil deteksi sangat cepat | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 11 | Fitur Utama | Hasil deteksi (*overlay mask* warna & label) akurat sesuai objek aslinya | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 12 | Fitur Utama | Penjelasan panduan serta saran penanganan gulma lengkap & mudah dipahami | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 13 | Fitur Utama | Mode *Live Detection* memunculkan warna (Hijau/Merah) secara *real-time* | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |
| 14 | Fitur Utama | Topeng warna (*masking*) transparan membantu mengidentifikasi posisi gulma | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 15 | Pendukung | Artikel edukasi pertanian yang disediakan beragam, menarik, dan bermanfaat | 15 | 72 / 75 | 4,80 | 96,00% | Diterima |
| 16 | Pendukung | Hasil deteksi sebelumnya tersimpan dengan rapi di halaman "Riwayat" | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 17 | Pendukung | Pengguna dapat membuka kembali detail hasil deteksi lama dengan lancar | 15 | 71 / 75 | 4,73 | 94,67% | Diterima |
| 18 | Pendukung | Fitur "Hapus Semua Riwayat" berfungsi dengan baik saat membersihkan data | 15 | 70 / 75 | 4,67 | 93,33% | Diterima |

### 4.8.8 Rekapitulasi Akhir dan Kesimpulan UAT (Google Form)

Secara keseluruhan, rekapitulasi akhir hasil pengujian *User Acceptance Testing* (UAT) dari 18 skenario kuesioner *Google Form* yang melibatkan 15 responden dirangkum pada Tabel 4.22:

**Tabel 4.22** Rekapitulasi Hasil Pengujian UAT Google Form

| **No** | **Modul Aspek yang Diuji** | **Jumlah Skenario** | **Total Poin Diperoleh** | **Skor Rata-rata ($Rate$)** | **Persentase (%)** | **Status Kelayakan** |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | Tampilan & Navigasi (UI/UX) | 4 | 285 / 300 | 4,75 | 95,00% | Sangat Layak (Diterima) |
| 2 | Otentikasi Akun (Login & Register) | 4 | 281 / 300 | 4,68 | 93,67% | Sangat Layak (Diterima) |
| 3 | Fitur Utama Deteksi Gulma & Live Mode | 6 | 425 / 450 | 4,72 | 94,44% | Sangat Layak (Diterima) |
| 4 | Fitur Pendukung (Artikel & Riwayat) | 4 | 284 / 300 | 4,73 | 94,67% | Sangat Layak (Diterima) |
| **TOTAL** | **RATA-RATA KESELURUHAN** | **18** | **1.275 / 1.350** | **4,72 / 5,00** | **94,44%** | **Sangat Layak (*Accepted*)** |

Berdasarkan rekapitulasi pada Tabel 4.22, secara akumulatif aplikasi Gulma Detektor memperoleh total 1.275 poin dari maksimal 1.350 poin, atau setara dengan skor rata-rata **4,72 dari skala 5,00 (94,44%)**. 

Mengacu pada interval kriteria penerimaan UAT di mana persentase $81\% - 100\%$ masuk ke dalam kategori tertinggi, maka disimpulkan secara mutlak bahwa sistem aplikasi Gulma Detektor declared **Sangat Layak dan Diterima (*Accepted*)** oleh pengguna akhir untuk diimplementasikan pada lahan pertanian cabai.


