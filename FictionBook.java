
/**
 * Write a description of class Fiction here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
class FictionBook extends GeneralBook
{
    private String genre;
    
    public FictionBook(String bookID, String bookTitle, String author, String isbn,
                        int stockQuantity, int pubYear, String genre){
       super(bookID,bookTitle,author, isbn,stockQuantity,pubYear);
       this.genre = genre;
    }
    
    public String UniqueAttribute() {return genre;};
    
    public String getCategory() {
        return "Fiction";
    }
    
    
    public String toString() {
        return super.toString() //override method toString from generalbook(superclass)
             + ", Genre:" + genre
             + " ("+ getCategory() +")";
    }
}
