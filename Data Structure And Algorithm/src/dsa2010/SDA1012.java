/**
 * SDA1012.java
 *
 * This document belongs to Andry Luthfi. this document will show the
 * implementation of Abstract Stucture Data of Linked List for ImplementStack and
 * ImplementQueue. Exactly this document using the implementation of multiple linked list
 * whereas the node will have bidirectional access. it can access previous node
 * and next node. The reason of using this implementation is running time.
 * for every action will cost O(1) running time.
 *
 * This document containt :
 *  - DualAccessedNode.class
 *  - Equality.class
 *  - ImplementStack.class
 *  - ImplementQueue.class
 *
 * Version:
 *  - 1.0.0.0   (25 October 2010)   :
 *      - analyze the implementation for uses of ImplementStack and ImplementQueue.
 *      - using array implementation for ImplementStack uses.
 *      - considering the weakness of array, and preferly to using linked list
 *        as assistance.
 *      - create Node.class
 *      - create ImplementStack.class
 *      - create ImplementQueue.class
 *      - create Equality.class
 * - 1.1.0.0    (26 October 2010)   :
 *      - use single access for Node implementation
 *      - build test cases for ImplementStack and ImplementQueue
 *      - mark date for successing ImplementStack and ImplementQueue implementation
 *      - override toString method in class of Object in order to simplify
 *        printing process
 * - 1.1.1.0    (27 October 2010)   :
 *      - renaming the Node into SingleAccessNode
 *      - renaming ImplementStack into LinkedList and also for ImplementQueue
 * - 1.1.2.0    (27 October 2010)   :
 *      - renaming the SingleAccessNode into DualAccessedNode in order to rebuild
 *        the code for using the implementation of dual access node.
 *      - planning to simplify the method from O(n) into O(1)
 *      - renaming the LinkedList class into ImplementStack and ImplementQueue, classify into its
 *        implementation.
 * - 1.2.0.0    (28 October 2010)   :
 *      - implements DualAccessedNode for linked list application
 *      - override toString method in equality in order to simplify the object
 *      - build another test case for new ImplementStack and ImplementQueue which already using
 *        multiple linked list access
 *      - build a main method with assistance of BufferedReader,
 *        InputStreamReader and IOException in java.io package
 *      - build a main class which inculded main method
 *
 * Copyright 2010 Andry Luthfi, 0906629044
 */

package dsa2010;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * SDA1012 class.
 *
 * This main class contains main method with its application of ImplementStack and ImplementQueue
 * implementation. this class will shows the uses of ImplementStack to checks
 * parenthesis and its partner. and the application of ImplementStack will be showed in
 * collection of equalities that ignored and accepted in parenthesis checking.
 * for every parenthesis type there are a prior for the each. a curly one has
 * greater prior than square one, and square has greater prior than the oval
 * one.
 *
 * @author Andry Luthfi
 * @version 1.2.0.0    (28 October 2010)
 * @see DualAccessedNode
 * @see Equality
 * @see ImplementStack
 * @see ImplementQueue
 */
public class SDA1012 {

