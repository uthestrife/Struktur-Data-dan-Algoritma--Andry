/*
 * SDA1013.java
 *
 * This document belongs to Andry Luthfi. many thanks for some of my companions
 * whose taught me about data structure applied to this problem and many lesson
 * taught by my superior.
 *
 * This document containt :
 *  - Travel.class
 *  - Edge.class
 *  - Graph.class
 *
 * Thanks to :
 *      - Krishna Priawan Hardinda : as System Analyst and share about this
 *        assignment spesification.
 *      - Robeth Rahmatullah : teach me how to using some concept of backtracing
 *        in order to avoid Time Limit Exceed. and the using of
 *        traceBreadthFirstSearch method to initialization phase.
 *
 * Version:
 *  - 1.0 (3-December-2010):
 *      - build input process to adapt input pattern that given.
 *      - build Travel class, and Edge class that have been taught by Robeth
 *        Rahmatullah.
 *      - fix bug in input process
 *      - using Scanner and object out in System class, in order to knowing the
 *        brute force mode.
 *      - use tokenizer as solve the input pattern in city details
 *  - 1.1 (5-December-2010):
 *      - create Graph class, and implements every Algortithm that needed
 *      - develop traceBreadthFirstSearch method in order to assists solve the
 *        the given problem.
 *      - replace Scanner and out object which contained in System class, with
 *        BufferedWriter and BufferedReader class. to optimized the running time
 *        cost by input process in case the inputs are massive.
 *      - analyze and give documentation to every method with its complexity
 *
 *
 * Copyright © 2010 Andry Luthfi, 0906629044
 */

package dsa2010;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * this class will performed every concept that have been taught in Data
 * Structure and Algorithm. such as, the effeciency of running time in every
 * process that been through. this class using BufferedReader and BufferedWriter
 * instead of Scanner and object of PrintStream, for the cause of efficiency of
 * running time. and this class will perform the using of Graph as Data
 * Structure which implements Matrix and Adjacency List. input will be desribe
 * in input method. and the output will be defined too in output method.
 *
 * @see input()
 * @see output()
 * @author Andry Luthfi
 * @version 1.1 (5-December-2010)
 */
public class SDA1013 {

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
     * numbers of cities, will be represents as vertices. using short as choosen
     * data type, because maximum numbers of cities is 5000 cities.
     */
    private static short numbersCities;
    /**
     * ad data structure which represent a real graph, and this data structure
     * using an implementastion of matrix and adjacency list.
     */
    private static Graph graph;
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
     * this main method will show the simulation the using of graph, from input
     * process through output process.
     *
     * Complexity Analysis :
     * input process have a cost of O(N*M) ~ O(M) and output process has cost of
     * O(K). so there are cost of O(K + M*N)
     *
     * @param arguments command line arguments, there are nothing to be
     *                  important
     * @throws IOException some exception if there are error or something bad
     *                     occured in Input/Output process
     */
    public static void main(String[] arguments) throws IOException {
        numbersCities = Short.parseShort(reader.readLine());
        graph = new Graph(numbersCities);
        for (short hometown = 0; hometown < numbersCities; hometown++) {
            int cityDetails = Integer.parseInt(reader.readLine());
            SDA1013.input(hometown, cityDetails);
        }
        graph.graphGrouping();
        for (String querry = ""; (querry = reader.readLine()) != null;) {
            SDA1013.output(querry);
        }
        reader.close();
        graph.finish();
    }

