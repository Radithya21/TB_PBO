import java.util.Scanner;

//! MATERI 2: Interface dan Implementasinya
// Interface untuk operasi dasar manajemen kasus
interface CaseManagement {
    void create(Scanner scanner);
    void readAll();
    void update(Scanner scanner);
    void delete(Scanner scanner);
    void showStatistics();
}
