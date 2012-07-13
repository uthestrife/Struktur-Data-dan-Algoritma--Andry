
/**
 * SDA11102.java
 *
 * Nama berkas kode sumber : SDA11102.java
 * Batas waktu eksekusi program : 1 detik / kasus uji
 * Batas memori program : 32 MiB / kasus uji
 *
 * <b>Format Masukan</b>,
 *
 * <b>Format Keluaran</b>,
 * 
 *
 * <b>Cara Pemakaian</b>:
 * buka konsol pada sistem operasi, lalu ketik perintah <i>javac
 * SDA11102.java</i> untuk kompilasi sumber kode. jalankan kode
 * binari pada berkas SDA11102.class dengan perintah <i>java SDA11102
 * < in > out</i>. dengan "in" adalah nama file yang dapat diubah
 * sesuai keinginan, begitu juga dengan perintah "out".
 *
 *
 * Version:
 * - 1.0 (6-Maret-2011):
 *	- 
 *
 * Copyright 2011 Arra Adidaya, 0906563533
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.PriorityQueue;

/**
 * SDA11102.class,
 *
 *
 * @author Arra Adidaya
 * @version 1.0 (6-Maret-2011)
 */
public class SDA11102 {

    public static final boolean DEBUG_MODE = true;
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
    private static HashMap<String, Human> container;
    private static PriorityQueue<Human> priorityQueue;

    static {
        container = new HashMap<String, Human>(110000);
        priorityQueue = new PriorityQueue<Human>(100);
        reader = new BufferedReader(new InputStreamReader(System.in));
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public static void main(String[] arguments) throws IOException {
        long start = System.currentTimeMillis();
        while (true) {
            String inputedLine = reader.readLine();
            if (inputedLine == null) {
                break;
            }
            tokenizer = new StringTokenizer(inputedLine, ",");
            String name = tokenizer.nextToken();
            int money = Integer.parseInt(tokenizer.nextToken());
            if (!container.containsKey(name)) {
                Human newHuman = new Human(name, money);
                container.put(name, null);
                if (money != 0) {
                    priorityQueue.add(newHuman);
                }
            }
        }
        if (SDA11102.DEBUG_MODE) {
            writer.write("Completed in " + ((System.currentTimeMillis() - start)) + " ms.");
            writer.newLine();
            start = System.currentTimeMillis();
        }
        int counter = 0;
        while (!priorityQueue.isEmpty()) {
            writer.write("Korban peringkat ke-" + (++counter) + " " + priorityQueue.poll().toString());
            writer.newLine();
        }
        if (SDA11102.DEBUG_MODE) {
            writer.write("Completed in " + ((System.currentTimeMillis() - start)) + " ms.");
            writer.newLine();
        }
        reader.close();
        writer.flush();
        writer.close();
    }
}

class Human implements Comparable<Human> {

    private String name;
    private int money;

    public Human(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public int compareTo(Human anotherHuman) {
        int comparedValue = anotherHuman.money - this.money;
        if (comparedValue != 0) {
            return comparedValue;
        }
        else {
            return this.name.compareTo(anotherHuman.name);
        }
    }

    @Override
    public String toString() {
        return "adalah " + this.name + ", jumlah setoran " + this.money;
    }
}
