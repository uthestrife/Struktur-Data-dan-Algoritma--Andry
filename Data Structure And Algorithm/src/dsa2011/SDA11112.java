
/**
 * berkas ini asli dari saya (Arra Adidaya) dan sengaja dibuat dengan niat
 * tugas 2 SDA yang telah diberikan pada mata perkuliahan SDA itu sendiri.
 *
 * Version:
 * - 1.0 (1-Juni-2011):
 *	- membuat kelas Cluster
 *      - membuat kelas FaceLangUser
 *      - membuat HardCode dari tugas 2 dan mencobanya langsung di kelas
 *        SDA11112 itu sendiri dan di metoda main
 *      - mengubah penggunaan Scanner dan System.out.print menjadi
 *        masukan dan keluaran yang berbasiskan buffering
 * - 1.1 (2-Juni-2011):
 *      - menjadikan satu kelas tersendiri dari proses di metoda main
 *        dalam kelas FaceLangSequel.
 *      - mendokumentasikan secara lengkap semua kelas dan metoda
 * Copyright 2011 Arra Adidaya, 0906563533
 */
import java.util.HashSet;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * kelas ini akan memperlihatkan simulasi dari penggunaan FaceLangSequel. dan
 * yang akan berkomunikasi secara langsung dengan masukan dari masukan standar
 * dan secara tidak langsung akan memberikan keluaran dari masukan yang
 * diberikan. namun proses akan ada di kelas FaceLangProcess (konsep information
 * hiding dari Object Oriented-Programming).
 * @author Arra Adidaya
 * @version 1.0 (2-Juni-2011)
 */
public class SDA11112 implements OutputInputProcess {

    /**
     * objek FaceLangSequel yang bekerja sebagai sistem utama dari program ini
     * yang mengurusi input dan output serta proses
     */
    protected static FaceLangSequel faceLangSequel;

    /**
     * metoda utama yang akan mensimulasikan penggunaan dari kelas
     * FaceLangSequel
     * @param args argumen
     * @throws IOException intrupsi dan gangguan dari Input Output
     */
    public static void main(String[] args) throws IOException {
        /*
         * mempersiapkan sistem
         */
        faceLangSequel = new FaceLangSequel();
        String inputLine;
        do {
            /*
             * membaca masukan dari pengguna
             */
            inputLine = reader.readLine();
            if (inputLine == null) {
                /*
                 * jika tidak ada maka proses simulasi selesai
                 */
                break;
            }
            /*
             * membuat pola dari masukan agar dapat diproses oleh sistem
             * FaceLangSequel
             */
            String[] inputPattern = inputLine.split(" ");
            /*
             * membaca pola pada karakter pertama pada pola pertama pada
             * masukan
             */
            char c = inputPattern[0].charAt(0);
            /*
             * menyimpan pola masukan
             */
            faceLangSequel.setCurrentInputPattern(inputPattern);
            if (c == 'm') {
                /*
                 * jika yang diinginkan ialah mutual
                 */
                faceLangSequel.mutual();
            } else if (c == 'c') {
                /*
                 * jika yang diinginkan ialah cetak
                 */
                faceLangSequel.print();
            } else if (c == 't') {
                /*
                 * jika yang diinginkan adalah teman
                 */
                faceLangSequel.friends();
            }
        } while (true);
        /*
         * selesai simulasi dan selesai semua proses input output
         */
        writer.flush();
        writer.close();
        reader.close();
    }
}

/**
 * sebagai sistem FaceLangSequel, yang akan siap menerima masukan dan memberikan
 * keluaran yang sebelumnya melakukan proses akan masukan yang diberikan. dan
 * dapat dikatakan kelas ini bekerja sebagai Input Output Proccess. dimana
 * hampir semua dominasi kerjaan dilakukan disini.
 * @author Arra Adidaya
 * @version 1.0 (2-Juni-2011)
 */
class FaceLangSequel implements OutputInputProcess {

