package Hash.PseudoDatabase;

import java.util.Random;

public class Database {

    /** Underlying array for student records */
    Student[] students = new Student[10];

    /** Underlying array for course records */
    Course[] courses = new Course[10];

    /** Underlying array for registration transactions */
    Registration[] registrations = new Registration[10];

    /** Inner class for registration objects */
    class Registration{
        private String courseCode;
        private String studentID;
        private Registration next;
    }

    /** Hash function ... since all hashable data in this problem are strings,
     * you may use a single hash function. Not the brightest idea but an
     * expeditious one given the limited time we have in the lab session.
     *
     * (NB: this was assigned as a lab project on 10/28/2020)
     *
     * (HINT: the String class has a hashcode method built-in. You may start
     * with that or you may ignore it completely).
     */
    private int hashFunction(String key) {

        return key.hashCode();
    }

    public boolean contains(String value) {
        boolean found = false;
        int bucket = hashFunction(value);
        Registration current = registrations[bucket];
        while (current != null) {
            if (current.studentID == value) {
                found = true;
            }
            current = current.next;
        }
        return found;
    }

    public boolean createNewStudentRecord(String studentName) {

        boolean success = false;

        // Some awesome code from the best section of COMP 271 ever
        
        int lastIndex = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                lastIndex = i;
                break;
            }
        }

        String studentID = "LUC00" + lastIndex;

        if (contains(studentName)) {

            forceNewStudentRecord(studentName, studentID);
        } else {
            int bucket = hashFunction(studentName);
            students[bucket] = new Student(studentID, studentName, students[bucket]); // LIFO
        }
        
        return success;
    }

    public void forceNewStudentRecord(String studentName, String studentID) {
        // more amazing code ... By the way, do we need two separate methods to
        // create and force a new student record, really?

        int bucket = hashFunction(studentID);
        students[bucket] = new Student(studentID, studentName, students[bucket]);
    }

    public boolean createNewCourseRecord(String courseTitle) {

        boolean success = false;

        Random rand = new Random();
        String courseCode = "COMP" + (rand.nextInt(300) + 100);

        if (!contains(courseTitle)) {
            int bucket = hashFunction(courseTitle);
            courses[bucket] = new Course(courseCode, courseTitle, courses[bucket]);
        }

        return success;
    }

    public void perStudentReport() {
        // HINT:
        // Students should be listed in alphabetical order by last name; before we get there
        // let's just list them in the way we can get them to get some functionality
        // going. Then add the alphabetical ordering sophistication.
    }
}