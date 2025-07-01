import javax.swing.*;
import java.awt.*;

public class StaffDashboard {
    JFrame frame;
    JButton manageBooksBtn, issueBooksBtn, viewChargesBtn, listBooksBtn, backBtn;

    public StaffDashboard() {
        frame = new JFrame("Staff Dashboard");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen

        // Use background panel (already defined in Student class)
        BackgroundPanelStudent backgroundPanel = new BackgroundPanelStudent("dash.png");
        backgroundPanel.setLayout(new BorderLayout());

        // ===== Header =====
        JPanel header = UIComponents.createHeaderPanel(() -> {
            frame.dispose();
            new LibrarySystem(); // Go back to home
        });
        backgroundPanel.add(header, BorderLayout.NORTH);

        // ===== Center Area =====
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false); // Important for transparent background

        JLabel titleLabel = new JLabel("STAFF DASHBOARD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        centerPanel.add(titleLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(700, 300));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        manageBooksBtn = createStyledButton("Book Management");
        issueBooksBtn = createStyledButton("Book Issuing");
        viewChargesBtn = createStyledButton("View Charges");
        listBooksBtn = createStyledButton("List of Books");

        buttonPanel.add(manageBooksBtn);
        buttonPanel.add(issueBooksBtn);
        buttonPanel.add(viewChargesBtn);
        buttonPanel.add(listBooksBtn);

        centerPanel.add(buttonPanel);

        // ===== Back Button =====
        backBtn = createStyledButton("Back");
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        backBtn.addActionListener(e -> {
            frame.dispose();
            new StaffLoginPage();
        });

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(backBtn);

        // Add center content to background
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // Set the background panel as the content pane
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);

        // ===== Actions for buttons =====
        manageBooksBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Opening Book Management..."));
        issueBooksBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Opening Book Issuing..."));
        viewChargesBtn.addActionListener(e -> {
    frame.getContentPane().removeAll();
    frame.setContentPane(new ViewChargesPage(frame));
    frame.revalidate();
    frame.repaint();
        });

        listBooksBtn.addActionListener(e -> {
        frame.dispose(); // Optional: close the dashboard
        new BookListInterface().setVisible(true);
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLUE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        new StaffDashboard();
    }
}





