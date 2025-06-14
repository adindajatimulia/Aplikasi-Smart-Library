/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import Services.Anggota;
import Services.Admin;
import View.DaftarBuku;
import View.FormPengunjung;
import View.DaftarBukuAdmin;
/**
 *
 * @author HP
 */
public class Login extends javax.swing.JFrame {
String username, password;
private Anggota anggota;
private Admin admin;
private int userId;
private String role;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.anggota = new Anggota();
        this.admin = new Admin();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login = new javax.swing.JButton();
        loginAsPengunjung = new javax.swing.JButton();
        RegisterAnggota = new javax.swing.JButton();
        TXTDusername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TXTDPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(252, 255, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(36, 49, 62));
        jLabel1.setText("Smart Library");

        jLabel2.setBackground(new java.awt.Color(32, 41, 49));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Username");

        jLabel3.setBackground(new java.awt.Color(32, 41, 49));
        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Password");

        login.setBackground(new java.awt.Color(47, 126, 229));
        login.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        login.setForeground(new java.awt.Color(255, 255, 255));
        login.setText("Masuk");
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        loginAsPengunjung.setBackground(new java.awt.Color(253, 254, 255));
        loginAsPengunjung.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        loginAsPengunjung.setText("Login sebagai pengunjung");
        loginAsPengunjung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginAsPengunjungActionPerformed(evt);
            }
        });

        RegisterAnggota.setBackground(new java.awt.Color(253, 254, 255));
        RegisterAnggota.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        RegisterAnggota.setText("Daftar sebagai anggota");
        RegisterAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterAnggotaActionPerformed(evt);
            }
        });

        TXTDusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTDusernameActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(136, 136, 136));
        jLabel4.setText("© Perpustakaan Kota 2025");

        TXTDPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTDPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(TXTDusername, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(TXTDPassword)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(loginAsPengunjung)
                .addGap(51, 51, 51)
                .addComponent(RegisterAnggota)
                .addGap(131, 131, 131))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TXTDusername, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TXTDPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginAsPengunjung, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RegisterAnggota))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
       
        username = TXTDusername.getText();
        password = new String(TXTDPassword.getPassword());
        String role = anggota.getUserRole(username);
        
        if (role != null) {
            if (role.equals("admin")) {
                
               this.userId = admin.loginAdmin(username,password);
               this.role = "admin";
                if(admin.loginAdmin(username,password) != -1){
                DaftarBukuAdmin daftarBuku = new DaftarBukuAdmin();
                daftarBuku.setVisible(true);
                this.dispose(); // Tutup halaman login
                }
                
            } else if (role.equals("anggota")) {
                
                this.userId = anggota.loginAnggota(username,password);
                this.role = "anggota";
                if(anggota.loginAnggota(username,password) != -1){
                DaftarBuku daftarBuku = new DaftarBuku();
                daftarBuku.setUserId(userId);
                daftarBuku.setRole("anggota");
                daftarBuku.setVisible(true);
                this.dispose(); // Tutup halaman login
                }
            } 
        } else {
            JOptionPane.showMessageDialog(null, "Username atau password salah");
        }
        
    }//GEN-LAST:event_loginActionPerformed

    private void loginAsPengunjungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginAsPengunjungActionPerformed
        new FormPengunjung().setVisible(true); // Buka halaman baru
        this.dispose(); // Tutup halaman login
    }//GEN-LAST:event_loginAsPengunjungActionPerformed

    private void TXTDusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTDusernameActionPerformed
        
    }//GEN-LAST:event_TXTDusernameActionPerformed

    private void RegisterAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterAnggotaActionPerformed
        new RegisterAnggota().setVisible(true); // Buka halaman baru
        this.dispose(); // Tutup halaman login
    }//GEN-LAST:event_RegisterAnggotaActionPerformed

    private void TXTDPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTDPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTDPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RegisterAnggota;
    private javax.swing.JPasswordField TXTDPassword;
    private javax.swing.JTextField TXTDusername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton login;
    private javax.swing.JButton loginAsPengunjung;
    // End of variables declaration//GEN-END:variables
}
