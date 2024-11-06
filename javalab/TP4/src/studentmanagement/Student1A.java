package studentmanagement;

public class Student1A extends StudentECTS {

    public Student1A(String name) {
        super(name);
    }

    @Override
    public int result() {
        return getAverage() >= 12 ? 60 : 0;
    }
}
