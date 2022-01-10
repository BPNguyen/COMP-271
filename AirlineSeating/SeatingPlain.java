package AirlineSeating;

public class SeatingPlain {

    public static String[] generatePassengers(int totalPassengers, String[] passengers) {

        RealisticNameGenerator rng = new RealisticNameGenerator();
        for (int i = 0; i < totalPassengers; i++) {
            String[] name = new String[2];
            name = rng.realisticName();
            passengers[i] = name[0] + "*" + name[1];
        }
        return passengers;
    }

    public static int calcLongestName(String[] arr, int num) {

        int longestName = 0;
        for (int i = 0; i < num; i++) {
            if (arr[i].length() > longestName) {
                longestName = arr[i].length();
            }
        }
        return longestName;
    }

    public static int compareInts(int x, int y) {

        int largerInt;
        if (x >= y) {
            largerInt = x;
        } else {
            largerInt = y;
        }
        return largerInt;
    }

    public static String[] assignSeats(String[] arr, int num) {

        int row, column;
        for (int i = 0; i < num; i++) {
            row = (i / 4) + 1;
            column = i % 4;
            arr[i] = String.valueOf(row) + (char)(65 + column);
        }
        return arr;
    }

    public static int calcPadding(int longestName, int segmentLength) {

        int padding = longestName - segmentLength;
        return padding;
    }

    public static void generateManifest(int totalPassengers, String[] seats, String[] fName, String[] lName) {

        int longestFname = calcLongestName(fName, totalPassengers);
        int longestLname = calcLongestName(lName, totalPassengers);
        int longestName = compareInts(longestFname, longestLname);
        int numDashes = longestName + 2 + 2 - 2;
        for (int i = 0; i < totalPassengers; i += 4) {
            System.out.println("+" + "-".repeat(numDashes) 
                            + "+" + "-".repeat(numDashes) + "+    "
                            + "+" + "-".repeat(numDashes)
                            + "+" + "-".repeat(numDashes) + "+");
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
        System.out.println("+" + "-".repeat(numDashes) 
                            + "+" + "-".repeat(numDashes) + "+    "
                            + "+" + "-".repeat(numDashes)
                            + "+" + "-".repeat(numDashes) + "+");
    }

    public static void main(String[] args) {

        int totalPassengers = 32;
        String[] passengers = new String[totalPassengers];
        String[] fName = new String[totalPassengers];
        String[] lName = new String[totalPassengers];
        passengers = generatePassengers(totalPassengers, passengers);
        for (int i = 0; i < totalPassengers; i++) {
            fName[i] = passengers[i].substring(0, passengers[i].lastIndexOf("*"));
            lName[i] = passengers[i].substring(passengers[i].lastIndexOf("*") + 1);
        }
        String[] seats = new String[totalPassengers];
        seats = assignSeats(seats, totalPassengers);
        generateManifest(totalPassengers, seats, fName, lName);
    }
}