package dsa2012;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * kelas yang akan simulasikan
 * @author 
 */
public class SDA1112 {

    /**
     * fungsi utama yang digunakan untuk menjalankan seluruh program ini
     * @param arguments
     */
    public static void main(String arguments[]) {
        // membangun Tree M.L.M.
        Tree tree = new Tree();
        // membuat objek pembaca dari masukan standar
        Scanner scanner = new Scanner(System.in);


        Write.sln("\n");
        // selalu iterasi selama tidak diganggu dengan break
        while (true) {
            // konfirmasi pengguna untuk memberikan perintah
            Write.s("Command > ");
            // menerima perintah dari pengguna.
            String commandLine = scanner.nextLine();

            // jika perintah yang diberikan ialah exit
            if (commandLine.equalsIgnoreCase("exit")) {
                break;
            }
            // jika merupakan perintah kosong maka akan dianggap tidak ada apa
            // dan lanjut ke iterasi berikutnya
            boolean isNothing = commandLine.equals("") || commandLine.equals("\n") || commandLine.equals(" ");

            // jika merupakan sesuatu
            if (!isNothing) {
                // membelah string menjadi proses perkata
                String[] querry = commandLine.split(" ");

                // jika kata pertama ialah insert
                if (querry[0].equalsIgnoreCase("insert")) {
                    // maka prosesnya ialah memasukan agen baru ke tree
                    // sesuai dengan atribut yang diberikan dan lokasi
                    tree.insert(new Agent(new Information(querry[1], querry[3], Integer.parseInt(querry[4]), querry[2].equalsIgnoreCase("male"))), querry[6]);
                } else if (querry[0].equalsIgnoreCase("delete")) {
                    // jika kata pertama ialah delete
                    // maka akan menghapus agen dengan informasi yang diberikan
                    tree.delete(querry[1]);
                } else if (querry[0].equalsIgnoreCase("update")) {
                    // jika kata pertama ialah update
                    // maka anak memperbaruhi sesuai perintah yang diberikan
                    // dan akan diproses oleh fungsinya
                    tree.update(querry);
                } else if (querry[0].equalsIgnoreCase("get")) {
                    // jika kata pertama ialah get, maka akan mengakses info

                    // jika kata kedua person dan kata ketiga ialah level
                    if (querry[1].equalsIgnoreCase("person") && querry[2].equalsIgnoreCase("level")) {
                        try {
                            // maka akan memberikan info jumlah agen pada level
                            // yang diberikan
                            tree.membersInDepth(Integer.parseInt(querry[3]));
                        } catch (NumberFormatException nfe) {
                            // jika terjadi kesalahan saat memasukan atribut level
                            Write.getLevelFailed(querry[3]);
                        }
                    } else if (querry[1].equalsIgnoreCase("children")) {
                        // jika kata kedua yang diberikan ialah children
                        // maka akan memberikan nama dari semua anak yang dimiliki

                        // akses agen dengan info yang diberikan, di dalam
                        // tree
                        Agent agent = tree.traceBranchAgent(querry[2]);
                        if (agent == null) {
                            // jika tidak ditemukan agen
                            Write.getAgentFailed(querry[2]);
                        } else {
                            // jika ditemukan
                            if (agent.listOfChilds.isEmpty()) {
                                // namun tidak ada anak
                                Write.sln("\nNOBODY\n");
                            } else {
                                // mencetak semua anak dengan cara iterasi
                                Write.sln("");
                                for (int iter = 0; iter < agent.listOfChilds.size(); iter++) {
                                    // mencetak elemen dari daftar anak.
                                    Write.element(agent.listOfChilds.get(iter).info.name, iter, agent.listOfChilds.size() - 1);
                                }
                            }
                        }
                    } else if (querry[1].equalsIgnoreCase("downline")) {
                        // mengakses agen pada pohon dengan informasi yang 
                        // diberikan (nama)
                        Agent agent = tree.traceBranchAgent(querry[2]);

                        // jika ditemukan
                        if (agent != null) {
                            // jika mempunyai anak
                            if (!agent.listOfChilds.isEmpty()) {
                                // membuat daftar kosong untuk menampung anak
                                ArrayList<Agent> list = new ArrayList<Agent>();
                                for (int i = 0; i < agent.listOfChilds.size(); i++) {
                                    // memasukan anak yang dimiliki oleh agen
                                    // tertunjuk
                                    list.add(agent.listOfChilds.get(i));
                                }
                                int i = 0;
                                int sentinel = list.size();
                                while (i < sentinel) {
                                    // mengakses anak dari anak yang terdapat dalam
                                    // daftar
                                    ArrayList<Agent> tempChilds = list.get(i).listOfChilds;
                                    for (int j = 0; j < tempChilds.size(); j++) {
                                        list.add(tempChilds.get(j));
                                        sentinel = list.size();
                                    }
                                    i++;
                                }

                                Write.sln("");
                                // mencetak semua anak yang ditampung tadi
                                for (int iter = 0; iter < list.size(); iter++) {
                                    // mencetak elemen pada daftar, dengan
                                    // pemisah koma
                                    Write.element(list.get(iter).info.name, iter, list.size() - 1);
                                }
                            } else {
                                // jika tidak punya anak
                                Write.sln("\nNOBODY\n");
                            }
                        } else {
                            // jika tidak ditemukan, terjadi kegagalan
                            // dan akan memberitahu pengguna
                            Write.getAgentFailed(querry[2]);
                        }
                    } else {
                        // selain dari itu semua
                        // akan akses langsung agen
                        Agent agent = tree.traceBranchAgent(querry[1]);

                        // jika ditemukan
                        if (agent != null) {
                            // mencetek informasi agen
                            Write.sln(agent.toString());
                        } else {
                            // jika gagal, memberikan informasi kegagalan
                            // saat mengakses
                            Write.getAgentFailed(querry[1]);
                        }
                    }
                } else if (querry[0].equalsIgnoreCase("count")) {
                    // jika kata pertama ialah count
                    // maka akan diberikan proses hitung dan datanya
                    if (querry[1].equalsIgnoreCase("level")) {
                        // jika kata berikutnya adalah level
                        // akses agen
                        Agent agent = tree.traceBranchAgent(querry[2]);
                        if (agent != null) {
                            // memberikan informasi level dari agen
                            Write.getAgentLevel(querry[2], agent.info.level);
                        } else {
                            // jika gagal akses agen
                            Write.getAgentFailed(querry[2]);
                        }
                    } else if (querry[1].toLowerCase().equals("salary")) {
                        // jika kata berikutnya ialah salary
                        // akses agen
                        Agent agent = tree.traceBranchAgent(querry[2]);
                        if (agent != null) {
                            // jika ditemukan
                            // maka cetak informasi salary
                            Write.getAgentSalary(querry[2], agent.totalSalarySelf());
                        } else {
                            // jika tidak ditemukan
                            Write.getAgentFailed(querry[2]);
                        }
                    } else {
                        // selain dari itu perintah tidak dimengerti
                        Write.sln("\nUNKNOWN COMMAND\n");
                    }
                } else {
                    // selain dari itu perintah tidak dimengerti
                    Write.sln("\nUNKNOWN COMMAND\n");
                }
            }
        }

    }
}

