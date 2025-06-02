/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Buku {
    // Private atribut
    private String kodeBuku;
    private String judul;
    private String pengarang;
    private int tahunTerbit;
    private String penerbit;
    private String genre;
    private String ringkasan;
    private int stok;

    // Konstruktor kosong (untuk manajemen daftar buku)
   public Buku(String kodeBuku, String judul, String pengarang, int tahunTerbit, String penerbit, String genre, String ringkasan, int stok) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
        this.penerbit = penerbit;
        this.genre = genre;
        this.ringkasan = ringkasan;
        this.stok = stok;
    }
    
   public String getKodeBuku() {
    return kodeBuku;
   }

    public String getJudul() {
        return judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public int getStok() {
        return stok;
    }

    public String getPenerbit() {
    return this.penerbit;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getRingkasan() {
        return this.ringkasan;
    }

    // Method untuk menambahkan buku baru
    public void createBuku() {
        String query = "INSERT INTO buku (kode_buku, judul_buku, pengarang, tahun_terbit, penerbit, genre, ringkasan, stock) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.kodeBuku);
            pstmt.setString(2, this.judul);
            pstmt.setString(3, this.pengarang);
            pstmt.setInt(4, this.tahunTerbit);
            pstmt.setString(5, this.penerbit);
            pstmt.setString(6, this.genre);
            pstmt.setString(7, this.ringkasan);
            pstmt.setInt(8, this.stok);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Buku berhasil ditambahkan.");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan buku.");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method untuk menampilkan semua buku
    public static List<Buku> getAllBuku() {
        List<Buku> daftarBuku = new ArrayList<>();
        String query = "SELECT * FROM buku";

        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Buku b = new Buku(
                    rs.getString("kode_buku"),
                    rs.getString("judul_buku"),
                    rs.getString("pengarang"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("penerbit"),
                    rs.getString("genre"),
                    rs.getString("ringkasan"),
                    rs.getInt("stock")
                );
                daftarBuku.add(b);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarBuku;
    }

    // Method untuk memperbarui buku berdasarkan kodeBuku
    public void updateBuku() {
        String query = "UPDATE buku SET judul_buku = ?, pengarang = ?, tahun_terbit = ?, penerbit = ?, genre = ?, ringkasan = ?, stock = ? WHERE kode_buku = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, this.judul);
            pstmt.setString(2, this.pengarang);
            pstmt.setInt(3, this.tahunTerbit);
            pstmt.setString(4, this.penerbit);
            pstmt.setString(5, this.genre);
            pstmt.setString(6, this.ringkasan);
            pstmt.setInt(7, this.stok);
            pstmt.setString(8, this.kodeBuku);  // kondisi WHERE

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Buku berhasil diperbarui.");
            } else {
                JOptionPane.showMessageDialog(null, "Gagal memperbarui buku.");
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat memperbarui buku: " + e.getMessage());
        }
    }


    // Method untuk menghapus buku berdasarkan kodeBuku
    public void deleteBuku() {
         String query = "DELETE FROM buku WHERE kode_buku = ?";
         try {
             Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);

             pstmt.setString(1, this.kodeBuku);  // kodeBuku yang akan dihapus

             int rowsDeleted = pstmt.executeUpdate();
             if (rowsDeleted > 0) {
                 JOptionPane.showMessageDialog(null, "Buku berhasil dihapus.");
             } else {
                 JOptionPane.showMessageDialog(null, "Buku tidak ditemukan atau gagal dihapus.");
             }

             pstmt.close();
             conn.close();
         } catch (SQLException e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(null, "Error saat menghapus buku: " + e.getMessage());
         }
     }


    // Method untuk mencari buku berdasarkan judul atau pengarang
    public static List<Buku> searchBuku(String keyword) {
        
        List<Buku> daftarBuku = new ArrayList<>();
        // Query dengan LIKE untuk pencarian di beberapa kolom
        String query = "SELECT * FROM buku WHERE " +
                       "kode_buku LIKE ? OR " +
                       "judul_buku LIKE ? OR " +
                       "pengarang LIKE ? OR " +
                       "tahun_terbit LIKE ? OR " +
                       "genre LIKE ? OR " +
                       "penerbit LIKE ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Keyword dicari dengan wildcard % di kiri dan kanan
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);
            pstmt.setString(4, searchKeyword);
            pstmt.setString(5, searchKeyword);
            pstmt.setString(6, searchKeyword);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Buku b = new Buku(
                    rs.getString("kode_buku"),
                    rs.getString("judul_buku"),
                    rs.getString("pengarang"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("penerbit"),
                    rs.getString("genre"),
                    rs.getString("ringkasan"),
                    rs.getInt("stock")
                );
                daftarBuku.add(b);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftarBuku;
    }
    
    public static Buku getDetailBuku(String kodeBuku) {
    Buku buku = null;
    String query = "SELECT * FROM buku WHERE kode_buku = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, kodeBuku);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                buku = new Buku(
                    rs.getString("kode_buku"),
                    rs.getString("judul_buku"),
                    rs.getString("pengarang"),
                    rs.getInt("tahun_terbit"),
                    rs.getString("penerbit"),
                    rs.getString("genre"),
                    rs.getString("ringkasan"),
                    rs.getInt("stock")
                );
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buku;
    }


}
