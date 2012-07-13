/*
 * SDA1011.java
 *
 * This document belongs to Andry Luthfi. many thanks for some of my companions
 * whose taught me about data structure applied to this problem and many lesson
 * taught by my superior.
 *
 * This document containt :
 *  - Patient.class
 *  - QueueOfficer.class
 *  - SubQueueOfficer.class
 *  - Time.class
 *
 * Thanks to :
 *      - Muhammad Andri Ihsannudin,
 *      - Lukmanul Hakim
 *
 * Version:
 *  - 1.0 (18-September-2010):
 *      - download assignment's description and apply the template
 *      - analysis testcases which are given in assignment's description
 *  - 1.0.1 (19-September-2010):
 *      - create Patient Class which contain every attribute needed
 *      - create Time Class in order to have ADT concept
 *      - prefering to use BufferedReader than Scanner
 *  - 1.1 (21-September-2010):
 *      - create QueueOfficer Class which implements Comparator
 *      - create SubQueueOfficer Class which implements Comparator
 *  - 1.2 (24-September-2010):
 *      - code getter and mutator methods in Patient Class and Time Class
 *  - 1.3 (26-September-2010):
 *      - complete and implements algorithm in source code
 *      - debug for every mislead input and output
 *      - done in different list problem
 *
 *
 * Copyright © 2010 Andry Luthfi, 0906629044
 */

package dsa2010;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * SDA1011.class
 *
 * this class help the hospital with manage system for patients whose came to
 * the hospital. every patient whose came will be treated by its priority.
 * and this class will hire QueueOfficer and SubQueueOfficer to help manage the
 * lists.
 *
 * @author Andry Luthfi (0906629044)
 * @version 1.3 (26-September-2010)
 */
public class SDA1011 {

    /** Maximum Queue Capacity */
    public static final int MAXIMUM_QUEUE_CAPACITY = 50000;
    /** Maximum Urgent Waiting Time */
    public static final int MAXIMUM_URGENT_WAITING_TIME = 300;
    /** Maximum Usual Waiting Time */
    public static final int MAXIMUM_USUAL_WAITING_TIME = 30000;
    /** Constant for patient whose got Two Minute treament */
    public static final int MINUTE_TWO = 120;
    /** Constant for patient whose got Three Minute treament */
    public static final int MINUTE_THREE = 180;

    /**
     * this main class is working as hospital, manage for every patient whose
     * came. manage every officer which hired to help manage the patient
     * priority.
     *
     * @param arguments (nothing)
     */
    public static void main(String[] arguments) {
        // create two list for patients. the first one is the list for each
        // patients whose capable to be treated in defined limit waiting time
        // and running out of time for the other list
        PriorityQueue<Patient> queueList =
                new PriorityQueue<Patient>(SDA1011.MAXIMUM_QUEUE_CAPACITY,
                new QueueOfficer());
        PriorityQueue<Patient> waitingList =
                new PriorityQueue<Patient>(SDA1011.MAXIMUM_QUEUE_CAPACITY,
                new SubQueueOfficer());

        // intialize hospital's time and patient's data
        Time time = null;
        String patientData = null;
        BufferedReader input = null;

        try {
            input = new BufferedReader(new InputStreamReader(System.in));
            time = null;
            patientData = input.readLine();
        } catch (IOException ioException) {
        }
        // if data is empty or the hospital is never visited by any patients
        if (patientData.equals("AKHIR DATA")) {
            System.out.println("KOSONG\n\nKOSONG");
            return;
        }
        // pick data and process while exists
        while (!patientData.equals("AKHIR DATA")) {
            // create temporer patient from data exist.
            Patient servedPatient = new Patient(patientData);
            if (time == null) {
                time = new Time(servedPatient.getArrivalTime());
            }
            // if patient less than or same with hospital's time
            // or still in interval time where there's patient treated
            if (servedPatient.getArrivalTimes().compareTo(time) <= 0) {
                queueList.add(servedPatient);
            } else // if not, then try to process patients priority in first list
            {
                while (!queueList.isEmpty()
                        && time.compareTo(servedPatient.getArrivalTimes()) < 0) {   // pick first priority in list
                    Patient out = queueList.poll();

                    if (time.compareTo(out.getArrivalTimes())
                            > out.getLimitTime()) // if couldn't survive
                    {
                        waitingList.add(out);
                    } else // if still survive then noted to be well treated
                    {
                        System.out.println(time + "" + out);
                        time.addValue(out.getTreatmentType());
                    }
                }
                // if time of the patient's greater than hospital's time
                if (time.compareTo(servedPatient.getArrivalTimes()) < 0) {   // reset the hospital's time with patient's recet arrival
                    // time
                    time.setValue(servedPatient.getArrivalTimes().getValue());
                }
                queueList.add(servedPatient);
            }

            try {   // pick another data which posibbly to be process
                patientData = input.readLine();
            } catch (IOException ioException) {
            }
        }

        // try to print the rest of the list.
        while (!queueList.isEmpty()) {   // pick patient in queue
            Patient out = queueList.poll();
            if (time.compareTo(out.getArrivalTimes()) > out.getLimitTime()) {   // if the patient is unsurvive then put in Second List
                waitingList.add(out);
            } else {   // if survive then process and noted
                System.out.println(time + "" + out);
                time.addValue(out.getTreatmentType());
            }
        }
        // create space between the lists
        System.out.println();
        // if second list is empty
        if (waitingList.isEmpty()) {   // then there isn't something to print but "KOSONG" sign
            System.out.println("KOSONG");
        }
        // print for the whole second list if there's data exist
        while (!waitingList.isEmpty()) {
            System.out.println(waitingList.poll());
        }
    }
}

