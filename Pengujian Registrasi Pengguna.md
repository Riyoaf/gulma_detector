### Pengujian Registrasi Pengguna

| No | Skenario | Langkah Pengujian | Data Input | Hasil yang diharapkan |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Registrasi dengan data lengkap dan valid | 1. Buka halaman Registrasi.<br>2. Masukkan Nama, Email, Password, dan Konfirmasi Password yang valid.<br>3. Klik tombol "Daftar". | Nama: `Petani Cerdas`<br>Email: `petani@example.com`<br>Password: `Password123!`<br>Confirm: `Password123!` | Akun berhasil dibuat di Supabase, data user tersimpan, notifikasi sukses muncul, dan diarahkan ke halaman Login/Home. |
| 2 | Registrasi dengan email yang sudah terdaftar | 1. Masukkan email yang sudah pernah diregistrasikan.<br>2. Isi field lain dengan valid.<br>3. Klik tombol "Daftar". | Email: `petani@example.com` (sudah ada) | Sistem menolak pendaftaran, muncul pesan error "Email sudah terdaftar". |
| 3 | Registrasi dengan password tidak sesuai konfirmasi | 1. Masukkan data valid.<br>2. Masukkan Password dan Konfirmasi Password yang berbeda.<br>3. Klik tombol "Daftar". | Pass: `PassA`<br>Confirm: `PassB` | Pesan error validasi muncul "Password dan konfirmasi tidak cocok", registrasi gagal. |
| 4 | Registrasi dengan password kurang dari 6 karakter | 1. Masukkan password dengan panjang < 6 karakter.<br>2. Klik tombol "Daftar". | Password: `12345` | Pesan error validasi muncul "Password minimal 6 karakter". |
| 5 | Registrasi dengan field mandatory kosong | 1. Kosongkan satu atau lebih field wajib (misal: Nama).<br>2. Klik tombol "Daftar". | Nama: `[Empty]` | Tombol daftar disabled atau muncul pesan error "Field [Nama] wajib diisi". |

### Pengujian Login Pengguna

| No | Skenario | Langkah Pengujian | Data Input | Hasil yang diharapkan |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Login pengguna dengan kredensial valid | 1. Buka halaman Login.<br>2. Masukkan Email dan Password terdaftar.<br>3. Klik tombol "Masuk". | Email: `petani@example.com`<br>Password: `Password123!` | Otentikasi Supabase berhasil, token sesi dibuat, user masuk ke halaman Home. |
| 2 | Login dengan email belum registrasi | 1. Masukkan email yang belum ada di database.<br>2. Masukkan password sembarang.<br>3. Klik tombol "Masuk". | Email: `unregistered@test.com` | Login gagal, pesan error "User tidak ditemukan" atau "Kredensial salah". |
| 3 | Login dengan password salah | 1. Masukkan email terdaftar.<br>2. Masukkan password yang salah.<br>3. Klik tombol "Masuk". | Pass: `WrongPass` | Login gagal, pesan error "Password salah" atau "Kredensial tidak valid". |

### Pengujian Fitur Artikel dan Pembelajaran

| No | Skenario | Langkah Pengujian | Data Input | Hasil yang diharapkan |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Tampilkan semua list artikel | 1. Buka menu Edukasi.<br>2. Scroll daftar artikel. | - | Daftar artikel termuat dari API/Lokal, menampilkan gambar thumbnail dan judul. |
| 2 | Membaca artikel | 1. Klik salah satu item artikel di list. | Artikel ID: `1` | Halaman detail artikel terbuka, menampilkan konten lengkap artikel tersebut. |
| 3 | Membaca Pembelajaran gulma (Ensiklopedia) | 1. Klik tab/bagian "Jenis Gulma".<br>2. Pilih salah satu jenis gulma. | Gulma ID: `Ageratum` | Detail informasi gulma (nama latin, deskripsi, pengendalian) ditampilkan. |

### Pengujian Fitur Deteksi

| No | Skenario | Langkah Pengujian | Data Input | Hasil yang diharapkan |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Tampilkan kamera | 1. Tap menu Scan/Kamera. | Izin Kamera | Preview kamera terbuka secara real-time. |
| 2 | Uji tombol kamera (Switch) | 1. Tekan tombol switch camera. | - | Kamera berpindah antara lensa depan dan belakang. |
| 3 | Uji tombol flash | 1. Tekan ikon flash. | - | Mode flash berubah (On/Off/Auto). |
| 4 | Ambil dan potong gambar | 1. Tekan tombol shutter.<br>2. Sesuaikan area potong di layar crop. | Foto Gulma | Gambar berhasil diambil dan dicrop sesuai keinginan pengguna. |
| 5 | Analisis gambar | 1. Konfirmasi hasil crop untuk dianalisis. | Gambar crop | Sistem mengirim gambar ke API Model, loading tampil, kemudian hasil deteksi (Masking & Label) muncul. |
| 6 | Ambil gambar dari gallery | 1. Tekan ikon galeri.<br>2. Pilih gambar dari penyimpanan. | File Gambar | Gambar terpilih masuk ke mode crop dan siap dianalisis. |
| 7 | **[BARU]** Tambahkan catatan setelah deteksi | 1. Pada halaman hasil deteksi, cari bagian "Catatan Pengguna".<br>2. Klik "Tambah Catatan".<br>3. Masukkan teks catatan dan Simpan. | Catatan: *"Gulma di petak utara, kepadatan tinggi."* | Input catatan tersimpan ke database (Supabase/Local) dan terasosiasi dengan history deteksi tersebut. Toast sukses "Catatan disimpan". |

### Pengujian Fitur History

| No | Skenario | Langkah Pengujian | Data Input | Hasil yang diharapkan |
| :--- | :--- | :--- | :--- | :--- |
| 1 | Simpan deteksi | 1. Selesaikan proses analisis deteksi.<br>2. Sistem melakukan auto-save atau user menekan save. | Hasil Deteksi | Data deteksi (Gambar, Label, Waktu, Catatan awal) tersimpan di riwayat. |
| 2 | Tampilkan history | 1. Buka menu Riwayat. | - | List riwayat deteksi tampil urut waktu (terbaru diatas). |
| 3 | Detail history | 1. Klik salah satu item riwayat. | Item ID | Halaman detail menampilkan gambar hasil, nama gulma, dan catatan yang tersimpan. |
| 4 | **[BARU]** Ubah (Edit) history | 1. Buka detail history.<br>2. Klik tombol Edit pada catatan.<br>3. Ubah isi catatan.<br>4. Simpan perubahan. | Catatan Baru: *"Sudah disemprot herbisida"* | Catatan berhasil diperbarui di database. Tampilan di detail history berubah sesuai input baru. |
| 5 | **[BARU]** Hapus history | 1. Pada list atau detail history, klik tombol Hapus (ikon tong sampah).<br>2. Konfirmasi penghapusan. | - | Item terhapus dari list dan database. Toast "History dihapus". |
