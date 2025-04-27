package Investasi.login;

import model.saham.Portofolio;
import model.saham.Saham;
import model.saham.SuratBerhargaNegara;

import java.util.List;

public class Customer extends User {
    private Portofolio portofolio;

    public Customer(String username, String password) {
        super(username, password);
        this.portofolio = new Portofolio();
    }

    public void beliSaham(Saham saham, int jumlah) {
        portofolio.tambahSaham(saham, jumlah);
    }

    public void jualSaham(Saham saham, int jumlah) throws Exception {
        portofolio.kurangiSaham(saham, jumlah);
    }

    public void beliSBN(SuratBerhargaNegara sbn, double nominal) {
        portofolio.tambahSBN(sbn, nominal);
        sbn.setKuotaNasional(sbn.getKuotaNasional() - nominal);
    }

    public Portofolio getPortofolio() {
        return portofolio;
    }
}