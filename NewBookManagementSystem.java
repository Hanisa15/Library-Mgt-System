import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.time.Year;
import java.util.Scanner;

public class NewBookManagementSystem {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private Image backgroundImage; // Store the background image
    //instance class for add/update functionalities
    private ManageBook bookManager;
    private final Color HEADER_COLOR = new Color(36, 42, 63);
    private final Color ACCENT_COLOR = new Color(70, 130, 180);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 12);
    
    private JComboBox<String> categoryCombo;
    private JComboBox<String> subCategoryCombo;
    private Map<String, String[]> subCategories;

    public NewBookManagementSystem() {
        bookManager = new ManageBook();
        initializeSubCategories();
        initializeMainFrame();
        createMainPanel();
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    
    
    private void initializeSubCategories() {
        subCategories = new HashMap<>();
        subCategories.put("Fiction", new String[]{
                            "Novel",
                            "Short Story",
                            "Poetry",
                            "Drama",
                            "Other"
                        });
        subCategories.put("Non-Fiction", new String[]{
                            "Computer Science",
                            "Chemistry",
                            "Physics",
                            "Engineering Mathematics",
                            "Pure Mathematics",
                            "Biology",
                            "Economics",
                            "Psychology",
                            "Other"
                    });

    }

    private void initializeMainFrame() {
        mainFrame = new JFrame("Book Management Page");
        mainFrame.setSize(900, 650);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.addTab("Update Book", createUpdateBookPanel());
        tabbedPane.addTab("Add New Book", createAddBookPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.setBackground(HEADER_COLOR);
        titleContainer.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);

        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/image/logo.png")); 
            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            leftPanel.add(logoLabel);
        } catch (Exception e) {
            JLabel logoPlaceholder = new JLabel("[LOGO]");
            logoPlaceholder.setForeground(Color.WHITE);
            logoPlaceholder.setFont(new Font("Arial", Font.BOLD, 14));
            leftPanel.add(logoPlaceholder);
        }

        JLabel titleLabel = new JLabel("AGILE ACCESS LIBRARY MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(titleLabel);

        JButton homeButton = createStyledButton("HOME", 100, 35);
        homeButton.addActionListener(e -> showHomeScreen());

        titleContainer.add(leftPanel, BorderLayout.WEST);
        titleContainer.add(homeButton, BorderLayout.EAST);
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        return headerPanel;
    }
//------------------------UPDATE BOOK---------------------------------
    private JPanel createUpdateBookPanel() {
        JPanel updatePanel = new JPanel(new BorderLayout());
        updatePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel bgLabel;
        try {
            ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/bgNew.jpg"));
            bgLabel = new JLabel(bgIcon);
            //utk add button kat atas gambar
            bgLabel.setLayout(new BorderLayout()); 
            updatePanel.add(bgLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            bgLabel = new JLabel(); //error akan kelaur bg putih
            bgLabel.setLayout(new BorderLayout());
            bgLabel.setOpaque(true);
            bgLabel.setBackground(new Color(245, 245, 245));
            updatePanel.add(bgLabel, BorderLayout.CENTER);
        }
    
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // transparent background
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
    
        String[] categories = {"Fiction", "Non-Fiction"};
        String[] statuses = {"Available", "Out of Stock"};
    
        categoryCombo = createStyledComboBox(categories);
        subCategoryCombo = createStyledComboBox(new String[]{"Other"});
        updateSubCategories();
    
        categoryCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateSubCategories();
            }
        });
    
        JTextField bookIdField = createStyledTextField(25, false);
        JTextField titleField = createStyledTextField(25, true);
        JTextField authorField = createStyledTextField(25, true);
        JTextField isbnField = createStyledTextField(25, true);
        JSpinner quantitySpinner = createStyledSpinner(1, 1, 100);
        JSpinner yearSpinner = createStyledSpinner(2023, 1900, 2100);
        JComboBox<String> statusCombo = createStyledComboBox(statuses);
    
        addFormField(formPanel, gbc, 0, 0, "Book ID:", bookIdField); 
        addFormField(formPanel, gbc, 1, 0, "Title:", titleField);
        addFormField(formPanel, gbc, 0, 1, "Author:", authorField);
        addFormField(formPanel, gbc, 1, 1, "ISBN:", isbnField);
        addFormField(formPanel, gbc, 0, 2, "Category:", categoryCombo);
        addFormField(formPanel, gbc, 1, 2, "Genre/Subject:", subCategoryCombo);
        addFormField(formPanel, gbc, 0, 3, "Quantity:", quantitySpinner);
        addFormField(formPanel, gbc, 1, 3, "Publication Year:", yearSpinner);
        addFormField(formPanel, gbc, 0, 4, "Status:", statusCombo);
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
    
        JButton updateButton = createStyledButton("Update", 120, 35);
        JButton deleteButton = createStyledButton("Delete", 120, 35);
        JButton cancelButton = createStyledButton("Cancel", 120, 35);
    
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
    
        // Add form and buttons to the bgLabel (which has the background image)
        bgLabel.add(formPanel, BorderLayout.CENTER);
        bgLabel.add(buttonPanel, BorderLayout.SOUTH);
    
        return updatePanel;
    }


    //---------------------------- ADD BOOK ------------------------------------
    private JPanel createAddBookPanel() {
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel bgLabel;
        try {
            ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/bgNew.jpg"));
            bgLabel = new JLabel(bgIcon);
            bgLabel.setLayout(new BorderLayout()); // enable layering on image
            addPanel.add(bgLabel, BorderLayout.CENTER); 
        } catch (Exception e) {
            bgLabel = new JLabel(); // fallback if image not found
            bgLabel.setLayout(new BorderLayout());
            bgLabel.setOpaque(true);
            bgLabel.setBackground(new Color(245, 245, 245));
            addPanel.add(bgLabel, BorderLayout.CENTER);
        }
        
        //---FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        //set to transparent to see the bg image
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        //CATEGORY 
        String[] categories = {"Fiction", "Non-Fiction"};
        // Initialize category components
        categoryCombo = createStyledComboBox(categories);
        subCategoryCombo = createStyledComboBox(new String[]{"Other"});
        updateSubCategories();
        
        categoryCombo.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateSubCategories();
            }
        });

        JTextField titleField = createStyledTextField(25, true);
        JTextField authorField = createStyledTextField(25, true);
        JTextField isbnField = createStyledTextField(25, true);
        JSpinner quantitySpinner = createStyledSpinner(1, 1, 100);
        JSpinner yearSpinner = createStyledSpinner(2023, 1900, 
        Year.now().getValue());

        addFormField(formPanel, gbc, 0, 0, "Title:", titleField); 
        addFormField(formPanel, gbc, 1, 0, "Author:", authorField);
        addFormField(formPanel, gbc, 0, 1, "ISBN:", isbnField);
        addFormField(formPanel, gbc, 1, 1, "Category:", categoryCombo);
        addFormField(formPanel, gbc, 0, 2, "Genre/Subject:", subCategoryCombo);
        addFormField(formPanel, gbc, 1, 2, "Quantity:", quantitySpinner);
        addFormField(formPanel, gbc, 0, 3, "Publication Year:", yearSpinner);
        
        //Add your formPanel _into_ the bgLabel
        bgLabel.add(formPanel, BorderLayout.CENTER);
        
        //BUTTONS KAT CENTER
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        JButton addButton = createStyledButton("Add Book", 120, 35);
        JButton cancelButton = createStyledButton("Cancel", 120, 35);
        
            // Add Book button action
        addButton.addActionListener(e -> {
            // Get all field values
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            String subCategory = (String) subCategoryCombo.getSelectedItem();
            int quantity = (int) quantitySpinner.getValue();
            int year = (int) yearSpinner.getValue();
    
            // Validate inputs
            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please fill in all required fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Validate ISBN format (simple check)
            if (!isbn.matches("[0-9-]+")) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "ISBN should contain only numbers and hyphens", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Validate publication year
            if (year > Year.now().getValue()) {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Publication year cannot be in the future", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // shortcut for if else (1 for Fiction, 2 for Non-Fiction)
            int type = category.equals("Fiction") ? 1 : 2;
    
            // Try to add the book
            boolean success = bookManager.addNewBook(
                                  type, 
                                  title,
                                  author,
                                  isbn,
                                  quantity, 
                                  year, 
                                  subCategory
                                );
                                    
            if (success) {
                // Clear fields after successful addition
                titleField.setText("");
                authorField.setText("");
                isbnField.setText("");
                quantitySpinner.setValue(1);
                yearSpinner.setValue(Year.now().getValue());
                categoryCombo.setSelectedIndex(0);
                subCategoryCombo.setSelectedIndex(0);
                
                String newID = bookManager.getLastAddedBookId();
                JOptionPane.showMessageDialog(mainFrame, 
                    "Book added successfully!\nBook ID: " + newID, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Failed to add book. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

                // Cancel button action
        cancelButton.addActionListener(e -> {
            // set them back to null
            titleField.setText("");
            authorField.setText("");
            isbnField.setText("");
            quantitySpinner.setValue(1);
            yearSpinner.setValue(Year.now().getValue());
            categoryCombo.setSelectedIndex(0);
            subCategoryCombo.setSelectedIndex(0);
        });
    
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        //add kat bgLabel isnetad of addPanel sbb nak atas gambar
        bgLabel.add(buttonPanel, BorderLayout.SOUTH);
    
        return addPanel;
    }
    

    private void updateSubCategories() {
        String selectedCategory = (String) categoryCombo.getSelectedItem();
        subCategoryCombo.setModel(new DefaultComboBoxModel<>(subCategories.get(selectedCategory)));
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int col, 
                            int row, String label, JComponent field) {
        gbc.gridx = col * 2;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(Color.WHITE);
        panel.add(lbl, gbc);

        gbc.gridx = col * 2 + 1;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(ACCENT_COLOR.darker(), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createStyledTextField(int columns, boolean editable) {
        JTextField textField = new JTextField(columns);
        textField.setFont(FIELD_FONT);
        textField.setEditable(editable);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(FIELD_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(2, 5, 2, 5)
        ));
        return comboBox;
    }

    private JSpinner createStyledSpinner(int value, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));
        spinner.setFont(FIELD_FONT);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200)),
            new EmptyBorder(2, 5, 2, 5)
        ));
        return spinner;
    }

    private void showHomeScreen() {
        JOptionPane.showMessageDialog(mainFrame,
            "Welcome to Agile Access Library Management System",
            "Home",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NewBookManagementSystem();
        });
    }
}