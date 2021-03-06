package CarWash;

/**
 * A simple queue class.
 * 
 * @author leo@cs.luc.edu
 */
public class BBQ implements Q {

    /** How many clients can the queue hold */
    private int capacity;

    /** How many clients are already in the queue */
    private int size;

    /** The million dollar question: underlying structure? */
    private String[] q; // let's go with an array

    /** Index position of the back of the q */
    private int b;

    /** Index position of the front of the q */
    private int f;

    /** Default constructor sets capacity to 5 and queue is empty */
    public BBQ() {
        capacity = 5;
        size = 0;
        q = new String[capacity];
        f = b = 0;
    } // default constructor CarWash

    /** Basic constructor */
    public BBQ(int capacity) {
        this.capacity = capacity;
        size = 0;
        q = new String[capacity];
        f = b = 0;
    }

    /** Accessor for size */
    public int getSize() {
        return size;
    }

    /** Accessor for capacity */
    public int getCapacity() {
        return capacity;
    }

    /** Accessor for the back of the queue */
    public int getB() { return b; }

    /** Accessor for the front of the queue */
    public int getF() { return f; }

    /**
     * Method to add a new arrival to the queue, if there is space.
     * If the addition is successful the method returns true. If the
     * queue is full, the method returns false.
     * @param s value to add to the queue
     * @return true is joining the queue successful; false if q full.
     */
    public boolean arrival(String s) {
        boolean successfulArrival = false;
        // Make sure there is room in the queue
        if ( size < capacity ) {
            // New arrival goes to the back of the q
            q[b] = s;
            // The back position is updated
            b++;
            // The size of the queue increases.
            size++;
            // And we flag the success!
            successfulArrival = true;
        }
        return  successfulArrival;
    } // method arrival

    /**
     * Method to remove element from q.
     * @return True if removal successful; false if q is empty already.
     */
    public boolean departure() {
        boolean successfulDeparture = false;
        // Make sure q is not empty
        if ( size > 0 ) {

            // Departures are always from the front of the q
            q[0] = null;

            /* Move everyone closer to the front; this is not computationally
            efficient as it runs in O(n) time; however ... for small queues
            with capacity < 30, we can sacrifice efficiency in favor of
            simplicity. Computationally efficient methods to add and remove
            elements from the queue are provided as well, at the bottom of
            this class:
              efficientArrival(String s), and
              efficientDeparture().
            at the end of the class.
             */
            for ( int i = 0; i < size-1; i++) {
                q[i] = q[i+1];
            }

            // Back of the q also moves forward
            b--;
            // And the corresponding position is cleared
            q[b] = null;
            // Update size
            size--;
            successfulDeparture = true;
        }
        return successfulDeparture;
    } // method departure

    public void displayQ() {
        System.out.println("\nQueue status");
        System.out.printf("Capacity %d, size %d, back at [%d], front at [%d]: \n", q.length, size, b, f);
        for (int i = 0; i < q.length; i++) {
            String element = q[i] == null ? " [ ] " : " [ " + q[i] + " ] " ;
            System.out.print(element);
        }
        System.out.println();
    } // method displayQ

    public void miniDisplayQ() {
        for (int i = 0; i < q.length; i++) {
            String element = q[i] == null ? " " : "#" ;
            System.out.print(element);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BBQ q = new BBQ(4);
        q.displayQ();
        q.arrival("a");
        q.displayQ();
        q.arrival("b");
        q.arrival("c");
        q.arrival("d");
        q.arrival("e");
        q.arrival("f");
        q.arrival("g");
        q.displayQ();
        q.departure();
        q.departure();
        q.displayQ();

        BBQ qq = new BBQ(4);
        qq.efficientArrival("A");
        qq.displayQ();
        qq.efficientArrival("B");
        qq.displayQ();
        qq.efficientArrival("C");
        qq.displayQ();
        qq.efficientArrival("D");
        qq.displayQ();
        qq.efficientArrival("E");
        qq.displayQ();
        qq.efficientDeparture();
        qq.displayQ();
        qq.efficientArrival("E");
        qq.displayQ();
        qq.efficientDeparture();
        qq.displayQ();
    }

    public boolean efficientArrival(String s) {
        boolean successfulArrival = false;
        if ( size < capacity ) {
            successfulArrival = true;
            size++;
            q[b] = s;
            b = (b+1) % capacity;
        }
        return successfulArrival;
    } // method efficientArrival

    public boolean efficientDeparture() {
        boolean successfulDeparture = false;
        if ( size > 0 ) {
            successfulDeparture = true;
            size--;
            q[f] = null;
            f = (f+1) % capacity;
        }
        return successfulDeparture;
    }
}