    /**
     * this method do input process using defined pattern. this method access
     * every information which collectable in each cities. we called as city
     * block.
     *
     * Complexity Analysis :
     * if there are N city to per inputed. and for every ciry there are M detail
     * data. so, T(N * T(M)) = O(N*M) ~ O(M) because the detail will be dominate
     * Type: O(M) ~ approach to linear.
     *
     * precondition for input :
     *  - 0 <= NUMBERS_CITY_DETAIL <= 10.000
     *  - 0 <= destination <= 5000, excluded with current city which detailed
     *  - 0 <= distance <= 2^30
     *
     * defined pattern :
     *      [NUMBERS_CITY_DETAIL]
     *      [DESTINATION_1] [DISTANCE_1] [STREET_NAME_1]
     *      [DESTINATION_2] [DISTANCE_2] [STREET_NAME_2]
     *      [DESTINATION_3] [DISTANCE_3] [STREET_NAME_3]
     *      .               .            .
     *      .               .            .
     *      [DESTINATION_N] [DISTANCE_N] [STREET_NAME_N]
     *
     * @param source source city.
     * @param numbersBlock numbers of city details of city block.
     * @throws IOException some exception if there are error or something bad
     *                     occured in Input/Output process
     */
    public static void input(int source, int numbersBlock) throws IOException {
        for (int cityDetail = 0; cityDetail < numbersBlock; cityDetail++) {
            SDA1013.tokenizer = new StringTokenizer(SDA1013.reader.readLine());
            int destination = Integer.parseInt(SDA1013.tokenizer.nextToken());
            int distance = Integer.parseInt(SDA1013.tokenizer.nextToken());
            String streetName = SDA1013.tokenizer.nextToken();
            SDA1013.graph.createConnection(source, destination, distance, streetName);
        }
    }

    /**
     * output process that depended on user requestment. this program is served
     * to receive an output request whenever the input is still exist.
     * (not End-Of-File)
     *
     * Complexity Analysis :
     * for every actions of output has cost of T(4) = O(1), and if there are N
     * output requested from user, then T(N*O(1)) = O(N)
     * Type : O(N) = linear
     *
     * @param querry querry which given by input pattern, there are two type of
     *        querry exist. first, the querry about asking : `whether the city A
     *        city B is connected`. two the querry about asking : `the shortest
     *        path between city A and city B`.
     * @throws IOException some exception if there are error or something bad
     *                     occured in Input/Output process
     */
    public static void output(String querry) throws IOException {
        SDA1013.tokenizer = new StringTokenizer(SDA1013.reader.readLine());
        if (querry.charAt(0) == '1') {
            int hometown = Integer.parseInt(SDA1013.tokenizer.nextToken());
            int destination = Integer.parseInt(SDA1013.tokenizer.nextToken());
            boolean isRelated = SDA1013.graph.areRelated(hometown, destination);
            SDA1013.graph.printConnentionStatusBetween(hometown, destination);
        } else {
            int hometown = Integer.parseInt(SDA1013.tokenizer.nextToken());
            int destination = Integer.parseInt(SDA1013.tokenizer.nextToken());
            SDA1013.graph.shortestPath(destination, hometown);
            SDA1013.graph.printShortestPath();
        }
    }
}

/**
 * this class is represents as Travel in this case. for every
 * vertices which created by this class, will have permanent value. and for
 * every vertices are compared by its distance. and if its still have same
 * value, then will be compared both's steps. and this class represents as a
 * record to journey that been through
 *
 * @author Andry Luthfi
 * @version 1.0 (3-December-2010)
 */
class Travel implements Comparable<Travel> {

    /**
     * city which enumerate into integer symbol. such as city 0. it means city
     * which are inputed in first time. and this variable symbolized as source
     * city
     */
    private int source;
    /**
     * city which enumerate into integer symbol. such as city 0. it means city
     * which are inputed in first time. and this variable symbolized as
     * destination city
     */
    private int destination;
    /**
     * distance which represents this vertex. whenever this vertex is visited,
     * the accumulation of distance that been through, occured.
     */
    private long distance;
    /**
     * total steps, to reach this vertex from somewhere point. accumulation of
     * step that been through is occured.
     */
    private int steps;

