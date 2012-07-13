/**
 * SDA11101.java
 *
 * Nama berkas kode sumber : SDA11101.java
 * Batas waktu eksekusi program : 1 detik / kasus uji
 * Batas memori program : 32 MiB / kasus uji
 *
 * <b>Format Masukan</b>,
 * Masukan dibaca dari masukan standar. Baris pertama berisi sebuah bilangan
 * bulat positif N <= 1000 yang menyatakan banyak rute bus. Kemudian untuk
 * setiap rute terdapat sebuah bilangan bulat nonnegatif M <= 1000 yang menyata-
 * kan banyak jalan yang ada dalam rute tersebut. Kemudian setiap baris berisi M
 * buah bilangan bulat x dimana |x| <= 1000 yang menyatakan nilai keasyikan
 * setiap jalan antar halte bus.
 *
 * <b>Format Keluaran</b>,
 * Keluaran ditulis ke keluaran standar. Untuk setiap rute bus, identifikasilah
 * pemberhentian bus awal i dan pemberhentian bus akhir j yang memiliki nilai
 * keasyikan maksimum. Jika terdapat lebih dari satu bagian yang sama-sama
 * memiliki nilai keasyikan maksimum, pilihkan yang memiliki jalan lebih banyak
 * ((j - i) yang terbesar) agar Agung senang karena lebih lama mengalami
 * masa-masa yang mengasyikkan. Kemudian jika masih ada yang sama, maka pilihkan
 * pemberhentian bus awal yang lebih dulu (i terkecil) agar rasa asyik Agung
 * lebih cepat terpuaskan. Jika terdapat jalan yang asyik di sebuah rute
 * keluarkan dengan format:
 * Jalan asyik rute r adalah di antara pemberhentian bus i dan j
 * di mana r (1 ≤ r ≤ N) adalah indeks rute dan 1 ≤ i < j ≤ M+1. Jika tidak
 * terdapat jalan yang asyik keluarkan dengan format:
 *
 * <b>Cara Pemakaian</b>:
 * buka konsol pada sistem operasi, lalu ketik perintah <i>javac
 * SDA11101.java</i> untuk kompilasi sumber kode. jalankan kode
 * binari pada berkas SDA11101.class dengan perintah <i>java SDA11101
 * < in > out</i>. dengan "in" adalah nama file yang dapat diubah
 * sesuai keinginan, begitu juga dengan perintah "out".
 *
 *
 * Version:
 * - 1.0 (29-Pebruari-2011):
 *	- membuat kode sumber dengan menggunakan metoda pembaca Scanner
 *        sebagai pembaca masukan standar.
 *      - mengubah metoda pembacaan dengan menggunakan BufferedReader
 * 	  untuk pembaca masukan standar (untuk mempercepat waktu eksekusi)
 *      - mengubah algoritma yang tadinya Brute Force, menjadi Linear. dengan
 *        menganalisis satu-satu langkahnya.
 *
 * Copyright 2011 Arra Adidaya, 0906563533
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

/**
 * SDA11101.class,
 *
 * kelas ini mengimplementasikan dan simulasikan algoritma MCSS yang efisien
 * serta dapat mencakup pertanyaan tentang indeks awal dan indeks akhir untuk
 * sebuah deret.
 *
 * @author Arra Adidaya
 * @version 1.0 (14-Pebruari-2011)
 */
public class SDA11101 {

    public static final boolean DEBUG_MODE = false;
    /**
     * an object which has a purpose to read input from input standard I/O.
     * this object has better time cost than next method which were consist in
     * instance object of Scanner.
     */
    private static BufferedReader reader;
    /**
     * an object which has a purpose to write output to standard  I/O.
     * this object has better time cost than print method which were exist in
     * class of PrintStream.
     */
    private static BufferedWriter writer;
    /**
     * an object of token which assists process of input by tokenized the
     * pattern of input. data mine purposes.
     */
    private static StringTokenizer tokenizer;

