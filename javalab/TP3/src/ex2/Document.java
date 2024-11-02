package ex2;

public class Document {

    private String regNumber;
    private String title;

    public Document(String regNumber, String title) {
        this.regNumber = regNumber;
        this.title = title;
    }

    public String getRegNumber() {

        return regNumber;
    }

    public String getTitle() {

        return title;
    }

    @Override
    public String toString() {
        return "Document[Registration Number: " + regNumber + ", Title: " + title + "]";
    }

}