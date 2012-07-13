
/**
 * SDA1002B.java
 *
 * This document belongs to Andry Luthfi. this document will show the
 * implementation of Abstract Stucture Data of Linked List for UStack and
 * Queue. Exactly this document using the implementation of multiple linked list
 * whereas the node will have bidirectional access. it can access previous node
 * and next node. The reason of using this implementation is running time.
 * for every action will cost O(1) running time.
 *
 * Collaboration :
 *  - Habibul Rafur.
 *  - Haya Rizky Fajrina
 *
 * This document containts :
 *  - NodeDualAccess.class
 *  - Equality.classclass
 *  - UStack.class
 *  - FamilyTree.class
 *  - MemberData.class
 *  - Member.class
 *
 * Version:
 *  - 1.0.0.0   (11 November 2010)   :
 *      - copy stack and double access node code from SDA1012.java
 *        (Copyrighted to Andry Luthfi)
 *      - get some help from Habibul Rafur by collaborate concepts to implements
 *        Tree as Structure Data choosen.    
 * 
 *
 * Copyright 2010 Andry Luthfi, 0906629044
 */
package dsa2010;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Vector;

/**
 * this class is show to implements Tree Structure Data Type by using Family
 * ilustration.
 * 
 * @author Andry Luthfi
 * @versin 1.0.0.0 (11 November 2010)
 */
public class SDA1002B {

    /**
     * main method core for ilustration of family tree.
     *
     * @param args do nothing
     * @throws IOException handle the exception that occurs in Input Output
     */
    public static void main(String[] args) throws IOException {
        SDA1002B program = new SDA1002B();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int totalFamilyMember;

        totalFamilyMember = Integer.parseInt(reader.readLine());
        FamilyTree familyTree = new FamilyTree(totalFamilyMember);
        for (int i = 0; i < totalFamilyMember; i++) {
            String inputRaw = reader.readLine();
            String[] memberData = inputRaw.split(", ");
            String[] nameAndMoney = memberData[1].replace('.', ' ').split(" ");
            String[] clanAndLevel = memberData[0].replaceAll("-", "0 ").split(" ");
            int hartaPribadi = Integer.parseInt(nameAndMoney[1]);
            familyTree.add(clanAndLevel[clanAndLevel.length - 1],
                    nameAndMoney[0], hartaPribadi, (clanAndLevel.length - 1));

        }
        String userChoice = reader.readLine();

        while (userChoice != null) {
            switch (Integer.parseInt(userChoice)) {
                case 1: {
                    System.out.println(familyTree.familyTreeHeight());
                    break;
                }

                case 2: {
                    System.out.println(familyTree.totalUniqueClan());
                    break;
                }

                case 3: {
                    System.out.println(familyTree.dominantClanNames(familyTree.getRoot()));
                    break;
                }

                case 4: {
                    System.out.println(familyTree.richest(familyTree.getRoot()));
                    break;
                }

                case 5: {
                    String rawData = reader.readLine();
                    String[] memberDescription = rawData.split(", ");
                    String[] nameDescription = memberDescription[0].split(" ");
                    familyTree.ancestorMember(familyTree.getRoot(), nameDescription, memberDescription[1]);
                    if (familyTree.isAncestored()) {
                        System.out.println("TIDAK ADA");
                    } else {
                        familyTree.getAncestorFullName();
                        System.out.println(familyTree.getAncestorFullName());
                    }
                    break;
                }

                case 6: {
                    familyTree.mothersList(familyTree.getRoot());
                    familyTree.printMothersList();
                    break;
                }
            }
            userChoice = reader.readLine();
        }


    }
}

/**
 * 
 * @author Andry Luthfi
 */
class FamilyTree {

    private Member root;
    private Member entree;
    private String dominantClan;
    private int totalDominantClan;
    private int dominantClanMoney;
    private int temporaryMoney;
    private boolean ancestored;
    private String ancestorFullName;
    private Vector<String> uniqueClanName;
    private UStack<String> motherAncestorFullName;

    public FamilyTree(int size) {
        root = null;
        uniqueClanName = new Vector<String>();
        motherAncestorFullName = new UStack<String>();
        this.ancestored = true;
    }

    public Member getRoot() {
        return this.root;
    }

    public boolean isAncestored() {
        return this.ancestored;
    }

    public String getAncestorFullName() {
        return this.ancestorFullName;
    }

    public void add(String namaMarga, String nama, int hartaPribadi, int level) {
        Member newMember = new Member(namaMarga, nama, hartaPribadi, level);
        root = add(newMember, root);

    }

    public int familyTreeHeight() {
        return root.familyTreeHeight() - 1;
    }

