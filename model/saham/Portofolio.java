package model.saham;

import java.util.HashMap;
import java.util.Map;

public class Portofolio {
    private Map<Saham, Integer> sahamDimiliki;
    private Map<SuratBerhargaNegara, Double> sbnDimiliki;

    public Portofolio() {
        this.sahamDimiliki = new HashMap<>();
        this.sbnDimiliki = new HashMap<>();
    }

    public void tambahSaham(Saham saham, int jumlah) {
        sahamDimiliki.put(saham, sahamDimiliki.getOrDefault(saham, 0) + jumlah);
    }

    public void kurangiSaham(Saham saham, int jumlah) throws Exception {
        if (!sahamDimiliki.containsKey(saham) || sahamDimiliki.get(saham) < jumlah) {
            throw new Exception("Jumlah saham tidak mencukupi");
        }
        sahamDimiliki.put(saham, sahamDimiliki.get(saham) - jumlah);
        if (sahamDimiliki.get(saham) == 0) {
            sahamDimiliki.remove(saham);
        }
    }

    public void tambahSBN(SuratBerhargaNegara sbn, double nominal) {
        sbnDimiliki.put(sbn, sbnDimiliki.getOrDefault(sbn, 0.0) + nominal);
    }

    public Map<Saham, Integer> getSahamDimiliki() {
        return sahamDimiliki;
    }

    public Map<SuratBerhargaNegara, Double> getSbnDimiliki() {
        return sbnDimiliki;
    }

    public double hitungTotalNilaiSaham() {
        return sahamDimiliki.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getHarga() * entry.getValue())
                .sum();
    }

    public double hitungTotalBungaSBNPerBulan() {
        return sbnDimiliki.entrySet().stream()
                .mapToDouble(entry -> (entry.getKey().getBunga() / 100 / 12) * 0.9 * entry.getValue())
                .sum();
    }
}