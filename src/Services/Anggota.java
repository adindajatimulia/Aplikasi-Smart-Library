/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Services.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Anggota extends User {
    private String nik;
    private String tanggalLahir;
    private String jenisKelamin;
    private String alamat;
    private String noTelp;
    private String password;
    private Connection conn;

    public Anggota() {
        this.conn = DatabaseConnection.getConnection(); 
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }
    
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void registerAnggota() {
        if (conn == null) {
            System.out.println("Koneksi gagal. Tidak bisa menyimpan data.");
            return;
        }

        String sql = "INSERT INTO user (username, role ,password, nik, jenis_kelamin, alamat, no_telp) VALUES ( ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.role);
            stmt.setString(3, this.password);
            stmt.setString(4, this.nik);
            stmt.setString(5, this.jenisKelamin);
            stmt.setString(6, this.alamat);
            stmt.setString(7, this.noTelp);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Pendaftaran Anggota berhasil disimpan.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Pendafataran Anggota Gagal dilakukan.");
            }
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan saat menyimpan pengunjung:");
            e.printStackTrace();
        }
    }
    
    public String hashPassword(String password) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = md.digest(password.getBytes());

                StringBuilder sb = new StringBuilder();
                for (byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }

                return sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

    public int loginAnggota(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND role = 'anggota'";
 
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                String inputHashedPassword = hashPassword(password);

                if (storedHashedPassword != null && storedHashedPassword.equals(inputHashedPassword)) {
                    // Login berhasil
                    int userId = rs.getInt("id"); // Ambil ID user dari database
                    JOptionPane.showMessageDialog(null, "Login Berhasil");
                    return userId;
  
                } else {
                    JOptionPane.showMessageDialog(null, "Password salah");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username tidak ditemukan");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat login");
            e.printStackTrace();
        }
        return -1; // jika return id -1 berarti login gagal
    }
    
    public String getUserRole(String username) {
    String sql = "SELECT role FROM user WHERE username = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("role"); // Mengembalikan: "admin", "anggota", dll
        } else {
            return null; // Username tidak ditemukan
        }
      } catch (SQLException e) {
            e.printStackTrace();
            return null;
      }
    }

}