    /**
     * this accumulation vertex, is stored an accumulation of status of the
     * current movement. stores its source city, destination city, distance
     * and its steps for the time of when the object is created.
     *
     * example :
     *
     *  A ---(2 km)---> B ---(6 km)---> C
     *  when we travel through C, distance which has accumulated is 8 km.
     *
     * @param source source city
     * @param destination destination city
     * @param distance distance that have been through
     * @param steps steps that have been through
     */
    public Travel(int newSource, int newDestination, long newDistance, int newSteps) {
        source = newSource;
        destination = newDestination;
        distance = newDistance;
        steps = newSteps;
    }

    /**
     * this method will access destination city in this Travel
     * Complexity Time : O(1), constant time cost
     * @return destination city in this Travel
     */
    public int getDestination() {
        return destination;
    }

    /**
     * this method will access distance that been through in this
     * Travel
     * Complexity Time : O(1), constant time cost
     * @return distance that been through in this Travel
     */
    public long getDistance() {
        return distance;
    }

    /**
     * this method will access source city in this Travel
     * Complexity Time : O(1), constant time cost
     * @return source city in this Travel
     */
    public int getSource() {
        return source;
    }

    /**
     * this method will access steps that been through in this
     * Travel
     * Complexity Time : O(1), constant time cost
     * @return steps that been through in this Travel
     */
    public int getSteps() {
        return steps;
    }

    /**
     * this method will compares this Travel with another
     * Travel. comparing both distance, if distance of both
     * Travel is same, then continue to compares both steps.
     *
     * @param anotherVertex another Travel
     * @return compares value, negativity is indicate that this object of
     *         Travel is less than anotherVertex. and vice versa.
     */
    public int compareTo(Travel anotherVertex) {
        return (distance - anotherVertex.distance) == 0
                ? steps - anotherVertex.steps
                : (int) (distance - anotherVertex.distance);
    }

    /**
     * this method is created in order to debug process, to knowing the
     * the information that stored in this class
     *
     * @return information in this class
     */
    @Override
    public String toString() {
        return source + " " + destination + " " + steps + " " + distance;
    }
}

/**
 * this class will represents as edge that connected between two city. it store
 * distance for traveling from city that connected by this edge with another
 * city. and for the another possible path of two city, it stores street name.
 *
 * @author Andry Luthfi
 * @version 1.0 (3-December-2010)
 */
class Edge implements Comparable<Edge> {

    /**
     * distance or cost to traveling from source city which connected by this
     * edge to destination city which connected by this edge. and vice versa to
     * its opposite direction.
     */
    private int distance;
    /**
     * indicates its street name, in order to classify to another edge that
     * connected to same city.
     */
    private String streetName;

    /**
     * intialize every attribute needed in this class. such as distance and
     * street name.
     *
     * @param distance distance for traveling from source city through
     *        destination city, and vice versa
     * @param streetName street name
     */
    public Edge(int newDistance, String newStreetName) {
        distance = newDistance;
        streetName = newStreetName;
    }

    /**
     * this method will access distance value for traveling from source city
     * through destination city, and vice versa in this class.
     * Complexity Time : O(1), constant time cost
     * @return distance value for traveling from source city
     *         through destination city, and vice versa
     */
    public int getDistance() {
        return distance;
    }

    /**
     * this method will access street name that indicates its edge.
     * Complexity Time : O(1), constant time cost
     * @return street name that indicates its edge.
     */
    public String getStreetName() {
        return streetName;
    }

    public void setDistance(int newDistance) {
        distance = newDistance;
    }

    public void setStreetName(String newStreetName) {
        streetName = newStreetName;
    }

    /**
     * this method will compares this edge with another edge. comparing both
     * distance, if distance of both edge is same, then continue to compares
     * both street name.
     *
     * @param anotherEdge another edge
     * @return compares this edge with another edge. comparing both
     *         distance, if distance of both edge is same, then continue to
     *         compares both street name.
     */
    public int compareTo(Edge anotherEdge) {
        return ((distance - anotherEdge.distance) == 0
                ? streetName.compareTo(anotherEdge.streetName)
                : distance - anotherEdge.distance);
    }
}

