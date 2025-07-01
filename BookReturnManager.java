import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BookReturnManager {
    private static final int GRACE_DAYS = 0;
    private static final double FINE_PER_DAY = 2.00;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //Save issued book record
    public static void saveIssueRecord(String studentId, String name, String borrowDate, String returnDate, String[][] books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Book Issue Record.txt", true))) {
            bw.write("STUDENT_ID: " + studentId); bw.newLine();
            bw.write("NAME: " + name); bw.newLine();
            bw.write("BORROW_DATE: " + borrowDate); bw.newLine();
            bw.write("RETURN_DATE: " + returnDate); bw.newLine();

            for (int i = 0; i < books.length; i++) {
                bw.write("BOOK_" + (i + 1) + ": " + books[i][0] + " - " + books[i][1]);
                bw.newLine();
            }
            bw.write("---"); bw.newLine(); bw.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //Fine calculation
    public static double calculateFine(String expectedReturnDate, String actualReturnDate) {
        try {
            LocalDate expected = LocalDate.parse(expectedReturnDate, formatter);
            LocalDate actual = LocalDate.parse(actualReturnDate, formatter);

            long lateDays = ChronoUnit.DAYS.between(expected, actual);
            return lateDays > GRACE_DAYS ? (lateDays * FINE_PER_DAY) : 0.0;

        } catch (Exception e) {
            System.out.println("Date error: " + e.getMessage());
            return -1.0;
        }
    }
}
/*
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.Properties;

public class BookReturnManager {
    private static int GRACE_DAYS;
    private static double FINE_PER_DAY;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    static {
        loadFineRules(); // Initialize config
    }

    // ==== 1. Configurable Rules ====
    private static void loadFineRules() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("library.properties")) {
            props.load(input);
            GRACE_DAYS = Integer.parseInt(props.getProperty("grace_days", "0"));
            FINE_PER_DAY = Double.parseDouble(props.getProperty("fine_per_day", "2.00"));
        } catch (IOException e) {
            System.err.println("Using default fine rules");
            GRACE_DAYS = 0;
            FINE_PER_DAY = 2.00;
        }
    }

    // ==== 2. Safe File Writing ====
    public static void saveIssueRecord(String studentId, String name, 
                                     String borrowDate, String returnDate, 
                                     String[][] books) throws LibraryException {
        File tempFile = new File("Book Issue Record.tmp");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile, true))) {
            writeRecord(bw, studentId, name, borrowDate, returnDate, books);
            commitChanges(tempFile);
        } catch (IOException e) {
            tempFile.delete();
            throw new LibraryException("Failed to save record: " + e.getMessage());
        }
    }

    private static void writeRecord(BufferedWriter bw, String studentId, String name,
                                  String borrowDate, String returnDate, 
                                  String[][] books) throws IOException {
        bw.write("STUDENT_ID: " + studentId); bw.newLine();
        bw.write("NAME: " + name); bw.newLine();
        bw.write("BORROW_DATE: " + borrowDate); bw.newLine();
        bw.write("RETURN_DATE: " + returnDate); bw.newLine();
        for (int i = 0; i < books.length; i++) {
            bw.write("BOOK_" + (i+1) + ": " + books[i][0] + " - " + books[i][1]);
            bw.newLine();
        }
        bw.write("---"); bw.newLine(); bw.newLine();
    }

    private static void commitChanges(File tempFile) throws IOException {
        File targetFile = new File("Book Issue Record.txt");
        if (targetFile.exists() && !targetFile.delete()) {
            throw new IOException("Could not delete old file");
        }
        if (!tempFile.renameTo(targetFile)) {
            throw new IOException("Could not rename temp file");
        }
    }

    // ==== 3. Enhanced Fine Calculation ====
    public static double calculateFine(String expectedReturnDate, String actualReturnDate) {
        try {
            LocalDate expected = parseDate(expectedReturnDate);
            LocalDate actual = parseDate(actualReturnDate);
            long lateDays = ChronoUnit.DAYS.between(expected, actual);
            return Math.max(0, lateDays - GRACE_DAYS) * FINE_PER_DAY;
        } catch (Exception e) {
            return -1; // Indicates error
        }
    }

    private static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, formatter);
    }

    // ==== 4. Receipt Generation ====
    public static void generateReceipt(String studentId, String actualDate, double fine) 
        throws LibraryException {
        String receipt = String.format(
            "LIBRARY RECEIPT\nStudent ID: %s\nReturn Date: %s\nFine: RM%.2f",
            studentId, actualDate, fine
        );
        try (PrintWriter pw = new PrintWriter("Receipt_" + studentId + ".txt")) {
            pw.println(receipt);
        } catch (FileNotFoundException e) {
            throw new LibraryException("Could not generate receipt");
        }
    }

    public static class LibraryException extends Exception {
        public LibraryException(String message) {
            super(message);
        }
    }
}
*/