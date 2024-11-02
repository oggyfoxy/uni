package tp2;

public class Cat extends Pet {

    private int age;
    private String name;
    private String gender;
    private String breed;

    public Cat(int age, String name, String gender, String breed) {
        super(age, name, gender, breed);
    }

    public void meow() {
        System.out.println(this.name + " : meow :3");
    }
}
