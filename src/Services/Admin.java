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
public class Admin extends User {
    private String password;
    private Connection conn;
    private Anggota anggota;
    
    public Admin(){
        this.conn = DatabaseConnection.getConnection(); 
        this.anggota = new Anggota(); 
    }
   
    public int loginAdmin(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND role = 'admin'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("role"));
                String storedHashedPassword = rs.getString("password");
                String inputHashedPassword = anggota.hashPassword(password);

                if (storedHashedPassword != null && storedHashedPassword.equals(inputHashedPassword)) {
                    // Login berhasil
                    int userId = rs.getInt("id"); // Ambil ID user dari database
                    JOptionPane.showMessageDialog(null, "Login Sebagai Admin Berhasil");
                    return userId;
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Password salah");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username tidak ditemukan !");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat login");
            e.printStackTrace();
        }
        
        return -1;
    }
}