    static {
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    /**
     * metoda utama, berguna untuk menjalankan program
     * @param args tidak ada pengaruh besar
     */
    public static void main(String[] args) throws IOException {
        int numbersOfRoute = Integer.parseInt(reader.readLine());
        for (int currentRoute = 0; currentRoute < numbersOfRoute; currentRoute++) {
            int[] array;
            int numbersOfPoints = Integer.parseInt(reader.readLine());
            String[] stringArray = reader.readLine().split(" ");
            array = new int[stringArray.length];
            for (int currentPoint = 0; currentPoint < numbersOfPoints; currentPoint++) {
                array[currentPoint] = Integer.parseInt(stringArray[currentPoint]);
            }
            Sequence currentMaximumSequence = SDA11101.findMaxValueContigousSequence(array);
            if (currentMaximumSequence.sum <= 0) {
                writer.write("Rute " + (currentRoute+1) + " tidak asyik");
                writer.newLine();
            }
            else {
                writer.write("Jalan asyik rute " + (currentRoute+1) + " " + currentMaximumSequence);
                writer.newLine();
            }
        }
        writer.flush();
        writer.close();
    }

    /**
     * <summary>
     * Find the maximum subsequence.
     * </summary>
     * <param name="input"></param>
     * <returns></returns>
     * @param input
     * @return
     */
    public static Sequence findMaxValueContigousSequence(int[] input) {
        Sequence curr = new Sequence();
        Sequence max = new Sequence();

        max.restart(1, 0);
        curr.restart(1, 0);

        for (int i = 0; i < input.length; i++) {
        int sum = input[i] + curr.sum;

            if (sum < 0) {
                curr.restart(i + 2, 0);
                sum = 0;
            }
            else {
                if (input[i] > sum) {
                    // if the element is greater than the running sum then
                    // a new sequence is started from the current element.
                    curr.restart(i + 2, input[i]);
                }
                else {
                    // the current element is part of the sequence.
                    curr.extend(input[i]);
                }
            }

            if (curr.sum > max.sum) {
                // if the current running sum is greater than
                // the max then it becomes the maximum
                max.copy(curr);
            }
            else if (curr.sum == max.sum) {
                if (curr.end - curr.start > max.end - max.start) {
                    max.copy(curr);
                }
                else if (curr.end - curr.start == max.end - max.start) {
                    if (curr.start < max.start) {
                        max.copy(curr);
                    }
                }
            }
            if (SDA11101.DEBUG_MODE) {
                System.out.println("\n========\n");
                System.out.println("CURR [" + i + "] : " + curr);
                System.out.println("MAXX [" + i + "] : " + max);
                System.out.println("\n========\n");
            }
        }
        return max;
    }
}

/**
 * <summary>
 * The result set containing the starting and ending sequence snd the sum of
 * that sequence.
 * </summary>
 * @author Arra Adidaya
 */
class Sequence {

    public int start = 1;
    public int end = 1;
    public int sum = 0;

    /**
     * <summary>
     * Restart the sequence at the current position.
     * </summary>
     * <param name="index"></param>
     * <param name="value"></param>
     * @param index
     * @param value
     */
    public void restart(int index, int value) {
        start = end = index;
        sum = value;
    }

    /**
     * <summary>
     * Extend the current sequence and add the 'value' to the sum.
     * </summary>
     * <param name="value"></param>
     * @param value
     */
    public void extend(int value) {
        end++;
        sum += value;
    }

    public void copy(Sequence anotherSequence) {
        this.sum = anotherSequence.sum;
        this.start = anotherSequence.start;
        this.end = anotherSequence.end;
    }

    @Override
    public String toString() {
        String str = "adalah di antara pemberhentian bus " + this.start + " dan " + this.end;
        if (SDA11101.DEBUG_MODE) {
            str = " [SUM = "+ this.sum +"] " + str;
        }
        return str;
    }
    
}
