// Import modules
import java.util.ArrayList;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * 10 - Sets
 */

public class Set {

    /** The elements of a set */
    private ArrayList<String> elements;

    /**
     * Default constructor
     */
    public Set() {

        // Initialize 'elements' ArrayList
        elements = new ArrayList<>();
    }

    /**
     * 'insert' method
     *
     * Method to insert a String element into a set.
     * Checks if element exists in set; If so, displays an error message.
     * Otherwise, element is added to the set.
     *
     * @param element Element to be added to the set
     * @return true if element is successfully added, false if not
     */
    public boolean insert(String element) {

        // Success key
        boolean success;

        // If element already exists in set...
        if (elements.contains(element)) {

            // Display error message
            System.out.println("ERROR: Element '" + element + "' already exists within the set.");

            // Set success key to false
            success = false;
        } else {

            // Add element to 'elements'
            elements.add(element);

            // Set success key to true
            success = true;
        }

        // Return 'success'
        return success;
    }

    /**
     * 'union' method
     *
     * Finds the union of two sets.
     * Returns a set containing every element from the first set and every element from the second.
     *
     * @param A Set A
     * @param B Set B
     * @return A Set object containing the union of Sets A and B
     */
    public static Set union(Set A, Set B) {

        // Placeholder for union of 'A' and 'B'
        Set union = new Set();

        // Run through elements in Set A
        for (String elementA : A.elements) {

            // If element does not exist in 'union'...
            // Bypasses error message in insert() method for display organization purposes
            if (!union.elements.contains(elementA)) {

                // Insert element into 'union'
                union.insert(elementA);
            }
        }

        // Run through elements in Set B
        for (String elementB : B.elements) {

            // If element does not exist in 'union'...
            // Bypasses error message in insert() method for display organization purposes
            if (!union.elements.contains(elementB)) {

                // Insert element into 'union'
                union.insert(elementB);
            }
        }

        // Return 'union'
        return union;
    }

    /**
     * 'intersection' method
     *
     * Finds the intersection of two sets.
     * Returns a set containing only those elements common to both sets.
     *
     * @param A Set A
     * @param B Set B
     * @return A Set object containing the intersection of Sets A and B
     */
    public static Set intersection(Set A, Set B) {

        // Placeholder for intersection of 'A' and 'B'
        Set intersection = new Set();

        // Run through elements in Set A
        for (String elementA : A.elements) {

            // Run through elements in Set B
            for (String elementB : B.elements) {

                // If common element is found...
                if (elementA.equals(elementB)) {

                    // Insert element into 'intersection'
                    intersection.insert(elementB);
                }
            }
        }

        // Return 'intersection'
        return intersection;
    }

    /**
     * 'toString' method
     *
     * Prepare a String for printing purposes.
     *
     * @return the String representation of the elements ArrayList
     */
    public String toString() {

        // Placeholder for reformatted digits
        String toReturn = "{";

        // Add elements to 'toReturn' and format
        for (int i = 0; i < elements.size(); i++) {

            // Add elements to 'toReturn'
            toReturn += elements.get(i);

            // If element is not the last element in set...
            if ((i + 1) < elements.size()) {

                // Add comma and space to 'toReturn'
                toReturn += ", ";
            }
        }

        // Close parenthesis in 'toReturn'
        toReturn += "}";

        // Return 'toReturn'
        return toReturn;
    }

    /**
     * main method
     */
    public static void main(String[] args) {

        // Set A
        Set a = new Set();
        a.insert("a");
        a.insert("b");
        a.insert("c");
        System.out.println("Set A: " + a.toString());

        // Attempt to add existing element to Set A
        a.insert("a");

        // Set B
        Set b = new Set();
        b.insert("c");
        b.insert("d");
        b.insert("e");
        System.out.println("\nSet B: " + b.toString());

        // Set C (empty set)
        Set c = new Set();
        System.out.println("\nSet C: " + c.toString());

        // Union of Sets A and B
        Set unionAB = new Set();
        unionAB = union(a, b);
        System.out.println("\nUnion of Sets A and B: " + unionAB.toString());

        // Intersection of Sets A and B
        Set intersectionAB = new Set();
        intersectionAB = intersection(a, b);
        System.out.println("Intersection of Sets A and B: " + intersectionAB.toString());
    }
}