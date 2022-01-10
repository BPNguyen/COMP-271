package Midterm;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * M1 - Midterm Exam
 * QBB
 */

public class QBB implements Q {

    /** Number of objects the queue can hold */
    private int capacity;

    /** Number of objects currently in the queue */
    private int size;

    /** String array "q" */
    private String[] q;

    /** Index of last object in queue */
    private int b;

    /** Index of first object in queue */
    private int f;

    /**
     * Default constructor
     */
    public QBB() {
        capacity = 5;
        size = 0;
        q = new String[capacity];
        f = b = 0;
    }

    /**
     * Constructor taking user-defined capacity
     */
    public QBB(int capacity) {
        this.capacity = capacity;
        size = 0;
        q = new String[capacity];
        f = b = 0;
    }

    /**
     * "getSize" method
     * 
     * Accessor for "size"
     */
    public int getSize() {
        return size;
    }

    /**
     * "getCapacity" method
     * 
     * Accessor for "capacity"
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * "getB" method
     * 
     * Accessor for "b"
     */
    public int getB() {
        return b;
    }

    /**
     * "getF" method
     * 
     * Accessor for "f"
     */
    public int getF() { 
        return f;
    }

    /**
     * "arrival" method
     * 
     * Adds new object to "q", if there is space.
     * Returns true if object is successfully added, returns false if
     * "q"" is full.
     * @param s Object to be added to "q"
     * @return true if added to "q" is successful; false if "q" is full.
     */
    public boolean arrival(String s) {

        // "successfulArrival" key
        boolean successfulArrival = false;

        // Check if there is space in "q"
        if (size < capacity) {

            // Add "s" to back of "q"
            q[b] = s;

            // Increase index of last object in "q"
            b++;

            // Increase size of "q"
            size++;

            // "successfulArrival" key = true
            successfulArrival = true;
        }

        // Return "successfulArrival" key
        return successfulArrival;
    }

    /**
     * "departure" method
     * 
     * Removes the last object added from "q".
     * @return true if removal is successful; false if "q" is already empty.
     */
    public boolean departure() {

        // "successfulDeparture" key
        boolean successfulDeparture = false;

        // Check if "q" is empty
        if (size > 0) {

            // Set last object in "q" to null
            q[b - 1] = null;

            // Decrease index of last object in "q"
            b--;

            // Decrease size of "q"
            size--;

            // "successDeparture" key = true
            successfulDeparture = true;
        }

        // Return "successDeparture" key
        return successfulDeparture;
    }

    /**
     * "displayQ" method
     * 
     * Displays contents of "q".
     */
    public void displayQ() {
        
        // Display header
        System.out.println("\nQueue status");
        
        // Display current details of "q"
        System.out.printf("Capacity %d, size %d, back at [%d], front at [%d]: \n", q.length, size, b, f);

        // Display contents of "q", including available spaces
        for (int i = 0; i < q.length; i++) {
            String element = q[i] == null ? " [ ] " : " [ " + q[i] + " ] " ;
            System.out.print(element);
        }
        
        // Display empty line
        System.out.println();
    }

    /** Main method */
    public static void main(String[] args) {
        
        // Instance of QBB with capacity of 4
        QBB q = new QBB(4);

        // Display inital empty queue
        System.out.println("\t~ Initial creation of queue");
        q.displayQ();
        System.out.println();

        // Demonstration of arrivals
        System.out.println("-".repeat(50));
        System.out.println("\n\t~ Demonstration of arrivals");
        q.arrival("a");
        q.displayQ();
        q.arrival("b");
        q.arrival("c");
        q.arrival("d");
        q.arrival("e");
        q.arrival("f");
        q.arrival("g");
        q.displayQ();
        System.out.println();

        // Demonstration of departures
        System.out.println("-".repeat(50));
        System.out.println("\n\t~ Demonstration of departures");
        q.departure();
        q.displayQ();
        q.departure();
        q.displayQ();
    }
}