package studentmanagement;

public class TestStudent {

    public static void main(String[] args) {
        // Create some students
        Student student1 = new Student("Mihail");
        student1.addGrade(15);
        student1.addGrade(18);

        Student student2 = new Student("Larissa");
        student2.addGrade(10);
        student2.addGrade(12);

        Student student3 = new Student("Greg");
        student3.addGrade(20);
        student3.addGrade(19);

        Student student4 = new Student("Vlad");
        student4.addGrade(8);
        student4.addGrade(14);

        // Create a StudentGroup and add students to it
        StudentGroup group = new StudentGroup();
        group.addStudent(student1);
        group.addStudent(student2);
        group.addStudent(student3);
        group.addStudent(student4);

        // Find the best student
        System.out.println("Best student: " + group.bestStudent());

        // Sort students and print the sorted list
        group.sortStudents();
        System.out.println("\nSorted students:");
        System.out.println(group);
    }
}
