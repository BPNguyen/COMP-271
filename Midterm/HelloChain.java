package Midterm;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * M1 - Midterm Exam
 * HelloChain
 */

/** Import modules */
import java.util.Random;

public class HelloChain {
    
    /** Random instance */
    Random rand = new Random();

    /** String of instructor's initials */
    String instructor = "LI";

    /** String array of class roster */
    String[] roster = new String[] {
        "AA", "AZ",
        "BH", "BN",
        "CS", "CC",
        "FW",
        "IA",
        "JH", "JC",
        "KC", "KH", "KV",
        "LG", "LH",
        "MS", "MP",
        "NF",
        "RB", "RJ", "RX",
        "SP", "SS", "SG",
        "TS",
        "VR",
        "WP",
        "ZN",
        "ZA"
    };

    /** Probability of error occuring */
    double probability;

    /** Error count */
    int errorCount;

    /** Default constructor */
    public HelloChain() {
        probability = 0.25;
    }

    /** Constructor taking user-defined probability of error occuring */
    public HelloChain(double newProbability) {
        probability = newProbability;
    }

    /** "error" method
     * 
     * Generate a random error in hello chain.
     * @param deliquent Initials of person making an error
    */
    public void error(String delinquent) {

        // Randomly choose an error
        switch (rand.nextInt(6)) {
            case 0:
                System.out.println("ERROR: " + delinquent + "'s audio is muffled.\n" 
                                    + " ".repeat(7) + delinquent + " clears up their audio.");
                break;
            case 1:
                System.out.println("ERROR: " + delinquent + "'s microphone is muted.\n" 
                                    + " ".repeat(7) + delinquent + " unmutes their microphone.");
                break;
            case 2:
                System.out.println("ERROR: " + delinquent + "'s camera is turned off.\n" 
                                    + " ".repeat(7) + delinquent + " turns their camera on.");
                break;
            case 3:
                System.out.println("ERROR: " + delinquent + " isn't paying attention.\n" 
                                    + " ".repeat(7) + delinquent + " realizes their name has been called.");
                break;
            case 4:
                System.out.println("ERROR: " + delinquent + " accidentally repeats a name already called.\n" 
                                    + " ".repeat(7) + delinquent + " realizes their mistake.");
                break;
            default:
                System.out.println("ERROR: " + delinquent + " greets " + instructor + ", but the hello chain is not done yet.\n" 
                                    + " ".repeat(7) + delinquent + " is scolded by " + instructor + ".");
                break;
        }
    }

    /** "greeting" method
     * 
     * Generate a random greeting in hello chain.
    */
    public String greeting() {

        // Randomly choose a greeting
        switch (rand.nextInt(5)) {
            case 0:
                return "Hi";
            case 1:
                return "Howdy";
            case 2:
                return "Salutations";
            case 3:
                return "Good afternoon";
            default:
                return "Hello";
        }
    }

    /** "simulator" method
     * 
     * Simulates the hello chain.
     */
    public void simulator() {

        // Display header
        System.out.println("HELLO CHAIN SIMULATOR");
        System.out.println("-".repeat(50) + "\n");
        System.out.println("\t* START SIMULATOR *\n");

        // Number of students greeted
        int greeted = 0;

        // Set greeter to instructor
        String greeter = instructor;

        // Main loop
        while (greeted != roster.length) {

            // Choose next random student from remaining roster
            int randomStudent = rand.nextInt(roster.length);

            // If student at index of "nextStudent" hasn't been greeted...
            if (roster[randomStudent] != null) {

                // If greeter error occurs...
                if ((rand.nextDouble() < probability) && (greeter != instructor)) {

                    // Generate and display an error for greeter
                    error(greeter);

                    // Increase error count by 1
                    errorCount++;

                    // Display blank line
                    System.out.println();
                }

                // Display greeting (Leo never greets with anything but "Hello")
                if (greeter.compareTo(instructor) == 0) {
                    System.out.println(greeter + " says, \"Hello, " + roster[randomStudent] + ".\"");
                } else {
                    System.out.println(greeter + " says, \"" + greeting() + ", " + roster[randomStudent] + ".\"");
                }

                // Set current greeter to next greeter
                greeter = roster[randomStudent];

                // Set surrent 
                roster[randomStudent] = null;

                // Increase number of students greeted by 1
                greeted++;

                // Display greeting stats
                System.out.println("[ " + greeted + "/" + roster.length + " students greeted. ]\n");
            }
        }

        // Display final greeting
        System.out.println(greeter + " says, \"" + greeting() + ", " + instructor + ".\"");
        System.out.println(instructor + " says, \"... and hello to everyone!\"");

        // Check for completion
        for (String student : roster) {

            // If not all students have been greeted...
            if (student != null) {

                // Display incompletion message
                System.out.println("ERROR: Not all students have been greeted!");
            }
        }

        // Display completion message
        System.out.println("\nHello chain completed!");

        // DIsplay error stats
        System.out.println("[ " + errorCount + " total errors occured. ]\n");

        // Display ending banner
        System.out.println("\t* END SIMULATOR *");
    }

    /** Main method */
    public static void main(String[] args) {

        // Instance of HelloChain with a 25% probability of an error occuring
        HelloChain demo = new HelloChain(0.25);

        // Start simulator
        demo.simulator();
    }
}