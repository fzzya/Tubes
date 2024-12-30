import java.util.Scanner;

class SistemAkademikPaket {
    SLLCustomer Customer = null;
    SLLTransaksi Transaksi = null;
    SLLPaket Paket = null;
    SLLDetailpaket Detailpaket = null;
    SLLAdmin Admin = null;

    public static void main(String[] args) {
        SistemAkademikPaket sa = new SistemAkademikPaket();
        sa.runThis();
    }

    Scanner scanner = new Scanner(System.in);

    void runThis() {
        // dibuat 3 buah list untuk menampung masing-masing struktur data
        Transaksi = new SLLTransaksi();
        Customer = new SLLCustomer();
        Paket = new SLLPaket();
        Detailpaket = new SLLDetailpaket();
        Admin = new SLLAdmin();

        Admin.insert(new Admin(1, "admin", "admin123"));
        while (true) {
            System.out.println("1. Register Customer");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerCustomer(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

    }
    // void prosedurTambahPaket(int id, String nama, int credit){
    // System.out.println("Prosedur pembuatan Paket baru "+nama);
    // Paket.insert(new Paket(id,nama, credit));
    // }
    // void prosedurTambahTransaksi(int id, int biaya, String nama){
    // System.out.println("Prosedur pembuatan Transaksi baru "+nama);
    // Transaksi.insert(new Transaksi(id,biaya,nama));
    // }

    void prosedurTambahCustomer(String username, String password, String phone) {
        System.out.println("Prosedur penerimaan Customer baru baru " + username);
        Customer.insert(new Customer(username, password, phone));
    }

    void prosedurShowAllPaket() {
        System.out.println("Prosedur Melihat data seluruh Paket");
        Paket.printList();
    }

    void prosedurShowAllCustomer() {
        System.out.println("Prosedur Melihat data seluruh Customer");
        Customer.printList();
    }

    void prosedurShowAllTransaksi() {
        System.out.println("Prosedur Melihat data seluruh Transaksi:");
        NodeTransaksi current = Transaksi.head; // Assuming head is public
        boolean found = false;

        // Displaying all transactions regardless of their status
        while (current != null) {
            System.out.println(current.data); // Displaying transaction details
            found = true; // Indicate that at least one transaction was found
            current = current.next; // Move to the next transaction
        }

        if (!found) {
            System.out.println("Tidak ada transaksi yang ditemukan.");
        }
    }

    void prosedurSetTransaksi(String username, int idTransaksi) {
        System.out.println("Prosedur pemetakan Customer ke Transaksi");
        NodeTransaksi k = Transaksi.search(idTransaksi);
        NodeCustomer m = Customer.searchByUsername(username);

        if ((m.data != null) && (k.data != null)) {
            Customer.setTransaksi(m, k);
            System.out.println("Data Transaksi dan Customer ditemukan dan telah diset");
        } else {
            System.out.println("List kosong");
        }
    }

    void prosedurVerifikasiTransaksi(Scanner scanner) {
        System.out.println("Daftar Transaksi yang Menunggu Konfirmasi:");
        NodeTransaksi current = Transaksi.head; // Asumsi head adalah public
        boolean found = false;

        // Menampilkan semua transaksi yang statusnya "PENDING"
        while (current != null) {
            if (current.data.getstatus().equals("PENDING")) {
                System.out.println(current.data); // Menampilkan detail transaksi
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("Tidak ada transaksi yang menunggu konfirmasi.");
            return;
        }

        System.out.print("Masukkan ID Transaksi untuk disetujui/ditolak: ");
        int idTransaksi = scanner.nextInt();
        scanner.nextLine(); // Mengonsumsi newline

        NodeTransaksi transaksiNode = Transaksi.search(idTransaksi);
        if (transaksiNode == null || !transaksiNode.data.getstatus().equals("PENDING")) {
            System.out.println("Transaksi tidak ditemukan atau tidak dalam status PENDING.");
            return;
        }

        System.out.println("Detail Transaksi: " + transaksiNode.data);
        System.out.println("1. Setujui");
        System.out.println("2. Tolak");
        System.out.print("Pilih opsi: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Mengonsumsi newline

        if (choice == 1) {
            transaksiNode.data.setStatus("APPROVED");
            System.out.println("Transaksi disetujui.");
        } else if (choice == 2) {
            transaksiNode.data.setStatus("DECLINED");
            System.out.println("Transaksi ditolak.");
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    void prosedurSetPaketCustomer(String username, int idPaket) {
        System.out.println("Prosedur pemetakan Customer ke Paket");
        NodePaket c = Paket.search(idPaket);
        NodeCustomer m = Customer.searchByUsername(username);

        if ((m.data != null) && (c.data != null)) {
            Customer.setPaket(m, c);
            System.out.println("Data Customer dan Paket ditemukan dan telah diset");
        } else {
            System.out.println("List kosong");
        }
    }

    void prosedurViewPaketCustomer(String username) {
        System.out.println("Prosedur Daftar pengambilan SKS Customer");
        NodeCustomer c = Customer.searchByUsername(username);
        if (c == null) {
            System.out.println("Data Customer tidak ditemukan");
        } else {
            c.data.daftarPaket();
        }
    }

    void prosedurGantiNamaMK(int idPaket, String namaBaru) {
        System.out.println("Prosedur ganti nama Paket");
        NodePaket c = Paket.search(idPaket);
        if (c == null) {
            System.out.println("Data Paket tidak ditemukan");
        } else {
            c.data.setNamapaket(namaBaru); // barusan eror aku ganti dari setnama, menjadi setnama
        }
    }

    void prosedurLihatPaket() {
        System.out.println("Daftar Paket yang Tersedia:");
        Paket.printList(); // Memanggil metode printList() dari SLLPaket
    }

    void prosedurBeliPaket(NodeCustomer customer) {
        System.out.println("Daftar Paket yang Tersedia:");
        Paket.printList(); // Menampilkan semua paket yang tersedia

        System.out.print("Masukkan ID Paket yang ingin dibeli: ");
        int idPaket = scanner.nextInt();
        scanner.nextLine(); // Mengonsumsi newline

        NodePaket paketNode = Paket.search(idPaket);
        if (paketNode == null) {
            System.out.println("Paket tidak ditemukan.");
            return;
        }

        // Meminta pengguna untuk memasukkan tanggal dan lokasi
        System.out.print("Masukkan Tanggal (dd/MM/yyyy): ");
        String tanggal = scanner.nextLine();

        System.out.print("Masukkan Lokasi: ");
        String lokasi = scanner.nextLine();

        // Membuat transaksi baru dengan status "PENDING"
        int idTransaksi = Transaksi.getNextId(); // Mendapatkan ID transaksi berikutnya
        Transaksi.insert(
                new Transaksi(idTransaksi, paketNode.data.getDetailpaket().getBiaya(), "PENDING", tanggal, lokasi));

        // Mengaitkan transaksi dengan customer
        NodeTransaksi transaksiNode = Transaksi.search(idTransaksi);
        if (transaksiNode == null) {
            System.out.println("Transaksi dengan ID " + idTransaksi + " tidak ditemukan.");
            return; //
        }

        // Pastikan customer tidak null
        if (customer != null) {
            customer.setTransaksi(transaksiNode); // Mengaitkan transaksi dengan customer
            System.out.println("Transaksi untuk paket '" + paketNode.data.getNamapaket()
                    + "' telah dibuat dan menunggu konfirmasi dari admin.");
        } else {
            System.out.println("Customer tidak valid.");
        }
    }

    void tambahPaketEvent(Scanner scanner) {
        // Menampilkan pilihan nama paket yang tersedia
        System.out.println("Pilih Nama Paket:");
        System.out.println("1. Wedding");
        System.out.println("2. Birthday");
        System.out.println("3. Concert");
        System.out.println("4. Workshop");
        System.out.println("5. Seminar");
        System.out.println("6. Tambah Nama Paket Baru");
        System.out.print("Pilih nama paket: ");
        int namaPaketChoice = scanner.nextInt();
        scanner.nextLine(); // Untuk mengonsumsi newline

        String namaPaket = "";
        switch (namaPaketChoice) {
            case 1:
                namaPaket = "Wedding";
                break;
            case 2:
                namaPaket = "Birthday";
                break;
            case 3:
                namaPaket = "Concert";
                break;
            case 4:
                namaPaket = "Workshop";
                break;
            case 5:
                namaPaket = "Seminar";
                break;
            case 6:
                System.out.print("Masukkan Nama Paket Baru: ");
                namaPaket = scanner.nextLine();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }

        // Menampilkan pilihan jenis paket
        System.out.println("Pilih Jenis Paket:");
        System.out.println("1. Basic");
        System.out.println("2. Premium");
        System.out.println("3. VIP");
        System.out.print("Pilih jenis paket: ");
        int jenisPaketChoice = scanner.nextInt();
        scanner.nextLine(); // Untuk mengonsumsi newline

        String jenisPaket = "";
        switch (jenisPaketChoice) {
            case 1:
                jenisPaket = "Basic";
                break;
            case 2:
                jenisPaket = "Premium";
                break;
            case 3:
                jenisPaket = "VIP";
                break;
            default:
                System.out.println("Pilihan tidak valid.");
                return;
        }

        // Memasukkan ID dan Detail Paket
        System.out.print("Masukkan ID Paket: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Untuk mengonsumsi newline

        System.out.print("Masukkan ID Detail Paket: ");
        int idDetailPaket = scanner.nextInt();
        scanner.nextLine(); // Untuk mengonsumsi newline

        // Menambahkan detail paket
        System.out.print("Masukkan Nama Detail Paket: ");
        String namaDetailPaket = scanner.nextLine();

        System.out.print("Masukkan Detail Paket: ");
        String detailPaket = scanner.nextLine();

        System.out.print("Masukkan Biaya Detail Paket: ");
        int biaya = scanner.nextInt();
        scanner.nextLine(); // Untuk mengonsumsi newline

        // Membuat objek Detailpaket
        Detailpaket detailPaketBaru = new Detailpaket(idDetailPaket, namaDetailPaket, detailPaket, biaya);

        // Membuat objek Paket baru
        Paket paketBaru = new Paket(id, namaPaket, jenisPaket, detailPaketBaru);

        // Menambahkan paket baru ke dalam daftar Paket (asumsi ada metode insert)
        Paket.insert(paketBaru); // Pastikan Anda memiliki cara untuk menambah Paket

        System.out.println("Paket event '" + namaPaket + "' jenis '" + jenisPaket + "' telah ditambahkan!");
    }

    void editPaketEvent(Scanner scanner) {
        System.out.print("Masukkan ID Paket yang ingin diedit: ");
        int idPaket = scanner.nextInt();
        scanner.nextLine(); // Mengonsumsi newline

        NodePaket paketNode = Paket.search(idPaket);
        if (paketNode == null) {
            System.out.println("Paket tidak ditemukan.");
            return;
        }

        // Mengedit detail paket
        System.out.print("Masukkan ID Detail Paket Baru (tekan enter untuk tidak mengubah): ");
        String idDetailPaketBaru = scanner.nextLine();
        if (!idDetailPaketBaru.isEmpty()) {
            NodeDetailpaket detailNode = Detailpaket.search(Integer.parseInt(idDetailPaketBaru));
            if (detailNode != null) {
                paketNode.data.setDetailpaket(detailNode.data); // Mengubah detail paket
            } else {
                System.out.println("Detail paket tidak ditemukan.");
            }
        }

        // Mengedit biaya detail paket
        System.out.print("Masukkan Biaya Detail Paket Baru (tekan enter untuk tidak mengubah): ");
        String biayaBaru = scanner.nextLine();
        if (!biayaBaru.isEmpty()) {
            paketNode.data.getDetailpaket().setBiaya(Integer.parseInt(biayaBaru)); // Mengubah biaya detail paket
        }

        System.out.println("Paket telah diperbarui.");
    }

    void hapusPaketEvent(Scanner scanner) {
        System.out.print("Masukkan ID Paket yang ingin dihapus: ");
        int idPaket = scanner.nextInt();
        scanner.nextLine(); // Mengonsumsi newline

        NodePaket paketNode = Paket.search(idPaket);
        if (paketNode == null) {
            System.out.println("Paket tidak ditemukan.");
            return;
        }

        // Logika untuk menghapus paket dari linked list
        if (paketNode == Paket.head) {
            Paket.head = paketNode.next; // Jika paket yang dihapus adalah head
        } else {
            NodePaket current = Paket.head;
            while (current != null && current.next != paketNode) {
                current = current.next;
            }
            if (current != null) {
                current.next = paketNode.next; // Menghapus paket dari list
            }
        }

        System.out.println("Paket dengan ID " + idPaket + " telah dihapus.");
    }

    void registerCustomer(Scanner scanner) {
        // System.out.print("Enter ID: ");
        // int id = scanner.nextInt();
        // scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        // Cek apakah username sudah ada
        if (Customer.searchByUsername(username) != null) {
            System.out.println("Username already taken! Please choose another.");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        Customer.insert(new Customer(username, password, phone));
        System.out.println("Customer registered successfully!");
    }

    void login(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        NodeAdmin admin = Admin.searchByUsername(username);
        if (admin != null && admin.data.getPassword().equals(password)) {
            System.out.println("Admin Login Successful!");
            adminPanel();
            return;
        }

        NodeCustomer customer = Customer.searchByUsername(username);
        if (customer != null && customer.data.getPassword().equals(password)) {
            System.out.println("Customer Login Successful!");
            customerPanel(customer);
            return;
        }

        System.out.println("Invalid credentials or not registered.");
    }

    void prosedurLihatTransaksiUser(NodeCustomer customer) {
        System.out.println("Daftar Transaksi untuk " + customer.data.getUsername() + ":");
        NodeTransaksi current = Transaksi.head; // Asumsi head adalah public
        boolean found = false;

        // Menampilkan semua transaksi yang terkait dengan customer
        while (current != null) {
            if (current.data.getstatus().equals("PENDING") || current.data.getstatus().equals("APPROVED")
                    || current.data.getstatus().equals("DECLINED")) {
                System.out.println(current.data); // Menampilkan detail transaksi
                found = true; // Indicate that at least one transaction was found
            }
            current = current.next; // Move to the next transaction
        }

        if (!found) {
            System.out.println("Tidak ada transaksi yang ditemukan untuk customer ini.");
        }
    }

    void adminPanel() {
        System.out.println("Selamat datang di Panel Admin");
        while (true) {
            System.out.println("1. Tambah Paket Event");
            System.out.println("2. Lihat daftar paket");
            System.out.println("3. Lihat semua transaksi");
            System.out.println("4. Verifikasi transaksi");
            System.out.println("5. Edit Paket Event");
            System.out.println("6. Hapus Paket Event");
            System.out.println("7. Kembali ke Menu Utama");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Mengonsumsi newline

            switch (choice) {
                case 1:
                    tambahPaketEvent(scanner);
                    break;
                case 2:
                    prosedurShowAllPaket();
                    break;
                case 3:
                    prosedurShowAllTransaksi();
                    break;
                case 4:
                    prosedurVerifikasiTransaksi(scanner);
                    break;
                case 5:
                    editPaketEvent(scanner);
                    break;
                case 6:
                    hapusPaketEvent(scanner);
                    break;
                case 7:
                    return; // Kembali ke menu utama
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    void customerPanel(NodeCustomer customer) {
        System.out.println("Welcome " + customer.data.getUsername() + "!");
        while (true) {
            System.out.println("1. Lihat Daftar Paket");
            System.out.println("2. Beli Paket");
            System.out.println("3. Lihat Status Transaksi");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Mengonsumsi newline

            switch (choice) {
                case 1:
                    prosedurLihatPaket(); // Memanggil metode untuk melihat paket
                    break;
                case 2:
                    prosedurBeliPaket(customer); // Memanggil metode untuk membeli paket
                    break;
                case 3:
                    prosedurLihatTransaksiUser(customer); // Memanggil metode untuk melihat status transaksi
                    break;
                case 4:
                    return; // Kembali ke menu utama
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

}

class Customer {
    // private int id;
    private String username;
    private String password;
    private String phone;
    private NodeTransaksi transaksi;
    private SLLPaket PaketCustomer;

    /*
     * NodeTransaksi digunakan untuk menyimpan informasi Customer tersebut dipetakan
     * ke
     * Transaksi tertentu
     */
    public Customer(String username, String password, String phone) {
        // this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        PaketCustomer = new SLLPaket(); // create PaketCustomer

    }

    // public int getId() {
    // return id;
    // }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getphone() {
        return phone;
    }

    public void setPaket(NodePaket Paket) {
        PaketCustomer.insert(Paket.data);

    }

    public NodeTransaksi getTransaksi() {
        return transaksi;
    }

    public void daftarPaket() {
        // Pemanggilan menampilkan data Paket yang diambil Customer dari
        // PaketCustomer
        PaketCustomer.printListCustomer();
    }

    public void setTransaksi(NodeTransaksi transaksiNode) {
        if (transaksiNode == null) {
            throw new IllegalArgumentException("Transaksi node cannot be null");
        }
        this.transaksi = transaksiNode; // Set the transaction
        System.out.println("Transaksi telah diatur untuk customer: " + this.toString());
    }

    public String toString() {
        if (transaksi == null) {
            return " " + username;
        } else {
            return " " + username + " " + transaksi.toString();
        }
    }
}

class Transaksi {
    private int id;
    private int biaya;
    private String status;
    private String tanggal;
    private String lokasi;

    // private SLLPaket PaketAdmin;
    // private SLLTransaksi PaketCustomer;
    // kurag id detail paket
    public Transaksi(int id, int biaya, String status, String tanggal, String lokasi) {
        this.id = id;
        this.biaya = biaya;
        this.status = status;
        this.tanggal = tanggal;
        this.lokasi = lokasi;
        // PaketAdmin = new SLLPaket(); // create PaketAdmin
        // PaketCustomer = new SLLTransaksi();
    }

    public int getid() {
        return id;
    }

    public int getbiaya() {
        return biaya;
    }

    public String getstatus() {
        return status;
    }

    public String toString() {
        return "id: " + id + ", biaya: " + biaya + ", Status: " + status + ", Tanggal: " + tanggal + ", Lokasi: "
                + lokasi;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getLokasi() {
        return lokasi;
    }

}

class Paket {
    private int id;
    private String namapaket;
    private String jenisPaket; // Menambahkan atribut jenis paket
    private Detailpaket detailpaket; // Menggunakan objek Detailpaket sebagai relasi

    public Paket(int id, String namapaket, String jenisPaket, Detailpaket detailpaket) {
        this.id = id;
        this.namapaket = namapaket;
        this.jenisPaket = jenisPaket; // Menyimpan jenis paket
        this.detailpaket = detailpaket;
    }

    // Getter untuk jenisPaket
    public String getJenisPaket() {
        return jenisPaket;
    }

    public int getId() {
        return id;
    }

    public String getNamapaket() {
        return namapaket;
    }

    public void setNamapaket(String namapaket) {
        this.namapaket = namapaket;
    }

    public Detailpaket getDetailpaket() {
        return detailpaket;
    }

    public void setDetailpaket(Detailpaket detailpaket) {
        this.detailpaket = detailpaket;
    }

    @Override
    public String toString() {
        return id + " " + namapaket + " " + jenisPaket + " " + detailpaket.toString(); // Menampilkan jenis paket
    }
}

class Detailpaket {
    private int id;
    private String namadetailpaket;
    private String detailpaket;
    private int biaya;

    public Detailpaket(int id, String namadetailpaket, String detailpaket, int biaya) {
        this.id = id;
        this.namadetailpaket = namadetailpaket;
        this.detailpaket = detailpaket;
        this.biaya = biaya;
    }

    public int getId() {
        return id;
    }

    public String getNamadetailpaket() {
        return namadetailpaket;
    }

    public void setNamadetailpaket(String namadetailpaket) {
        this.namadetailpaket = namadetailpaket;
    }

    public String getDetailpaket() {
        return detailpaket;
    }

    public void setDetailpaket(String detailpaket) {
        this.detailpaket = detailpaket;
    }

    public int getBiaya() {
        return biaya;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public String toString() {
        return namadetailpaket + " " + detailpaket + " " + biaya;
    }
}

class Admin {
    private int id;
    private String usernameAdmin;
    private String password;
    // private String phone;
    // private NodeTransaksi Transaksi;
    // private SLLPaket PaketAdmin;

    /*
     * NodeTransaksi digunakan untuk menyimpan informasi Admin tersebut dipetakan ke
     * Transaksi tertentu
     */
    public Admin(int id, String usernameAdmin, String password) {
        this.id = id;
        this.usernameAdmin = usernameAdmin;
        this.password = password;
        // this.phone = phone;
        // PaketAdmin = new SLLPaket(); // create PaketAdmin

    }

    public int getId() {
        return id;
    }

    public String getUsernameAdmin() {
        return usernameAdmin;
    }

    public String getPassword() {
        return password;
    }

}

class NodeTransaksi {
    Transaksi data;
    NodeTransaksi next;

    NodeTransaksi(Transaksi data) {
        this.data = data;
        next = null;
    }

    public String toString() {
        return data.toString();
    }
}

class NodeCustomer {
    Customer data;
    NodeCustomer next;

    NodeCustomer(Customer data) {
        this.data = data;
        next = null;
    }

    public void setTransaksi(NodeTransaksi transaksiNode) {
        if (transaksiNode == null) {
            throw new IllegalArgumentException("Transaksi node cannot be null");
        }
        this.data.setTransaksi(transaksiNode); // Mengatur transaksi untuk customer
        System.out.println("Transaksi telah diatur untuk customer: " + this.data.getUsername());
    }

    public String toString() {
        return data.toString();
    }

}

class NodePaket {
    Paket data;
    NodePaket next;

    NodePaket(Paket data) {
        this.data = data;
        next = null;

    }

    public String toString() {
        return data.toString();
    }

}

class NodeDetailpaket {
    Detailpaket data;
    NodeDetailpaket next;

    NodeDetailpaket(Detailpaket data) {
        this.data = data;
        next = null;

    }

    public String toString() {
        return data.toString();
    }

}

class NodeAdmin {
    Admin data;
    NodeAdmin next;

    NodeAdmin(Admin data) {
        this.data = data;
        next = null;

    }

    public String toString() {
        return data.toString();
    }

}

class SLLAdmin {
    NodeAdmin head;

    public int insert(Admin data) {
        NodeAdmin nn = new NodeAdmin(data);
        NodeAdmin current = head;

        if (current == null) {
            nn.next = null;
            head = nn;
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = nn;
        }
        return 1;
    }

    public NodeAdmin searchByUsername(String username) {
        NodeAdmin current = head;
        while (current != null) {
            if (current.data.getUsernameAdmin().equals(username)) {
                return current; // Kembalikan node yang ditemukan
            }
            current = current.next;
        }
        return null; // Kembalikan null jika tidak ditemukan
    }

    public void printList() {
        NodeAdmin current = head;
        while (current.next != null) {
            System.out.println(current.data.toString() + " ");
            current = current.next;
        }
    }

}

class SLLDetailpaket {
    NodeDetailpaket head;

    public int insert(Detailpaket data) {
        NodeDetailpaket nn = new NodeDetailpaket(data);
        NodeDetailpaket current = head;

        if (current == null) {
            nn.next = null;
            head = nn;
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = nn;
        }
        return 1;
    }

    public NodeDetailpaket search(int id) {
        NodeDetailpaket current = head;
        if (current == null) {
            return null;
        } else {
            if (current.data.getId() == id) {
                return head;
            } else {
                // boolean found = false;
                while (current != null) {
                    if (current.data.getId() == id) {
                        return current;
                    }
                    current = current.next;
                }
            }
        }
        return null;
    }

    public void printList() {
        NodeDetailpaket current = head;
        while (current.next != null) {
            System.out.println(current.data.toString() + " ");
            current = current.next;
        }
    }

}

class SLLTransaksi {
    NodeTransaksi head; // head of list

    public int insert(Transaksi data) {
        NodeTransaksi nn = new NodeTransaksi(data);
        if (head == null) {
            head = nn; // Jika list kosong, set head ke node baru
        } else {
            NodeTransaksi current = head;
            while (current.next != null) {
                current = current.next; // Mencari node terakhir
            }
            current.next = nn; // Menambahkan node baru di akhir list
        }
        return 1;
    }

    public NodeTransaksi search(int id) {
        NodeTransaksi current = head;
        while (current != null) {
            if (current.data.getid() == id) {
                return current; // Kembalikan node yang ditemukan
            }
            current = current.next;
        }
        return null; // Kembalikan null jika tidak ditemukan
    }

    public int getNextId() {
        int nextId = 1; // Mulai dari 1
        NodeTransaksi current = head;
        while (current != null) {
            if (current.data.getid() >= nextId) {
                nextId = current.data.getid() + 1; // Update nextId jika ID lebih besar
            }
            current = current.next;
        }
        return nextId; // Kembalikan ID berikutnya
    }

    public void printList() {
        NodeTransaksi current = head;
        while (current.next != null) {
            System.out.println(current.data.toString() + " ");
            current = current.next;
        }
    }

}

class SLLCustomer {
    NodeCustomer head; // head of list

    public int insert(Customer data) {
        NodeCustomer nn = new NodeCustomer(data);
        NodeCustomer current = head;

        if (current == null) {
            nn.next = null;
            head = nn;
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = nn;
        }
        return 1;
    }

    public NodeCustomer searchByUsername(String username) {
        NodeCustomer current = head;
        while (current != null) {
            if (current.data.getUsername().equals(username)) {
                return current; // Kembalikan node yang ditemukan
            }
            current = current.next;
        }
        return null; // Kembalikan null jika tidak ditemukan
    }

    public void setTransaksi(NodeCustomer Customer, NodeTransaksi Transaksi) {
        Customer.data.setTransaksi(Transaksi);
        System.out.println(Customer.data.getUsername() + " dipetakan ke Transaksi " + Transaksi.toString());
    }

    public void setPaket(NodeCustomer Customer, NodePaket Paket) {
        Customer.data.setPaket(Paket);
        System.out.println(Customer.data.getUsername() + " mengambil mata kuliah " + Paket.toString());
    }

    public void printList() {
        NodeCustomer current = head;
        while (current != null) {
            System.out.println(current.data.toString() + " ");

            current = current.next;
        }
    }
}

class SLLPaket {
    NodePaket head; // head of list

    public int insert(Paket data) {
        NodePaket nn = new NodePaket(data);
        NodePaket current = head;

        if (current == null) {
            nn.next = null;
            head = nn;
        } else {
            while (current.next != null) {
                current = current.next;
            }
            current.next = nn;
        }
        return 1;
    }

    public NodePaket search(int id) {
        NodePaket current = head;
        if (current == null) {
            return null;
        } else {
            if (current.data.getId() == id) {
                return head;
            } else {
                // boolean found = false;
                while (current != null) {
                    if (current.data.getId() == id) {
                        return current;
                    }
                    current = current.next;
                }
            }

        }
        return null;

    }

    // public void printList() {
    // NodePaket current = head;
    // while (current != null) {
    // System.out.println(current.data.toString() + " ");
    // current = current.next;
    // }
    // }

    public void printList() {
        NodePaket current = head;
        if (current == null) {
            System.out.println("Tidak ada paket yang tersedia.");
            return;
        }
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
    }

    public void printListCustomer() {
        NodePaket current = head;
        while (current != null) {
            System.out.println(current.data.toString() + " ");
            current = current.next;
        }

    }

}
