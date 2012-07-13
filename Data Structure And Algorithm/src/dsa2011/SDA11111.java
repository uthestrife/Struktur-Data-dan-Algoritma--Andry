/**
 * SDA11111.java
 *
 * Nama berkas kode sumber : SDA11111.java
 * Batas waktu eksekusi program : 3 detik / kasus uji
 * Batas memori program : 256 MiB / kasus uji
 *
 * <b>Format Masukan</b>,
 * Masukan dibaca dari masukan standar. Masukan diakhiri dengan EOF. Masukan
 * dibagi menjadi 3 bagian yang tidak dipisahkan baris kosong atau pun baris
 * yang berisi tulisan, yaitu:
 * Bagian 1:
 *      informasi dari pengguna bernama Nia yang memiliki format sama seperti
 *      Informasi pengguna.
 * Bagian 2:
 *      daftar informasi dari pengguna yang merupakan teman-teman Nia. Jika
 *      Nia belum memiliki teman, maka Bagian 2 tidak ada dalam masukan (bukan
 *      direpresentasikan dengan baris kosong). Setiap informasi tersebut
 *      memiliki format sama seperti Informasi pengguna. Daftar ini memiliki
 *      ukuran sesuai dengan banyak teman Nia pada Bagian 1. Misalkan Nia
 *      memiliki m teman, maka formatnya adalah sebagai berikut.
 *              [Informasi teman Nia ke-1]
 *              [Informasi teman Nia ke-2]
 *               .
 *               .
 *               .
 *              [Informasi teman Nia ke-m]
 * Bagian 3:
 *      daftar informasi dari calon teman yang akan dimasukkan ke dalam
 *      Friend Suggestion. Jika tidak ada calon teman yang akan dimasukkan ke
 *      Friend Suggestion, maka Bagian 3 tidak ada dalam masukan (bukan
 *      direpresentasikan dengan baris kosong). Setiap informasi tersebut
 *      memiliki format sama seperti Informasi calon teman. Dijamin informasi-
 *      informasi pada Bagian 3 terurut menaik berdasarkan <Waktu dimasukkan ke
 *      Friend Suggestion>. Misalkan calon teman yang akan dimasukkan ke Friend
 *      Suggestion sebanyak n, maka formatnya adalah sebagai berikut.
 *             [Informasi calon teman ke-1]
 *             [Informasi calon teman ke-2]
 *              .
 *              .
 *              .
 *             [Informasi calon teman ke-n]
 * Dijamin setiap [Nama pengguna] yang terdapat pada ketiga bagian itu unik
 * (misalnya jika [Nama pengguna] pada Bagian 1 adalah "nia", maka tidak akan
 * ada teman Nia pada Bagian 2 yang bernama "nia"; begitu juga dengan Bagian 3).
 * Dijamin pula unit yang sama merepresentasikan obyek yang sama (misalnya jika
 * unit berupa nama orang adalah "ido" dan muncul beberapa kali pada masukan,
 * antara "ido" yang satu dengan "ido" yang lain merepresentasikan orang yang
 * sama). Dijamin m merupakan sebuah bilangan bulat nonnegatif yang tidak lebih
 * dari 3000. Dijamin n merupakan sebuah bilangan bulat nonnegatif yang tidak
 * lebih dari 3000.
 *
 * <b>Format Keluaran</b>,
 * Keluaran ditulis ke keluaran standar dengan format sebagai berikut.
 *      [Daftar calon teman yang berhasil dirangkum MacLang Filter]
 *      [Daftar calon teman yang tidak dirangkum MacLang Filter]
 * Setiap [Daftar ...] pada keluaran hanya mencantumkan nama dari calon teman
 * yang bersangkutan. Jika [Daftar ...] tidak terdapat nama, maka tampilkan
 * "[KOSONG]". Jika pada sebuah [Daftar ...] terdapat lebih dari satu nama,
 * maka pisahkan dengan menyisipkan sebuah karakter koma di antara tiap nama.
 * Nama-nama dalam [Daftar calon teman yang berhasil dirangkum MacLang Filter]
 * diurutkan berdasarkan urutan perangkumannya. Sedangkan dalam
 * [Daftar calon teman yang tidak dirangkum MacLang Filter], nama diurutkan
 * secara leksikografis naik (ascending).
 *
 * <b>Cara Pemakaian</b>:
 * buka konsol pada sistem operasi, lalu ketik perintah <i>javac
 * SDA11111.java</i> untuk kompilasi sumber kode. jalankan kode
 * binari pada berkas SDA11111.class dengan perintah <i>java SDA11111
 * < in > out</i>. dengan "in" adalah nama file yang dapat diubah
 * sesuai keinginan, begitu juga dengan perintah "out".
 *
 *
 * Version:
 * - 1.0 (25-Maret-2011):
 *	- membuat kode sumber dengan menggunakan metoda pembaca Scanner
 *        sebagai pembaca masukan standar.
 *      - mengubah metoda pembacaan dengan menggunakan BufferedReader
 * 	  untuk pembaca masukan standar (untuk mempercepat waktu eksekusi)
 *      - membuat kelas FaceLangAccount serta kelas FaceLangAccountSuggestable dengan
 *        pertimbangan bahwa ada beberapa data yang akan disimpan yang nantinya
 *        akan diproses.
 *      - membuat kelas MacLangSystem dan FaceLangSystem untuk memodulisasi
 *        kerja dari tiap sub-problem.
 *      - mengaplikasikan orientasi berobjek dalam mendesain kelas dan konten
 *        lainnya.
 * - 1.1 (26-Maret-2011):
 *      - menghapus banyak variabel yang dianggap tidak guna, dilihat dari sudut
 *        pandang hemat memori dan duplikasi variabel.
 *      - menganalisis kemungkinan memori yang akan dipakai.
 *
 *
 * Copyright 2011 Arra Adidaya, 0906563533
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * SDA11111.class,
 *
 * kelas ini mengimplementasikan dan simulasikan algoritma menentukan sugesti
 * informasi dengan mempertimbangkan beberapa prioritas yang telah didefiniskan.
 * dengan pertimabangan memori serta pertimbangan waktu maka haruslah ditemukan
 * algoritma serta struktur data yang tepat untuk memecahkan masalah ini.
 *
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
public class SDA11111 implements InputOutputProccess {
    /**
     * jumlah nilai menit maksimum dalam satu hari dengan mempertimbangkan bahwa
     * satu hari terdiri dari 24 jam yang dimana 1 jam terdiri dari 60 menit.
     */
    protected static final int MAX_MINUTES_A_DAY    = 1440;
    /**
     * jumlah pengguna maksimum dengan menganalisis soal, dimana dipastikan
     * jumalah teman tidak lebih dari 3000 pengguna.
     */
    protected static final int MAX_POSSIBLE_USER    = 3001;
    /**
     * tambahan waktu yang telah didefinisikan. dimana menambahkan 3 menit
     * untuk kasus yang sama.
     */
    protected static final int ADDITIONAL_TIME      = 3;

    /**
     * menyimpan daftar sugesti teman yang akan ditampilkan dengan pertimbangan
     * perbandingan yang nanti didefinisikan di kelas FaceLangAccountSuggestable
     */
    protected static PriorityQueue<FaceLangAccountSuggestable> priorityQueue;
    /**
     * sebuah sistem yang digunakan oleh seorang user, yakni sistem FaceLang.
     * dimana ketika log in, maka orang yang log in saat itu dibuatkan objek
     * FaceLangSystem dan dijadikan bahwa dia sebagai primary user.
     */
    protected static FaceLangSystem faceLangSystem;
    /**
     * sistem MacLang yang akan membantu menyaring informasi dengan pertimbangan
     * yang didefinisikan nantinya.
     */
    protected static MacLangSystem macLangSystem;

    /**
     * sebuah alat bantu yang dapat memisahkan string yang diterima dengan
     * pemisah yang nantinya didefinisikan sesuai kebutuhan nantinya.
     */
    private static StringTokenizer tokenizer;

    /*
     * perintah untuk pembuatan objek dari beberapa variabel yang nantinya akan
     * digunakan dalam proses yang nanti terjadi.
     */
    static {
        priorityQueue   = new PriorityQueue<FaceLangAccountSuggestable>(SDA11111.MAX_POSSIBLE_USER);
        faceLangSystem  = new FaceLangSystem();
    }

    /**
     * menjalankan serta mensimulasikan proses dari MacLangSystem dan
     * FaceLangSystem. yang dapat dipandang menjadi abstraksi tiga tahap.
     * yaitu proses masukan, proses utama serta proses keluaran.
     *
     * @param arguments tidak berpengaruh sama sekali pada saat ini
     * @throws IOException sebuah pengecualian jika terjadi masalah di IO
     */
    public static void main(String[] arguments) throws IOException {
        SDA11111.inputProccess();
        SDA11111.proccessing();
        SDA11111.outputProcess();
    }

    /**
     * input proses dibagi menjadi tiga bagian, dimana bagian pertama ialah
     * informasi pengguna, bagian kedua ialah informasi teman-teman dari
     * pengguna dan bagian ketiga adalah daftar informasi dari calon teman yang
     * akan dimasukkan ke dalam Friend Suggestion.
     * dimana pola pertama ialah pola A
     * dimana pola kedua ialah pola B
     * dimana pola ketiga ialah pola C
     * @throws IOException sebuah pengecualian jika terjadi masalah di IO
     */
    public static void inputProccess() throws IOException {
        SDA11111.readPatternA();
        SDA11111.readPatternB();
        SDA11111.readPatternC();
    }

    /**
     * proses pengeluaran dari hasil proses utama, dimana pada saat ini semua
     * orang yang akan disugesti akan diproses yang sudah dipisahkan antara
     * daftar yang diterima dan daftar yang tidak diterima.
     *
     * @throws IOException sebuah pengecualian jika terjadi masalah di IO
     */
    public static void outputProcess() throws IOException {
        // mencetak daftar yang diterima
        if (faceLangSystem.approvesIsEmpty()) {
            writer.write("[KOSONG]");
            writer.newLine();
        } else {
            for (int i = 0; i < faceLangSystem.approvedSize(); i++) {
                writer.write(faceLangSystem.getApprovedIn(i));
                writer.write((i == faceLangSystem.approvedSize()-1 ? "" : ","));
            }
            writer.newLine();
        }
        // mengurutkan daftar yang tidak diterima
        Collections.sort(faceLangSystem.getListDeletes());
        // mencetak daftar yang tidak diterima
        if (faceLangSystem.deletesIsEmpty()) {
            writer.write("[KOSONG]");
            writer.newLine();
        } else {
            for (int i = 0; i < faceLangSystem.deletedSize(); i++) {
                writer.write(faceLangSystem.getDeletesIn(i));
                writer.write((i == faceLangSystem.deletedSize()-1 ? "" : ","));
            }
            writer.newLine();
        }
        writer.flush();
    }

    /**
     * proses A, proses pembacaan masukan pada masukan standar dengan bantuan
     * kelas pada Java yakni BufferedReader. pada proses ini, proses akan
     * mendapatkan informasi nama pengguna utama, daftar teman yang disukai,
     * daftar teman yang dibenci, daftar tempat favorit, daftar hobi favorit
     * bagi pengguna.
     *
     * @throws IOException sebuah pengecualian jika terjadi masalah di IO
     */
    public static void readPatternA() throws IOException {
        // menyiapkan penampungan sementara untuk informasi pengguna
        HashSet<String> setOfFriend = new HashSet<String>();
        HashSet<String> setOfDislike = new HashSet<String>();
        HashSet<String> setOfPlace = new HashSet<String>();
        HashSet<String> setOfHobby = new HashSet<String>();
        String name = reader.readLine();
        // proses pengambilan informasi daftar orang yang disukai oleh pengguna
        tokenizer = new StringTokenizer(reader.readLine(), ",");
        while (tokenizer.hasMoreTokens()) {
            setOfFriend.add(tokenizer.nextToken());
        }
        // proses pengambilan informasi daftar orang yang dibenci oleh pengguna
        tokenizer = new StringTokenizer(reader.readLine(), ",");
        while (tokenizer.hasMoreTokens()) {
            setOfDislike.add(tokenizer.nextToken());
        }
        // proses pengambilan informasi daftar tempat yang menjadi favorit
        tokenizer = new StringTokenizer(reader.readLine(), ",");
        while (tokenizer.hasMoreTokens()) {
            setOfPlace.add(tokenizer.nextToken());
        }
        // proses pengambilan informasi daftar hobi yang bagi pengguna.
        tokenizer = new StringTokenizer(reader.readLine(), ",");
        while (tokenizer.hasMoreTokens()) {
            setOfHobby.add(tokenizer.nextToken());
        }
        // membuat informasi yang didapatkan tadi sebagai informasi pengguna
        // utama
        faceLangSystem.setPrimaryUser(new FaceLangAccount(name, setOfFriend, setOfDislike, setOfPlace, setOfHobby));
    }
    
    /**
     * proses B, proses pembacaan masukan pada masukan standar dengan bantuan
     * kelas pada Java yakni BufferedReader. pada proses ini, proses akan
     * mendapatkan informasi dari teman-teman yang ditujukan untuk pengguna
     * utama
     *
     * @throws IOException sebuah pengecualian jika terjadi masalah di IO
     */
    public static void readPatternB() throws IOException {
        // alokasi memori yang dibutuhkan dalam proses pembacaan proses kedua
        String name;
        HashSet<String> tempSetOfDislike;
        faceLangSystem.allocatesFriends();
        // iterasi pengambilan informasi yang berguna
        for (int i = 0; i < faceLangSystem.getPrimaryUser().sizeOfFriend(); i++) {
            // membaca nama dan menyimpannya, namun membaca baris berikutnya
            // yang nantinya diabaikan.
            name = reader.readLine();
            reader.readLine();
            // menyiapkan daftar yang dibenci oleh orang yang sedang dituju.
            tempSetOfDislike = new HashSet<String>();
            // proses pengambilan informasi orang yang dibenci dari masukan.
            tokenizer = new StringTokenizer(reader.readLine(), ",");
            while (tokenizer.hasMoreTokens()) {
                tempSetOfDislike.add(tokenizer.nextToken());
            }
            // melewatkan informasi dua baris selanjutanya dengan membaca saja.
            reader.readLine();
            reader.readLine();
            // menambahkan informasi ini sebagai teman baru untuk pengguna utama
            faceLangSystem.addFriend(i, new FaceLangAccount(name, null, tempSetOfDislike, null, null));
        }
    }

    /**
     * proses C, proses pembacaan masukan pada masukan standar dengan bantuan
     * kelas pada Java yakni BufferedReader. pada proses ini, proses akan
     * mendapatkan informasi dari teman-teman yang ditujukan untuk pengguna
     * utama daftar informasi dari calon teman yang akan dimasukkan ke dalam
     * Friend Suggestion
     *
     * @throws IOException sebuah pengecualian jika terjadi masalah di IO
     */
    public static void readPatternC() throws IOException {
        // alokasi memori yang dibutuhkan dalam proses pembacaan proses ketigas
        faceLangSystem.allocatesSuggestions(SDA11111.MAX_MINUTES_A_DAY);
        String timeInputed;
        String name;
        int mutualFriendsCounter = 0;
        int mutualPlacesCounter = 0;
        int mutualHobbiesCounter = 0;
        int arrivalTimes;
        // membaca dengan patokan dimana tidak ada input lagi.
        while ((timeInputed = reader.readLine()) != null) {
            // set ulang penghitung mutual untuk semua informasi yang dibutuhkan
            mutualFriendsCounter = 0;
            mutualPlacesCounter = 0;
            mutualHobbiesCounter = 0;
            // menerima informasi waktu tiba pada teman yang dituju
            tokenizer = new StringTokenizer(timeInputed, ":");
            arrivalTimes = (Integer.parseInt(tokenizer.nextToken()) * 60) + Integer.parseInt(tokenizer.nextToken());
            name = reader.readLine();
            // menghitung jumlah mutual friend dengan ditujukan pada pengguna
            // utama
            tokenizer = new StringTokenizer(reader.readLine(), ",");
            while (tokenizer.hasMoreTokens()) {
                if (faceLangSystem.getPrimaryUser().friendWith(tokenizer.nextToken())) {
                    mutualFriendsCounter++;
                }
            }
            // lewatkan informasi teman yang tidak disukai
            reader.readLine();
            // menghitung jumlah mutual tempat dengan ditujukan pada pengguna
            // utama
            tokenizer = new StringTokenizer(reader.readLine(), ",");
            while (tokenizer.hasMoreTokens()) {
                if (faceLangSystem.getPrimaryUser().everVisits(tokenizer.nextToken())) {
                    mutualPlacesCounter++;
                }
            }
            // menghitung jumlah mutual hobi dengan ditujukan pada pengguna
            // utama
            tokenizer = new StringTokenizer(reader.readLine(), ",");
            while (tokenizer.hasMoreTokens()) {
                if (faceLangSystem.getPrimaryUser().like(tokenizer.nextToken())) {
                    mutualHobbiesCounter++;
                }
            }
            // menambah sugesti teman yang nantinya akan dipertimbangkan
            faceLangSystem.addSuggestion(arrivalTimes, new FaceLangAccountSuggestable(
                    name, arrivalTimes, mutualFriendsCounter,
                    mutualPlacesCounter, mutualHobbiesCounter));
        }
    }

    /**
     * melakukan proses pembentukan sugesti dengan pertimbangan yang sudah di
     * definisikan. 
     */
    public static void proccessing() {
        // menyiapkan MacLang untuk filter informasi
        macLangSystem = new MacLangSystem(faceLangSystem);
        // alokasi memori yang dibutuhkan untuk proses pembentukan sugesti nanti
        FaceLangAccountSuggestable suggested;
        FaceLangAccountSuggestable[] suggestedList = faceLangSystem.getListSuggestions();
        int currentTime = -99;

        // membentuk sugesti
        for (int minute = 0; minute < MAX_MINUTES_A_DAY; minute++) {
            if (suggestedList[minute] != null) {
                priorityQueue.add(suggestedList[minute]);
            }
            if (!priorityQueue.isEmpty() && currentTime <= minute && minute + ADDITIONAL_TIME <= MAX_MINUTES_A_DAY) {
                // mengeluarkan calon sugesti
                suggested = priorityQueue.poll();
                // filter nama dari calon pengguna yang akan disugesti
                if (macLangSystem.macLangFilter(suggested.getName())) {
                    // jika berhasil maka akan dimasukan ke dalam daftar terima
                    // dan menambahkan waktunya.
                    faceLangSystem.addApproved(suggested.getName());
                    currentTime = minute + ADDITIONAL_TIME;
                } else {
                    // jika tidak maka akan dimasukan ke dalam daftar tolak
                    faceLangSystem.addDeleted(suggested.getName());
                }
            }
        }
        // sisanya akan dimasukan kedalam daftar tolak
        while (!priorityQueue.isEmpty()) {
            faceLangSystem.addDeleted(priorityQueue.poll().getName());
        }
    }
}

