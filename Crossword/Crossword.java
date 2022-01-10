/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * F1 - Final Exam
 * Crossword
 */

/** Import modules */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Crossword {

    /** Instance of Random */
    private final Random rand = new Random();

    /** Instance of Scanner */
    private final Scanner input = new Scanner(System.in);

    /** Minimum percentage of cells in grid allowed to be blocked off */
    private final static double minBlockedCells = 0.25;

    /** Maximum percentage of cells in grid allowed to be blocked off */
    private final static double maxBlockedCells = 0.35;

    /** Amount of spaces available for padding */
    private final int padSpaces = 9;

    /** Amount of spaces available for styling */
    private final int styleSpaces = 64;

    /** Number of blocked cells in crossword puzzle */
    private int numBlockedCells;

    /** Number of rows in crossword puzzle */
    private int numRows;

    /** Number of columns in crossword puzzle */
    private int numColumns;

    /** Dimension adjustment key */
    private boolean wasAdjusted = false;

    /** 2D integer array for crossword puzzle grid
     *
     * A 2D array was chosen because I believe it is easier to navigate through each
     * cell sequentially using nested for-loops. Additionally, an array was chosen
     * because of the inability to alter its length once it is initialized, since the
     * dimensions are to remain constant once defined.
     */
    private String[][] grid;

    /** 2D integer array for starting indexes of words going across in crossword puzzle grid
     *
     * A 2D array was chosen because I believe it is easier to navigate through each
     * cell sequentially using nested for-loops. Additionally, an array was chosen
     * because of the inability to alter its length once it is initialized, since the
     * dimensions are to remain constant once defined.
     */
    private String[][] acrossIndexGrid;

    /** 2D integer array for starting indexes of words going down in crossword puzzle grid
     *
     * A 2D array was chosen because I believe it is easier to navigate through each
     * cell sequentially using nested for-loops. Additionally, an array was chosen
     * because of the inability to alter its length once it is initialized, since the
     * dimensions are to remain constant once defined.
     */
    private String[][] downIndexGrid;

    /** 2D integer array for starting indexes of all words crossword puzzle grid
     *
     * A 2D array was chosen because I believe it is easier to navigate through each
     * cell sequentially using nested for-loops. Additionally, an array was chosen
     * because of the inability to alter its length once it is initialized, since the
     * dimensions are to remain constant once defined.
     */
    private String[][] indexGrid;

    /** String ArrayList for words going across in crossword puzzle
     *
     * An ArrayList was chosen because I believe it is easier to add to, remove from, and
     * display its contents. Additionally, an ArrayList was chosen because of the
     * ability to alter its length as objects are added to it once it has been
     * initialized, since the word list is meant to increase as words are added.
     */
    private ArrayList<String> wordsAcross;

    /** Integer ArrayList for starting indexes of words going across in crossword puzzle
     *
     * An ArrayList was chosen because I believe it is easier to add to, remove from, and
     * display its contents. Additionally, an ArrayList was chosen because of the
     * ability to alter its length as objects are added to it once it has been
     * initialized, since the word list is meant to increase as words are added.
     */
    private ArrayList<Integer> wordsAcrossIndex;

    /** String ArrayList for words going down in crossword puzzle
     *
     * An ArrayList was chosen because I believe it is easier to add to, remove from, and
     * display its contents. Additionally, an ArrayList was chosen because of the
     * ability to alter its length as objects are added to it once it has been
     * initialized, since the word list is meant to increase as words are added.
     */
    private ArrayList<String> wordsDown;

    /** String ArrayList for starting indexes of words going down in crossword puzzle
     *
     * An ArrayList was chosen because I believe it is easier to add to, remove from, and
     * display its contents. Additionally, an ArrayList was chosen because of the
     * ability to alter its length as objects are added to it once it has been
     * initialized, since the word list is meant to increase as words are added.
     */
    private ArrayList<Integer> wordsDownIndex;

    /**
     * 'importWords' method
     *
     * Accesses 'words.txt' in @lgreco's GH repository and reads
     * its contents line-by-line, adding them to a String ArrayList
     * if the line consists entirely of alphabetic characters.
     *
     * @return String ArrayList with valid lines from text file
     */
    public ArrayList<String> importWords() {

        // Instance of URL
        URL link;

        // Create new ArrayList 'arr'
        ArrayList<String> arr = new ArrayList<String>();

        // Attempt to access file
        try {

            // URL link to 'words.txt' in @lgreco's GH repository
            link = new URL("https://raw.githubusercontent.com/lgreco/DataStructures/master/CrossWords/src/words.txt");

            // Read file using Scanner
            Scanner sc = new Scanner(link.openStream());

            // While a next line exists...
            while (sc.hasNextLine()) {

                // Access next line
                String line = sc.nextLine();

                // If line is fully alphabetic...
                if (isAlphabetic(line)) {

                    // If line is at least 2 characters long...
                    if (line.length() >= 2) {

                        // Add line to 'arr'
                        arr.add(line.toUpperCase());
                    }
                }
            }

            // Close Scanner
            sc.close();
        } catch (IOException e) {

            // Output error if caught
            e.printStackTrace();
        }

        // Return 'arr'
        return arr;
    }

    /**
     * 'isAlphabetic' method
     *
     * Checks if a String fully consists of alphabetic characters.
     *
     * @param str String to be analyzed
     * @return True if String is fully alphabetic; False if word is not
     * */
    private boolean isAlphabetic(String str) {

        // For each character in 'str'...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < str.length(); i++) {

            // If character is not an alphabetic character...
            if (!Character.isAlphabetic(str.charAt(i))) {

                // Return false
                return false;
            }
        }

        // Return true
        return true;
    }

    /**
     * 'buildCrossword' method
     *
     * Builds crossword puzzle using a 2D String array. Begins by
     * checking for dimension validity and adjusting if necessary,
     * then randomly generates a word and places it in middle of
     * grid. Afterwards, cells are randomly blocked up to a threshold
     * and remaining gaps are identified and filled with random words.
     * Words, character sequences, and their starting indexes are
     * identified and added to respective data structures.
     */
    public void buildCrossword() {

        // Ensure dimension validity and adjust if necessary
        dimensionCheck();

        // Initialize grid with user-indicated dimensions
        grid = new String[numRows][numColumns];

        // Initialize 'acrossIndexGrid' with user-indicated dimensions
        acrossIndexGrid = new String[numRows][numColumns];

        // Initialize 'downIndexGrid' with user-indicated dimensions
        downIndexGrid = new String[numRows][numColumns];

        // Initialize 'indexGrid' with user-indicated dimensions
        indexGrid = new String[numRows][numColumns];

        // Initialize 'wordsAcross' for words going across
        wordsAcross = new ArrayList<String>();

        // Initialize 'wordsAcrossIndex' for starting indexes of words going across
        wordsAcrossIndex = new ArrayList<Integer>();

        // Initialize 'wordsDown' for words going down
        wordsDown = new ArrayList<String>();

        // Initialize 'wordsDownIndex' for starting indexes of words going across
        wordsDownIndex = new ArrayList<Integer>();

        // Calculate number of cells in grid
        int numCells = numRows * numColumns;

        // Calculate index of middle row
        int middleRow = numRows / 2;

        // Calculate index of middle column
        int middleColumn = numColumns / 2;

        // ArrayList of words with max characters up to number of columns
        ArrayList<String> words = getConstrainedWords(importWords(), numColumns);

        // Random word to be placed in middle of grid
        String randMiddleWord = words.get(rand.nextInt(words.size()));

        // Substring of characters to left of middle character
        String charsLeftOfMiddle = getCharsLeftOfMiddle(randMiddleWord);

        // Substring of characters to right of middle character
        String charsRightOfMiddle = getCharsRightOfMiddle(randMiddleWord);

        // Place middle character in center cell of grid to ensure word is centered in row
        grid[middleRow][middleColumn] = String.valueOf(randMiddleWord.charAt(randMiddleWord.length() / 2));

        // Populate corresponding cells to right of middle character with 'charsRightOfMiddle'
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 1; i < charsRightOfMiddle.length() + 1; i++) {
            grid[middleRow][middleColumn + i] = String.valueOf(charsRightOfMiddle.charAt(i - 1));
        }

        // Populate corresponding cells to left of middle character with 'charsLeftOfMiddle'
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 1; i < charsLeftOfMiddle.length() + 1; i++) {
            grid[middleRow][middleColumn - i] = String.valueOf(charsLeftOfMiddle.charAt(charsLeftOfMiddle.length() - i));
        }

        // Add reformatted 'randMiddleWord' to 'wordsAcross'
        wordsAcross.add(recapitalize(randMiddleWord));

        // If 'randMiddleWord's length > 'numColumns'...
        if (randMiddleWord.length() < numColumns) {

            // If 'randMiddleWord' does not extend to last column...
            if ((middleColumn + charsRightOfMiddle.length()) != numColumns) {

                // If cell to right of last character in 'randMiddleWord' is empty...
                if (grid[middleRow][middleColumn + charsRightOfMiddle.length() + 1] == null) {

                    // Set empty cell to blocked cell
                    grid[middleRow][middleColumn + charsRightOfMiddle.length() + 1] = "#";
                }
            }

            // If 'randMiddleWord' does not begin in first column...
            if ((middleColumn - charsLeftOfMiddle.length()) != 0) {

                // If cell to left of first character in 'randMiddleWord' is empty...
                if (grid[middleRow][middleColumn - charsLeftOfMiddle.length() - 1] == null) {

                    // Set empty cell to blocked cell
                    grid[middleRow][middleColumn - charsLeftOfMiddle.length() - 1] = "#";
                }
            }
        }

        // Generate random number of blocked cells in grid
        numBlockedCells = (int)(numCells * (minBlockedCells + (maxBlockedCells - minBlockedCells) * rand.nextDouble()));

        // Randomly block cells while avoiding middle row
        randomBlocking(middleRow);

        // Populate empty gaps with random words, row by row, while avoiding middle row
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numRows; i++) {

            // If not accessing middle row...
            if (i != middleRow) {

                // Populate row
                populateRow(i);
            } else {

                // Replace middle word with next random word in 'wordsAcross' to correspond with appropriate starting indexes
                // (Stops at the element before the last one, to allow for a fence-post termination)
                for (int j = 0; j < wordsAcross.size(); j++) {

                    // Create String placeholder for middle word
                    String temp = wordsAcross.get(j);

                    // If last letter is not being accessed...
                    if (j + 1 != wordsAcross.size()) {

                        // Switch middle word with next random word
                        wordsAcross.set(j, wordsAcross.get(j + 1));

                        // Replace spot of next random word with middle word
                        wordsAcross.set(j + 1, temp);
                    }
                }
            }
        }

        // Block empty cells
        blockEmptyCells();

        // Gather starting indexes of words going across
        getAcrossIndexes();

        // Find valid words in each column
        findColumnWords();

        // Gather starting indexes of words going down
        //getDownIndexes();
        getDownIndexes();

        // Combine starting indexes of words going across and down
        combineIndexes();
    }

    /**
     * 'dimensionCheck' method
     *
     * Ensures compatibility of user-defined dimensions with program by
     * so that middle row and/or column can be found. If user-defined
     * dimension is even, respective dimension is incremented by 1.
     * Also handles special case if user-defined dimension is 1, in
     * which respective dimension is set to 3 (minimum dimension size).
     */
    private void dimensionCheck() {

        // If number of rows is even...
        if (numRows % 2 == 0) {

            // Increment 'numRows' by 1
            numRows++;

            // Set 'wasAdjusted' to true
            wasAdjusted = true;
        }

        // If number of rows is 1...
        else if (numRows == 1) {

            // Set number of rows to 3 (least acceptable number of rows)
            numRows = 3;

            // Set 'wasAdjusted' to true
            wasAdjusted = true;
        }

        // If number of columns is even...
        if (numColumns % 2 == 0) {

            // Increment 'numColumns' by 1
            numColumns++;

            // Set 'wasAdjusted' to true
            wasAdjusted = true;
        }

        // If number of columns is 1...
        else if (numColumns == 1) {

            // Set number of columns to 3 (least acceptable number of columns)
            numColumns = 3;

            // Set 'wasAdjusted' to true
            wasAdjusted = true;
        }
    }

    /**
     * 'getConstrainedWords' method
     *
     * Creates and returns a new String ArrayList containing words with a
     * specified maximum length from an existing String ArrayList.
     *
     * @param arr String ArrayList containing words to be extracted from
     * @param maxLength Maximum length of desired words
     * @return String ArrayList containing words with a specified maximum length
     */
    private ArrayList<String> getConstrainedWords(ArrayList<String> arr, int maxLength) {

        // String ArrayList containing words within constraints
        ArrayList<String> constrainedWords = new ArrayList<String>();

        // For each 'word' in 'arr'...
        for (String word : arr) {

            // If 'word' has a length <= 'maxLength' and >= 2 characters long
            if ((word.length() <= maxLength) && (word.length() >= 2)) {

                // Add 'word' to 'constrainedWords'
                constrainedWords.add(word);
            }
        }

        // Return 'constrainedWords'
        return constrainedWords;
    }

    /**
     * 'getCharsRightOfMiddle' method
     *
     * Creates a String with characters to the right of the middle character
     * in a word.
     *
     * @param word Word to be deconstructed.
     * @return String containing characters to the right of the middle character.
     */
    private String getCharsRightOfMiddle(String word) {

        // Return substring of characters to right of middle character
        return word.substring((word.length() / 2) + 1);
    }

    /**
     * 'getCharsLeftOfMiddle' method
     *
     * Creates a String with characters to the left of the middle character
     * in a word.
     *
     * @param word Word to be deconstructed.
     * @return String containing characters to the left of the middle character.
     */
    private String getCharsLeftOfMiddle(String word) {

        // Return Substring of characters to left of middle character
        return word.substring(0, (word.length() / 2));
    }

    /**
     * 'randomBlocking' method
     *
     * Randomly blocks cells throughout grid up to a number of blocked cells,
     * while avoiding a certain row.
     *
     * @param avoidRow Row to be avoided
     */
    private void randomBlocking(int avoidRow) {

        // Block up to 'numBlockedCells'
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numBlockedCells; i++) {

            // 'findEmptyCell' key
            boolean findEmptyCell = true;

            // WHile 'findEmptyCell' is true...
            while (findEmptyCell) {

                // Randomly select a row
                int randRow = rand.nextInt(numRows);

                // Randomly select a column
                int randColumn = rand.nextInt(numColumns);

                // If row being accessed is not row to be avoided...
                if (randRow != avoidRow) {

                    // If cell is empty...
                    if (grid[randRow][randColumn] == null) {

                        // Block cell
                        grid[randRow][randColumn] = "#";

                        // Set 'findEmptyCell' to false
                        findEmptyCell = false;
                    }
                }
            }
        }
    }

    /**
     * 'populateRow' method
     *
     * Randomly populates a row with words that fit empty gaps.
     *
     * @param row Row to be populated
     */
    private void populateRow(int row) {

        // Convert row to String
        String rowStr = convertRowToString(row);

        // Split 'rowStr' using blocked cells as delimiter
        String[] splitRowStr = rowStr.split("#");

        // Placeholder String ArrayList for empty gaps to be populated
        ArrayList<String> toReplace = getApplicableSequences(splitRowStr, 0);

        // Placeholder String ArrayList for random words
        ArrayList<String> randWords = new ArrayList<String>();

        // For each gap in 'splitRowStr'...
        for (String str : splitRowStr) {

            // If gap's length is > 1...
            if (str.length() > 1) {

                // Get words constrained by gap's length
                ArrayList<String> words = getConstrainedWords(importWords(), str.length());

                // 'searching' key
                boolean searching = true;

                // While 'searching' is true...
                while (searching) {

                    // Generate random word fitting gap
                    String randWord = getRandomWordOfLength(words, str.length());

                    // If 'randword' is not already an existing word in 'wordsAcross'...
                    if (!wordsAcross.contains(randWord)) {

                        // Add word to 'randWords'
                        randWords.add(randWord);

                        // Add recapitalized word to 'wordsAcross'
                        wordsAcross.add(recapitalize(randWord));

                        // Set 'searching' to false
                        searching = false;
                    }
                }
            } else if (str.length() == 1) {

                // Block cell
                randWords.add("#");
            }
        }

        // For each gap to be replaced...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < toReplace.size(); i++) {

            // Gap to be replaced
            String gap = toReplace.get(i);

            // Replace gap with corresponding word
            rowStr = rowStr.replaceFirst(gap, randWords.get(i));
        }

        // For each column in row...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numColumns; i++) {

            // Replace cell with corresponding cell in 'rowStr'
            grid[row][i] = String.valueOf(rowStr.charAt(i));
        }
    }

    /**
     * 'findColumnWords' method
     *
     * Searches grid from top to bottom, column by column, for sequences of
     * characters. If a sequence exists, adds it to 'wordsDown'.
     */
    private void findColumnWords() {

        // For each row...
        for (int i = 0; i < numRows; i++) {

            // Convert row to String
            String rowStr = convertRowToString(i);

            // For each cell in 'rowStr'...
            // (Stops at the element before the last one, to allow for a fence-post termination)
            for (int j = 0; j < numColumns; j++) {

                // If cell is not a blocked cell...
                if (!String.valueOf(rowStr.charAt(j)).equals("#")) {

                    // Convert column to String
                    String columnStr = convertColumnToString(j);

                    // Split 'columnStr' using blocked cells as delimiter
                    String[] splitColumnStr = columnStr.split("#");

                    // Placeholder String ArrayList for applicable sequences
                    ArrayList<String> applicableStrs = getApplicableSequences(splitColumnStr, 1);

                    // If current row is the first row...
                    if (i == 0) {

                        // If cell below is a character...
                        if (Character.isAlphabetic(grid[i + 1][j].charAt(0))) {

                            // If sequences exist in 'applicableStrs'
                            if (applicableStrs.size() != 0) {

                                System.out.println(applicableStrs.get(0));

                                // Add recapitalized sequence to 'wordsDown'
                                wordsDown.add(recapitalize(applicableStrs.get(0)));
                            }
                        }
                    } else {

                        // If cell is not last cell...
                        if (i + 1 != numRows) {

                            // If cell above is blocked...
                            if (grid[i - 1][j].equals("#")) {

                                // If sequences exist in 'applicableStrs'
                                if (applicableStrs.size() != 0) {

                                    System.out.println(applicableStrs.get(0));

                                    // Add recapitalized sequence to 'wordsDown'
                                    wordsDown.add(recapitalize(applicableStrs.get(0)));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 'getApplicableSequences' method
     *
     * Returns a String ArrayList containing sequences of a specified minimum length
     * from a String array.
     *
     * @param arr String array containing words to be converted
     * @param minLength Minimum length of String (exclusive)
     * @return String ArrayList containing applicable sequences
     */
    private ArrayList<String> getApplicableSequences(String[] arr, int minLength) {

        // Placeholder String ArrayList for applicable sequences
        ArrayList<String> applicableStrs = new ArrayList<String>();

        // For each String in 'arr'...
        for (String str : arr) {

            // If String length > 'minLength'
            if (str.length() > minLength) {

                // Add String to 'applicableStrs'
                applicableStrs.add(str);
            }
        }

        // Return 'applicableStrs'
        return applicableStrs;
    }

    /**
     * 'convertRowToString' method
     *
     * Converts a row into a String.
     *
     * @param row Row to be converted
     * @return String containing row's sequence
     */
    private String convertRowToString(int row) {

        // String placeholder for row sequence
        String rowStr = "";

        // For each column in row...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numColumns; i++) {

            // If cell is empty...
            if (grid[row][i] == null) {

                // Add placeholder '-' to 'rowStr'
                rowStr += "-";
            } else {

                // Add content of cell to 'rowStr'
                rowStr += grid[row][i];
            }
        }

        // Return 'rowStr'
        return rowStr;
    }

    /**
     * 'convertColumnToString' method
     *
     * Converts a column into a String.
     *
     * @param column Column to be converted
     * @return String containing column's sequence
     */
    private String convertColumnToString(int column) {

        // String placeholder for column sequence
        String columnStr = "";

        // For each row...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numRows; i++) {

            // If cell is null...
            if (grid[i][column] == null) {

                // Add placeholder '-' to 'columnStr'
                columnStr += "-";
            } else {

                // Add content of cell to 'columnStr'
                columnStr += grid[i][column];
            }
        }

        // Return 'columnStr'
        return columnStr;
    }

    /**
     * 'getRandomWordOfLength' method
     *
     * Returns a word of specified length from a given String ArrayList.
     *
     * @param arr String ArrayList containing words
     * @param length Desired length of word
     * @return String containing random word of desired length
     */
    private String getRandomWordOfLength(ArrayList<String> arr, int length) {

        // Placeholder String for word
        String word = "";

        // 'searching' key
        boolean searching = true;

        // If 'searching' is true...
        while (searching) {

            // Get a random word from 'arr'
            word = arr.get(rand.nextInt(arr.size()));

            // If 'word's length is valid...
            if (word.length() == length) {

                // Set 'searching' to false
                searching = false;
            }
        }

        // Return 'word'
        return word;
    }

    /**
     * 'recapitalize' method
     *
     * Reformats a word to adhere to proper capitalization standards.
     *
     * @param word Word to be reformatted
     * @return String containing reformatted word
     */
    private String recapitalize(String word) {

        // Recapitalize and return 'recappedWord'
        return word.charAt(0) + word.substring(1).toLowerCase();
    }

    /**
     * 'blockEmptyCells' method
     *
     * Searches grid for empty cells and blocks them.
     */
    private void blockEmptyCells() {

        // For each cell in grid...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {

                // If empty cell exists...
                if (grid[i][j] == null) {

                    // Block cell
                    grid[i][j] = "#";
                }
            }
        }
    }

    /**
     * 'getAcrossIndexes' method
     *
     * Searches grid for character sequences and adds an index number to
     * 'acrossIndexGrid', indicating a first letter of a word starts there.
     */
    private void getAcrossIndexes() {

        // Sequential word index starting at 1
        int wordIndex = 1;

        // For each cell in grid...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {

                // If cell is blocked...
                if (grid[i][j].equals("#")) {

                    // If current cell is not last cell in row...
                    if (j + 1 != numColumns) {

                        // If cell to the right is a character...
                        if (Character.isAlphabetic(grid[i][j + 1].charAt(0))) {

                            // Indicate cell to the right of current cell in 'acrossIndexGrid' is a starting letter
                            acrossIndexGrid[i][j + 1] = "A" + wordIndex;

                            // Increment 'wordIndex' by 1
                            wordIndex++;
                        }
                    }
                } else {

                    // If first row is being accessed...
                    if (j == 0) {

                        // Indicate current cell in 'acrossIndexGrid' is a starting letter
                        acrossIndexGrid[i][j] = "A" + wordIndex;

                        // Increment 'wordIndex' by 1
                        wordIndex++;
                    }
                }
            }
        }
    }

    /**
     * 'getDownIndexes' method
     *
     * Searches grid for character sequences and adds an index number to
     * 'downIndexGrid', indicating a first letter of a sequence starts there.
     */
    private void getDownIndexes() {

        // Sequential word index starting at 1
        int sequenceIndex = 1;

        // For each cell...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {

                // If cell is not blocked...
                if (!grid[j][i].equals("#")) {

                    // If current cell is not last cell and cell below contains a character...
                    if ((j + 1 != numRows) && (Character.isAlphabetic(grid[j + 1][i].charAt(0)))) {

                        // If first row is being accessed...
                        if (j == 0) {

                            // Indicate current cell in 'downIndexGrid' is a starting letter
                            downIndexGrid[j][i] = "D" + sequenceIndex;

                            // Increment 'sequenceIndex' by 1
                            sequenceIndex++;
                        } else {

                            // If cell above is blocked...
                            if (grid[j - 1][i].equals("#")) {

                                // Indicate current cell in 'downIndexGrid' is a starting letter
                                downIndexGrid[j][i] = "D" + sequenceIndex;

                                // Increment 'sequenceIndex' by 1
                                sequenceIndex++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 'combineIndexes' method
     *
     * Combines indexes in 'acrossIndexGrid' and 'downIndexGrid' into one grid
     * and indicates if an index is a starting character for multiple
     * character sequences.
     */
    private void combineIndexes() {

        // Sequential word index starting at 1
        int wordIndex = 1;

        // For each cell in grid...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {

                // If index exists in 'acrossIndexGrid'...
                if (acrossIndexGrid[i][j] != null) {

                    // If index exists in 'downIndexGrid'...
                    if (downIndexGrid[i][j] != null) {

                        // Add index to 'wordsAcrossIndex'
                        wordsAcrossIndex.add(wordIndex);

                        // Add index to 'wordsDownIndex'
                        wordsDownIndex.add(wordIndex);

                        // Add index to 'indexGrid'
                        indexGrid[i][j] = Integer.toString(wordIndex);

                        // Increment 'wordIndex' by 1
                        wordIndex++;
                    } else {

                        // Add index to 'wordsAcrossIndex'
                        wordsAcrossIndex.add(wordIndex);

                        // Add index to 'indexGrid'
                        indexGrid[i][j] = Integer.toString(wordIndex);

                        // Increment 'wordIndex' by 1
                        wordIndex++;
                    }
                } else {

                    // If index exists in 'downIndexGrid'...
                    if (downIndexGrid[i][j] != null) {

                        // Add index to 'wordsDownIndex'
                        wordsDownIndex.add(wordIndex);

                        // Add index to 'indexGrid'
                        indexGrid[i][j] = Integer.toString(wordIndex);

                        // Increment 'wordIndex' by 1
                        wordIndex++;
                    }
                }
            }
        }
    }

    /**
     * 'showCrossword' method
     *
     * Displays either a populated or unpopulated crossword puzzle.
     *
     * @param populated Indication of whether to display populated
     *                  or unpopulated crossword puzzle. True if
     *                  populated, false if not,
     */
    public void showCrossword(boolean populated) {

        // If populated grid is not desired...
        if (!populated) {

            // For each cell in grid...
            // (Stops at the element before the last one, to allow for a fence-post termination)
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {

                    // If cell is not blocked...
                    if (!grid[i][j].equals("#")) {

                        // Replace content with a space
                        grid[i][j] = " ";
                    }
                }
            }
        }

        // For number of rows...
        for (int i = 0; i < numRows; i++) {

            // Print horizontal border with corresponding number of columns
            System.out.print(("+" + "-".repeat(padSpaces)).repeat(numColumns) + "+\n");

            // Placeholder int for padding on right side
            int padding = padSpaces - 1;

            // Print first row containing indexes
            // (Stops at the element before the last one, to allow for a fence-post termination)
            for (int j = 0; j < numColumns; j++) {

                // If grid is blocked...
                if (grid[i][j].equals("#")) {

                    // Block first row of cell
                    System.out.print("| " + "#".repeat(padding - 1) + " ");
                } else {

                    // If first row cell is contains a starting index...
                    if ((indexGrid[i][j] != null)) {

                        // Calculate padding
                        padding -= indexGrid[i][j].length();

                        // Print index number
                        System.out.print("| " + indexGrid[i][j] + " ".repeat(padding));

                        // Reset padding
                        padding = padSpaces - 1;
                    } else {

                        // Print empty cell
                        System.out.print(("|" + " ".repeat(padSpaces)));
                    }
                }
            }

            // Print vertical border and return line
            System.out.println("|");

            // Print second row containing cell content
            // (Stops at the element before the last one, to allow for a fence-post termination)
            for (int j = 0; j < numColumns; j++) {

                // If second row of cell is blocked...
                if (grid[i][j].equals("#")) {

                    // Block second row of cell
                    System.out.print("| " + "#".repeat(padding - 1) + " ");
                } else {

                    // Print cell content
                    System.out.print("|" + " ".repeat(padding / 2) + grid[i][j] + " ".repeat(padding / 2));
                }
            }

            // Print vertical border and return line
            System.out.println("|");
        }

        // Print bottom border with corresponding number of columns
        System.out.println(("+" + "-".repeat(padSpaces)).repeat(numColumns) + "+");

        // Display words going across
        System.out.println("\nWords going Across: ");

        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < wordsAcross.size(); i++) {
            System.out.println("\t" + wordsAcrossIndex.get(i) + ") " + wordsAcross.get(i));
        }

        // Display words going down
        System.out.println("\nWords going Down: ");

        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < wordsDown.size(); i++) {
            System.out.println("\t" + wordsDownIndex.get(i) + ") " + wordsDown.get(i));
        }
    }

    /**
     * 'showStatistics' method
     *
     * Displays crossword statistics, including number of rows, columns,
     * words, and percentage of cells blocked.
     */
    private void displayStatistics() {

        // If user-defined dimensions were adjusted...
        if (wasAdjusted) {

            // Display notice of adjustment
            System.out.println("NOTE: \tDimensions were adjusted to accommodate for " +
                    "\n\t\tcrossword-generating specifications.\n");
        }

        // Display number of rows
        System.out.println("Number of Rows: " + numRows);

        // Display number of columns
        System.out.println("Number of Columns: " + numColumns);

        // Display total number of words in crossword puzzle
        System.out.println("Number of Words: " + (wordsAcross.size() + wordsDown.size()));

        // Update number of blocked cells
        updateNumBlockedCells();

        // Calculate percentage of blocked cells
        double saturation = (double)numBlockedCells / (numRows * numColumns);

        // Display percentage of blocked cells
        System.out.printf("Percentage of Cells Blocked: %.2f", (saturation * 100));
        System.out.print("%\n");
    }

    /**
     * 'updateNumBlockedCells' method
     *
     * Counts and updates number of blocked cells currently in crossword puzzle.
     */
    private void updateNumBlockedCells() {

        // Reset 'numBlockedCells'
        numBlockedCells = 0;

        // For each cell in grid...
        // (Stops at the element before the last one, to allow for a fence-post termination)
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {

                // If cell is blocked...
                if (grid[i][j].equals("#")) {

                    // Increment 'numBlockedCells' by 1
                    numBlockedCells++;
                }
            }
        }
    }

    /**
     * 'driver' method
     *
     * Class to test COMP 271 final exam code.
     * The class expects that the words.txt file resides
     * either at @lgreco's GH repository or in the ./
     * folder (ie same folder as this java file).
     */
    public void driver() {

        // 'keepRunning' key
        boolean keepRunning = true;

        // While 'keepRunning' is true...
        while (keepRunning) {

        /*
        Show user a menu of options, e.g., enter two numbers greater than zero
        to create a puzzle with as many rows and columns, or enter zero to exit
        the program.

        If N==0 and M==0:
          keepRunning <-- false
        else:
          If user enters N>0 and M>0, build a NxM puzzle as specified in the project.

          Show the puzzle.

           If implementing the bonus part:
              ask user if they want to enter cues:
                if yes:
                  go through puzzle words, check to see if they are in the
                  crosswords_dictionary.txt file and if they are not,
                  ask user to type a clue, then save it in the file.
           else:
             ask user if they want to produce another puzzle
               if no, keepRunning <-- false
         */

            // Print program header and styling
            System.out.print("*".repeat(styleSpaces) + "\n" +
                    "\tCROSSWORD PUZZLE GENERATOR\n" +
                    "-".repeat(styleSpaces) + "\n" +
                    "\tEnter two positive integers to create a crossword puzzle\n" +
                    "\twith as many rows and columns indicated, respectively.\n\n" +
                    "\tYou can also enter \"0\" at any time to exit the program.\n" +
                    "-".repeat(styleSpaces) + "\n\n");

            // Prompt user for number of rows
            System.out.print("Number of Rows: ");

            // 'gettingRows' key
            boolean gettingRows = true;

            // While 'gettingRows' is true...
            while (gettingRows) {

                // Get user input for number of rows
                numRows = input.nextInt();

                // If 'numRows' is a positive integer...
                if ((numRows != 0) && (numRows > 0)) {

                    // Prompt user for number of columns
                    System.out.print("Number of Columns: ");

                    // 'gettingColumns' key
                    boolean gettingColumns = true;

                    // While 'gettingColumns' is true...
                    while (gettingColumns) {

                        // Get user input for number of columns
                        numColumns = input.nextInt();

                        // If 'numColumns' is a positive integer...
                        if ((numColumns != 0) && (numColumns > 0)) {

                            // Generated crossword puzzle
                            buildCrossword();

                            // Display dash spacer
                            displayDashSpacer();

                            // Display crossword puzzle
                            showCrossword(false);

                            // Display dash spacer
                            displayDashSpacer();

                            // Display statistics
                            displayStatistics();

                            // Display dash spacer
                            displayDashSpacer();

                            // Prompt user for repetition
                            System.out.print("Do you want to generate another crossword puzzle? \n" +
                                    "Please enter either \"Yes\" or \"No\": ");

                            // 'gettingRepeat' key
                            boolean gettingRepeat = true;

                            // While 'gettingRepeat' is true...
                            while (gettingRepeat) {

                                // Get user input for repetition
                                String repeat = input.next();

                                // Ensure 'repeat' is a valid input
                                if ((repeat.equalsIgnoreCase("Yes")) || (repeat.equalsIgnoreCase("No"))) {

                                    // If user does not want to repeat...
                                    if (repeat.equalsIgnoreCase("No")) {

                                        // Display exit footer
                                        displayExitFooter();

                                        // Set 'keepRunning' to false
                                        keepRunning = false;
                                    } else {

                                        // Display star spacer
                                        displayStarSpacer();
                                    }

                                    // Set previous keys to false
                                    gettingRepeat = gettingColumns = gettingRows = false;
                                } else {

                                    // Display error message and reprompt user for input
                                    System.out.print("ERROR: Please enter either \"Yes\" or \"No\": ");
                                }
                            }
                        } else if (numColumns < 0) {

                            // Display error message and reprompt user for input
                            System.out.print("ERROR: Please enter a POSITIVE integer value: ");
                        } else {

                            // Display exit footer
                            displayExitFooter();

                            // Set previous keys to false
                            gettingColumns = gettingRows = keepRunning = false;
                        }
                    }
                } else if (numRows < 0) {

                    // Display error message and reprompt user for input
                    System.out.print("ERROR: Please enter a POSITIVE integer value: ");
                } else {

                    // Display exit footer
                    displayExitFooter();

                    // Set previous keys to false
                    gettingRows = keepRunning = false;
                }
            }
        }
    }

    /**
     * 'displayStarSpacer' method
     *
     * Displays star spacing and styling.
     */
    private void displayStarSpacer() {
        System.out.println("\n" + "*".repeat(styleSpaces) + "\n");
    }

    /**
     * 'displayDashSpacer' method
     *
     * Displays dash spacing and styling.
     */
    private void displayDashSpacer() {
        System.out.println("\n" + "-".repeat(styleSpaces) + "\n");
    }

    /**
     * 'displayExitFooter' method
     *
     * Displays exit message and styling.
     */
    private void displayExitFooter() {
        System.out.println("\n"+ "-".repeat(64) +
                "\n\tExiting the program.\n" +
                "\n\tThank you for an exciting and incredibly interesting semester!" +
                "\n\tStay healthy and happy holidays!" +
                "\n\t- Brian Nguyen\n" +
                "*".repeat(styleSpaces));
    }

    /** Main method */
    public static void main(String[] args) {

        Crossword demo = new Crossword();
        demo.driver();
        // NOTE: Crossword puzzle solution can be enabled on line 1252
    }
}
