package dsa2011;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

public class SDA11104 {

    public static void main(String[] args) {
        BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            String line;
            while ((line = buffReader.readLine()) != null) {
                MathExpression mathExpr = new MathExpression(new StringBuffer(line));
                buffWriter.write(mathExpr.getExpressionString().toString());
                buffWriter.write(" = ");

                if (mathExpr.containsDivByZero()) {
                    buffWriter.write("Error");
                } else {
                    buffWriter.write(mathExpr.result().toString());
                }

                buffWriter.newLine();
                buffWriter.write(mathExpr.expressionTree().toString());
                buffWriter.newLine();
            }
            buffWriter.flush();
        } catch (Exception theException) {
            theException.printStackTrace();
        }
    }
}

/**
 * MathExpression
 */
final class MathExpression {

    protected StringBuffer exprString;
    protected BinaryTree<BigInteger> exprTree;

    public MathExpression(StringBuffer exprString) {
        this.setExpressionString(exprString);
        exprTree = new BinaryTree<BigInteger>(parse(0, exprString.length() - 1));
    }

    public void setExpressionString(StringBuffer exprString) {
        this.exprString = exprString;
    }

    public StringBuffer getExpressionString() {
        return exprString;
    }

    private BinaryTree<BigInteger>.Node<BigInteger> parse(int start, int end) {
        int checkCurrent, checkStart, checkEnd;
        int midLeft = 0, midRight = 0;
        char separator = '\0';
        int pointer = 0;
        checkStart = start;
        checkEnd = end;
        for (checkCurrent = start; checkCurrent <= end; checkCurrent++) {
            separator = exprString.charAt(checkCurrent);
            if (separator == '(') {
                pointer++;
            }
            if (separator == ')') {
                pointer--;
            }
            if ((separator == '+' || separator == '-' || separator == '/' || separator == '*') && pointer == 0) {
                midLeft = checkCurrent - 1;
                midRight = checkCurrent + 1;
                break;
            }
        }
        BinaryTree<BigInteger>.Node<BigInteger> subRoot = new BinaryTree<BigInteger>().new Node<BigInteger>(BigInteger.ZERO);

        if (midRight == 0) {
            if (exprString.charAt(checkStart) == '(' && exprString.charAt(checkEnd) == ')') {
                return parse(checkStart+1, checkEnd-1);
            }
            subRoot.setDatum(new BigInteger(exprString.substring(checkStart, checkEnd + 1)));
        } else {
            subRoot.setName("" + separator);
            subRoot.setLeftChild(parse(checkStart, midLeft));
            subRoot.setRightChild(parse(midRight, checkEnd));
        }
        return subRoot;
    }

    public BinaryTree<BigInteger> expressionTree() {
        return exprTree;
    }

    public boolean containsDivByZero() {
        return containsDivByZero(exprTree.getRoot());
    }

    private boolean containsDivByZero(BinaryTree<BigInteger>.Node<BigInteger> node) {
        if (node.hasChildren()) {
            if (node.getName().equalsIgnoreCase("/")) {
                return this.containsDivByZero(node.getLeftChild()) 
                        || this.containsDivByZero(node.getRightChild())
                        || result(node.getRightChild()).compareTo(BigInteger.ZERO) == 0;
            } else {
                return this.containsDivByZero(node.getLeftChild())
                        || this.containsDivByZero(node.getRightChild());
            }
        } else {
            return false;
        }
    }

    public BigInteger result() {
        return result(exprTree.getRoot());
    }

    private BigInteger result(BinaryTree<BigInteger>.Node<BigInteger> node) {
        if (node.hasChildren()) {
            String operator = node.getName();
            if (operator.equalsIgnoreCase("+")) {
                return result(node.getLeftChild()).add(result(node.getRightChild()));
            } else if (operator.equalsIgnoreCase("-")) {
                return result(node.getLeftChild()).add(result(node.getRightChild()).negate());
            } else if (operator.equalsIgnoreCase("*")) {
                return result(node.getLeftChild()).multiply(result(node.getRightChild()));
            } else {
                BigInteger numerator = result(node.getLeftChild());
                BigInteger denominator = result(node.getRightChild());
                if (numerator.multiply(denominator).compareTo(BigInteger.ZERO) < 0 && !numerator.remainder(denominator).equals(BigInteger.ZERO)) {
                    return numerator.divide(denominator).subtract(BigInteger.ONE);
                }
                return numerator.divide(denominator);
            }
        } else {
            return node.getDatum();
        }
    }
}

/**
 * BinaryTree<T> is a binary tree data structure
 * @author Ricky Suryadharma
 * @author Lasguido
 */