/**
 * this class represents as Graph, this Graph capable to do some tracing using
 * breadth first search method. and finding the connection status between two
 * cities. this class capable of finding the shortest path between two cities.
 *
 * @author Andry Luthfi
 * @version 1.0 (3-December-2010)
 */
class Graph {

    /**
     * maximum connected city for every cities. using prime number near 1000000
     * in order to using Hash Map
     */
    private final int MAXIMUM_CONNECTED = 1000003;
    /**
     * some symbol that symbolized situation where node from this graph is
     * untraced. in order to grouping of sub graph which not connected.
     */
    private final static int UNTRACED = 0;
    /**
     * some symbol that symbolize situation where node of selected one is not
     * having previous track.
     */
    private final static int UNDEFINED = -1;
    /**
     * the numbers of the city. that given in constructor method. this variable
     * that indicate how many city must be created in this graph.
     */
    private final int NUMBER_OF_CITY;
    /**
     * this data structure is represents as relation matrix between two cities.
     * this data structure is served every outcome from number of cities. and
     * for this elements from this matrix is stores edge that connected between
     * row and coloumn.
     */
    private HashMap<Integer, Edge> relationMap;
    /**
     * this data structure is represents as adjacency list for every posible
     * outcome that given from input pattern.
     */
    private ArrayList<Integer>[] adjacencyList;
    /**
     * this list is stores every street name that traced with shortesPath method
     * an automaticaly print as sequence of journey if calls it toString
     */
    private List<String> routeList;
    /**
     * this data structure serves as queue that have priority system. the one
     * which first out is the one which have greatest priority.
     */
    private PriorityQueue<Travel> priorityQueue;
    /**
     * this data structure serves as queue to help store temporary memory, and
     * not to rearranged its order.
     */
    private LinkedList<Integer> queue;
    /**
     * memory that store visited status to city that given, index of this
     * array is represents as city.
     */
    private boolean[] visitedFor;
    /**
     * memory that store minimum distance for city that given, index of this
     * array is represents as city.
     */
    private long[] minimumDistanceFor;
    /**
     * memory that store minimum step for city that given, index of this array
     * is represents as city.
     */
    private int[] minimumStepsFor;
    /**
     * memory that store previous for city that given, index of this array is
     * represents as city.
     */
    private int[] previousFor;
    /**
     * memory that store type of graph grouping for city that given, index of
     * this array is represents as city.
     */
    private int[] groupTypeFor;
    /**
     * condition where indicates that current condition is finished to be traced
     * by shortestPath method.
     */
    private boolean tracedWithShortestPath;
    /**
     * this variable acts as flag or marker to every cities. indicates type of
     * group of graph. grouping occured because unconnected graph.
     */
    private int groupType;
    /**
     * objects which acts as writer to output standard. using buffered system.
     */
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * this method will initialize every memory and variable to using graph
     * as data structure. by calling initComponent method.
     *
     * @see initComponent()
     * @param size graph size, indicates number of cities
     */
    public Graph(int size) {
        this.NUMBER_OF_CITY = size;
        this.initComponents();
    }

    /**
     * this method acts to initialize every array that used in this grap.
     * initialize every memories and objects.
     */
    private void initComponents() {
        this.relationMap = new HashMap<Integer, Edge>(MAXIMUM_CONNECTED);
        this.adjacencyList = new ArrayList[NUMBER_OF_CITY];
        this.minimumDistanceFor = new long[NUMBER_OF_CITY];
        this.minimumStepsFor = new int[NUMBER_OF_CITY];
        this.previousFor = new int[NUMBER_OF_CITY];
        this.groupTypeFor = new int[NUMBER_OF_CITY];
        this.groupType = 1;
        this.tracedWithShortestPath = false;

        for (int i = 0; i < NUMBER_OF_CITY; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }
    }

