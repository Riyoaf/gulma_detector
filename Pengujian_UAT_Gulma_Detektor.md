# Rancangan Kuesioner Pengujian UAT (User Acceptance Testing)
## Aplikasi: Gulma Detektor (Android)

Kuesioner ini dirancang untuk diinput ke dalam **Google Forms (GForm)**. Gunakan fitur **"Add Section" (Tambahkan Bagian)** di Google Forms untuk memisahkan setiap halaman di bawah ini agar survei terlihat profesional dan tidak melelahkan responden.

---

### METODE PENILAIAN (Likert Scale)
Untuk pertanyaan evaluasi fitur, gunakan format **Skala Linier 1 sampai 5** di Google Forms:
* **1** = Sangat Tidak Setuju (STS) / Sangat Buruk
* **2** = Tidak Setuju (TS) / Buruk
* **3** = Netral (N) / Cukup
* **4** = Setuju (S) / Baik
* **5** = Sangat Setuju (SS) / Sangat Baik

---

## HALAMAN 1: Pengantar & Identitas Responden
*(Bagian ini untuk mengetahui siapa yang menguji aplikasi)*

* **Judul Formulir:** Pengujian Pengguna (UAT) - Aplikasi Gulma Detektor
* **Deskripsi Formulir:** 
  > Selamat datang. Kami sedang melakukan pengujian kelayakan dan kemudahan penggunaan aplikasi berbasis Kecerdasan Buatan (AI) **"Gulma Detektor"**. Mohon kesediaan Anda untuk mencoba aplikasi kami di HP Android Anda, lalu menjawab pertanyaan singkat di bawah ini sesuai dengan pengalaman nyata Anda. Data Anda akan dijaga kerahasiaannya.

**Pertanyaan Identitas:**
1. **Nama Lengkap / Inisial:** *(Jawaban Singkat - Wajib)*
2. **Usia Anda saat ini:** *(Jawaban Singkat/Pilihan Ganda: < 20 thn, 20-30 thn, 31-45 thn, > 45 thn - Wajib)*
3. **Profesi / Latar Belakang:** *(Pilihan Ganda - Wajib)*
   - [ ] Petani / Praktisi Pertanian
   - [ ] Penyuluh Pertanian / Instansi Pemerintah
   - [ ] Mahasiswa / Pelajar / Akademisi
   - [ ] Masyarakat Umum / Hobi Berkebun
4. **Merek & Model HP Android yang digunakan:** *(Jawaban Singkat, misal: Samsung A54, Xiaomi Redmi Note 11 - Wajib)*
5. **Seberapa sering Anda menggunakan aplikasi smartphone di keseharian?** *(Pilihan Ganda)*
   - [ ] Sangat Jarang (Hanya untuk telepon/WA)
   - [ ] Cukup Sering (Medsos, Youtube, dll)
   - [ ] Sangat Mahir (Sering mencoba berbagai aplikasi baru)

---

## HALAMAN 2: Pengujian Tampilan & Navigasi (UI/UX)
*(Instruksi untuk Responden: Buka aplikasi Gulma Detektor, amati tampilan awal, warna, dan coba tekan tombol-tombol menu di bagian bawah)*

1. Tampilan antarmuka (UI) aplikasi terlihat modern, menarik, dan rapi. *(Skala 1 - 5)*
2. Ukuran teks tulisan, tombol, dan gambar jelas serta mudah terbaca di HP saya. *(Skala 1 - 5)*
3. Menu navigasi bawah (Home, Artikel, Deteksi, Riwayat, Profil) mudah dipahami posisinya. *(Skala 1 - 5)*
4. Perpindahan antar halaman terasa mulus, cepat, dan tidak bingung saat ingin kembali (*tombol back*). *(Skala 1 - 5)*

---

## HALAMAN 3: Pengujian Otentikasi Akun (Login & Register)
*(Instruksi untuk Responden: Coba lakukan pendaftaran akun baru jika belum punya, atau masuk menggunakan akun yang ada)*

