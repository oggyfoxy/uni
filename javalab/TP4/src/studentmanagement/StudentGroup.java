package studentmanagement;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class StudentGroup {

    private ArrayList<Student> studentList;

    public StudentGroup() {
        this.studentList = new ArrayList<>();
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public Student bestStudent() {
        return Collections.max(studentList);
    }

    public void sortStudents() {
        Collections.sort(studentList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StudentGroup:\n");
        for (Student student : studentList) {
            sb.append(student).append("\n");
        }
        return sb.toString();
    }
}
