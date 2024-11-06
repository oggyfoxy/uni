package studentmanagement;

import java.util.ArrayList;

public class Student implements Comparable<Student> {

    private String name;
    protected ArrayList<Double> gradesList;
    private double average;

    public Student(String name) {
        this.name = name;
        this.gradesList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addGrade(double grade) {
        if (grade < 0)
            grade = 0;
        else if (grade > 20)
            grade = 20;
        gradesList.add(grade);

        double sum = 0;
        for (double g : gradesList)
            sum += g;
        average = sum / gradesList.size();
    }

    public double getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", average=" + average + "]";
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(average, o.average);
    }
}
