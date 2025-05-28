/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Services.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class TransaksiPeminjaman {
    private int id;
    private int idUser;
    private String kodeBuku;
    private String judulBuku;
    private int jumlah;
    private String tanggalPinjam;
    private String estimasiKembali; // alias dari tanggal_kembali (estimasi)
    private String tanggalKembali;  // tanggal kembali asli dari transaksi_kembali
    private boolean status;
    private int denda;
    private String kondisiBuku;

    // Constructor opsional (jika perlu)
    public void setData(int id, int idUser, String kodeBuku, int jumlah,
                        String tanggalPinjam, String estimasiKembali, boolean status) {
        this.id = id;
        this.idUser = idUser;
        this.kodeBuku = kodeBuku;
        this.jumlah = jumlah;
        this.tanggalPinjam = tanggalPinjam;
        this.estimasiKembali = estimasiKembali;
        this.status = status;
    }

    // ================================
    // Getters
    // ================================

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public String getEstimasiKembali() {
        return estimasiKembali;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public boolean getStatus() {
        return status;
    }

    public int getDenda() {
        return denda;
    }

    public String getKondisiBuku() {
        return kondisiBuku;
    }

    // ================================
    // Setters
    // ================================

    public void setId(int id) {
        this.id = id;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setKodeBuku(String kodeBuku) {
        this.kodeBuku = kodeBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void setEstimasiKembali(String estimasiKembali) {
        this.estimasiKembali = estimasiKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }

    public void setKondisiBuku(String kondisiBuku) {
        this.kondisiBuku = kondisiBuku;
    }
    
    public void createTransaksiPinjam(int userId, String kodeBuku, int jumlah) {
          Connection conn = DatabaseConnection.getConnection();
          if (conn == null) {
              System.out.println("Koneksi gagal. Tidak bisa menyimpan transaksi.");
              return;
          }

          String insertSql = "INSERT INTO transaksi_pinjam_buku (user_id, kode_buku, jumlah, tanggal_pinjam, tanggal_kembali, status) " +
                             "VALUES (?, ?, ?, ?, ?, ?)";
          String updateStokSql = "UPDATE buku SET stock = stock - ? WHERE kode_buku = ? AND stock >= ?";

          try (
              PreparedStatement insertStmt = conn.prepareStatement(insertSql);
              PreparedStatement updateStokStmt = conn.prepareStatement(updateStokSql)
          ) {
              conn.setAutoCommit(false); // Transaksi manual

              LocalDate today = LocalDate.now();
              LocalDate kembali = today.plusDays(7); // Masa pinjam 7 hari

              // 1. Insert ke tabel peminjaman
              insertStmt.setInt(1, userId);
              insertStmt.setString(2, kodeBuku);
              insertStmt.setInt(3, jumlah);
              insertStmt.setDate(4, java.sql.Date.valueOf(today));
              insertStmt.setDate(5, java.sql.Date.valueOf(kembali));
              insertStmt.setBoolean(6, false); // false = Belum dikembalikan
              int inserted = insertStmt.executeUpdate();

              // 2. Update stok buku
              updateStokStmt.setInt(1, jumlah);       // stok - jumlah
              updateStokStmt.setString(2, kodeBuku);
              updateStokStmt.setInt(3, jumlah);       // pastikan stok cukup
              int updated = updateStokStmt.executeUpdate();

              if (inserted > 0 && updated > 0) {
                  conn.commit();
                  JOptionPane.showMessageDialog(null, "Transaksi peminjaman berhasil disimpan.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
              } else {
                  conn.rollback(); // Gagal salah satu, batalkan
                  JOptionPane.showMessageDialog(null, "Gagal menyimpan transaksi. Periksa stok buku.", "Gagal", JOptionPane.ERROR_MESSAGE);
              }

          } catch (SQLException e) {
              try {
                  conn.rollback();
              } catch (SQLException ex) {
                  ex.printStackTrace();
              }
              System.out.println("Terjadi kesalahan saat menyimpan transaksi peminjaman:");
              e.printStackTrace();
          } finally {
              try {
                  conn.setAutoCommit(true); // Kembalikan ke auto
                  conn.close();
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }


    
    public static List<TransaksiPeminjaman> getAllTransaksiGabungan(int idUser) {
        List<TransaksiPeminjaman> daftar = new ArrayList<>();

        String query = "SELECT t.id, b.judul_buku AS judul_buku, t.jumlah, t.tanggal_pinjam, " +
                       "t.tanggal_kembali AS estimasi_kembali, k.tanggal_kembali, t.status, " +
                       "k.denda, k.kondisi_buku " +
                       "FROM transaksi_pinjam_buku t " +
                       "JOIN buku b ON t.kode_buku = b.kode_buku " +
                       "LEFT JOIN transaksi_pengembalian_buku k ON t.id = k.transaksi_pinjam_id " +
                       "WHERE t.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUser); // Set parameter user_id

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransaksiPeminjaman t = new TransaksiPeminjaman();
                    t.setId(rs.getInt("id"));
                    t.setJudulBuku(rs.getString("judul_buku"));
                    t.setJumlah(rs.getInt("jumlah"));
                    t.setTanggalPinjam(rs.getDate("tanggal_pinjam").toString());
                    t.setEstimasiKembali(rs.getDate("estimasi_kembali").toString());
                    t.setTanggalKembali(rs.getDate("tanggal_kembali") != null
                            ? rs.getDate("tanggal_kembali").toString()
                            : "Belum dikembalikan");
                    t.setStatus(rs.getBoolean("status"));
                    t.setDenda(rs.getInt("denda"));
                    t.setKondisiBuku(rs.getString("kondisi_buku"));
                    daftar.add(t);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }

    public static TransaksiPeminjaman getDetailTransaksi(int transaksiId) {
        TransaksiPeminjaman transaksi = null;

        String query = "SELECT t.id, t.user_id, t.kode_buku, b.judul_buku AS judul_buku, t.jumlah, " +
                       "t.tanggal_pinjam, t.tanggal_kembali AS estimasi_kembali, t.status, " +
                       "k.tanggal_kembali, k.kondisi_buku, k.denda " +
                       "FROM transaksi_pinjam_buku t " +
                       "JOIN buku b ON t.kode_buku = b.kode_buku " +
                       "LEFT JOIN transaksi_pengembalian_buku k ON t.id = k.transaksi_pinjam_id " +
                       "WHERE t.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, transaksiId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    transaksi = new TransaksiPeminjaman();
                    transaksi.setId(rs.getInt("id"));
                    transaksi.setIdUser(rs.getInt("user_id"));
                    transaksi.setKodeBuku(rs.getString("kode_buku"));
                    transaksi.setJudulBuku(rs.getString("judul_buku"));
                    transaksi.setJumlah(rs.getInt("jumlah"));
                    transaksi.setTanggalPinjam(rs.getDate("tanggal_pinjam").toString());
                    transaksi.setEstimasiKembali(rs.getDate("estimasi_kembali").toString());
                    transaksi.setStatus(rs.getBoolean("status"));

                    // Optional: nilai NULL jika belum dikembalikan
                    Date tanggalKembali = rs.getDate("tanggal_kembali");
                    transaksi.setTanggalKembali(tanggalKembali != null ? tanggalKembali.toString() : "Belum dikembalikan");

                    transaksi.setKondisiBuku(rs.getString("kondisi_buku") != null ? rs.getString("kondisi_buku") : "Belum dikembalikan");
                    transaksi.setDenda(rs.getInt("denda")); // bisa 0 jika belum ada
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaksi;
    }
    
    public int hitungDenda(String estimasiKembaliStr) {
        try {
            // Format tanggal, sesuaikan dengan format estimasiKembaliStr
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ubah jika formatnya berbeda

            // Parse tanggal estimasi pengembalian
            LocalDate estimasiKembali = LocalDate.parse(estimasiKembaliStr, formatter);

            // Ambil tanggal hari ini
            LocalDate hariIni = LocalDate.now();

            // Hitung selisih hari
            long selisihHari = ChronoUnit.DAYS.between(estimasiKembali, hariIni);

            // Jika selisih positif (telat), hitung denda
            if (selisihHari > 0) {
                return (int) selisihHari * 2500;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
     
    public void updateTransaksi() {
        // update data transaksi peminjaman
    }

    public void deleteTransaksi() {
        // hapus transaksi
    }

    public void searchTransaksi() {
        // cari transaksi berdasarkan parameter tertentu
    }

}
