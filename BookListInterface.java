import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class BookListInterface extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private TableRowSorter<DefaultTableModel> sorter;

    public BookListInterface() {
        setTitle("Library System");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ✅ Fullscreen

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== HEADER PANEL from UIComponents =====
        JPanel header = UIComponents.createHeaderPanel(() -> {
            dispose();
            new StaffDashboard();
        });

        // ===== Top Search Panel =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchTypeComboBox = new JComboBox<>(new String[]{"BookID", "Title", "Author", "ISBN"});
        searchField = new JTextField(15);
        JButton searchBtn = createButton("Search", new Color(72, 201, 176));
        searchBtn.addActionListener(this::performSearch);
        searchField.addActionListener(this::performSearch);

        JButton fictionBtn = createButton("Fiction", new Color(52, 152, 219));
        JButton nonFictionBtn = createButton("Non-Fiction", new Color(241, 196, 15));
        JButton allBtn = createButton("All", new Color(155, 89, 182));

        fictionBtn.addActionListener(e -> filterByCategory("Fiction"));
        nonFictionBtn.addActionListener(e -> filterByCategory("Non-Fiction"));
        allBtn.addActionListener(e -> sorter.setRowFilter(null));

        topPanel.add(searchTypeComboBox);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(fictionBtn);
        topPanel.add(nonFictionBtn);
        topPanel.add(allBtn);

        // ===== Combine Header + Top Panel =====
        JPanel combinedTop = new JPanel();
        combinedTop.setLayout(new BorderLayout());
        combinedTop.add(header, BorderLayout.NORTH);
        combinedTop.add(topPanel, BorderLayout.SOUTH);
        combinedTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(combinedTop, BorderLayout.NORTH);

        // ===== Table Data =====
        String[] columns = {"Book ID", "Title", "Author", "ISBN", "Category", "Status"};
        Object[][] data = {
            {"B001", "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Fiction", "Available"},
            {"B002", "A Brief History of Time", "Stephen Hawking", "9780553380163", "Non-Fiction", "Checked Out"},
            {"B003", "1984", "George Orwell", "9780451524935", "Fiction", "Available"},
            {"B004", "Sapiens", "Yuval Noah Harari", "9780062316097", "Non-Fiction", "Available"},
            {"B005", "To Kill a Mockingbird", "Harper Lee", "9780061120084", "Fiction", "Available"},
            {"B006", "Cosmos", "Carl Sagan", "9780345539434", "Non-Fiction", "Checked Out"},
            {"B007", "Pride and Prejudice", "Jane Austen", "9780141439518", "Fiction", "Available"},
            {"B008", "The Selfish Gene", "Richard Dawkins", "9780192860927", "Non-Fiction", "Available"}
        };

        // ===== Table Setup =====
        tableModel = new DefaultTableModel(data, columns) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(24);
        bookTable.setSelectionBackground(new Color(30, 144, 255));
        sorter = new TableRowSorter<>(tableModel);
        bookTable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Bottom Panel (Action Buttons) =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton addBtn = createButton("Manage Book", new Color(46, 204, 113));
        
        bottomPanel.add(addBtn);
    
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return btn;
    }

    private void performSearch(ActionEvent e) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        int column = switch (searchTypeComboBox.getSelectedItem().toString()) {
            case "BookID" -> 0;
            case "Title" -> 1;
            case "Author" -> 2;
            case "ISBN" -> 3;
            default -> 1;
        };

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(keyword), column));
    }

    private void filterByCategory(String category) {
        sorter.setRowFilter(RowFilter.regexFilter("^" + category + "$", 4));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookListInterface().setVisible(true));
    }
}