/**
 * MacLangSystem.class,
 *
 * kelas ini sebagai sistem MacLang, bahwa pada kelas ini akan menargetkan
 * pengguna serta sistem yang akan difilter. pada kelas ini ada method yang
 * akan membantu filter bilamana pengguna yang diberikan diterima atau tidak
 * sebagai sugesti pengguna yang ditargetkan.
 *
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
class MacLangSystem {
    /**
     * sistem FaceLang yang akan dituju oleh sistem MacLang ini, yang nantinya
     * digunakan sebagai proses filter.
     */
    private FaceLangSystem system;
    /**
     * pengguna utama yang akan ditargetkan sebagai patokan utama dalam
     * penentuan kondisi filter.
     */
    private FaceLangAccount targetedUser;

    /**
     * method konstraktor yang akan menyiapkan objek dari FaceLangSystem dan
     * akan secara tidak langsung menyimpan referensi dari pengguna utama
     *
     * @param system sistem FaceLangSystem
     */
    public MacLangSystem(FaceLangSystem system) {
        this.system = system;
        this.targetedUser = system.getPrimaryUser();
    }

    /**
     * method yang akan membantu filter apakah seseorang pengguna dapat
     * disugestikan ke target pengguna yang diinisialisasi awal pada pembuatan
     * objek MacLangSystem
     *
     * @param name nama calon sugesti.
     * @return kondisi apakah dapat disugesti apa tidak.
     */
    public boolean macLangFilter(String name) {
        FaceLangAccount[] usersList = system.getListFriends();
        int numberHates = 0;
        for (FaceLangAccount user : usersList) {
            if (user.hates(name)) {
                ++numberHates;
            }
            if (targetedUser.sizeOfFriend() < numberHates * 2) {
                return false;
            }
        }
        return true;
    }
}