/**
 * QueueOfficer.class
 *
 * This class is help to manage the patients priority in First List. the prior
 * is listed as :
 *  - Urgent Status,
 *  - Arrival Times,
 *  - Age.
 * the officer automaticlly compare every patients which located in List where
 * same officer was located.
 *
 * @author Andry Luthfi (0906629044)
 * @version 1.3 (26-September-2010)
 */
class QueueOfficer implements Comparator<Patient> {

    /** Adult Age Enumaration */
    private static final int AGE_ADULT = 5;
    /** Teenager Age Enumaration */
    private static final int AGE_TEENAGER = 4;
    /** Oldman Age Enumaration */
    private static final int AGE_OLDMAN = 3;
    /** Child Age Enumaration */
    private static final int AGE_CHILD = 2;
    /** Toddler Age Enumaration */
    private static final int AGE_TODDLER = 1;
    /** Symbol for same priority situation */
    public static final int SAME = 0;
    /** Symbol for less than priority situation */
    public static final int LESS_THAN = -1;
    /** Symbol for greater priority situation */
    public static final int GREATER_THAN = 1;

    /**
     * This method will automaticlly compare between two patients. the first
     * priority for this comparing method is status of urgent then following
     * with arrival time priority. then following compare with age priority.
     *
     * Complexity : O(n)
     * this method has growth rate in linear
     *
     * @param patientA object of compared patients
     * @param patientB object of compared patients
     * @return the compare priotiry value
     */
    public int compare(Patient patientA, Patient patientB) {   // intial value, is same in intially
        int comparedValue = QueueOfficer.SAME;
        // if patient A is urgent and patient B is not
        if (patientA.isUrgent() && !patientB.isUrgent()) {   // the priority wil be given to patient A
            comparedValue = QueueOfficer.LESS_THAN;
        } // vice versa
        else if (!patientA.isUrgent() && patientB.isUrgent()) {   // the priority wil be given to patient B
            comparedValue = QueueOfficer.GREATER_THAN;
        } else {   // compare arrival time if both of urgent status is same
            comparedValue = patientA.getArrivalTimes().compareTo(
                    patientB.getArrivalTimes());

            // if still same, then compare both age priority
            if (comparedValue == QueueOfficer.SAME) {   // compare the age priority
                comparedValue = this.enumerateAges(patientA.getAge())
                        - this.enumerateAges(patientB.getAge());
            }
        } // return the age priority
        return comparedValue;
    }

