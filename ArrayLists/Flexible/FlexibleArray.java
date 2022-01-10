// Declare package
package ArrayLists.Flexible;

// 'FlexibleArray' class
public class FlexibleArray implements Flexibility {

    // int[] array placeholder 'arr'
    private int[] arr;

    // int placeholder 'size'
    private int size;

    // int placeholder 'occupancy'
    private int occupancy;

    // Default constructor 'FlexibleArray'
    // Initializes placeholder variables
    public FlexibleArray() {
        arr = new int[2];
        size = 0;
        occupancy = 2;
    }

    // 'size' method
    // Method to return the number of elements this array can accommodate.
    @Override
    public int size() {
        return size;
    }

    // 'occupancy' method
    // Method to return the number of elements currently stored in the array.
    @Override
    public int occupancy() {
        return occupancy;
    }

    // 'newArray' method
    // Method to return a newly created, flexible array.
    @Override
    public int[] newArray() {
        return arr;
    }

    // 'addElement' method, one-parameter
    // Method to add a value after the last inserted element in the array and,
    // if necessary, expand the size of the array to accommodate the new value.
    @Override
    public int[] addElement(int value) {
        if (size == occupancy) {
            ensureCapacity(2);
        }
        arr[size] = value;
        size++;
        return arr;
    }

    // 'addElement' method, two-parameters
    // Method to add a value at a given position in the array - that position
    // may or may not be within current bounds. If necessary, this method
    // expands the array to provide the space for the requested position.
    // It is left to the Developer to determine where the next addElement(int value)
    // operation will place the new value.
    @Override
    public int[] addElement(int value, int position) {
        if (size == occupancy) {
            ensureCapacity(2);
        }
        for (int i = size - 1; i >= position; i--) {
            arr[i + 1] = arr[i];
        }
        arr[position] = value;
        size++;
        return arr;
    }

    // 'ensureCapacity' method
    // Method to increase capacity of the array, if necessary, to hold at least the
    // minimum capacity indicated
    public void ensureCapacity(int minCapacity) {
        int tmp[] = new int[occupancy * minCapacity];
        for (int i = 0; i < occupancy; i++) {
            tmp[i] = arr[i];
        }
        arr = tmp;
        occupancy = occupancy * minCapacity;
    }
    
}