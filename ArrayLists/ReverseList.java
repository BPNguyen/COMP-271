package ArrayLists;

/*
* Brian Nguyen
* COMP 271 - 003, F20
* (9/11/2020) Friday In-class Coding
*/

// Import modules
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// 'ReverseList' class
public class ReverseList {

    // 'reverseLineOrder' method
    // Returns new ArrayList with reversed line-order
    public ArrayList<String> reverseLineOrder(ArrayList<String> arr) {

        // Create new ArrayList 'arr2'
        ArrayList<String> arr2 = new ArrayList<String>();

        // Add lines from 'arr' into 'arr2' backwards
        for (int i = arr.size() - 1; i >= 0; i--) {

            // Split line into array of words
            String[] line = arr.get(i).split(" ");

            // String placeholder 'newLine'
            String newLine = "";

            // Reverse words in 'line'
            for (int j = line.length - 1; j >= 0; j--) {
                newLine = newLine + line[j] + " ";
            }

            // Add 'newLine' to 'arr2'
            arr2.add(newLine);
        }

        // Return 'arr2'
        return arr2;
    }

    // 'reverseLineOrder' method
    // Prints ArrayList with mirrored output
    public void mirrorWords(ArrayList<String> arr) {

        // Create new ArrayList 'arr2'
        ArrayList<String> arr2 = new ArrayList<String>();

        // Add lines from 'arr' into 'arr2'
        for (int i = 0; i < arr.size(); i++) {
            String word = arr.get(i);

            // String placeholder 'reverse'
            String reverse = "";
            
            // Reverse characters in 'reverse'
            for (int j = word.length() - 1; j >= 0; j--) {
                reverse = reverse + word.charAt(j);
            }

            // Add 'reverse' to 'arr2'
            arr2.add(reverse);
        }

        // Integer placeholder 'longestLine'
        int longestLine = 0;

        // Calculate longest line in 'arr' and increase length by 2 (for spacing)
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).length() > longestLine) {
                longestLine = arr.get(i).length() + 2;
            }
        }

        // Integer placeholder 'padding'
        int padding = 0;

        // Calculate padding required for each line
        for (int i = 0; i < arr.size(); i++) {
            padding = longestLine - arr.get(i).length();

            // Print mirrored line output
            System.out.println(arr.get(i) + " ".repeat(padding) + "*" + " ".repeat(padding) + arr2.get(i));
        }
    }

    // 'printElements' method
    // Print elements in an ArrayList
    public void printLines(ArrayList<String> arr) {

        // Print lines in 'arr'
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }

    // Main method
    public static void main(String[] args) {

        // Create new instance of ReverseList 'rl'
        ReverseList rl = new ReverseList();

        // Create new ArrayList 'arr'
        ArrayList<String> arr = new ArrayList<String>();

        // Read and add lines from 'input.txt' into 'arr' using Scanner
        try {
            Scanner sc = new Scanner(new File("input.txt"));
            while (sc.hasNextLine()) {
                arr.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Original text output
        System.out.println("   ~ Original text:");
        rl.printLines(arr);

        // Reversed line-order text output
        System.out.println("\n   ~ Reversed line-order text:");
        ArrayList<String> reversedLinesArr = rl.reverseLineOrder(arr);
        rl.printLines(reversedLinesArr);

        // Mirrored text output
        System.out.println("\n   ~ Mirrored text:");
        rl.mirrorWords(arr);
    }
    
}