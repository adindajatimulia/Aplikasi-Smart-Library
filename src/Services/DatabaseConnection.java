/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import java.sql.Connection;  // Tambahkan import untuk Connection
import java.sql.DriverManager;  // Tambahkan import untuk DriverManager
import java.sql.SQLException;
/**
 *
 * @author HP
 */
public class DatabaseConnection {
    
      // Informasi koneksi database
    private static final String URL = "jdbc:mysql://localhost:3306/finalproject-pbo"; // Ubah sesuai database Anda
    private static final String USER = "root"; // Username MySQL Anda
    private static final String PASSWORD = ""; // Kosongkan jika tidak ada password

    // Method untuk mendapatkan koneksi ke database
    public static Connection getConnection() {
        Connection con = null;
        try {
            // Load driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database
            con = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Koneksi ke database berhasil!");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC tidak ditemukan! Pastikan sudah ditambahkan.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal! Periksa konfigurasi koneksi.");
            e.printStackTrace();
        }
        return con;
    }
    
}
