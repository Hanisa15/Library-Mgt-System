import javax.swing.*;
import java.awt.*;

class BackgroundPanelStaff extends JPanel {
    private Image backgroundImage;

    public BackgroundPanelStaff(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class StaffLoginPage {
    JFrame frame;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, backBtn;

    public StaffLoginPage() {
        frame = new JFrame("Staff Login");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

        BackgroundPanelStaff loginPanel = new BackgroundPanelStaff("try.png");
        loginPanel.setLayout(new BorderLayout());

        // Header
        JPanel header = UIComponents.createHeaderPanel(() -> {
            frame.dispose();
            new LibrarySystem();
        });
        loginPanel.add(header, BorderLayout.NORTH);

        // Center content wrapper
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Title label
        JLabel titleLabel = new JLabel("STAFF LOGIN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 40, 0); // Top margin
        centerPanel.add(titleLabel, gbc);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        userLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        centerPanel.add(userLabel, gbc);

        // Username Field
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(usernameField, gbc);

        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        passLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(passLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(passwordField, gbc);

        // Buttons
        loginBtn = createStyledButton("Login");
        backBtn = createStyledButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backBtn);
        buttonPanel.add(loginBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 0, 0, 0);
        centerPanel.add(buttonPanel, gbc);

        loginPanel.add(centerPanel, BorderLayout.CENTER);

        frame.setContentPane(loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Actions
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                new StaffDashboard();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Credentials!");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new LibrarySystem();
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
}//which part should i edit to make the back button move to left a bit and login button to the right 






