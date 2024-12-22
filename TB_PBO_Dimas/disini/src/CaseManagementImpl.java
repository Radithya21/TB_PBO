import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//! MATERI 2 & 7: Interface & Implementasinya dan Collection Framework
class CaseManagementImpl implements CaseManagement {

    // Collection Framework: ArrayList
    private List<Case> caseCache = new ArrayList<>();

    //! MATERI 8 JDBC DAN CRUD OPERATION
    @Override
    public void create(Scanner scanner) {
        System.out.print("\nMasukkan ID Kasus: ");
        String idKasus = scanner.nextLine().trim();

        System.out.print("Masukkan Kasus: ");
        String kasus = scanner.nextLine().trim();

        System.out.print("Masukkan Status Kasus (selesai/proses): ");
        String statusKasus = scanner.nextLine().trim().toLowerCase();

        System.out.print("Masukkan ID Klien: ");
        String idKlien = scanner.nextLine().trim();

        System.out.print("Masukkan Nama Klien: ");
        String namaKlien = scanner.nextLine().trim();

        System.out.print("Masukkan Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Masukkan nomor handphone: ");
        String nomorHp = scanner.nextLine().trim();

        Client client = new Client(idKlien, namaKlien, email, nomorHp);
        Case newCase = new Case(idKasus, kasus, statusKasus, client);

        saveToDatabase(newCase, client);
        caseCache.add(newCase);
    }

