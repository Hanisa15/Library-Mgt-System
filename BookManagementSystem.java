import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BookManagementSystem {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private final Color HEADER_COLOR = new Color(36, 42, 63);
    private final Color FORM_BG = new Color(255, 255, 255, 220);
    private final Color ACCENT_COLOR = new Color(70, 130, 180);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 12);

    public BookManagementSystem() {
        initializeMainFrame();
        createMainPanel();
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private void initializeMainFrame() {
        mainFrame = new JFrame("Agile Access Library Management System");
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
            JLabel logoPlaceholder = new JLabel("[LOGO]"); //error ! : output : LOGO text
            logoPlaceholder.setForeground(Color.WHITE);
            logoPlaceholder.setFont(new Font("Arial", Font.BOLD, 14));
            leftPanel.add(logoPlaceholder);
        }

        JLabel titleLabel = new JLabel("AGILE ACCESS LIBRARY MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        leftPanel.add(titleLabel);

        JButton homeButton = createStyledButton("HOME", 100, 35);
        homeButton.addActionListener(e -> showHomeScreen()); //will go back to page menu homepage

        titleContainer.add(leftPanel, BorderLayout.WEST);
        titleContainer.add(homeButton, BorderLayout.EAST);
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createUpdateBookPanel() {
        JPanel updatePanel = new JPanel(new BorderLayout());
        updatePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        try {
            ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/bg.jpg"));
            JLabel bgLabel = new JLabel(bgIcon);
            bgLabel.setLayout(new BorderLayout());
            updatePanel.add(bgLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            updatePanel.setBackground(new Color(245, 245, 245));
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(FORM_BG);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        String[] categories = {"Fiction", "Non-Fiction", "Science", "History", "Biography"};
        String[] statuses = {"Available", "Out of Stock"};

        JTextField bookIdField = createStyledTextField(25, false);
        JTextField titleField = createStyledTextField(25, true);
        JTextField authorField = createStyledTextField(25, true);
        JTextField isbnField = createStyledTextField(25, true);
        JComboBox<String> categoryCombo = createStyledComboBox(categories);
        JSpinner quantitySpinner = createStyledSpinner(1, 1, 100);
        JSpinner yearSpinner = createStyledSpinner(2023, 1900, 2100);
        JComboBox<String> statusCombo = createStyledComboBox(statuses);

        addFormField(formPanel, gbc, 0, 0, "Book ID:", bookIdField);
        addFormField(formPanel, gbc, 1, 0, "Title:", titleField);
        addFormField(formPanel, gbc, 0, 1, "Author:", authorField);
        addFormField(formPanel, gbc, 1, 1, "ISBN:", isbnField);
        addFormField(formPanel, gbc, 0, 2, "Category:", categoryCombo);
        addFormField(formPanel, gbc, 1, 2, "Quantity:", quantitySpinner);
        addFormField(formPanel, gbc, 0, 3, "Publication Year:", yearSpinner);
        addFormField(formPanel, gbc, 1, 3, "Status:", statusCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton updateButton = createStyledButton("Update", 120, 35);
        JButton deleteButton = createStyledButton("Delete", 120, 35);
        JButton cancelButton = createStyledButton("Cancel", 120, 35);
        

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        updatePanel.add(formPanel, BorderLayout.CENTER);
        updatePanel.add(buttonPanel, BorderLayout.SOUTH);

        return updatePanel;
    }

    private JPanel createAddBookPanel() {
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        try {
            ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/bg.jpg"));
            JLabel bgLabel = new JLabel(bgIcon);
            bgLabel.setLayout(new BorderLayout());
            addPanel.add(bgLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            addPanel.setBackground(new Color(249, 245, 245));
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(FORM_BG);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        String[] categories = {"Fiction", "Non-Fiction", "Science", "History", "Biography"};

        JTextField titleField = createStyledTextField(25, true);
        JTextField authorField = createStyledTextField(25, true);
        JTextField isbnField = createStyledTextField(25, true);
        JComboBox<String> categoryCombo = createStyledComboBox(categories);
        JSpinner quantitySpinner = createStyledSpinner(1, 1, 100);
        JSpinner yearSpinner = createStyledSpinner(2023, 1900, 2100);

        addFormField(formPanel, gbc, 0, 0, "Title:", titleField);
        addFormField(formPanel, gbc, 1, 0, "Author:", authorField);
        addFormField(formPanel, gbc, 0, 1, "ISBN:", isbnField);
        addFormField(formPanel, gbc, 1, 1, "Category:", categoryCombo);
        addFormField(formPanel, gbc, 0, 2, "Quantity:", quantitySpinner);
        addFormField(formPanel, gbc, 1, 2, "Publication Year:", yearSpinner);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton addButton = createStyledButton("Add Book", 120, 35);
        JButton cancelButton = createStyledButton("Cancel", 120, 35);

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        addPanel.add(formPanel, BorderLayout.CENTER);
        addPanel.add(buttonPanel, BorderLayout.SOUTH);

        return addPanel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int col, int row, String label, JComponent field) {
        gbc.gridx = col * 2;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        panel.add(lbl, gbc);

        gbc.gridx = col * 2 + 1;
        panel.add(field, gbc);
    }

    // Helper methods for consistent styling: for styling our button
    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(ACCENT_COLOR.darker(), 1)); //outline of button
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
            new BookManagementSystem();
        });
    }
}