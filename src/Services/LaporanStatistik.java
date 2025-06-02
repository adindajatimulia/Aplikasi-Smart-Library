/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.*;
import java.util.*;
/**
 *
 * @author HP
 */
public class LaporanStatistik {
private String bulanTahun;  // misal "06-2025"
private int totalPeminjaman;
// fields untuk data anggota (public biar bisa langsung diakses)
private String nik;
private String username;
private java.sql.Date tanggalLahir;
private java.sql.Date tanggalKunjungan;
private String jenisKelamin;
private String noTelp;
private String alamat;
private String password;

    public void setNik(String nik) { this.nik = nik; }
    public void setUsername(String username) { this.username = username; }
    public void setTanggalLahir(java.sql.Date tanggalLahir) { this.tanggalLahir = tanggalLahir; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setPassword(String password) { this.password = password; }
    public void setTanggalKunjungan(java.sql.Date tanggalKunjungan) { this.tanggalKunjungan = tanggalKunjungan; }
    
    public String getNik() { return nik; }
    public String getUsername() { return username; }
    public java.sql.Date getTanggalLahir() { return tanggalLahir; }
    public String getJenisKelamin() { return jenisKelamin; }
    public String getNoTelp() { return noTelp; }
    public String getAlamat() { return alamat; }
    public String getPassword() { return password; }
    public java.sql.Date getTanggalKunjungan() { return tanggalKunjungan; }

    public void setTotalPeminjamanPerBulan(String bulanTahun, int totalPeminjaman) {
        this.bulanTahun = bulanTahun;
        this.totalPeminjaman = totalPeminjaman;
    }

    public String getBulanTahun() {
        return bulanTahun;
    }

    public int getTotalPeminjaman() {
        return totalPeminjaman;
    }
    
   // Total jumlah judul buku (baris pada tabel buku)
    public int getJumlahBukuTersedia() {
        int tersedia = this.getJumlahTotalBuku(); 
        int dipinjam = this.getJumlahBukuDipinjam();
        return tersedia + dipinjam;
    }

    
    // Total semua stok buku (akumulasi dari kolom stock)
    public int getJumlahTotalBuku() {
        String query = "SELECT SUM(stock) FROM buku";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Banyak genre unik
    public int getJumlahGenre() {
        String query = "SELECT COUNT(DISTINCT genre) FROM buku";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Buku yang sedang dipinjam (belum dikembalikan)
    public int getJumlahBukuDipinjam() {
        String query = "SELECT COUNT(*) FROM transaksi_pinjam_buku WHERE status = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Menghitung jumlah judul buku (jumlah baris pada tabel buku)
    public int getJumlahJudulBuku() {
        String query = "SELECT COUNT(*) FROM buku";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String getTop10BukuSeringDipinjam() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT b.judul_buku, COUNT(tp.kode_buku) AS jumlah_pinjam " +
                       "FROM transaksi_pinjam_buku tp " +
                       "JOIN buku b ON tp.kode_buku = b.kode_buku " +
                       "GROUP BY b.kode_buku, b.judul_buku " +
                       "ORDER BY jumlah_pinjam DESC " +
                       "LIMIT 10";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int rank = 1;
            while (rs.next()) {
                String judul = rs.getString("judul_buku");
                result.append(rank).append(". ").append(judul).append("\n");
                rank++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
    
    public static List<LaporanStatistik> getTotalPeminjamanPerBulan() {
        List<LaporanStatistik> daftar = new ArrayList<>();
        String query = "SELECT DATE_FORMAT(tanggal_pinjam, '%m-%Y') AS bulan_tahun, COUNT(*) AS total_peminjaman " +
                       "FROM transaksi_pinjam_buku " +
                       "GROUP BY bulan_tahun " +
                       "ORDER BY STR_TO_DATE(bulan_tahun, '%m-%Y') ASC";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String bulanTahun = rs.getString("bulan_tahun");
                int total = rs.getInt("total_peminjaman");

                LaporanStatistik data = new LaporanStatistik();
                data.setTotalPeminjamanPerBulan(bulanTahun, total);
                daftar.add(data);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftar;
    }

    // Statistik Pengunjung
    public static List<LaporanStatistik> getAllAnggota() {
        List<LaporanStatistik> daftarAnggota = new ArrayList<>();
        String query = "SELECT nik, username, tanggal_lahir, jenis_kelamin, no_telp, alamat, password " +
                       "FROM user WHERE role = 'anggota'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LaporanStatistik anggota = new LaporanStatistik();

                anggota.nik = rs.getString("nik");
                anggota.username = rs.getString("username");
                anggota.tanggalLahir = rs.getDate("tanggal_lahir");
                anggota.jenisKelamin = rs.getString("jenis_kelamin");
                anggota.noTelp = rs.getString("no_telp");
                anggota.alamat = rs.getString("alamat");

                daftarAnggota.add(anggota);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftarAnggota;
    }

    // Method untuk mendapatkan list pengunjung
    public static List<LaporanStatistik> getAllPengunjung() {
        List<LaporanStatistik> daftarPengunjung = new ArrayList<>();
        String query = "SELECT username, alamat, jenis_kelamin, no_telp, tanggal_kunjungan " +
                       "FROM user WHERE role = 'pengunjung'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                LaporanStatistik pengunjung = new LaporanStatistik();

                pengunjung.setUsername(rs.getString("username"));
                pengunjung.setAlamat(rs.getString("alamat"));
                pengunjung.setJenisKelamin(rs.getString("jenis_kelamin"));
                pengunjung.setNoTelp(rs.getString("no_telp"));
                pengunjung.setTanggalKunjungan(rs.getDate("tanggal_kunjungan"));

                daftarPengunjung.add(pengunjung);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftarPengunjung;
    }

    // Menghitung total anggota aktif (misal berdasarkan role 'anggota')
    public int getTotalAnggotaAktif() {
        String query = "SELECT COUNT(*) FROM user WHERE role = 'anggota'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Menghitung total kunjungan pengunjung (jumlah baris di tabel user dengan role pengunjung)
    public int getTotalKunjungan() {
        String query = "SELECT COUNT(*) FROM user WHERE role = 'pengunjung'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

   public String getStatistikKelompokUsia() {
        StringBuilder hasil = new StringBuilder();

        Map<String, String> kelompokQuery = new LinkedHashMap<>();
        kelompokQuery.put("1. Anak-anak (0-12)", "TIMESTAMPDIFF(YEAR, tanggal_lahir, CURDATE()) BETWEEN 0 AND 12");
        kelompokQuery.put("2. Remaja (13-17)", "TIMESTAMPDIFF(YEAR, tanggal_lahir, CURDATE()) BETWEEN 13 AND 17");
        kelompokQuery.put("3. Dewasa (18-59)", "TIMESTAMPDIFF(YEAR, tanggal_lahir, CURDATE()) BETWEEN 18 AND 59");
        kelompokQuery.put("4. Lansia (60+)", "TIMESTAMPDIFF(YEAR, tanggal_lahir, CURDATE()) >= 60");

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            for (Map.Entry<String, String> entry : kelompokQuery.entrySet()) {
                String label = entry.getKey();
                String kondisi = entry.getValue();

                String query = "SELECT COUNT(*) FROM user WHERE " + kondisi;
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    int jumlah = rs.getInt(1);
                    hasil.append(label).append(" = ").append(jumlah).append(" orang\n\n"); // <-- Tambahkan baris kosong
                }

                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasil.toString();
    }
   
    public String getStatistikPengunjungByPekerjaan() {
        StringBuilder hasil = new StringBuilder();
        String query = "SELECT pekerjaan, COUNT(*) as jumlah FROM user GROUP BY pekerjaan ORDER BY jumlah DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int no = 1;
            while (rs.next()) {
                String pekerjaan = rs.getString("pekerjaan");
                int jumlah = rs.getInt("jumlah");

                // Jika pekerjaan null atau kosong, beri label default
                if (pekerjaan == null || pekerjaan.trim().isEmpty()) {
                    pekerjaan = "(Tidak diketahui)";
                }

                hasil.append(no).append(". ").append(pekerjaan).append(" = ").append(jumlah).append(" orang\n\n");
                no++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasil.toString();
    }



}
