import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class PelangganLama extends Pelanggan implements Transaksi {
    String namaLayanan;
    String status;
    Double berat;
    Double totalBayar;
    String idLayanan;

    public static Connection con;
    public static Statement stm;

    // Deklarasi variabel layanan sebagai Map
    static Map<String, Layanan> layanan;

    static Scanner scanner = new Scanner(System.in);

    // Construktor
    public PelangganLama(String nama, String noHp, String idPelanggan, Map<String, Layanan> layanan) {
        super(nama, noHp, idPelanggan);
        PelangganLama.layanan = layanan;
    }

    public static PelangganLama getPelangganDariDatabase() {
        try {
            // Memuat kelas Driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Koneksi ke Database
            String url = "jdbc:mysql://localhost/laundry_tbpbo";
            String user = "root";
            String pass = "";
            con = DriverManager.getConnection(url, user, pass);

            // Membuat statement
            stm = con.createStatement();

            System.out.print("ID Pelanggan\t: ");
            String inputIdPelanggan = scanner.nextLine();

            // Membuat dan menjalankan query SELECT
            String sqlSelect = "SELECT * FROM pelanggan WHERE id_pelanggan = '" + inputIdPelanggan + "'";
            ResultSet rs = stm.executeQuery(sqlSelect);

            // READ, mengambil data pelanggan dari ResultSet (CRUD)
            if (rs.next()) {
                nama = rs.getString("nama_pelanggan");
                noHp = rs.getString("no_hp");
                System.out.println("Nama Pelanggan\t: " + nama);
                return new PelangganLama(nama, noHp, idPelanggan, layanan);
            } else {
                System.out.println("Pelanggan dengan ID " + idPelanggan + " tidak ditemukan.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("KONEKSI DATABASE GAGAL " + e.getMessage());
            return null;
        }
    }

    @Override
    public Double hitungTotal() {
        System.out.print("\nPilih Layanan\t: ");
        idLayanan = scanner.nextLine();

        System.out.print("Berat\t\t: ");
        if (scanner.hasNextDouble()) {
            berat = scanner.nextDouble();
            Layanan layananDipilih = Layanan.layanan.get(idLayanan);
            if (layananDipilih != null) {
                totalBayar = layananDipilih.harga * berat;
                System.out.println("Total Bayar\t: Rp" + totalBayar);
                namaLayanan = layananDipilih.nama;
                return totalBayar;

            } else {
                System.out.println("Layanan dengan ID " + idLayanan + " tidak ditemukan.");
                return 0.0;
            }
        } else {
            System.out.println("Input berat tidak valid.");
        }
        return berat;

    }

    public void masukkanTransaksiKeDatabase() {
        try {
            status = "Belum Bayar";
            Date HariSekarang = new Date();
            SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

            // Format tanggal database
            String tanggalStr = day.format(HariSekarang);
            String waktuStr = time.format(HariSekarang);

            java.sql.Date tanggalSql = java.sql.Date.valueOf(tanggalStr);
            java.sql.Time waktuSql = java.sql.Time.valueOf(waktuStr);

            // CREATE, INSERT data transaksi ke database (CRUD)
            String sqlInsert = "INSERT INTO transaksi (tanggal, waktu, id_pelanggan, nama_pelanggan, jenis_layanan, berat, total_bayar, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sqlInsert);
            pstmt.setDate(1, tanggalSql);
            pstmt.setTime(2, waktuSql);
            pstmt.setString(3, idPelanggan);
            pstmt.setString(4, nama);
            pstmt.setString(5, namaLayanan);
            pstmt.setDouble(6, berat);
            pstmt.setDouble(7, totalBayar);
            pstmt.setString(8, status);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("\nGAGAL MEMASUKKAN TRANSAKSI KE DATABASE " + e.getMessage());
        }
    }

    // UPDATE, Method untuk memperbarui status (CRUD)
    public static void updateStatus() {
        String statusBaru;
        try {
            // Memuat kelas Driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Koneksi ke Database
            String url = "jdbc:mysql://localhost/laundry_tbpbo";
            String user = "root";
            String pass = "";
            con = DriverManager.getConnection(url, user, pass);

            System.out.println("\nUpdate Status Transaksi");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("ID Pelanggan\t: ");
            String inputId = reader.readLine();

            System.out.print("ID Transaksi\t: ");
            int idTransaksi = Integer.parseInt(reader.readLine());

            System.out.println("Pilih Status\t: 1. Lunas");
            System.out.println("            \t  2. Sudah diambil");
            System.out.print("Masukkan Status\t: ");
            int pilihStatus = Integer.parseInt(reader.readLine());

            if (con != null) {
                switch (pilihStatus) {
                    case 1:
                        statusBaru = "Lunas";
                        String sql = "UPDATE transaksi SET status = ? WHERE id_transaksi = ? AND id_pelanggan = ?";
                        PreparedStatement statement = con.prepareStatement(sql);
                        statement.setString(1, statusBaru);
                        statement.setInt(2, idTransaksi);
                        statement.setString(3, inputId);
                        int rowsUpdated = statement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("\nStatus berhasil diperbarui di database.");
                        } else {
                            System.out.println("\nStatus gagal diperbarui di database.");
                        }
                        break;
                    case 2:
                        statusBaru = "Sudah diambil";
                        String sql2 = "UPDATE transaksi SET status = ? WHERE id_transaksi = ? AND id_pelanggan = ?";
                        PreparedStatement statement2 = con.prepareStatement(sql2);
                        statement2.setString(1, statusBaru);
                        statement2.setInt(2, idTransaksi);
                        statement2.setString(3, inputId);
                        int rowsUpdated2 = statement2.executeUpdate();
                        if (rowsUpdated2 > 0) {
                            System.out.println("Status berhasil diperbarui di database.");
                        } else {
                            System.out.println("Status gagal diperbarui di database.");
                        }
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } else {
                System.err.println("\nKONEKSI DATABASE NULL");
            }

        } catch (Exception e) {
            System.err.println("\nGAGAL MENGUBAH STATUS TRANSAKSI" + e.getMessage());
        }

    }
}