    /**
     * this method help to enumerate age category into priority value which are
     * defined in this officer.
     *
     * @param ageCategory age category
     * @return value of age priority was given
     */
    public int enumerateAges(String ageCategory) {
        return ((ageCategory.equals("dewasa")) ? (QueueOfficer.AGE_ADULT)
                : ((ageCategory.equals("remaja")) ? (QueueOfficer.AGE_TEENAGER)
                : ((ageCategory.equals("lansia")) ? (QueueOfficer.AGE_OLDMAN)
                : ((ageCategory.equals("anak-anak")) ? (QueueOfficer.AGE_CHILD)
                : (QueueOfficer.AGE_TODDLER)))));
    }
}

/**
 * SubQueueOfficer.class
 *
 * This class is help to manage the patients priority in Second List. the prior
 * is listed as :
 *  - Urgent Status,
 *  - Age,
 *  - Name.
 * the sub-officer automaticlly compare every patients which located in List
 * where same officer was located.
 *
 * @author Andry Luthfi (0906629044)
 * @version 1.3 (26-September-2010)
 */
class SubQueueOfficer extends QueueOfficer implements Comparator<Patient> {

    /** Oldman Age Enumaration */
    private static final int AGE_OLDMAN = 5;
    /** Adult Age Enumaration */
    private static final int AGE_ADULT = 4;
    /** Teenager Age Enumaration */
    private static final int AGE_TEENAGER = 3;
    /** Child Age Enumaration */
    private static final int AGE_CHILD = 2;
    /** Toddler Age Enumaration */
    private static final int AGE_TODDLER = 1;

    /**
     * This method override the super class method in order to redesign the
     * algorithm for different compare purpose in Second List.
     *
     * This method will automaticlly compare between two patients. the first
     * priority for this comparing method is status of urgent then following
     * with arrival time priority. then following compare with age priority.
     *
     * Complexity : O(n)
     * this method has growth rate in linear
     *
     * @param patientA object of compared patients
     * @param patientB object of compared patients
     * @return the compare priotiry value
     */
    @Override
    public int compare(Patient patientA, Patient patientB) {   // intial value, is same in intially
        int comparedValue = QueueOfficer.SAME;
        // if patient A is urgent and patient B is not
        if (patientA.isUrgent() && !patientB.isUrgent()) {   // the priority wil be given to patient A
            comparedValue = QueueOfficer.LESS_THAN;
        } // vice versa
        else if (!patientA.isUrgent() && patientB.isUrgent()) {   // the priority wil be given to patient B
            comparedValue = QueueOfficer.GREATER_THAN;
        } else // if still same then ..
        {   // compare with age priority by subtracting both age prior
            comparedValue = this.enumerateAges(patientA.getAge())
                    - this.enumerateAges(patientB.getAge());
            if (comparedValue == QueueOfficer.SAME) // if still same
            {   // compare with name priority
                comparedValue = patientA.getName().compareTo(patientB.getName());
            }
        }
        return comparedValue;
    }

    /**
     * this method help to enumerate age category into priority value which are
     * defined in this officer.
     *
     * @param ageCategory age category
     * @return value of age priority was given
     */
    @Override
    public int enumerateAges(String ageCategory) {
        return ((ageCategory.equals("dewasa")) ? (SubQueueOfficer.AGE_ADULT)
                : ((ageCategory.equals("remaja")) ? (SubQueueOfficer.AGE_TEENAGER)
                : ((ageCategory.equals("lansia")) ? (SubQueueOfficer.AGE_OLDMAN)
                : ((ageCategory.equals("anak-anak")) ? (SubQueueOfficer.AGE_CHILD)
                : (SubQueueOfficer.AGE_TODDLER)))));
    }
}

/**
 * Patient.class
 *
 * This class will contains for every data was collected in data given. Datas
 * needed to be compared with another patient in order to define the prior
 * amongst the patients whose queueing.
 *
 * @author Andry Luthfi (0906629044)
 * @version 1.3 (26-September-2010)
 */
class Patient {