    /**
     * this main method will verbaly shows the implementation of ImplementStack and ImplementQueue
     * and its application. this method will shows whether the equalities that
     * given is accepted or not by using the implementation of ImplementStack and ImplementQueue
     *
     * @param arguments nothing to be process
     * @throws IOException
     */
    public static void main(String[] arguments) throws IOException {   // ImplementQueue to collect the equalities which to be accepted and ignored
        ImplementQueue<Equality> acceptedList = new ImplementQueue<Equality>();
        ImplementQueue<Equality> ignoredList = new ImplementQueue<Equality>();
        // an object of assistance to read standard input in keyboard or file
        // in this case, the class will read in keyboard input
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        // always loop
        while (true) {
            // condition when input is not ready
            if (!reader.ready()) {   // then loop will be break
                break;
            }
            // an input from keyboard
            String input = reader.readLine();
            // case when the input is contains with sequence "!benar"
            // in this case, the user is intents to demand the system to show
            // accepted equalities in ImplementQueue
            if (input.contains("!benar")) {   // need to extract the information into two parts
                String[] part = input.split(" ");
                // part two of this information is number of accepted equalities
                // which accepted in parenthesis test
                int target = Integer.parseInt(part[1]);
                // initialize value of current
                int current = 0;
                // will be printed while the current is less than target and
                // accepted list is not empty
                while (current < target && !acceptedList.isEmpty()) {   // prints deImplementQueue result's
                    System.out.println(acceptedList.deImplementQueue());
                    current++;
                }
                // make space between another information
                System.out.println(current + "\n");
            }
            // case when the input is contains with sequence "!salah"
            // in this case, the user is intents to demand the system to show
            // ignored equalities in ImplementQueue
            else if (input.contains("!salah")) {   // need to extract the information into two parts
                String[] part = input.split(" ");
                // part two of this information is number of ignored equalities
                // which ignored in parenthesis test
                int target = Integer.parseInt(part[1]);
                // initialize value of current
                int current = 0;
                // will be printed while the current is less than target and
                // ignored list is not empty
                while (current < target && !ignoredList.isEmpty()) {   // prints deImplementQueue result's
                    System.out.println(ignoredList.deImplementQueue());
                    current++;
                }
                // make space between another information
                System.out.println(current + "\n");
            }
            // case whether the user wants to give equation with its equalities
            // for example : newtonianForce F=m*a=(-k)x
            // for this case, the system will process the equalities
            else {   // this ImplementStack is used to checks parenthesis partner
                ImplementStack<Character> parenthesisImplementStack = new ImplementStack<Character>();
                // list of numbered index ignored equalities
                ImplementQueue<Integer> ignoredEqualitydIndexList = new ImplementQueue<Integer>();
                // extract the information into two parts whereas the part one
                // is equation names, and part two is its equalities
                String[] part = input.split(" ");
                String[] equalities = part[1].split("=");
                // check for every equalities
                for (int i = 0; i < equalities.length; i++) {
                    int offsetParenthesis = 0;
                    int squareParenthesisCounter = 0;
                    int notParenthesisCounter = 0;
                    boolean startCount = false;
                    boolean isWrong = false;
                    // check for every characters
                    for (int j = 0; j < equalities[i].length(); j++) {   // situation where counts the inner square parenthesis
                        startCount = (!parenthesisImplementStack.isEmpty()
                                && parenthesisImplementStack.peek() == '[');
                        // if character is open parenthesis
                        if (Equality.isOpenParenthesis(equalities[i].charAt(j))) {
                            if (equalities[i].charAt(j) == '[' && startCount) {
                                squareParenthesisCounter++;
                            }
                            notParenthesisCounter = 0;
                            if (!parenthesisImplementStack.isEmpty() && (parenthesisImplementStack.peek() < equalities[i].charAt(j)
                                    || parenthesisImplementStack.peek() == '{'
                                    && equalities[i].charAt(j) == '{')
                                    || squareParenthesisCounter > 1) {
                                isWrong = true;
                                break;
                            }// push into ImplementStack to check with its partner
                            parenthesisImplementStack.push(equalities[i].charAt(j));
                            offsetParenthesis++; // raise the offset
                        }
                        // else if character is closed parenthesis
                        else if (Equality.isClosedParenthesis(equalities[i].charAt(j))) {
                            if (notParenthesisCounter == 0) {
                                isWrong = true;
                                break;
                            }
                            // if parenthesis is not empty
                            if (!parenthesisImplementStack.isEmpty()) {   // peek for the top of ImplementStack
                                char peek = parenthesisImplementStack.peek();
                                char partner = Equality.parenthesisPartner(peek);
                                // if matched to its partner
                                if (equalities[i].charAt(j) == partner) {   // then delete the elements of TOS
                                    char deleted = parenthesisImplementStack.pop();
                                    if (parenthesisImplementStack.isEmpty()
                                            || parenthesisImplementStack.peek() == '{') {
                                        squareParenthesisCounter = 0;
                                    }

                                }
                            }
                            offsetParenthesis--; // lower the offset
                        }
                        else {
                            notParenthesisCounter++;
                        }
                    }
                    // equation called to be ignored if only if there is offset
                    // or the ImplementStack is not empty
                    if (offsetParenthesis != 0 || !parenthesisImplementStack.isEmpty()
                            || isWrong) {
                        ignoredEqualitydIndexList.enImplementQueue(i + 1);
                    } // reset the ImplementStack into empty
                    parenthesisImplementStack.empty();
                }
                // if thereare ignored numbered index of equalities
                if (!ignoredEqualitydIndexList.isEmpty()) {   // put into accepted ImplementQueue
                    ignoredList.enImplementQueue(new Equality(part[0], ignoredEqualitydIndexList));
                }
                else // otherwise
                {   // put into accepted list
                    acceptedList.enImplementQueue(new Equality(part[0], ignoredEqualitydIndexList));
                }
            }
        }
    }
}

/**
 * Equality class.
 *
 * This class is used to stored equality name and index number of ignored
 * equlity. this class is served to check whether the equality parenthesis
 * is valid.
 *
 * @author Andry Luthfi
 * @version 1.1.0.0 (26 Oktober 2010)
 * @see ImplementQueue
 */
