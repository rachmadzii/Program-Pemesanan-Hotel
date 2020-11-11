package com.ksm_android;

import java.io.IOException;
import java.util.Scanner;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

abstract class JenisKamar {
    public abstract void fasilitas_kamar();
}

interface Kamar {
    void set_no_kamar(int no_kamar);

    int get_harga();

    String get_jenis_kamar();
}

class SingleRoom extends JenisKamar implements Kamar {
    int no_kamar;
    private final double harga = 80000.00;
    String jenis_kamar = "Single Room";
    boolean empty_room = true;

    @Override
    public void set_no_kamar(int no_kamar) {
        this.no_kamar = no_kamar;
    }

    @Override
    public int get_harga() {
        return (int) harga;
    }

    @Override
    public String get_jenis_kamar() {
        return this.jenis_kamar;
    }

    public void fasilitas_kamar() {
        System.out.println("- 1 Tempat tidur");
        System.out.println("- Tipe : Queen Bed");
        System.out.println("- Wifi gratis");
        System.out.println("- Televisi satelit");
        System.out.println("- Kamar mandi");
        System.out.println("- 1 Air mineral");
    }
}

class DoubleRoom extends JenisKamar implements Kamar {
    int no_kamar;
    private final double harga = 140000.00;
    String jenis_kamar = "Double Room";
    boolean empty_room = true;

    @Override
    public void set_no_kamar(int no_kamar) {
        this.no_kamar = no_kamar;
    }

    @Override
    public int get_harga() {
        return (int) harga;
    }

    @Override
    public String get_jenis_kamar() {
        return this.jenis_kamar;
    }

    public void fasilitas_kamar() {
        System.out.println("- 2 Tempat tidur");
        System.out.println("- Tipe : Queen Bed");
        System.out.println("- Wifi gratis");
        System.out.println("- Televisi satelit");
        System.out.println("- Kamar mandi");
        System.out.println("- 2 Air mineral");
    }
}

abstract class Pesanan {
    public int no_kamar;
    private String no_pesanan;
    public String[] tgl_check_in = new String[3];
    public String[] tgl_check_out = new String[3];
    public int hari;
    public int total_harga;
    public String jenis_kamar;
    public boolean valid = true;

    public void set_no_pesanan(int no_pesanan) {
        this.no_pesanan = Integer.toString(no_pesanan);
    }

    public String get_no_pesanan() {
        return this.no_pesanan;
    }

    public void set_tanggal_check_in(String tgl_check_in) {
        this.tgl_check_in = tgl_check_in.split("/");
    }

    public void set_tanggal_check_out(String tgl_check_out) {
        this.tgl_check_out = tgl_check_out.split("/");

    }

    public void set_harga(Kamar kamar) {
        this.total_harga = kamar.get_harga() * hari;
    }

    public void set_jenis_kamar(Kamar kamar) {
        this.jenis_kamar = kamar.get_jenis_kamar();
    }

    public void validasi_tanggal_check_in(){
        try{
            if(tgl_check_in.length == 3){
                if(Integer.parseInt(tgl_check_in[0]) > 31){
                    throw new ArithmeticException();  
                }else if(Integer.parseInt(tgl_check_in[1]) > 12){
                    throw new ArithmeticException();  
                }
            } else {
                throw new ArithmeticException();
            }
        } catch (ArithmeticException e) {
            this.valid = false;
            System.out.println("[!] Tanggal Check In yang dimasukan di luar jangkauan");
        }
    }
    
    public void validasi_tanggal_check_out(){
        try{
            if(tgl_check_out.length == 3){
                if(Integer.parseInt(tgl_check_out[0]) > 31){
                    throw new ArithmeticException();  
                }else if(Integer.parseInt(tgl_check_out[1]) > 12){
                    throw new ArithmeticException();  
                }else if(this.hari < 0){
                    throw new ArithmeticException();
                }
            } else {
                throw new ArithmeticException();
            }
        } catch (ArithmeticException e) {
            this.valid = false;
            System.out.println("[!] Tanggal Check Out yang dimasukan di luar jangkauan");
        }
    }
    