    /** Patient's name */
    private String name;
    /** Patient's age */
    private String age;
    /** Patient's urgent status */
    private boolean isUrgent;
    /** Patient's arrival times */
    private Time arrivalTimes;
    /** Patien's treatment type */
    public int treatmentType;
    /** Patient's limit time */
    public int limitTime;
    /** Directory data for Patient's name */
    public static final int DATA_NAME = 0;
    /** Directory data for Patient's age */
    public static final int DATA_AGE = 3;
    /** Directory data for Patient's urgent status */
    public static final int DATA_URGENT_STATUS = 1;
    /** Directory data for Patient's arrival times */
    public static final int DATA_ARRIVAL_TIMES = 2;

    /**
     * this constructor method is receive raw data which will be process into
     * seperate directories. and will initialize every attribute needed in one
     * patient
     *
     * @param rawData raw data (CSV format : [name],[status],[time],[age])
     */
    public Patient(String rawData) {
        String[] temporerData = rawData.split(",");
        this.setName(temporerData[Patient.DATA_NAME]);
        this.setIsUrgent(temporerData[Patient.DATA_URGENT_STATUS].equals("ya"));
        this.toCategorized(temporerData[Patient.DATA_AGE]);
        this.arrivalTimes = new Time(temporerData[Patient.DATA_ARRIVAL_TIMES]);
        this.limitTime = (this.isUrgent) ? SDA1011.MAXIMUM_URGENT_WAITING_TIME
                : SDA1011.MAXIMUM_USUAL_WAITING_TIME;
        this.treatmentType = (this.isUrgent) ? SDA1011.MINUTE_TWO
                : (age.equals("balita") || age.equals("anak-anak")
                ? SDA1011.MINUTE_THREE : SDA1011.MINUTE_TWO);

    }

    /**
     * this method will categorized patient's age by its data spesification and
     * rules applied
     *
     * @param spesification data spesification
     */
    private void toCategorized(String spesification) {   // direktory for lower bound in age spesification
        final int AGE_MIN = 0;
        if (spesification.contains(">")) {   // categorized into oldman
            this.age = "lansia";
        } else if (spesification.contains("<")) {   // cagetorized into toddler
            this.age = "balita";
        } else if (spesification.contains("-")) {   // categorized into patients lower bound of age
            String[] data = spesification.split("-");
            this.age = this.checkAge(Integer.parseInt(data[AGE_MIN]));
        } else {   // categorized into its age nominal
            this.age = this.checkAge(Integer.parseInt(spesification));
        }
    }

    /**
     * this method will help the patient categorized him/herself into age
     * category or priority applied in this hospital
     *
     * @param age patient's age
     * @return age category
     */
    private String checkAge(int age) {
        return ((age < 5) ? ("balita")
                : ((age < 13) ? ("anak-anak")
                : ((age < 20) ? ("remaja")
                : ((age < 65) ? ("dewasa") : ("lansia")))));
    }

    /**
     * just assistance method
     *
     * @param clock
     */
    private void accumulateSeconds(String clock) {
        this.setArrivalTime(clock);
    }

    // Accessor Method(s)
    // all of accessor method in Patient.class in order to pass the value
    /**
     * Patient's treatment type accessor
     * @return treatment type
     */
    public int getTreatmentType() {
        return treatmentType;
    }

    /**
     * Patient's arrival times accessor
     * @return arrival times
     */
    public Time getArrivalTimes() {
        return arrivalTimes;
    }

    /**
     * Patient's name accessor
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Patient's limit time accessor
     * @return name
     */
    public int getLimitTime() {
        return limitTime;
    }

    /**
     * Patient's age accessor
     * @return age
     */
    public String getAge() {
        return age;
    }

    /**
     * Patient's urgent status accessor
     * @return urgent status
     */
    public boolean isUrgent() {
        return isUrgent;
    }

    /**
     * Patient's arrival time accessor
     * needed to be processed before the value has passed into another object
     * @return arrival time status
     */
    public String getArrivalTime() {
        int value = this.arrivalTimes.getValue();
        String hour = (short) (value / 3600) + "";
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        value -= Integer.parseInt(hour) * 3600;
        String minute = (short) (value / 60) + "";
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        value -= Integer.parseInt(minute) * 60;
        String second = (short) value + "";
        if (second.length() == 1) {
            second = "0" + second;
        }
        return hour + ":" + minute + ":" + second;
    }

