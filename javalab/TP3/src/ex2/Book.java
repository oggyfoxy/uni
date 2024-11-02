package ex2;

public class Book extends Document {

    private String author;
    private int pageCount;

    public Book(String regNumber, String title, String author, int pageCount) {
        super(regNumber, title);
        this.author = author;
        this.pageCount = pageCount;

    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    // Override toString() to return a description of a Book
    @Override
    public String toString() {
        return "Book[Registration Number: " + getRegNumber() +
                ", Title: " + getTitle() +
                ", Author: " + author +
                ", Pages: " + pageCount + "]";
    }

}