1. Proses pendaftaran akun baru (*Register*) mudah dilakukan. *(Skala 1 - 5)*
2. Proses masuk (*Login*) ke dalam aplikasi lancar tanpa masalah. *(Skala 1 - 5)*
3. Fitur ikon "Mata" (*Show/Hide Password*) sangat membantu saat mengetik kata sandi. *(Skala 1 - 5)*
4. Pesan peringatan (*Alert*) mudah dimengerti ketika sengaja memasukkan email/password yang salah. *(Skala 1 - 5)*

---

## HALAMAN 4: Pengujian Fitur Utama – Deteksi Gulma
*(Instruksi untuk Responden: Masuk ke menu "Deteksi". Uji kedua mode: **[Kamera Manual]** dengan memotong gambar, dan mode **[Detection / Live]** dengan mengarahkan kamera langsung ke tanaman)*

**A. Deteksi Kamera Manual:**
1. Fitur pemotongan gambar (*Crop*) sehabis memotret sangat mudah digunakan untuk memfokuskan objek. *(Skala 1 - 5)*
2. Kecepatan aplikasi dalam memproses dan memunculkan hasil deteksi terasa **sangat cepat**. *(Skala 1 - 5)*
3. Hasil deteksi yang ditampilkan (Warna overlay, persentase kecocokan, dan nama gulma) akurat sesuai objek aslinya. *(Skala 1 - 5)*
4. Penjelasan panduan serta saran penanganan gulma yang diberikan sangat lengkap dan mudah dipahami. *(Skala 1 - 5)*

**B. Deteksi Langsung (Live Mode):**
5. Mode *Live Detection* sangat praktis karena langsung memunculkan warna (Hijau = Tanaman, Merah = Gulma) di layar kamera secara *real-time*. *(Skala 1 - 5)*
6. Topeng warna (*masking*) yang melayang di atas kamera cukup membantu saya mengidentifikasi posisi gulma di antara tanaman. *(Skala 1 - 5)*

---

## HALAMAN 5: Pengujian Fitur Pendukung (Artikel & Riwayat)
*(Instruksi untuk Responden: Buka menu "Artikel" untuk membaca materi edukasi, lalu periksa menu "Riwayat")*

1. Artikel edukasi pertanian yang disediakan beragam, menarik, dan bermanfaat. *(Skala 1 - 5)*
2. Hasil deteksi yang pernah saya lakukan sebelumnya tersimpan dengan rapi di halaman "Riwayat". *(Skala 1 - 5)*
3. Saya dapat membuka kembali detail hasil deteksi lama saya melalui daftar riwayat dengan lancar. *(Skala 1 - 5)*
4. Fitur "Hapus Semua Riwayat" berfungsi dengan baik ketika saya ingin membersihkan data. *(Skala 1 - 5)*

---

## HALAMAN 6: Evaluasi Keseluruhan & Masukan
*(Bagian terakhir untuk menyimpulkan kepuasan responden)*

1. **Secara keseluruhan, seberapa puas Anda dengan kinerja aplikasi Gulma Detektor?** *(Skala 1 - 5: 1 = Sangat Kecewa, 5 = Sangat Puas)*
2. **Menurut Anda, apakah aplikasi ini layak dan bermanfaat jika digunakan oleh para petani di lapangan?** *(Pilihan Ganda)*
   - [ ] Sangat Layak & Sangat Membantu
   - [ ] Cukup Layak (Perlu beberapa perbaikan kecil)
   - [ ] Kurang Layak (Masih banyak kekurangan)
3. **Apa kendala atau kesulitan terbesar yang Anda alami saat menguji aplikasi ini tadi?** *(Jawaban Panjang / Paragraf - Opsional)*
4. **Apa saran atau kritik Anda untuk pengembangan aplikasi Gulma Detektor ke depannya?** *(Jawaban Panjang / Paragraf - Opsional)*

---
### PENUTUP FORMULIR (Pesan Konfirmasi GForm)
> *"Terima kasih banyak atas partisipasi, waktu, dan masukan berharga Anda. Dedikasi Anda sangat berarti bagi kemajuan teknologi pertanian kita."*
