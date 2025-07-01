import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ReturnBookPanel extends JPanel {
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel model;
    private java.util.List<BookIssueRecord> records;
    private BookIssueRecord selectedRecord;
    private Runnable onBack; //go to prev page

    public ReturnBookPanel(Runnable onBack) {
        this.onBack = onBack;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter Student ID: "));
        searchField = new JTextField(15);
        topPanel.add(searchField);
        JButton searchBtn = new JButton("Search");
        topPanel.add(searchBtn);        
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Student ID", "Name", "Borrow Date", "Return Date", "Books"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton returnBtn = new JButton("Return");
        JButton lateBtn = new JButton("Late");
        JButton bckBtn = new JButton("Back");
        bottomPanel.add(returnBtn);
        bottomPanel.add(lateBtn);
        bottomPanel.add(bckBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        searchBtn.addActionListener(e -> searchRecords());

        returnBtn.addActionListener(e -> {
            if (selectedRecord != null) deleteRecord(selectedRecord.studentID);
        });

        lateBtn.addActionListener(e -> {
            if (selectedRecord != null) showLatePopup(selectedRecord);
        });
        
        bckBtn.addActionListener(e -> {
            if(onBack != null) onBack.run();//close current window
            //open Book Issue Form GUI - create new JFrame
            JFrame iF = new JFrame("BOOK ISSUE SYSTEM");
            iF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            iF.setSize(900,600);
            iF.setLocationRelativeTo(null);
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && row < records.size()) {
                selectedRecord = records.get(row);
            }
        });
    }

    private void searchRecords() {
        String id = searchField.getText().trim();
        records = new ArrayList<>();
        model.setRowCount(0);

        try (BufferedReader reader = new BufferedReader(new FileReader("Book Issue Record.txt"))){
            String line;
            String sid = "", name = "", bDate = "", rDate = "";
            java.util.List<String> books = new ArrayList<>();
            boolean match = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("STUDENT_ID:")) {
                    sid = line.substring(11).trim();
                    match = sid.equals(id);
                } else if (line.startsWith("NAME:") && match) {
                    name = line.substring(5).trim();
                } else if (line.startsWith("BORROW_DATE:") && match) {
                    bDate = line.substring(12).trim();
                } else if (line.startsWith("RETURN_DATE:") && match) {
                    rDate = line.substring(12).trim();
                } else if (line.startsWith("BOOK_") && match) {
                    books.add(line.split(":", 2)[1].trim());
                } else if (line.equals("---") && match) {
                    String[] bookArr = books.toArray(new String[0]);
                    BookIssueRecord record = new BookIssueRecord(sid, name, bDate, rDate, bookArr);
                    records.add(record);
                    model.addRow(new Object[]{sid, name, bDate, rDate, Arrays.toString(bookArr)});
                    books.clear();
                    match = false;
                }
            }

        }catch (IOException e) 
        {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
        }
    }

    private void deleteRecord(String studentID) {
        File inputFile = new File("Book Issue Record.txt");
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean skip = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("STUDENT_ID:")) {
                    skip = line.contains(studentID);
                }
                if (!skip) writer.write(line + "\n");
                if (line.equals("---")) skip = false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating file.");
            return;
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
        searchRecords();
    }

    private void showLatePopup(BookIssueRecord record) {
        String actualDate = JOptionPane.showInputDialog(this, "Enter Actual Return Date [dd-MM-yyyy]:");
        BookReturnManager m = new BookReturnManager();
        double fine = m.calculateFine(record.returnDate, actualDate);
        if (fine >= 0) {
            long daysLate = (long)(fine / 2); // Assuming RM2 per day
            JOptionPane.showMessageDialog(this, "Late by " + daysLate + " day(s).\nFine: RM" + fine);
            model.setRowCount(0);
            model.addRow(new Object[]{record.studentID, record.studentName, record.borrowDate, actualDate, Arrays.toString(record.books)});
        } else {
            JOptionPane.showMessageDialog(this, "Invalid date format.");
        }
    }

    //inner class
    private static class BookIssueRecord {
        String studentID;
        String studentName;
        String borrowDate;
        String returnDate;
        String[] books;

        public BookIssueRecord(String studentID, String studentName, String borrowDate, String returnDate, String[] books) {
            this.studentID = studentID;
            this.studentName = studentName;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            this.books = books;
        }
    }
}

/*import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.BorderFactory.*;
import javax.swing.border.Border.*;

public class ReturnBookPanel extends JPanel {
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel model;
    private java.util.List<BookIssueRecord> records;
    private BookIssueRecord selectedRecord;
    private Runnable onBack;

    public ReturnBookPanel(Runnable onBack) {
        this.onBack = onBack;
        setLayout(new BorderLayout());
        setOpaque(false); // Transparent for background
        
        // ===== 1. Modern Top Panel =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setOpaque(false);
        
        JLabel label = new JLabel("Enter Student ID:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        
        searchField = new JTextField(20);
        styleTextField(searchField); // Custom styling
        
        JButton searchBtn = new JButton("🔍 Search");
        styleButton(searchBtn, new Color(70, 130, 180));
        
        topPanel.add(label);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        // ===== 2. Modern Table =====
        model = new DefaultTableModel(new String[]{"Student ID", "Name", "Borrow Date", "Return Date", "Books"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing
            }
        };
        
        table = new JTable(model);
        styleTable(table); // Zebra stripes, hover effects
        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);

        // ===== 3. Action Buttons =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setOpaque(false);
        
        JButton returnBtn = new JButton("✓ Return");
        JButton lateBtn = new JButton("⚠ Late");
        JButton bckBtn = new JButton("← Back");
        
        styleButton(returnBtn, new Color(46, 125, 50)); // Green
        styleButton(lateBtn, new Color(211, 84, 0));   // Orange
        styleButton(bckBtn, new Color(120, 120, 120)); // Gray
        
        bottomPanel.add(returnBtn);
        bottomPanel.add(lateBtn);
        bottomPanel.add(bckBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // ===== 4. Event Listeners =====
        searchBtn.addActionListener(e -> searchRecords());
        searchField.addActionListener(e -> searchRecords()); // Search on Enter
        
        returnBtn.addActionListener(e -> confirmReturn());
        lateBtn.addActionListener(e -> {
            if (selectedRecord != null) showLatePopup(selectedRecord);
        });
        bckBtn.addActionListener(e -> onBack.run());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && row < records.size()) {
                selectedRecord = records.get(row);
            }
        });
    }

    // ===== STYLING METHODS =====
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200),
            BorderFactory.createEmptyBorder(5,10,5,10)
        )));
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(brighter(bgColor, 20));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
    }
    
    private Color brighter(Color c, int amount) {
        return new Color(
            Math.min(c.getRed() + amount, 255),
            Math.min(c.getGreen() + amount, 255),
            Math.min(c.getBlue() + amount, 255)
        );
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setSelectionBackground(new Color(220, 240, 255));
        
        // Zebra stripes
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                   boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? new Color(240, 240, 240, 150) : new Color(220, 220, 220, 150));
                if (isSelected) c.setBackground(new Color(180, 220, 255));
                return c;
            }
        });
    }*/

    // ===== CORE FUNCTIONALITY (Existing methods remain the same) =====
   /* private void searchRecords() { /* ... */ //}
    //*private void deleteRecord(String studentID) { /* ... */ }
    //private void showLatePopup(BookIssueRecord record) { /* ... */ }
    //private void confirmReturn() { /* ... */ }
    
    //private static class BookIssueRecord { /* ... */ }
//}
