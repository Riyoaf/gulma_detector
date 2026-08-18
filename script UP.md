**PEMBUKAAN**

"Om Swastiastu, Assalamu'alaikum Warahmatullahi Wabarakatuh, dan Salam Sejahtera bagi kita semua. Selamat \[Pagi\]"

"Yang saya hormati, Bapak **Dewa Made Sri Arsa, Ph.D.** dan Ibu **Dr. Eng. Desy Purnama Singgih Putri**, selaku Dosen Pembimbing saya." "Serta yang saya hormati, Ibu **Ni Made Ika Marini Mandenni**, Bapak **Wayan Oger Vihikan**, dan Bapak **I Made Sunia Raharja**, selaku Dosen Penguji pada hari ini."

"Perkenalkan, nama saya **Riyo Andika Febriyan**. Pada kesempatan kali ini, saya akan memaparkan proposal Tugas akhir saya yang berjudul: **'Rancang Bangun Aplikasi Deteksi Dini Gulma pada Lahan Cabai Berbasis Android'**."

**LATAR BELAKANG**

"Penelitian ini dilatarbelakangi oleh potensi besar komoditas cabai di Indonesia. Cabai bukan hanya bernilai ekonomi tinggi, tetapi juga menjadi kebutuhan konsumsi harian masyarakat."

"Namun, produktivitas cabai sering terhambat. Masalah utamanya adalah kehadiran **gulma**, terutama pada periode kritis tanaman, yaitu umur **30 sampai 60 Hari Setelah Tanam**. Pada fase ini, kompetisi perebutan nutrisi dan cahaya sangat tinggi."

"Saat ini, pengendalian gulma masih dilakukan secara manual. Cara ini memakan biaya tinggi dan tenaga kerja yang besar. Lebih parahnya lagi, sering terjadi **Human Error**. Karena bentuk fisik bibit gulma sangat mirip dengan bibit cabai muda, sehingga petani sering salah mencabut tanaman cabai yang dikira gulma."

"Oleh karena itu, diperlukan solusi teknologi. Saya mengusulkan pengembangan aplikasi berbasis **Android** yang memanfaatkan **Computer Vision** untuk mendeteksi gulma secara dini, sekaligus menyediakan fitur edukasi untuk meningkatkan literasi petani."

**RUMUSAN MASALAH**

"Berdasarkan latar belakang tersebut, terdapat tiga rumusan masalah utama dalam penelitian ini:

- Bagaimana menerapkan teknologi **Computer Vision** dan algoritma **Deep Learning** untuk mengklasifikasikan gulma dan cabai secara akurat?
- Bagaimana merancang sistem informasi berbasis Android yang mengintegrasikan fitur deteksi dan edukasi?
- Serta, bagaimana tingkat akurasi dan fungsionalitas aplikasi tersebut dalam membantu petani?"

**TUJUAN**

"Adapun tujuan dari penelitian ini adalah:

- Menerapkan Computer Vision untuk meminimalisir kesalahan identifikasi visual.
- Membangun aplikasi Android deteksi dini yang lengkap dengan fitur edukasi.
- Mengukur tingkat keberhasilan aplikasi baik dari segi akurasi deteksi maupun manfaat fiturnya bagi petani."

**MANFAAT**

"Manfaat yang diharapkan dari penelitian ini mencakup tiga aspek:

- Mempermudah identifikasi di lapangan dan mengurangi risiko salah cabut.
- Menjadi referensi pengembangan _Smart Farming_, khususnya implementasi _Deep Learning_.
- Menghasilkan (_prototype_) aplikasi yang nantinya bisa dikembangkan untuk komoditas pertanian lainnya."

**BATASAN MASALAH**

"Agar penelitian ini lebih terarah, saya menetapkan beberapa batasan masalah:

- Objek deteksi dibatasi pada tanaman cabai fase kritis (**30-60 HST**) dan jenis gulma dominan di lahan tersebut.
- Dataset menggunakan **Data Primer** (foto langsung dari lahan) dan **Data Sekunder** (dari Kaggle).
- Aplikasi dikembangkan khusus untuk platform **Android**.
- Fokus sistem adalah pada **deteksi dan klasifikasi visual**, tidak mencakup pembuatan robotika untuk pencabutan otomatis."

**TINJAUAN PUSTAKA**

"Penelitian ini merujuk pada beberapa studi terdahulu untuk menemukan kebaruan.

- Pertama, penelitian oleh **R et al. (2025)** yang membandingkan arsitektur CNN pada cabai. Mereka menemukan MobileNetV2 sangat efisien, namun mereka hanya fokus pada klasifikasi gambar utuh, belum segmentasi.
- Kedua, **Li et al. (2025)** yang memodifikasi U-Net untuk bit gula. Hasilnya bagus, namun arsitekturnya sangat kompleks dan berat untuk perangkat _mobile_."

