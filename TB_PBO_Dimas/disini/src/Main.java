import java.util.Scanner;

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CaseManagement caseManager = new CaseManagementImpl();

        System.out.println("                                                                                                     ");
        System.out.println("                                                                                                     ☻");
        System.out.println("██╗      █████╗ ██╗    ██╗    ███████╗██╗██████╗ ███╗   ███╗    ██████╗ ██╗███╗   ███╗ █████╗ ███████╗");
        System.out.println("██║     ██╔══██╗██║    ██║    ██╔════╝██║██╔══██╗████╗ ████║    ██╔══██╗██║████╗ ████║██╔══██╗██╔════╝");
        System.out.println("██║     ███████║██║ █╗ ██║    █████╗  ██║██████╔╝██╔████╔██║    ██║  ██║██║██╔████╔██║███████║███████╗");
        System.out.println("██║     ██╔══██║██║███╗██║    ██╔══╝  ██║██╔══██╗██║╚██╔╝██║    ██║  ██║██║██║╚██╔╝██║██╔══██║╚════██║");
        System.out.println("███████╗██║  ██║╚███╔███╔╝    ██║     ██║██║  ██║██║ ╚═╝ ██║    ██████╔╝██║██║ ╚═╝ ██║██║  ██║███████║");
        System.out.println("╚══════╝╚═╝  ╚═╝ ╚══╝╚══╝     ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝    ╚═════╝ ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝");

        // Login logic
        if (!login(scanner)) {
            System.out.println("Login gagal setelah beberapa percobaan.");
            return;
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    caseManager.create(scanner);
                    break;
                case 2:
                    caseManager.readAll();
                    break;
                case 3:
                    caseManager.update(scanner);
                    break;
                case 4:
                    caseManager.delete(scanner);
                    break;
                case 5:
                    caseManager.showStatistics();
                    break;
                case 6:
                System.out.println("                                                                                             ");
                System.out.println("                                                                                             ");
                System.out.println("█▀▀██▀▀█                  ██                        ▀██                      ██  ▀██         ");
                System.out.println("   ██      ▄▄▄▄  ▄▄▄ ▄▄  ▄▄▄  ▄▄ ▄▄ ▄▄    ▄▄▄▄       ██  ▄▄   ▄▄▄▄    ▄▄▄▄  ▄▄▄   ██ ▄▄      ");
                System.out.println("   ██    ▄█▄▄▄██  ██▀ ▀▀  ██   ██ ██ ██  ▀▀ ▄██      ██ ▄▀   ▀▀ ▄██  ██▄ ▀   ██   ██▀ ██     ");
                System.out.println("   ██    ██       ██      ██   ██ ██ ██  ▄█▀ ██      ██▀█▄   ▄█▀ ██  ▄ ▀█▄▄  ██   ██  ██     ");
                System.out.println("  ▄██▄    ▀█▄▄▄▀ ▄██▄    ▄██▄ ▄██ ██ ██▄ ▀█▄▄▀█▀    ▄██▄ ██▄ ▀█▄▄▀█▀ █▀▄▄█▀ ▄██▄ ▄██▄ ██▄    ");
                System.out.println("                                                                                             ");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }

        scanner.close();
    }

    private static boolean login(Scanner scanner) {
        int attempts = 3;
        while (attempts > 0) {
            System.out.println("\n<<--- SELAMAT DATANG DI FIRMA HUKUM DIMAS --->>\n");
            System.out.println("      Silakan login terlebih dahulu untuk \n\t    mengakses aplikasi ini.\n");
            System.out.println("\n+-------------------------------+");
            System.out.println("|\t     Log in\t\t|");
            System.out.println("+-------------------------------+");
            System.out.print("Username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();
            
            if (username.equals("admin") && password.equals("admin")) {
                return true;
            }
            attempts--;
            System.out.println("\nLOGIN GAGAL | PERCOBAAN: " + attempts);
            System.out.println("=================================\n");
        }
        return false;
        
    }

    private static void displayMenu() {
        System.out.println("\n+-------------------------------+");
        System.out.println("|     Menu Manajemen Kasus      |");
        System.out.println("+-------------------------------+");
        System.out.println("1. Tambah Kasus (Create)");
        System.out.println("2. Lihat Semua Kasus (Read)");
        System.out.println("3. Update Kasus (Update)");
        System.out.println("4. Hapus Kasus (Delete)");
        System.out.println("5. Statistik (Operasi MTK)");
        System.out.println("6. Keluar\n");
        System.out.print("Pilihan Anda: ");
    }
}