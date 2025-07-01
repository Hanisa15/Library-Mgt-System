import java.nio.file.*;
import java.io.*;
import java.util.*;

public class ManageBook {
    private final ArrayList<GeneralBook> books = new ArrayList<>();
    private int fictionCount    = 100;
    private int nonfictionCount = 100;
    
    //1. Load books from file dulu
    public ManageBook() {
        loadBooksFromFile();
    }
    
// books.txt: category, bookID, title, author, isbn, quantity, pubYear, uniqueAtt, status
    private void loadBooksFromFile() {
        Path dataFile = Paths.get("data", "books.txt");
        if (!Files.exists(dataFile)) return;

        try (BufferedReader reader = Files.newBufferedReader(dataFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 9); 
                if (parts.length < 9) continue; //ada 9 attr nak masukkan

                String type      = parts[0].trim(); //trim just in case ada space
                String bookID    = parts[1].trim();
                String title     = parts[2].trim();
                String author    = parts[3].trim();
                String isbn      = parts[4].trim();
                int    stock     = Integer.parseInt(parts[5].trim());
                int    year      = Integer.parseInt(parts[6].trim());
                String uniqueAtt = parts[7].trim();
                String status = parts[8].trim();

                if (type.equalsIgnoreCase("Fiction")) {
                    books.add(new FictionBook(bookID, title, author, isbn,  stock, year,uniqueAtt));
                    fictionCount = Math.max(fictionCount, Integer.parseInt(bookID.substring(1)) + 1);
                }
                else if (type.equalsIgnoreCase("Non-Fiction")){
                    books.add(new NonFictionBook(bookID, title, author, isbn,  stock, year,uniqueAtt));
                    nonfictionCount = Math.max(nonfictionCount, Integer.parseInt(bookID.substring(2)) + 1);
                }
            }
            System.out.println("Loaded " + books.size() + " books from file.");
        }
        catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
    }

    private boolean idExists(String id) {
        for (GeneralBook b : books) {
            if (b.getBookID().equals(id)) return true;
        }
        return false;
    }

    private String getNextBookID(int type) {
        String id;
        if (type == 1) {
            do { 
                id = "F"  + fictionCount++; 
            } while (idExists(id));
        } else {
            do { 
                id = "NF" + nonfictionCount++; 
            } while (idExists(id));
        }
        return id;
    }
    
    //for ARRAYLIST BOOK
    public boolean addNewBook(int type,
                              String title,
                              String author,
                              String isbn,
                              int stockQuantity,
                              int pubYear,
                              String uniqueAtt) {
        //next book GET next ID from method > getNextBookID()
        String bookID = getNextBookID(type);

        if (type == 1) {
            books.add(new FictionBook(bookID, title, author, isbn, stockQuantity, pubYear, uniqueAtt));
        } else if (type == 2) {
            books.add(new NonFictionBook(bookID, title, author, isbn, stockQuantity, pubYear, uniqueAtt));
        } else {
            return false;
        }
        
        return writeBooksToFile();
    }

    private boolean writeBooksToFile() {
        try {
            Path dataDir  = Paths.get("data");
            Path dataFile = dataDir.resolve("books.txt"); // combine the main path with. currpath
            Files.createDirectories(dataDir);

            try (BufferedWriter writer = Files.newBufferedWriter(dataFile)) {
                for (GeneralBook b : books) {
                    writer.write(String.join(",",
                        b.getCategory(),
                        b.getBookID(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getIsbn(),
                        Integer.toString(b.getQuantity()),
                        Integer.toString(b.getPublicationYear()),
                        b.UniqueAttribute(),
                        b.getStatus()
                    ));
                    writer.newLine();
                }
            }
            return true;
        }
        catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the collection.");
            return;
        }
        System.out.println("\n--- All Books ---");
        for (GeneralBook b : books) {
            System.out.println(b);
        }
        System.out.println("Total: " + books.size());
    }

    // handy helper
    public String getLastAddedBookId() {
        if (books.isEmpty()) return "N/A";
        return books.get(books.size() - 1).getBookID();
    }
}