/**
 * kelas pohon
 * @author asus
 */
class Tree {

    /**
     * variabel yang digunakan sebagai root dalam kelas tree ini
     */
    private final Agent BIG_BOSS = new Agent(new Information("bigboss", "02/01/1990", 5000000, Information.MALE));
    /**
     * variabel yang digunakan sebagai penunjuk akhir dalam kelas tree ini
     */
    private Agent current;

    /**
     * contructor kelas tree
     */
    public Tree() {
        this.current = this.BIG_BOSS;
    }

    /**
     * method yang digunakan untuk mencari agent dalam tree berdasarkan nama
     * yang diberikan
     * @param search nama yang diberikan
     * @return alamat dari agent yang ada di dalam tree
     */
    public Agent traceBranchAgent(String search) {
        Agent q = this.BIG_BOSS;
        Agent p = null;

        while (q != null) {
            if (q.getInformation().name.toLowerCase().equals(search.toLowerCase())) {
                p = q;
                break;
            }
            q = q.next;
        }
        return p;
    }

    /**
     * method yang digunakan untuk menambahkan node pada tree
     * @param agent node yang ingin ditambahkan
     * @param search node yang dicari untuk meletakkan node agent, dibawah node
     * tersebut
     */
    public void insert(Agent agent, String search) {
        if (search.equalsIgnoreCase("bigboss")) {
            this.BIG_BOSS.listOfChilds.add(agent);
            agent.parent = this.BIG_BOSS;
            this.current.next = agent;
            agent.prev = this.current;
            this.current = agent;

            Agent par = agent.parent;
            updateAll(par);

        } else {
            if (this.traceBranchAgent(agent.info.name) != null) {
                Write.nameFailed(agent.info.name);
            } else {
                if (this.traceBranchAgent(search) == null) {
                    Write.insertFailed(search);
                } else {
                    Agent found = this.traceBranchAgent(search);
                    found.listOfChilds.add(agent);
                    agent.parent = found;
                    this.current.next = agent;
                    agent.prev = this.current;
                    this.current = agent;

                    Agent par = agent.parent;
                    updateAll(par);
                }
            }
        }
    }

