import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Layanan {
    String nama;
    Double harga;

    Scanner scanner = new Scanner(System.in);

    // Penerapan Collection Framework HashMap
    // Pembuatan objek HashMap
    static HashMap<String, Layanan> layanan = new HashMap<>();

    // List HashMap
    static {
        layanan.put("1", new Layanan("Cuci Setrika", 10000.0));
        layanan.put("2", new Layanan("Cuci ", 8000.0));
        layanan.put("3", new Layanan("Setrika", 5000.0));
    }

    // Penerapan construktor
    public Layanan(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    // Method untuk menampilkan jenis layanan laundry
    public void jenisLayanan() {
        System.out.println("\nJenis Layanan");

        // Menampilkan list pada HashMap
        for (Map.Entry<String, Layanan> entry : layanan.entrySet()) {
            String id = entry.getKey();
            Layanan layanan = entry.getValue();
            System.out.println(id + ". " + layanan.nama + "\t Harga: Rp" + layanan.harga);
        }
    }

    // Method untuk menambah jenis layanan laundry
    public void tambahLayanan() {
        System.out.print("\nMasukkan ID layanan: ");
        String id = scanner.nextLine();

        System.out.print("Masukkan nama layanan: ");
        String nama = scanner.nextLine();

        System.out.print("Masukkan harga layanan: ");
        double harga = scanner.nextDouble();
        scanner.nextLine(); // Mengkonsumsi newline

        // Menambah list pada HashMap
        layanan.put(id, new Layanan(nama, harga));
    }

    // Method untuk menghapus jenis layanan laundry
    public void hapusLayanan() {
        System.out.print("\nID layanan yang ingin dihapus: ");
        String id = scanner.nextLine();

        if (layanan.containsKey(id)) {
            // Menghapus list pada HashMap
            layanan.remove(id);
            System.out.println("Layanan dengan ID " + id + " telah dihapus.");
        } else {
            System.out.println("Layanan dengan ID " + id + " tidak ditemukan.");
        }
    }
}
