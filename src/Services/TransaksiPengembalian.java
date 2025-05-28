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
import javax.swing.JTextField;
/**
 *
 * @author HP
 */
public class TransaksiPengembalian {
    private int id;
    private int idPinjam;
    private String tanggalDikembalikan;
    private double denda;
    private String kondisiBuku;

    // Constructor
    public void pengembalianBuku(int id, int idPinjam, String tanggalDikembalikan, double denda, String kondisiBuku) {
        this.id = id;
        this.idPinjam = idPinjam;
        this.tanggalDikembalikan = tanggalDikembalikan;
        this.denda = denda;
        this.kondisiBuku = kondisiBuku;
    }
    
    public void prosesPengembalianBuku(int transaksiPinjamId, String kodeBuku, LocalDate tanggalKembali, int denda, String kondisiBuku) {
        String insertPengembalian = "INSERT INTO transaksi_pengembalian_buku (transaksi_pinjam_id, tanggal_kembali, denda, kondisi_buku) " +
                                     "VALUES (?, ?, ?, ?)";
        String updateStatusPeminjaman = "UPDATE transaksi_pinjam_buku SET status = true WHERE id = ?";
        String updateStokBuku = "UPDATE buku SET stock = stock + 1 WHERE kode_buku = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Mulai transaksi

            try (
                PreparedStatement insertStmt = conn.prepareStatement(insertPengembalian);
                PreparedStatement updateStmt = conn.prepareStatement(updateStatusPeminjaman);
                PreparedStatement updateStokStmt = conn.prepareStatement(updateStokBuku)
            ) {
                // Insert ke transaksi_pengembalian_buku
                insertStmt.setInt(1, transaksiPinjamId);
                insertStmt.setDate(2, Date.valueOf(tanggalKembali));
                insertStmt.setInt(3, denda);
                insertStmt.setString(4, kondisiBuku);
                insertStmt.executeUpdate();

                // Update status peminjaman
                updateStmt.setInt(1, transaksiPinjamId);
                updateStmt.executeUpdate();

                // Update stok buku
                updateStokStmt.setString(1, kodeBuku);
                updateStokStmt.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(null, "Pengembalian buku berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal mengembalikan buku:\n" + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Koneksi database gagal:\n" + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }


   
}