"**Gap** yang saya temukan adalah belum adanya implementasi segmentasi semantik yang ringan namun akurat khusus untuk cabai di Android. **Solusi** yang saya tawarkan yaitu membandingkan model segmentasi **U-Net** dan **DeepLabV3+**, dengan mengganti _backbone_\-nya menggunakan **MobileNetV2**. Ini dilakukan agar model tetap akurat mengenali bentuk daun tapi cukup ringan untuk berjalan di HP Android."

**METODOLOGI PENELITIAN**

"Berikut adalah alur penelitian yang saya lakukan. Dimulai dari identifikasi masalah, penetapan rumusan masalah, dan studi literatur. Kemudian dilanjutkan ke tahap teknis yaitu **Pengumpulan Data**, **Preprocessing**, hingga **Implementasi Model**. Setelah model dilatih, dilakukan perbandingan evaluasi, implementasi ke Android, analisis hasil, dan penarikan kesimpulan."

**TOOLS**

"Perangkat lunak pendukung yang digunakan meliputi:

- **Figma** untuk desain antarmuka.
- **Android Studio** untuk pengembangan aplikasi mobile.
- **Google Colab** untuk pelatihan model dalam mengenali citra
- **CVAT/LabelImg** untuk proses anotasi data citra."
- **Supabase** berperan sebagai database untuk menyimpan data pengguna dan data deteksi

**GAMBARAN UMUM SISTEM**

"Secara garis besar, arsitektur sistem menunjukan alur kerja **Pengguna** berinteraksi dengan perangkat **Mobile** untuk mengirim gambar objek atau meminta artikel. dimana Aplikasi Mobile akan berkomunikasi dengan dua komponen yaitu model dan database

- **Model:** Mengirim gambar dan menerima hasil prediksi lokasi gulma.
- **Database:** Menyimpan data pengguna dan riwayat deteksi.

**USE CASE**

"Pada perancangan fungsional, terdapat satu aktor utama yaitu **Pengguna**. Pengguna memiliki akses ke fitur Registrasi dan Login. Melihat artikel dan pembelajaran. Melakukan **Deteksi Gulma**, yang include dengan fungsi menambah dan menyimpan catatan. Serta manajemen riwayat deteksi."

**ACTIVITY DIAGRAM REGISTER**

"Untuk alur aktivitas, dimulai dari Autentikasi. Pada proses **Register**, pengguna memasukkan data diri yang akan divalidasi dan disimpan ke database.

**ACTIVITY DIAGRAM LOGIN**

Pada proses **Login**, sistem memvalidasi kredensial pengguna untuk memberikan hak akses masuk ke halaman utama."

**ACTIVITY DIAGRAM ARTIKEL**

Selanjutnya yaitu fitur artikel dimana diagram ini menunjukan proses bagaimana pengguna melakukan akses artikel atau membaca artikel.

**ACTIVITY DIAGRAM PEMBELAJARAN GULMA**

Pada pembelajaran gulma ini prosesnya mirip seperti artikel namun akses yang di dapat nanti bentuk penjelasan dari ciri ciri tentang gulma saja.

**ACTIVITY DIAGRAM DETEKSI**

Selanjutnya yaitu activity diagram deteksi yang dimana menampilkan proses bagaimana pengguna melakukan deteksi untuk mengetahui mana tanaman cabai dan mana gulma serta disni pengguna juga dapat menambahkan catatan dan menyimpan hasil deteksi

**ACTIVITY DIAGRAM RIWAYAT**

Activity ini menunjukan proses Data yang disimpan tadi bisa diakses kembali melalui fitur **Riwayat**. Di sini, pengguna bisa melihat kembali hasil deteksi lama, mengedit catatan, atau menghapus data yang tidak diperlukan lagi."

**ALUR PEMODELAN**

Pada alur pemodelan ini menunjukan proses pembuatan model dari tahap awal hingga menghasilkan ouput yang dimulai dari **Pengumpulan Data:** Mengambil foto langsung di lahan pertanian dan menambah variasi dari Kaggle. Kemudian tahap **Pra-pemrosesan:** Data mentah di-_resize_, dianotasi (dilabeli mana gulma/cabai), diaugmentasi untk memperbanyak variasinya, dan membagi datanya. Kemudian lanjut **Pembuatan Model:** Data yang sudah siap, digunakan untuk melatih dua model: **U-Net** dan **DeepLabV3+** dengan backbone MobileNetV2. Terakhir **Evaluasi & Output:** Kedua model dibandingkan kinerjanya, dan model terbaik akan diekspor menjadi file output untuk aplikasi Android."

