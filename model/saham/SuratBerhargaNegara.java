package model.saham;

import java.util.Date;

public class SuratBerhargaNegara {
    private String nama;
    private double bunga;
    private int jangkaWaktu;
    private Date jatuhTempo;
    private double kuotaNasional;

    public SuratBerhargaNegara(String nama, double bunga, int jangkaWaktu, Date jatuhTempo, double kuotaNasional) {
        this.nama = nama;
        this.bunga = bunga;
        this.jangkaWaktu = jangkaWaktu;
        this.jatuhTempo = jatuhTempo;
        this.kuotaNasional = kuotaNasional;
    }
    public double getBunga() {
        return bunga;
    }
    public double getKuotaNasional() {
        return kuotaNasional;
    }

    public void setKuotaNasional(double kuotaNasional) {
        this.kuotaNasional = kuotaNasional;
    }

    @Override
    public String toString() {
        return String.format("%s - Bunga: %.2f%%, Jangka Waktu: %d bulan, Jatuh Tempo: %s, Kuota: Rp%,.2f",
                nama, bunga, jangkaWaktu, jatuhTempo.toString(), kuotaNasional);
    }
}