    /**
     * method yang digunakan untuk menghapus node dalam tree.
     * pada method ini akan dicek terlebih dahulu apakah node tersebut ada dalam
     * tree tersebut atau tidak, jika tidak ada, maka proses penghapusan akan
     * dibatalkan
     * @param search merupakan variabel untuk merepresentasikan node yang dicari
     * untuk dihapus
     */
    public void delete(String search) {
        if (!search.equalsIgnoreCase("bigboss")) {
            if (this.traceBranchAgent(search) == null) {
                Write.deleteFailed(search);
            } else {
                Agent agent = this.traceBranchAgent(search);
                if (!agent.listOfChilds.isEmpty()) {
                    for (int i = 0; i < agent.listOfChilds.size(); i++) {
                        agent.parent.listOfChilds.add(agent.listOfChilds.get(i));
                    }
                }
                agent.prev.next = agent.next;
                agent.next.prev = agent.prev;
                agent.parent.listOfChilds.remove(agent);

                Agent par = agent.parent;
                updateAll(par);
                agent.parent = null;
            }
        } else {
            Write.deleteFailed();
        }
    }

    /**
     * method yang digunakan untuk mengubah informasi dalam suatu node yang
     * ingin dicari
     * @param querry kumpulan perintah yang akan mengubah node tersebut
     */
    public void update(String[] querry) {
        if (this.traceBranchAgent(querry[1]) == null) {
            Write.updateFailed(querry[1]);
        } else {
            Agent agent = this.traceBranchAgent(querry[1]);
            for (int i = 2; i < querry.length; i += 2) {
                if (querry[i].equalsIgnoreCase("gender")) {
                    agent.info.gender = querry[i + 1].equalsIgnoreCase("male") ? Information.MALE : Information.FEMALE;
                } else if (querry[i].equalsIgnoreCase("daybirth")) {
                    agent.info.date = querry[i + 1];
                } else if (querry[i].equalsIgnoreCase("sales")) {
                    boolean failed = false;
                    try {
                        agent.info.omzet = Integer.parseInt(querry[i + 1]);
                    } catch (NumberFormatException nfe) {
                        Write.updateIdFailed(querry[i + 1]);
                        failed = true;
                    }
                    if (!failed) {
                        agent.update();
                        Agent parent = agent.parent;
                        while (parent != null) {
                            parent.update();
                            parent = parent.parent;
                        }
                    }
                }
            }
            Write.sln("\nOK\n");
        }
    }