    /**
     * koleksi kluster-kluster yang ada
     */
    private ArrayList<Cluster> clusters;
    /**
     * daftar/peta dari pengguna yang telah ada, memudahkan pengecekan
     * keberadaan dari pengguna FaceLang.
     */
    private HashMap<String, FaceLangUser> faceLangUserMap;
    /**
     * pola masukan terkini
     */
    private String[] currentInputPattern;
    /**
     * penghitung
     */
    private int counter;

    /**
     * konstraktor yang digunakan sebagai inisialisasi nilai penghitung dan
     * pembuatan objek dari koleksi kluster. dan pemetaan pengguna FaceLang.
     */
    public FaceLangSequel() {
        this.counter = 0;
        this.clusters = new ArrayList<Cluster>();
        this.faceLangUserMap = new HashMap<String, FaceLangUser>(10000);
    }

    /**
     * mencetak nama-nama yang saling mutual.
     * @throws IOException intrupsi dan gangguan dari Input Output
     */
    public void mutual() throws IOException {
        /*
         * mengambil kedua pengguna dalam daftar
         */
        FaceLangUser userA = faceLangUserMap.get(currentInputPattern[1]);
        FaceLangUser userB = faceLangUserMap.get(currentInputPattern[2]);

        /*
         * mempunyai beda indeks key pada kluster
         */
        boolean isNotSameIndexKey = userA.clustered.indexKey != userB.clustered.indexKey;
        /*
         * ada yang null diantara kedua pengguna yang diminta
         */
        boolean isNull = userA == null || userB == null;
        if (!isNull && !isNotSameIndexKey) {
            /*
             * tidak memenuhi kedua syarat tersebut
             */

            /*
             * mendaftarkan semua nama yang saling mutual diantara pengguna
             * pertama dan pengguna kedua
             */
            ArrayList<String> names = new ArrayList<String>();
            for (FaceLangUser selectedUser : userA.mutualSet) {
                if (userB.isMutual(selectedUser)) {
                    names.add(selectedUser.name);
                }
            }
            /*
             * mencetaknya dan dengan sebelumnya diurutkan nama-nama yang
             * terdaftar.
             */
            if (!names.isEmpty()) {
                Collections.sort(names);
                String result = names.toString();
                writer.write(result.substring(1, result.length() - 1).replace(" ", ""));
            }

        }
        writer.newLine();
    }

