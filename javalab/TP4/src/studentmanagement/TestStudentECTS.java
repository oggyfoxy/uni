package studentmanagement;

import java.util.ArrayList;

public class TestStudentECTS {

    public static void main(String[] args) {
        ArrayList<StudentECTS> students = new ArrayList<>();

        Student1A student1 = new Student1A("Ruben");
        student1.addGrade(15);
        student1.addGrade(14);
        students.add(student1);

        MasterStudent student2 = new MasterStudent("Anwar");
        student2.addGrade(10);
        student2.addGrade(9);
        student2.addGrade(8);
        students.add(student2);

        // Display results
        for (StudentECTS student : students) {
            System.out.println("Name: " + student.getName());
            System.out.println("Average: " + student.getAverage());
            System.out.println("ECTS Credits Earned: " + student.result());
            System.out.println();
        }
    }
}