    public String dominantClanNames(Member root) {
        int temporaryTotal = 1;
        if (root == null) {
            return "";
        } else {
            if (root.getFather() != null) {
                temporaryTotal = fathersClanTotalMember(root) + 1;
            }

            if (totalDominantClan < temporaryTotal) {
                totalDominantClan = temporaryTotal;
                dominantClan = root.getClanName();
            }
            dominantClanNames(root.getFather());
            dominantClanNames(root.getMother());
        }

        return dominantClan;
    }

    public int fathersClanTotalMember(Member root) {

        if (root == null) {
            return 0;
        } else if (root != null) {
            return fathersClanTotalMember(root.getFather()) + 1;
        } else {
            return 1;
        }
    }

    public int totalUniqueClan() {
        if (root != null) {
            traversalPreOrder(root);
            return uniqueClanName.size();
        }
        return 0;
    }

    public void traversalPreOrder(Member root) {
        if (!uniqueClanName.contains(root.getClanName())) {
            uniqueClanName.add(root.getClanName());
        }

        if (root.getFather() != null) {
            traversalPreOrder(root.getFather());
        }
        if (root.getMother() != null) {
            traversalPreOrder(root.getMother());
        }
    }

    public int richest(Member root) {
        if (root == null) {
            return 0;
        } else {
            temporaryMoney = root.getMoney();

            if (root.getFather() != null) {
                temporaryMoney = temporaryMoney + calculateMoneyClan(root.getFather());
            }

            if (dominantClanMoney < temporaryMoney) {
                dominantClanMoney = temporaryMoney;
            }

            richest(root.getFather());
            richest(root.getMother());
        }
        return dominantClanMoney;
    }

    public void mothersList(Member root) {
        if (root.getMother() != null) {
            motherAncestorFullName.push(root.getMother().getFullName());
            mothersList(root.getMother());
        }
    }

    public void printMothersList() {
        while (!motherAncestorFullName.isEmpty()) {
            System.out.print(motherAncestorFullName.pop());
            if (!motherAncestorFullName.isEmpty()) {
                System.out.print(", ");
            }
        }
        System.out.println();

    }

    public void ancestorMember(Member root, String[] nameDescription, String ancestorLevel) {
        if (root.getClanName().equals(nameDescription[1])
                && root.getName().equals(nameDescription[0])) {
            this.ancestored = false;
            ancestorFullName = searchAncestor(root, ancestorLevel);
        }

        if (root.getFather() != null) {
            ancestorMember(root.getFather(), nameDescription, ancestorLevel);
        }

        if (root.getMother() != null) {
            ancestorMember(root.getMother(), nameDescription, ancestorLevel);
        }
    }

    public String searchAncestor(Member root, String ancestorLevel) {
        if (ancestorLevel.length() == 0) {
            return root.getFullName();
        } else if (ancestorLevel.startsWith("1")) {
            if (root.getFather() != null) {
                return searchAncestor(root.getFather(), ancestorLevel.substring(1));
            } else {
                return "TIDAK ADA";
            }
        } else if (ancestorLevel.startsWith("0")) {
            if (root.getMother() != null) {
                return searchAncestor(root.getMother(), ancestorLevel.substring(1));
            } else {
                return "TIDAK ADA";
            }
        } else {
            return "TIDAK ADA";
        }
    }

    public int calculateMoneyClan(Member root) {
        return (root == null) ? 0 : calculateMoneyClan(root.getFather()) + root.getMoney();
    }

    public Member add(Member newMember, Member temporaryRoot) {
        if (root != null) {
            if (temporaryRoot.getLevel() + 1 == newMember.getLevel()) {
                if (temporaryRoot.getClanName().equals(newMember.getClanName())) {
                    newMember.setAncestor(temporaryRoot);
                    temporaryRoot.setFather(newMember);
                    entree = temporaryRoot.getFather();
                } else {
                    newMember.setAncestor(temporaryRoot);
                    temporaryRoot.setMother(newMember);
                    entree = temporaryRoot.getMother();
                }
            } else {
                if (newMember.getLevel() == entree.getLevel()) {
                    temporaryRoot = entree.getAncestor();
                } else if (newMember.getLevel() > entree.getLevel()) {
                    temporaryRoot = entree;
                } else {
                    entree = entree.getAncestor();
                }
                add(newMember, temporaryRoot);
            }
        } else {
            newMember.setAncestor(root);
            root = newMember;
        }

        return root;
    }
}

class MemberData {

    private String clanName;
    private String name;
    private String fullName;
    private int money;
    private int level;

    public MemberData(String clanName, String name, String fullName, int money, int level) {
        this.clanName = clanName;
        this.name = name;
        this.fullName = fullName;
        this.money = money;
        this.level = level;
    }

    public int getMoney() {
        return this.money;
    }

