package Investasi.login;

import model.saham.Saham;
import  model.saham.SuratBerhargaNegara;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private List<Saham> daftarSaham;
    private List<SuratBerhargaNegara> daftarSBN;

    public Admin(String username, String password) {
        super(username, password);
        this.daftarSaham = new ArrayList<>();
        this.daftarSBN = new ArrayList<>();
    }

    public void tambahSaham(Saham saham) {
        daftarSaham.add(saham);
    }

    public void ubahHargaSaham(String kodeSaham, double hargaBaru) {
        for (Saham saham : daftarSaham) {
            if (saham.getKode().equals(kodeSaham)) {
                saham.setHarga(hargaBaru);
                break;
            }
        }
    }

    public void tambahSBN(SuratBerhargaNegara sbn) {
        daftarSBN.add(sbn);
    }

    public List<Saham> getDaftarSaham() {
        return daftarSaham;
    }

    public List<SuratBerhargaNegara> getDaftarSBN() {
        return daftarSBN;
    }
}