**LIST KEMUNGKINAN PERTANYAAN**

**DAFTAR PERTANYAAN & JAWABAN SIDANG PROPOSAL**

**1\. Mengapa lebih memilih ke lahan cabai?**

**Jawaban:** "Saya memilih komoditas cabai karena dua alasan utama, yaitu **urgensi ekonomi** dan **masalah teknis spesifik**.

- **Secara Ekonomi:** Cabai adalah komoditas dengan volatilitas harga tinggi dan kebutuhan harian besar. Kegagalan panen akibat gulma berdampak langsung pada inflasi pasar.
- **Secara Teknis:** Tantangan terbesar di lahan cabai adalah **kemiripan morfologi** (bentuk fisik). Bibit gulma daun lebar (_Broadleaves_) sangat mirip dengan bibit cabai muda pada fase 30-60 HST. Hal ini menyebabkan tingginya risiko _human error_ (salah cabut) jika dilakukan manual. Oleh karena itu, intervensi teknologi sangat dibutuhkan di sini."

**2\. Mengapa memilih pengembangan Deep Learning dibanding Machine Learning konvensional?**

**Jawaban:** "Karena karakteristik data di lahan pertanian sangat **kompleks dan tidak terstruktur**.

- **Machine Learning (seperti SVM/KNN):** Membutuhkan ekstraksi fitur manual (_hand-crafted features_). Kita harus mendefinisikan sendiri mana bentuk daun, mana warna, dll. Ini rentan gagal jika pencahayaan berubah atau _background_ tanah bervariasi.
- **Deep Learning (CNN):** Memiliki kemampuan **Automatic Feature Extraction**. Model belajar sendiri fitur-fitur penting dari jutaan parameter. Ini membuat sistem jauh lebih tangguh (_robust_) terhadap variasi kondisi lahan, bayangan, dan tumpang tindih daun yang sulit ditangani ML biasa."

**3\. Bagaimana cara kerja Computer Vision dalam penelitian ini?**

**Jawaban:** "Secara sederhana, Computer Vision bekerja dengan mengubah citra digital menjadi data numerik yang bisa dipahami mesin.

- **Input:** Kamera menangkap gambar tanaman.
- **Proses:** Citra tersebut dipecah menjadi matriks piksel (angka 0-255).
- **Analisis:** Algoritma _Deep Learning_ (U-Net/DeepLabV3+) memindai pola angka tersebut untuk menemukan fitur unik (tepi daun, tekstur, warna).
- **Output:** Sistem memberikan label pada setiap piksel, apakah itu piksel 'Gulma', 'Cabai', atau 'Tanah', lalu menampilkannya kembali ke pengguna dalam bentuk _masking_ warna visual."

**4\. Apakah bisa deteksi ini dilakukan di lahan lain selain cabai?**

**Jawaban:** "Untuk saat ini, **tidak bisa secara langsung**. Model _Deep Learning_ bekerja berdasarkan data yang dipelajarinya. Karena saya melatih model ini spesifik menggunakan dataset tanaman cabai dan gulma di sekitarnya, maka akurasinya hanya valid di ekosistem tersebut. Namun, **arsitektur sistem** yang saya bangun (Aplikasi Android + TFLite) bersifat _reusable_. Jika kita ingin mendeteksi di lahan jagung, kita hanya perlu mengganti data latihnya dan melakukan pelatihan ulang (_retraining_) tanpa perlu merombak kode aplikasinya."

**5\. Bagaimana cara implementasi Computer Vision dan Deep Learning di sini?**

**Jawaban:** "Implementasinya dilakukan melalui alur _End-to-End_:

- **Training (Di PC/Cloud):** Saya melatih model (U-Net & DeepLabV3+) menggunakan Python dan TensorFlow di Google Colab.
- **Konversi:** Model yang sudah cerdas (_trained weights_) dikonversi menjadi format **TensorFlow Lite (.tflite)**.
- **Deployment (Di Android):** File .tflite tersebut dimasukkan ke dalam aset aplikasi Android (Android Studio). Aplikasi menggunakan _TFLite Interpreter_ untuk menjalankan model tersebut secara _offline_ di HP petani."

**6\. Penelitian menggunakan metode apa?**

**Jawaban:** "Dalam pengembangan perangkat lunaknya, saya menggunakan metode **Waterfall** atau **Prototyping** (sesuaikan dengan Bab 3 Anda). Sedangkan untuk metode penyelesaian masalah kecerdasan buatannya, saya menggunakan metode **Semantic Segmentation** (_Segmentasi Semantik_) berbasis _Convolutional Neural Network_ (CNN)."

**7\. Mengapa memilih Segmentasi Semantik daripada segmentasi biasa (Deteksi Objek/Bounding Box)? Apa bedanya?**

