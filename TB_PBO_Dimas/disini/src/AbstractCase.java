import java.time.LocalDateTime;

//! MATERI 1 & 3: Class, Object, Constructor & Inheritance
// Abstract class sebagai superclass
abstract class AbstractCase {
    protected String idKasus;
    protected String kasus;
    protected String statusKasus;
    protected LocalDateTime waktuPencatatan;

    //Constructor
    public AbstractCase(String idKasus, String kasus, String statusKasus) {
        this.idKasus = idKasus;
        this.kasus = kasus;
        this.statusKasus = statusKasus;
        this.waktuPencatatan = LocalDateTime.now();
    }

    abstract void displayInfo();
}