class Equality {

    /** equation's name */
    private String equationName;
    /** list of number of ignored equality  */
    private ImplementQueue<Integer> ignoredEquationList;

    /**
     * this constructor is initialize equation name and its ignored equation
     * list into local variable in this class.
     *
     * @param equationName equation name
     * @param ignoredEquationList ignored equation list
     */
    public Equality(String equationName, ImplementQueue<Integer> ignoredEquationList) {
        this.equationName = equationName;
        this.ignoredEquationList = ignoredEquationList;
    }

    /**
     * this method is check wheter the given char is open parenthesis or not.
     * there are three type of open parenthesis:
     *  - { curly parenthesis
     *  - [ sqaure parenthesis
     *  - ( oval parenthesis
     *
     * @param parenthesis character that given which considered to be open
     *                    parenthesis
     * @return whether its a open parenthesis or not
     */
    public static boolean isOpenParenthesis(char parenthesis) {
        return (parenthesis == '(' || parenthesis == '[' || parenthesis == '{');
    }

    /**
     * this method is check wheter the given char is closed parenthesis or not.
     * there are three type of closed parenthesis:
     *  - ) oval parenthesis
     *  - ] sqaure parenthesis
     *  - ) curly parenthesis
     *
     * @param parenthesis character that given which considered to be closed
     *                    parenthesis
     * @return whether its a closed parenthesis or not
     */
    public static boolean isClosedParenthesis(char parenthesis) {
        return (parenthesis == ')' || parenthesis == ']' || parenthesis == '}');
    }

    /**
     * this method is helps to find a partner for given open parenthesis by
     * returning its partner, there are three type of partner in parenthesis :
     *  - { with }
     *  - [ with ]
     *  - ( with )
     * 
     * @param parenthesis opened parenthesis which to be matched with its
     *                    partner
     * @return its partner or open parenthesis type's partner
     */
    public static char parenthesisPartner(char parenthesis) {
        return (parenthesis == '(') ? ')'
                : (parenthesis == '[') ? ']'
                : '}';
    }

    /**
     * return an information about this equality with its ignored equality
     * by simply returning the information with String data type.
     *
     * @return equality information with ignored list
     */
    @Override
    public String toString() {
        String ignoredList = "";
        while (!this.ignoredEquationList.isEmpty()) {
            ignoredList += " " + this.ignoredEquationList.deImplementQueue();
        }
        return equationName + (ignoredList);
    }
}

/**
 * DualAccessedNode class.
 *
 * This class is simply acts as node that will be connected to another node.
 * for every nodes will have previous access and next access. this class
 * implements double linked list structure data type.
 *
 * @author Andry Luthfi
 * @param <G> Generic type class
 * @version 1.1.2.0 (27 October 2010)
 */
class DualAccessedNode<G> {

    /** linked node for next access */
    public DualAccessedNode<G> next;
    /** linked node for previous access */
    public DualAccessedNode<G> prev;
    /** data stored */
    public G object;

    /**
     * empty constructor. will not initialize for every value.
     */
    public DualAccessedNode() {
    }

    /**
     * this constructor will intialize the DualAccessedNode with its object
     *
     * @param object object to be intialized
     */
    public DualAccessedNode(G object) {   // initialize object
        this.object = object;
    }

    /**
     * this method will give information for this Node with its String
     * data type
     *
     * @return Node informations
     */
    @Override
    public String toString() {
        return ((Object) object).toString();
    }
}

/**
 * ImplementQueue class.
 *
 * This class implements Linked List implementation with ImplementQueue structure data
 * type. this class has generic type which means will allowed to every type
 * which wanted to be EnImplementQueue.
 *
 * ImplementQueue structure data type is structure which has a concept that "First In
 * First Out" (FIFO). by using linked list implementation, the new nodes will
 * be entried into last list. and will be out in first list.
 * 
 * @author Andry Luthfi
 * @param <G> Generic Data Type
 * @version 1.2.0.0    (28 October 2010)
 * @see DualAccessedNode
 */
final class ImplementQueue<G> {

    /** nodes which acts as first pointer in linked list */
    private DualAccessedNode<G> firstPointer;
    /** nodes which acts as last pointer in linked list */
    private DualAccessedNode<G> lastPointer;
    /** the size of the list */
    private int size;

    /**
     * First initialization for intial situation of ImplementQueue. the situation is
     * where the first pointer is pointing at last pointer, and last pointer is
     * pointing at first pointer. this routine is same as empty method
     */
    public ImplementQueue() {
        this.empty();
    }

