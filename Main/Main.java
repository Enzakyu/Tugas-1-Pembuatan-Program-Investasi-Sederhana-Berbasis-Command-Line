package Main;

import java.util.*;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isLoggedIn = false;
    private static boolean isAdmin = false;
    private static String currentUser = "";


    private static List<Saham> daftarSaham = new ArrayList<>();
    private static List<SBN> daftarSBN = new ArrayList<>();
    private static Map<String, Customer> customers = new HashMap<>();
    private static Map<String, String> userCredentials = new HashMap<>();

    public static void main(String[] args) {

        initializeData();

        boolean isRunning = true;

        while (isRunning) {
            if (!isLoggedIn) {
                showLoginMenu();
            } else if (isAdmin) {
                showAdminMenu();
            } else {
                showCustomerMenu();
            }
        }
    }

    private static void initializeData() {
        // Inisialisasi user credentials
        userCredentials.put("admin", "admin123");
        userCredentials.put("john", "john123");
        userCredentials.put("jane", "jane123");

        // Inisialisasi data saham
        daftarSaham.add(new Saham("BBCA", "Bank Central Asia", 8000, 0));
        daftarSaham.add(new Saham("BBRI", "Bank Rakyat Indonesia", 4500, 0));
        daftarSaham.add(new Saham("TLKM", "Telkom Indonesia", 3200, 0));
        daftarSaham.add(new Saham("ASII", "Astra International", 5700, 0));
        daftarSaham.add(new Saham("UNVR", "Unilever Indonesia", 4400, 0));

        // Inisialisasi data SBN
        daftarSBN.add(new SBN("SBR010", "Savings Bond Retail", 5.7, 2, LocalDate.now(), LocalDate.now().plusYears(2)));
        daftarSBN.add(new SBN("SR016", "Sukuk Ritel", 6.25, 3, LocalDate.now(), LocalDate.now().plusYears(3)));
        daftarSBN.add(new SBN("ORI020", "Obligasi Ritel Indonesia", 5.9, 3, LocalDate.now(), LocalDate.now().plusYears(3)));

        // Inisialisasi data customer
        customers.put("john", new Customer("John Doe"));
        customers.put("jane", new Customer("Jane Smith"));

        // Tambahkan beberapa kepemilikan saham & SBN awal untuk customer
        Customer john = customers.get("john");
        john.beliSaham(daftarSaham.get(0), 10);
        john.beliSaham(daftarSaham.get(2), 20);
        john.beliSBN(daftarSBN.get(0), 5000000);

        Customer jane = customers.get("jane");
        jane.beliSaham(daftarSaham.get(1), 15);
        jane.beliSaham(daftarSaham.get(3), 5);
        jane.beliSBN(daftarSBN.get(1), 10000000);
    }

    private static void showLoginMenu() {
        System.out.println("\n===== APLIKASI INVESTASI =====");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.print("Pilih menu: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Terima kasih telah menggunakan aplikasi kami.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Menu tidak valid. Silakan pilih kembali.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            isLoggedIn = true;
            currentUser = username;
            isAdmin = username.equals("admin");
            System.out.println("Login berhasil sebagai " + (isAdmin ? "Admin" : "Customer"));
            delay(1);
        } else {
            System.out.println("Username atau password salah!");
            delay(1);
        }
    }

    private static void showAdminMenu() {
        System.out.println("\n===== MENU ADMIN =====");
        System.out.println("1. Kelola Saham");
        System.out.println("2. Kelola SBN");
        System.out.println("3. Logout");
        System.out.print("Pilih menu: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    kelolaSaham();
                    break;
                case 2:
                    kelolaSBN();
                    break;
                case 3:
                    logout();
                    break;
                default:
                    System.out.println("Menu tidak valid. Silakan pilih kembali.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void kelolaSaham() {
        boolean kembali = false;

        while (!kembali) {
            System.out.println("\n===== KELOLA SAHAM =====");
            tampilkanDaftarSaham();
            System.out.println("\n1. Tambah Saham Baru");
            System.out.println("2. Ubah Harga Saham");
            System.out.println("3. Kembali");
            System.out.print("Pilih menu: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                switch (choice) {
                    case 1:
                        tambahSaham();
                        break;
                    case 2:
                        ubahHargaSaham();
                        break;
                    case 3:
                        kembali = true;
                        break;
                    default:
                        System.out.println("Menu tidak valid. Silakan pilih kembali.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.nextLine(); // clear buffer
            }
        }
    }

    private static void tambahSaham() {
        System.out.println("\n----- TAMBAH SAHAM BARU -----");
        System.out.print("Kode Saham: ");
        String kode = scanner.nextLine().toUpperCase();

        // Periksa jika kode saham sudah ada
        for (Saham saham : daftarSaham) {
            if (saham.getKode().equals(kode)) {
                System.out.println("Saham dengan kode " + kode + " sudah ada!");
                delay(1);
                return;
            }
        }

        System.out.print("Nama Perusahaan: ");
        String nama = scanner.nextLine();

        double harga = 0;
        try {
            System.out.print("Harga Saham: ");
            harga = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (harga <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                delay(1);
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input harga tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
            return;
        }

        daftarSaham.add(new Saham(kode, nama, harga, 0));
        System.out.println("Saham " + kode + " berhasil ditambahkan!");
        delay(1);
    }

    private static void ubahHargaSaham() {
        System.out.println("\n----- UBAH HARGA SAHAM -----");
        System.out.print("Kode Saham: ");
        String kode = scanner.nextLine().toUpperCase();

        Saham targetSaham = null;
        for (Saham saham : daftarSaham) {
            if (saham.getKode().equals(kode)) {
                targetSaham = saham;
                break;
            }
        }

        if (targetSaham == null) {
            System.out.println("Saham dengan kode " + kode + " tidak ditemukan!");
            delay(1);
            return;
        }

        double hargaLama = targetSaham.getHarga();
        System.out.println("Harga lama: " + formatCurrency(hargaLama));

        try {
            System.out.print("Harga Baru: ");
            double hargaBaru = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (hargaBaru <= 0) {
                System.out.println("Harga harus lebih dari 0!");
                delay(1);
                return;
            }

            // Hitung persentase perubahan
            double persentasePerubahan = ((hargaBaru - hargaLama) / hargaLama) * 100;
            targetSaham.setHarga(hargaBaru);
            targetSaham.setPerubahanHarga(persentasePerubahan);

            System.out.println("Harga saham " + kode + " berhasil diubah!");
            System.out.println("Perubahan: " + String.format("%.2f%%", persentasePerubahan));
            delay(1);
        } catch (InputMismatchException e) {
            System.out.println("Input harga tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void kelolaSBN() {
        boolean kembali = false;

        while (!kembali) {
            System.out.println("\n===== KELOLA SBN =====");
            tampilkanDaftarSBN();
            System.out.println("\n1. Tambah SBN Baru");
            System.out.println("2. Kembali");
            System.out.print("Pilih menu: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                switch (choice) {
                    case 1:
                        tambahSBN();
                        break;
                    case 2:
                        kembali = true;
                        break;
                    default:
                        System.out.println("Menu tidak valid. Silakan pilih kembali.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.nextLine(); // clear buffer
            }
        }
    }

    private static void tambahSBN() {
        System.out.println("\n----- TAMBAH SBN BARU -----");
        System.out.print("Kode SBN: ");
        String kode = scanner.nextLine().toUpperCase();

        // Periksa jika kode SBN sudah ada
        for (SBN sbn : daftarSBN) {
            if (sbn.getKode().equals(kode)) {
                System.out.println("SBN dengan kode " + kode + " sudah ada!");
                delay(1);
                return;
            }
        }

        System.out.print("Nama SBN: ");
        String nama = scanner.nextLine();

        double sukuBunga = 0;
        try {
            System.out.print("Suku Bunga (%, per tahun): ");
            sukuBunga = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (sukuBunga <= 0) {
                System.out.println("Suku bunga harus lebih dari 0!");
                delay(1);
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input suku bunga tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
            return;
        }

        int tenorTahun = 0;
        try {
            System.out.print("Tenor (tahun): ");
            tenorTahun = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (tenorTahun <= 0) {
                System.out.println("Tenor harus lebih dari 0!");
                delay(1);
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input tenor tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
            return;
        }

        // Tentukan tanggal mulai dan jatuh tempo
        LocalDate tanggalMulai = LocalDate.now();
        LocalDate tanggalJatuhTempo = tanggalMulai.plusYears(tenorTahun);

        daftarSBN.add(new SBN(kode, nama, sukuBunga, tenorTahun, tanggalMulai, tanggalJatuhTempo));
        System.out.println("SBN " + kode + " berhasil ditambahkan!");
        delay(1);
    }

    private static void showCustomerMenu() {
        System.out.println("\n===== MENU CUSTOMER =====");
        System.out.println("1. Beli/Jual Saham");
        System.out.println("2. Beli SBN");
        System.out.println("3. Simulasi SBN");
        System.out.println("4. Lihat Portofolio");
        System.out.println("5. Logout");
        System.out.print("Pilih menu: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    transaksiSaham();
                    break;
                case 2:
                    beliSBN();
                    break;
                case 3:
                    simulasiSBN();
                    break;
                case 4:
                    lihatPortofolio();
                    break;
                case 5:
                    logout();
                    break;
                default:
                    System.out.println("Menu tidak valid. Silakan pilih kembali.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void transaksiSaham() {
        boolean kembali = false;

        while (!kembali) {
            System.out.println("\n===== TRANSAKSI SAHAM =====");
            tampilkanDaftarSaham();

            System.out.println("\n1. Beli Saham");
            System.out.println("2. Jual Saham");
            System.out.println("3. Kembali");
            System.out.print("Pilih menu: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                switch (choice) {
                    case 1:
                        beliSaham();
                        break;
                    case 2:
                        jualSaham();
                        break;
                    case 3:
                        kembali = true;
                        break;
                    default:
                        System.out.println("Menu tidak valid. Silakan pilih kembali.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Masukkan angka.");
                scanner.nextLine(); // clear buffer
            }
        }
    }

    private static void beliSaham() {
        System.out.println("\n----- BELI SAHAM -----");
        System.out.print("Kode Saham: ");
        String kode = scanner.nextLine().toUpperCase();

        Saham targetSaham = null;
        for (Saham saham : daftarSaham) {
            if (saham.getKode().equals(kode)) {
                targetSaham = saham;
                break;
            }
        }

        if (targetSaham == null) {
            System.out.println("Saham dengan kode " + kode + " tidak ditemukan!");
            delay(1);
            return;
        }

        try {
            System.out.print("Jumlah lot yang ingin dibeli: ");
            int jumlahLot = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (jumlahLot <= 0) {
                System.out.println("Jumlah lot harus lebih dari 0!");
                delay(1);
                return;
            }

            double totalBiaya = targetSaham.getHarga() * jumlahLot;
            System.out.println("Total biaya: " + formatCurrency(totalBiaya));
            System.out.print("Konfirmasi pembelian (y/n): ");
            String konfirmasi = scanner.nextLine();

            if (konfirmasi.equalsIgnoreCase("y")) {
                Customer customer = customers.get(currentUser);
                customer.beliSaham(targetSaham, jumlahLot);
                System.out.println("Pembelian saham " + kode + " sebanyak " + jumlahLot + " lot berhasil!");
            } else {
                System.out.println("Pembelian dibatalkan.");
            }
            delay(1);
        } catch (InputMismatchException e) {
            System.out.println("Input jumlah lot tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void jualSaham() {
        System.out.println("\n----- JUAL SAHAM -----");
        Customer customer = customers.get(currentUser);

        // Tampilkan daftar saham yang dimiliki
        List<KepemilikanSaham> kepemilikanSaham = customer.getKepemilikanSaham();

        if (kepemilikanSaham.isEmpty()) {
            System.out.println("Anda tidak memiliki saham untuk dijual!");
            delay(1);
            return;
        }

        System.out.println("\nSaham Yang Anda Miliki:");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-6s | %-20s | %-10s | %-12s | %-10s\n", "Kode", "Nama", "Lot", "Harga/Lot", "Total Nilai");
        System.out.println("----------------------------------------------------------------");

        for (KepemilikanSaham ks : kepemilikanSaham) {
            Saham saham = ks.getSaham();
            System.out.printf("%-6s | %-20s | %-10d | %-12s | %-10s\n",
                    saham.getKode(),
                    saham.getNama(),
                    ks.getJumlahLot(),
                    formatCurrency(saham.getHarga()),
                    formatCurrency(saham.getHarga() * ks.getJumlahLot()));
        }
        System.out.println("----------------------------------------------------------------");

        System.out.print("Kode Saham yang ingin dijual: ");
        String kode = scanner.nextLine().toUpperCase();

        // Cari kepemilikan saham dengan kode tersebut
        KepemilikanSaham targetKepemilikan = null;
        for (KepemilikanSaham ks : kepemilikanSaham) {
            if (ks.getSaham().getKode().equals(kode)) {
                targetKepemilikan = ks;
                break;
            }
        }

        if (targetKepemilikan == null) {
            System.out.println("Anda tidak memiliki saham dengan kode " + kode + "!");
            delay(1);
            return;
        }

        try {
            System.out.print("Jumlah lot yang ingin dijual: ");
            int jumlahLot = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (jumlahLot <= 0) {
                System.out.println("Jumlah lot harus lebih dari 0!");
                delay(1);
                return;
            }

            if (jumlahLot > targetKepemilikan.getJumlahLot()) {
                System.out.println("Jumlah lot yang ingin dijual melebihi kepemilikan Anda!");
                delay(1);
                return;
            }

            double totalPenjualan = targetKepemilikan.getSaham().getHarga() * jumlahLot;
            System.out.println("Total penjualan: " + formatCurrency(totalPenjualan));
            System.out.print("Konfirmasi penjualan (y/n): ");
            String konfirmasi = scanner.nextLine();

            if (konfirmasi.equalsIgnoreCase("y")) {
                customer.jualSaham(targetKepemilikan.getSaham(), jumlahLot);
                System.out.println("Penjualan saham " + kode + " sebanyak " + jumlahLot + " lot berhasil!");
            } else {
                System.out.println("Penjualan dibatalkan.");
            }
            delay(1);
        } catch (InputMismatchException e) {
            System.out.println("Input jumlah lot tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void beliSBN() {
        System.out.println("\n===== BELI SBN =====");
        tampilkanDaftarSBN();

        System.out.print("Kode SBN: ");
        String kode = scanner.nextLine().toUpperCase();

        SBN targetSBN = null;
        for (SBN sbn : daftarSBN) {
            if (sbn.getKode().equals(kode)) {
                targetSBN = sbn;
                break;
            }
        }

        if (targetSBN == null) {
            System.out.println("SBN dengan kode " + kode + " tidak ditemukan!");
            delay(1);
            return;
        }

        try {
            System.out.print("Nominal pembelian (minimum 1 juta): ");
            double nominal = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (nominal < 1000000) {
                System.out.println("Nominal pembelian harus minimal 1 juta rupiah!");
                delay(1);
                return;
            }

            // Nominal harus kelipatan 100 ribu
            if (nominal % 100000 != 0) {
                System.out.println("Nominal pembelian harus kelipatan 100 ribu rupiah!");
                delay(1);
                return;
            }

            System.out.println("Detail pembelian:");
            System.out.println("Kode SBN: " + targetSBN.getKode());
            System.out.println("Nama SBN: " + targetSBN.getNama());
            System.out.println("Suku Bunga: " + targetSBN.getSukuBunga() + "% per tahun");
            System.out.println("Tenor: " + targetSBN.getTenor() + " tahun");
            System.out.println("Tanggal Jatuh Tempo: " + targetSBN.getTanggalJatuhTempo().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
            System.out.println("Nominal Pembelian: " + formatCurrency(nominal));

            // Estimasi total imbal hasil
            double totalImbalHasil = nominal * (1 + (targetSBN.getSukuBunga() / 100 * targetSBN.getTenor()));
            System.out.println("Estimasi nilai pada jatuh tempo: " + formatCurrency(totalImbalHasil));

            System.out.print("Konfirmasi pembelian (y/n): ");
            String konfirmasi = scanner.nextLine();

            if (konfirmasi.equalsIgnoreCase("y")) {
                Customer customer = customers.get(currentUser);
                customer.beliSBN(targetSBN, nominal);
                System.out.println("Pembelian SBN " + kode + " senilai " + formatCurrency(nominal) + " berhasil!");
            } else {
                System.out.println("Pembelian dibatalkan.");
            }
            delay(1);
        } catch (InputMismatchException e) {
            System.out.println("Input nominal tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void simulasiSBN() {
        System.out.println("\n===== SIMULASI SBN =====");

        try {
            System.out.print("Nominal investasi: ");
            double nominal = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (nominal <= 0) {
                System.out.println("Nominal harus lebih dari 0!");
                delay(1);
                return;
            }

            System.out.print("Suku bunga (%, per tahun): ");
            double sukuBunga = scanner.nextDouble();
            scanner.nextLine(); // clear buffer

            if (sukuBunga <= 0) {
                System.out.println("Suku bunga harus lebih dari 0!");
                delay(1);
                return;
            }

            System.out.print("Tenor (tahun): ");
            int tenor = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            if (tenor <= 0) {
                System.out.println("Tenor harus lebih dari 0!");
                delay(1);
                return;
            }

            // Simulasi
            System.out.println("\n----- HASIL SIMULASI -----");
            System.out.println("Nominal Investasi: " + formatCurrency(nominal));
            System.out.println("Suku Bunga: " + sukuBunga + "% per tahun");
            System.out.println("Tenor: " + tenor + " tahun");
            System.out.println("\nSimulasi Pembayaran Kupon:");

            double totalBunga = 0;
            double kuponPerTahun = nominal * sukuBunga / 100;

            for (int tahun = 1; tahun <= tenor; tahun++) {
                totalBunga += kuponPerTahun;
                System.out.println("Tahun " + tahun + ": " + formatCurrency(kuponPerTahun));
            }

            System.out.println("\nTotal Kupon/Bunga: " + formatCurrency(totalBunga));
            System.out.println("Nilai Pokok pada Jatuh Tempo: " + formatCurrency(nominal));
            System.out.println("Total Penerimaan: " + formatCurrency(nominal + totalBunga));

            // Simulasi tambahan untuk imbal hasil efektif
            double imbalHasilEfektif = (totalBunga / (nominal * tenor)) * 100;
            System.out.println("Imbal Hasil Efektif: " + String.format("%.2f%%", imbalHasilEfektif) + " per tahun");

            delay(3);
        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid. Masukkan angka.");
            scanner.nextLine(); // clear buffer
        }
    }

    private static void lihatPortofolio() {
        System.out.println("\n===== PORTOFOLIO INVESTASI =====");
        Customer customer = customers.get(currentUser);
        List<KepemilikanSaham> kepemilikanSaham = customer.getKepemilikanSaham();
        List<KepemilikanSBN> kepemilikanSBN = customer.getKepemilikanSBN();

        // PORTOFOLIO SAHAM
        System.out.println("\n--- PORTOFOLIO SAHAM ---");
        if (kepemilikanSaham.isEmpty()) {
            System.out.println("Anda belum memiliki saham.");
        } else {
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s | %-18s | %-5s | %-9s | %-9s | %-12s\n",
                    "Kode", "Nama", "Lot", "Harga/Lot", "Perubahan", "Total Nilai");
            System.out.println("----------------------------------------------------------------");

            double totalInvestasiSaham = 0;
            for (KepemilikanSaham ks : kepemilikanSaham) {
                Saham saham = ks.getSaham();
                double totalNilai = saham.getHarga() * ks.getJumlahLot();
                totalInvestasiSaham += totalNilai;

                String perubahanStr = String.format("%.2f%%", saham.getPerubahanHarga());
                if (saham.getPerubahanHarga() > 0) {
                    perubahanStr = "+" + perubahanStr;
                }

                System.out.printf("%-6s | %-18s | %-5d | %-9s | %-9s | %-12s\n",
                        saham.getKode(),
                        truncateString(saham.getNama(), 18),
                        ks.getJumlahLot(),
                        formatCurrency(saham.getHarga()),
                        perubahanStr,
                        formatCurrency(totalNilai));
            }
            System.out.println("----------------------------------------------------------------");
            System.out.println("Total Nilai Portofolio Saham: " + formatCurrency(totalInvestasiSaham));
        }

        // PORTOFOLIO SBN
        System.out.println("\n--- PORTOFOLIO SBN ---");
        if (kepemilikanSBN.isEmpty()) {
            System.out.println("Anda belum memiliki SBN.");
        } else {
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-6s | %-18s | %-11s | %-10s | %-12s | %-10s\n",
                    "Kode", "Nama", "Nilai Pokok", "Bunga", "Jatuh Tempo", "Est. Imbal");
            System.out.println("--------------------------------------------------------------------------------");

            double totalInvestasiSBN = 0;
            for (KepemilikanSBN ksbn : kepemilikanSBN) {
                SBN sbn = ksbn.getSbn();
                double nilaiPokok = ksbn.getNilaiPokok();
                totalInvestasiSBN += nilaiPokok;

                LocalDate tanggalJatuhTempo = sbn.getTanggalJatuhTempo();
                String tanggalJT = tanggalJatuhTempo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Hitung estimasi imbal hasil saat jatuh tempo
                double estimasiImbalHasil = nilaiPokok * (1 + (sbn.getSukuBunga() / 100 * sbn.getTenor()));

                System.out.printf("%-6s | %-18s | %-11s | %-10s | %-12s | %-12s\n",
                        sbn.getKode(),
                        truncateString(sbn.getNama(), 18),
                        formatCurrency(nilaiPokok),
                        sbn.getSukuBunga() + "%/th",
                        tanggalJT,
                        formatCurrency(estimasiImbalHasil));
            }
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Total Nilai Pokok SBN: " + formatCurrency(totalInvestasiSBN));
        }

        // RINGKASAN TOTAL INVESTASI
        double totalSaham = 0;
        for (KepemilikanSaham ks : kepemilikanSaham) {
            totalSaham += ks.getSaham().getHarga() * ks.getJumlahLot();
        }

        double totalSBN = 0;
        for (KepemilikanSBN ksbn : kepemilikanSBN) {
            totalSBN += ksbn.getNilaiPokok();
        }

        double totalInvestasi = totalSaham + totalSBN;

        System.out.println("\n--- RINGKASAN INVESTASI ---");
        System.out.println("Total Nilai Saham: " + formatCurrency(totalSaham) + " (" +
                String.format("%.1f%%", totalSaham / totalInvestasi * 100) + ")");
        System.out.println("Total Nilai SBN: " + formatCurrency(totalSBN) + " (" +
                String.format("%.1f%%", totalSBN / totalInvestasi * 100) + ")");
        System.out.println("TOTAL NILAI INVESTASI: " + formatCurrency(totalInvestasi));

        System.out.print("\nTekan Enter untuk kembali ke menu utama...");
        scanner.nextLine();
    }

    private static void tampilkanDaftarSaham() {
        System.out.println("\nDAFTAR SAHAM TERSEDIA:");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-6s | %-25s | %-12s | %-10s\n", "Kode", "Nama Perusahaan", "Harga/Lot", "Perubahan");
        System.out.println("----------------------------------------------------------------------");

        for (Saham saham : daftarSaham) {
            String perubahanStr = String.format("%.2f%%", saham.getPerubahanHarga());
            if (saham.getPerubahanHarga() > 0) {
                perubahanStr = "+" + perubahanStr;
            }

            System.out.printf("%-6s | %-25s | %-12s | %-10s\n",
                    saham.getKode(),
                    saham.getNama(),
                    formatCurrency(saham.getHarga()),
                    perubahanStr);
        }
        System.out.println("----------------------------------------------------------------------");
    }

    private static void tampilkanDaftarSBN() {
        System.out.println("\nDAFTAR SBN TERSEDIA:");
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("%-6s | %-25s | %-12s | %-8s | %-15s\n",
                "Kode", "Nama SBN", "Bunga/Tahun", "Tenor", "Jatuh Tempo");
        System.out.println("------------------------------------------------------------------------------");

        for (SBN sbn : daftarSBN) {
            System.out.printf("%-6s | %-25s | %-12s | %-8s | %-15s\n",
                    sbn.getKode(),
                    sbn.getNama(),
                    sbn.getSukuBunga() + "%",
                    sbn.getTenor() + " tahun",
                    sbn.getTanggalJatuhTempo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        System.out.println("------------------------------------------------------------------------------");
    }

    private static void logout() {
        isLoggedIn = false;
        isAdmin = false;
        currentUser = "";
        System.out.println("Logout berhasil!");
        delay(1);
    }

    private static String formatCurrency(double amount) {
        Locale localeID = new Locale("id", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localeID);
        String formattedAmount = formatter.format(amount);

        if (formattedAmount.endsWith(",00")) {
            formattedAmount = formattedAmount.replace(",00", "");
        }
        return formattedAmount;
    }

    private static String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    private static void delay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Saham {
    private String kode;
    private String nama;
    private double harga;
    private double perubahanHarga;

    public Saham(String kode, String nama, double harga, double perubahanHarga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.perubahanHarga = perubahanHarga;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getPerubahanHarga() {
        return perubahanHarga;
    }

    public void setPerubahanHarga(double perubahanHarga) {
        this.perubahanHarga = perubahanHarga;
    }
}

class SBN {
    private String kode;
    private String nama;
    private double sukuBunga; // dalam persen per tahun
    private int tenor; // dalam tahun
    private LocalDate tanggalMulai;
    private LocalDate tanggalJatuhTempo;

    public SBN(String kode, String nama, double sukuBunga, int tenor, LocalDate tanggalMulai, LocalDate tanggalJatuhTempo) {
        this.kode = kode;
        this.nama = nama;
        this.sukuBunga = sukuBunga;
        this.tenor = tenor;
        this.tanggalMulai = tanggalMulai;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public double getSukuBunga() {
        return sukuBunga;
    }

    public int getTenor() {
        return tenor;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public LocalDate getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    // Hitung sisa waktu hingga jatuh tempo dalam hari
    public long getHariHinggaJatuhTempo() {
        return ChronoUnit.DAYS.between(LocalDate.now(), tanggalJatuhTempo);
    }
}

class KepemilikanSaham {
    private Saham saham;
    private int jumlahLot;

    public KepemilikanSaham(Saham saham, int jumlahLot) {
        this.saham = saham;
        this.jumlahLot = jumlahLot;
    }

    public Saham getSaham() {
        return saham;
    }

    public int getJumlahLot() {
        return jumlahLot;
    }

    public void tambahLot(int tambahan) {
        jumlahLot += tambahan;
    }

    public void kurangiLot(int pengurangan) {
        jumlahLot -= pengurangan;
    }
}


class KepemilikanSBN {
    private SBN sbn;
    private double nilaiPokok; // nilai investasi awal

    public KepemilikanSBN(SBN sbn, double nilaiPokok) {
        this.sbn = sbn;
        this.nilaiPokok = nilaiPokok;
    }

    public SBN getSbn() {
        return sbn;
    }

    public double getNilaiPokok() {
        return nilaiPokok;
    }

    public void tambahNilaiPokok(double tambahan) {
        nilaiPokok += tambahan;
    }
}


class Customer {
    private String nama;
    private List<KepemilikanSaham> kepemilikanSaham = new ArrayList<>();
    private List<KepemilikanSBN> kepemilikanSBN = new ArrayList<>();

    public Customer(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public List<KepemilikanSaham> getKepemilikanSaham() {
        return kepemilikanSaham;
    }

    public List<KepemilikanSBN> getKepemilikanSBN() {
        return kepemilikanSBN;
    }

    public void beliSaham(Saham saham, int jumlahLot) {

        for (KepemilikanSaham ks : kepemilikanSaham) {
            if (ks.getSaham().getKode().equals(saham.getKode())) {

                ks.tambahLot(jumlahLot);
                return;
            }
        }


        kepemilikanSaham.add(new KepemilikanSaham(saham, jumlahLot));
    }

    public void jualSaham(Saham saham, int jumlahLot) {
        Iterator<KepemilikanSaham> iterator = kepemilikanSaham.iterator();
        while (iterator.hasNext()) {
            KepemilikanSaham ks = iterator.next();
            if (ks.getSaham().getKode().equals(saham.getKode())) {
                if (ks.getJumlahLot() == jumlahLot) {

                    iterator.remove();
                } else {

                    ks.kurangiLot(jumlahLot);
                }
                return;
            }
        }
    }

    public void beliSBN(SBN sbn, double nilaiPokok) {

        for (KepemilikanSBN ksbn : kepemilikanSBN) {
            if (ksbn.getSbn().getKode().equals(sbn.getKode())) {

                ksbn.tambahNilaiPokok(nilaiPokok);
                return;
            }
        }


        kepemilikanSBN.add(new KepemilikanSBN(sbn, nilaiPokok));
    }
}