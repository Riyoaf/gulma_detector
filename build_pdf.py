import sys
from fpdf import FPDF, XPos, YPos

class JuTISI_PDF(FPDF):
    def header(self):
        self.set_font("Times", "I", 8)
        self.cell(0, 5, "p-ISSN : 2443-2210 | e-ISSN : 2685-0893       Jutisi: Jurnal Ilmiah Teknik Informatika dan Sistem Informasi", border="B", new_x=XPos.LMARGIN, new_y=YPos.NEXT, align="R")
        self.ln(5)

    def footer(self):
        self.set_y(-15)
        self.set_font("Times", "I", 8)
        self.cell(0, 10, f"Halaman {self.page_no()}/{{nb}}", align="C")

def sanitize(text):
    replacements = {
        "\u2014": "-", "\u201c": "'", "\u201d": "'",
        "\u2018": "'", "\u00b1": "+/-", "\u2013": "-",
        "\u2022": "-", "\u00d7": "x", "\u2019": "'"
    }
    for old, new in replacements.items():
        text = text.replace(old, new)
    return text

def create_pdf(filename):
    pdf = JuTISI_PDF(format="A4", unit="mm")
    pdf.alias_nb_pages()
    pdf.set_margins(left=20, top=20, right=15)
    pdf.set_auto_page_break(auto=True, margin=20)
    pdf.add_page()

    def body(text):
        pdf.set_font("Times", "", 9)
        pdf.multi_cell(0, 4.5, sanitize(text), align="J")

    def add_heading(text):
        pdf.ln(3)
        pdf.set_font("Times", "B", 10)
        pdf.multi_cell(0, 4.5, sanitize(text), align="L")
        pdf.ln(1)

    def add_subheading(text):
        pdf.ln(1.5)
        pdf.set_font("Times", "B", 9.5)
        pdf.multi_cell(0, 4.2, sanitize(text), align="L")
        pdf.ln(0.8)

    def add_subsubheading(text):
        pdf.ln(1)
        pdf.set_font("Times", "BI", 9)
        pdf.multi_cell(0, 4, sanitize(text), align="L")
        pdf.ln(0.5)

    def add_bullet(text, indent=5):
        pdf.set_font("Times", "", 9)
        pdf.set_x(pdf.l_margin + indent)
        pdf.multi_cell(0, 4.5, sanitize("- " + text), align="J")

    def simple_table_row(cols, widths, is_bold=False, align="L"):
        if is_bold:
            pdf.set_font("Times", "B", 7.5)
        else:
            pdf.set_font("Times", "", 7.5)
        for i, col in enumerate(cols):
            pdf.cell(widths[i], 4.5, sanitize(col), border=1, align=align)
        pdf.ln()

    def add_code_block(lines):
        pdf.set_font("Courier", "", 7)
        for line in lines:
            pdf.set_x(pdf.l_margin + 3)
            pdf.cell(0, 3.8, sanitize(line), new_x=XPos.LMARGIN, new_y=YPos.NEXT)
        pdf.ln(1)

    # JUDUL & PENGARANG
    pdf.set_font("Times", "B", 14)
    pdf.multi_cell(0, 6.5, sanitize("Rancang Bangun Aplikasi Mobile Deteksi Dini Gulma pada Lahan Cabai Berbasis Android Menggunakan Segmentasi Semantik U-Net dan MobileNetV2"), align="C")
    pdf.ln(3)
    pdf.set_font("Times", "B", 10)
    pdf.cell(0, 4.5, sanitize("Riyo Andika Febriyan1, Dewa Made Sri Arsa2, Desy Purnama Singgih Putri3"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.set_font("Times", "I", 9)
    pdf.cell(0, 4.5, sanitize("1,2,3 Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.cell(0, 4.5, sanitize("Jalan Raya Kampus Udayana, Jimbaran, Badung, Bali, Indonesia"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.set_font("Courier", "", 8)
    pdf.cell(0, 4.5, sanitize("1riyoandikafebriyan@gmail.com | 2sriarsa@unud.ac.id | 3desypurnama@unud.ac.id"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(4)

    # ABSTRAK INDONESIA
    pdf.set_font("Times", "B", 8.5)
    pdf.write(4.5, sanitize("Abstrak - "))
    pdf.set_font("Times", "", 8.5)
    pdf.write(4.5, sanitize("Sektor pertanian cabai (Capsicum annuum L.) di Indonesia menghadapi ancaman penurunan hasil panen akibat kompetisi gulma pada periode kritis vegetatif awal, yaitu 2 minggu setelah tanam (2 MST). Penyiangan manual terkendala biaya tenaga kerja tinggi dan risiko kesalahan pencabutan (human error) yang signifikan karena kemiripan morfologi antara bibit gulma dan bibit cabai muda. Penelitian ini merancang dan membangun aplikasi Android deteksi dini gulma berbasis Computer Vision dan Semantic Segmentation menggunakan model U-Net berbasis backbone MobileNetV2 yang dikonversi ke format TensorFlow Lite (21,15 MB) untuk eksekusi inferensi lokal offline on-device tanpa ketergantungan internet. Aplikasi dikembangkan dengan Android Studio, Kotlin, dan Jetpack Compose, terintegrasi dengan Supabase Cloud untuk autentikasi dan riwayat lahan, serta Retrofit REST API untuk artikel edukasi. Pengujian penerimaan pengguna (User Acceptance Testing / UAT) menggunakan kuesioner skala Likert kepada 15 responden menghasilkan persentase kelayakan 94,44% (skor 4,72 dari 5,00) dengan kategori Sangat Layak (Accepted).\n"))
    pdf.set_font("Times", "B", 8.5)
    pdf.write(4.5, sanitize("Kata kunci - "))
    pdf.set_font("Times", "I", 8.5)
    pdf.write(4.5, sanitize("Android; aplikasi mobile; cabai; deteksi gulma; MobileNetV2; segmentasi semantik; U-Net; User Acceptance Testing.\n\n"))

    # ABSTRAK INGGRIS
    pdf.set_font("Times", "BI", 11)
    pdf.multi_cell(0, 5, sanitize("Development of Android-Based Mobile Application for Early Weed Detection on Chili Fields Using U-Net Semantic Segmentation and MobileNetV2"), align="C")
    pdf.ln(2)
    pdf.set_font("Times", "B", 8.5)
    pdf.write(4.5, sanitize("Abstract - "))
    pdf.set_font("Times", "I", 8.5)
    pdf.write(4.5, sanitize("Chili (Capsicum annuum L.) production in Indonesia faces serious threats from yield losses due to weed competition during the critical early vegetative period (2 weeks after planting). Manual weeding is constrained by high labor costs and risk of human error, as weed seedlings morphologically mimic young chili sprouts. This study designs and develops an Android application for early weed detection using Computer Vision and Semantic Segmentation with a U-Net model and MobileNetV2 backbone, converted to TensorFlow Lite format (21.15 MB) for offline on-device inference. Built using Android Studio, Kotlin, and Jetpack Compose, integrated with Supabase Cloud and Retrofit REST API. User Acceptance Testing (UAT) with 15 respondents achieved 94.44% feasibility (average 4.72/5.00), categorized as Highly Feasible (Accepted).\n"))
    pdf.set_font("Times", "B", 8.5)
    pdf.write(4.5, sanitize("Keywords - "))
    pdf.set_font("Times", "I", 8.5)
    pdf.write(4.5, sanitize("Android; chili; MobileNetV2; mobile application; semantic segmentation; U-Net; User Acceptance Testing; weed detection.\n\n"))

    # BAB 1: PENDAHULUAN
    add_heading("1. PENDAHULUAN")
    body("Tanaman cabai (Capsicum annuum L.) merupakan komoditas hortikultura strategis di Indonesia dengan nilai ekonomi tinggi dan permintaan konsumsi harian yang konsisten [1]. Namun, produktivitas lahan cabai mengalami tekanan akibat keberadaan gulma yang menciptakan persaingan langsung dalam memperebutkan nutrisi tanah, air, cahaya, dan ruang tumbuh [2].")
    pdf.ln(2)
    body("Persaingan paling destruktif terjadi pada Periode Kritis Vegetatif Awal (2 MST). Pada fase ini, perakaran bibit cabai belum cukup dalam sehingga monopoli nutrisi oleh gulma dapat menyebabkan penurunan panen 40-50% [3]. Penyiangan manual membutuhkan tenaga kerja intensif, biaya tinggi, dan risiko kesalahan pencabutan (human error) sangat tinggi karena kemiripan morfologi daun bibit gulma - terutama golongan daun lebar - dengan daun bibit cabai muda [4],[5].")
    pdf.ln(2)
    body("Teknik Segmentasi Semantik merupakan pendekatan Computer Vision tingkat tinggi yang melakukan klasifikasi piksel demi piksel (pixel-level classification) [2],[8], menghasilkan topeng warna piksel yang mengikuti kontur asli daun secara presisi. Kemampuan ini memungkinkan pemisahan visual gulma-cabai bahkan dalam kondisi daun bertumpukan. Berdasarkan identifikasi celah penelitian, penelitian ini merancang aplikasi Android 'Gulma Detektor' yang mengintegrasikan model U-Net + MobileNetV2 dalam format TensorFlow Lite (21,15 MB) untuk eksekusi inferensi lokal 100% offline on-device, dilengkapi logbook riwayat Supabase Cloud dan fitur edukasi Retrofit REST API, divalidasi melalui User Acceptance Testing (UAT).")

    # BAB 2: TINJAUAN PUSTAKA
    add_heading("2. TINJAUAN PUSTAKA")

    add_subheading("2.1. Kajian Penelitian Terdahulu (State of the Art)")
    body("Islam et al. (2021) [1] menyelidiki deteksi gulma pada lahan cabai di Australia menggunakan UAV dan Machine Learning konvensional. Keterbatasan utama: investasi UAV sangat tinggi dan tidak praktis bagi petani skala kecil. Hashemi-Beni et al. (2020) [2] membandingkan U-Net vs FCN-8s untuk segmentasi wortel organik; U-Net meraih akurasi global 75,2% vs FCN-8s 72,1%, membuktikan superioritas skip connections untuk batas tepi piksel presisi.")
    pdf.ln(2)
    body("R et al. (2025) [3] menganalisis klasifikasi CNN untuk bibit cabai-gulma; MobileNetV2 meraih akurasi 96,6%, mengungguli ResNet50 (95,0%) dan VGG16 (88,0%), dengan efisiensi Depthwise Separable Convolution yang ideal untuk perangkat bergerak. Khan et al. (2020) [4] mengusulkan CED-Net untuk segmentasi gulma yang efisien. Yu et al. (2022) [5] memodifikasi DeepLabV3+ dengan MobileNetV2 + CBAM. Li et al. (2025) [6] memodernisasi U-Net mencapai mIoU 91,02%. Kusuma et al. [10], Ariatmaja et al. [11], Suarjaya et al. [12], dan Wijaya et al. [13] menekankan optimasi antarmuka Android yang efisien memori.")

    add_subheading("2.2. Sintesis Celah Penelitian dan Kebaruan")
    body("Mayoritas sistem terdahulu: (1) mengandalkan UAV berbiaya tinggi [1]; (2) menghasilkan klasifikasi image-level yang tidak melokalisasi piksel gulma [3]; atau (3) mengeksekusi model di server GPU yang memerlukan internet stabil [2],[4],[5],[6]. Kebaruan penelitian ini: model U-Net + MobileNetV2 dikonversi ke TFLite (21,15 MB) untuk 100% offline on-device inference; target domain spesifik cabai 2 MST + 3 golongan gulma divisualisasikan via overlay mask transparan; platform edukasi terintegrasi (logbook Supabase + artikel Retrofit).")

    add_subheading("2.3. Landasan Teori")
    add_subsubheading("A. Tanaman Cabai 2 MST dan Morfologi Gulma")
    body("Fase 2 MST merupakan 'Periode Kritis' dimana perakaran cabai dangkal dan rentan kompetisi. Tiga gulma target: (1) Eleusine indica - rumputan dengan daun pita; (2) Cyperus rotundus - teki dengan batang segitiga; (3) Amaranthus spinosus - daun lebar dengan kemiripan visual tertinggi dengan cabai muda.")
    add_subsubheading("B. Segmentasi Semantik Piksel")
    body("Teknik Computer Vision yang mengklasifikasikan setiap piksel citra ke kelas kategori. Citra masukan 512x512x3 (RGB) diproses menjadi peta prediksi 512x512x1: Kelas 0 (Background/Tanah - bening), Kelas 1 (Tanaman Cabai - Topeng Hijau #00FF00 opacity 40%), Kelas 2 (Gulma - Topeng Merah #FF0000 opacity 50%). Berbeda dengan bounding box, segmentasi semantik menghasilkan mask yang mengikuti kontur asli piksel secara tepat [8].")
    add_subsubheading("C. Arsitektur U-Net, MobileNetV2, TFLite, dan Metode RAD")
    body("U-Net [8] terdiri dari Encoder dan Decoder yang dihubungkan Skip Connections, mempertahankan informasi spasial resolusi tinggi untuk batas tepi yang presisi. MobileNetV2 [7] menggunakan Depthwise Separable Convolution (mengurangi FLOPs 8-9x) dan Inverted Residual Blocks - backbone ideal untuk perangkat bergerak. TFLite memungkinkan inferensi tanpa internet dengan latensi rendah. RAD (Rapid Application Development) adalah metodologi pengembangan cepat berbasis prototipe iteratif: (1) Requirements Planning, (2) User Design & Construction, (3) Cutover/Testing.")

    # BAB 3: METODOLOGI
    add_heading("3. METODOLOGI PENELITIAN")

    add_subheading("3.1. Tempat, Waktu, dan Sumber Data")
    body("Penelitian dilaksanakan Juli 2025 - Juli 2026 di laboratorium Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana, Jimbaran, Badung, Bali. Akuisisi dataset citra dan pengujian UAT di lahan pertanian cabai Desa Anggabaya, Penatih, Denpasar, Bali. Data primer: citra digital dikumpulkan mandiri dari ketinggian 30-50 cm pada pencahayaan alami pukul 08.00-11.00 WITA, meliputi bibit cabai 2 MST dan 3 golongan gulma. Seluruh citra dianotasi piksel demi piksel (pixel-level annotation) untuk ground truth mask pelatihan model.")

    add_subheading("3.2. Metode RAD - Rapid Application Development")
    body("Tahap 1 - Requirements Planning: Identifikasi permasalahan petani dalam mengenali gulma visual. Merumuskan kebutuhan fungsional (fitur deteksi, edukasi, riwayat, autentikasi) dan non-fungsional (inferensi < 1 detik, offline, mudah digunakan). Menentukan arsitektur AI (U-Net + MobileNetV2), platform (Android Studio, Kotlin), backend (Supabase, Retrofit), dan validasi (UAT).")
    pdf.ln(2)
    body("Tahap 2 - User Design & Construction: (a) Perancangan antarmuka UI/UX via Figma prinsip mobile-first; (b) Pengembangan model AI melalui eksperimen komparatif arsitektur (U-Net, DeepLabV3+), backbone (MobileNetV2, MobileViT, EfficientViT), dan loss function (Cross-Entropy, Tversky Loss) di Google Colab GPU T4; (c) Pengodean Android: Kotlin + Jetpack Compose dengan integrasi TFLite, Supabase, dan Retrofit.")
    pdf.ln(2)
    body("Tahap 3 - Cutover/Testing: Pengujian kelayakan sistem menggunakan User Acceptance Testing (UAT) - kuesioner skala Likert via Google Form kepada 15 responden (petani, penyuluh, akademisi).")

    add_subheading("3.3. Instrumen Perancangan dan Pembuatan Sistem")
    body("Rincian lengkap instrumen hardware dan software penelitian disajikan pada Tabel 1.")
    pdf.ln(2)
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 1 RINCIAN INSTRUMEN HARDWARE DAN SOFTWARE PENELITIAN"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    w = [30, 42, 103]
    pdf.set_font("Times", "B", 7.5)
    for col, width in zip(["Perangkat", "Jenis", "Spesifikasi / Keterangan"], w):
        pdf.cell(width, 5, sanitize(col), border=1, align="C")
    pdf.ln()
    rows_t1 = [
        ("Hardware", "Laptop Pengembang", "ACER Predator Helios Neo 16 (Intel Core i5-13500HX, RAM 8GB DDR5, GPU NVIDIA RTX 4050 6GB)"),
        ("Hardware", "Smartphone Android", "Perangkat pengujian inferensi kamera on-device di lapangan"),
        ("Software", "Sistem Operasi", "Windows 11 Home (Lingkungan Pengembangan Lokal)"),
        ("Software", "Environment Pelatihan", "Google Colab (Python 3.x, TF/Keras, GPU Tesla T4 VRAM 16GB, RAM 25GB)"),
        ("Software", "IDE Pengembangan", "Android Studio (Kotlin, Jetpack Compose, CameraX, TFLite Interpreter)"),
        ("Software", "Desain UI/UX", "Figma (Wireframe & Mockup Antarmuka)"),
        ("Software", "Backend Cloud", "Supabase Cloud (Autentikasi JWT & Basis Data PostgreSQL)"),
        ("Software", "Client REST API", "Retrofit 2 (Artikel Edukasi & Ensiklopedia Gulma)"),
        ("Software", "Mesin AI On-Device", "TFLite (best_model_unet_tversky_float32.tflite, 21,15 MB, Float32)"),
    ]
    for row in rows_t1:
        simple_table_row(list(row), w)
    pdf.ln(3)

    add_subheading("3.4. Mekanisme Segmentasi Semantik Tingkat Piksel")
    body("Proses segmentasi dimulai ketika pengguna mengambil foto lahan cabai. Citra dipra-proses (resizing 512x512 piksel, normalisasi [0.0, 1.0]) sebelum diumpankan ke model. Model U-Net + MobileNetV2 memproses matriks input [512x512x3] menghasilkan prediksi kelas [512x512x3] berupa skor softmax untuk tiga kelas:")
    pdf.ln(2)
    add_bullet("Kelas 0 - Background (Tanah/Mulsa/Bayangan): Tampil bening tanpa lapisan warna.")
    add_bullet("Kelas 1 - Tanaman Cabai: Topeng Hijau Transparan (#00FF00, opacity 40%).")
    add_bullet("Kelas 2 - Gulma (Rumputan/Teki/Daun Lebar): Topeng Merah Transparan (#FF0000, opacity 50%).")
    pdf.ln(2)
    body("Secara teknis, WeedClassifier.kt menginisialisasi org.tensorflow.lite.Interpreter. Citra bitmap dikonversi ke ByteBuffer (512x512x3x4 byte, float32). Setelah inferensi, nilai argmax per piksel (x,y) menentukan kelas prediksi. Piksel Kelas 1 dicat hijau dan Kelas 2 dicat merah transparan, lalu di-blend ke foto asli via alpha compositing pada Canvas Android. Alur: Input -> TFLite Interpreter -> Argmax/Pixel -> Color Map -> Alpha Blend -> Overlay Bitmap. [Gambar 1]")

    add_subheading("3.5. Arsitektur Sistem, UML, Konversi Model, dan Rancangan UAT")
    body("Arsitektur sistem menggunakan three-tier mobile architecture: (1) Presentation Layer - Jetpack Compose + CameraX API; (2) Application Layer - WeedClassifier.kt (TFLite Interpreter), pola MVVM; (3) Data Layer - Supabase Cloud (JWT auth + PostgreSQL logbook), Retrofit REST API (artikel edukasi), Local Assets (berkas .tflite). [Gambar 2]")
    pdf.ln(2)
    body("Use Case Diagram memiliki satu aktor utama (Pengguna/Petani) dengan 6 use case: UC1 Register Akun, UC2 Login, UC3 Deteksi Gulma (<<include>> Tambah & Simpan Catatan), UC4 Live Detection, UC5 Kelola Riwayat (View/Update/Delete), UC6 Baca Artikel & Ensiklopedia. [Gambar 3]")
    pdf.ln(2)
    body("Konversi model terbaik (U-Net + MobileNetV2 + Tversky Loss): PyTorch (.pth) -> ONNX -> TF SavedModel -> TFLite (.tflite, 21,15 MB Float32). Rancangan UAT: 18 skenario Likert 5-tingkat dalam 4 modul, 15 responden. Formula kelayakan: Persentase (%) = (Total Poin) / (N x 5) x 100%. Kriteria: 81%-100% = Sangat Layak.")

    # BAB 4: HASIL DAN PEMBAHASAN
    add_heading("4. HASIL DAN PEMBAHASAN")

    add_subheading("4.1. Hasil Implementasi Model Segmentasi Semantik")
    body("Model U-Net + MobileNetV2 dengan Tversky Loss + Cross-Entropy dipilih berdasarkan keseimbangan akurasi, sensitivitas, dan efisiensi ukuran. Hasil evaluasi disajikan pada Tabel 2.")
    pdf.ln(2)
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 2 EVALUASI MODEL U-NET + MOBILENETV2 (TVERSKY LOSS + CROSS-ENTROPY)"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    w2 = [55, 28, 28, 28, 28, 8]
    simple_table_row(["Kelas", "IoU", "Presisi", "Recall", "F1-Score", ""], w2, is_bold=True, align="C")
    rows_t2 = [
        ("Background (Tanah)", "0,9589", "0,9940", "0,9645", "0,9790", ""),
        ("Tanaman Cabai", "0,8533", "0,8778", "0,9683", "0,9208", ""),
        ("Gulma", "0,6397", "0,6754", "0,9238*", "0,7803", ""),
        ("Global Accuracy", "", "", "0,9620", "", ""),
        ("Mean IoU (mIoU)", "", "", "0,8173", "", ""),
        ("Ukuran Model .tflite", "", "", "21,15 MB", "", "F32"),
    ]
    for row in rows_t2:
        simple_table_row(list(row), w2)
    pdf.set_font("Times", "I", 7.5)
    pdf.cell(0, 4, sanitize("*Recall Gulma 92,38% = hampir seluruh area gulma berhasil terdeteksi dan ditandai."), new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(3)

    add_subheading("4.2. Hasil Implementasi Antarmuka Pengguna")
    body("Antarmuka diimplementasikan sepenuhnya dengan Kotlin dan Jetpack Compose, menghasilkan 5 modul layar utama yang terintegrasi via bottom navigation bar:")

    add_subsubheading("4.2.1. Halaman Utama (Home Screen) - [Gambar 4]")
    body("Menyajikan: Header sapaan personal; Banner edukasi periode kritis gulma 2 MST yang menonjol sebagai pengingat urgensi; Dua tombol aksi cepat berukuran besar ('Scan Kamera Deteksi' dan 'Ambil dari Galeri'); Ensiklopedia botani tiga golongan gulma target dalam format kartu informasi.")

    add_subsubheading("4.2.2. Halaman Pemindaian Kamera & Crop (Scan Screen) - [Gambar 5]")
    body("Memanfaatkan CameraX API untuk live preview real-time. Dilengkapi: bingkai crop 1:1 yang dapat digeser; tombol Switch Camera (depan/belakang); pengatur Flash (Otomatis/Aktif/Nonaktif); dan akses Galeri untuk foto yang sudah diambil. Setelah foto diambil, layar crop terbuka untuk presisi area fokus sebelum dianalisis AI.")

    add_subsubheading("4.2.3. Halaman Hasil Deteksi & Overlay Masking - [Gambar 6]")
    body("Inti inovasi aplikasi. WeedClassifier.kt memproses citra via TFLite dalam < 1 detik menampilkan: (1) Overlay Mask Transparan - daun cabai Topeng Hijau (#00FF00, 40%), daun gulma Topeng Merah (#FF0000, 50%), tanah bening - mengikuti kontur piksel presisi; (2) Label Diagnosis (contoh: 'Terdeteksi Gulma Daun Lebar'); (3) Rekomendasi Penanganan Agronomi kontekstual; (4) Formulir catatan lahan; (5) Tombol Simpan ke Riwayat Supabase.")

    add_subsubheading("4.2.4. Halaman Riwayat (History Screen) - [Gambar 7]")
    body("Logbook digital lahan berbasis Supabase Cloud. Daftar kartu riwayat kronologis dengan thumbnail overlay, label, stempel waktu, dan catatan. Mendukung Detail, Update catatan, Delete (per item maupun Hapus Semua).")

    add_subsubheading("4.2.5. Halaman Pusat Edukasi & Artikel (Article Screen) - [Gambar 8]")
    body("Platform edukasi agronomi digital via Retrofit REST API. Tab Artikel Edukasi (manajemen penyiangan, pengenalan gulma, teknik pengendalian) dan Tab Ensiklopedia Gulma (katalog botani tiga golongan gulma dengan morfologi dan rekomendasi pengendalian).")

    add_subheading("4.3. Hasil Pengujian User Acceptance Testing (UAT)")
    body("Pengujian UAT merupakan tahap validasi final melibatkan 15 responden pada 18 skenario kuesioner skala Likert dalam 4 modul evaluasi.")
    pdf.ln(2)

    # Tabel 3 Demografi
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 3 PROFIL DEMOGRAFI RESPONDEN PENGUJI UAT"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    w3 = [12, 85, 38, 40]
    simple_table_row(["No", "Kategori Profesi / Latar Belakang", "Jumlah (N)", "Persentase (%)"], w3, is_bold=True, align="C")
    rows_demo = [
        ("1", "Petani Cabai / Praktisi Pertanian", "6 Orang", "40,0%"),
        ("2", "Penyuluh Pertanian / Instansi Terkait", "4 Orang", "26,7%"),
        ("3", "Mahasiswa / Pelajar / Akademisi", "3 Orang", "20,0%"),
        ("4", "Masyarakat Umum / Hobi Berkebun", "2 Orang", "13,3%"),
        ("TOTAL", "KESELURUHAN RESPONDEN", "15 Orang", "100,0%"),
    ]
    for i, row in enumerate(rows_demo):
        simple_table_row(list(row), w3, is_bold=(i == 4))
    pdf.ln(3)

    # 4 Tabel UAT per Modul
    wuat = [10, 70, 12, 22, 20, 22, 19]

    # Modul UI/UX
    add_subsubheading("4.3.1. Modul Tampilan & Navigasi (UI/UX)")
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 4 HASIL UAT MODUL TAMPILAN & NAVIGASI (UI/UX)"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    simple_table_row(["No", "Skenario Pertanyaan", "N", "Total Poin", "Rata-rata", "Persentase", "Status"], wuat, is_bold=True, align="C")
    for i, row in enumerate([
        ("1", "Tampilan UI aplikasi modern, menarik, dan rapi", "15", "71/75", "4,73", "94,67%", "Diterima"),
        ("2", "Teks, tombol, gambar jelas serta mudah terbaca", "15", "72/75", "4,80", "96,00%", "Diterima"),
        ("3", "Menu navigasi bawah mudah dipahami posisinya", "15", "72/75", "4,80", "96,00%", "Diterima"),
        ("4", "Perpindahan halaman mulus dan tidak membingungkan", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("SUBTOTAL", "MODUL UI/UX", "15", "285/300", "4,75", "95,00%", "Sangat Layak"),
    ]):
        simple_table_row(list(row), wuat, is_bold=(i == 4))
    pdf.ln(2)
    body("Modul UI/UX meraih 285/300 poin (95,00%, rata-rata 4,75). Menu navigasi dan keterbacaan teks mendapat skor sempurna 72/75 (96,00%), mengindikasikan desain antarmuka berhasil mengakomodasi pengguna dari berbagai tingkat familiaritas dengan smartphone.")
    pdf.ln(2)

    # Modul Otentikasi
    add_subsubheading("4.3.2. Modul Otentikasi Akun (Login & Register)")
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 5 HASIL UAT MODUL OTENTIKASI AKUN"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    simple_table_row(["No", "Skenario Pertanyaan", "N", "Total Poin", "Rata-rata", "Persentase", "Status"], wuat, is_bold=True, align="C")
    for i, row in enumerate([
        ("1", "Proses Register akun baru mudah dilakukan", "15", "68/75", "4,53", "90,67%", "Diterima"),
        ("2", "Proses Login ke aplikasi lancar tanpa masalah", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("3", "Fitur Show/Hide Password sangat membantu", "15", "73/75", "4,87", "97,33%", "Diterima"),
        ("4", "Pesan Alert mudah dimengerti saat salah input", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("SUBTOTAL", "MODUL OTENTIKASI", "15", "281/300", "4,68", "93,67%", "Sangat Layak"),
    ]):
        simple_table_row(list(row), wuat, is_bold=(i == 4))
    pdf.ln(2)
    body("Modul otentikasi meraih 281/300 poin (93,67%, rata-rata 4,68). Fitur Show/Hide Password mendapat skor tertinggi keseluruhan (73/75, 97,33%) - terbukti sangat diapresiasi petani yang sering keliru mengetik kata sandi.")
    pdf.ln(2)

    # Modul Deteksi
    add_subsubheading("4.3.3. Modul Fitur Utama Deteksi Gulma & Live Mode")
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 6 HASIL UAT FITUR UTAMA DETEKSI GULMA & LIVE MODE"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    simple_table_row(["No", "Skenario Pertanyaan", "N", "Total Poin", "Rata-rata", "Persentase", "Status"], wuat, is_bold=True, align="C")
    for i, row in enumerate([
        ("1", "Fitur Crop sehabis memotret sangat mudah", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("2", "Kecepatan memproses hasil deteksi sangat cepat", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("3", "Overlay mask & label klasifikasi akurat", "15", "71/75", "4,73", "94,67%", "Diterima"),
        ("4", "Panduan & saran penanganan gulma lengkap", "15", "72/75", "4,80", "96,00%", "Diterima"),
        ("5", "Live Detection memunculkan warna real-time", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("6", "Topeng warna membantu identifikasi posisi gulma", "15", "72/75", "4,80", "96,00%", "Diterima"),
        ("SUBTOTAL", "MODUL FITUR UTAMA DETEKSI", "15", "425/450", "4,72", "94,44%", "Sangat Layak"),
    ]):
        simple_table_row(list(row), wuat, is_bold=(i == 6))
    pdf.ln(2)
    body("Modul fitur utama meraih 425/450 poin (94,44%, rata-rata 4,72). Panduan penanganan gulma dan efektivitas topeng warna transparan keduanya meraih 72/75 (96,00%). Penyuluh pertanian mengkonfirmasi visualisasi topeng merah signifikan membantu petani mengambil keputusan penyiangan lebih percaya diri.")
    pdf.ln(2)

    # Modul Pendukung
    add_subsubheading("4.3.4. Modul Fitur Pendukung (Artikel & Riwayat)")
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 7 HASIL UAT MODUL FITUR PENDUKUNG"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    simple_table_row(["No", "Skenario Pertanyaan", "N", "Total Poin", "Rata-rata", "Persentase", "Status"], wuat, is_bold=True, align="C")
    for i, row in enumerate([
        ("1", "Artikel edukasi pertanian beragam dan bermanfaat", "15", "72/75", "4,80", "96,00%", "Diterima"),
        ("2", "Hasil deteksi tersimpan rapi di halaman Riwayat", "15", "71/75", "4,73", "94,67%", "Diterima"),
        ("3", "Detail hasil deteksi lama dapat dibuka lancar", "15", "71/75", "4,73", "94,67%", "Diterima"),
        ("4", "Hapus Semua Riwayat berfungsi baik", "15", "70/75", "4,67", "93,33%", "Diterima"),
        ("SUBTOTAL", "MODUL FITUR PENDUKUNG", "15", "284/300", "4,73", "94,67%", "Sangat Layak"),
    ]):
        simple_table_row(list(row), wuat, is_bold=(i == 4))
    pdf.ln(2)
    body("Modul pendukung meraih 284/300 poin (94,67%, rata-rata 4,73). Artikel edukasi mendapat skor tertinggi (96,00%), membuktikan petani membutuhkan sumber pengetahuan mudah diakses untuk meningkatkan literasi pengelolaan lahan secara mandiri.")
    pdf.ln(2)

    # Tabel 8 Master 18 skenario
    add_subsubheading("4.3.5. Rincian Poin Seluruh 18 Skenario UAT")
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 8 RINCIAN TOTAL POIN SELURUH SKENARIO UAT (1-18)"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    w8 = [10, 22, 60, 10, 22, 20, 22, 9]
    simple_table_row(["No", "Modul", "Deskripsi Skenario", "N", "Total", "Rata-rata", "Persentase", "Sts"], w8, is_bold=True, align="C")
    rows_m = [
        ("1", "UI/UX", "Tampilan UI modern, menarik, dan rapi", "15", "71/75", "4,73", "94,67%", "OK"),
        ("2", "UI/UX", "Teks, tombol, gambar jelas dan terbaca", "15", "72/75", "4,80", "96,00%", "OK"),
        ("3", "UI/UX", "Menu navigasi bawah mudah dipahami", "15", "72/75", "4,80", "96,00%", "OK"),
        ("4", "UI/UX", "Perpindahan halaman mulus dan cepat", "15", "70/75", "4,67", "93,33%", "OK"),
        ("5", "Otent.", "Proses Register mudah dilakukan", "15", "68/75", "4,53", "90,67%", "OK"),
        ("6", "Otent.", "Proses Login lancar tanpa masalah", "15", "70/75", "4,67", "93,33%", "OK"),
        ("7", "Otent.", "Fitur Show/Hide Password membantu", "15", "73/75", "4,87", "97,33%", "OK"),
        ("8", "Otent.", "Pesan Alert mudah dimengerti", "15", "70/75", "4,67", "93,33%", "OK"),
        ("9", "Deteksi", "Fitur Crop sehabis memotret mudah", "15", "70/75", "4,67", "93,33%", "OK"),
        ("10", "Deteksi", "Kecepatan deteksi sangat cepat", "15", "70/75", "4,67", "93,33%", "OK"),
        ("11", "Deteksi", "Overlay mask & label akurat", "15", "71/75", "4,73", "94,67%", "OK"),
        ("12", "Deteksi", "Panduan & saran penanganan lengkap", "15", "72/75", "4,80", "96,00%", "OK"),
        ("13", "Deteksi", "Live Detection real-time memunculkan warna", "15", "70/75", "4,67", "93,33%", "OK"),
        ("14", "Deteksi", "Topeng warna membantu identifikasi posisi gulma", "15", "72/75", "4,80", "96,00%", "OK"),
        ("15", "Pend.", "Artikel edukasi beragam dan bermanfaat", "15", "72/75", "4,80", "96,00%", "OK"),
        ("16", "Pend.", "Riwayat tersimpan rapi di halaman History", "15", "71/75", "4,73", "94,67%", "OK"),
        ("17", "Pend.", "Detail deteksi lama dapat dibuka lancar", "15", "71/75", "4,73", "94,67%", "OK"),
        ("18", "Pend.", "Hapus Semua Riwayat berfungsi baik", "15", "70/75", "4,67", "93,33%", "OK"),
    ]
    for row in rows_m:
        simple_table_row(list(row), w8)
    pdf.ln(3)

    # Rekapitulasi Final
    add_subsubheading("4.3.6. Rekapitulasi Final dan Kesimpulan UAT")
    pdf.set_font("Times", "B", 9)
    pdf.cell(0, 4.5, sanitize("Tabel 9 REKAPITULASI FINAL HASIL PENGUJIAN USER ACCEPTANCE TESTING (UAT)"), align="C", new_x=XPos.LMARGIN, new_y=YPos.NEXT)
    pdf.ln(1)
    w9 = [12, 55, 22, 30, 22, 22, 32]
    simple_table_row(["No", "Modul Aspek yang Diuji", "Skenario", "Total Poin", "Rata-rata", "Persentase", "Status Kelayakan"], w9, is_bold=True, align="C")
    for i, row in enumerate([
        ("1", "Tampilan & Navigasi (UI/UX)", "4", "285/300", "4,75", "95,00%", "Sangat Layak"),
        ("2", "Otentikasi Akun (Login & Register)", "4", "281/300", "4,68", "93,67%", "Sangat Layak"),
        ("3", "Fitur Utama Deteksi Gulma & Live Mode", "6", "425/450", "4,72", "94,44%", "Sangat Layak"),
        ("4", "Fitur Pendukung (Artikel & Riwayat)", "4", "284/300", "4,73", "94,67%", "Sangat Layak"),
        ("TOTAL", "RATA-RATA KESELURUHAN", "18", "1.275/1.350", "4,72", "94,44%", "Sangat Layak (Accepted)"),
    ]):
        simple_table_row(list(row), w9, is_bold=(i == 4))
    pdf.ln(2)
    body("Secara akumulatif, aplikasi Gulma Detektor meraih total 1.275 poin dari maksimal 1.350 poin (skor rata-rata 4,72 dari 5,00 / 94,44%). Berdasarkan kriteria kelayakan UAT (81%-100% = Sangat Layak), disimpulkan aplikasi dinyatakan Sangat Layak dan Diterima (Accepted) oleh pengguna akhir untuk diimplementasikan di lahan pertanian cabai.")

    add_subheading("4.4. Pembahasan Akademik Komprehensif")
    body("Pertama - Validasi On-Device Inference: Kelayakan UAT 94,44% memvalidasi bahwa TFLite (21,15 MB) mampu dieksekusi efektif (< 1 detik) di Android standar tanpa GPU server atau internet - mengatasi hambatan infrastruktur di wilayah pertanian pedesaan.")
    pdf.ln(2)
    body("Kedua - Superioritas Overlay Mask: Skor tinggi 'efektivitas topeng warna' (96,00%) mengkonfirmasi visualisasi mask piksel-per-piksel jauh lebih efektif dibanding label teks saja. Petani dengan literasi botani terbatas dapat intuitif mengikuti panduan visual warna merah untuk penyiangan yang tepat.")
    pdf.ln(2)
    body("Ketiga - Nilai Tambah Edukasi Terintegrasi: Skor artikel edukasi (96,00%) membuktikan fitur edukasi menciptakan ekosistem pembelajaran berkelanjutan bagi petani dalam satu platform bersama fitur deteksi.")
    pdf.ln(2)
    body("Keempat - Efektivitas Tversky Loss: Recall Gulma 92,38% memastikan hampir seluruh gulma teridentifikasi. Ukuran model 21,15 MB vs model segmentasi GPU-dependent (150-300 MB) membuktikan efektivitas strategi TFLite Float32 untuk mobile deployment.")

    # BAB 5: SIMPULAN
    add_heading("5. SIMPULAN")
    body("Penelitian ini berhasil merancang dan membangun aplikasi mobile Android 'Gulma Detektor' (WeedGuard) yang mengintegrasikan segmentasi semantik berbasis Computer Vision. Sistem menggunakan model U-Net dengan backbone MobileNetV2 + Tversky Loss, menghasilkan Global Accuracy 96,20%, Mean IoU 81,73%, dan Recall Gulma 92,38%. Model dikonversi ke TFLite berukuran 21,15 MB untuk eksekusi lokal 100% offline on-device.")
    pdf.ln(2)
    body("Aplikasi terdiri dari 5 modul terintegrasi: Halaman Utama, Pemindaian Kamera & Galeri, Hasil Deteksi dengan Overlay Mask, Riwayat Pemindaian (Supabase Cloud), dan Pusat Edukasi Artikel (Retrofit REST API).")
    pdf.ln(2)
    body("Hasil UAT dengan 15 responden pada 18 skenario skala Likert meraih 94,44% (skor rata-rata 4,72 dari 5,00) kategori Sangat Layak (Accepted). Seluruh 4 modul evaluasi memperoleh nilai > 81%, membuktikan aplikasi efektif membantu petani meminimalisir risiko kesalahan pencabutan bibit cabai pada periode kritis 2 MST dan meningkatkan literasi agronomi melalui fitur edukasi digital terintegrasi.")

    # UCAPAN TERIMA KASIH
    add_heading("UCAPAN TERIMA KASIH")
    body("Ucapan terima kasih disampaikan kepada Program Studi Teknologi Informasi, Fakultas Teknik, Universitas Udayana atas dukungan fasilitas penelitian, serta kepada kelompok petani cabai dan penyuluh pertanian di Desa Anggabaya, Penatih, Denpasar, Bali yang telah berpartisipasi aktif dalam pengujian lapangan.")

    # DAFTAR REFERENSI
    add_heading("DAFTAR REFERENSI")
    refs = [
        "[1] N. Islam, M. M. Rashid, S. Wibowo, et al., Early weed detection using image processing and machine learning in an Australian chili farm, Computers and Electronics in Agriculture, vol. 188, p. 106030, 2021.",
        "[2] L. Hashemi-Beni, A. A. Gebrehiwot, and A. Karimoddini, Weed segmentation in organic carrot farms using deep learning, Remote Sensing Applications, vol. 20, p. 100416, 2020.",
        "[3] R. R., S. Kumar, and T. Prabakar, Comparative analysis of CNN architectures for chili and weed classification, Journal of Agricultural Technology, vol. 15, no. 2, pp. 112-124, 2025.",
        "[4] A. Khan, M. Ilyas, and M. Umraiz, CED-Net: Cascaded encoder-decoder network for weed segmentation, Computers and Electronics in Agriculture, vol. 175, p. 105566, 2020.",
        "[5] J. Yu, W. Zhang, and Y. Xu, DeepLabV3+ with MobileNetV2 and CBAM for weed segmentation in soybean, Plants, vol. 11, no. 17, p. 2288, 2022.",
        "[6] Y. Li, X. Wang, and H. Zhang, Enhanced U-Net with DCM, CA, MSFF for weed segmentation in sugar beet, Computers and Electronics in Agriculture, vol. 228, p. 109670, 2025.",
        "[7] M. Sandler, A. Howard, M. Zhu, A. Zhmoginov, and L. C. Chen, MobileNetV2: Inverted residuals and linear bottlenecks, in Proc. IEEE/CVF CVPR, 2018, pp. 4510-4520.",
        "[8] O. Ronneberger, P. Fischer, and T. Brox, U-Net: Convolutional networks for biomedical image segmentation, in Proc. MICCAI, 2015, pp. 234-241.",
        "[9] L. C. Chen, Y. Zhu, G. Papandreou, F. Schroff, and H. Adam, Encoder-decoder with atrous separable convolution, in Proc. ECCV, 2018, pp. 801-818.",
        "[10] I. W. A. Kusuma, I. M. O. Widyantara, and I. P. A. Bayupati, Penerapan pemrosesan citra digital pada platform Android, Jurnal Ilmiah Merpati, vol. 6, no. 1, pp. 55-66, 2018.",
        "[11] I. G. A. Ariatmaja, I. W. A. Kusuma, and I. M. Sukarsa, Rancang bangun aplikasi Android berkinerja tinggi, Jurnal Ilmiah Merpati, vol. 2, no. 1, pp. 45-54, 2014.",
        "[12] I. M. A. D. Suarjaya and A. A. K. A. C. Wiranatha, Analisis deteksi tepi dan segmentasi citra digital, Jurnal Ilmiah Merpati, vol. 5, no. 2, pp. 77-88, 2017.",
        "[13] I. G. P. S. Wijaya and I. K. A. Purnawan, Sistem monitoring antarmuka kontrol tanaman berbasis Android, Jurnal Ilmiah Merpati, vol. 7, no. 2, pp. 112-123, 2019.",
    ]
    pdf.set_font("Times", "", 8.5)
    for ref in refs:
        pdf.multi_cell(0, 4.5, sanitize(ref), align="J")
        pdf.ln(1)

    pdf.output(filename)
    print(f"PDF berhasil dibuat: {filename}")

if __name__ == "__main__":
    output_file = "JURNAL_GULMA_DETEKTOR.pdf"
    create_pdf(output_file)