    public void kalkulasi_hari() {
        DateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date tanggal_check_in = date_format.parse(String.join("/", this.tgl_check_in));
            Date tanggal_check_out = date_format.parse(String.join("/", this.tgl_check_out));

            long check_in = tanggal_check_in.getTime();
            long check_out = tanggal_check_out.getTime();

            long diff = check_out - check_in;
            this.hari = (int) diff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            System.out.println("[!] Format tanggal yang dimasukan salah");
        }
    }
}

class Customer extends Pesanan {
    String nama, no_telp;
    final String NIK;

    Customer(String nama, String NIK, String no_telp) {
        this.nama = nama;
        this.NIK = NIK;
        this.no_telp = no_telp;
    }

    public void display_data() {
        System.out.println("No. Pesanan        : " + this.get_no_pesanan());
        System.out.println("Nama               : " + this.nama);
        System.out.println("NIK                : " + this.NIK);
        System.out.println("No. Telepon        : " + this.no_telp);
        System.out.println("Tanggal Check In   : " + String.join("/", this.tgl_check_in));
        System.out.println("Tanggal Check Out  : " + String.join("/", this.tgl_check_out));
        System.out.println("Jenis Kamar        : " + this.jenis_kamar);
        System.out.println("No. kamar          : " + this.no_kamar);
        System.out.println("Total Harga        : " + this.total_harga);
        System.out.print("------------------------------------------------------");
    }
}

class CustomerList {
    int no_pesanan = 100;
    int pelanggan_single_room = 0;
    int pelanggan_double_room = 0;
    int banyak_pelanggan = 0;
    Customer customer_list[] = new Customer[40];

    public void add(Customer customer) {
        this.customer_list[this.banyak_pelanggan] = customer;
        this.customer_list[this.banyak_pelanggan].set_no_pesanan(this.no_pesanan);

        if ("Single Room".equals(customer.jenis_kamar)) {
            this.pelanggan_single_room++;
        } else {
            this.pelanggan_double_room++;
        }

        this.banyak_pelanggan = this.pelanggan_single_room + this.pelanggan_double_room;
        this.no_pesanan++;
    }

    public void display_list_data(String jenis_kamar) {
        if ("Single Room".equals(jenis_kamar)) {
            if (this.pelanggan_single_room != 0) {
                for (int i = 0; i < this.banyak_pelanggan; i++) {
                    if ("Single Room".equals(this.customer_list[i].jenis_kamar)) {
                        customer_list[i].display_data();
                        System.out.println("\n");
                    }
                }
            } else {
                System.out.println("[!] Tidak ada data penginapan");
                System.out.println("------------------------------------------------------\n");
            }
        } else if ("Double Room".equals(jenis_kamar)) {
            if (this.pelanggan_double_room != 0) {
                for (int i = 0; i < this.banyak_pelanggan; i++) {
                    if ("Double Room".equals(this.customer_list[i].jenis_kamar)) {
                        customer_list[i].display_data();
                        System.out.println("\n");
                    }
                }
            } else {
                System.out.println("[!] Tidak ada data penginapan");
                System.out.println("------------------------------------------------------\n");
            }
        }
    }

    public void display_search_data(int no_pesanan) {
        for (int i = 0; i < banyak_pelanggan; i++) {
            if (Integer.parseInt(customer_list[i].get_no_pesanan()) == no_pesanan) {
                customer_list[i].display_data();
            }
        }
    }

    public boolean search_data(int no_pesanan) {
        if (no_pesanan <= this.no_pesanan) {
            for (int i = 0; i < banyak_pelanggan; i++) {
                if (Integer.parseInt(customer_list[i].get_no_pesanan()) == no_pesanan) {
                    return true;
                }
            }
        }
        return false;
    }