/**
 * FaceLangSystem.class,
 *
 * kelas ini sebagai sistem FaceLang, bahwa pada kelas ini akan menyimpan
 * informasi dari sudut pandang satu pengguna, yaitu pengguna utama. pada kelas
 * ini akan disimpan beberapa daftar yang dibutuhkan. yaitu :
 *      - daftar teman
 *      - daftar calon sugesti
 *      - daftar sugesti yang diterima
 *      - daftar sugesti yang ditolak
 * kelas ini bekerja sebagai alokasi memori secara bagian besar.
 *
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
class FaceLangSystem {
    /**
     * daftar teman yang dimiliki oleh pengguna, disimpan pada array (memori
     * statis)dengan asumsi maksimum teman dapat ditentukan.
     */
    private FaceLangAccount[] listFriends;
    /**
     * daftar calon sugesti teman yang akan dimiliki oleh pengguna, disimpan
     * pada array (memori statis) karena diasumsikan sugesti dibatasi oleh
     * menit pada satu hari.
     */
    private FaceLangAccountSuggestable[] listSuggestions;
    /**
     * daftar sugesti yang diterima, menggunakan memori dinamis dikarenakan
     * jumlahnya yang bersifat juga dinamis. tergantung banyak faktor perbanding
     * an.
     */
    private ArrayList<String> listApproves;
    /**
     * daftar sugesti yang ditolak, menggunakan memori dinamis dikarenakan
     * jumlahnya yang bersifat juga dinamis. tergantung banyak faktor perbanding
     * an.
     */
    private ArrayList<String> listDeletes;
    /**
     * pengguna utama yang ditujukan nantinya sebagai indikator utama dalam
     * perbandingan di proses.
     */
    private FaceLangAccount primaryUser;

    /**
     * method konstraktor yang akan membantu menyiapkan memori serta referensi
     * ke objek daftar yang akan diterima sugestinya dan yang ditolak.
     */
    public FaceLangSystem() {
        this.listApproves = new ArrayList<String>();
        this.listDeletes = new ArrayList<String>();
    }

    /**
     * method yang membantu memberikan informasi ukuran dari daftar yang
     * diterima
     * @return ukuran dari daftar yang diterima
     */
    public int approvedSize() {
        return this.listApproves.size();
    }

    /**
     * method yang membantu memberikan informasi ukuran dari daftar yang
     * ditolak
     * @return ukuran dari daftar yang ditolak
     */
    public int deletedSize() {
        return this.listDeletes.size();
    }

    /**
     * method yang membantu memberikan akses dari pengguna yang terdaftar di
     * dalam daftar diterima dengan menunjukan indeks yang diminta.
     * @return pengguna yang terdaftar di dalam daftar diterima dengan
     *         menunjukan indeks yang diminta
     */
    public String getApprovedIn(int selected) {
        return this.listApproves.get(selected);
    }

    /**
     * method yang membantu memberikan akses dari pengguna yang terdaftar di
     * dalam daftar ditolak dengan menunjukan indeks yang diminta.
     * @return pengguna yang terdaftar di dalam daftar ditolak dengan
     *         menunjukan indeks yang diminta
     */
    public String getDeletesIn(int selected) {
        return this.listDeletes.get(selected);
    }

    /**
     * memberikan informasi apabila daftar yang diterima apakah kosong
     * atau tidak
     * @return informasi apabila daftar yang diterima apakah kosong
     *         atau tidak
     */
    public boolean approvesIsEmpty() {
        return this.listApproves.isEmpty();
    }
    
    /**
     * memberikan informasi apabila daftar yang ditolak apakah kosong
     * atau tidak
     * @return informasi apabila daftar yang ditolak apakah kosong
     *         atau tidak
     */
    public boolean deletesIsEmpty() {
        return this.listDeletes.isEmpty();
    }

    /**
     * memberikan akses ke pengguna utama yang disimpan pada sistem FaceLang
     *
     * @return akses ke pengguna utama yang disimpan pada sistem FaceLang
     */
    public FaceLangAccount getPrimaryUser() {
        return primaryUser;
    }

    /**
     * memberikan akses ke daftar yang diterima
     * @return akses ke daftar yang diterima
     */
    public ArrayList<String> getListApproves() {
        return listApproves;
    }

    /**
     * memberikan akses ke daftar yang ditolak
     * @return akses ke daftar yang ditolak
     */
    public ArrayList<String> getListDeletes() {
        return listDeletes;
    }

    /**
     * memberikan akses ke daftar yang calon yang disugesti
     * @return akses ke daftar yang calon yang disugesti
     */
    public FaceLangAccountSuggestable[] getListSuggestions() {
        return listSuggestions;
    }

    /**
     * memberikan akses ke daftar teman
     * @return akses ke daftar teman
     */
    public FaceLangAccount[] getListFriends() {
        return listFriends;
    }

    /**
     * set pengguna utama
     * @param primaryUser pengguna utama
     */
    public void setPrimaryUser(FaceLangAccount primaryUser) {
        this.primaryUser = primaryUser;
    }

    /**
     * menambahkan teman ke daftar dengan sesuai indeks yang diberikan
     * @param i indeks yang diberikan
     * @param friend teman baru
     */
    public void addFriend(int i, FaceLangAccount friend) {
        this.listFriends[i] = friend;
    }

    /**
     * menambahkan sugesti ke daftar dengan sesuai indeks yang diberikan
     * @param i indeks yang diberikan
     * @param sugesti teman baru
     */
    public void addSuggestion(int i, FaceLangAccountSuggestable suggested) {
        this.listSuggestions[i] = suggested;
    }

    /**
     * menambahkan pengguna yang diterima untuk disugesti
     * @param approved pengguna yang diterima untuk disugesti
     */
    public void addApproved(String approved) {
        this.listApproves.add(approved);
    }

    /**
     * menambahkan pengguna yang ditolak untuk disugesti
     * @param approved pengguna yang ditolak untuk disugesti
     */
    public void addDeleted(String deleted) {
        this.listDeletes.add(deleted);
    }

    /**
     * mengalokasikan memori untuk jumlah teman yang akan ditampung
     * @param size jumlah teman
     */
    public void allocatesFriends(int size) {
        this.listFriends = new FaceLangAccount[size];
    }

    /**
     * mengalokasikan memori untuk jumlah teman yang akan ditampung
     * dengan ukuran dari teman pengguna
     */
    public void allocatesFriends() {
        this.listFriends = new FaceLangAccount[this.primaryUser.sizeOfFriend()];
    }

    /**
     * mengalokasikan memori untuk jumlah sugesti yang akan ditampung
     * @param size jumlah sugesti
     */
    public void allocatesSuggestions(int size) {
        this.listSuggestions = new FaceLangAccountSuggestable[size];
    }
}