    /**
     * this method is empty the ImplementStack and make the state into intial state or
     * initial situation
     */
    public void empty() {
        this.firstPointer = new DualAccessedNode<G>();
        this.lastPointer = new DualAccessedNode<G>();
        this.firstPointer.next = this.lastPointer;
        this.lastPointer.prev = this.firstPointer;
        this.size = 0;
    }

    /**
     * this method will insert the element into last entry, by insert in between
     * last pointer and node before last pointer. by using routine :
     * newNode will pointing into last pointer and node before last pointer,
     * node before last pointer will pointing to newNode, and for the last
     * pointer will pointing into newNode
     *
     * Complexity   : O(1)
     * Analysis     : to insert an element, there is no need to search the last
     * element, because the assistance of last pointer and bidirectional
     * access in DualAccessedNode
     *
     * @param object object which to be inserted
     * @see DualAccessedNode
     */
    public void enImplementQueue(G object) {
        DualAccessedNode<G> newNode = new DualAccessedNode<G>(object);
        // insert new node by config the next pointer of new node is pointed
        // into last pointer, and new node also pointed into node before last
        newNode.next = this.lastPointer;
        newNode.prev = this.lastPointer.prev;
        // node before last one is pointing into new nodelast pointer will
        // pointing into new node.
        this.lastPointer.prev.next = newNode;
        this.lastPointer.prev = newNode;
        // increase size
        this.size++;
    }

    /**
     * this method is intented into retrive element in first pointer and delete
     * the first one. before delete the first pointer, first it needed to be
     * pointed into next pointer from first pointer. and recover the connection
     * between the list.
     *
     * @return object retrived in first element
     */
    public G deImplementQueue() {
        G deImplementQueued = null;
        if (!this.isEmpty()) {
            deImplementQueued = this.firstPointer.next.object;
            this.firstPointer.next = this.firstPointer.next.next;
            this.firstPointer.next.prev = this.firstPointer;
            this.size--;
        }
        return deImplementQueued;
    }

    /**
     * check elements in top of ImplementStacks. not delete the first element, only
     * just peek in top of ImplementStack.
     *
     * @return elements in top of ImplementStacks
     */
    public G peek() {
        return this.lastPointer.prev.object;
    }

    /**
     * check whether the ImplementStack is empty or not
     * 
     * @return the condition of empty state
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * give the information of ImplementQueue size
     *
     * @return ImplementQueue size
     */
    public int size() {
        return size;
    }
}

/**
 * ImplementStack class.
 *
 * This class implements Linked List implementation with ImplementStack structure data
 * type. this class has generic type which means will allowed to every type
 * which wanted to be Pushed.
 *
 * ImplementStack structure data type is structure which has a concept that "First In
 * Last Out" (FILO). by using linked list implementation, the new nodes will
 * be entried into first list. and will be out in last list.
 *
 * @author Andry Luthfi
 * @param <G> Generic Data Type
 * @version 1.2.0.0    (28 October 2010)
 * @see DualAccessedNode
 */
final class ImplementStack<U> {

    /** node that acts as first pointer */
    private DualAccessedNode<U> firstPointer;
    /** ImplementStack current size */
    private int size;

    /**
     * default method which initialize every attribute and build a state where
     * initialization state is begin in ImplementStack. by calling empty method
     * @see empty
     */
    public ImplementStack() {
        this.empty();
    }

    /**
     * empty the ImplementStack and with its initialization state
     */
    public void empty() {
        this.firstPointer = new DualAccessedNode<U>();
        this.size = 0;
    }

    /**
     * entry the element in ImplementStack, by using push in ImplementStack abstract data type
     * implementation in linked list
     *
     * @param object element that wanted to be pushed in ImplementStack
     */
    public void push(U object) {
        DualAccessedNode<U> oldFirstPointer = this.firstPointer;
        this.firstPointer = new DualAccessedNode<U>(object);
        this.firstPointer.next = oldFirstPointer;
        this.size++;
    }

    /**
     * retrive the elements in top of ImplementStack and swap the top of ImplementStack into
     * next node which has to be next first pointer.
     *
     * @return element to be retrieved
     */
    public U pop() {
        U poped = null;
        if (!this.isEmpty()) {
            poped = this.firstPointer.object;
            this.firstPointer = this.firstPointer.next;
            this.size--;
        }
        return poped;
    }

    /**
     * check element in top of ImplementStack
     * @return element in top of ImplementStack
     */
    public U peek() {
        return this.firstPointer.object;
    }

    /**
     * to check whether the ImplementStack is empty or not
     * @return empty state
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * ImplementStack size
     * @return ImplementStack size
     */
    public int size() {
        return size;
    }
}
