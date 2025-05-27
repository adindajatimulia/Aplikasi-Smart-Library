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
    public void createBuku(String kodeBuku, String judul, String pengarang, int tahunTerbit, String penerbit, String genre, String ringkasan, int stok) {
        
      
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
    public void updateBuku(String kodeBuku, String judul, String pengarang, int tahunTerbit,
                              String penerbit, String genre, String ringkasan, int stok) {
       
    }

    // Method untuk menghapus buku berdasarkan kodeBuku
    public void deleteBuku(String kodeBuku) {
      
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
