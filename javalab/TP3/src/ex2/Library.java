package ex2;

public class Library {
    public static void main(String[] args) {

        DocumentList documentList = new DocumentList();

        documentList.addDocument(new Document("DOC1", "main doc"));
        documentList.addDocument(new Book("BOOK1", "Java prog", "Raph", 250));
        documentList.addDocument(new Dictionary("DICT101", "English Dictionary", "English", 3));

        documentList.allAuthors(); // can test any of those classes
        documentList.allDocuments();
    }
}