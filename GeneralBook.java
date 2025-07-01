
/**
 * Write a description of class GeneralBook here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
abstract class GeneralBook
{
    private String bookID;
    private String bookTitle;
    private String author;
    private String isbn;
    private int stockQuantity;
    private int pubYear;
    private String bookStatus;
    
    //constructor
    public GeneralBook(String bookID,String bookTitle,String author, String isbn,int stockQuantity,int pubYear){
        this.bookID = bookID;
        this.bookTitle=bookTitle;
        this.author=author;
        this.isbn=isbn;
        this.stockQuantity=stockQuantity;
        this.pubYear=pubYear;
        //Retrieve curr status dari setQuantity(stockQuantity)
        setQuantity(stockQuantity);
        
    }
    //get and set
    public String getBookID() { return bookID; }
    public String getTitle() { return bookTitle; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public int getQuantity() { return stockQuantity; }
    public int getPublicationYear() { return pubYear; }
    public String getStatus() { return bookStatus; }
    
    public void setTitle(String title) { this.bookTitle = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setQuantity(int stockQuantity){ 
        this.stockQuantity = stockQuantity;
        //book quantity calculation
        if(stockQuantity>0){
            this.bookStatus = "Available";
        }else{
            this.bookStatus = "Unavailable";
        }
    }
    public void setPublicationYear(int publicationYear){ this.pubYear = publicationYear;}
    
    //POLYMOR 
    public abstract String UniqueAttribute();
    //Nak panggil differ category (fiction an dnon fiction nnt)
    public abstract String getCategory();
    
    //string 
    public String toString()
    {
        return"Book ID: "+ bookID +
            ",Book Title:" + bookTitle +
            ",Author: " + author+
            ",ISBN : " + isbn +
            ",Quantity: " + stockQuantity +
            ",Publication Year: " + pubYear +
            ",Status: " + bookStatus;
    }
    
}
