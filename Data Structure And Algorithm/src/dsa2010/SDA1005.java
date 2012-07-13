
package dsa2010;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Andry Luthfi
 */
public class SDA1005 {

    private static final int MAXIMUM_SIZE = 31000;
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static HeapableArray heapArray;
    private static String initialTree;
    private static String[] elementsFirstTree;
    private static String querry;
    private static int insertedElement;
    private static int[] content;

    static {
        SDA1005.reader = new BufferedReader(new InputStreamReader(System.in));
        SDA1005.heapArray = new HeapableArray(SDA1005.MAXIMUM_SIZE);
    }

    public static void main(String[] arguments) throws IOException {
        SDA1005.initialTree = reader.readLine();
        SDA1005.elementsFirstTree = initialTree.split(" ");
        if (!SDA1005.initialTree.equals("")) {
            for (int i = 0; i < elementsFirstTree.length; i++) {
                SDA1005.heapArray.add(Integer.parseInt(elementsFirstTree[i]));
            }
        }
        SDA1005.heapArray.heapify();
        while ((SDA1005.querry = SDA1005.reader.readLine()) != null) {
            if (SDA1005.querry.charAt(0) == 'i') {
                SDA1005.heapArray.insert(Integer.parseInt((querry.split(" "))[1]));
            } else {
                SDA1005.heapArray.removeMin();
            }
        }
        SDA1005.heapArray.printIndexOrder();
        SDA1005.reader.close();
    }
}

class HeapableArray {

    private BufferedWriter writer;
    private Integer[] array;
    private int heapSize;

    public HeapableArray(int size) {
        this.array = new Integer[size];
        this.writer = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    private int parent(int currentIndex) {
        return (currentIndex - 1) / 2;
    }

    private int leftChild(int currentIndex) {
        return 2 * currentIndex + 1;
    }

    private int rightChild(int currentIndex) {
        return 2 * (currentIndex + 1);
    }

    public void insert(Integer newData) {
        this.array[this.heapSize] = newData;
        this.percolateUp(this.heapSize++);
    }

    public void add(Integer newData) {
        this.array[this.heapSize++] = newData;
    }

    public void heapify() {
        int currentIndex = this.heapSize - 1;
        for (; currentIndex >= 0; currentIndex--) {
            this.percolateDown(currentIndex);
        }
    }

    private void percolateUp(int currentIndex) {
        if (currentIndex != 0) {
            if (this.array[this.parent(currentIndex)] > this.array[currentIndex]) {
                this.swap(this.parent(currentIndex), currentIndex);
                this.percolateUp(this.parent(currentIndex));
            }
        }
    }

    private void percolateDown(int currentIndex) {
        int indexOfMinimum;
        if (this.rightChild(currentIndex) >= this.heapSize) {
            if (this.leftChild(currentIndex) >= heapSize) {
                return;
            } else {
                indexOfMinimum = this.leftChild(currentIndex);
            }
        } else {
            if (this.array[this.leftChild(currentIndex)] <= this.array[this.rightChild(currentIndex)]) {
                indexOfMinimum = this.leftChild(currentIndex);
            } else {
                indexOfMinimum = this.rightChild(currentIndex);
            }
        }

        if (this.array[currentIndex] > this.array[indexOfMinimum]) {
            this.swap(currentIndex, indexOfMinimum);
            percolateDown(indexOfMinimum);
        }
    }

    private void swap(int firstIndex, int secondIndex) {
        Integer temporary = this.array[firstIndex];
        this.array[firstIndex] = this.array[secondIndex];
        this.array[secondIndex] = temporary;
    }

    public void removeMin() throws IOException {
        if (!this.isEmpty()) {
            this.array[0] = this.array[heapSize - 1];
            heapSize--;
            if (heapSize > 0) {
                this.percolateDown(0);
            }
        } else {
            writer.write("[Empty]");
            writer.newLine();
        }
    }

    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    public void printIndexOrder() throws IOException {
        boolean first = true;
        if (!this.isEmpty()) {
            for (int i = 0; i < this.heapSize; i++) {
                writer.write((first ? "" : ",") + this.array[i]);
                if (first) {
                    first = false;
                }
            }
            writer.newLine();
        } else {
            writer.write("[Empty]");
            writer.newLine();
        }
    }

    public void finish() throws IOException {
        this.writer.flush();
        this.writer.close();
    }
}
