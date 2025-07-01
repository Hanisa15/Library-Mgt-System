import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIComponents {
    private static final Color HEADER_COLOR = new Color(36, 42, 155); // Ubah ikut tema kau

    public static JPanel createHeaderPanel(Runnable homeAction) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.setBackground(HEADER_COLOR);
        titleContainer.setOpaque(false);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);

        try {
            ImageIcon originalIcon = new ImageIcon(UIComponents.class.getResource("logo.png")); 
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
        homeButton.addActionListener(e -> {
            if (homeAction != null) {
                homeAction.run();
            }
        });

        titleContainer.add(leftPanel, BorderLayout.WEST);
        titleContainer.add(homeButton, BorderLayout.EAST);
        headerPanel.add(titleContainer, BorderLayout.CENTER);
        return headerPanel;
    }

    private static JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return button;
    }
}