    // Mutator Method(s)
    // all of mutator method in Patient.class in order to mutate the value
    /**
     * Patient's name mutator
     * @param name patient's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Patient's age mutator
     * @param age patient's age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Patient's arrival time mutator
     * @param arrivalTime patient's arrival time
     */
    public void setArrivalTime(String arrivalTimes) {
        this.arrivalTimes = new Time(arrivalTimes);
    }

    /**
     * Patient's urgent status mutator
     * @param isUrgent patient's urgent status
     */
    public void setIsUrgent(boolean isUrgent) {
        this.isUrgent = isUrgent;
    }

    /**
     * Patient's treatment type mutator
     * @param treatmentType patient's treatment type
     */
    public void setTreatmentType(int treatmentType) {
        this.treatmentType = treatmentType;
    }

    /**
     * Patient's arrival time mutator
     * @param arrivalTime patient's arrival time
     */
    public void setArrivalTimes(Time arrivalTimes) {
        this.arrivalTimes = arrivalTimes;
    }

    /**
     * Patient's limit time mutator
     * @param limitTime patient's limit time
     */
    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }

    /**
     * this method will give string about patient's object description.
     * contains patient's name, age, and urgent status.
     *
     * @return patient's description
     */
    @Override
    public String toString() {
        return getName() + " (" + getAge() + "): " + (this.isUrgent() ? "ya" : "tidak");
    }
}

/**
 * Time.class
 *
 * this class help with manage the time of the hospital. in order to maintain
 * the patient treatment priority.
 *
 * @author Andry Luthfi
 * @version 1.3 (26-September-2010)
 */
class Time implements Comparable<Time> {

    /** Maximum seconds a day */
    private static final int MAXIMUM_DAY_TIME = 24 * 60 * 60;
    /** time description or time for mat using (hh:mm:ss) */
    private String description;
    /** time value in integer, in order to comparing purposes */
    private int value;
    /** day enumeration */
    private int day;

    /**
     * this constructor method is to build time object
     * @param description time format
     */
    public Time(String description) {
        this.description = description;
        this.value = this.parseValue(description);
    }

    /**
     * this method help to parse the time format into value in integer
     *
     * @param description time format
     * @return integer value of time
     */
    private int parseValue(String description) {
        String[] partialDescription = description.split(":");
        return Integer.parseInt(partialDescription[0]) * 3600
                + Integer.parseInt(partialDescription[1]) * 60
                + Integer.parseInt(partialDescription[2]);
    }

    /**
     * compare to another time object
     *
     * @param time another time object
     * @return compared value
     */
    public int compareTo(Time time) {
        return this.value - time.getValue();
    }

    /**
     * time's description accessor
     * @return time's description
     */
    public String getDescription() {

        return description;
    }

    /**
     * time's value accessor
     * @return time's value
     */
    public int getValue() {
        return value;
    }

    /**
     * time's description mutator
     * @param description time format
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * time's value mutator
     * before set into time object, the format needed to be reformat with
     * new value given.
     *
     * @param value time's value
     */
    public void setValue(int value) {
        this.value = (value);

        if (value >= this.MAXIMUM_DAY_TIME) {
            value -= MAXIMUM_DAY_TIME;
        }

        this.description = "";

        for (int ii = 0; ii < 3; ii++) {
            String temp = "";
            if (value % 60 < 10) {
                temp = "0";
            }
            temp += value % 60;
            description = temp + description;
            value /= 60;

            if (ii < 2) {
                description = ":" + description;
            }
        }
    }

    /**
     * this method is help to add the value of the time
     * @param value additional value
     */
    public void addValue(int value) {
        this.setValue(this.value + value);
    }

    /**
     * time description to the object, in order to give information about object
     * selected
     * @return object time's description
     */
    public String toString() {
        return ((value < Time.MAXIMUM_DAY_TIME) ? "Hari ini (" : "Besok (")
                + this.getDescription()
                + ") > ";
    }
}
