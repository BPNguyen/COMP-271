/**
 * A class to provide a passenger manifest using realistic
 * first and last names, using popular (ie) frequent first
 * and last names in the US. The class comprises three String
 * arrays with common masculine and feminine first names. In
 * a tribute to my COMP 170 SP 20 students -- the first class
 * to go online due to COVID-19, in March 2020 -- I am using
 * their last names in array lastnames[].
 *
 * The class comprises a method that returns a randomly generated
 * name, with a 50-50 probability for a male or female person.
 *
 * To use this class, instantiate it first, then access its
 * .realisticName() method.
 *
 */

package AirlineSeating;

import java.util.Random;

public class RealisticNameGenerator {

    private static final String[] lastNames = {
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Garcia",
            "Miller",
            "Davis",
            "Rodriguez",
            "Martinez",
            "Hernandez",
            "Lopez",
            "Gonzalez",
            "Wilson",
            "Anderson",
            "Thomas",
            "Taylor",
            "Moore",
            "Jackson",
            "Martin",
            "Lee",
            "Irakliotis"};

    private static final String[] firstNamesMale = {
            "James",
            "John",
            "Robert",
            "Michael",
            "William",
            "David",
            "Richard",
            "Charles",
            "Joseph",
            "Thomas",
            "Christopher",
            "Daniel",
            "Paul",
            "Mark",
            "Donald",
            "George",
            "Kenneth",
            "Steven",
            "Edward",
            "Brian",
            "Leo"
    };

    private static final String[] firstNameFemale = {
            "Mary",
            "Patricia",
            "Linda",
            "Barbara",
            "Elizabeth",
            "Jennifer",
            "Maria",
            "Susan",
            "Margaret",
            "Dorothy",
            "Lisa",
            "Nancy",
            "Karen",
            "Betty",
            "Helen",
            "Sandra",
            "Donna",
            "Carol",
            "Ruth",
            "Sharon",
            "Michelle",
    };

    /**
     * Method realisticName is the principal method of this class. Users
     * may access this class' main service only through this method. It
     * returns a String[2] array, where the first element is a realistic
     * first  name and the second element is a realistic last name. The
     * gender for the first name is determined by a coin toss.
     * @return realisticName Contains first and last name
     */
    public String[] realisticName () {

        /**
         * Sizes of the name arrays. These are necessary and parameters
         * for Random.nextInt().
         */
        int lastNamesCount = lastNames.length;
        int maleFirstNamesCount = firstNamesMale.length;
        int femaleFirstNamesCount = firstNameFemale.length;

        /**
         * A local variable to be used as a coin flip to determine
         * if the output will be a masculine or feminine first name.
         */
        int gender;

        /**
         * Local array with two elements for first and last name,
         * respectively. This is the item returned by this method.
         */
        String[] name = new String[2];

        /** Instantiate random number class */
        Random rand = new Random();

        /** Flip a coid; 0 is female; 1 is male */
        gender = rand.nextInt(2);

        /** Select a last name at random */
        name[1] = lastNames[rand.nextInt(lastNamesCount)];  // last names common to both genders

        /**
         * Select a first name at random, from the male or female
         * array, based on the value of the gender "coin flip"
         */
        if (gender == 0) {                     // female first name
            name[0] = firstNameFemale[rand.nextInt(femaleFirstNamesCount)];
        } else {                               // male first name
            name[0] = firstNamesMale[rand.nextInt(maleFirstNamesCount)];
        }

        /* question for class: how to bias towards female or male first names? */
        return name;
    }

    /* Method main() below is for local testing only */
    public static void main(String[] args) {
        final int N = 20;
        RealisticNameGenerator realNames = new RealisticNameGenerator();
        String[] realName = new String[2];
        for (int i = 0;  i<N; i++) {
            realName = realNames.realisticName();
            System.out.println(realName[0] + " " + realName[1]);
        }
    }
}

