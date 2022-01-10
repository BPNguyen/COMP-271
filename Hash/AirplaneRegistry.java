package Hash;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * 07 - HashMaps
 */

/**
 * 
 * Problem 1:
 * 
 * Perhaps I would modify the "insert" method to take an additional Boolean argument 
 * (e.g. "boolean toBeRehashed") that indicates whether the general or overloaded 
 * "hashFunction" method should be utilized and whether or not the saturation level 
 * of the hash map should be checked. In the "insert" method, There would be an 
 * if-else-statement that utilizes the general "hashFunction" method (line 103 from 
 * original code) if "toBeRehashed" is false, otherwise the overloaded "hashFunction" 
 * method (line 133) would be utilized. Additionally, I would nest the saturation 
 * if-statement check (lines 109-111) inside an if-statement checking whether 
 * "toBeRehashed" is true or false, proceeding if the "toBeRehashed" is false.
 * 
 * - True would indicate that the insert method is being called from the rehash method, 
 * meaning it would only require the insert method's functionalities of inserting 
 * contents into a structure for the purpose of copying a hash map's contents to a 
 * newly-rehashed hash map. In this case, the overloaded "hashFunction" method would 
 * be used (line 133) and the saturation check would not be performed.
 * 
 * - False would indicate that the insert method is simple inserting additional data, 
 * meaning the saturation level check would be applicable in this instance. In this 
 * case, the general "hashFunction" method would be used (line 103) and the 
 * saturation check would be performed.
 * 
 * Additional modification inside the "rehash" method would also be necessary with 
 * this change. Lines 133-137 would be replaced with a call to the "insert" method.
 * 
 */

// Import modules
import java.util.Random;

public class AirplaneRegistry {
    
    // User-defined capacity for 'registry'
    private static final int DEFAULT_CAPACITY = 5;

    // Array containing key-value pairs
    private Airplane[] registry = new Airplane[DEFAULT_CAPACITY];

    // Number of values/models in registry
    private int size;

    // Number of tail numbers/keys in registry
    private int occupancy;

    // Inner class 'Airplane'
    class Airplane {

        // Key
        private String tailNumber;

        // Value
        private String model;

        // Pointer to next item in linked list
        private Airplane next;

        // Default constructor
        public Airplane(String tailNumber, String model) {

            this.tailNumber = tailNumber;
            this.model = model;
            this.next = null;
        }

        // Contructor implementing pointer
        public Airplane(String tailNumber, String model, Airplane next) {
            
            this.tailNumber = tailNumber;
            this.model = model;
            this.next = next;
        }
    }

    /** 
     * 'hashFunction' method
     * 
     * Hashes value using String class' 'hashCode' method multiplied by a prime number 
     * of choice (271), then divided by 'registry's length and returned as an absolute 
     * value to ensure that it is within bounds.
     *
     * @param value Value to be hashed
     * @return Map output
     */
    private int hashFunction(String value) {

        // Return hashed code value
        return Math.abs(/*COMP*/271 * value.hashCode() % registry.length);
    }

    /** 
     * 'contains' method
     * 
     * Checks if a tail number exists in the registry.
     *
     * @param tailNumber Tail number to be checked
     * @return True if tail number exists; False if not
     */
    public boolean contains(String tailNumber) {

        // Found key
        boolean found = false;

        // Index of key containing tail number
        int bucket = hashFunction(tailNumber);

        // Access said key
        Airplane current = registry[bucket];

        // While 'current' is not null...
        while (current != null) {

            // If the tail number exists in the registry...
            if (current.tailNumber.equals(tailNumber)) {

                // Set found key to true
                found = true;
            }

            // Access next key in registry
            current = current.next;
        }

        // Return found key
        return found;
    }

