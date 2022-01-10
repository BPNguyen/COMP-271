/*
* Brian Nguyen
* COMP 271 - 003, F20
* 01 - Warmup Assignment
*/

package AirlineSeating;

public class Seating {

    // 'generatePassengers' method
    // Generates and populates passenger array
    public static String[] generatePassengers(int totalPassengers, String[] passengers) {

        // RealisticNameGenerator instance
        RealisticNameGenerator rng = new RealisticNameGenerator();

        for (int i = 0; i < totalPassengers; i++) {

            // Local array to store output from method realisticName()
            String[] name = new String[2];

            // Obtain a pair of realistic first and last names
            name = rng.realisticName();

            // Combine names together into the 'passengers', using '*' as delimiter
            passengers[i] = name[0] + "*" + name[1];
        }

        // Return 'passengers'
        return passengers;
    }

    // 'calcLongestName' method
    // Calculates the longest name in an array and returns its length
    public static int calcLongestName(String[] arr, int num) {

        // Placeholder variable 'longestName'
        int longestName = 0;

        // Compare each object length in array with current 'longestName', then assigns the larger value to 'longestName'
        for (int i = 0; i < num; i++) {

            if (arr[i].length() > longestName) {

                longestName = arr[i].length();
            }
        }

        // Return 'longestName'
        return longestName;
    }

    // 'compareInts' method
    // Compares two integers and returns the value of the larger integer
    public static int compareInts(int x, int y) {

        // Placeholder variable 'largerInt'
        int largerInt;

        // Compares integers and assigns larger value to 'largerInt'
        if (x >= y) {

            largerInt = x;
        } else {

            largerInt = y;
        }

        // Return 'largerInt'
        return largerInt;
    }

    // 'assignSeats' method
    // Populates array with seats
    public static String[] assignSeats(String[] arr, int num) {

        // Placeholder variables 'row' and 'column'
        int row, column;
        
        // Calculate 'row' and 'column' values, then populates array with generated seat assignments
        for (int i = 0; i < num; i++) {

            // Calculate row and column numbers
            row = (i / 4) + 1;
            column = i % 4;

            // Populate array with generated seat assignment
            arr[i] = String.valueOf(row) + (char)(65 + column);
        }
        
        // Return array
        return arr;
    }

    // 'calcPadding' method
    // Calculates amount of 'padding' needed for passenger manifest data segment
    public static int calcPadding(int longestName, int segmentLength) {

        // Calculate padding value
        int padding = longestName - segmentLength;

        // Return 'padding'
        return padding;
    }

    // 'generateManifest' method
    // Generates passenger manifest output
    public static void generateManifest(int totalPassengers, String[] seats, String[] fName, String[] lName) {

        // Calculate longest first name
        int longestFname = calcLongestName(fName, totalPassengers);

        // Calculate longest last name
        int longestLname = calcLongestName(lName, totalPassengers);

        // Compare longest first and last name
        int longestName = compareInts(longestFname, longestLname);

        // Calculate number of dashes required for bordering
        int numDashes = longestName + 2 + 2 - 2;

        // Generate manifest output
        for (int i = 0; i < totalPassengers; i += 4) {

            // Display top border of manifest output
            System.out.println("+" + "-".repeat(numDashes) 
                            + "+" + "-".repeat(numDashes) + "+    "
                            + "+" + "-".repeat(numDashes)
                            + "+" + "-".repeat(numDashes) + "+");
            
            // Calculate padding needed for each column's data
            int padC1Seat = calcPadding(longestName, seats[i].length());
            int padC1Fname = calcPadding(longestName, fName[i].length());
            int padC1Lname = calcPadding(longestName, lName[i].length());

            int padC2Seat = calcPadding(longestName, seats[i + 1].length());
            int padC2Fname = calcPadding(longestName, fName[i + 1].length());
            int padC2Lname = calcPadding(longestName, lName[i + 1].length());

            int padC3Seat = calcPadding(longestName, seats[i + 2].length());
            int padC3Fname = calcPadding(longestName, fName[i + 2].length());
            int padC3Lname = calcPadding(longestName, lName[i + 2].length());

            int padC4Seat = calcPadding(longestName, seats[i + 3].length());
            int padC4Fname = calcPadding(longestName, fName[i + 3].length());
            int padC4Lname = calcPadding(longestName, lName[i + 3].length());

            // Display formatted manifest data
            System.out.println("| " + seats[i] + " ".repeat(padC1Seat) + " | " 
                                + seats[i + 1] + " ".repeat(padC2Seat) + " |    "
                                + "| " + seats[i + 2] + " ".repeat(padC3Seat) + " | "
                                + seats[i + 3] + " ".repeat(padC4Seat) + " |");

            System.out.println("| " + fName[i] + " ".repeat(padC1Fname) + " | " 
                                + fName[i + 1] + " ".repeat(padC2Fname) + " |    "
                                + "| " + fName[i + 2] + " ".repeat(padC3Fname) + " | "
                                + fName[i + 3] + " ".repeat(padC4Fname) + " |");

            System.out.println("| " + lName[i] + " ".repeat(padC1Lname) + " | "
                                + lName[i + 1] + " ".repeat(padC2Lname) + " |    "
                                + "| " + lName[i + 2] + " ".repeat(padC3Lname) + " | "
                                + lName[i + 3] + " ".repeat(padC4Lname) + " |");
        }

        // Display bottom border of manifest output
        System.out.println("+" + "-".repeat(numDashes) 
                            + "+" + "-".repeat(numDashes) + "+    "
                            + "+" + "-".repeat(numDashes)
                            + "+" + "-".repeat(numDashes) + "+");
    }

    // Main method
    public static void main(String[] args) {

        // Total # of passengers on airplane
        int totalPassengers = 32;

        // Passenger manifest data arrays
        String[] passengers = new String[totalPassengers];
        String[] fName = new String[totalPassengers];
        String[] lName = new String[totalPassengers];

        // Generate passenger list
        passengers = generatePassengers(totalPassengers, passengers);

        // Separate and assign first names and last names to respective arrays
        for (int i = 0; i < totalPassengers; i++) {

            fName[i] = passengers[i].substring(0, passengers[i].lastIndexOf("*"));
            lName[i] = passengers[i].substring(passengers[i].lastIndexOf("*") + 1);
        }

        // Create seat assignments
        String[] seats = new String[totalPassengers];
        seats = assignSeats(seats, totalPassengers);

        // Generate passenger manifest
        generateManifest(totalPassengers, seats, fName, lName);
    }
}