# BAB V 

# PENUTUP 

Bab V merupakan penutup dari laporan tugas akhir. Bab V membahas mengenai Kesimpulan dan saran serta masukan yang merujuk pada rumusan masalah penelitian. 

## 5.1 Kesimpulan 

Berdasarkan keseluruhan tahapan penelitian, pra-pemrosesan data, pengujian performa berbagai skenario eksperimen model _Deep Learning_ , hingga implementasi sistem berbasis Android pada aplikasi Gulma Detektor ( _WeedGuard_ ), diperoleh kesimpulan sebagai berikut: 

- 1 Penerapan Computer Vision dan Algoritma Deep Learning 

   - Melalui analisis komparasi ( _head-to-head_ ) antar arsitektur model dan _backbone_ (MobileNetV2, MobileViT, dan EfficientViT), terbukti bahwa pendekatan segmentasi semantik terbaik dihasilkan oleh arsitektur U-Net dengan _backbone_ MobileNetV2 yang dilatih menggunakan kombinasi _loss function Cross-Entropy_ dan _Tversky Loss_ . Model ini terbukti paling optimal mengatasi ketimpangan kelas ( _class imbalance_ ) di lahan pertanian, dengan raihan akurasi global 96,20%, Mean IoU (mIoU) 81,73%, serta sensitivitas deteksi gulma ( _Recall_ Gulma) mencapai 92,38% dan IoU Cabai 85,33%. Tingkat kesalahan identifikasi silang antara tanaman cabai dan gulma berhasil ditekan di bawah 2%, sehingga sangat aman digunakan guna meminimalisir risiko kesalahan pencabutan ( _human error_ ) pada periode kritis pertumbuhan tanaman 2 minggu setelah tanam (2 MST). 

- 2 Perancangan Sistem Informasi Pertanian Cerdas Berbasis Android 

Aplikasi mobile deteksi dini gulma telah berhasil dirancang dan dibangun secara fungsional dalam lingkungan Android Studio menggunakan bahasa Kotlin. Model U- Net terbaik dikemas secara ringan ke dalam format _TensorFlow Lite_ (.tflite) berukuran ringkas (±21,15 MB) agar mampu berjalan cepat secara lokal ( _on-device_ ) tanpa ketergantungan koneksi internet. Sistem ini mengintegrasikan lima menu navigasi utama secara harmonis, yaitu Beranda ( _Home_ ), Deteksi Kamera (foto langsung 

maupun unggah galeri), Deteksi _Real-time_ ( _Live Mode_ ), Riwayat Deteksi ( _History_ ) yang dilengkapi stempel waktu ( _timestamp_ ) untuk pemantauan lahan secara kronologis, serta fitur Pusat Edukasi & Artikel sebagai media pembelajaran digital bagi petani. 

- 3 Tingkat Akurasi, Fungsionalitas, dan Kebermanfaatan Aplikasi 

Hasil pengujian sistem dan pengujian penerimaan pengguna akhir ( _User Acceptance Testing_ / UAT) menunjukkan bahwa aplikasi berjalan sangat stabil, akurat, dan ramah pengguna ( _user-friendly_ ). Fitur deteksi mampu menghasilkan pemetaan visual ( _overlay mask_ ) yang tegas (hijau untuk daun cabai dan merah untuk gulma) beserta panduan interpretasi hasil. Aplikasi ini terbukti memberikan manfaat ganda bagi petani cabai: mempercepat pengambilan keputusan penyiangan secara presisi sekaligus meningkatkan wawasan serta literasi petani dalam pengolahan lahan melalui artikel penanganan gulma yang terpercaya. 

## 5.2 Saran 

Berdasarkan hasil evaluasi serta batasan masalah dalam penelitian ini, terdapat beberapa saran yang dapat dijadikan acuan untuk pengembangan sistem dan penelitian selanjutnya: 

- 1 Perluasan Platform Aplikasi 

   - Pengembangan aplikasi mobile deteksi gulma diharapkan tidak hanya terbatas pada sistem operasi Android, melainkan dapat diperluas dan dikembangkan berbasis iOS. Hal ini bertujuan agar jangkauan pemanfaatan teknologi _Smart Farming_ ini semakin luas bagi seluruh kalangan petani maupun praktisi agrikultur. 

- 2 Pemanfaatan Model untuk Sistem Robotika 

   - Diharapkan penelitian selanjutnya tidak hanya berhenti pada pengembangan perangkat lunak atau sistem informasi saja, namun model AI segmentasi semantik yang telah berakurasi tinggi ini dapat dimanfaatkan dan diintegrasikan sebagai perangkat otak visual ( _computer vision_ ) pada pengembangan sistem robotika pertanian, seperti purwarupa robot pencabut gulma atau penyemprot herbisida otomatis di lahan cabai. 

- 3 Optimasi Kinerja Deteksi _Real-time_ 

Dalam penyempurnaan sistem berikutnya, perlu dilakukan optimasi pemrosesan citra dan kompresi model lebih lanjut pada pemanggilan fitur deteksi langsung ( _real-time_ / _Live Mode_ ). Hal ini diperlukan agar tangkapan _frame rate per second_ (FPS) berjalan lebih cepat, ringan, dan _smooth_ tanpa jeda saat sorotan kamera bergerak menyusuri area pertanaman. 

