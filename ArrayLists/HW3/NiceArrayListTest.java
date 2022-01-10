package ArrayLists.HW3;

import java.util.ArrayList;

public class NiceArrayListTest {
    
    public static void main(String[] args) {

        NiceArrayList<String> arr = new NiceArrayList<String>();

        // Test enhanced 'add' method
        arr.add("Apple");
        arr.add("Pear");
        arr.add("Banana");
        arr.add("Apple");
        arr.add("Grape");
        System.out.println("Original: " + arr);

        // Test 'removeDuplicates' method
        ArrayList<String> uniqueArr = new ArrayList<String>(arr.removeDuplicates(arr));
        System.out.println("No Duplicates: " + uniqueArr);
    }
}
