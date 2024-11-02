package tp2;

public class Dog extends Pet {

    private int age;
    private String name;
    private String gender;
    private String breed;

    public Dog(int age, String name, String gender, String breed) {
       super(age, name, gender, breed);
    }

    public void bark()
    {
        System.out.println(this.name + " : Wouf Wouf");
    }
}
