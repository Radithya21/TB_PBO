// Class Kasus yang mewarisi AbstractCase

import java.time.LocalDateTime;

//! MATERI 1 & 3: Class, Object, Constructor & Inheritance
// Case subclass dari abstractCase
class Case extends AbstractCase {
    private Client client;

    //Constructor
    public Case(String idKasus, String kasus, String statusKasus, Client client) {
        super(idKasus, kasus, statusKasus);
        this.client = client;
    }

    public String getIdKasus() { return idKasus; }
    public String getKasus() { return kasus; }
    public String getStatusKasus() { return statusKasus; }
    public LocalDateTime getWaktuPencatatan() { return waktuPencatatan; }
    public void setStatusKasus(String statusKasus) { this.statusKasus = statusKasus; }

    @Override
    void displayInfo() {
        System.out.println("ID Kasus          : " + idKasus);
        System.out.println("Kasus             : " + kasus);
        System.out.println("Status Kasus      : " + statusKasus);
        System.out.println("ID Klien          : " + client.getIdKlien());
        System.out.println("Nama Klien        : " + client.getNamaKlien());
        System.out.println("Email             : " + client.getEmail());
        System.out.println("Nomor             : " + client.getNomorHp());
        System.out.println("Waktu Pencatatan  : " + waktuPencatatan);
    }
}
