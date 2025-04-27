package model.saham;

public class Saham {
    private String kode;
    private String namaPerusahaan;
    private double harga;

    public Saham(String kode, String namaPerusahaan, double harga) {
        this.kode = kode;
        this.namaPerusahaan = namaPerusahaan;
        this.harga = harga;
    }

    // Getter dan Setter
    public String getKode() {
        return kode;
    }

    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Rp%,.2f)", kode, namaPerusahaan, harga);
    }
}