    /**
     * method untuk menghitung jumlah node pada kedalaman yang diberikan
     * @param level kedalaman yang diberikan
     */
    public void membersInDepth(int level) {
        ArrayList<String> list = new ArrayList<String>();
        Agent agent = this.BIG_BOSS;

        while (agent != null) {
            if (agent.info.level == level) {
                list.add(agent.info.name);
            }
            agent = agent.next;
        }
        Write.sln("\n" + (list.isEmpty() ? "NOBODY" : list.toString()) + "\n");
    }

    /**
     * mengupdate informasi yang terbaru pada semua node
     * @param parent induk/root dari node-node yang akan diupdate
     */
    public static void updateAll(Agent parent) {
        while (parent != null) {
            parent.update();
            parent = parent.parent;
        }
        Write.sln("\nOK\n");
    }
}

/**
 * class yang digunakan untuk
 * @author asus
 */
class Agent {

    /**
     * variabel yang digunakan untuk menggassign suatu node menjadi parent
     */
    protected Agent parent;
    /**
     * variabel yang digunakan untuk menggassign anak dari parent. Jika di-next
     * lagi, maka anak sebelah kanan dari anak parent yang sebelumnya diassign
     * sebagai nextlah yang menjadi next selanjutnya
     *
     */
    protected Agent next;
    /**
     * variabel yang digunakan untuk menggassign anak dari parent. Jika di-prev
     * lagi, maka anak sebelah kiri dari anak parent yang sebelumnya diassign
     * sebagai prevlah yang menjadi prev selanjutnya
     */
    protected Agent prev;
    /**
     * variabel yang digunakan sebagai informasi node saja
     */
    protected Information info;
    /**
     * variabel yang digunakan untuk mengassign list dari anak-anak node yang
     * diassign sebagai parent
     */
    protected ArrayList<Agent> listOfChilds;

    /**
     * contructor kelas agent
     * @param info
     */
    public Agent(Information info) {
        this.info = new Information(info.name, info.date, info.omzet, info.gender);
        this.parent = null;
        this.next = null;
        this.prev = null;
        this.listOfChilds = new ArrayList<Agent>();
    }

    /**
     * constructor kelas agent
     */
    public Agent() {
        this(new Information(null, null, 0, false));
    }

    /**
     * method yang digunakan untuk menghitung berapa banyak jumlah anak dari
     * suatu parent node
     * @return banyaknya jumlah anak dari suatu parent node
     */
    public int totalChilds() {
        return this.listOfChilds.size();
    }

    /**
     * method yang digunakan untuk menghitung jumlah pendapatan dari setiap anak
     * suatu node parent
     * @return total pendapatan.gaji dari setiap anak dari suatu parent node
     */
    public int totalSalaryChilds() {
        int result = 0;
        if (!this.listOfChilds.isEmpty()) {
            for (int i = 0; i < this.listOfChilds.size(); i++) {
                result = result + this.listOfChilds.get(i).totalSalarySelf();
            }
        }
        return result;
    }

    /**
     * method yang digunakan untuk menghitung jumlah pendapatan sang agent
     * @return nilai pendapatan dari sang agent dan anak-anak node dia
     */
    public int totalSalarySelf() {
        return ((int) ((this.totalSalaryChilds() * 10) / 100) + (this.info.level * 1000000));
    }

