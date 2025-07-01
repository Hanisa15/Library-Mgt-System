import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        setLayout(new GridBagLayout()); // Untuk center content
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Semi-transparent overlay
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

public class LibrarySystem {
    JFrame frame;
    JButton studentBtn, staffBtn;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibrarySystem::new);
    }

    public LibrarySystem() {
        frame = new JFrame("Library Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel panel = new BackgroundPanel("background.jpeg");

        // Panel yang mengandungi semua komponen (logo, title, button)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Logo + Title (dalam satu panel horizontal)
        JPanel logoTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        logoTitlePanel.setOpaque(false);

        ImageIcon logoIcon = new ImageIcon("logo.png");
        Image logo = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logo));

        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);

        logoTitlePanel.add(logoLabel);
        logoTitlePanel.add(titleLabel);

        // Butang
        studentBtn = createTransparentButton("Student");
        staffBtn = createTransparentButton("Staff");

        contentPanel.add(logoTitlePanel);
        contentPanel.add(Box.createVerticalStrut(40));
        contentPanel.add(studentBtn);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(staffBtn);

        // Letak contentPanel di tengah background panel
        panel.add(contentPanel, new GridBagConstraints());

        frame.setContentPane(panel);
        frame.setVisible(true);

        // Actions
        studentBtn.addActionListener(e -> {
            frame.dispose();
            new StudentMenuPage();
        });

        staffBtn.addActionListener(e -> {
            frame.dispose();
            new StaffLoginPage();
        });
    }

    private JButton createTransparentButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 24));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300, 50));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}