    /**
     * perintah teman pada masukan pola 1, membuat pertemanan tergantung akan
     * keadaan dari kedua pengguna yang diminta. 
     */
    public void friends() {
        /*
         * mengambil pengguna yang diminta pada parameter 1
         */
        FaceLangUser userA = faceLangUserMap.get(currentInputPattern[1]);
        /*
         * mengambil pengguna yang diminta pada parameter 2
         */
        FaceLangUser userB = faceLangUserMap.get(currentInputPattern[2]);

        if (userA == null) {
            /*
             * pengguna pertama tidak ada
             */
            if (userB == null) {
                /*
                 * pnegguna kedua tidak ada
                 */

                /*
                 * membuat kluster baru yang diisi oleh pengguna pertama dan
                 * pengguna kedua. lalu kedua pengguna dibuat hubungan
                 * mutualisme diantara keduanya. dan mendaftarkan kluster baru
                 * ke koleksi kluster.
                 */
                Cluster cluster = new Cluster(counter++);
                userA = new FaceLangUser(currentInputPattern[1], cluster);
                userB = new FaceLangUser(currentInputPattern[2], cluster);
                userA.mutualism(userB);
                cluster.add(userA, userB);
                /*
                 * mendaftarkan pengguna pertama dan pengguna kedua
                 */
                faceLangUserMap.put(currentInputPattern[1], userA);
                faceLangUserMap.put(currentInputPattern[2], userB);
                clusters.add(cluster);
            } else {
                /*
                 * pengguna kedua ada
                 */

                /*
                 * membuat pengguna pertama dan membuat mutualisme dengan
                 * pengguna kedua. dan memasukan pengguna pertama ke kluster
                 * yang sama dengan kluster pengguna kedua.
                 */
                userA = new FaceLangUser(currentInputPattern[1], userB.inCluster());
                userA.mutualism(userB);
                userB.addInCluster(userA);
                /*
                 * mendaftarkan pengguna pertama
                 */
                faceLangUserMap.put(currentInputPattern[1], userA);
            }
        } else {
            /*
             * pengguna pertama ada
             */
            if (userB == null) {
                /*
                 * pengguna kedua tidak ada
                 */

                /*
                 * membuat kedua dan membuat mutualisme dengan pengguna pertama
                 * dan membuat pengguna kedua termasuk dalam kluster yang sama
                 * dengan pengguna pertama.
                 */
                userB = new FaceLangUser(currentInputPattern[2], userA.inCluster());
                userB.mutualism(userA);
                userA.addInCluster(userB);
                /*
                 * mendaftarkan pengguna kedua
                 */
                faceLangUserMap.put(currentInputPattern[2], userB);
            } else {
                /*
                 * pengguna kedua ada
                 */
                int indexA = userA.inCluster().indexKey;
                int indexB = userB.inCluster().indexKey;
                /*
                 * membuat mutualisme diantara keduanya
                 */
                userA.mutualism(userB);
                if (indexA != indexB) {
                    /*
                     * jika indeks kluster mereka berdua berbeda
                     */
                    if (indexA < indexB) {
                        /*
                         * jika indeks kluster yang ditempati oleh pengguna
                         * pertama lebih kecil dari pengguna kedua.
                         */

                        /*
                         * mendaftarkan semua anggota yang berada dalam kluster
                         * yang sama dengan pengguna kedua ke kluster yang
                         * ditempati pengguna pertama dan mereferensi ulang
                         * bahwa pengguna yang ada pada kluster yang sama dengan
                         * pengguna kedua mempunyai kluster baru yakni kluster
                         * yang sama dengan pengguna pertama.
                         */
                        for (FaceLangUser user : userB.inCluster().members) {
                            user.clustered = userA.inCluster();
                            userA.addInCluster(user);
                        }
                        /*
                         * membuang kluster yang mempunyai indeks key yang sama
                         * dengan indeks A (kluster pada pengguna kedua yang
                         * awal)
                         */
                        int i = 0;
                        while (i < clusters.size()) {
                            if (clusters.get(i).indexKey == indexB) {
                                clusters.remove(i);
                                break;
                            }
                            i++;
                        }
                    } else {
                        /*
                         * jika indeks kluster yang ditempati oleh pengguna
                         * pertama lebih besar dari pengguna kedua.
                         */

                        /*
                         * mendaftarkan semua anggota yang berada dalam kluster
                         * yang sama dengan pengguna pertama ke kluster yang
                         * ditempati pengguna kdua dan mereferensi ulang
                         * bahwa pengguna yang ada pada kluster yang sama dengan
                         * pengguna pertama mempunyai kluster baru yakni kluster
                         * yang sama dengan pengguna kedua.
                         */
                        for (FaceLangUser user : userA.inCluster().members) {
                            user.clustered = userB.inCluster();
                            userB.addInCluster(user);
                        }
                        /*
                         * membuang kluster yang mempunyai indeks key yang sama
                         * dengan indeks A (kluster pada pengguna pertama yang
                         * awal)
                         */
                        int i = 0;
                        while (i < clusters.size()) {
                            if (clusters.get(i).indexKey == indexA) {
                                clusters.remove(i);
                                break;
                            }
                            i++;
                        }
                    }
                }
            }
        }
    }

    /**
     * perintah mencetak, tergantun pola kedua dari masukan yang diberikan
     * dan sampai disini diyakinkan permintaan pada masukan pola 1 ialah
     * cetak
     * @throws IOException intrupsi dan gangguan dari Input Output
     */
    public void print() throws IOException {
        /*
         * membaca pada pola karakter ke 3 pada pola ke 2 pada masukan
         */
        char code = currentInputPattern[1].charAt(2);
        if (code == 'u') {
            /*
             * jika diinginkan kluster
             */
            this.printCluster();
        } else if (code == 'm') {
            /*
             * jika diinginkan temankluster
             */
            this.printClusterFriends();
        } else if (code == 'r') {
            /*
             * jika diinginkan terbesar
             */
            this.printMaximum();
        }
        writer.newLine();
    }