/**
 * FaceLangAccount Template
 * Kelas ini memiliki 5 atribut yakni : - nama, - daftar teman, - daftar
 * orang yang tidak disukai, - daftar tempat wisata yang pernah dikunjungi,
 * dan - daftar hobi.
 *
 * Tiap daftar hanya menyimpan nama saja berkaitan dengan kategori yang
 * ditentukan. Misalnya daftar teman hanya akan menyimpan nama dari teman.
 *
 * Daftar yang dibangun harus dapat melakukan operasi pencarian suatu nama
 * dengan cepat, dan operasi pengaksesan semua anggota dalam daftar dengan
 * tingkat kecepatan yang cukup cepat.
 * Version:
 *      - 1.0 (9-Maret-2011) :
 *          - membangun kelas FaceLangAccount
 *      - 1.0.1.1 (10-Maret-201) :
 *          - mengganti struktur data pada daftar dari ArrayList menjadi HashMap.
 *      - 1.0.1.2 (11-03-2011) :
 *          - mengganti struktur data pada daftar dari HashMap menjadi HashSet.
 *       - 1.0.2.1 (12-03-2011):
 *          - menambahkan dokumentasi.
 *
 * Copyright 2011 Denvil Prasetya.
 */
class FaceLangAccount {

    /** Nama pengguna. */
    private String name;
    /** Kumpulan nama pengguna lain yang merupakan teman dari pengguna. */
    protected HashSet<String> setOfFriends;
    /** Kumpulan nama pengguna lain yang tidak disukai oleh pengguna. */
    protected HashSet<String> setOfDislikes;
    /** Kumpulan nama tempat wisata yang pernah dikunjungi oleh pengguna. */
    protected HashSet<String> setOfPlaces;
    /** Kumpulan nama hobi yang disukai oleh pengguna. */
    protected HashSet<String> setOfHobbies;