    /**
     * initialize every elements in every arrays that needed to do
     * shortestPath method. by fill every elements to its default value
     * for intial condition.
     *
     * @see shortestPath
     */
    private void initialization() {
        Arrays.fill(this.minimumDistanceFor, Long.MAX_VALUE);
        Arrays.fill(this.minimumStepsFor, Integer.MAX_VALUE);
        Arrays.fill(this.previousFor, Graph.UNDEFINED);
    }

    /**
     * put edse into hash map by calculate its domain, in order to reversible
     * mapping. example :
     * cityA --> cityB is located to index 9878
     * cityB --> cityA is located to index 9878
     * the index would be the same
     * .
     * @param hometown city hometown
     * @param destination city destination
     * @param inputEdge inputed edge from input
     */
    private void setEdgeFrom(int hometown, int destination, Edge inputEdge) {
        relationMap.put((hometown > destination)
                ? (destination * 5000 + hometown)
                : (hometown * 5000 + destination), inputEdge);
    }

    /**
     * take every adjacency city from adjacency list where in current
     * condition. relative to currentCity that given.
     *
     * @param currentCity city that relative to adjacency list
     */
    private void advanceToAdjacency(Travel currentCity) {
        for (int adjacency : this.adjacencyList[currentCity.getDestination()]) {
            Integer distanceThrough = this.getEdgeFrom(
                    currentCity.getDestination(), adjacency).getDistance();
            Travel travelAdjacency = new Travel(
                    currentCity.getDestination(), adjacency,
                    currentCity.getDistance() + distanceThrough,
                    currentCity.getSteps() + 1);
            this.priorityQueue.add(travelAdjacency);
        }
    }

    /**
     * update new value for djikastra memory. relativelly to current movement
     * that given.
     * @param current current movement
     */
    private void updateDjikstraData(Travel current) {
        minimumDistanceFor[current.getDestination()] = current.getDistance();
        minimumStepsFor[current.getDestination()] = current.getSteps();
        previousFor[current.getDestination()] = current.getSource();
    }

    /**
     * some method to do some tracing using breadth first search method. initial
     * position given from start.
     * @param start initial position.
     */
    private void traceBreadthFirstSearch(int start) {
        this.queue = new LinkedList<Integer>();
        this.groupTypeFor[start] = this.groupType;
        queue.add(start);

        while (!queue.isEmpty()) {
            int currentCity = queue.poll();
            for (Integer adjacency : this.adjacencyList[currentCity]) {
                if (this.groupTypeFor[adjacency] == Graph.UNTRACED) {
                    this.groupTypeFor[adjacency] = this.groupType;
                    this.queue.add(adjacency);
                }
            }
        }
    }

    /**
     * condition whether current movement is worth to be expanded or not. by
     * check its requirement.
     * @param current current movement
     * @return condition whether current movement is worth to be expanded or not
     */
    private boolean isWorthedToExpanded(Travel current) {
        return current.getDistance() <= minimumDistanceFor[current.getDestination()]
                && current.getSteps() <= minimumStepsFor[current.getDestination()]
                && comparingPathBetween(current.getDestination(), current.getSource()) < 0;
    }

    /**
     * comparing path between both input. compares by its lexicograph method.
     * take its street name. in this view, this method called by view point of
     * reverse path.
     *
     * @param nextDestination next destination
     * @param newPrevious new previous
     * @return comparing value
     */
    private int comparingPathBetween(int nextDestination, int newPrevious) {
        if (previousFor[nextDestination] != Graph.UNDEFINED) {
            return this.getStreetNameFrom(nextDestination, newPrevious).compareTo(this.getStreetNameFrom(nextDestination, this.previousFor[nextDestination]));
        } else {
            return Graph.UNDEFINED;
        }
    }