    /** 
     * 'generateTailNumber' method
     * 
     * Generates a tail number based on the airplane's model and random four-digit number
     *
     * @param model Given airplane model
     * @return Generated tail number
     */
    public String generateTailNumber(String model) {

        // Instance of 'Random'
        Random rand = new Random();

        // Generate tail number
        String tailNumber = "N" + (rand.nextInt(9000) + 1000) + model.charAt(0);

        // Return generated tail number
        return tailNumber;
    }

    /** 
     * 'insert' method
     * 
     * Checks if a tail number already exists in the registry. If so, a boolean value 
     * of false if returned. If not, the airplane is inserted into the registry by 
     * hashing its tail number and the registry's occupancy and size are also updated.
     *
     * @param tailNumber Given tail number
     * @param model Given airplane model
     * @return True if insertion was successful; False if not
     */
    public boolean insert(String tailNumber, String model) {

        // Success key
        boolean success = false;

        // If tail number does not exist in the registry...
        if (!contains(tailNumber)) {

            // Index of key containing tail number
            int bucket = hashFunction(tailNumber);

            // If key has not been accessed yet...
            if (registry[bucket] == null) {

                // Increase registry's occupancy
                occupancy++;
            }

            // LIFO insersion of airplane into registry
            registry[bucket] = new Airplane(tailNumber, model, registry[bucket]);

            // Update registry's size
            size++;

            // Set success key to true
            success = true;
        }
        
        // Return success key
        return success;
    }

    /**
     * 'generateReport' method
     * 
     * Generates a report of the airplane registry and its data
     */
    public void generateReport() {

        // Display header
        System.out.println("*" + "-".repeat(48) + "*");
        System.out.println("\n* AIRPLANE REGISTRY *");
        System.out.println("\n" + "-".repeat(50) + "\n");

        // Display registry statistics
        System.out.println("* STATISTICS *\n");
        System.out.println("Buckets: " + registry.length);
        System.out.println("Occupancy: " + occupancy);
        System.out.println("Saturation: " + (100 * ((double) occupancy) / ((double) registry.length)) + "%");
        System.out.println("Size: " + size);
        System.out.println("\n" + "-".repeat(50) + "\n");

        // Display contents of registry
        System.out.println("* ( Bucket # ): [ Tail # | Model ] *\n");
        for (int i = 0; i < registry.length; i++) {

            // Display index of key in registry
            System.out.print("( " + i + " ): \t");

            // If key is empty...
            if (registry[i] == null) {

                // Display null
                System.out.println("[ NULL ]\n");
            } else {

                // Access key in registry
                Airplane current = registry[i];

                // While key is not null...
                while (current != null) {

                    // Display airplane information
                    System.out.print("[ " + current.tailNumber + " | " + current.model + " ]\n\t");
                    
                    // Access next key in registry
                    current = current.next;
                }

                // Return line
                System.out.println();
            }
        }

        // Display bottom border
        System.out.println("*" + "-".repeat(48) + "*");
    }

    /** Main method */
    public static void main(String[] args) {

        // Instance of 'AirplaneRegistry'
        AirplaneRegistry ar = new AirplaneRegistry();

        // Various airplane models
        String[] models = {"Piper Archer", "Cessna Skyhawk", "Wright Flyer"};

        // Generate five unique tail numbers
        String[] generatedTailNumbers = {
            ar.generateTailNumber(models[0]),
            ar.generateTailNumber(models[1]),
            ar.generateTailNumber(models[0]),
            ar.generateTailNumber(models[1]),
            ar.generateTailNumber(models[2]),
        };

        // Populate airplane registry
        ar.insert(generatedTailNumbers[0], models[0]);
        ar.insert(generatedTailNumbers[1], models[1]);
        ar.insert(generatedTailNumbers[2], models[0]);
        ar.insert(generatedTailNumbers[3], models[1]);
        ar.insert(generatedTailNumbers[4], models[2]);

        // Attempt to insert an existing tail number
        ar.insert(generatedTailNumbers[2], "Looseleaf Glider");

        // Generate airplane registry report
        ar.generateReport();
    }
}