    /**
     * Membangun objek <code>FaceLangAccount</code> dengan nama sesuai parameter
     * yang diberikan. Kumpulan nama teman, nama pengguna yang tidak disukai,
     * nama tempat wisata yang pernah dikunjungi, dan nama hobi yang disukai
     * pengguna merupakan kumpulan kosong.
     *
     * @param name nama pengguna ini.
     */
    public FaceLangAccount(String name) {
        this.name = name;
        this.setOfFriends = new HashSet<String>();
        this.setOfDislikes = new HashSet<String>();
        this.setOfPlaces = new HashSet<String>();
        this.setOfHobbies = new HashSet<String>();
    }

    /**
     * Membangun objek <code>FaceLangAccount</code> sesuai dengan parameter yang
     * diberikan.
     *
     * @param name nama pengguna ini.
     * @param friends kumpulan nama teman dari pengguna ini.
     * @param dislikes kumpulan nama pengguna lain yang tidak disukai pengguna ini.
     * @param places kumpulan nama tempat wisata yang pernah dikunjungi oleh
     *               pengguna ini.
     * @param hobbies kumpulan nama hobi yang disukai oleh pengguna ini.
     */
    public FaceLangAccount(String name, HashSet<String> friends,
            HashSet<String> dislikes, HashSet<String> places,
            HashSet<String> hobbies) {
        this.name = name;
        this.setOfFriends = friends;
        this.setOfDislikes = dislikes;
        this.setOfPlaces = places;
        this.setOfHobbies = hobbies;
    }

