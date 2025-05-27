/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Services.User;
import Services.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */
public class Pengunjung extends User {
    private String jenisKelamin;
    private String alamat;
    private String noTelp;
    private java.sql.Date tanggalKunjungan;
    
    // Setter methods
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setTanggalKunjungan(java.sql.Date tanggalKunjungan) {
        this.tanggalKunjungan = tanggalKunjungan;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
   
    public boolean registerPengunjung() {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal. Tidak bisa menyimpan data.", "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO user (username, role, jenis_kelamin, alamat, no_telp, tanggal_kunjungan) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.role);
            stmt.setString(3, this.jenisKelamin);
            stmt.setString(4, this.alamat);
            stmt.setString(5, this.noTelp);
            stmt.setDate(6, this.tanggalKunjungan); // Pastikan bertipe java.sql.Date

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Data pengunjung berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menyimpan data pengunjung.", "Gagal", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan pengunjung:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void getAllPengunjung() {
        
    }
}
