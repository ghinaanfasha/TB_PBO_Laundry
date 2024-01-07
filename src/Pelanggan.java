public class Pelanggan {
    static String nama;
    static String noHp;
    static String idPelanggan;

    public Pelanggan(String nama, String noHp, String idPelanggan) {
        Pelanggan.nama = nama;
        Pelanggan.noHp = noHp;
        Pelanggan.idPelanggan = noHp.substring(noHp.length() - 4); // Mengambil 4 angka terakhir noHp sebagai
                                                                   // idPelanggan
    }

}