    /**
     * method yang digunakan untuk mencari jumlah bawahan
     * @return jumlah bawahan
     */
    public int traceDownline() {
        ArrayList<Agent> list = new ArrayList<Agent>();
        for (int iter = 0; iter < this.listOfChilds.size(); iter++) {
            list.add(this.listOfChilds.get(iter));
        }

        int end = list.size();
        int iter = 0;
        while (iter < end) {
            ArrayList<Agent> tempChilds = list.get(iter).listOfChilds;
            for (int subIter = 0; subIter < tempChilds.size(); subIter++) {
                list.add(tempChilds.get(subIter));
                end = list.size();
            }
            iter++;
        }

        return list.size();
    }

    /**
     * method yang digunakan untuk menghitung jumlah keuntungan sesuai dengan
     * posisi dia
     */
    public void update() {
        int downline = this.traceDownline();
        if ((info.omzet >= 50000000) && (downline >= 500)) {
            info.level = 10;
        } else if ((info.omzet >= 40000000) && (downline >= 250)) {
            info.level = 9;
        } else if ((info.omzet >= 20000000) && (downline >= 200)) {
            info.level = 8;
        } else if ((info.omzet >= 10000000) && (downline >= 150)) {
            info.level = 7;
        } else if ((info.omzet >= 6000000) && (downline >= 100)) {
            info.level = 6;
        } else if ((info.omzet >= 5000000) && (downline >= 40)) {
            info.level = 5;
        } else if ((info.omzet >= 3000000) && (downline >= 20)) {
            info.level = 4;
        } else if ((info.omzet >= 1000000) && (downline >= 10)) {
            info.level = 3;
        } else if ((info.omzet >= 100000) && (downline >= 5)) {
            info.level = 2;
        } else if ((info.omzet >= 0) && (downline >= 0)) {
            info.level = 1;
        }
    }

    /**
     * method getter
     * @return info
     */
    public Information getInformation() {
        return this.info;
    }

    @Override
    public String toString() {
        return "\n" + info.name + "\nDAYBIRTH " + info.date + "\nGENDER "
                + (info.gender == Information.MALE ? "Male" : "Female")
                + "\nLEVEL " + info.level + "\nSALES " + info.omzet
                + "\nSALARY " + this.totalSalarySelf()
                + "\nTOTAL CHILD " + this.listOfChilds.size()
                + "\nTOTAL DOWNLINE " + this.traceDownline() + "\n";
    }
}

/**
 * kelas yang digunakan untuk menampung informasi.data diri sang agent (node)
 * @author asus
 */
class Information {

    /**
     * variabel yang mengassign data gender laki-laki dengan true
     */
    public static final boolean MALE = true;
    /**
     * variabel yang menggassign data gender perempuan dengan false
     */
    public static final boolean FEMALE = !MALE;
    /**
     * variabel yang menyimpan nama dari agent (node)
     */
    protected String name;
    /**
     * variabel yang menyimpan data lahir sang agent
     */
    protected String date;
    /**
     * variabel yang menyimpan data keuntungan dari sang agent
     */
    protected int omzet;
    /**
     * variabel yang menyimpan data posisi agent tersebut dalam perusahaan MLM
     * ini
     */
    protected int level;
    /**
     * variabel yang menyimpan data jenis kelamin agent
     */
    protected boolean gender;

    /**
     * constructor kelas Information
     * @param name nama agent
     * @param date data lahir agent
     * @param omzet keuntungan agent
     * @param level posisi agent dalam perusahaan
     * @param gender jenis kelamin agent
     */
    public Information(String name, String date, int omzet, int level, boolean gender) {
        this.name = name;
        this.date = date;
        this.omzet = omzet;
        this.level = level;
        this.gender = gender;
    }

    /**
     * contrusctor kelas Information
     * @param name nama agent
     * @param date data lahir agent
     * @param omzet keuntungan agent
     * @param gender jenis kelamin agent
     */
    public Information(String name, String date, int omzet, boolean gender) {
        this(name, date, omzet, 1, gender);
    }
}

/**
 * kelas yang digunakan untk membantu memberi konfirmasi, memudahkan kodingan
 * agar dapat menampilkan tulisan dengan cepat (developer mode)
 * @author asus
 */
