package com.example.jakirespons.mvvm.category

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val _categories = listOf(
        "Administrasi Terkait Penanggulangan Kebakaran dan Penyelamatan",
        "Batas Wilayah",
        "Coretan/Tempelan/Iklan/Selebaran",
        "Fasilitas Sosial/Fasilitas Umum",
        "Iklan Rokok",
        "Internal Dinas Pariwisata dan Kebudayaan",
        "Internal Dinas Pemberdayaan, Perlindungan Anak dan Pengendalian Penduduk",
        "Koperasi",
        "Pekerja Penanganan Prasarana dan Sarana Umum Kelurahan",
        "Pelayanan Perhubungan",
        "Penanganan Kebakaran",
        "Penataan dan Pengembangan Wilayah",
        "Penyelamatan",
        "Perizinan Kepemudaan dan Olahraga",
        "Perizinan Kesehatan",
        "Perizinan Ketentraman, Ketertiban, dan Perlindungan Masyarakat",
        "Perizinan Koperasi, Usaha Kecil, dan Menengah",
        "Perizinan Lingkungan Hidup",
        "Perizinan Pekerjaan Umum dan Penataan Ruang",
        "Perizinan Penanaman Modal",
        "Perizinan Pendidikan",
        "Perizinan Perhubungan",
        "Perizinan Pertanahan",
        "Perizinan Perumahan dan Kawasan Permukiman",
        "Perizinan Sosial",
        "Perizinan Tenaga Kerja",
        "Posyandu",
        "Prasarana dan Sarana Penanggulangan Kebakaran",
        "Reklame",
        "Rupabumi",
        "Statistik Daerah",
        "Statistik Pusat",
        "UMKM",
        "Ambulans Gawat Darurat",
        "Arus Lalu Lintas",
        "Bahan Bakar Gas",
        "Bahan Bakar Minyak",
        "Banjir",
        "Bantuan Pendidikan",
        "Bantuan Sosial",
        "BPJS",
        "Demam Berdarah Dengue",
        "DP Rp0",
        "Fasilitas Kesehatan Milik Pusat/Swasta",
        "Fasilitas Olahraga",
        "Fasilitas Pendidikan Milik Pemerintah Pusat/Swasta",
        "Gangguan Ketenteraman dan Ketertiban",
        "Gedung Sekolah",
        "Hubungan Pekerja-Pengusaha",
        "Imunisasi",
        "Industri Kecil dan Menengah",
        "Jalan",
        "Jaringan Air Bersih",
        "Jaringan Komunikasi",
        "Jaringan Listrik",
        "Jembatan Penyeberangan Orang (JPO) dan/atau Halte",
        "Kartu Jakarta Pintar",
        "Kartu Jakarta Sehat (KJS)",
        "Kartu Keluarga",
        "Kearsipan",
        "Kegiatan Seni dan Budaya",
        "Keluarga Berencana",
        "Kepemudaan",
        "Komunikasi Pemerintah",
        "Konflik Sosial",
        "KTP Elektronik (KTP-El)",
        "Kurikulum dan Kegiatan Sekolah",
        "Lembaga Kemasyarakatan",
        "Lokasi Binaan dan Lokasi Sementara",
        "Minimarket",
        "Orang Hilang",
        "Pajak Bumi dan Bangunan",
        "Parkir Liar",
        "Pelanggaran Perda/Pergub",
        "Pelatihan Kerja dan Produktivitas Tenaga Kerja",
        "Pembebasan Lahan",
        "Pemberdayaan Perempuan",
        "Penataan Permukiman (Kampung Deret, Bedah Rumah, DLL)",
        "Pencemaran Lingkungan",
        "PPDB",
        "Pendataan Kesehatan",
        "Pendidikan Anak Usia Dini",
        "Pengolahan Ikan",
        "Penyakit Masyarakat",
        "Penyandang Masalah Kesejahteraan Sosial (PMKS)",
        "Perdagangan",
        "Perlindungan Anak",
        "Permasalahan Siswa",
        "Perpustakaan",
        "Persandian",
        "Pohon",
        "Puskesmas",
        "RSUD",
        "Ruang Publik Terpadu Ramah Anak (RPTRA)",
        "Rumah Potong Hewan",
        "Rumah Susun / Hunian Vertikal",
        "Saluran Air, Kali/Sungai",
        "Sampah",
        "Sanitasi dan Keamanan Pangan",
        "Sembilan Bahan Pokok",
        "Sertifikasi Guru",
        "Sertifikat atau Dokumen Kepemilikan",
        "Sertifikat Laik Fungsi",
        "Sumur Resapan",
        "Taman",
        "Taman Pemakaman Umum",
        "Tata Ruang dan Bangunan",
        "Tempat Hiburan",
        "Tempat Pelelangan Ikan",
        "Tempat Wisata",
        "Tenaga Kependidikan",
        "Tindakan Asusila",
        "Transmigrasi",
        "Transportasi Publik",
        "Trotoar",
        "Tutup Saluran"
    )

    private val _categoriesLiveData = MutableLiveData(_categories)
    val categories : LiveData<List<String>> = Transformations.map(_categoriesLiveData) {
        it
    }

    fun filter(filter : String){
        viewModelScope.launch {
            if (filter.isNotBlank() || filter.isNotEmpty()){
                val __categories = _categories.filter { it.contains(filter, ignoreCase = true) }
                _categoriesLiveData.postValue(__categories)
            }
            else {
                _categoriesLiveData.postValue(_categories)
            }
        }
    }


}