    /**
     * 
     * @param hometown
     * @param destination
     * @return
     */
    private Edge getEdgeFrom(int hometown, int destination) {
        return (hometown > destination)
                ? this.relationMap.get(destination * 5000 + hometown)
                : this.relationMap.get(hometown * 5000 + destination);
    }

    /**
     * this method will created connection between two given cities. named as
     * hometown and destination. by create edge and put into map to store
     * information related to two cities. and store adjacency list between two
     * cities.
     *
     * @param hometown hometown city
     * @param destination destination city
     * @param distance distance that define between both cities
     * @param streetName street name indicates this connection
     */
    public void createConnection(int hometown, int destination, int distance, String streetName) {
        Edge inputedEdge = new Edge(distance, streetName);
        if (this.getEdgeFrom(hometown, destination) != null) {
            if (this.getEdgeFrom(hometown, destination).compareTo(inputedEdge) > 0) {
                this.getEdgeFrom(hometown, destination).setDistance(distance);
                this.getEdgeFrom(hometown, destination).setStreetName(streetName);
            }
        } else {
            this.adjacencyList[hometown].add(destination);
            this.adjacencyList[destination].add(hometown);
            this.setEdgeFrom(hometown, destination, inputedEdge);
        }
    }

    /**
     * this method will find shortest path from every possible outcome that come
     * from adjacency list that already defined from input process. by using
     * assistance of expand method such, djikstra and checking.
     *
     * @param start initial position
     * @param destination destination position of city
     * @throws IOException if there are exception in IO process
     */
    public void shortestPath(int start, int destination) throws IOException {
        if (this.areRelated(start, destination)) {
            this.initialization();
            Travel currentCity = new Travel(start, start, 0, 0);
            priorityQueue = new PriorityQueue<Travel>();
            priorityQueue.add(currentCity);

            while (previousFor[destination] == -1 || currentCity.getDistance() <= minimumDistanceFor[destination]) {
                currentCity = priorityQueue.poll();
                if (this.isWorthedToExpanded(currentCity)) {
                    this.updateDjikstraData(currentCity);
                    this.advanceToAdjacency(currentCity);
                }
            }
            writer.write(Long.toString(minimumDistanceFor[destination]));
            writer.newLine();
            this.routeList = new LinkedList<String>();
            this.routeList.add(getEdgeFrom(destination, previousFor[destination]).getStreetName());
            destination = previousFor[destination];
            while (start != destination) {
                this.routeList.add(getEdgeFrom(destination, previousFor[destination]).getStreetName());
                destination = previousFor[destination];
            }
            tracedWithShortestPath = true;
        } else {
            writer.write("Tidak ada jalan dari Kota " + destination + " menuju Kota " + start + ".");
            writer.newLine();
            tracedWithShortestPath = false;
        }
    }

    public void printShortestPath() throws IOException {
        if (this.tracedWithShortestPath) {
            this.writer.write(this.routeList.toString());
            this.writer.newLine();
        }
    }

    public void finish() throws IOException {
        this.writer.flush();
        this.writer.close();
    }

    public void graphGrouping() {
        for (int currentCity = 0; currentCity < NUMBER_OF_CITY; currentCity++) {
            if (this.groupTypeFor[currentCity] == Graph.UNTRACED) {
                traceBreadthFirstSearch(currentCity);
                this.groupType++;
            }
        }
    }

    public void printConnentionStatusBetween(int cityA, int cityB) throws IOException {
        if (this.areRelated(cityA, cityB)) {
            writer.write("Kota " + cityA + " dan Kota " + cityB + " terhubung.");
        } else {
            writer.write("Kota " + cityA + " dan Kota " + cityB + " tidak terhubung.");
        }
        writer.newLine();
    }

    public boolean areRelated(int cityA, int cityB) {
        return this.groupTypeFor[cityA] == this.groupTypeFor[cityB];
    }

    public String getStreetNameFrom(int homewtown, int destination) {
        return this.getEdgeFrom(homewtown, destination).getStreetName();
    }
}