    @Override
    public void readAll() {
        caseCache.clear();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM manajemenKasus")) {

            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("\t\t\tDaftar Kasus");
            System.out.println("+-----------------------------------------------------+");

            while (rs.next()) {
                Client client = new Client(
                    rs.getString("idKlien"),
                    rs.getString("namaKlien"),
                    rs.getString("email"),
                    rs.getString("nomorHp")
                );
                
                Case kasus = new Case(
                    rs.getString("idKasus"),
                    rs.getString("kasus"),
                    rs.getString("statusKasus"),
                    client
                );
                
                caseCache.add(kasus);
                kasus.displayInfo();
                System.out.println("+-----------------------------------------------------+");
            }

        } catch (SQLException e) {
            System.out.println("\nError saat mengambil data dari database: " + e.getMessage());
        }
    }

    @Override
    public void update(Scanner scanner) {
        System.out.print("Masukkan ID Kasus yang ingin diperbarui: ");
        String idKasus = scanner.nextLine().trim();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Cek kasus exists
            String checkSql = "SELECT statusKasus FROM manajemenKasus WHERE idKasus = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, idKasus);
                ResultSet rs = checkStmt.executeQuery();
                
                if (!rs.next()) {
                    System.out.println("\n==========================================.");
                    System.out.println("Kasus dengan ID tersebut tidak ditemukan.");
                    System.out.println("==========================================.");
                    return;
                }
                
                String statusSaatIni = rs.getString("statusKasus");
                System.out.println("Status saat ini: " + statusSaatIni);
            }

            System.out.print("Masukkan Status Kasus baru (selesai/proses): ");
            String statusKasus = scanner.nextLine().trim().toLowerCase();

            if (!statusKasus.equals("selesai") && !statusKasus.equals("proses")) {
                System.out.println("\nStatus tidak valid. Hanya menerima 'selesai' atau 'proses'.");
                return;
            }

            String updateSql = "UPDATE manajemenKasus SET statusKasus = ?, waktuPencatatan = ? WHERE idKasus = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, statusKasus);
                pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                pstmt.setString(3, idKasus);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("\nStatus kasus berhasil diperbarui!");
                    // Update cache
                    updateCaseInCache(idKasus, statusKasus);
                } else {
                    System.out.println("\nGagal memperbarui status kasus.");
                }
            }
        } catch (SQLException e) {
            System.out.println("\nError saat memperbarui data: " + e.getMessage());
        }
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.print("Masukkan ID Kasus yang ingin dihapus: ");
        String idKasus = scanner.nextLine().trim();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM manajemenKasus WHERE idKasus = ?")) {

            pstmt.setString(1, idKasus);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\n==========================================.");
                System.out.println("        Kasus berhasil dihapus.");
                System.out.println("==========================================.");
                // Update cache
                removeCaseFromCache(idKasus);
            } else {
                System.out.println("\n==========================================.");
                System.out.println("Kasus dengan ID tersebut tidak ditemukan.");
                System.out.println("==========================================.");
            }
        } catch (SQLException e) {
            System.out.println("\nError saat menghapus data: " + e.getMessage());
        }
    }

    @Override
    public void showStatistics() {

        // Collection Framework: HashMap
        Map<String, Integer> stats = new HashMap<>();
        stats.put("total", 0);
        stats.put("proses", 0);
        stats.put("selesai", 0);

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                "SELECT " +
                "COUNT(*) as total_kasus, " +
                "SUM(CASE WHEN statusKasus = 'proses' THEN 1 ELSE 0 END) as kasus_proses, " +
                "SUM(CASE WHEN statusKasus = 'selesai' THEN 1 ELSE 0 END) as kasus_selesai " +
                "FROM manajemenKasus")) {

            //! MATERI 4 PERULANGAN, PERCABANGAN, DAN OPERASI MATEMATIKA        
            if (rs.next()) {
                int totalKasus = rs.getInt("total_kasus");
                int kasusProses = rs.getInt("kasus_proses");
                int kasusSelesai = rs.getInt("kasus_selesai");

                double persenProses = totalKasus > 0 ? (kasusProses * 100.0 / totalKasus) : 0;
                double persenSelesai = totalKasus > 0 ? (kasusSelesai * 100.0 / totalKasus) : 0;

                System.out.println("\n+-----------------------------------------------------+");
                System.out.println("\t\tStatistik Kasus");
                System.out.println("+-----------------------------------------------------+");
                System.out.println("Total Kasus: " + totalKasus);
                System.out.println("\nStatus Proses:");
                System.out.printf("- Jumlah: %d kasus\n", kasusProses);
                System.out.printf("- Persentase: %.1f%%\n", persenProses);
                System.out.println("\nStatus Selesai:");
                System.out.printf("- Jumlah: %d kasus\n", kasusSelesai);
                System.out.printf("- Persentase: %.1f%%\n", persenSelesai);
                System.out.println("+-----------------------------------------------------+");
            }
        } catch (SQLException e) {
            System.out.println("\nError saat mengambil statistik: " + e.getMessage());
        }
    }

    //! MATERI 6 EXCEPTION HANDLING
    private void saveToDatabase(Case kasus, Client client) {
        String sql = "INSERT INTO manajemenKasus (idKasus, kasus, statusKasus, idKlien, namaKlien, email, nomorHp, waktuPencatatan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            

            //! MATERI 5 MANIPULASI METHOD DATE
            // Memformat waktu sebelum menyimpan
            LocalDateTime waktuSekarang = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
            String waktuTerformat = waktuSekarang.format(formatter);


            //! MATERI 5 MANIPULASI METHOD STRING
            pstmt.setString(1, kasus.getIdKasus().toUpperCase());
            pstmt.setString(2, kasus.getKasus());
            pstmt.setString(3, kasus.getStatusKasus().toLowerCase());
            pstmt.setString(4, client.getIdKlien());
            pstmt.setString(5, client.getNamaKlien());
            pstmt.setString(6, client.getEmail());
            pstmt.setString(7, client.getNomorHp());
            pstmt.setTimestamp(8, Timestamp.valueOf(waktuSekarang));
            
            System.out.println("\nWaktu pencatatan (terformat): " + waktuTerformat);
            
            //! MATERI 5 MANIPULASI METHOD DATE
            // Tambahkan durasi 3 hari dan 2 jam ke waktu pencatatan
            LocalDateTime waktuTenggat = tambahDurasi(waktuSekarang, 3, 2);
            System.out.println("Waktu tenggat: " + waktuTenggat.format(formatter));

            

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\n==========================================.");
                System.out.println("    Kasus berhasil disimpan ke database.");
                System.out.println("==========================================.");
            } else {
                System.out.println("\nKasus gagal disimpan.");
            }
        } catch (SQLException e) {
            System.out.println("\nError saat menyimpan data ke database: " + e.getMessage());
        }
    }

    private void updateCaseInCache(String idKasus, String newStatus) {
        for (Case kasus : caseCache) {
            if (kasus.getIdKasus().equals(idKasus)) {
                kasus.setStatusKasus(newStatus);
                break;
            }
        }
    }

    private void removeCaseFromCache(String idKasus) {
        caseCache.removeIf(kasus -> kasus.getIdKasus().equals(idKasus));
    }

    //! MATERI 5 MANIPULASI METHOD DATE
    // Method untuk menambah durasi ke waktu sekarang
public LocalDateTime tambahDurasi(LocalDateTime waktu, int hari, int jam) {
    return waktu.plusDays(hari).plusHours(jam);
}
                            
}