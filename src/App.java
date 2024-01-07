import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String username = "Admin";
        String password = "12345";
        int pilihan1, pilihan2, pilihan3;

        Scanner scanner = new Scanner(System.in);

        // Heading Sistem Laundry
        System.out.println("");
        System.out.println("           ANFASHA LAUNDRY           ");
        System.out.println("======================================");
        System.out.println("Silahkan login terlebih dahulu!");

        // Perulangan untuk proses login
        while (true) {
            // Input username
            System.out.print("Username\t: ");
            String inputUsername = scanner.next();

            // Input password
            System.out.print("Password\t: ");
            String inputPassword = scanner.next();

            // Percabangan if else untuk username dan password
            // Penerapan manipulasi string (equals)
            if (inputUsername.equals(username) && inputPassword.equals(password)) {
                // Perulangan untuk entry captcha
                while (true) {
                    // Menampilkan captcha
                    String captcha = generateCaptcha();
                    System.out.println("");
                    System.out.println("Kode Captcha\t: " + captcha);
                    System.out.print("Entry Captcha\t: ");
                    String inputCaptcha = scanner.next();

                    // Percabangan if else untuk captcha
                    // Penerapan manipulasi string (equalsIgnoreCase)
                    if (inputCaptcha.equalsIgnoreCase(captcha)) {
                        // Pesan berhasil login
                        System.out.println("--------------------------------------");
                        System.out.println("            Login Berhasil            ");
                        System.out.println("--------------------------------------");

                        // Membuat format Date untuk tanggal dan waktu
                        Date HariSekarang = new Date();
                        SimpleDateFormat day = new SimpleDateFormat("EEEE',' dd/MM/yyyy");
                        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss z");

                        // Menampilkan tanggal dan waktu
                        System.out.println("");
                        System.out.println("Tanggal\t: " + day.format(HariSekarang));
                        System.out.println("Waktu\t: " + time.format(HariSekarang));

                        // Perulangan untuk menampilkan menu
                        do {
                            System.out.println("\nMENU UTAMA");
                            System.out.println("1. Layanan Laundry");
                            System.out.println("2. Masukkan Laundry");
                            System.out.println("3. Status Laundry");
                            System.out.println("4. Selesai");
                            System.out.println("--------------------------------------");
                            System.out.print("Masukkan pilihan Anda: ");
                            pilihan1 = scanner.nextInt();

                            switch (pilihan1) {
                                case 1:
                                    System.out.println("--------------------------------------");
                                    do {
                                        System.out.println("\nSUB-MENU : LAYANAN LAUNDRY");
                                        System.out.println("1. Jenis Layanan");
                                        System.out.println("2. Tambah Layanan");
                                        System.out.println("3. Hapus Layanan");
                                        System.out.println("4. Kembali");
                                        System.out.println("--------------------------------------");
                                        System.out.print("Masukkan pilihan Anda: ");
                                        pilihan2 = scanner.nextInt();
                                        System.out.println("--------------------------------------");

                                        Layanan layanan = new Layanan("", 0);

                                        switch (pilihan2) {
                                            case 1:
                                                layanan.jenisLayanan();
                                                break;
                                            case 2:
                                                layanan.tambahLayanan();
                                                break;
                                            case 3:
                                                layanan.hapusLayanan();
                                                break;
                                            case 4:
                                                System.out.println("\nKembali ke MENU UTAMA");
                                                break;
                                            default:
                                                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                                        }
                                    } while (pilihan2 != 4);
                                    break;
                                case 2:
                                    System.out.println("--------------------------------------");
                                    do {
                                        System.out.println("\nSUB-MENU : MASUKKAN LAUNDRY");
                                        System.out.println("1. Pelanggan Lama");
                                        System.out.println("2. Pelanggan Baru");
                                        System.out.println("3. Kembali");
                                        System.out.println("--------------------------------------");
                                        System.out.print("Masukkan pilihan Anda: ");
                                        pilihan3 = scanner.nextInt();
                                        System.out.println("--------------------------------------");

                                        switch (pilihan3) {
                                            case 1:
                                                PelangganLama pelangganLama = PelangganLama.getPelangganDariDatabase();
                                                if (pelangganLama != null) {
                                                    pelangganLama.hitungTotal();
                                                    pelangganLama.masukkanTransaksiKeDatabase();
                                                } else {
                                                    System.out.println("Pelanggan tidak ditemukan.");
                                                }
                                                break;
                                            case 2:
                                                PelangganBaru.inputDataPelanggan();
                                                break;
                                            case 3:
                                                System.out.println("\nKembali ke MENU UTAMA");
                                                break;
                                            default:
                                                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                                        }
                                    } while (pilihan3 != 3);
                                    break;
                                case 3:
                                    System.out.println("--------------------------------------");
                                    PelangganLama.updateStatus();
                                    break;
                                case 4:
                                    System.out.println("======================================");
                                    System.out.println("           Program Selesai            ");
                                    System.out.println("======================================");
                                    System.out.println("");
                                    break;
                                default:
                                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                            }
                        } while (pilihan1 != 4);
                        break;
                    }

                    // Percabangan if else untuk captcha
                    else {
                        System.out.println("");
                        System.out.println("Captcha salah, silakan coba lagi");
                        System.out.println("");
                    }
                }
                break;
            }

            // Percabangan if else untuk username dan password
            else {
                System.out.println("");
                System.out.println("Username atau Password Invalid!");
                System.out.println("");
            }
        }
        scanner.close();
    }

    // Method untuk membuat random captcha
    private static String generateCaptcha() {
        // Variabel karakter berisi semua karakter yang mungkin digunakan dalam captcha
        String karakter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Membuat objek captcha random
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();

        // Perulangan untuk membuat captcha 6 karakter
        for (int i = 0; i < 6; i++) {
            captcha.append(karakter.charAt(random.nextInt(karakter.length())));
        }
        return captcha.toString();
    }

}
