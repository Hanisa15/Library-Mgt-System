/*import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;

class BackgroundPanelView extends JPanel {
    private Image backgroundImage;
    private DefaultTableModel model;
    private java.util.List<BookIssueRecord> records;
    private BookIssueRecord selectedRecord;

    public BackgroundPanelView(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class ViewChargesPage extends JPanel {

    public ViewChargesPage(JFrame mainFrame) {
        setLayout(new BorderLayout());

        // ✅ Add background panel
        BackgroundPanelView background = new BackgroundPanelView("/IMG_0191.JPG"); // Ensure image is in resources
        background.setLayout(new BorderLayout());
        add(background);

        // ✅ Add shared header
        background.add(UIComponents.createHeaderPanel(() -> {
            mainFrame.dispose();
            new StaffDashboard(); // go back to staff dashboard
        }), BorderLayout.NORTH);

        // ✅ Add ViewCharges content
        background.add(new ViewChargesPanel(), BorderLayout.CENTER);
    }

    // ---------------- VIEW CHARGES PANEL ----------------
    static class ViewChargesPanel extends JPanel {
        private JTextField studentIdField;
        private JTable table;
        private DefaultTableModel tableModel;

        public ViewChargesPanel() {
            setLayout(new BorderLayout(10, 10));
            setOpaque(false);
            setBorder(new EmptyBorder(20, 20, 20, 20));

            JPanel content = new JPanel(new BorderLayout(10, 10));
            content.setBackground(Color.WHITE);
            content.setBorder(new EmptyBorder(20, 20, 20, 20));

            // TOP PANEL
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            topPanel.setOpaque(false);

            JLabel label = new JLabel("VIEW CHARGES");
            label.setFont(new Font("Segoe UI", Font.BOLD, 18));
            label.setForeground(new Color(36, 42, 63));
            topPanel.add(label);

            JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
            searchPanel.setOpaque(false);

            studentIdField = new JTextField(15);
            studentIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            studentIdField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200)),
                    new EmptyBorder(5, 5, 5, 5)
            ));

            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            searchButton.setBackground(new Color(70, 130, 180));
            searchButton.setForeground(Color.WHITE);
            searchButton.setFocusPainted(false);
            searchButton.setBorder(new LineBorder(new Color(50, 90, 130), 1));

            searchButton.addActionListener(e -> performSearch());
            

            searchPanel.add(studentIdField, BorderLayout.CENTER);
            searchPanel.add(searchButton, BorderLayout.EAST);
            topPanel.add(searchPanel);

            // TABLE
            String[] columnNames = {"Title", "Book ID", "Quantity", "Status"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(tableModel);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
            table.setRowHeight(25);
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(new LineBorder(new Color(200, 200, 200)));

            content.add(topPanel, BorderLayout.NORTH);
            content.add(scrollPane, BorderLayout.CENTER);
            add(content, BorderLayout.CENTER);
        }

        private void performSearch() {
            String studentId = studentIdField.getText().trim();

            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           
            

            int daysLate = 5; // test value
            int fine = calculateFine(daysLate);

            JOptionPane.showMessageDialog(this,
                    daysLate > 3 ? "Student has a fine of RM " + fine : "No fine. Book returned within 3 days.",
                    "Fine Info", JOptionPane.INFORMATION_MESSAGE);
        }   
        
        private void searchRecords() {
            String id = searchField.getText().trim();
            records = new ArrayList<>();
            tableModel.setRowCount(0);

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
                    }else if (line.startsWith("BORROW_DATE:") && match) {
                        bDate = line.substring(12).trim();
                    } else if (line.startsWith("RETURN_DATE:") && match) {
                        rDate = line.substring(12).trim();
                    } else if (line.startsWith("BOOK_") && match) {
                        books.add(line.split(":", 2)[1].trim());
                    } else if (line.equals("---") && match) {
                        String[] bookArr = books.toArray(new String[0]);
                        BookIssueRecord record = new BookIssueRecord(sid, name, bDate, rDate, bookArr);
                        records.add(record);
                        tableModel.addRow(new Object[]{sid, name, bDate, rDate, Arrays.toString(bookArr)});
                        books.clear();
                        match = false;
                    }
                }

            }catch (IOException e) 
            {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
            }
        }

        private String calculateStatus(int quantity) {
            return (quantity == 0) ? "Unavailable" : "Available";
        }

        private int calculateFine(int daysLate) {
            return (daysLate > 3) ? 5 : 0;
        }
    }

    // ---------------- TEST RUN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agile Access Library Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
            frame.setUndecorated(true); // Optional: hide window border
            frame.setContentPane(new ViewChargesPage(frame));
            frame.setVisible(true);
        });
    }
}*/
/*import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class BackgroundPanelView extends JPanel {
    private Image backgroundImage;

    public BackgroundPanelView(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class ViewChargesPage extends JPanel {

    public ViewChargesPage(JFrame mainFrame) {
        setLayout(new BorderLayout());

        // ✅ Background panel
        BackgroundPanelView background = new BackgroundPanelView("/IMG_0191.JPG");
        background.setLayout(new BorderLayout());
        add(background);

        // ✅ Header panel (replace with your header if needed)
        background.add(UIComponents.createHeaderPanel(() -> {
            mainFrame.dispose();
            new StaffDashboard();
        }), BorderLayout.NORTH);

        // ✅ Main panel
        background.add(new ViewChargesPanel(), BorderLayout.CENTER);
    }

    // ---------------- VIEW CHARGES PANEL ----------------
    static class ViewChargesPanel extends JPanel {
        private JTextField studentIdField;
        private JTable table;
        private DefaultTableModel tableModel;

        public ViewChargesPanel() {
            setLayout(new BorderLayout(10, 10));
            setOpaque(false);
            setBorder(new EmptyBorder(20, 20, 20, 20));

            JPanel content = new JPanel(new BorderLayout(10, 10));
            content.setBackground(Color.WHITE);
            content.setBorder(new EmptyBorder(20, 20, 20, 20));

            // Top panel
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            topPanel.setOpaque(false);

            JLabel label = new JLabel("VIEW CHARGES");
            label.setFont(new Font("Segoe UI", Font.BOLD, 18));
            label.setForeground(new Color(36, 42, 63));
            topPanel.add(label);

            JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
            searchPanel.setOpaque(false);

            studentIdField = new JTextField(15);
            studentIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            studentIdField.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200)),
                    new EmptyBorder(5, 5, 5, 5)
            ));

            JButton searchButton = new JButton("Search");
            searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            searchButton.setBackground(new Color(70, 130, 180));
            searchButton.setForeground(Color.WHITE);
            searchButton.setFocusPainted(false);
            searchButton.setBorder(new LineBorder(new Color(50, 90, 130), 1));
            searchButton.addActionListener(e -> performSearch());

            searchPanel.add(studentIdField, BorderLayout.CENTER);
            searchPanel.add(searchButton, BorderLayout.EAST);
            topPanel.add(searchPanel);

            // Table setup
            String[] columnNames = {"Title", "Book ID", "Quantity", "Status", "Charge"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table = new JTable(tableModel);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
            table.setRowHeight(25);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(new LineBorder(new Color(200, 200, 200)));

            content.add(topPanel, BorderLayout.NORTH);
            content.add(scrollPane, BorderLayout.CENTER);
            add(content, BorderLayout.CENTER);
        }

        private void performSearch() {
            String studentId = studentIdField.getText().trim();

            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            searchRecords(studentId);
        }

        private void searchRecords(String id) {
            tableModel.setRowCount(0); // clear table

            try (BufferedReader reader = new BufferedReader(new FileReader("Book Issue Record.txt"))) {
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
                        for (String bookLine : books) {
                            String[] parts = bookLine.split(" - ");
                            if (parts.length == 3) {
                                String title = parts[0];
                                String bookId = parts[1];
                                String qtyStr = parts[2];
                                int quantity = Integer.parseInt(qtyStr);
                                String status = calculateStatus(quantity);
                                String charge = calculateCharge(bDate, rDate);

                                tableModel.addRow(new Object[]{title, bookId, qtyStr, status, charge});
                            }
                        }
                        books.clear();
                        match = false;
                    }
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
            }
        }

        private String calculateStatus(int quantity) {
            return (quantity == 0) ? "Unavailable" : "Available";
        }

        private String calculateCharge(String borrowDateStr, String returnDateStr) {
            try {
                LocalDate borrowDate = LocalDate.parse(borrowDateStr);
                LocalDate returnDate = LocalDate.parse(returnDateStr);
                long days = ChronoUnit.DAYS.between(borrowDate, returnDate);

                if (days <= 7) {
                    return "0.00";
                } else if (days <= 20) {
                    return "8.00";
                } else {
                    return "15.00";
                }
            } catch (Exception ex) {
                return "Error";
            }
        }
    }

    // ---------------- TEST RUN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agile Access Library Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setContentPane(new ViewChargesPage(frame));
            frame.setVisible(true);
        });
    }
}*/
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ViewChargesPage extends JPanel {

    public ViewChargesPage(JFrame mainFrame) {
        setLayout(new BorderLayout());

        // Background Panel
        JPanel background = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/IMG_0191.JPG")); // Pastikan file ada
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        add(background);

        // Header
        background.add(UIComponents.createHeaderPanel(() -> {
            mainFrame.dispose();
            new StaffDashboard();
        }), BorderLayout.NORTH);

        // Content Panel
        background.add(new ViewChargesPanel(), BorderLayout.CENTER);
    }

    static class ViewChargesPanel extends JPanel {
        private JTextField studentIdField;
        private JTable table;
        private DefaultTableModel tableModel;

        public ViewChargesPanel() {
            setLayout(new BorderLayout(10, 10));
            setOpaque(false);
            setBorder(new EmptyBorder(20, 20, 20, 20));

            JPanel content = new JPanel(new BorderLayout(10, 10));
            content.setBackground(new Color(255, 255, 255, 180)); // Slight transparency
            content.setBorder(new EmptyBorder(20, 20, 20, 20));

            // Top Panel
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            topPanel.setOpaque(false);

            JLabel label = new JLabel("VIEW CHARGES");
            label.setFont(new Font("Segoe UI", Font.BOLD, 18));
            label.setForeground(new Color(36, 42, 63));
            topPanel.add(label);

            studentIdField = new JTextField(15);
            JButton searchBtn = new JButton("Search");
            searchBtn.addActionListener(e -> searchRecords());

            topPanel.add(studentIdField);
            topPanel.add(searchBtn);

            // Table
            String[] columns = {"Title", "Book ID", "Quantity", "Borrow Date", "Return Date", "Charge (RM)"};
            tableModel = new DefaultTableModel(columns, 0);
            table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);

            content.add(topPanel, BorderLayout.NORTH);
            content.add(scrollPane, BorderLayout.CENTER);
            add(content, BorderLayout.CENTER);
        }

        private void searchRecords() {
            String id = studentIdField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setRowCount(0); // Clear table
            boolean found = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("Book Issue Record.txt"))) {
                String line;
                String sid = "", borrowDate = "", returnDate = "";
                java.util.List<String> books = new ArrayList<>();
                boolean match = false;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("STUDENT_ID:")) {
                        sid = line.substring(11).trim();
                        match = sid.equals(id);
                    } else if (line.startsWith("BORROW_DATE:") && match) {
                        borrowDate = line.substring(12).trim();
                    } else if (line.startsWith("RETURN_DATE:") && match) {
                        returnDate = line.substring(12).trim();
                    } else if (line.startsWith("BOOK_") && match) {
                        books.add(line.split(":", 2)[1].trim());
                    } else if (line.equals("---") && match) {
                        String charge = calculateCharge(borrowDate, returnDate);
                        for (String book : books) {
                            String[] parts = book.split(" - ", 2);
                            String bookID = parts[0];
                            String title = parts.length > 1 ? parts[1] : "Unknown";
                            tableModel.addRow(new Object[]{title, bookID, "1", borrowDate, returnDate, charge});
                        }
                        found = true;
                        books.clear();
                        match = false;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(this, "No record found for Student ID: " + id);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage());
            }
        }

        private String calculateCharge(String borrowDateStr, String returnDateStr) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate borrowDate = LocalDate.parse(borrowDateStr, formatter);
                LocalDate returnDate = LocalDate.parse(returnDateStr, formatter);
                long days = ChronoUnit.DAYS.between(borrowDate, returnDate);

                if (days <= 7) return "0.00";
                else if (days <= 20) return "8.00";
                else return "15.00";

            } catch (Exception ex) {
                return "Error";
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agile Access Library Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
            frame.setContentPane(new ViewChargesPage(frame));
            frame.setVisible(true);
        });
    }
}






