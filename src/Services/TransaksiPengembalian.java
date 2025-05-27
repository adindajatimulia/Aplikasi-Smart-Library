/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

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

    public void getAllTransaksiKembali() {
        
    }
}