    public void check_out(int no_pesanan) {
        for (int i = 0; i < banyak_pelanggan; i++) {
            if (Integer.parseInt(customer_list[i].get_no_pesanan()) == no_pesanan) {
                if ("Single Room".equals(customer_list[i].jenis_kamar)) {
                    this.pelanggan_single_room--;
                } else if ("Double Room".equals(customer_list[i].jenis_kamar)) {
                    this.pelanggan_double_room--;
                }
                for (int j = i; j < banyak_pelanggan - 1; j++) {
                    customer_list[j] = customer_list[j + 1];
                }
                break;
            }
        }
        this.banyak_pelanggan = this.pelanggan_single_room + this.pelanggan_double_room;
    }

    public int get_no_kamar(int no_pesanan) {
        for (int i = 0; i < banyak_pelanggan; i++) {
            if (Integer.parseInt(customer_list[i].get_no_pesanan()) == no_pesanan) {
                return customer_list[i].no_kamar;
            }
        }
        return 0;
    }
}

public class project_akhir {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Scanner enter = new Scanner(System.in).useDelimiter("\n");
        char menu, lanjut;
        int no_kamar = 0;
        boolean is_continue;
        
        // instantiasi
        CustomerList customer_list = new CustomerList();
        SingleRoom single_room_list[] = new SingleRoom[20];
        DoubleRoom double_room_list[] = new DoubleRoom[20];

        for (int i = 0; i < 20; i++) {
            SingleRoom single_room = new SingleRoom();
            DoubleRoom double_room = new DoubleRoom();

            single_room.no_kamar = i + 1;
            double_room.no_kamar = i + 1 + 20;

            single_room_list[i] = single_room;
            double_room_list[i] = double_room;
        }

