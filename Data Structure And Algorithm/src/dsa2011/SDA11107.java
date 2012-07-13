
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Arra Adidaya
 */
public class SDA11107 implements IOProccess {

    public static void main(String[] arguments) throws IOException {
        HashArray table = new HashArray(1000000);
        String input = "";

        while (reader.ready()) {
            char querry = (char) reader.read();
            for (int i = 0; i < 6; i++) {
                reader.read();
            }
            String data = reader.readLine();
            String[] info;
            if (querry == 'S') {
                info = data.split(" ");
                table.put(info[0], Integer.parseInt(info[1]));
            } else {
                String names = "";
                HashArray listed = new HashArray(1000000);
                info = data.split(",");
                int total = 0;
                for (int i = 0; i < info.length; i++) {
                    int uang = table.get(info[i]);

                    if (uang == HashArray.NOT_PAID) {
                        if (!listed.containsKey(info[i])) {
                            listed.put(info[i], 0);
                            names += info[i] + ",";
                        }
                    } else {
                        if (!listed.containsKey(info[i])) {
                            total += uang;
                            listed.put(info[i], 0);
                        }
                    }
                }
                if (names.length() != 0) {
                    names = names.substring(0, names.length() - 1);
                    writer.write(names);
                    writer.newLine();
                }
                writer.write(Integer.toString(total));
                writer.newLine();
            }
        }
        writer.flush();
        writer.close();
        reader.close();
    }
}

class HashArray {

    protected static final int NOT_PAID = -1;
    private Participant[] participants;

    public HashArray(int initSize) {
        this.participants = new Participant[initSize];
    }

    public void put(String key, int money) {
        int iterations = 0;
        while (true) {
            int index = Math.abs((key.hashCode() + collision(iterations)) % participants.length);
            if (participants[index] == null) {
                participants[index] = new Participant(key, money);
                break;

            } else {
                if (participants[index].name.hashCode() == key.hashCode()) {
                    participants[index].money += money;
                    break;
                } else {
                    iterations++;
                }
            }
        }
    }

    public boolean containsKey(String key) {
        int iterations = 0;
        while (true) {
            int index = Math.abs((key.hashCode() + collision(iterations)) % participants.length);
            if (participants[index] == null) {
                return false;
            } else {
                if (participants[index].name.hashCode() == key.hashCode()) {
                    return true;
                } else {
                    iterations++;
                }
            }
        }
    }

    public int get(String key) {
        int iterations = 0;
        int money = NOT_PAID;
        while (true) {
            int index = Math.abs((key.hashCode() + collision(iterations)) % participants.length);
            if (participants[index] == null) {
                money = NOT_PAID;
                break;
            } else {
                if (participants[index].name.hashCode() == key.hashCode()) {
                    money = participants[index].money;
                    break;
                } else {
                    iterations++;
                }
            }
        }
        return money;
    }

    public int collision(int index) {
        return (int) Math.pow(2, index);
    }
}

class Participant {

    protected int money;
    protected String name;

    public Participant(String nama, int uang) {
        this.name   = nama;
        this.money  = uang;
    }

    public Participant(String nama) {
        this(nama, -1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participant other = (Participant) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}

/**
 * IOProccess.class,
 *
 * interface ini akan menyediakan pembaca serta penulis pada masukan dan
 * keluaran standar. akan teraplikasikan ke kelas yang mengimplemenatasikan
 * interface ini.
 *
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
interface IOProccess {

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
