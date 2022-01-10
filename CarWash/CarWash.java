package CarWash;

/*
* Brian Nguyen
* COMP 271 - 003, F20
* 05 - Queues
*/

// Import modules
import java.util.Random;

public class CarWash {
    
    // Class instances
    BBQ bbq;
    Random rand = new Random();

    // User-definable variables
    int washDuration;
    int simLength;

    // Car queue variables
    int carNum;
    int remWashDuration = 0;
    String carName;

    // Wait time variables
    int minWaitTime;
    int maxWaitTime;
    int totalWaitTime;
    int avgWaitTime;

    // Default status for occupancy
    boolean occupied = false;

    // Default status for car waiting next in queue
    boolean carWaiting = false;

    // Empty constructor
    public CarWash() {

        // Set capacity to 10 cars
        bbq = new BBQ(10);

        // Set wash duration to 5 minutes
        washDuration = 5;

        // Set simulation length to 20 minutes
        simLength = 24*60;
    }

    // Contructor taking user-defined capacity
    public CarWash(int newCapacity) {
        bbq = new BBQ(newCapacity);
        washDuration = 5;
        simLength = 24*60;
    }

    // Constructor taking user-defined capacity and wash duration
    public CarWash(int newCapacity, int newWashDuration) {
        bbq = new BBQ(newCapacity);
        washDuration = newWashDuration;
        simLength = 25*60;
    }

    // Constructor taking user-defined capacity, wash duration, and simulation length
    public CarWash(int newCapacity, int newWashDuration, int newSimLength) {
        bbq = new BBQ(newCapacity);
        washDuration = newWashDuration;
        simLength = newSimLength;
    }

    // 'carArrives' method
    // Set car arrival rate
    public boolean carArrives() {
        return rand.nextInt(4) > 2;
    }

    // 'calcAverageWaitTime' method
    // Calculates average wait time
    public int calcAverageWaitTime() {
        return totalWaitTime / carNum;
    }

    // 'displayWaitStats' method
    // Displays wait statistics of current car
    public void displayWaitStats() {

        // Display minimum wait time
        System.out.println("Minimum wait time: " + minWaitTime);

        // Display maximum wait time
        System.out.println("Maximum wait time: " + maxWaitTime);

        // Display average wait time
        System.out.println("Average wait time: " + avgWaitTime);
    }

    // 'displayWashProgress' method
    // Displays car wash's current progress
    public void displayWashProgress(int time) {

        // Display progress banner
        displayBanner("CAR WASH PROGRESS");

        // Display current time
        System.out.println("Current time: " + time);

        // Display remaining wash duration
        System.out.println("Remaining wash duration: " + remWashDuration);
    }

    // 'displayWashStatus' method
    // Displays car wash's current status
    public void displayWashStatus() {

        // Display status sub-banner
        System.out.println("   > Car wash status: ");

        // Display car wash occupancy
        System.out.println("Car wash occupied? " + occupied);

        // Display waiting car status
        System.out.println("Car waiting next? " + carWaiting);
    }

    // 'displayBanner' method
    // Displays event banner with defined heading
    public void displayBanner(String header) {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("\n\t>>> " + header + "\n");
    }

    // 'transferCar' method
    // Transfers waiting car into car wash
    public void transferCar() {

        // Display transfer banner
        displayBanner("TRANSFER WAITING CAR INTO WASH");

        // Remove next car in queue
        bbq.departure();

        // Car wash is now occupied
        occupied = true;

        // If no cars are in queue...
        if (bbq.getSize() == 0) {

            // There are no waiting cars
            carWaiting = false;
        }

        // Display car wash status
        displayWashStatus();

        // Set remaining wash duration
        remWashDuration = washDuration;

        // Display remaining wash duration
        System.out.println("\nRemaining wash duration: " + remWashDuration);

        // Display queue
        bbq.displayQ();
    }

    // 'simulator' method
    // Car wash simulation
    public void simulator() {

        // Display top border
        System.out.println("-".repeat(50) + "\n");

        // Display simulator banner
        System.out.println("\t\tCAR WASH SIMULATOR");

        // Time loop
        for (int time = 1; time <= simLength; time++) {

            // EVENT: Car arrives
            if (carArrives()) {

                // Increment number of cars by 1
                carNum++;

                // Set car name
                carName = "Car" + String.format("%05d", carNum);

                // Add car to queue
                if (bbq.arrival(carName)) {

                    // Display arrival banner
                    displayBanner("CAR ARRIVAL");

                    // Display timestamps
                    System.out.println("Time arrived: " + time);

                    // Display queue
                    bbq.displayQ();
                    System.out.println();

                    // Compute minimum wait time
                    if (!occupied) {
                        minWaitTime = 0;
                    } else {
                        if (carWaiting) {
                            minWaitTime = remWashDuration + (5 * (bbq.getSize() - 1));
                            totalWaitTime += minWaitTime;
                        } else {
                            minWaitTime = remWashDuration;
                            totalWaitTime += minWaitTime;
                        }
                    }

                    // Compute maximum wait time
                    if (!occupied) {
                        maxWaitTime = 0;
                    } else {
                        maxWaitTime = remWashDuration + (5 * bbq.getSize());
                    }

                    // Compute average wait time
                    avgWaitTime = calcAverageWaitTime();

                    // Display car wash status
                    displayWashStatus();
                    System.out.println();

                    // Display arriving car's wait statistics
                    System.out.println("   > " + carName + "'s wait statistics: ");
                    displayWaitStats();
                }

                // If car wash is not occupied...
                if (!occupied) {

                    // Transfer car into car wash
                    transferCar();
                }

                // If car wash is occupied and there is no car next in queue...
                else if (occupied && !carWaiting) {

                    // There is now a car waiting next in queue
                    carWaiting = true;
                }
            }

            // If car wash is currently occupied...
            if (occupied) {

                // Decrease remaining wash duration by 1
                remWashDuration--;

                // If current car is done being washed...
                if (remWashDuration == 0) {

                    // Display finished banner
                    displayBanner("CURRENT CAR DONE WASHING");

                    // Display departure time
                    System.out.println("Time departed: " + time);

                    // Car wash is now vacant
                    occupied = false;

                    // Display queue
                    bbq.displayQ();

                    // If there is a car waiting next in queue...
                    if (carWaiting) {

                        // Transfer car into car wash
                        transferCar();
                    }
                } else {
                    
                    // Display car wash progress
                    displayWashProgress(time);
                }
            }
        }

        // Display ending banner
        System.out.println("\n" + "-".repeat(50));
        System.out.println("\n\t\tEND OF SIMULATION");

        // Display note
        System.out.println("\nNOTE:\tThe following minimum and maximum wait\n\ttimes are for the last car remaining in\n\tthe queue at termination of the\n\tsimulation.\n");
        
        // Display final car's wait statistics
        displayWaitStats();

        // Display bottom border
        System.out.println("\n" + "-".repeat(50) + "\n");
    }

    // 'main' method
    public static void main(String[] args) {

        // Create CarWash instance with capacity of 6
        CarWash demo = new CarWash(6);

        // Start simulator
        demo.simulator();
    }
}