    public String getClanName() {
        return this.clanName;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public int getLevel() {
        return this.level;
    }
}

class Member {

    private MemberData memberData;
    private Member father;
    private Member mother;
    private Member ancestor;
    private boolean visited;

    public Member(String clanName, String name, int money, int level) {
        this.memberData = new MemberData(clanName, name, name + " " + clanName, money, level);
        this.father = null;
        this.mother = null;
        this.visited = false;
    }

    public int familyTreeHeight() {
        int height;

        if (this.getClanName() == null) {
            height = 0;
        } else if (father != null && mother != null) {
            height = max(1 + father.familyTreeHeight(), 1 + mother.familyTreeHeight());
        } else if (father != null) {
            height = max(father.familyTreeHeight() + 1, 0);
        } else if (mother != null) {
            height = max(0, mother.familyTreeHeight() + 1);
        } else {
            return 1;
        }

        return height;

    }

    public int max(int valueA, int valueB) {
        return (valueA >= valueB) ? valueA : valueB;
    }

    public Member getFather() {
        return this.father;
    }

    public Member getMother() {
        return this.mother;
    }

    public Member getAncestor() {
        return this.ancestor;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public int getMoney() {
        return this.memberData.getMoney();
    }

    public String getClanName() {
        return this.memberData.getClanName();
    }

    public String getName() {
        return this.memberData.getName();
    }

    public String getFullName() {
        return this.memberData.getFullName();
    }

    public int getLevel() {
        return this.memberData.getLevel();
    }

    public void setFather(Member father) {
        this.father = father;
    }

    public void setMother(Member mother) {
        this.mother = mother;
    }

    public void setAncestor(Member ancestor) {
        this.ancestor = ancestor;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

/**
 * NodeDualAccess class.
 *
 * This class is simply acts as node that will be connected to another node.
 * for every nodes will have previous access and next access. this class
 * implements double linked list structure data type.
 *
 * @author Andry Luthfi
 * @param <U> Generic type class
 * @version 1.1.2.0 (27 October 2010)
 */
class NodeDualAccess<U> {

    /** linked node for next access */
    public NodeDualAccess<U> next;
    /** linked node for previous access */
    public NodeDualAccess<U> prev;
    /** data stored */
    public U object;

    /**
     * empty constructor. will not initialize for every value.
     */
    public NodeDualAccess() {
    }

    /**
     * this constructor will intialize the NodeDualAccess with its object
     *
     * @param object object to be intialized
     */
    public NodeDualAccess(U object) {   // initialize object
        this.object = object;
    }

    public U getObject() {
        return this.object;
    }

    public NodeDualAccess<U> getNext() {
        return this.next;
    }

    public void setObject(U object) {
        this.object = object;
    }

    public void setNext(NodeDualAccess<U> next) {
        this.next = next;
    }

    /**
     * this method will give information for this NodeDualAccess with its String
     * data type
     *
     * @return NodeDualAccess informations
     */
    @Override
    public String toString() {
        return ((Object) object).toString();
    }
}

/**
 * UStack class.
 *
 * This class implements Linked List implementation with UStack structure data
 * type. this class has generic type which means will allowed to every type
 * which wanted to be Pushed.
 *
 * UStack structure data type is structure which has a concept that "First In
 * Last Out" (FILO). by using linked list implementation, the new nodes will
 * be entried into first list. and will be out in last list.
 *
 * @author Andry Luthfi
 * @param <G> Generic Data Type
 * @version 1.2.0.0    (28 October 2010)
 * @see NodeDualAccess
 */
final class UStack<U> {

    /** node that acts as first pointer */
    private NodeDualAccess<U> firstPointer;
    /** stack current size */
    private int size;

    /**
     * default method which initialize every attribute and build a state where
     * initialization state is begin in stack. by calling empty method
     * @see empty
     */
    public UStack() {
        this.empty();
    }

    /**
     * empty the stack and with its initialization state
     */
    public void empty() {
        this.firstPointer = new NodeDualAccess<U>();
        this.size = 0;
    }

    /**
     * entry the element in stack, by using push in stack abstract data type
     * implementation in linked list
     *
     * @param object element that wanted to be pushed in stack
     */
    public void push(U object) {
        NodeDualAccess<U> oldFirstPointer = this.firstPointer;
        this.firstPointer = new NodeDualAccess<U>(object);
        this.firstPointer.next = oldFirstPointer;
        this.size++;
    }

    /**
     * retrive the elements in top of stack and swap the top of stack into
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
     * check element in top of stack
     * @return element in top of stack
     */
    public U peek() {
        return this.firstPointer.object;
    }

    /**
     * to check whether the stack is empty or not
     * @return empty state
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * stack size
     * @return stack size
     */
    public int size() {
        return size;
    }
}
