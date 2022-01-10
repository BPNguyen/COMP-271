import java.util.ArrayList;
import java.util.Collections;

/**
 * Brian Nguyen
 * COMP 271 - 003, F20
 * 08 - Very Large Numbers
 * 09 - The Gazillion Class
 */

/**
 * A simple class to capture really large integer numbers. Each instance of
 * this class is a big integer. Doesn't work with negative numbers for now.
 */
public class Gazillion {

    /** The digits of a very big number */
    private ArrayList<Integer> digits;

    /**
     * Constructor with String parameter
     * @param s String representation of big number, e.g., s = "1234...."
     */
    public Gazillion(String s) {
        final char LOWEST_DIGIT = '0';
        digits = new ArrayList<Integer>(s.length()); // Initialize class field to size of given String
        char c;
        int digit;
        boolean numericDigitDetected = false; // flag in case passed argument s contains no numerical digits
        // Copy string contents to ArrayList
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i); // scan passed argument s, one character at a time.
            if (Character.isDigit(c)) { // is this character a number digit?
                digit = c - LOWEST_DIGIT; // compute its value; should be 0, 1, ..., 9
                digits.add(digit); // add it to the digits ArrayList
                numericDigitDetected = true; // flag that we have at least one numeric digit!
            }
        }
        // If no numeric digit found in String s, we have a problem!
        if (!numericDigitDetected) {
            throw new IllegalArgumentException();
        }
    } // constructor with string parameter

    /**
     * Prepare a string for printing purposes.
     * @return the String representation of the digits array
     */
    public String toString() {
        
        // Placeholder for reformatted digits
        String formattedDigits = "";

        // Run through digits from right to left
        for (int i = digits.size() - 1; i >= 0; i--) {

            // Populate 'formattedDigits'
            formattedDigits += least(i);

            // Insert comma every 3 spaces
            if (i % 3 == 0 && i != 0) {

                formattedDigits += ",";
            }
        }

        // Return 'formattedDigits'
        return formattedDigits;
    } // method toString

    /**
     * Method to add two big integers. Usage:
     *   Gazillion a = new Gazillion("123...");
     *   Gazillion b = new Gazillion("987...");
     *   a.add(b); // a+b now assigned to a
     * From basic arithmetic, remember that for single digit numbers x and y:
     *   sum(x,y) = (x+y) % BASE
     *   carry(x,y) = (x+y) // BASE, where // is an integer division
     * @param gazillion second operate to add (first operand is accessed with operator)
     */
    public void add(Gazillion gazillion) {
        final int BASE = 10; // numerical base; obvious but necessary for carry and sum operations
        int largestSize; // size of the largest of two numbers
        int partialSum; // digit-by-digit sum
        int carry = 0; // initial value of carry

        // find the size of the largest number
        if (digits.size() > gazillion.digits.size()) {
            largestSize = digits.size();
        } else {
            largestSize = gazillion.digits.size();
        }

        // set up a temp ArrayList for the summation. It has space for as many digits as the largest
        // of the two numbers, PLUS one, in case of overflow.
        ArrayList<Integer> sumDigits = new ArrayList<Integer>(largestSize + 1);

        // Add numbers digit-by-digit
        for (int i = 0; i < largestSize; i++) {
            partialSum = least(i) + gazillion.least(i) + carry; // bring carry from previous digit-by-digit operation
            carry = partialSum / BASE; // integer division
            sumDigits.add(partialSum % BASE); // append the sum digit to the temporary ArrayList
        } // rof

        if (carry == 1) {
            sumDigits.add(carry); // overflow carry, if present
        }

        Collections.reverse(sumDigits); // reverse for proper representation
        digits = sumDigits; // copy sum to digits
    } // method add

    /**
     * Method to obtain the i-th digit
     * @param i position of digit
     * @return digit
     */
    private int least(int i) {
        if ( i>= digits.size()) {
            return 0;
        } else {
            return digits.get(digits.size() - i - 1);
        }
    } // method least

    /**
     * BRUTE FORCE MULTIPLICATION BASED ON GRADE SCHOOL TECHNIQUE
     *
     * Multiply this number with passed gazillion. Usage:
     *   Gazillion a = new Gazillion(" ... ");
     *   Gazillion b = new Gazillion(" ... ");
     *   a.multiply(b); // a is now a x b
     *
     * Multiplication, though trivial on paper, requires some work as a program.
     * You may want to check out the Karatsuba algorithm for multiplication, to
     * appreciate the complexity involved. However, for this problem you can use
     * the grade school multiplication algorithm. Consider two numbers written
     * as:  a2 a1 a0 (e.g., 214, a2 = 2, a1 = 1, a0 = 4) and
     *         b1 b0 (e.g., 33, b1 = 5, b0 = 3)
     * Multiplying them step by step:
     *         214
     *        x 53
     *    --------
     *         642   .... ( this is 3x214, i.e, b0 x (a2 a1 a0) )
     *       1070    .... ( this is 5x214, i.e., b1 x (a2 a1 a0) )
     *    ========
     *       11342   .... ( this is column-by-column addition )
     *
     * @param gazillion number to multiply .this with
     *
     */
    public void multiply(Gazillion gazillion) {

        // Numerical base
        final int BASE = 10;

        // Placeholder for carry number
        int carry;

        // Placeholder for partial product calculation
        int partialProduct;

        // Placeholder ArrayList 'productDigits'
        ArrayList<Integer> productDigits = new ArrayList<Integer>();

        // Number to be multiplied as integer array
        int[] mult = new int[digits.size()];

        // Populate 'mult' with .this' digits
        for (int i = 0; i < mult.length; i++) {
            mult[i] = least(i);
        }

        // Number to be multiplied by as integer array
        int[] multBy = new int[gazillion.digits.size()];

        // Populate 'multBy' with gazillion's digits
        for (int i = 0; i < multBy.length; i++) {
            multBy[i] = gazillion.least(i);
        }

        // Placeholder integer array 'product' for product digits, stored in reverse order
        int product[] = new int[mult.length + multBy.length];

        // Variable for accessing a specific index position in 'mult'
        int i_mult = 0;

        // Variable for accessing a specific index position in 'multBy'
        int i_multBy = 0;
        
        // Run through 'mult' from right to left
        for (int i = 0; i < mult.length; i++) {

            // Reset 'carry'
            carry = 0;

            // Current digit in 'mult'
            int currentMultDigit = mult[i];
        
            // Reset 'i_multBy' to shift left one index position after multiplication of each digit in 'multBy'
            i_multBy = 0;
            
            // Run through 'multBy' from right to left
            for (int j = 0; j < multBy.length; j++) {

                // Take current digit of second number
                int currentMultByDigit = multBy[j];
                
                // Calculate partial product and add to previous digit in shifted position
                partialProduct = (currentMultDigit * currentMultByDigit) + product[i_mult + i_multBy] + carry;
                
                // Calculate carry number
                carry = partialProduct / BASE;
                
                // Store result in 'product' 
                product[i_mult + i_multBy] = partialProduct % BASE;
                
                // Shift left one index position in 'multBy' 
                i_multBy++;
            }

            // If a carry number exists...
            if (carry > 0) {

                //Store 'carry' in next position to left
                product[i_mult + i_multBy] += carry;
            }
            
            // Shift left one index position after multiplication of each digit in 'mult'
            i_mult++;
        }

        // Variable for accessing certain index position in 'product' from right to left
        int i_product = product.length - 1;

        // While a zero digit exists in 'product'...
        while ((i_product < product.length) && (product[i_product] == 0)) {

            // Decrease index position by 1
            i_product--;
        }
        
        // If all digits in 'product' are 0...
        if (i_product == -1) {

            // Product = 0
            productDigits.add(0);

            // Copy contents of 'productDigits' to 'digits'
            digits = productDigits;

            // End method
            return;
        }

        // Populate 'productDigits' with applicable digits from 'product'
        for (int i = 0; i <= i_product; i++) {
            productDigits.add(product[i]);
        }

        // Reverse 'productDigits' for proper representation
        Collections.reverse(productDigits);

        // Copy contents of 'productDigits' to 'digits'
        digits = productDigits;
    } // method multiply

    /**
     * STANDARD RECURSIVE MULTIPLICATION
     *
     * A recursive implementation of the multiplication operation for two Gazillion objects
     * x and y, representing numbers written as:
     *
     *      x = 10^N * a + b
     *      y = 10^N * c + d
     *
     * where N is an int primitive and correspond to the number of digits in x and y;
     * (we assume that x and y have the same number of digits),  and a, b, c, and d
     * are Gazillion objects. As discussed in class, the pseudocode is:
     *
     *    RecursiveMultiplication:
     *     Input: x, y with N digits (N is a power of 2)
     *    Output: the product of x and y as a Gazillion object
     *      Note: ^ is not the correct way for exponentiation in Java!!!
     *
     *    if ( N==1 ):
     *       return new Gazillion(Integer.toString(x[0]*y[0])
     *    else:
     *      a, b <--- first and second halves of x (both are Gazillion objects)
     *      c, d <--- first and second halves of y (both are Gazillion objects)
     *      ac <--- RecursiveMultiplication(a,c);   //---------------------------//
     *      ad <--- RecursiveMultiplication(a,d);   //  REMEMBER: ALL THESE ARE  //
     *      bc <--- RecursiveMultiplication(b,c);   //  GAZILLION OBJECTS        //
     *      bd <--- RecursiveMultiplication(b,d);   //---------------------------//
     *      return (as Gazillion object) 10^N * ac + 10^(N/2) * (ad+bc) + bd
     *
     *  Notice that for very large values of N (e.g., N=100), the quantities 10^N (ten to the
     *  power N) and 10^(N/2) may be beyond Java's range for long primitives and you may have
     *  to express them as Gazillion objects. You may do so, or you may simply pad the underlying
     *  ArrayLists with the necessary number of 0s.
     *
     *  Notice also that the expression in the recursive return involves Gazillion objects
     *
     *    10^N * ac + 10^(N/2) * (ad+bc) + bd
     *
     *  Each operand above is a Gazillion object:
     *    10^N * ac
     *    10^(N/2) * (ad+bc)
     *    bd
     *  and so are the operands in (ad+bc).
     *
     *  In other words, every "+" in the return expression corresponds to a call to
     *  method add.
     *
     * @param x Gazillion operand
     * @param y Gazillion operand
     * @return product x*y as a Gazillion object
     */
    public static Gazillion recursiveMultiplication(Gazillion x, Gazillion y) {

        // Numerical base
        final int BASE = 10;

        // Placeholder for product calculation
        Gazillion product = null;

        int nX = x.digits.size();
        //System.out.println("nX: " + nX);
        int nY = y.digits.size();
        //System.out.println("nY: " + nY);

        if ((nX == 1) || (nY == 1)) {

            product = new Gazillion(Integer.toString(x.digits.get(0) * y.digits.get(0))); 
        } else {

            String convertTenPowerOfN = String.valueOf((int) Math.pow(BASE, nX));
            Gazillion tenPowerOfN = new Gazillion(convertTenPowerOfN);
            //System.out.println("10^N: " + tenPowerOfN.toString());

            String convertPowerOfTen = String.valueOf((int) Math.pow(BASE, nX / 2));
            Gazillion powerOfTen = new Gazillion(convertPowerOfTen);
            //System.out.println("10^(N/2): " + powerOfTen.toString());

            // int a = x / (int)Math.pow(BASE, (nX/2));
            // int b = x % (int)Math.pow(BASE, (nX/2));
            String convertA = null;
            String convertB = null;
            for (int i = 0; i < x.digits.size() / 2; i++) {
                convertA += x.digits.get(i);
                convertB += x.least((x.digits.size() / 2) - i - 1);
            }
            Gazillion a = new Gazillion(convertA);
            //System.out.println("a: " + a.toString());
            Gazillion b = new Gazillion(convertB);
            //System.out.println("b: " + b.toString());

            // int c = y / (int)Math.pow(BASE, (nY/2));
            // int d = y % (int)Math.pow(BASE, (nY/2));
            String convertC = null;
            String convertD = null;
            for (int i = 0; i < y.digits.size() / 2; i++) {
                convertC += y.digits.get(i);
                convertD += y.least((x.digits.size() / 2) - i - 1);
            }
            Gazillion c = new Gazillion(convertC);
            //System.out.println("c: " + c.toString());
            Gazillion d = new Gazillion(convertD);
            //System.out.println("d: " + d.toString());

            // int ac = RecursiveMultiplication(a, c);
            Gazillion ac = recursiveMultiplication(a, c);
            //System.out.println("ac: " + ac.toString());
            
            // int ad = RecursiveMultiplication(a, d);
            Gazillion ad = recursiveMultiplication(a, d);
            //System.out.println("ad: " + ad.toString());
            
            // int bc = RecursiveMultiplication(b, c);
            Gazillion bc = recursiveMultiplication(b, c);
            //System.out.println("bc: " + bc.toString());

            // int bd = RecursiveMultiplication(b, d);
            Gazillion bd = recursiveMultiplication(b, d);
            //System.out.println("bd: " + bd.toString());

            // product = (int)Math.pow(BASE, nX) * ac + (int)Math.pow(BASE, (nX / 2)) * (ad
            // + bc) + bd;
            tenPowerOfN.multiply(ac);
            //System.out.println("10^N * ac: " + tenPowerOfN.toString());
            ad.add(bc);
            //System.out.println("ad + bc: " + ad.toString());
            powerOfTen.multiply(ad);
            //System.out.println("10^(N/2) * (ad + bc): " + ad.toString());

            // product = ac + ad + bd;
            tenPowerOfN.add(powerOfTen);
            //System.out.println("ac + ad: " + tenPowerOfN);
            bd.add(tenPowerOfN);
            //System.out.println("... + bd: " + bd);
            product = bd;
            //System.out.println("= " + product);
        }

        return product;
    } // method RecursiveMultiplication

    // Lab assignment using integers
    public static int recursiveMultiplication(int x, int y) {

        if (x == 0 || y == 0) {
            // N = 1
            return x*y;
        }

        int N = (int) Math.log10(x) + 1; // number of digits in x (and y)
        int N2 = (int) Math.log10(y) + 1;

        if ( N == 1 || N2 == 1) {
            return x*y;
        } else {
            int powerOfTen = (int) Math.pow(10,(N/2));
            //System.out.println(powerOfTen);

            int a = x / powerOfTen; // left half of x
            //System.out.println(a);
            int b = x - a * powerOfTen; // right half of x
            //System.out.println(b);
            int c = y / powerOfTen; // left half of y
            //System.out.println(c);
            int d = y - c * powerOfTen; // right half of y
            //System.out.println(d);

            // recursive calls
            int ac = recursiveMultiplication(a,c);
            //System.out.println(ac);
            int ad = recursiveMultiplication(a,d);
            //System.out.println(ad);
            int bc = recursiveMultiplication(b,c);
            //System.out.println(bc);
            int bd = recursiveMultiplication(b,d);
            //System.out.println(bd);

            return powerOfTen*powerOfTen*ac + powerOfTen*(ad+bc) + bd;
        }
    }

    /** Driver */
    public static void main(String[] args) {

        // add() demo
        Gazillion a = new Gazillion("9798298283984209823944820970792685297384298249828402");
        Gazillion b = new Gazillion("2424245242988922424339283493752037827348728782472787");
        a.add(b);
        System.out.println("add(): " + a.toString());

        // multiply() demo
        Gazillion c = new Gazillion("000214");
        Gazillion d = new Gazillion("000053");
        c.multiply(d);
        System.out.println("multiply(): " + c.toString());

        // RecursiveMultiplication() with Gazillion objects demo
        Gazillion e = new Gazillion("1234");
        Gazillion f = new Gazillion("5678");
        Gazillion ef = recursiveMultiplication(e, f);
        System.out.println("recursiveMultiplication() with Gazillion objects: " + ef.toString());

        // RecursiveMultiplication() with integers demo
        int g = 1234;
        int h = 5678;
        System.out.println("recursiveMultiplication() with integers: " + recursiveMultiplication(g, h));

        // HOMEWORK DONE JUST COMMENT
    }
} // class Gazillion