    /**
     * mencetak ukuran dari semua kluster yang ada dan mencetaknya secara
     * terurut dari besar ke terkecil
     * @throws IOException intrupsi dan gangguan dari Input Output
     */
    public void printCluster() throws IOException {
        if (!clusters.isEmpty()) {
            /*
             * mendaftarkan semua ukuran dari kluster yang ada
             */
            ArrayList<Integer> sizeList = new ArrayList<Integer>();
            int i;
            i = 0;
            while (i < clusters.size()) {
                sizeList.add(clusters.get(i).members.size());
                i++;
            }
            /*
             * mengurutkan dan mencetaknya secara terbalik (secara dari besar
             * ke kecil)
             */
            Collections.sort(sizeList, new Comparator<Integer>() {

                public int compare(Integer integer1, Integer integer2) {
                    return integer2.compareTo(integer1);
                }
            });

            String result = sizeList.toString();
            writer.write(result.substring(1, result.length() - 1).replace(" ", ""));
        }
    }

    /**
     * mencetak informasi nama-nama pengguna yang mempunyai jumlah mutual
     * dengan sama dengan jumlah temannya di satu kluster. dan mengurutkannya
     * serta mencetaknya.
     * @throws IOException intrupsi dan gangguan dari Input Output
     */
    public void printClusterFriends() throws IOException {
        ArrayList<String> username = new ArrayList<String>();
        int i = 0;
        /*
         * mendaftarkan pengguna yang mempunyai jumlah mutual dengan sama dengan
         * jumlah temannya di satu kluster.
         */
        while (i < clusters.size()) {
            Cluster selectedCluster = clusters.get(i);
            for (FaceLangUser user : selectedCluster.members) {
                if (selectedCluster.members.size() - user.mutualSet.size() == 1) {
                    username.add(user.name);
                }
            }
            i++;
        }
        /*
         * mengurutkan dan mencetak nama-nama yang memenuhi syarat tersebut
         */
        if (!username.isEmpty()) {
            Collections.sort(username);
            String result = username.toString();
            writer.write(result.substring(1, result.length() - 1).replace(" ", ""));
        }
    }

    /**
     * mencetak informasi kluster dengan ukuran terbesar. pertama akan dicari
     * dari koleksi kluster yang telah disimpan, dan dicari maksimalnya secara
     * linear time cost lalu mendaftarkan nama-namanya dan  mengurutkannya lalu
     * dicetak.
     * @throws IOException intrupsi dan gangguan dari Input Output
     */
    public void printMaximum() throws IOException {
        int currentIndexMax = 0;
        if (!clusters.isEmpty()) {
            int currentMaxSize = 0;
            /*
             * O(n) dicari maksimum size dari semua kemungkinan kluster yang
             * telah disimpan
             */
            for (int currentIndex = 0; currentIndex < clusters.size(); currentIndex++) {
                int currentSize = clusters.get(currentIndex).members.size();
                if (currentSize > currentMaxSize) {
                    currentMaxSize = currentSize;
                    currentIndexMax = currentIndex;
                }
            }
            /*
             * mengambil kluster dengan ukuran terbesar
             */
            HashSet<FaceLangUser> memberSet = clusters.get(currentIndexMax).members;
            /*
             * mendaftarkan nama-nama yang terdapat pada kluster dengan ukuran
             * terbesar
             */
            ArrayList<String> username = new ArrayList<String>();
            int index = 0;
            for (FaceLangUser user : memberSet) {
                username.add(user.name);
            }
            /*
             * mengurutkan nama-nama tersebut
             */
            Collections.sort(username);
            /*
             * mencetak semua nama-nama tersebut
             */
            String result = username.toString();
            writer.write(result.substring(1, result.length() - 1).replace(" ", ""));
        }
    }

    /**
     * untuk menyimpan pola input terkini, dengan maksud ingin memproses masukan
     * yang berbeda-beda. ini akan dipanggil setiap satu querry selesai
     * diproses.
     * @param currentInputPattern pola masukan terkini
     */
    public void setCurrentInputPattern(String[] currentInputPattern) {
        this.currentInputPattern = currentInputPattern;
    }
}

