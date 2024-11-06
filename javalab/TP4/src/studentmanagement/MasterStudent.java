package studentmanagement;

public class MasterStudent extends StudentECTS {

    public MasterStudent(String name) {
        super(name);
    }

    @Override
    public int result() {
        int totalCredits = 0;
        for (double grade : gradesList) {
            if (grade >= 10) {
                totalCredits += 6;
            } else if (grade >= 8) {
                totalCredits += 3;
            }
        }
        return totalCredits;
    }
}