        while (true) {
            clrscr();
            System.out.println("=====================================");
            System.out.println("       SISTEM PENGINAPAN HOTEL       ");
            System.out.println("=====================================");
            System.out.println("             MENU PROGRAM            ");
            System.out.println("-------------------------------------");
            System.out.println("1. |  Registrasi Pemesanan           ");
            System.out.println("2. |  No. Kamar yang Tersedia        ");
            System.out.println("3. |  Lihat Data Penginapan          ");
            System.out.println("4. |  Cari Data Penginapan           ");
            System.out.println("5. |  Check Out Penginapan           ");
            System.out.println("6. |  Keluar Program                 ");
            System.out.println("=====================================");
            System.out.print("Pilih Menu [1..6] > ");
            menu = input.next().charAt(0);

            switch (menu) {
                case '1':
                    is_continue = true;
                    while (is_continue) {
                        clrscr();
                        System.out.println("========================================================");
                        System.out.println("                  REGISTRASI PEMESANAN                  ");
                        System.out.println("--------------------------------------------------------");
                        System.out.print("Masukan Nama Lengkap      : ");
                        String nama = enter.next();
                        System.out.print("Masukan NIK               : ");
                        String NIK = input.next();
                        System.out.print("Masukan No. Telepon       : ");
                        String no_telp = input.next();
                        System.out.print("Masukan Tanggal Check In  : ");
                        String tgl_check_in = input.next();
                        System.out.print("Masukan Tanggal Check Out : ");
                        String tgl_check_out = input.next();
                        System.out.println("--------------------------------------------------------");
                        
                        Customer customer = new Customer(nama, NIK, no_telp);
                        customer.set_tanggal_check_in(tgl_check_in);
                        customer.set_tanggal_check_out(tgl_check_out);
                        customer.kalkulasi_hari();
                        customer.validasi_tanggal_check_in();
                        customer.validasi_tanggal_check_out();
                        
                        if(customer.valid){
                            while (true) {
                                System.out.print("\nTekan Enter untuk beralih ke halaman selanjutnya ");
                                enter.next();
                                clrscr();
                                System.out.println("=================================");
                                System.out.println("       Pilihan Jenis Kamar       ");
                                System.out.println("---------------------------------");
                                System.out.println("[1] Single Room                  ");
                                System.out.println("[2] Double Room                  ");
                                System.out.println("=================================");
                                System.out.print("Pilih Menu [1..2] > ");
                                menu = input.next().charAt(0);

                                if (menu == '1') {
                                    clrscr();
                                    System.out.println("=====================================================");
                                    System.out.println("                     SINGLE ROOM                     ");
                                    System.out.println("-----------------------------------------------------");
                                    System.out.println("Fasilitas kamar : ");
                                    single_room_list[0].fasilitas_kamar();
                                    System.out.println("\n> Harga per hari : " + single_room_list[0].get_harga());
                                    System.out.println("\nNomor Kamar yang tersedia");
                                    for (int i = 0; i < 20; i++) {
                                        if (single_room_list[i].no_kamar != -1) {
                                            System.out.print(single_room_list[i].no_kamar + " ");
                                        }
                                    }
                                    System.out.print("\nPilih nomor kamar : ");
                                    no_kamar = input.nextInt();
                                    
                                    if(single_room_list[no_kamar - 1].empty_room == false){
                                        do{
                                            System.out.println("\n[!] Nomor Kamar tidak tersedia");
                                            System.out.print("\nPilih nomor kamar : ");
                                            no_kamar = input.nextInt();
                                        }while(single_room_list[no_kamar - 1].empty_room == false);
                                    }
                                    if(single_room_list[no_kamar - 1].empty_room == true){
                                        customer.no_kamar = no_kamar;
                                        customer.set_harga(single_room_list[no_kamar - 1]);
                                        customer.set_jenis_kamar(single_room_list[no_kamar - 1]);
                                        break;
                                    }
                                  break;
                                  
                                } else if (menu == '2') {
                                    clrscr();
                                    System.out.println("=============================================================");
                                    System.out.println("                         DOUBLE ROOM                         ");
                                    System.out.println("-------------------------------------------------------------");
                                    System.out.println("Fasilitas kamar : ");
                                    double_room_list[0].fasilitas_kamar();
                                    System.out.println("\n> Harga per hari : " + double_room_list[0].get_harga());
                                    System.out.println("\nNomor Kamar yang tersedia");
                                    for (int i = 0; i < 20; i++) {
                                        if (double_room_list[i].no_kamar != -1) {
                                            System.out.print(double_room_list[i].no_kamar + " ");
                                        }
                                    }
                                    
                                    System.out.print("\nPilih nomor kamar : ");
                                    no_kamar = input.nextInt();
                                    
                                    if(double_room_list[no_kamar - 21].empty_room == false){
                                        do{
                                            System.out.println("\n[!] Nomor Kamar tidak tersedia");
                                            System.out.print("\nPilih nomor kamar : ");
                                            no_kamar = input.nextInt();
                                        }while(double_room_list[no_kamar - 21].empty_room == false);
                                    }
                                    if(double_room_list[no_kamar - 21].empty_room == true){
                                        customer.no_kamar = no_kamar;
                                        customer.set_harga(double_room_list[no_kamar - 21]);
                                        customer.set_jenis_kamar(double_room_list[no_kamar - 21]);
                                        break;
                                    }
                                    break;

                                } else {
                                    System.out.println("Pilihan tidak tersedia");
                                    System.out.print("\nTekan Enter untuk mengulangi ");
                                    enter.next();
                                }
                            }

                            clrscr();
                            System.out.println("==================================================");
                            System.out.println("              DATA PESANAN PENGINAPAN             ");
                            System.out.println("--------------------------------------------------");
                            System.out.println("Nama Lengkap        : " + customer.nama);
                            System.out.println("NIK                 : " + customer.NIK);
                            System.out.println("No. Telepon         : " + customer.no_telp);
                            System.out.println("Tanggal Check In    : " + String.join("/", customer.tgl_check_in));
                            System.out.println("Tanggal Check Out   : " + String.join("/", customer.tgl_check_out));
                            System.out.println("Jenis Kamar         : " + customer.jenis_kamar);
                            System.out.println("Nomor Kamar         : " + customer.no_kamar);
                            System.out.println("Total Harga         : Rp." + customer.total_harga);

                            do {
                                System.out.print("\nApakah data yang dimasukan sudah benar [y/n]? ");
                                lanjut = input.next().charAt(0);
                            } while (lanjut != 'y' && lanjut != 'n');

                            if (lanjut == 'y') {
                                try {
                                    customer_list.add(customer);
                                    if ("Single Room".equals(customer.jenis_kamar)) {
                                        single_room_list[no_kamar - 1].no_kamar = -1;
                                        single_room_list[no_kamar - 1].empty_room = false;
                                    } else {
                                        double_room_list[no_kamar - 21].no_kamar = -1;
                                        double_room_list[no_kamar - 21].empty_room = false;
                                    }
                                    
                                    System.out.println("\n[!] Data penginapan berhasil ditambahkan");
                                    System.out.println("--------------------------------------------------");
                                    System.out.print("\nTekan Enter untuk kembali ke Menu ");
                                    enter.next();
                                } catch (Exception e) {
                                    System.out.print("\n[!] Data penginapan sudah penuh ");
                                    System.out.print("\nTekan Enter untuk kembali ke Menu ");
                                    enter.next();
                                }
                                break;
                            } else {
                                do {
                                    System.out.print("Apakah anda ingin mengisi ulang data [y/n]? ");
                                    lanjut = input.next().charAt(0);
                                } while (lanjut != 'y' && lanjut != 'n');

                                if (lanjut == 'n') {
                                    is_continue = false;
                                }
                            }
                        } else {
                            System.out.println("--------------------------------------------------------");
                            System.out.print("\nTekan Enter untuk mengisi data ulang ");
                            enter.next();
                        }
                    }
                    break;
                case '2':
                    clrscr();
                    System.out.println("===============================================================");
                    System.out.println("                    Nomor Kamar yang Tersedia                  ");
                    System.out.println("---------------------------------------------------------------");
                    System.out.println("> Single Room");
                    System.out.print("  ");
                    for (int i = 0; i < 20; i++) {
                        if (single_room_list[i].no_kamar != -1) {
                            System.out.print(single_room_list[i].no_kamar + " ");
                        }
                    }
                    System.out.println("\n");
                    System.out.println("> Double Room");
                    System.out.print("  ");
                    for (int i = 0; i < 20; i++) {
                        if (double_room_list[i].no_kamar != -1) {
                            System.out.print(double_room_list[i].no_kamar + " ");
                        }
                    }
                    System.out.println("\n===============================================================");
                    System.out.print("\nTekan Enter untuk kembali ke Menu ");
                    enter.next();
                    break;
                case '3':
                    is_continue = true;
                    while (is_continue) {
                        clrscr();
                        System.out.println("================================");
                        System.out.println("         DATA PENGINAPAN        ");
                        System.out.println("--------------------------------");
                        System.out.println("       Pilihan Jenis Kamar      ");
                        System.out.println("--------------------------------");
                        System.out.println("[1] Single Room                 ");
                        System.out.println("[2] Double Room                 ");
                        System.out.println("[3] Keluar                      ");
                        System.out.println("================================");
                        System.out.print("Pilih Menu [1..3] : ");
                        menu = input.next().charAt(0);

                        clrscr();
                        switch (menu) {
                            case '1':
                                System.out.println("======================================================");
                                System.out.println("                    DATA PENGINAPAN                   ");
                                System.out.println("------------------------------------------------------");
                                System.out.println("                      SINGLE ROOM                     ");
                                System.out.println("------------------------------------------------------");
                                customer_list.display_list_data("Single Room");
                                System.out.print("Tekan Enter untuk kembali ke Menu ");
                                enter.next();
                                break;
                            case '2':
                                System.out.println("======================================================");
                                System.out.println("                    DATA PENGINAPAN                   ");
                                System.out.println("------------------------------------------------------");
                                System.out.println("                      DOUBLE ROOM                     ");
                                System.out.println("------------------------------------------------------");
                                customer_list.display_list_data("Double Room");
                                System.out.print("Tekan Enter untuk kembali ke Menu ");
                                enter.next();
                                break;
                            case '3':
                                is_continue = false;
                                break;
                            default:
                                System.out.println("\nHarap masukkan pilihan dengan benar !\n");
                                System.out.print("Tekan Enter untuk kembali ke Menu ");
                                enter.next();
                        }
                    }
                    break;
                case '4':
                    clrscr();
                    System.out.println("=====================================");
                    System.out.println("         Cari Data Penginapan        ");
                    System.out.println("-------------------------------------");
                    System.out.print("Masukan No. Pesanan : ");
                    int no_cari_pesanan = input.nextInt();

                    if (customer_list.search_data(no_cari_pesanan)) {
                        clrscr();
                        System.out.println("======================================================");
                        System.out.println("                DATA PESANAN PENGINAPAN               ");
                        System.out.println("------------------------------------------------------");
                        customer_list.display_search_data(no_cari_pesanan);
                        System.out.print("\n======================================================");
                        System.out.print("\n\nTekan Enter untuk kembali ke Menu ");
                        enter.next();
                    } else {
                        System.out.println("\n[!] Data penginapan tidak ditemukan");
                        System.out.println("=====================================");
                        System.out.print("\nTekan Enter untuk kembali ke Menu ");
                        enter.next();
                    }
                    break;
                case '5':
                    clrscr();
                    System.out.println("=====================================");
                    System.out.println("         Check Out Penginapan        ");
                    System.out.println("-------------------------------------");
                    System.out.print("Masukan No. Pesanan : ");
                    no_cari_pesanan = input.nextInt();

                    if (customer_list.search_data(no_cari_pesanan)) {
                        clrscr();
                        System.out.println("======================================================");
                        System.out.println("                DATA PESANAN PENGINAPAN               ");
                        System.out.println("------------------------------------------------------");
                        customer_list.display_search_data(no_cari_pesanan);

                        do {
                            System.out.print("\nCheck Out Data Penginapan [y/n]? ");
                            lanjut = input.next().charAt(0);
                        } while (lanjut != 'y' && lanjut != 'n');

                        if (lanjut == 'y') {
                            no_kamar = customer_list.get_no_kamar(no_cari_pesanan);
                            if (no_kamar <= 20) {
                                single_room_list[no_kamar - 1].no_kamar = no_kamar;
                                single_room_list[no_kamar - 1].empty_room = true;
                            } else {
                                double_room_list[no_kamar - 21].no_kamar = no_kamar;
                                double_room_list[no_kamar - 21].empty_room = true;
                            }
                            customer_list.check_out(no_cari_pesanan);
                            System.out.println("\nNomor Pesanan " + no_cari_pesanan + " sudah berhasil Check Out !");
                            System.out.print("======================================================");
                            System.out.print("\n\nTekan Enter untuk kembali ke Menu ");
                            enter.next();
                        }

                    } else {
                        System.out.println("\n[!] Data tidak ditemukan");
                        System.out.println("=====================================");
                        System.out.print("\nTekan Enter untuk kembali ke Menu ");
                        enter.next();
                    }
                    break;
                case '6':
                    clrscr();
                    System.out.println("[!] Keluar dari Menu Program");
                    System.exit(0);
                default:
                    System.out.println("\n[!] Pilihan tidak tersedia di Menu\n");
                    System.out.print("Tekan Enter untuk kembali ke Menu ");
                    enter.next();
            }
        }
    }

    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            System.out.println("\n");
        }
    }
}