    /**
     * Mengembalikan nilai <i>hash code</i> untuk pengguna ini.
     *
     * @return Nilai <i>hash code</i> untuk pengguna ini.
     */
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Mengembalikan nilai kebenaran yang menyatakan bahwa apakah pengguna ini
     * sama dengan objek yang diberikan pada bagian parameter input.
     *
     * @param otherObject objek lain yang akan dibandingkan dengan pengguna ini.
     * @return TRUE jika pengguna ini sama dengan objek yang diberikan, FALSE
     *         untzuk kondisi lainnya.
     */
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof FaceLangAccount)) {
            return false;
        }

        // mengubah otherObject yang bertipe Object menjadi objek dengan tipe
        // FaceLangAccount.
        FaceLangAccount otherUser = (FaceLangAccount) otherObject;
        return this.name.equals(otherUser);
    }

    /**
     * Mengembalikan nama dari pengguna ini.
     *
     * @return nama dari pengguna ini.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Mengembalikan nilai kebenaran yang menyatakan bahwa apakah pengguna ini
     * tidak menyukai pengguna lain yang memiliki nama sesuai dengan parameter
     * input.
     *
     * @param userName nama dari pengguna lain.
     * @return TRUE jika nama pada input masukan merupakan nama dari pengguna
     *         lain yang tidak disukai pengguna ini, FALSE untuk kondisi
     *         lainnya.
     */
    public boolean hates(String userName) {
        return setOfDislikes.contains(userName);
    }

    /**
     * Mengembalikan nilai kebenaran yang menyatakan bahwa apakah pengguna ini
     * berteman dengan pengguna lain yang memiliki nama sesuai dengan parameter
     * input.
     *
     * @param userName nama dari pengguna lain.
     * @return TRUE jika nama pada input masukan merupakan nama salah satu teman
     *         dari pengguna ini, FALSE untuk kondisi lainnya.
     */
    public boolean friendWith(String userName) {
        return setOfFriends.contains(userName);
    }

    /**
     * Mengembalikan nilai kebenaran yang menyatakan bahwa apakah pengguna ini
     * pernah mengunjungi nama tempat wisata sesuai dengan parameter input.
     *
     * @param placeName nama dari tempat wisata.
     * @return TRUE jika nama tempat wisata pada input masukan merupakan salah
     *         satu tempat wisata yang pernah dikunjungi oleh pengguna ini,
     *         FALSE untuk kondisi lainnya.
     */
    public boolean everVisits(String placeName) {
        return setOfPlaces.contains(placeName);
    }

    /**
     * Mengembalikan nilai kebenaran yang menyatakan bahwa apakah pengguna ini
     * menyukai nama hobi yang sesuai dengan parameter input.
     *
     * @param hobbyName nama dari hobi.
     * @return TRUE jika nama pada input masukan merupakan salah satu hobi yang
     *         disukai oleh pengguna ini, FALSE untuk kondisi lainnya.
     */
    public boolean like(String hobbyName) {
        return setOfHobbies.contains(hobbyName);
    }

    /**
     * Mengembalikan banyaknya teman yang dimiliki oleh pengguna ini.
     *
     * @return ukuran atau banyaknya teman yang dimiliki pengguna ini.
     */
    public int sizeOfFriend() {
        return setOfFriends.size();
    }

    /**
     * Mengembalikan objek <code>Iterator</code> yang berkaitan dengan nama
     * teman-teman dari pengguna ini.
     *
     * @return objek <code>Iterator</code> yang berkaitan dengan nama
     *         teman-teman dari pengguna ini.
     */
    public Iterator<String> iteratorForFriend() {
        return setOfFriends.iterator();
    }

    /**
     * Mengembalikan objek <code>Iterator</code> yang berkaitan dengan nama
     * tempat wisata yang pernah dikunjungi oleh pengguna ini.
     *
     * @return objek <code>Iterator</code> yang berkaitan dengan nama tempat
     *         wisata yang pernah dikunjungi oleh pengguna ini.
     */
    public Iterator<String> iteratorForPlace() {
        return setOfPlaces.iterator();
    }

    /**
     * Mengembalikan objek <code>Iterator</code> yang berkaitan dengan nama hobi
     * yang disukai oleh pengguna ini.
     *
     * @return objek <code>Iterator</code> yang berkaitan dengan nama hobi yang
     *         disukai oleh pengguna ini.
     */
    public Iterator<String> iteratorForHobby() {
        return setOfHobbies.iterator();
    }

    /**
     * Mengembalikan deskripsi dari pengguna ini.
     *
     * @return deskripsi mengenai pengguna ini.
     */
    public String toString() {
        return this.name + "\n" + "friend: " + this.setOfFriends + "\n"
                + "dislike: " + this.setOfDislikes + "\n" + "place: "
                + this.setOfPlaces + "\n" + "hobby: " + this.setOfHobbies + "\n";
    }
}

