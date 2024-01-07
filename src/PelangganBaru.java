import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class PelangganBaru extends Pelanggan {
    public static Connection con;
    public static Statement stm;

    static Scanner scanner = new Scanner(System.in);

    // Construktor
    public PelangganBaru(String nama, String noHp, String idPelanggan) {
        super(nama, noHp, idPelanggan);
    }

    public static void inputDataPelanggan() {
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

            System.out.println("\nDATA PELANGGAN");
            System.out.println("--------------");
            System.out.print("Nama Pelanggan\t: ");
            nama = scanner.nextLine();

            System.out.print("No. HP\t\t: ");
            noHp = scanner.nextLine();

            String idPelanggan = noHp.substring(noHp.length() - 4);

            // CREATE, INSERT data pelanggan ke database (CRUD)
            String sqlInsert = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, no_hp) VALUES ('" + idPelanggan
                    + "', '" + nama + "', '" + noHp + "')";
            int rowsAffected = stm.executeUpdate(sqlInsert);

            if (rowsAffected > 0) {
                System.out.println("\nData Pelanggan berhasil dimasukkan ke database.");

            } else {
                System.out.println("\nData Pelanggan gagal dimasukkan ke database.");
            }
        }

        catch (Exception e) {
            System.err.println("KONEKSI DATABASE GAGAL " + e.getMessage());
        }
    }
}
