package Midterm;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * M1 - Midterm Exam
 * HobbitGenerator
 */

/** Import modules */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HobbitGenerator {

    /** String placeholder for generated Hobbit name */
    private String hobbitName;

    /** Instance of Random */
    private Random rand = new Random();

    /** Instance of URL */
    private URL link;

    /** String ArrayList placeholder for imported text files */
    private ArrayList<String> txtFile;

    /** "importURLText" method
     * 
     * Accesses a text file from a URL and reads its contents line-by-line, 
     * adding them to a String ArrayList.
     * @param url URL link of text file
     * @return String ArrayList with each line from text file
     */
    private ArrayList<String> importURLText(String url) {

        // Create new ArrayList "arr"
        ArrayList<String> arr = new ArrayList<String>();

        try {
            // Access URL link using Scanner
            link = new URL(url);
            Scanner sc = new Scanner(link.openStream());

            // Add each line from text file into "arr"
            while (sc.hasNextLine()) {
                arr.add(sc.nextLine());
            }

            // Close Scanner
            sc.close();
        } catch (IOException e) {

            // Output error if caught
            e.printStackTrace();
        }

        // Return "arr"
        return arr;
    }

    /** "formatName" method
     * 
     * Formats name with proper capitalization.
     * @param name Name to be formatted
     * @return Formatted name
     */
    private String formatName(String name) {

        // If name is made of multiple components...
        if (name.indexOf(" ") > 0) {

            // Get index of space in name
            int spaceIndex = name.indexOf(" ");

            // Format name
            name = name.substring(0, 1).toUpperCase() 
                    + name.substring(1, spaceIndex)
                    + " "
                    + name.substring(spaceIndex + 1, spaceIndex + 2).toUpperCase() 
                    + name.substring(spaceIndex + 2); 
        } else {

            // Format name
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        // Return formatted name
        return name;
    }

    /** "getRandomName" method
     * 
     * Randomly chooses a name from a String ArrayList.
     * @param importArr String ArrayList of names to choose from
     * @return Randomly chosen and formatted name
     */
    private String getRandomName(ArrayList<String> importArr) {

        // String placeholder for chosen name
        String name;

        // Choose a random name from imported ArrayList
        name = importArr.get(rand.nextInt(importArr.size()));

        // Format chosen name
        name = formatName(name);

        // Return chosen name
        return name;
    }

    /** "getRandomName" method
     * 
     * Randomly chooses a name from a String ArrayList given the first letter.
     * If no corresponding name exists, then a random name is chosen.
     * @param importArr String ArrayList of names to choose from
     * @param firstLetter First letter in name
     * @return Randomly chosen and formatted name
     */
    private String getRandomName(ArrayList<String> importArr, char firstLetter) {

        // Count of names starting with indicated first letter
        int count = 0;

        // Check if names starting with indicated first letter exist
        for (String item : importArr) {

            // If name starting with indicated first letter exists...
            if (item.charAt(0) == firstLetter) {

                // Increase count by 1
                count++;
            }
        }

        // ArrayList with names starting with indicated first letter
        ArrayList<String> relevantNames = new ArrayList<String>();

        // String placeholder for Hobbit first name
        String name;

        // If relevant names exist...
        if (count != 0) {

            // Add relevant names to "relevantNames"
            for (String item : importArr) {
                if (item.charAt(0) == firstLetter) {
                    relevantNames.add(item);
                }
            }

            // Choose a random name from "relevantNames"
            name = relevantNames.get(rand.nextInt(relevantNames.size()));
        } else {

            // Choose a random name from imported ArrayList
            name = importArr.get(rand.nextInt(importArr.size()));
        }

        // Format Hobbit first name
        name = formatName(name);

        // Return Hobbit first name
        return name;
    }

    /** "getFirstName" method
     * 
     * Generates a Hobbit first name based on a given first name.
     * @param name Given first name
     * @return Hobbit first name
     */
    private String getFirstName(String name) {

        // Import "spices.txt"
        txtFile = importURLText(
            "https://raw.githubusercontent.com/lgreco/DataStructures/master/Midterm%20F20/src/spices.txt"
        );

        // Generate Hobbit first name
        String firstName = getRandomName(txtFile, name.charAt(0));
        
        // Return Hobbit first name
        return firstName;
    }

    /** "getLastName" method
     * 
     * Generates a Hobbit last name based on a given last name.
     * @param name Given last name
     * @return Hobbit last name
     */
    private String getLastName(String name) {

        // String placeholder for generated Hobbit last name
        String lastName;

        // Randomly choose between drink/baked good or body part/landform structure
        if (rand.nextInt(2) == 0) {
            
            // Import "drinks.txt"
            txtFile = importURLText(
                "https://raw.githubusercontent.com/lgreco/DataStructures/master/Midterm%20F20/src/drinks.txt"
            );

            // Generate first component
            lastName = getRandomName(txtFile, name.charAt(0));

            // Import "baked_goods.txt"
            txtFile = importURLText(
            "https://raw.githubusercontent.com/lgreco/DataStructures/master/Midterm%20F20/src/baked_goods.txt"
            );

            // Generate and append second component to Hobbit last name
            lastName += getRandomName(txtFile).toLowerCase();
        } else {

            // Import "body_parts.txt"
            txtFile = importURLText(
            "https://raw.githubusercontent.com/lgreco/DataStructures/master/Midterm%20F20/src/body_parts.txt"
            );

            // Generate first component
            lastName = getRandomName(txtFile, name.charAt(0));

            // Import "landforms.txt"
            txtFile = importURLText(
            "https://raw.githubusercontent.com/lgreco/DataStructures/master/Midterm%20F20/src/landforms.txt"
            );

            // Generate and append second component to Hobbit last name
            lastName += getRandomName(txtFile).toLowerCase();
        }

        // Return Hobbit last name
        return lastName;
    }

    /** "hobbitName" method
     * 
     * Generates a Hobbit name given a name.
     * @param name User-inputted name
     * @return Generated Hobbit name
     */
    public String hobbitName(String name) {

        // Split first and last name
        String[] splitName = name.toLowerCase().split(" ");

        // Check compatability of name structure
        if (splitName.length > 2) {

            // Return error message if incompatible
            return "ERROR: Name must consist only of a first and last name (e.g. \"Leo Irakliotis\").";
        }

        // Generate Hobbit first name
        String firstName = getFirstName(splitName[0]);

        // Set "hobbitName" to generated first name
        hobbitName = firstName;

        // Generate Hobbit last name
        String lastName = getLastName(splitName[1]);

        // Append generated Hobbit last name to Hobbit name
        hobbitName += " " + lastName;

        // Return Hobbit name
        return hobbitName;
    }
}