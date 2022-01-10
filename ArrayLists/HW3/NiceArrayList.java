/*
* Brian Nguyen
* COMP 271 - 003, F20
* 03 - ArrayLists
*/

// Declare package
package ArrayLists.HW3;

// Import modules
import java.util.ArrayList;

// 'NiceArrayList' class
public class NiceArrayList<E> extends ArrayList<E> {

    // Default serial version ID
    private static final long serialVersionUID = 1L;

    // 'add' method
    // Checks if ArrayList already has an element, refusing to add it if so
    @Override
    public boolean add(E element) {
        if (!contains(element)) {
            super.add(element);
            return true;
        } else {
            System.out.println("ERROR: ArrayList already contains \'" + element + "\'");
            return false;
        }
    }

    // 'removeDuplicates' method
    // Creates a new ArrayList without duplicate elements from the passed-in ArrayList
    public ArrayList<E> removeDuplicates(ArrayList<E> arr) {

        // Create new ArrayList 'newArr' to contain adjusted ArrayList
        ArrayList<E> newArr = new ArrayList<E>();

        // Populate 'newArr' with 'arr' contents, disregarding duplicate elements
        for (E element : arr) {
            if (!newArr.contains(element)) {
                newArr.add(element);
            }
        }

        // Return 'newArr' without duplicates from 'arr'
        return newArr;
    }
}