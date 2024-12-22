// Class Klien untuk menyimpan informasi klien
class Client {
    private String idKlien;
    private String namaKlien;
    private String email;
    private String nomorHp;

    public Client(String idKlien, String namaKlien, String email, String nomorHp) {
        this.idKlien = idKlien;
        this.namaKlien = namaKlien;
        this.email = email;
        this.nomorHp = nomorHp;
    }

    // Getters
    public String getIdKlien() { return idKlien; }
    public String getNamaKlien() { return namaKlien; }
    public String getEmail() { return email; }
    public String getNomorHp() { return nomorHp; }
}