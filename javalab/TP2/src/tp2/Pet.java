package tp2;

public class Pet {

    protected String name;
    protected int age;
    protected String gender;
    protected String breed;

    public Pet(int age, String name, String gender, String breed) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

   public int getAge() {
        return age;
   }

   public String getGender() {
        return gender;
   }

   public String getBreed() {
        return breed;
   }
}