**Jawaban:** "Perbedaannya ada pada **presisi**.

- **Object Detection (Bounding Box):** Hanya memberi kotak di sekeliling objek. Kelemahannya, kotak tersebut masih memuat area _background_ (tanah/mulsa). Jika daun gulma menempel pada daun cabai (_overlapping_), kotak keduanya akan bertumpuk dan membingungkan.
- **Semantic Segmentation (Pilihan Saya):** Mengklasifikasikan **per piksel**. Sistem mewarnai bentuk daun mengikuti lekuk-lekuk aslinya. Ini sangat krusial untuk kasus gulma karena kita perlu tahu persis batas pinggir daun gulma agar pencabutan tepat sasaran dan bisa menghitung kepadatan gulma secara akurat."

**8\. Mengapa memilih model arsitektur U-Net dan DeepLabV3+?**

**Jawaban:** "Saya memilih keduanya untuk dikomparasi karena masing-masing memiliki keunggulan teoritis:

- **U-Net:** Memiliki _Skip Connections_ yang menghubungkan _encoder_ ke _decoder_. Ini membuatnya sangat hebat dalam mempertahankan detail tepi objek (garis pinggir daun), yang penting untuk membedakan jenis tanaman.
- **DeepLabV3+:** Memiliki fitur _ASPP (Atrous Spatial Pyramid Pooling)_. Ini membuatnya hebat dalam melihat konteks objek dalam berbagai ukuran (multiskala). Mengingat gulma ada yang kecil (baru tumbuh) dan besar, arsitektur ini potensial. Saya ingin membuktikan mana yang paling efektif untuk morfologi cabai."

**9\. Dan mengapa memilih backbone MobileNetV2?**

**Jawaban:** "Karena target implementasi saya adalah **Android (Perangkat Mobile)**. Backbone standar seperti ResNet atau VGG sangat berat dan lambat jika dijalankan di HP. **MobileNetV2** dirancang khusus oleh Google untuk _mobile_. Ia menggunakan teknologi _Depthwise Separable Convolution_ yang mengurangi beban komputasi secara drastis namun tetap mempertahankan akurasi yang tinggi. Ini memastikan aplikasi berjalan lancar (_real-time_) dan tidak membuat HP petani cepat panas."

**10\. Apa nanti database yang digunakan? Kenapa SQL/Supabase?**

**Jawaban:** "Untuk penyimpanan data pengguna (login), artikel, dan riwayat deteksi, saya menggunakan **Supabase** (atau sebutkan database pilihan Anda).

- **Kenapa Supabase:** Ini adalah layanan _Backend-as-a-Service_ yang berbasis **PostgreSQL**. Sehingga pengguna tidak perlu membuat backend manual dari nol,

supabase secara otomatis mengubah database menjadi restful API jadi tidak perlu membuat kode file php atau python untuk membuat API.

- **Kenapa SQL (Relasional):** Data saya memiliki struktur yang jelas dan berelasi (Satu User memiliki Banyak Riwayat). SQL sangat efisien untuk menjaga integritas data relasional seperti ini dibandingkan NoSQL."

**11\. Model ini kenapa ga ke TFlite? Mengapa make modal server cloud?**

_(Koreksi: Berdasarkan desain terakhir Anda, aplikasi berjalan di HP, jadi_ **_JANGAN_** _bilang pakai server cloud untuk deteksi)._

**Jawaban (Yang Benar sesuai sistem Anda):** "Mohon izin meluruskan, Pak/Bu. Untuk proses **Deteksi**, saya **menggunakan TensorFlow Lite (TFLite)** yang berjalan secara _on-device_ (di dalam HP). Alasannya agar petani tetap bisa menggunakan fitur deteksi meskipun di lahan **tidak ada sinyal internet (Offline)**. Penggunaan Server/Cloud/API hanya digunakan untuk fitur tambahan seperti sinkronisasi Login, menyimpan Riwayat ke server pusat, atau mengambil update Artikel berita, bukan untuk proses deteksi utamanya."

**12\. Kenapa tidak membuat DFD, SOP, PDM, Kamus Data?**

**Jawaban:** "Karena pendekatan pengembangan sistem yang saya gunakan adalah **Object-Oriented Programming (OOP)**, bukan Terstruktur/Prosedural.

- Dalam standar modern OOP dan pengembangan Android, pemodelan sistem menggunakan **UML (Unified Modeling Language)**.
- Fungsi DFD digantikan oleh **Activity Diagram** dan **Sequence Diagram**.
- Fungsi PDM digantikan oleh **Class Diagram** atau skema Database relasional modern. UML dianggap lebih relevan untuk menggambarkan interaksi antar objek (User, Sistem, Database) dalam aplikasi _mobile_."