/**
 * FaceLangAccountSuggestable.class,
 *
 * kelas ini bekerja sebagai penampung informasi yang bersifat instansi
 * (informasi pada satu objek). kelas ini merupakan penambahan informasi dari
 * kelas FaceLangAccount, pada kelas ini dapat ditampung nilai mutual ke pengguna
 * yang nantinya dituju pada proses luaran. dan pada kelas ini dapat
 * dibandingkan dengan kelas FaceLangAccountSuggestable lainnya. dengan
 * implementasi kelas Comparable. secara langsung kelas ini dapat dipakai secara
 * langsung di kelas Collections yang menggunakan compareTo sebagai pembanding,
 * seperti PriorityQueue
 *
 * @see PriorityQueue
 * @see FaceLangAccount
 * @see Comparable
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
class FaceLangAccountSuggestable extends FaceLangAccount implements Comparable<FaceLangAccountSuggestable> {
    /**
     * informasi waktu yang didapatkan dari masukan. dimana waktu ini menyatakan
     * waktu datang ketika sedang mau diproses.
     */
    private int arriveTime;
    /**
     * jumlah teman yang mutual terhadap pengguna yang nantinya ditujukan.
     */
    private int numberOfMutualFriends;
    /**
     * jumlah tempat yang mutual terhadap pengguna yang nantinya ditujukan.
     */
    private int numberOfMutualPlaces;
    /**
     * jumlah hobi yang mutual terhadap pengguna yang nantinya ditujukan.
     */
    private int numberOfMutualHobby;

    /**
     * objek yang akan menyediakan nilai bagi kelas ini dengan nilai yang
     * dibutuhkan
     * @param name nama pengguna
     * @param arriveTime waktu ketika mau diproses
     * @param numberOfMutualFriends jumlah teman yang mutual
     * @param numberOfMutualPlaces jumlah tempat yang mutual
     * @param numberOfMutualHobby jumalah hobi yang mutual
     */
    public FaceLangAccountSuggestable(String name, int arriveTime, int numberOfMutualFriends, int numberOfMutualPlaces, int numberOfMutualHobby) {
        super(name);
        this.arriveTime = arriveTime;
        this.numberOfMutualFriends = numberOfMutualFriends;
        this.numberOfMutualPlaces = numberOfMutualPlaces;
        this.numberOfMutualHobby = numberOfMutualHobby;
    }

    /**
     * membandingkan sugesti ini dengan sugesti lainnya. dengan pertimbangan
     * pertama ialah jumlah teman yang mutual lalu jumlah tempat yang mutual
     * lalu jumlah hobi yang mutual lalu waktu ketika ingin diproses
     *
     * @param otherUser pengguna lainnya
     * @return kondisi perbandingan, dimana negatif menandakan kurang dari
     *         positif menandakan lebih dari dan nol menandakan sama dengan
     */
    public int compareTo(FaceLangAccountSuggestable otherUser) {
        // membandingkan jumlah teman yang mutual
        int comparedValue = otherUser.numberOfMutualFriends - this.numberOfMutualFriends;
        if (comparedValue == Comparator.SAME) {
            // membandingkan jumlah tempat yang mutual
            comparedValue = otherUser.numberOfMutualPlaces - this.numberOfMutualPlaces;
            if (comparedValue == Comparator.SAME) {
                // membandingkan jumlah hobi yang mutual
                comparedValue = otherUser.numberOfMutualHobby - this.numberOfMutualHobby;
                if (comparedValue == Comparator.SAME) {
                    // membandingkan waktu
                    comparedValue = this.arriveTime - otherUser.arriveTime;
                }
            }
        }
        // hasil perbandingan
        return comparedValue;
    }

    /**
     * mengakses waktu
     * @return waktu
     */
    public int getArriveTime() {
        return arriveTime;
    }

    /**
     * mengakses jumlah teman yang mutual
     * @return jumlah teman yang mutual
     */
    public int getNumberOfMutualFriends() {
        return numberOfMutualFriends;
    }

    /**
     * mengakses jumlah hobi yang mutual
     * @return jumlah hobi yang mutual
     */
    public int getNumberOfMutualHobby() {
        return numberOfMutualHobby;
    }

    /**
     * mengakses jumlah tempat yang mutual
     * @return jumlah tempat yang mutual
     */
    public int getNumberOfMutualPlaces() {
        return numberOfMutualPlaces;
    }
}

/**
 * InputOutputProcess.class,
 *
 * interface ini akan menyediakan pembaca serta penulis pada masukan dan
 * keluaran standar. akan teraplikasikan ke kelas yang mengimplemenatasikan
 * interface ini.
 *
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
interface InputOutputProccess {

    /**
     * objek yang mempunyai tujuan akan membaca masukan dari masukan standar,
     * dengan menampung (buffer) yang secara pembiayaan waktu sangatlah jauh
     * lebih efisien dibandingkan dengan kelas Scanner.
     */
    public final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
    /**
     * objek yang mempunyai tujuan akan menulis keluaran ke keluaran standar,
     * dengan menampung (buffer) yang secara pembiayaan waktu sangatlah jauh
     * lebih efisien dibandingkan dengan kelas metoda print yang ada pada kelas
     * PrintStream.
     */
    public final BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(System.out));
}

/**
 * StringComparator.class,
 * hanya untuk memberi bentuk yang indah pada pembagian kerja pada sub-problem
 * kelas ini menyimpan nilai standar yang menyatakan bahwa perbandingan pada
 * compareTo akan 0 jika sama.
 * 
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
class Comparator {
    public static int SAME = 0;
}
