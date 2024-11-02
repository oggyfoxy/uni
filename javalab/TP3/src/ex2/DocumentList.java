package ex2;

import java.util.List;
import java.util.ArrayList;

public class DocumentList {

    private List<Document> documents;

    public DocumentList() {
        documents = new ArrayList<>();
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void allAuthors() {
        for (Document d : documents ) {
            if (d instanceof Book) {
                Book book = (Book) d;
                System.out.println("Registration Number: " + d.getRegNumber() + " Author: " + book.getAuthor());
            }
            else {
                System.out.println("Registration Number: " + d.getRegNumber() +  " no author found");
            }
        }
    }

    public void allDocuments() {
        for (Document d : documents) {
            System.out.println(d.toString());
        }
    }

}
