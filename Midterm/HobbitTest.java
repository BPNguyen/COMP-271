package Midterm;

public class HobbitTest {

    public static void main(String[] args) {
        
        HobbitGenerator demo = new HobbitGenerator();
        System.out.println(demo.hobbitName("Brian Nguyen"));
        System.out.println(demo.hobbitName("Isaac Nara"));
        System.out.println(demo.hobbitName("Isaac Ira"));
        System.out.println(demo.hobbitName("Roy G Biv"));

        System.out.println(demo.hobbitName("Jack Hutchinson"));
    }
}