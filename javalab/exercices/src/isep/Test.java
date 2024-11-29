package isep;

public class Test {
    private String artist;
    private String title;
    private int year;
    private String description;

    // Constructor
    public Test(String artist, String title, int year) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.description = artist + " a sorti en " + year + " l'album " + title + ".";
    }

    // Method to get the description
    public String getDescription() {
        return description;
    }

    // Main method for testing
    public static void main(String[] args) {
        Test album = new Test("Pink Floyd", "The Dark Side of the Moon", 1973);
        String description = album.getDescription();
        System.out.println(description);  // Output: Pink Floyd a sorti en 1973 l'album The Dark Side of the Moon.
    }
}
