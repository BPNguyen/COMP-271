/*
* Brian Nguyen
* COMP 271 - 003, F20
* 03 - ArrayLists
*/

/*
Collins, Exercise 6.12:

Suppose we create the following ArrayList instance:

    ArrayList<String> words = new ArrayList<String>();

And then we insert several words into words. Write the code to print out each element of words that has
exactly four letters. You should have three different versions of the code:

a. using an index;
b. using an explicit iterator;
c. using an enhanced for statement.
*/

// Declare package
package ArrayLists.HW3;

// Import modules
import java.util.ArrayList;

public class Collins612 {

    public static void main(String[] args) {

        // ArrayList instance 'words'
        ArrayList<String> words = new ArrayList<String>();

        // Add random words to 'words'
        words.add("Apple");
        words.add("Candle");
        words.add("Sign");
        words.add("Can");
        words.add("Whoa");

        // Print words in 'words'
        System.out.println("ArrayList 'words': " + words);

        // (a) Print four-letter words in 'words' using index
        System.out.println("\n(a) Print four-letter words in 'words' using index:");
        System.out.println(words.get(2));
        System.out.println(words.get(4));

        // (b) Print four-letter words in 'words' using explicit iterator
        System.out.println("\n(b) Print four-letter words in 'words' using explicit iterator:");
        for (int i = 0; i < words.size(); i++) {
            String w = words.get(i);
            if (w.length() == 4) {
                System.out.println(w);
            }
        }

        // (c) Print four-letter words in 'words' using enhanced for loop
        System.out.println("\n(c) Print four-letter words in 'words' using enhanced for loop:");
        for (String w : words) {
            if (w.length() == 4) {
                System.out.println(w);
            }
        }
        
    }
    
}
