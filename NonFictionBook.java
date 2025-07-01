
/**
 * Write a description of class Fiction here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
class NonFictionBook extends GeneralBook
{
    private String subject;
    //String bookID,String BookTitle,String isbn,String author,int stockQuantity,int pubYear
    public NonFictionBook(String bookID, String bookTitle, String author, 
                            String isbn, int stockQuantity, int pubYear, String subject){
       super(bookID,bookTitle,author, isbn, stockQuantity,pubYear);
       this.subject = subject;
    }

    
    public String UniqueAttribute() {return subject;};
    
    public String getCategory() {
        return "Non-Fiction";
    }
    
    
    public String toString() {
        return super.toString() //same
             + ", Subject:" + subject
             + " ("+ getCategory() +")";
    }
}