class BinaryTree<T> {

    /**
     * Node<T> is a binary-tree node data structure
     * @author Ricky Suryadharma
     * @author Lasguido
     */
    protected class Node<T> {

        /** 
         * the datum for <code>this</code>
         */
        protected T datum;
        /** 
         * the left child for <code>this</code>
         */
        protected Node<T> leftChild;
        /** 
         * the right child for <code>this</code>
         */
        protected Node<T> rightChild;
        /** 
         * the name for <code>this</code>
         */
        protected String name;

        // Constructor(s).
        /**
         * A constructor to set a datum
         * @param datum a datum to be set
         */
        public Node(T datum) {
            this(datum, null, null);
        }

        /**
         * A constructor to set a datum, left and right child
         * @param datum a datum to be set
         * @param leftChild a left child to be set
         * @param rightChild a right child to be set
         */
        public Node(T datum, Node<T> leftChild, Node<T> rightChild) {
            setDatum(datum);
            setLeftChild(leftChild);
            setRightChild(rightChild);
            setName(null);
        }

        //Method(s).
        /**
         * set a datum for <code>this</code>
         * @param datum a datum to be set
         */
        public void setDatum(T datum) {
            this.datum = datum;
        }

        /**
         * set a left child for <code>this</code>
         * @param leftChild a left child to be set
         */
        public void setLeftChild(Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        /**
         * set a right child for <code>this</code>
         * @param rightChild a right child to be set
         */
        public void setRightChild(Node<T> rightChild) {
            this.rightChild = rightChild;
        }

        /**
         * set a name for <code>this</code>
         * @param name a name to be set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * get a datum from <code>this</code>
         * @return the datum
         */
        public T getDatum() {
            return datum;
        }

        /**
         * get a left child from <code>this</code>
         * @return the left child
         */
        public Node<T> getLeftChild() {
            return leftChild;
        }

        /**
         * get a right child from <code>this</code>
         * @return the right child
         */
        public Node<T> getRightChild() {
            return rightChild;
        }

        /**
         * get a name from <code>this</code>
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * check whether <code>this</code> has one or two children
         * @return true iff has one or two children
         */
        public boolean hasChildren() {
            return this != null && !(this.leftChild == null
                    && this.rightChild == null);
        }

        /**
         * get a representation of this node
         * @return the representation
         */
        public String toString() {
            return name == null ? datum.toString() : name;
        }
    }

    /**
     * EOL (End Of Line)
     * @param Of
     */
    final protected static StringBuffer EOL = new StringBuffer(System.getProperty("line.separator"));
    /** 
     * the root for <code>this</code>
     */
    protected Node<T> root;

    // Constructor(s).
    /**
     * Default constructor: root = null
     */
    public BinaryTree() {
        this(null);
    }

    /**
     * A constructor to set a root
     * @param root a root to be set
     */
    public BinaryTree(Node<T> root) {
        setRoot(root);
    }

    // Method(s).
    /**
     * set root for <code>this</this>
     * @param root a root to be set
     */
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     * get root from <code>this</code>
     * @return a root
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * get a representation of binary tree in text style
     * @param root a root and all its children to be represented
     * @param carry a text that will be placed at left position as a carry
     *              from the previous recursion or first call
     * @return the representation
     */
    public StringBuffer representation(Node<T> root, StringBuffer carry) {
        StringBuffer binaryTreeRep = new StringBuffer();

        if (root != null && root.hasChildren()) {
            binaryTreeRep.append('[');
            binaryTreeRep.append(root);
            binaryTreeRep.append(']');
            binaryTreeRep.append(EOL);

            binaryTreeRep.append(carry);
            binaryTreeRep.append('|');
            binaryTreeRep.append(EOL);

            StringBuffer nextCarry = new StringBuffer(carry);
            nextCarry.append("|   ");

            binaryTreeRep.append(carry);
            binaryTreeRep.append("#=>");
            binaryTreeRep.append(representation(root.getRightChild(), nextCarry));

            binaryTreeRep.append(carry);
            binaryTreeRep.append('|');
            binaryTreeRep.append(EOL);

            nextCarry = new StringBuffer(carry);
            nextCarry.append("    ");

            binaryTreeRep.append(carry);
            binaryTreeRep.append("#=>");
            binaryTreeRep.append(representation(root.getLeftChild(), nextCarry));
        } else {
            binaryTreeRep.append(root).append(EOL);
        }
        return binaryTreeRep;
    }

    /**
     * get a representation of binary tree in default text style
     * @return the representation
     */
    public String toString() {
        return representation(root, new StringBuffer(" ")).toString();
    }
}