class Write {

    /**
     * method untuk mencetak kalimat, dengan newline
     * @param string kalimat yang akan dicetak
     */
    public static void sln(String string) {
        System.out.println(string);
    }

    /**
     * method untuk mencetak kalimat, tanpa newline
     * @param string kalimat yang akan dicetak
     */
    public static void s(String string) {
        System.out.print(string);
    }

    /**
     * method untuk mencetak warning, dengan newline,
     * bahwa nama orang tersebut sudah ada dalam perusahaan
     * @param name nama agent yang digunakan sebagai input
     */
    public static void nameFailed(String name) {
        sln("INSERT FAILED. NAME ‘" + name + "’ ALREADY EXIST");
    }

    /**
     * method untuk mencetak warning, dengan newline,
     * bahwa nama orang tersebut sudah ada dalam perusahaan
     * @param name nama agent yang digunakan sebagai input
     * namun pada method ini diberikan tambahan "as" , agar lebih sopan
     */
    public static void insertFailed(String name) {
        sln("\nINSERT FAILED. NAME '" + name + "' ALREADY EXIST\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, bahwa nama orang tersebu
     * tidak dapat dihapus karena merupakan boss dari perusahaan
     */
    public static void deleteFailed() {
        sln("\nDELETE FAILED. BIGBOSS CANNOT BE DELETED\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, bahwa nama orang tersebut
     * tidak dapat dihapus karena merupakan boss dari perusahaan
     * namun pada method ini diberikan tambahan "as" agar lebih sopan
     */
    public static void deleteFailed(String name) {
        sln("\nDELETE FAILED. INVALID Agent '" + name + "'\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, bahwa nama orang tersebut
     * tidak ada dalam perusahaan, sehingga perintah update tidak bisa dilakukan
     * @param name nama orang yang dicari untuk diupdate datanya
     */
    public static void updateFailed(String name) {
        sln("\nUPDATE FAILED. INVALID Agent '" + name + "'\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, bahwa id yang dimasukkan
     * tidak valid dalam perusahaan tersebut
     * @param id data id yang dimasukkan
     */
    public static void updateIdFailed(String id) {
        sln("\nUPDATE FAILED. INVALID SALES NUMBER '" + id + "'\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, bahwa level tersebut tidak
     * valid dalam perusahaan tersebut
     * @param level posisi yang dimasukkan sebagai input
     */
    public static void getLevelFailed(String level) {
        sln("\nINVALID LEVEL NUMBER '" + level + "'\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, bahwa agent tersebut tidak
     * ada dalam data perusahaan
     * @param agent orang/node yang dimasukkan sebagai input
     */
    public static void getAgentFailed(String agent) {
        sln("\nINVALID Agent '" + agent + "'\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, jika ingin mengakses agent
     * pada suatu level gagal
     * @param agent pegawai/anggota perusahaan
     * @param level posisi
     */
    public static void getAgentLevel(String agent, int level) {
        sln("\n" + agent + " LEVEL " + level + "\n");
    }

    /**
     * method untuk mencetak warning, dengan newline, jika terjadi kegagalan
     * saat mengakses salary
     * @param agent pegawai/anggota perusahaan
     * @param salary gaji
     */
    public static void getAgentSalary(String agent, int salary) {
        sln("\n" + agent + " SALARY " + salary + "\n");
    }

    /**
     * method untuk mencetak isi dari anggota, dimana pada setiap anggota
     * dipisahkan dengan sebuah koma (sampai sebelum anggota terakhir tentunya)
     * @param message yang pingin dicetak
     * @param iter kedudukan dalam suatu list
     * @param end akhir dari suatu list
     */
    public static void element(String message, int iter, int end) {
        if (iter < end) {
            Write.s(message + ", ");
        } else {
            Write.s(message + "\n\n");
        }
    }
}