/**
 * pengguna FaceLang, sebagai penyimpanan informasi satu pengguna dan kluster
 * bagi penggunanya. serta beberapa pengguna yang mutual dengan dia.
 * @author Arra Adidaya
 * @version 1.0 (1-Juni-2011)
 */
class FaceLangUser {

    /**
     * nama dari pengguna
     */
    protected String name;
    /**
     * set dari orang-orang/pengguna yang mutual dengan pengguna ini
     */
    protected HashSet<FaceLangUser> mutualSet;
    /**
     * kluster bagi pengguna ini, objek ini mereferensi kluster dimanakah
     * pengguna ini.
     */
    protected Cluster clustered;

    /**
     * konstraktor untuk pengguna FaceLang yang akan dibuat, dan akan
     * menginisialisasi nama dan dimana kluster dia berada. dna membuat objek
     * dari HashSet yang akan menyimpan set dari mutual pengguna ini.
     * @param name nama pengguna
     * @param clustered kluster dimana dia berada
     */
    public FaceLangUser(String name, Cluster clustered) {
        this.mutualSet = new HashSet<FaceLangUser>();
        this.name = name;
        this.clustered = clustered;
    }

    /**
     * untuk memerikas apakah pengguna yang diberikan ialah mutual dengan
     * pengguna ini.
     * @param anotherUser pengguna lain
     * @return kebenaran apakah pengguna lain termasuk kedalam set mutual dari
     *         pengguna ini.
     */
    public boolean isMutual(FaceLangUser anotherUser) {
        return this.mutualSet.contains(anotherUser);
    }

    /**
     * mereferensikan kluster dimana ia berada
     * @return kluster dimana ia berada
     */
    public Cluster inCluster() {
        return clustered;
    }

    /**
     * menambahkan anggota baru/pengguna lain ke kluster yang dimiliki oleh
     * pengguna ini
     * @param anotherUser anggota baru/pengguna lain
     */
    public void addInCluster(FaceLangUser anotherUser) {
        this.clustered.members.add(anotherUser);
    }

    /**
     * membentuk mutualisme diantara pengguna ini dengan pengguna lain. dimana
     * pengguna lain akan membuat pengguna ini menjadi mutualnya dan begitu juga
     * sebaliknya.
     * @param anotherUser
     */
    public void mutualism(FaceLangUser anotherUser) {
        this.mutualSet.add(anotherUser);
        anotherUser.mutualSet.add(this);
    }
}

/**
 * digambarkan sebagai kluster, sebagaimana arti aslinya kluster, kelas ini akan
 * mengelompokan beberapa anggota dan menyimpan index-keynya.
 * @author Arra Adidaya
 * @version 1.0 (1-Juni-2011)
 */
class Cluster {

    /**
     * implementasi penyimpanan data aslinya menggunakan HashSet, sebuah
     * tipe data abstrak yang bekerja sebagai set yang menggunakan hash sebagai
     * fungsi pemetaan.
     */
    protected HashSet<FaceLangUser> members;
    /**
     * index key yang disimpan, menandakan index pada koleksi beberapa kluster
     * nantinya
     */
    protected int indexKey;

    /**
     * konstaktor yang akan menginisialisasi objek dari HashSet yang akan
     * menyimpan FaceLang User, dan dengan index key 0
     */
    public Cluster() {
        this(0);
    }

    /**
     * konstaktor yang akan menginisialisasi objek dari HashSet yang akan
     * menyimpan FaceLang User, dan dengan index key tergantung user
     * @param index indeks yang diberikan user
     */
    public Cluster(int index) {
        this.members = new HashSet<FaceLangUser>();
        this.indexKey = index;
    }

    /**
     * menambahkan dua pengguna kedalam HashSet yang telah ada.
     * @param memberA pengguna A
     * @param memberB pengguna B
     */
    public void add(FaceLangUser memberA, FaceLangUser memberB) {
        this.members.add(memberA);
        this.members.add(memberB);
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
interface OutputInputProcess {

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
