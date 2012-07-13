package dsa2011;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SDA11103 implements InputOutputProccessing {

    protected static Azuma azuma;
    protected static InputOutput inputOutput;

    static {
        azuma = new Azuma();
        inputOutput = new InputOutput(azuma);
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int numbersOfShoot = Integer.parseInt(reader.readLine());
        for (int currentShoot = 0; currentShoot < numbersOfShoot; currentShoot++) {
            int[] value = inputOutput.inputProccess();
            azuma.aimsMarble(value[InputOutput.INPUT_INDEX], value[InputOutput.INPUT_COLOR]);
        }
        azuma.calculatesLeftover();
        inputOutput.outputProccess();
    }
}

final class InputOutput implements InputOutputProccessing {

    public final static int INPUT_INDEX = 0;
    public final static int INPUT_COLOR = 1;
    private Azuma azuma;

    public InputOutput(Azuma azuma) {
        this.azuma = azuma;
    }

    public int[] inputProccess() throws IOException {
        String[] input = (reader.readLine()).split(" ");
        int index = Integer.parseInt(input[0]);
        int color = Integer.parseInt(input[1]);
        return new int[]{index, color};
    }

    public void outputProccess() throws IOException {
        writer.write("" + azuma.finalScore());
        writer.newLine();
        writer.flush();
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
interface InputOutputProccessing {

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
 *
 * @author Arra Adidaya
 * @version 1.1 (26-Maret-2011)
 */
interface Indexable {

    public int getIndex();
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
class StringComparator {

    public static int SAME = 0;
}

/**
 * MultiAccessedNode class.
 *
 * This class is simply acts as node that will be connected to another node.
 * for every nodes will have previous access and next access. this class
 * implements double linked list structure data type.
 *
 * @author Arra Adidaya
 * @param <G> Generic type class
 * @version 1.1.2.0 (29 Maret 2011)
 */
final class MultiAccessedNode<G> {

    /** linked node for next access */
    public MultiAccessedNode<G> next;
    /** linked node for previous access */
    public MultiAccessedNode<G> prev;
    /** data stored */
    public G object;

    /**
     * empty constructor. will not initialize for every value.
     */
    public MultiAccessedNode() {
    }

    /**
     * this constructor will intialize the MultiAccessedNode with its object
     *
     * @param object object to be intialized
     */
    public MultiAccessedNode(G object) {   // initialize object
        this.object = object;
    }

    /**
     * this method will give information for this Node with its String
     * data type
     *
     * @return Node informations
     */
    public String toString() {
        return ((Object) object).toString();
    }
}

final class Marbles implements Indexable {

    private int index;
    private int color;

    public Marbles(int index, int color) {
        this.index = index;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}

class LinkedList<G> {

    protected MultiAccessedNode<G> lastPointer;
    protected MultiAccessedNode<G> firstPointer;
    protected int size;

    public LinkedList() {
        this.lastPointer = null;
        this.firstPointer = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return lastPointer == null;
    }

    public void empty() {
        lastPointer = null;
        firstPointer = null;
    }

    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

final class AzumaLine<G extends Indexable> extends LinkedList<G> {

    public MultiAccessedNode<G> insertLast(G data) {
        MultiAccessedNode<G> newNode = new MultiAccessedNode<G>(data);
        if (this.isEmpty()) {
            firstPointer = newNode;
        } else {
            lastPointer.prev = newNode;
            newNode.next = lastPointer;
        }
        lastPointer = newNode;
        size++;
        return lastPointer;
    }

    public MultiAccessedNode<G> insertFirst(G data) {
        MultiAccessedNode<G> newNode = new MultiAccessedNode<G>(data);
        if (isEmpty()) {
            lastPointer = newNode;
        } else {
            firstPointer.next = newNode;
            newNode.prev = firstPointer;
        }
        firstPointer = newNode;
        size++;
        return firstPointer;
    }

    public MultiAccessedNode<G> insertLowerHalf(G data) {
        MultiAccessedNode<G> newNode = new MultiAccessedNode<G>(data);
        MultiAccessedNode<G> current = firstPointer;
        int indexLeft = data.getIndex();
        for (int i = size() - 1; i >= indexLeft; i--) {
            current = current.prev;
        }
        newNode.prev = current;
        newNode.next = current.next;
        current.next.prev = newNode;
        current.next = newNode;
        this.size++;
        current = newNode;
        return current;
    }

    public MultiAccessedNode<G> insertUpperHalf(G data) {
        MultiAccessedNode<G> newNode = new MultiAccessedNode<G>(data);
        MultiAccessedNode<G> current = lastPointer;
        int indexLeft = data.getIndex();
        for (int i = 0; i < indexLeft - 1; i++) {
            current = current.next;
        }
        newNode.prev = current;
        newNode.next = current.next;
        current.next.prev = newNode;
        current.next = newNode;
        size++;
        current = newNode;
        return current;
    }

    public void blastMarbles(int start, int finish) {
        MultiAccessedNode<G> current = lastPointer;
        int movementToGo = finish - start + 1;
        int index = 0;
        while (current != null && index != start) {
            current = current.next;
            index++;
        }
        for (int movement = 0; movement < movementToGo && current != null; movement++) {

            if (current == lastPointer) {
                lastPointer = current.next;
            } else {
                current.prev.next = current.next;
            }

            if (current == firstPointer) {
                firstPointer = current.prev;
            } else {
                current.next.prev = current.prev;
            }
            current = current.next;
        }
        size = size - movementToGo;
    }
}

class Azuma {

    protected final static int COMBO_HIT = 4;
    protected AzumaLine<Marbles> azumaLine;
    protected long comboCounter = 0;
    protected long score = 0;

    public Azuma() {
        this.azumaLine = new AzumaLine<Marbles>();
    }

    public void aimsMarble(int index, int color) throws IOException {
        Marbles aimedMarble = new Marbles(index, color);
        MultiAccessedNode<Marbles> rightBound = null;
        MultiAccessedNode<Marbles> leftBound = null;

        if (aimedMarble.getIndex() == 0) {
            rightBound = azumaLine.insertLast(aimedMarble);
            leftBound = rightBound;
        } else if (aimedMarble.getIndex() >= azumaLine.size()) {
            rightBound = azumaLine.insertFirst(aimedMarble);
            leftBound = rightBound;
        } else if (2 * aimedMarble.getIndex() <= azumaLine.size()) {
            rightBound = azumaLine.insertUpperHalf(aimedMarble);
            leftBound = rightBound;
        } else {
            leftBound = azumaLine.insertLowerHalf(aimedMarble);
            rightBound = leftBound;
        }
        this.comboCheck(rightBound, leftBound);
    }

    public void comboCheck(MultiAccessedNode<Marbles> rightBound, MultiAccessedNode<Marbles> leftBound) {
        boolean isComboHit = true;
        int sequencesCounter;
        while (isComboHit) {
            sequencesCounter = 1;
            while (rightBound != null) {
                if (rightBound.prev != null && rightBound.object.getColor() == rightBound.prev.object.getColor()) {
                    sequencesCounter++;
                    rightBound = rightBound.prev;
                } else {
                    break;
                }
            }
            while (leftBound != null) {
                if (leftBound.next != null && leftBound.object.getColor() == leftBound.next.object.getColor()) {
                    sequencesCounter++;
                    leftBound = leftBound.next;
                } else {
                    break;
                }
            }

            if (sequencesCounter >= Azuma.COMBO_HIT) {
                if (leftBound.next == null && rightBound.prev == null) {
                    azumaLine.lastPointer = null;
                    azumaLine.firstPointer = null;
                    leftBound = azumaLine.lastPointer;
                    rightBound = azumaLine.firstPointer;
                } else if (rightBound.prev == null) {
                    leftBound = leftBound.next;
                    azumaLine.lastPointer = leftBound;
                    leftBound = azumaLine.lastPointer;
                    leftBound.prev = null;
                    rightBound = leftBound;
                } else if (leftBound.next == null) {
                    rightBound = rightBound.prev;
                    azumaLine.firstPointer = rightBound;
                    rightBound = azumaLine.firstPointer;
                    rightBound.next = null;
                    leftBound = rightBound;
                } else {
                    rightBound = rightBound.prev;
                    leftBound = leftBound.next;
                    rightBound.next = leftBound;
                    leftBound.prev = rightBound;
                }

                leftBound = rightBound;
                rightBound = leftBound;
                azumaLine.setSize(azumaLine.size() - sequencesCounter);
                this.updateScore(sequencesCounter);
                comboCounter++;
            } else {
                comboCounter = 0;
                isComboHit = false;
            }
        }
    }

    public void updateScore(int countCombo) {
        long additionalScore = (long) 10 * (long) countCombo * ((long) Math.pow((comboCounter + 1), 3));
        this.score = this.score + additionalScore;
    }

    public void calculatesLeftover() {
        long countLeftover = 0;
        MultiAccessedNode<Marbles> current = azumaLine.lastPointer;
        while (current != null) {
            countLeftover++;
            current = current.next;
        }
        this.score = this.score - (long) 11 * (long) countLeftover;
    }

    public long finalScore() {
        return this.score;
    }
}
