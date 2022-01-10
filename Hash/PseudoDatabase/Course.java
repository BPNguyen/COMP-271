package Hash.PseudoDatabase;

public class Course implements Comparable<Course>{

    private String courseCode;
    private String courseTitle;
    private Course next;

    public Course(String courseCode, String courseTitle) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.next = null;
    }

    public Course(String courseCode, String courseTitle, Course next) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.next = next;
    }

    /**
     * Method to return student information in a string like
     *   courseCode courseTitle
     * @param courseCode courseCode that we wish to obtain information about
     * @returnn String with student information courseCode+" "+courseTitle
     */
    public String getCourseInformation(String courseCode){

        return courseCode + " " + courseTitle;
    }

    /**
     * Implementation of the Comparable interface to compares students.
     *   course1.comparesTo(course2) is
     *     -1 if course1 less than course2
     *      0 if course1 same as course2
     *      1 if course1 more than course2
     * The notions more "less", "same", and "more" are determined by the programmer.
     * @param course Course to compare to
     * @return -1, 0, or 1
     */
    @Override
    public int compareTo(Course course) {

        int comparison = 0;
        return comparison;
    }
}