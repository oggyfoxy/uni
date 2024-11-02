package ex2;

public class Dictionary extends Document {

    private String language;
    private int numVolumes;

    public Dictionary(String regNumber, String title, String language, int numVolumes) {
        super(regNumber, title);
        this.language = language;
        this.numVolumes = numVolumes;

    }

    public String getLanguage() {

        return language;
    }

    public int getNumVolumes() {

        return numVolumes;
    }

    @Override
    public String toString() {
        return "Dictionary[Registration Number: " + getRegNumber() +
                ", Title: " + getTitle() +
                ", Language: " + language +
                ", Volumes: " + numVolumes + "]";
    }
}